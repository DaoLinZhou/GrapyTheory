package path;

import bfs.CC;
import graph.AdjSet;
import graph.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author Daolin
 * @date 2020/09/04
 */
public class DirectedEulerPath {

    private Graph G;
    private int start;
    private int end;
    public DirectedEulerPath(Graph G){
        if(!G.isDirected())
            throw new IllegalArgumentException("DirectedEulerPath only works in directed graph.");
        this.G = G;
        start = 0;  // 如果所有点的度为偶数, 则从0开始寻路, 这个算法就变成求欧拉回路的算法了
        end = -1;
    }

    public boolean hasEulerLoop(){
//        CC cc = new CC(G);  // 求联通分量
//        if(cc.count() > 1){ // 只有无向联通图才有欧拉回路
//            return false;
//        }
        int count = 0;
        ArrayList<Integer> starts = new ArrayList<>();
        ArrayList<Integer> ends = new ArrayList<>();
        for(int v = 0; v < G.V(); v++){
            if(G.indegree(v) != G.outdegree(v)){
                count ++;
                if(G.indegree(v) + 1 == G.outdegree(v)){
                    starts.add(v);
                }else if(G.indegree(v) - 1 == G.outdegree(v)){
                    ends.add(v);
                }else{
                    return false;
                }
            }
            if(count > 2){
                return false;
            }
        }
        if(starts.size() == 0 && ends.size() == 0){ // 欧拉回路
            start = 0;
            end = 0;
        }else if(starts.size() == 1 && ends.size() == 1){   // 欧拉路径
            start = starts.get(0);
            end = ends.get(0);
        }else{
            return false;
        }

        return true;
    }

    public ArrayList<Integer> result(){
        ArrayList<Integer> res = new ArrayList<>();
        if(!hasEulerLoop()){
            return res;
        }
        Graph g = (Graph) G.clone();
        Deque<Integer> stack = new LinkedList<>(); // stack 在java中有坑
        int curv = start;
        stack.addFirst(curv);
        while(!stack.isEmpty()){
            if(g.outdegree(curv) != 0){
                // 只要degree != 0 肯定能通过这个方式找到环, 因为所有顶点的degree是偶数
                // 不可能出现进入一个点之后走出不来的情况
                stack.push(curv);
                int w = g.adj(curv).iterator().next();
                g.removeEdge(curv, w);
                curv = w;
            } else {
                res.add(curv);
                curv = stack.removeFirst();
            }
        }
        Collections.reverse(res);
        return res;
    }

    public static void main(String[] args) {
        Graph g2 = new AdjSet("ug2.txt", true);
        DirectedEulerPath ep2 = new DirectedEulerPath(g2);
        System.out.println(ep2.result());
    }

}