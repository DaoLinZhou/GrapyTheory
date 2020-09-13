package dfs;

import graph.AdjSet;
import graph.Graph;

import java.util.Arrays;

/**
 * @author Daolin
 * @date 2020/08/28
 */
public class DirectedCycleDetection {

    private Graph G;
    private int[] pre;
    private boolean[] onPath;
    private boolean hasCycle = false;

    public DirectedCycleDetection(Graph G) {
        if(!G.isDirected())
            throw new IllegalArgumentException("DirectedCycleDetection only works in directed graph.");
        this.G = G;
        pre = new int[G.V()];
        onPath = new boolean[G.V()];
        Arrays.fill(pre, -1);
        for(int v = 0; v < G.V(); v++){
            if (hasCycle) return;
            if(pre[v] == -1){
                pre[v] = v;     // 每个联通分量的起始点的pre设为自身, 标记为已访问.
                dfs(v);
            }
        }
    }

    private void dfs(int v){
        onPath[v] = true;
        // 访问v的所有没被访问过的相邻顶点
        for(int w : G.adj(v)) {
            if (hasCycle) return;
            if (pre[w] == -1) {
                pre[w] = v; // 由于从 v 到 w, 所以把pre[w] 记录为v
                dfs(w);
            } else if(onPath[w]){ // 如果w在路径上
                hasCycle = true;
                return;
            }
        }
        onPath[v] = false;
    }

    public boolean hasCycle(){
        return this.hasCycle;
    }

    public static void main(String[] args) {
//        Graph g = new AdjSet("g.txt");
//        DirectedCycleDetection cycleDetection = new DirectedCycleDetection(g);
//        System.out.println(cycleDetection.hasCycle);


        Graph g2 = new AdjSet("g4.txt", true);
        DirectedCycleDetection cycleDetection2 = new DirectedCycleDetection(g2);
        System.out.println(cycleDetection2.hasCycle);

    }

}
