import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.PriorityQueue;

/**
 * My GraphAlgorithms Implementation
 *
 * @author Sohum Gala
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getVertices().contains(start)) {
            throw new java.lang.IllegalArgumentException("inputs cannot be null and start must be part of the graph");
        }
        List<Vertex<T>> vertices = new LinkedList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        Queue<Vertex<T>> vertexQueue = new LinkedList<>();
        vertexQueue.add(start);
        visited.add(start);
        vertices.add(start);
        while (vertices.size() != graph.getVertices().size() && !vertexQueue.isEmpty()) {
            Vertex<T> curr = vertexQueue.poll();
            for (VertexDistance<T> destination : graph.getAdjList().get(curr)) {
                if (!visited.contains(destination.getVertex())) {
                    visited.add(destination.getVertex());
                    vertexQueue.add(destination.getVertex());
                    vertices.add(destination.getVertex());
                }
            }
        }
        return vertices;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null || !graph.getAdjList().containsKey(start)) {
            throw new java.lang.IllegalArgumentException("inputs cannot be null and start must be part of the graph");
        }
        List<Vertex<T>> vertices = new LinkedList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        dfsHelper(start, graph, vertices, visited);
        return vertices;
    }

    /**
     * private helper method to help with dfs recursion
     * @param curr current vertex being traversed
     * @param graph graph being traversed
     * @param vertices list of visited vertices to be returned
     * @param visited set of visited vertices
     * @param <T> the generic typing of the data
     */
    private static <T> void dfsHelper(Vertex<T> curr, Graph<T> graph, List<Vertex<T>> vertices,
                                      Set<Vertex<T>> visited) {
        visited.add(curr);
        vertices.add(curr);
        for (VertexDistance<T> destination : graph.getAdjList().get(curr)) {
            if (!visited.contains(destination.getVertex())) {
                dfsHelper(destination.getVertex(), graph, vertices, visited);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     * This is Dijkstra's Algorithm.
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null || !graph.getAdjList().containsKey(start)) {
            throw new java.lang.IllegalArgumentException("inputs cannot be null and start must be part of the graph");
        }
        Set<Vertex<T>> visited = new HashSet<>();
        Map<Vertex<T>, Integer> distanceMap = new HashMap<>();
        PriorityQueue<VertexDistance<T>> priorityQueue = new PriorityQueue<>();
        for (Vertex<T> vertex : graph.getVertices()) {
            distanceMap.put(vertex, Integer.MAX_VALUE);
        }
        distanceMap.put(start, 0);
        priorityQueue.add(new VertexDistance<>(start, 0));
        while (!priorityQueue.isEmpty() && visited.size() != graph.getVertices().size()) {
            VertexDistance<T> vd = priorityQueue.remove();
            if (!visited.contains(vd.getVertex())) {
                visited.add(vd.getVertex());
                for (VertexDistance<T> destination : graph.getAdjList().get(vd.getVertex())) {
                    if (!visited.contains(destination.getVertex())) {
                        int distance = vd.getDistance() + destination.getDistance();
                        if (distance < distanceMap.get(destination.getVertex())) {
                            distanceMap.put(destination.getVertex(), distance);
                            priorityQueue.add(new VertexDistance<>(destination.getVertex(), distance));
                        }
                    }
                }
            }
        }
        return distanceMap;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new java.lang.IllegalArgumentException("graph cannot be null");
        }
        HashSet<Edge<T>> mst = new HashSet<>();
        DisjointSet<Vertex<T>> disjointSet = new DisjointSet<>();
        PriorityQueue<Edge<T>> priorityQueue = new PriorityQueue<>(graph.getEdges());
        while (!priorityQueue.isEmpty() && mst.size() < 2 * (graph.getVertices().size() - 1)) {
            Edge<T> edge = priorityQueue.remove();
            if (!disjointSet.find(edge.getU()).equals(disjointSet.find(edge.getV()))) {
                mst.add(new Edge<>(edge.getU(), edge.getV(), edge.getWeight()));
                mst.add(new Edge<>(edge.getV(), edge.getU(), edge.getWeight()));
                disjointSet.union(edge.getU(), edge.getV());
            }
        }
        if (mst.size() != 2 * (graph.getVertices().size() - 1)) {
            return null;
        }
        return mst;
    }
}
