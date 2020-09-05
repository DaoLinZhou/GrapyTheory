package path;

import bfs.CC;
import graph.AdjSet;
import graph.Graph;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author Daolin
 * @date 2020/09/04
 */
public class EulerPath {

    private Graph G;
    private int start;
    public EulerPath(Graph G){
        this.G = G;
    }

    public boolean hasEulerLoop(){
        CC cc = new CC(G);
        if(cc.count() > 1){
            return false;
        }
        int count = 0;
        for(int v = 0; v < G.V(); v++){
            if((G.degree(v) & 1) == 1){
                count ++;
                start = v;
            }
            if(count > 2){
                return false;
            }
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
            if(g.degree(curv) != 0){
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
        return res;
    }

    public static void main(String[] args) {
        Graph g = new AdjSet("g.txt");
        EulerPath ep = new EulerPath(g);
        System.out.println(ep.result());

        Graph g2 = new AdjSet("g2.txt");
        EulerPath ep2 = new EulerPath(g2);
        System.out.println(ep2.result());
    }

}