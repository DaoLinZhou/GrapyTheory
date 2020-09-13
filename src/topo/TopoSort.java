package topo;

import graph.AdjSet;
import graph.Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Daolin
 * @date 2020/09/10
 */
public class TopoSort {

    Graph G;
    private ArrayList<Integer> res;
    private boolean hasCycle = false;

    public TopoSort(Graph G){
        if(!G.isDirected())
            throw new IllegalArgumentException("Topo Logicial sort only works on directed graph.");
        this.G = G;
        res = new ArrayList<>();

        Queue<Integer> queue = new LinkedList<>();

        int[] indegrees = new int[G.V()];

        for(int v = 0; v < G.V(); v++){
            indegrees[v] = G.indegree(v);
            if(indegrees[v] == 0) {
                queue.add(v);
            }
        }

        while (!queue.isEmpty()){
            int curv = queue.remove();
            res.add(curv);
            for(int w : G.adj(curv)){
                indegrees[w] --;
                if(indegrees[w] == 0){
                    queue.add(w);
                }
            }
        }

        if(res.size() != G.V()){
            hasCycle = true;
            res.clear();
        }
    }

    public boolean hasCycle(){
        return hasCycle;
    }

    public ArrayList<Integer> result(){
        return res;
    }

    public static void main(String[] args) {
        TopoSort topoSort = new TopoSort(new AdjSet("ug.txt", true));
        System.out.println(topoSort.result());
    }

}
