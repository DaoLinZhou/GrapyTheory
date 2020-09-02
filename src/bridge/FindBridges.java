package bridge;

import graph.AdjSet;
import graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Daolin
 * @date 2020/09/01
 * 寻找桥
 */
public class FindBridges {

    private Graph G;
    private int cnt;
    private int[] ord;
    private int[] low;
    private boolean[] visited;
    private ArrayList<Edge> res;

    public FindBridges(Graph G) {
        this.G = G;
        visited = new boolean[G.V()];
        ord = new int[G.V()];
        low = new int[G.V()];
        res = new ArrayList<>();

        Arrays.fill(visited, false);
        Arrays.fill(ord, -1);
        Arrays.fill(low, -1);

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

        for(int w : G.adj(v)) {
            if (!visited[w]) {
                dfs(w, v);
                low[v] = Math.min(low[v], low[w]);
                if(low[w] > ord[v]){    // 说明是桥, 无法通过其他路径返回
                    res.add(new Edge(v, w));
                }
            }
            if(w != parent) {      // 如果访问过, 且不是之前那条路, 则形成环, 此时这条边一定不是桥
                low[v] = Math.min(low[v], low[w]);
            }
        }
    }

//    private void dfs(int v, int parent){
//        visited[v] = true;
//        ord[v] = cnt;
//        low[v] = ord[v];
//        cnt++;
//
//        for(int w : G.adj(v)) {
//            if (!visited[w]) {
//                dfs(w, v);
//            }
//            if(w != parent) {
//                low[v] = Math.min(low[v], low[w]);
//                if(low[w] > ord[v]){
//                    res.add(new Edge(v, w));
//                }
//            }
//        }
//    }

    public ArrayList<Edge> result(){
        return res;
    }

    public static void main(String[] args){

        Graph g = new AdjSet("g.txt");
        FindBridges fb = new FindBridges(g);
        System.out.println("Bridges in g : " + fb.result());

        Graph g2 = new AdjSet("g2.txt");
        FindBridges fb2 = new FindBridges(g2);
        System.out.println("Bridges in g2 : " + fb2.result());

        Graph g3 = new AdjSet("g3.txt");
        FindBridges fb3 = new FindBridges(g3);
        System.out.println("Bridges in g3 : " + fb3.result());

        Graph tree = new AdjSet("tree.txt");
        FindBridges fb_tree = new FindBridges(tree);
        System.out.println("Bridges in tree : " + fb_tree.result());
    }

}
