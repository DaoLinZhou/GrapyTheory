package mst;

import bfs.CC;
import graph.WeightedGraph;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * @author Daolin
 * @date 2020/09/05
 */
public class Prime {

    private WeightedGraph G;
    private ArrayList<WeightedEdge> mst;

    public Prime(WeightedGraph G){
        this.G = G;
        mst = new ArrayList<>();

        CC cc = new CC(G);
        if(cc.count() > 1) return;

        // Lazy Prim
        boolean[] visited = new boolean[G.V()];
        visited[0] = true;
        PriorityQueue<WeightedEdge> pq = new PriorityQueue<>();
        for (int w : G.adj(0)){
            pq.add(new WeightedEdge(0, w, G.getWeight(0, w)));
        }
        while(!pq.isEmpty()){
            WeightedEdge edge = pq.poll();
            if(visited[edge.getV()] != visited[edge.getW()]){
                mst.add(edge);
                int v = visited[edge.getV()] ? edge.getW() : edge.getV(); // 获取没被访问过的那个点
                visited[v] = true;
                for (int w : G.adj(v)){
                    if(!visited[w])
                        pq.add(new WeightedEdge(v, w, G.getWeight(v, w)));
                }
            }
        }

    }

    public ArrayList<WeightedEdge> result(){
        return mst;
    }
    public static void main(String[] args) {
        WeightedGraph g = new WeightedGraph("g.txt");
        Prime prime = new Prime(g);
        System.out.println(prime.result());
    }

}
