package path;

import graph.WeightedGraph;

import java.util.Arrays;

/**
 * @author Daolin
 * @date 2020/09/07
 */
public class Floyed {
    private WeightedGraph G;
    private double[][] dis;
    private boolean hasNegativeCycle = false;

    public Floyed(WeightedGraph G){
        this.G = G;

        dis = new double[G.V()][G.V()];
        for(int i = 0; i < G.V(); i++)
            Arrays.fill(dis[i], Double.MAX_VALUE);

        for(int v = 0; v < G.V(); v++){
            dis[v][v] = 0;
            for(int w: G.adj(v)){
                dis[v][w] = G.getWeight(v, w);
            }
        }

        for(int t = 0; t < G.V(); t++)
            for(int v = 0; v < G.V(); v++)
                for(int w = 0; w < G.V(); w++)
                    if(dis[v][t] != Double.MAX_VALUE && dis[t][w] != Double.MAX_VALUE
                            && dis[v][t] + dis[t][w] < dis[v][w])
                        dis[v][w] = dis[v][t] + dis[t][w];

        for(int v = 0; v < G.V(); v++){
            if(dis[v][v] < 0){
                hasNegativeCycle = true;
                break;
            }
        }
    }

    public boolean hasNegCycle(){
        return hasNegativeCycle;
    }

    public boolean isConnected(int v, int w){
        G.validateVertex(v);
        G.validateVertex(w);
        return dis[v][w] != Double.MAX_VALUE;
    }

    public double dis(int v, int w){
        G.validateVertex(v);
        G.validateVertex(w);
        if(hasNegativeCycle) throw new RuntimeException("exist negative cycle.");
        return dis[v][w];
    }

    public static void main(String[] args) {
        WeightedGraph g = new WeightedGraph("g.txt");
        Floyed floyed = new Floyed(g);
        if(!floyed.hasNegCycle()){
            for(int v = 0; v < g.V(); v++){
                for(int w = 0; w < g.V(); w++){
                    System.out.print(floyed.dis(v, w) + " ");
                }
                System.out.println();
            }
        } else {
            System.out.println("exist negative cycle");
        }
    }

}
