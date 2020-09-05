package graph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Daolin
 * @date 2020/08/25
 */
public class AdjMatrix implements Graph, Cloneable{

    private int V; // 顶点数
    private int E; // 边数

    private int[][] adj;

    public AdjMatrix(String filename){
        File file = new File(filename);
        try(Scanner scanner = new Scanner(file)){

            V = scanner.nextInt();
            if(V < 0){
                throw new IllegalArgumentException("V must be non-negative");
            }

            adj = new int[V][V];

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

                adj[a][b] = adj[b][a] = 1;
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
        return adj(v).size();
    }

    @Override
    public void removeEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v][w] = adj[w][v] = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("V = %d, E = %d\n", V, E));
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
