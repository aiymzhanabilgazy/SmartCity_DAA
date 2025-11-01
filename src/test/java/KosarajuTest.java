
import graph.model.Graph;
import graph.scc.Kosaraju;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KosarajuTest {

    @Test
    public void testEmptyGraph() {
        Graph g = new Graph(0, true);
        Kosaraju.Result res = new Kosaraju().findSCC(g);
        assertEquals(0, res.getComponents().size());
    }

    @Test
    public void testSingleNodeGraph() {
        Graph g = new Graph(1, true);
        Kosaraju.Result res = new Kosaraju().findSCC(g);
        assertEquals(1, res.getComponents().size());
        assertEquals(List.of(0), res.getComponents().get(0));
    }

    @Test
    public void testSimpleCycle() {
        Graph g = new Graph(3, true);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 0, 1);
        Kosaraju.Result res = new Kosaraju().findSCC(g);
        assertEquals(1, res.getComponents().size());
        assertTrue(res.getComponents().get(0).containsAll(List.of(0, 1, 2)));
    }

    @Test
    public void testTwoSCCs() {
        Graph g = new Graph(4, true);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 0, 1);
        g.addEdge(2, 3, 1);
        Kosaraju.Result res = new Kosaraju().findSCC(g);
        assertEquals(3, res.getComponents().size());
        boolean hasPair = res.getComponents().stream().anyMatch(c -> c.containsAll(List.of(0, 1)));
        assertTrue(hasPair);
    }
}

