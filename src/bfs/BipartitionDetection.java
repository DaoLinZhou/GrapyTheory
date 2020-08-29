package bfs;

import graph.AdjSet;
import graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Daolin
 * @date 2020/08/28
 */
public class BipartitionDetection {

    private Graph G;
    private int[] color; // 把节点分为 0, 1
    private boolean isBipartiteGraph = true;

    public BipartitionDetection(Graph G){
        this.G = G;
        color = new int[G.V()];
        Arrays.fill(color, -1);

        for(int v = 0; v < G.V(); v++){
            if(!isBipartiteGraph) return;
            if(color[v] == -1){
                color[v] = 0;
                bfs(v);
            }
        }
    }

    private void bfs(int s){
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);

        while (!queue.isEmpty()){
            int v = queue.remove();
            for(int w : G.adj(v)){
                if(color[w] == -1){
                    queue.add(w);
                    color[w] = 1 - color[v];
                } else if(color[w] == color[v]){
                    isBipartiteGraph = false;
                    return;
                }
            }
        }
    }

    // 判断是否为二分图
    public boolean isBipartiteGraph(){
        return isBipartiteGraph;
    }

    // 获得二分图的两部分中各自部分的节点的节点
    public ArrayList<Integer>[] getGroups(){
        if(!isBipartiteGraph){
            throw new IllegalArgumentException("This is not a BipartiteGraph");
        }
        ArrayList<Integer>[] res = new ArrayList[2];
        res[0] = new ArrayList<>();
        res[1] = new ArrayList<>();

        for(int v = 0; v < color.length; v++){
            res[color[v]].add(v);
        }
        return res;
    }

    public static void main(String[] args) {
        Graph g = new AdjSet("g.txt");
        BipartitionDetection bg = new BipartitionDetection(g);
        System.out.println(bg.isBipartiteGraph());

        Graph g2 = new AdjSet("g2.txt");
        BipartitionDetection bg2 = new BipartitionDetection(g2);
        System.out.println(bg2.isBipartiteGraph());
        System.out.println("group 0:" + bg.getGroups()[0]);
        System.out.println("group 1:" + bg.getGroups()[1]);
    }
}
