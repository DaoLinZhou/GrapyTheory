package dfs;

import graph.AdjSet;
import graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Daolin
 * @date 2020/08/28
 * 获得两个点之间的一条路径而不遍历所有节点
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
        dfs(s);
    }

    // 返回是否找到节点t(用于提前终止)
    private boolean dfs(int v){
        if(v == t) return true;
        // 访问v的所有没被访问过的相邻顶点
        for(int w : G.adj(v)) {
            if (pre[w] == -1) {
                pre[w] = v; // 由于从 v 到 w, 所以把pre[w] 记录为v
                if(dfs(w)) return true;
            }
        }
        return false;
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
