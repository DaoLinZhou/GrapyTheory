package bfs;

import graph.AdjSet;
import graph.Graph;

import java.util.*;

/**
 * @author Daolin
 * @date 2020/08/29
 */


public class Path {
    private int t;
    private Graph G;
    private int s;
    private int[] pre;

    /**
     *
     * @param G 图
     * @param s 起始点
     * @param t 终止点
     */
    public Path(Graph G, int s, int t) {
        G.validateVertex(s);
        G.validateVertex(t);

        this.G = G;
        this.s = s;
        this.t = t;
        pre = new int[G.V()];
        Arrays.fill(pre, -1);

        pre[s] = s;
        bfs(s);
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
                }
                if(w == t){
                    return;
                }
            }
        }
    }

    /**
     * @return 两点是否相连
     */
    public boolean isConnected(){
        return pre[t] != -1;
    }

    /**
     * @return 起始点到终止点的路径
     */
    public Iterable<Integer> path(){
        ArrayList<Integer> res = new ArrayList<>();
        if(!isConnected()){
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

    public static void main(String[] args) {
        Graph g = new AdjSet("g.txt");
        Path path = new Path(g, 0, 6);
        System.out.println(" 0 -> 6 : " + path.path());

        Path path2 = new Path(g, 0, 1);
        System.out.println(" 0 -> 1 : " + path2.path());

        Path path3 = new Path(g, 0, 5);
        System.out.println(" 0 -> 5 : " + path3.path());
    }

}
