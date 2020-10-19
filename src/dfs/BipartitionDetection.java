package dfs;

import graph.AdjSet;
import graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;

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
                dfs(v);
            }
        }
    }

    // 此时v已经被染色, 要对v的相邻节点染色
    private void dfs(int v){
        for(int w : G.adj(v)){
            if(!isBipartiteGraph) return; // 如果不是二分图直接返回
            if(color[w] == -1){           // 如果没被染色, 则染色并递归
                // 如果 color[v] == 1 那么 color[w] == 0, 如果 color[v] == 0 那么 color[w] == 1
                color[w] = 1 - color[v];
                dfs(w);
            } else if(color[w] == color[v]) { // 如果被染色, 如果和自己颜色一样, 说明不是二分图
                isBipartiteGraph = false;
                return;
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

    public int[] colors(){
        return color;
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
