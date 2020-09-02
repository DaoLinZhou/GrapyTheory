package bridge;

import graph.AdjSet;
import graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Daolin
 * @date 2020/09/01
 * 寻找割点
 */
public class FindCutPoints {

    private Graph G;
    private int cnt;
    private int[] ord;
    private int[] low;
    private boolean[] visited;
    private HashSet<Integer> res;

    public FindCutPoints(Graph G) {
        this.G = G;
        visited = new boolean[G.V()];

        ord = new int[G.V()];
        low = new int[G.V()];
        res = new HashSet<>();
        cnt = 0;

        for (int v = 0; v < G.V(); v++)
            if (!visited[v])
                dfs(v, v);
    }

    private void dfs(int v, int parent){

        visited[v] = true;
        ord[v] = cnt;
        low[v] = ord[v];
        cnt++;

        int child = 0;
        for(int w : G.adj(v)) {
            if (!visited[w]) {
                dfs(w, v);
                low[v] = Math.min(low[v], low[w]);

                if(low[w] >= ord[v] && v != parent)
                    res.add(v);
                child++;
            }
            else if(w != parent) {
                low[v] = Math.min(low[v], low[w]);
            }
        }
        if(v == parent && child > 1)
            res.add(v);
    }

    public Iterable<Integer> result(){
        return res;
    }

    public static void main(String[] args){

        Graph g = new AdjSet("g.txt");
        FindCutPoints fb = new FindCutPoints(g);
        System.out.println("Cut Points in g : " + fb.result());

        Graph g2 = new AdjSet("g2.txt");
        FindCutPoints fb2 = new FindCutPoints(g2);
        System.out.println("Cut Points in g2 : " + fb2.result());

        Graph g3 = new AdjSet("g3.txt");
        FindCutPoints fb3 = new FindCutPoints(g3);
        System.out.println("Cut Points in g3 : " + fb3.result());

        Graph tree = new AdjSet("tree.txt");
        FindCutPoints fb_tree = new FindCutPoints(tree);
        System.out.println("Cut Points in tree : " + fb_tree.result());

        Graph g5 = new AdjSet("g5.txt");
        FindCutPoints fb5 = new FindCutPoints(g5);
        System.out.println("Cut Points in g5 : " + fb5.result());

    }

}