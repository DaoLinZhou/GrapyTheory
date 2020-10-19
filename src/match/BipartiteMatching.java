package match;

import dfs.BipartitionDetection;
import graph.AdjSet;
import graph.Graph;
import graph.WeightedGraph;
import netflow.MaxFlow;

/**
 * @author Daolin
 * @date 2020/10/18
 */
public class BipartiteMatching {

    private Graph G;
    private int maxMatching;

    public BipartiteMatching(Graph G){
        BipartitionDetection bd = new BipartitionDetection(G);
        if(!bd.isBipartiteGraph()){
            throw new IllegalArgumentException("BipartiteMatching only works on BipartiteGraph");
        }
        this.G = G;
        int[] colors = bd.colors();

        // 原点为 V, 汇点为 V+1
        WeightedGraph network = new WeightedGraph(G.V()+2, true);

        for(int v = 0; v < G.V(); v++){
            // 连接这个点和源点/汇点
            if(colors[v] == 0){
                network.addEdge(G.V(), v, 1);
            } else {
                network.addEdge(v, G.V()+1, 1);
            }
            // 连接这个点和它在无向图中连接的点
            for(int w: G.adj(v)){
                if(v < w){  // 防止添加2次边, 所以通过大小进行边的过滤
                    if(colors[v] == 0) network.addEdge(v, w, 1);
                    else network.addEdge(w, v, 1);
                }
            }
        }

        MaxFlow maxFlow = new MaxFlow(network, G.V(), G.V()+1);
        maxMatching = (int) maxFlow.result();
    }

    public int maxMatching(){
        return maxMatching;
    }

    public boolean isPerfectMatching(){
        return maxMatching * 2 == G.V();
    }

    public static void main(String[] args) {
        Graph g = new AdjSet("match_test.txt");
        BipartiteMatching dm = new BipartiteMatching(g);
        System.out.println(dm.maxMatching());
    }

}
