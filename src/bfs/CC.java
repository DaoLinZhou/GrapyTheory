package bfs;

import graph.AdjSet;
import graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Daolin
 * @date 2020/08/29
 */
public class CC {

    private Graph G;
    private int[] visited;
    private int ccount = 0;


    public CC(Graph G) {
        this.G = G;
        visited = new int[G.V()];
        Arrays.fill(visited, -1);
        for (int v = 0; v < G.V(); v++) {
            if (visited[v] == -1) {
                bfs(v);
                ccount ++;
            }
        }
    }

    private void bfs(int s){
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = ccount;

        while (!queue.isEmpty()){
            int v = queue.remove();
            for(int w : G.adj(v)){
                if(visited[w] == -1){
                    queue.add(w);
                    visited[w] = ccount;
                }
            }
        }
    }

    // 获得这张图的联通分量的个数
    public int count(){
        return this.ccount;
    }

    // 判断两点是否连接(在同一联通分量)
    public boolean isConnected(int v, int w){
        G.validateVertex(v);
        G.validateVertex(w);
        return visited[v] == visited[w];
    }

    // 获取图的所有联通分量
    public ArrayList<Integer>[] components(){
        ArrayList<Integer>[] res = new ArrayList[ccount];
        for(int i = 0; i < ccount; i++){
            res[i] = new ArrayList<>();
        }
        for(int v = 0; v < G.V(); v++){
            res[visited[v]].add(v);
        }
        return res;
    }

    public static void main(String[] args) {
        Graph g = new AdjSet("g.txt");
        CC cc = new CC(g);
        System.out.println(cc.count());
        System.out.println(cc.isConnected(0, 6));
        System.out.println(cc.isConnected(0, 5));

        ArrayList<Integer>[] comp = cc.components();
        for(int ccid = 0; ccid < comp.length; ccid++){
            System.out.print(ccid + " : ");
            for(int w : comp[ccid])
                System.out.print(w + " ");
            System.out.println();
        }

    }

}