package graph.model;
import java.util.*;

public class Graph {
    private final int n;
    private final boolean directed;
    private final Map<Integer,List<Edge>> adjList = new HashMap<>();

    public Graph(int n, boolean directed) {
        this.n = n;
        this.directed = directed;
        for (int i = 0; i < n; i++) {
            adjList.put(i, new ArrayList<>());
        }
    }
    public void addEdge(int from,int to,int weight){
        adjList.get(from).add(new Edge(from,to,weight));
        if(!directed){
            adjList.get(to).add(new Edge(to,from,weight));
        }
    }
    public Map<Integer,List<Edge>> getAdjList() {
        return adjList;
    }
    public Graph getTranspose() {
        Graph transpose = new Graph(n, directed);
        for (int u : adjList.keySet()) {
            for (Edge e : adjList.get(u)) {
                transpose.addEdge(e.getTo(), e.getFrom(), e.getWeight());
            }
        }
        return transpose;
    }
    public int getNodesCount(){
        return n;
    }
}
