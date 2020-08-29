package bfs;

import graph.AdjSet;
import graph.Graph;

import java.util.*;

/**
 * @author Daolin
 * @date 2020/08/29
 */
public class SingleSourcePath {

    private Graph G;
    private int s;
    private int[] pre;

    public SingleSourcePath(Graph G, int s){
        this.G = G;
        this.s = s;
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
            }
        }
    }

    public boolean isConnectedTo(int t){
        G.validateVertex(t);
        return pre[t] != -1;
    }

    public Iterable<Integer> path(int t){
        List<Integer> res = new ArrayList<>();
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
        SingleSourcePath sspBFS = new SingleSourcePath(g, 0);
        System.out.println("0 -> 6 : " + sspBFS.path(6));

        SingleSourcePath sspBFS2 = new SingleSourcePath(g, 0);
        System.out.println("0 -> 5 : " + sspBFS2.path(5));
    }


}
