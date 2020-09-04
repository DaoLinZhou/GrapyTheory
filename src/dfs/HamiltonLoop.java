package dfs;

import graph.AdjSet;
import graph.Graph;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Daolin
 * @date 2020/09/02
 */
public class HamiltonLoop {
    private Graph G;
    private int[] pre;
    private int end;
    private int count;  // 记录已经访问的节点的数量

    public HamiltonLoop(Graph G) {
        if(G.V() > 32)
            throw new IllegalArgumentException("Number of vertex should not bigger than 32");
        this.G = G;
        int visited = 0;
        pre = new int[G.V()];
        pre[0] = 0;
        end = -1;
        count = 0;
        dfs(0, visited); // 如果有哈密尔顿回路, 则一定能通过顶点0
    }

//    private boolean dfs(int v){
//        visited[v] = true;
//        count++;
//        for(int w : G.adj(v)) {
//            if (!visited[w]) {
//                pre[w] = v;
//                if (dfs(w)) return true;
//            } else if (w == 0 && count == G.V()) {  // 如果所有节点都访问过, 同时回到原点
//                end = v;
//                return true;
//            }
//        }
//        visited[v] = false;
//        count--;
//        return false;
//    }

    private boolean dfs(int v, int visited){
        visited |= (1 << v);
        count++;
        if(count == G.V() && G.hasEdge(0, v)){ // 如果访问完所有节点, 同时终止点和0相连
            end = v;
            return true;
        }

        for(int w : G.adj(v)) {
            if ((visited & (1 << w)) == 0) {
                pre[w] = v;
                if (dfs(w,visited)) return true;
            }
        }
        count--;
        return false;
    }

    private ArrayList<Integer> result(){
        ArrayList<Integer> res = new ArrayList<>();
        if(end == -1){
            return res;
        }
        int cur = end;
        while (cur != 0){
            res.add(cur);
            cur = pre[cur];
        }
        res.add(0);

        Collections.reverse(res);
        return res;
    }

    public static void main(String[] args) {
        Graph g = new AdjSet("g.txt");
        HamiltonLoop hl = new HamiltonLoop(g);
        System.out.println(hl.result());

        Graph g2 = new AdjSet("g2.txt");
        HamiltonLoop hl2 = new HamiltonLoop(g2);
        System.out.println(hl2.result());
    }



}
