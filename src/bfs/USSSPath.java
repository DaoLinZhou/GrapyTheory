package bfs;

import graph.AdjSet;
import graph.Graph;

import java.util.*;

/**
 * @author Daolin
 * @date 2020/08/29
 *
 * Unweighted Single Source Shortest Path
 *
 */
public class USSSPath {

    private Graph G;
    private int s;
    private int[] pre;
    private int[] dis;

    /**
     *
     * @param G 图
     * @param s 起始点
     */
    public USSSPath(Graph G, int s) {
        G.validateVertex(s);

        this.G = G;
        this.s = s;
        pre = new int[G.V()];
        dis = new int[G.V()];
        Arrays.fill(pre, -1);
        Arrays.fill(dis, -1);
        bfs(s);
    }

    private void bfs(int s){
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        pre[s] = s;
        dis[s] = 0;

        while (!queue.isEmpty()){
            int v = queue.remove();
            for(int w : G.adj(v)){
                if(pre[w] == -1){
                    queue.add(w);
                    pre[w] = v;
                    dis[w] = dis[v] + 1;
                }
            }
        }
    }

    /**
     * @return 两点是否相连
     */
    public boolean isConnected(int t){
        return pre[t] != -1;
    }

    /**
     * @return 起始点到终止点的路径
     */
    public Iterable<Integer> path(int t){
        ArrayList<Integer> res = new ArrayList<>();
        if(!isConnected(t)){
            return res;
        }
        int cur = t;
        while(cur != s){
            res.add(cur);
            cur = pre[cur];
        }
        res.add(s);
        // 此时是倒序的排列, 要 reverse 回正序
        Collections.reverse(res);
        return res;
    }

    public int dis(int t){
        G.validateVertex(t);
        return dis[t];
    }

    public static void main(String[] args) {
        Graph g = new AdjSet("g.txt");
        USSSPath usssPath = new USSSPath(g, 0);
        System.out.println("0 -> 6 : " + usssPath.path(6));
        System.out.println(usssPath.dis(6));
    }

}
