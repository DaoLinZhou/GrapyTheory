package graph;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * @author Daolin
 * @date 2020/08/26
 */
public interface Graph {

    int V();
    int E();

    boolean hasEdge(int v, int w);

    Iterable<Integer> adj(int v);
    int degree(int v);

    void validateVertex(int v);

}
