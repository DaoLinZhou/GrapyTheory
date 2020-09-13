package graph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * @author Daolin
 * @date 2020/08/25
 */
public class AdjMatrix implements Graph, Cloneable{

    private int V; // 顶点数
    private int E; // 边数
    private boolean directed;
    private int[] indegrees, outdegrees;
    private int[][] adj;

    public AdjMatrix(String filename){
        this(filename, false);
    }

    public AdjMatrix(String filename, boolean directed){
        this.directed = directed;
        File file = new File(filename);
        try(Scanner scanner = new Scanner(file)){

            V = scanner.nextInt();
            if(V < 0){
                throw new IllegalArgumentException("V must be non-negative");
            }

            adj = new int[V][V];
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
                if(adj[a][b] == 1) throw new IllegalArgumentException("Parallel Edges are Detected! ");

                adj[a][b] = 1;
                if(directed){
                    indegrees[b] ++;
                    outdegrees[a] ++;
                }
                if(!directed)
                    adj[b][a] = 1;
            }

        } catch (IOException e){
            e.printStackTrace();
        }
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
        return adj[v][w] == 1;
    }

    public ArrayList<Integer> adj(int v){
        validateVertex(v);
        ArrayList<Integer> res = new ArrayList<>();
        for(int i = 0; i < V; i++)
            if(adj[v][i] == 1)
                res.add(i);
        return res;
    }

    public int degree(int v){
        if(directed)
            throw new RuntimeException("degree only works in undirected graph.") ;
        validateVertex(v);
        return adj(v).size();
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
    public Graph reverseGraph() {
        AdjMatrix ret = (AdjMatrix) this.clone();
        for(int i = 0; i < V; i++)
            for(int j = 0; j < i; j++) {
                int t = ret.adj[i][j];
                ret.adj[i][j] = ret.adj[j][i];
                ret.adj[j][i] = t;
            }
        return ret;
    }

    @Override
    public void removeEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        if(adj[v][w] == 1){
            E --;
            if(directed){
                indegrees[w] --;
                outdegrees[v] --;
            }
        }
        adj[v][w] = 0;
        if(!directed)
            adj[w][v] = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("V = %d, E = %d, directed = %b\n", V, E, directed));
        for(int i = 0; i < V; i++){
            for(int j = 0; j < V; j++){
                sb.append(String.format("%d ", adj[i][j]));
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public Object clone() {
        AdjMatrix cloned = null;
        try {
            cloned = (AdjMatrix) super.clone();
            cloned.adj = new int[V][V];
            for(int v = 0; v < V; v++){
                for(int w = 0; w < V; w++){
                    cloned.adj[v][w] = adj[v][w];
                }
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return cloned;
    }

    public static void main(String[] args) {
        AdjMatrix adjMatrix = new AdjMatrix("g.txt");
        System.out.print(adjMatrix.toString());
    }
}
