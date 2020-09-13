package topo;

import dfs.DirectedCycleDetection;
import dfs.GraphDFS;
import graph.AdjSet;
import graph.Graph;

import java.util.ArrayList;
import java.util.Collections;


/**
 * @author Daolin
 * @date 2020/09/10
 */
public class TopoSort2 {

    Graph G;
    private ArrayList<Integer> res;
    private boolean hasCycle = false;

    public TopoSort2(Graph G){
        if(!G.isDirected())
            throw new IllegalArgumentException("Topo Logicial sort only works on directed graph.");
        this.G = G;
        res = new ArrayList<>();

        hasCycle = new DirectedCycleDetection(G).hasCycle();
        if(hasCycle) return;

        GraphDFS dfs = new GraphDFS(G);
        for(int v : dfs.post())
            res.add(v);
        Collections.reverse(res);
    }


    public boolean hasCycle(){
        return hasCycle;
    }

    public ArrayList<Integer> result(){
        return res;
    }

    public static void main(String[] args) {
        TopoSort2 topoSort = new TopoSort2(new AdjSet("ug.txt", true));
        System.out.println(topoSort.result());
    }

}
