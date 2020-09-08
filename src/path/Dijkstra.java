package path;

import graph.WeightedGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * @author Daolin
 * @date 2020/09/06
 * O(ElogE)
 */
public class Dijkstra {

    private WeightedGraph G;
    private int s;
    private double[] dis;
    private int[] pre;
    private boolean[] visited;

    private class Node implements Comparable<Node>{
        public int v;
        public double dis;
        public Node(int v, double dis){
            this.v = v;
            this.dis = dis;
        }

        @Override
        public int compareTo(Node another) {
            if(dis - another.dis > 0)
                return 1;
            else if(dis - another.dis == 0)
                return 0;
            else
                return -1;
        }
    }

    public Dijkstra(WeightedGraph G, int s){

        this.G = G;

        G.validateVertex(s);
        this.s = s;

        dis = new double[G.V()];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[s] = 0;

        visited = new boolean[G.V()];
        pre = new int[G.V()];
        // 不能再此处把visited[s]设为true, 因为dis中只有s是0, 第一次必须要找到s
        pre[s] = s;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(s,0));
        while(!pq.isEmpty()){
            int cur = pq.poll().v;
            if(visited[cur]) continue;

            visited[cur] = true;    // 确定最短路径已经求出来了
            for(int w: G.adj(cur)){ // 遍历邻边更新最短距离
                if(!visited[w]){
                    dis[w] = Math.min(dis[cur] + G.getWeight(cur, w), dis[w]);
                    pq.add(new Node(w, dis[w]));    // 同一个顶点可能有多份Node. 不过没关系, 最先取出来的dis一定是最小的
                    pre[w] = cur;
                }
            }
        }
    }

    public boolean isConnectedTo(int v){
        G.validateVertex(v);
        return visited[v];
    }

    public double disTo(int v){
        G.validateVertex(v);
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
        Dijkstra dij = new Dijkstra(g, 0);
        for(int v = 0; v < g.V(); v++){
            System.out.print(dij.disTo(v) + " ");
        }
        System.out.println();
        System.out.println(dij.path(3));
    }
}
