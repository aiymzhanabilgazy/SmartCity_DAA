package graph.metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

public class CsvExporter implements AutoCloseable {
    private final PrintWriter writer;

    public CsvExporter(String filename) throws IOException {
        writer = new PrintWriter(new FileWriter(filename));
        writer.println("file,scc_time_ns,dfs_visits,dfs_edges,topo_time_ns,pushes,pops,sp_time_ns,relaxations");
    }
    public void record(String fileName, Metrics scc, Metrics topo, Metrics sp) {
        writer.printf(Locale.US,
                "%s,%dns,%d,%d,%dns,%d,%d,%dns,%d%n",
                fileName,
                scc.getElapsedTimeNs(), scc.dfsVisits, scc.dfsEdges,
                topo.getElapsedTimeNs(), topo.pushes, topo.pops,
                sp.getElapsedTimeNs(), sp.relaxations);
    }
    @Override
    public void close() {
        writer.close();
    }
}
