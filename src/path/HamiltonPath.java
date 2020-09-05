package path;

import graph.AdjSet;
import graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Daolin
 * @date 2020/09/03
 * 哈密尔顿路径
 */
public class HamiltonPath {

    private Graph G;
    private boolean[] visited;
    private int[] pre;
    private int end;    // 结束点
    private int s;  // 起始点
    private int count;  // 记录已经访问的节点的数量

    public HamiltonPath(Graph G, int s) {
        this.G = G;
        this.s = s;
        visited = new boolean[G.V()];
        pre = new int[G.V()];
        Arrays.fill(visited, false);
        pre[0] = 0;
        end = -1;
        count = 0;
        dfs(s); // 如果有哈密尔顿回路, 则一定能通过顶点0
    }

    private boolean dfs(int v){
        visited[v] = true;
        count++;
        if(count == G.V()){
            end = v;
            return true;
        }

        for(int w : G.adj(v)) {
            if (!visited[w]) {
                pre[w] = v;
                if (dfs(w)) return true;
            }
        }
        visited[v] = false;
        count--;
        return false;
    }

    private ArrayList<Integer> result(){
        ArrayList<Integer> res = new ArrayList<>();
        if(end == -1){
            return res;
        }
        int cur = end;
        while (cur != s){
            res.add(cur);
            cur = pre[cur];
        }
        res.add(s);

        Collections.reverse(res);
        return res;
    }

    public static void main(String[] args) {
        Graph g = new AdjSet("g.txt");
        HamiltonPath hl = new HamiltonPath(g, 1);
        System.out.println(hl.result());
    }



}
