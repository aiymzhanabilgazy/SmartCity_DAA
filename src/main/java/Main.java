import graph.metrics.CsvExporter;
import graph.model.Graph;
import graph.reader.GraphReader;
import graph.scc.CondensationGraph;
import graph.scc.Kosaraju;
import graph.topo.DfsTopoSort;
import graph.dagsp.DagShortestPaths;
import graph.metrics.Metrics;
import graph.metrics.CsvExporter;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws Exception {
        String folderPath = "src/main/resources/data";
        File folder = new File(folderPath);

        CsvExporter exporter = new CsvExporter("metrics_results.csv");

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Folder does not exist or is not a folder");
            return;
        }

        System.out.println("--- SSC + Condensation + Topological Order ---");

        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (!file.getName().endsWith(".json")) {
                continue;
            }
            System.out.println("\nProcessing file: " + file.getName());

            GraphReader.GraphWithSource loaded = GraphReader.readGraph(file.getAbsolutePath());
            Graph graph = loaded.graph;
            int source = loaded.source;
            Metrics sccMetrics = new Metrics();
            Metrics topoMetrics = new Metrics();
            Metrics spMetrics = new Metrics();

            Kosaraju kos = new Kosaraju();
            Kosaraju.Result sccRes = kos.findSCC(graph,sccMetrics);
            System.out.println("Total SSCs: " + sccRes.getComponents().size());
            int compIndex = 0;
            for (List<Integer> comp : sccRes.getComponents()) {
                System.out.println("Component " + compIndex + " (size = " + comp.size() + "): "+comp);
                compIndex++;
            }
            Graph dag = CondensationGraph.buildCondensation(graph, sccRes);
            System.out.println("Condensation DAG has : " + dag.getNodesCount() + "nodes");

            DfsTopoSort top = new DfsTopoSort();
            List<Integer> topOrder = top.topoSort(dag,topoMetrics);
            System.out.println("Topological order of SSC has : " + topOrder);

            System.out.println("Derived order of tasks(vertices):");
            for (int componentIndex : topOrder) {
                System.out.println("Component " + componentIndex + "->" + sccRes.getComponents().get(componentIndex));
            }

            System.out.println("--- Shortest and Longest Path from source" + source + " ---");

            DagShortestPaths.Result shortest = DagShortestPaths.shortestPathDAG(dag, source,spMetrics);
            DagShortestPaths.Result longest = DagShortestPaths.longestPathDAG(dag, source,new Metrics());

            System.out.println("\nShortest distances (edge-weight model):");
            for(int nodeIndex = 0; nodeIndex< dag.getNodesCount();nodeIndex++) {
                double dist = shortest.distance[nodeIndex];
                if(Double.isInfinite(dist)) {
                    System.out.printf(" to %d = no path%n", nodeIndex);
                }else{
                    System.out.printf(" to %d = %.2f%n", nodeIndex, dist);
                }
            }
            double maxDist = Double.NEGATIVE_INFINITY;
            int target = -1;
            for(int i=0; i<dag.getNodesCount(); i++){
                if(longest.distance[i] > maxDist){
                    maxDist = longest.distance[i];
                    target = i;
                }
            }
            System.out.println("\nCritical path (longest path via max-DP):");
            System.out.println(" Length = "+maxDist);
            System.out.println(" Path = "+ longest.reconstructPath(target));

            exporter.record(file.getName(), sccMetrics, topoMetrics, spMetrics);
            System.out.println("\n[SCC metrics] " + sccMetrics);
            System.out.println("[Topo metrics] " + topoMetrics);
            System.out.println("[SP metrics] " + spMetrics);
        }
        exporter.close();
        System.out.println("\nAll graphs processed successfully.Metrics exported to metrics_results.csv");
    }
}
