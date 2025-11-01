package graph.topo;

import graph.model.Graph;
import graph.model.Edge;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class DfsTopoSort {
    public List<Integer> topoSort(Graph graph){
        int n = graph.getNodesCount();
        boolean[] visited = new boolean[n];
        Deque<Integer> stack = new ArrayDeque<>();

        for(int i=0; i<n; i++){
            if(!visited[i]){
                dfs(graph,i,visited,stack);
            }
        }
        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }
    private void dfs(Graph graph, int node, boolean[] visited, Deque<Integer> stack){
        visited[node] = true;
        for(Edge e : graph.getAdjList().get(node)){
            if(!visited[e.getTo()]){
                dfs(graph,e.getTo(),visited,stack);
            }
        }
        stack.push(node);
    }
}
