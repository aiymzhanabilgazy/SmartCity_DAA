package graph.scc;

import graph.metrics.Metrics;
import graph.model.Edge;
import graph.model.Graph;
import java.util.*;

public class Kosaraju {

    public static class Result {
        public final int[] component;
        public final List<List<Integer>> components;

        public Result(int[] component, List<List<Integer>> components) {
            this.component = component;
            this.components = components;
        }

        public int[] getComponent() {
            return component;
        }

        public List<List<Integer>> getComponents() {
            return components;
        }
    }

    public Result findSCC(Graph graph,Metrics metrics) {
        metrics.start();
        int n = graph.getNodesCount();
        boolean[] visited = new boolean[n];
        Deque<Integer> stack = new ArrayDeque<>();

        for(int i = 0; i < n; i++) {
            if(!visited[i]) {
                dfs1(graph,i,visited,stack,metrics);
            }
        }
        Graph transpose = graph.getTranspose();

        Arrays.fill(visited, false);
        int[] component = new int[n];
        Arrays.fill(component, -1);
        List<List<Integer>> components = new ArrayList<>();
        int componentIndex = 0;

        while(!stack.isEmpty()) {
            int node = stack.pop();
            if(!visited[node]) {
                List<Integer> currentComp = new ArrayList<>();
                dfs2(transpose,node,visited,currentComp,metrics);
                for(int v : currentComp) {
                    component[v] = componentIndex;
                }
                components.add(currentComp);
                componentIndex++;
            }
        }
        metrics.stop();
        return new Result(component, components);
    }
    private void dfs1(Graph graph, int node, boolean[] visited, Deque<Integer> stack, Metrics metrics) {
        visited[node] = true;
        metrics.dfsVisits++;
        for(Edge e : graph.getAdjList().get(node)) {
            metrics.dfsEdges++;
            if (!visited[e.getTo()]) {
                dfs1(graph, e.getTo(), visited, stack,metrics);
            }
        }
        stack.push(node);
    }
    private void dfs2(Graph graph, int node, boolean[] visited,List<Integer> comp, Metrics metrics) {
        visited[node] = true;
        comp.add(node);
        metrics.dfsVisits++;
        for(Edge e : graph.getAdjList().get(node)) {
            metrics.dfsEdges++;
            if(!visited[e.getTo()]){
                dfs2(graph,e.getTo(),visited,comp,metrics);
            }
        }
    }
}
