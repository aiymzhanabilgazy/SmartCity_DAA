package graph.dagsp;

import graph.metrics.Metrics;
import graph.model.Edge;
import graph.model.Graph;
import graph.topo.DfsTopoSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DagShortestPaths {

    public static class Result {
        public final double[] distance;
        public final int[] parent;
        public final int source;

        public Result(double[] distance, int[] parent, int source) {
            this.distance = distance;
            this.parent = parent;
            this.source = source;
        }
        public List<Integer> reconstructPath(int target){
            List<Integer> path = new ArrayList<>();
            for(int v = target; v != -1; v = parent[v]){
                path.add(v);
            }
            Collections.reverse(path);
            return path;
        }
    }
    public static Result shortestPathDAG(Graph dag, int source,Metrics metrics){
        metrics.start();
        int n = dag.getNodesCount();
        double[] distance = new double[n];
        int[] parent = new int[n];
        Arrays.fill(distance, Double.POSITIVE_INFINITY);
        Arrays.fill(parent, -1);
        distance[source] = 0;

        DfsTopoSort top = new DfsTopoSort();
        List<Integer> order = top.topoSort(dag,new Metrics());

        for(int u : order){
            if(distance[u] != Double.POSITIVE_INFINITY){
                for(Edge e : dag.getAdjList().get(u)){
                    metrics.relaxations++;
                    if(distance[e.getTo()] > distance[u]+e.getWeight()){
                        distance[e.getTo()] = distance[u]+e.getWeight();
                        parent[e.getTo()] = u;
                    }
                }
            }
        }
        metrics.stop();
        return new Result(distance, parent, source);
    }
    public static Result longestPathDAG(Graph dag, int source,Metrics metrics){
        metrics.start();
        int n = dag.getNodesCount();
        double[] distance = new double[n];
        int[] parent = new int[n];
        Arrays.fill(distance, Double.NEGATIVE_INFINITY);
        Arrays.fill(parent, -1);
        distance[source] = 0;

        DfsTopoSort top = new DfsTopoSort();
        List<Integer> order = top.topoSort(dag,new Metrics());

        for(int u : order){
            if(distance[u] != Double.NEGATIVE_INFINITY){
                for(Edge e : dag.getAdjList().get(u)){
                    metrics.relaxations++;
                    if(distance[e.getTo()] < distance[u]+e.getWeight()){
                        distance[e.getTo()] = distance[u]+e.getWeight();
                        parent[e.getTo()] = u;
                    }
                }
            }
        }
        metrics.stop();
        return new Result(distance,parent,source);
    }

}
