import graph.model.Graph;
import graph.reader.GraphReader;
import graph.scc.CondensationGraph;
import graph.scc.Kosaraju;
import graph.topo.DfsTopoSort;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws Exception {
        String folderPath = "src/main/resources/data";
        File folder = new File(folderPath);

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

            Graph graph = GraphReader.readGraph(file.getAbsolutePath());

            Kosaraju kos = new Kosaraju();
            Kosaraju.Result sccRes = kos.findSCC(graph);
            System.out.println("Total SSCs: " + sccRes.getComponents().size());
            int i = 0;
            for (List<Integer> comp : sccRes.getComponents()) {
                System.out.println("Component " + i + " (size = " + comp.size() + "): "+comp);
                i++;
            }
            Graph dag = CondensationGraph.buildCondensation(graph, sccRes);
            System.out.println("Condensation DAG has : " + dag.getNodesCount() + "nodes");

            DfsTopoSort top = new DfsTopoSort();
            List<Integer> topOrder = top.topoSort(dag);
            System.out.println("Topological order of SSC has : " + topOrder);

            System.out.println("Derived order of tasks(vertices):");
            for (int componentIndex : topOrder) {
                System.out.println("Component " + componentIndex + "->" + sccRes.getComponents().get(componentIndex));
            }
        }
        System.out.println("\nAll graphs processed successfully");
    }
}
