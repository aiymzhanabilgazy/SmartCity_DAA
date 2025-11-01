
import graph.model.Graph;
import graph.topo.DfsTopoSort;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DfsTopoSortTest {

    @Test
    public void testEmptyGraph() {
        Graph g = new Graph(0, true);
        DfsTopoSort topo = new DfsTopoSort();
        List<Integer> order = topo.topoSort(g);
        assertTrue(order.isEmpty());
    }

    @Test
    public void testSingleNode() {
        Graph g = new Graph(1, true);
        DfsTopoSort topo = new DfsTopoSort();
        List<Integer> order = topo.topoSort(g);
        assertEquals(List.of(0), order);
    }

    @Test
    public void testSimpleDAG() {
        Graph g = new Graph(4, true);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(0, 3, 1);

        DfsTopoSort topo = new DfsTopoSort();
        List<Integer> order = topo.topoSort(g);

        assertEquals(4, order.size());
        assertTrue(order.indexOf(0) < order.indexOf(1));
        assertTrue(order.indexOf(1) < order.indexOf(2));
    }
}
