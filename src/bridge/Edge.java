package bridge;

/**
 * @author Daolin
 * @date 2020/09/01
 */
public class Edge {

    private int v, w;

    public Edge(int v, int w){
        this.v = v;
        this.w = w;
    }

    @Override
    public String toString(){
        return String.format("%d-%d", v, w);
    }
}

