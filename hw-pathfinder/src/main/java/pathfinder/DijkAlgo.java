package pathfinder;

import graph.CreateGraph;
import pathfinder.datastructures.Path;

import java.util.*;

// This does not represent an ADT
public class DijkAlgo {

    /**
     *
     * @param start starting node
     * @param dest ending node
     * @param graph graph with nodes and edges
     * @return a path of the least cost
     */
    public static <N> Path<N> minCostPath(N start, N dest, CreateGraph<N, Double> graph) {

        Comparator<Path<N>> comparator = new Comparator<Path<N>>() {
            @Override
            public int compare(Path<N> o1, Path<N> o2) {
                if (o1.getCost() > o2.getCost()) {
                    return 1;
                } else if (o1.getCost() < o2.getCost()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };

        // priority queue of paths
        Queue<Path<N>> active = new PriorityQueue<>(comparator); // implements comparator
        // Set of finished nodes
        Set<N> finished = new HashSet<N>();
        // adds path from a starting point to itself to the queue
        active.add(new Path<N>(start));

        while (!active.isEmpty()) {
            Path<N> minPath = active.remove();
            N minDest = minPath.getEnd();
            if (minDest.equals(dest)) {
                return minPath;
            }
            if (finished.contains(minDest)) {
                continue;
            }
            for (CreateGraph.Edge<N, Double> edge : graph.edgesOut(minDest)) {
                if (!finished.contains(edge.getChild())) {
                    Path<N> newPath = minPath.extend(edge.getChild(), edge.getLabel());
                    active.add(newPath);
                }
            }
            finished.add(minDest);
        }
     return null;
    }


}
