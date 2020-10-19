package match;

import dfs.BipartitionDetection;
import graph.AdjSet;
import graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class HungarianBFS {

    private Graph G;
    private int maxMatching = 0;
    private int[] matching;

    public HungarianBFS(Graph G){
        BipartitionDetection bd = new BipartitionDetection(G);
        if(!bd.isBipartiteGraph()){
            throw new IllegalArgumentException("BipartiteMatching only works on BipartiteGraph");
        }

        this.G = G;
        // 定义: color == 0代表左侧的点, color == 1代表右侧的点
        int[] colors = bd.colors();
        matching = new int[G.V()];
        Arrays.fill(matching, -1);

        for(int v = 0; v < G.V(); v++)
            if(colors[v] == 0 && matching[v] == -1)
                if(bfs(v)) maxMatching++;
    }

    private boolean bfs(int v){
        Queue<Integer> q = new LinkedList<>();
        int[] pre = new int[G.V()];
        Arrays.fill(pre, -1);

        q.add(v);
        pre[v] = v;
        while(!q.isEmpty()){
            int cur = q.remove();
            for(int next: G.adj(cur)){
                if(pre[next] == -1){
                    if(matching[next] != -1){
                        // 如果next是已经匹配过的点. 访问自身和自身匹配的点
                        pre[next] = cur;
                        pre[matching[next]] = next;
                        q.add(matching[next]);
                    } else {
                        // 找到增广路径结尾点
                        pre[next] = cur;
                        ArrayList<Integer> augPath = getAugPath(pre, v, next);
                        for(int i = 0; i < augPath.size(); i+=2){
                            matching[augPath.get(i)] = augPath.get(i + 1);
                            matching[augPath.get(i + 1)] = augPath.get(i);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private ArrayList<Integer> getAugPath(int[] pre, int start, int end){
        ArrayList<Integer> res = new ArrayList<>();
        int cur = end;
        res.add(end);
        while (pre[cur] != start){
            res.add(pre[cur]);
            cur = pre[cur];
        }
        res.add(start);
        return res;
    }

    public int maxMatching(){
        return maxMatching;
    }

    public boolean isPerfectMatching(){
        return maxMatching * 2 == G.V();
    }

    public static void main(String[] args) {
        Graph g = new AdjSet("match_test.txt");
        HungarianBFS hungarianBFS = new HungarianBFS(g);
        System.out.println(hungarianBFS.maxMatching());
    }
}
