package graph.topo;

import graph.metrics.Metrics;
import graph.model.Graph;
import graph.model.Edge;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class DfsTopoSort {
    public List<Integer> topoSort(Graph graph,Metrics metrics){
        metrics.start();
        int n = graph.getNodesCount();
        boolean[] visited = new boolean[n];
        Deque<Integer> stack = new ArrayDeque<>();

        for(int i=0; i<n; i++){
            if(!visited[i]){
                dfs(graph,i,visited,stack,metrics);
            }
        }
        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
            metrics.pops++;
        }
        metrics.stop();
        return result;
    }
    private void dfs(Graph graph, int node, boolean[] visited, Deque<Integer> stack, Metrics metrics){
        visited[node] = true;
        metrics.pushes++;
        for(Edge e : graph.getAdjList().get(node)){
            if(!visited[e.getTo()]){
                dfs(graph,e.getTo(),visited,stack,metrics);
            }
        }
        stack.push(node);
    }
}
