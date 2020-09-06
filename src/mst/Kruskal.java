package mst;

import bfs.CC;
import graph.WeightedGraph;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Daolin
 * @date 2020/09/05
 */
public class Kruskal {

    private WeightedGraph G;
    private ArrayList<WeightedEdge> mst;

    public Kruskal(WeightedGraph G){
        this.G = G;
        mst = new ArrayList<>();

        CC cc = new CC(G);
        if(cc.count() > 1) return;

        // Kruskal
        ArrayList<WeightedEdge> edges = new ArrayList<>();
        for(int v = 0; v < G.V(); v++){
            for(int w: G.adj(v))
                if(v < w)
                    edges.add(new WeightedEdge(v, w, G.getWeight(v, w)));
        }
        Collections.sort(edges);
        UnionFind uf = new UnionFind(G.V());
        for(WeightedEdge edge : edges){
            int v = edge.getV();
            int w = edge.getW();
            if(!uf.isConnected(v, w)){
                uf.unionElements(v, w);
                mst.add(edge);
            }
        }
    }

    public ArrayList<WeightedEdge> result(){
        return mst;
    }


    public static void main(String[] args) {
        WeightedGraph g = new WeightedGraph("g.txt");
        Kruskal kruskal = new Kruskal(g);
        System.out.println(kruskal.result());
    }

}
