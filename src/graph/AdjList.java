package graph;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Daolin
 * @date 2020/08/25
 */
public class AdjList implements Graph, Cloneable{

    private int V; // 顶点数
    private int E; // 边数

    private LinkedList<Integer>[] adj;

    public AdjList(String filename){
        File file = new File(filename);
        try(Scanner scanner = new Scanner(file)){

            V = scanner.nextInt();
            if(V < 0){
                throw new IllegalArgumentException("V must be non-negative");
            }

            adj = new LinkedList[V];
            for(int i = 0; i < V; i++)
                adj[i] = new LinkedList<Integer>();

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
                adj[b].add(a);
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
        return adj[v].contains(w);
    }

    public LinkedList<Integer> adj(int v){
        validateVertex(v);
        return adj[v];
    }

    public int degree(int v){
        return adj(v).size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("V = %d, E = %d\n", V, E));
        for(int v = 0; v < V; v++){
            sb.append(String.format("%d : ", v));
            for(int w: adj[v]){
                sb.append(String.format("%d ", w));
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public void removeEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].remove((Integer)w);
        adj[w].remove((Integer)v);
    }

    @Override
    public Object clone() {
        AdjList cloned = null;
        try {
            cloned = (AdjList) super.clone();
            cloned.adj = new LinkedList[V];
            for(int v = 0; v < V; v++){
                cloned.adj[v] = new LinkedList<>();
                for(int w : this.adj[v]){
                    cloned.adj[v].add(w);
                }
            }

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return cloned;
    }

    public static void main(String[] args) {
        AdjList adjList = new AdjList("g.txt");
        System.out.print(adjList.toString());
    }


}
