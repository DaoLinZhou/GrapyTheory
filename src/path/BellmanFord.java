package path;

import graph.WeightedGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Daolin
 * @date 2020/09/07
 */
public class BellmanFord {

    private WeightedGraph G;
    private int s;
    private double[] dis;
    private int[] pre;
    private boolean hasNegativeCycle = false;

    public BellmanFord(WeightedGraph G, int s){
        this.G = G;
        G.validateVertex(s);
        this.s = s;

        dis = new double[G.V()];
        Arrays.fill(dis, Double.MAX_VALUE);
        dis[s] = 0;

        pre = new int[G.V()];
        Arrays.fill(pre, -1);
        pre[s] = s;

        for(int pass = 1; pass < G.V(); pass++){
            // 对所有边进行松弛操作
            for(int v = 0; v < G.V(); v++)
                for(int w : G.adj(v))
                    if(dis[v] != Double.MAX_VALUE && dis[v] + G.getWeight(v, w) < dis[w]) {
                        dis[w] = dis[v] + G.getWeight(v, w);
                        pre[w] = v;
                    }
        }
        for(int v = 0; v < G.V(); v++)
            for(int w : G.adj(v))
                if(dis[v] != Double.MAX_VALUE && dis[v] + G.getWeight(v, w) < dis[w])
                    hasNegativeCycle = true;
    }

    public boolean hasNegCycle(){
        return hasNegativeCycle;
    }

    public boolean isConnectedTo(int v){
        G.validateVertex(v);
        return dis[v] != Double.MAX_VALUE;
    }

    public double disTo(int v){
        G.validateVertex(v);
        if(hasNegativeCycle) throw new RuntimeException("exist negative cycle.");
        return dis[v];
    }

    public Iterable<Integer> path(int v){
        G.validateVertex(v);
        ArrayList<Integer> res = new ArrayList<>();
        if(!isConnectedTo(v)){
            return res;
        }
        int cur = v;
        while(cur != s){
            res.add(cur);
            cur = pre[cur];
        }
        res.add(s);
        // 此时是倒序的排列, 要 reverse 回正序
        Collections.reverse(res);
        return res;
    }

    public static void main(String[] args) {
        WeightedGraph g = new WeightedGraph("g.txt");
        BellmanFord bf = new BellmanFord(g, 0);
        if(!bf.hasNegCycle()){
            for(int v = 0; v < g.V(); v++){
                System.out.print(bf.disTo(v) + " ");
            }
            System.out.println();
            System.out.println(bf.path(3));
        }else{
            System.out.println("exist negative cycle");
        }
    }




}
