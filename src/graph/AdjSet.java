package graph;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * @author Daolin
 * @date 2020/08/25
 */
public class AdjSet implements Graph, Cloneable{

    private int V; // 顶点数
    private int E; // 边数
    private boolean directed;
    private TreeSet<Integer>[] adj;
    private int[] indegrees, outdegrees;

    public AdjSet(TreeSet<Integer>[] adj, boolean directed){
        this.adj = adj;
        this.directed = directed;
        this.V = adj.length;
        this.E = 0;

        indegrees = new int[V];
        outdegrees = new int[V];

        for(int v = 0; v < V; v++)
            for(int w: adj[v]){
                outdegrees[v] ++;
                indegrees[w] ++;
                this.E++;
            }
        if(!directed)
            this.E /= 2;
    }

    public AdjSet(String filename){
        this(filename, false);
    }

    public AdjSet(String filename, boolean directed){
        this.directed = directed;
        File file = new File(filename);
        try(Scanner scanner = new Scanner(file)){

            V = scanner.nextInt();
            if(V < 0){
                throw new IllegalArgumentException("V must be non-negative");
            }

            adj = new TreeSet[V];
            for(int i = 0; i < V; i++)
                adj[i] = new TreeSet<Integer>();

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

                if(a == b) throw new IllegalArgumentException("Self Loop id Detected!");
                if(adj[a].contains(b)) throw new IllegalArgumentException("Parallel Edges are Detected! ");

                adj[a].add(b);
                if(directed){
                    outdegrees[a] ++;
                    indegrees[b] ++;
                }
                if(!directed)
                    adj[b].add(a);
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public Graph reverseGraph(){
        TreeSet<Integer>[] rAdj = new TreeSet[V];
        for(int i = 0; i < V; i++)
            rAdj[i] = new TreeSet<>();
        for(int v = 0; v < V; v++)
            for(int w: adj(v))
                rAdj[w].add(v);
        return new AdjSet(rAdj, directed);
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
        return adj[v].contains(w);
    }

    public Iterable<Integer> adj(int v){
        validateVertex(v);
        return adj[v];
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

    @Override
    public boolean isDirected() {
        return directed;
    }

    @Override
    public void removeEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        if(adj[v].contains(w)){
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
        AdjSet cloned = null;
        try {
            cloned = (AdjSet) super.clone();
            cloned.adj = new TreeSet[V];
            for(int v = 0; v < V; v++){
                cloned.adj[v] = new TreeSet<>();
                for(int w : this.adj[v]){
                    cloned.adj[v].add(w);
                }
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return cloned;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("V = %d, E = %d, directed = %b\n", V, E, directed));
        for(int v = 0; v < V; v++){
            sb.append(String.format("%d : ", v));
            for(int w: adj[v]){
                sb.append(String.format("%d ", w));
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        AdjSet g = new AdjSet("g4.txt", true);
        System.out.print(g.toString());

        for(int v = 0; v < g.V(); v++)
            System.out.println(g.indegree(v) + " " + g.outdegree(v));
    }

}
