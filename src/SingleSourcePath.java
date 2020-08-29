import graph.AdjSet;
import graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Daolin
 * @date 2020/08/27
 * 单源路径 (起始点固定)
 */
public class SingleSourcePath {

    private Graph G;
    private int s;
    private int[] pre;

    public SingleSourcePath(Graph G, int s) {
        G.validateVertex(s);
        this.G = G;
        this.s = s;
        pre = new int[G.V()];
        Arrays.fill(pre, -1);
        pre[s] = s;
        dfs(s);
    }

    private void dfs(int v){
        // 访问v的所有没被访问过的相邻顶点
        for(int w : G.adj(v)) {
            if (pre[w] == -1) {
                pre[w] = v; // 由于从 v 到 w, 所以把pre[w] 记录为v
                dfs(w);
            }
        }
    }

    /**
     * @param t 终止点
     * @return 判断起始点是否和 t 相连
     */
    public boolean isConnectedTo(int t){
        G.validateVertex(t);
        return pre[t] != -1;
    }

    /**
     * @param t 终止点
     * @return 获取起始点到终止点的一条路径
     */
    public Iterable<Integer> path(int t){
        ArrayList<Integer> res = new ArrayList<>();
        if(!isConnectedTo(t)){
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
        SingleSourcePath singleSourcePath = new SingleSourcePath(g, 0);
        System.out.println(" 0 -> 6 : " + singleSourcePath.path(6));
        System.out.println(" 0 -> 5 : " + singleSourcePath.path(5));
    }
}
