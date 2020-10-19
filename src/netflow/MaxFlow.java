package netflow;

import graph.WeightedGraph;

import java.util.*;

/**
 * @author Daolin
 * @date 2020/10/13
 */
public class MaxFlow {

    private WeightedGraph network;
    private int s, t;
    private int maxFlow = 0;

    // 残量图
    private WeightedGraph rG;

    public MaxFlow(WeightedGraph network, int s, int t){
        if(!network.isDirected())
            throw new IllegalArgumentException("MaxFlow only works in directed graph");
        if(network.V() < 2)
            throw new IllegalArgumentException("The network should has at least 2 vertex");
        network.validateVertex(s);
        network.validateVertex(t);
        if(s == t)
            throw new IllegalArgumentException("s and t should be different");

        this.network = network;
        this.s = s;
        this.t = t;
        this.rG = new WeightedGraph(network.V(), true);

        for(int v = 0; v < network.V(); v++){
            for(int w: network.adj(v)){
                double c = network.getWeight(v, w);
                rG.addEdge(v, w, c);
                rG.addEdge(w, v, 0);
            }
        }

        while (true){
            ArrayList<Integer> augPath = getAugmentingPath();
            if(augPath.size() == 0) break;

            // 获得当前路径最大流向, 即最小容量
            double f = Double.MAX_VALUE;
            for(int i = 1; i < augPath.size(); i++){
                int v = augPath.get(i-1);
                int w = augPath.get(i);
                f = Math.min(f, rG.getWeight(v, w));
            }

            maxFlow += f;

            // 更新残量图
            for(int i = 1; i < augPath.size(); i++){
                int v = augPath.get(i-1);
                int w = augPath.get(i);

                rG.setWeight(v, w, rG.getWeight(v, w) - f);
                rG.setWeight(w, v, rG.getWeight(w, v) + f);
            }
        }
    }

    public double result(){
        return maxFlow;
    }

    public double flow(int v, int w){
        if(!network.hasEdge(v, w))
            throw new IllegalArgumentException(String.format("No edge %d-%d", v, w));
        return rG.getWeight(w, v);  // 反向边可以抵消的流量数就是正向边流量数
    }

    private ArrayList<Integer> getAugmentingPath(){
        Queue<Integer> q = new LinkedList<>();
        int[] pre = new int[network.V()];
        Arrays.fill(pre, -1);

        pre[s] = s;
        q.add(s);

        while (!q.isEmpty()){
            int cur = q.remove();
            if(cur == t) break;
            for(int next: rG.adj(cur)){
                if(pre[next] == -1 && rG.getWeight(cur, next) > 0){
                    pre[next] = cur;
                    q.add(next);
                }
            }
        }
        ArrayList<Integer> res = new ArrayList<>();
        if(pre[t] == -1) return res;

        int cur = t;
        while(cur != s){
            res.add(cur);
            cur = pre[cur];
        }
        res.add(s);
        Collections.reverse(res);
        return res;
    }

    public static void main(String[] args) {
        WeightedGraph network = new WeightedGraph("network.txt", true);
        MaxFlow maxFlow = new MaxFlow(network, 0, 3);
        System.out.println(maxFlow.result());

        for(int v = 0; v < network.V(); v++){
            for(int w: network.adj(v)){
                System.out.println(String.format("%d-%d : %f / %f", v, w, maxFlow.flow(v, w), network.getWeight(v, w)));
            }
        }
    }

}
