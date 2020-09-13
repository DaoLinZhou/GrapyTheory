package dfs;

import graph.AdjSet;
import graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Daolin
 * @date 2020/09/12
 */
public class SCC {

    private Graph G;
    private int[] visited;
    private int sccount = 0;

    public SCC(Graph G) {
        if(!G.isDirected())
            throw new IllegalArgumentException("SCC only works on directed graph");
        this.G = G;
        visited = new int[G.V()];
        Arrays.fill(visited, -1);

        // 获得节点的访问顺序
        GraphDFS dfs = new GraphDFS(G.reverseGraph());
        ArrayList<Integer> order = new ArrayList<>();
        for(int v : dfs.post())
            order.add(v);
        Collections.reverse(order);

        for (int v : order) {
            if (visited[v] == -1) {
                dfs(v);
                sccount ++;
            }
        }
    }

    private void dfs(int v){
        visited[v] = sccount;
        for(int w : G.adj(v))
            if(visited[w] == -1)
                dfs(w);
    }

    // 获得这张图的联通分量的个数
    public int count(){
        return this.sccount;
    }

    // 判断两点是否连接(在同一联通分量)
    public boolean isStronglyConnected(int v, int w){
        G.validateVertex(v);
        G.validateVertex(w);
        return visited[v] == visited[w];
    }

    // 获取图的所有联通分量
    public ArrayList<Integer>[] components(){
        ArrayList<Integer>[] res = new ArrayList[sccount];
        for(int i = 0; i < sccount; i++){
            res[i] = new ArrayList<>();
        }
        for(int v = 0; v < G.V(); v++){
            res[visited[v]].add(v);
        }
        return res;
    }

    public static void main(String[] args) {
        Graph g = new AdjSet("ug3.txt", true);
        SCC scc = new SCC(g);
        System.out.println(scc.count());

        ArrayList<Integer>[] comp = scc.components();
        for(int sccid = 0; sccid < comp.length; sccid++){
            System.out.print(sccid + " : ");
            for(int w : comp[sccid])
                System.out.print(w + " ");
            System.out.println();
        }

    }

}
