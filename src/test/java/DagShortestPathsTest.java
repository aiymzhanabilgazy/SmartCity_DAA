
import graph.model.Graph;
import graph.dagsp.DagShortestPaths;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DagShortestPathsTest {
    @Test
    public void testSingleNodeNoEdges() {
        Graph g = new Graph(1, true);
        DagShortestPaths.Result shortest = DagShortestPaths.shortestPathDAG(g, 0);
        DagShortestPaths.Result longest = DagShortestPaths.longestPathDAG(g, 0);
        assertEquals(0.0, shortest.distance[0]);
        assertEquals(0.0, longest.distance[0]);
    }
    @Test
    public void testShortestPathSimpleDAG() {
        Graph g = new Graph(5, true);
        g.addEdge(0, 1, 2);
        g.addEdge(0, 2, 4);
        g.addEdge(1, 2, 1);
        g.addEdge(1, 3, 7);
        g.addEdge(2, 4, 3);

        DagShortestPaths.Result res = DagShortestPaths.shortestPathDAG(g, 0);
        assertEquals(0.0, res.distance[0]);
        assertEquals(2.0, res.distance[1]);
        assertEquals(3.0, res.distance[2]);
        assertEquals(9.0, res.distance[3]);
        assertEquals(6.0, res.distance[4]);
    }
    @Test
    public void testLongestPathSimpleDAG() {
        Graph g = new Graph(4, true);
        g.addEdge(0, 1, 2);
        g.addEdge(1, 2, 3);
        g.addEdge(0, 2, 1);
        g.addEdge(2, 3, 4);

        DagShortestPaths.Result res = DagShortestPaths.longestPathDAG(g, 0);

        double maxDist = -1;
        int target = -1;
        for (int i = 0; i < g.getNodesCount(); i++) {
            if (res.distance[i] > maxDist) {
                maxDist = res.distance[i];
                target = i;
            }
        }
        assertEquals(9.0, maxDist, 0.0001);
        assertEquals(java.util.List.of(0, 1, 2, 3), res.reconstructPath(target));
    }
    @Test
    public void testNoEdgesGraph() {
        Graph g = new Graph(3, true);
        DagShortestPaths.Result res = DagShortestPaths.shortestPathDAG(g, 0);
        assertEquals(Double.POSITIVE_INFINITY, res.distance[1]);
        assertEquals(Double.POSITIVE_INFINITY, res.distance[2]);
    }
}
