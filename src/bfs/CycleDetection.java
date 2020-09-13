package bfs;

import graph.AdjSet;
import graph.Graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Daolin
 * @date 2020/08/29
 */
public class CycleDetection {
    private Graph G;
    private int[] pre;
    private boolean hasCycle = false;

    public CycleDetection(Graph G) {
        if(G.isDirected())
            throw new IllegalArgumentException("CycleDetection only works in undirected graph.");
        this.G = G;
        pre = new int[G.V()];
        Arrays.fill(pre, -1);
        for(int v = 0; v < G.V(); v++){
            if (hasCycle) return;
            if(pre[v] == -1){
                pre[v] = v;     // 每个联通分量的起始点的pre设为自身, 标记为已访问.
                bfs(v);
            }
        }
    }

    private void bfs(int s){
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);

        while (!queue.isEmpty()){
            int v = queue.remove();
            for(int w : G.adj(v)){
                if(pre[w] == -1){
                    queue.add(w);
                    pre[w] = v;
                } else if(pre[w] != v){
                    hasCycle = true;
                    return;
                }
            }
        }
    }

    public boolean hasCycle(){
        return this.hasCycle;
    }

    public static void main(String[] args) {
        Graph g = new AdjSet("g.txt");
        CycleDetection cycleDetection = new CycleDetection(g);
        System.out.println(cycleDetection.hasCycle);


        Graph g2 = new AdjSet("g4.txt");
        CycleDetection cycleDetection2 = new CycleDetection(g2);
        System.out.println(cycleDetection2.hasCycle);

    }
}
