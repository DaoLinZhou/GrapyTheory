package match;

import dfs.BipartitionDetection;
import graph.AdjSet;
import graph.Graph;

import java.util.Arrays;

public class HungarianDFS {

    private Graph G;
    private int maxMatching = 0;
    private int[] matching;
    private boolean[] visited;

    public HungarianDFS(Graph G){
        BipartitionDetection bd = new BipartitionDetection(G);
        if(!bd.isBipartiteGraph()){
            throw new IllegalArgumentException("BipartiteMatching only works on BipartiteGraph");
        }

        this.G = G;
        // 定义: color == 0代表左侧的点, color == 1代表右侧的点
        int[] colors = bd.colors();
        matching = new int[G.V()];
        Arrays.fill(matching, -1);
        visited = new boolean[G.V()];

        for(int v = 0; v < G.V(); v++)
            if(colors[v] == 0 && matching[v] == -1) {
                Arrays.fill(visited, false);
                if (dfs(v)) maxMatching++;
            }
    }

    private boolean dfs(int v){
        // v 一定是一个二分图中左边的节点
        // u 一定是一个二分图中右边的节点
        visited[v] = true;
        for(int u: G.adj(v)){
            if(!visited[u]){
                visited[u] = true;
                if(matching[u] == -1){
                    matching[u] = v;
                    matching[v] = u;
                    return true;
                } else if(dfs(matching[u])) {
                    // 这里为了逻辑清晰, 不和上面的if合并的
                    // 然而两个if做的其实是同一件事
                    // 可以合并为 if(matching[u] == -1 || dfs(matching[u]))
                    matching[u] = v;
                    matching[v] = u;
                    return true;
                }
            }
        }
        return false;
    }

    public int maxMatching(){
        return maxMatching;
    }

    public boolean isPerfectMatching(){
        return maxMatching * 2 == G.V();
    }

    public static void main(String[] args) {
        Graph g = new AdjSet("match_test.txt");
        HungarianDFS hungarian = new HungarianDFS(g);
        System.out.println(hungarian.maxMatching());
    }
}
