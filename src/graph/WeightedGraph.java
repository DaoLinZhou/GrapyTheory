package graph;

import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Daolin
 * @date 2020/09/05
 * 暂时只支持无向带权图
 */
public class WeightedGraph implements Graph, Cloneable {

    private int V; // 顶点数
    private int E; // 边数
    private boolean directed;
    private int[] indegrees, outdegrees;
    private TreeMap<Integer, Double>[] adj;

    public WeightedGraph(String filename){
        this(filename, false);
    }


    public WeightedGraph(String filename, boolean directed){
        this.directed = directed;
        File file = new File(filename);
        try(Scanner scanner = new Scanner(file)){

            V = scanner.nextInt();
            if(V < 0){
                throw new IllegalArgumentException("V must be non-negative");
            }

            adj = new TreeMap[V];
            for(int i = 0; i < V; i++)
                adj[i] = new TreeMap<Integer, Double>();
            indegrees = new int[V];
            outdegrees = new int[V];
            E = scanner.nextInt();
            if(E < 0){
                throw new IllegalArgumentException("E must be non-negative");
            }

            for(int i = 0; i < E; i++){
                int a = scanner.nextInt();
                validateVertex(a);
                int b = scanner.nextInt();
                validateVertex(b);
                double weight = scanner.nextDouble();

                if(a == b) throw new IllegalArgumentException("Self Loop id Detected!");
                if(adj[a].containsKey(b)) throw new IllegalArgumentException("Parallel Edges are Detected! ");

                adj[a].put(b, weight);
                if(directed){
                    indegrees[b] ++;
                    outdegrees[a] ++;
                }
                if(!directed)
                    adj[b].put(a, weight);
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public WeightedGraph(TreeMap<Integer, Double>[] rAdj, boolean directed) {
        this.adj = adj;
        this.directed = directed;
        this.V = adj.length;
        this.E = 0;

        indegrees = new int[V];
        outdegrees = new int[V];

        for(int v = 0; v < V; v++)
            for(int w: adj[v].keySet()){
                outdegrees[v] ++;
                indegrees[w] ++;
                this.E++;
            }
        if(!directed)
            this.E /= 2;
    }

    public Graph reverseGraph(){
        TreeMap<Integer, Double>[] rAdj = new TreeMap[V];
        for(int i = 0; i < V; i++)
            rAdj[i] = new TreeMap<>();
        for(int v = 0; v < V; v++)
            for(Map.Entry<Integer, Double> e: adj[v].entrySet())
                rAdj[e.getKey()].put(v, e.getValue());
        return new WeightedGraph(rAdj, directed);
    }

    public void validateVertex(int v){
        if(v < 0 || v >= V){
            throw new IllegalArgumentException("vertex " + v + " is invalid");
        }
    }

    public int V(){
        return V;
    }

    public int E(){
        return E;
    }

    public boolean hasEdge(int v, int w){
        validateVertex(v);
        validateVertex(w);
        return adj[v].containsKey(w);
    }

    public Iterable<Integer> adj(int v){
        validateVertex(v);
        return adj[v].keySet();
    }

    public int degree(int v){
        if(directed)
            throw new RuntimeException("degree only works in undirected graph.");
        validateVertex(v);
        return adj[v].size();
    }

    public int indegree(int v){
        if(!directed)
            throw new RuntimeException("indegree only works in directed graph.");
        validateVertex(v);
        return indegrees[v];
    }

    public int outdegree(int v){
        if(!directed)
            throw new RuntimeException("outdegrees only works in directed graph.");
        validateVertex(v);
        return outdegrees[v];
    }

    public double getWeight(int v, int w){
        if(hasEdge(v, w))
            return adj[v].get(w);
        throw new IllegalArgumentException(String.format("No edge %d-%d", v, w));
    }

    @Override
    public void removeEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        if(adj[v].containsKey(w)){
            E --;
            if(directed){
                indegrees[w] --;
                outdegrees[v] --;
            }
        }
        adj[v].remove(w);
        if(!directed)
            adj[w].remove(v);
    }

    @Override
    public Object clone() {
        WeightedGraph cloned = null;
        try {
            cloned = (WeightedGraph) super.clone();
            cloned.adj = new TreeMap[V];
            for(int v = 0; v < V; v++){
                cloned.adj[v] = new TreeMap<>();
                for(Map.Entry<Integer, Double> entry : adj[v].entrySet()){
                    cloned.adj[v].put(entry.getKey(), entry.getValue());
                }
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return cloned;
    }

    @Override
    public boolean isDirected() {
        return directed;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("V = %d, E = %d, directed = %b\n", V, E, directed));
        for(int v = 0; v < V; v++){
            sb.append(String.format("%d : ", v));
            for(Map.Entry<Integer, Double> entry : adj[v].entrySet()){
                sb.append(String.format("(%d: %f)", entry.getKey(), entry.getValue()));
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        WeightedGraph g = new WeightedGraph("g.txt", true);
        System.out.print(g.toString());
    }


}
