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

    private TreeMap<Integer, Double>[] adj;

    public WeightedGraph(String filename){
        File file = new File(filename);
        try(Scanner scanner = new Scanner(file)){

            V = scanner.nextInt();
            if(V < 0){
                throw new IllegalArgumentException("V must be non-negative");
            }

            adj = new TreeMap[V];
            for(int i = 0; i < V; i++)
                adj[i] = new TreeMap<Integer, Double>();

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
                adj[b].put(a, weight);
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
        return adj[v].containsKey(w);
    }

    public Iterable<Integer> adj(int v){
        validateVertex(v);
        return adj[v].keySet();
    }

    public int degree(int v){
        validateVertex(v);
        return adj[v].size();
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
        adj[v].remove(w);
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("V = %d, E = %d\n", V, E));
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
        WeightedGraph g = new WeightedGraph("g.txt");
        System.out.print(g.toString());
    }


}
