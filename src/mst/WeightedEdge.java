package mst;

/**
 * @author Daolin
 * @date 2020/09/05
 */
public class WeightedEdge implements Comparable<WeightedEdge> {
    private int v, w;
    private double weight;

    public WeightedEdge(int v, int w, double weight){
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int getV() {
        return v;
    }

    public int getW() {
        return w;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString(){
        return String.format("%d-%d: %f", v, w, weight);
    }

    @Override
    public int compareTo(WeightedEdge another) {
        if(weight - another.weight > 0)
            return 1;
        else if(weight - another.weight == 0)
            return 0;
        else
            return -1;
    }
}
