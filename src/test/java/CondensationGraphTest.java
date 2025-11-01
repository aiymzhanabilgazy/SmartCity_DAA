
import graph.metrics.Metrics;
import graph.model.Graph;
import graph.scc.CondensationGraph;
import graph.scc.Kosaraju;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CondensationGraphTest {

    @Test
    public void testCondensationEdges() {
        Graph g = new Graph(4, true);

        g.addEdge(0, 1, 1);
        g.addEdge(1, 0, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 3, 1);

        Metrics metrics = new Metrics();
        Kosaraju.Result scc = new Kosaraju().findSCC(g, metrics);
        Graph dag = CondensationGraph.buildCondensation(g, scc);

        assertEquals(3, dag.getNodesCount());

        int edgeCount = dag.getAdjList().values().stream().mapToInt(java.util.List::size).sum();
        assertEquals(2, edgeCount);
    }
}
