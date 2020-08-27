import graph.AdjList;
import graph.AdjMatrix;
import graph.AdjSet;
import graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Daolin
 * @date 2020/08/26
 * 深度遍历一
 */
public class GraphDFS {

    private Graph G;
    private ArrayList<Integer> pre = new ArrayList<>();  // 前序遍历
    private ArrayList<Integer> post = new ArrayList<>(); // 后序遍历
    private boolean[] visited;

    public GraphDFS(Graph G) {
        this.G = G;
        visited = new boolean[G.V()];
        Arrays.fill(visited, false);
        for (int v = 0; v < G.V(); v++)
            if (!visited[v])
                dfs(v);
    }

    private void dfs(int v){
        visited[v] = true;
        pre.add(v);
        for(int w : G.adj(v))
            if(!visited[w])
                dfs(w);
        post.add(v);
    }

    public Iterable<Integer> pre(){
        return pre;
    }

    public Iterable<Integer> post(){
        return post;
    }

    public static void main(String[] args) {
        Graph g = new AdjSet("g.txt");
        GraphDFS graphDFS = new GraphDFS(g);
        System.out.println(graphDFS.pre());
        System.out.println(graphDFS.post());
    }

}
