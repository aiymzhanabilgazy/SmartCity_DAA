package graph.scc;

import graph.model.Graph;
import graph.model.Edge;
import java.util.HashSet;
import java.util.Set;


public class CondensationGraph {
    public static Graph buildCondensation (Graph original,Kosaraju.Result sccResult){
        int compCount = sccResult.getComponents().size();
        Graph dag = new Graph(compCount, true);

        Set<String> addedEdges = new HashSet<>();

        for(int u = 0; u < original.getNodesCount(); u++){
            int compU = sccResult.getComponent() [u];

            for(Edge e : original.getAdjList().get(u)){
                int compV = sccResult.getComponent()[e.getTo()];
                if(compU != compV){
                    String key = compU + "-"+ compV;
                    if(!addedEdges.contains(key)){
                        dag.addEdge(compU,compV,e.getWeight());
                        addedEdges.add(key);
                    }
                }
            }
        }
        return dag;
    }
}
