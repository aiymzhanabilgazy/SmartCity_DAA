package graph.metrics;

public class Metrics {
    public long dfsVisits = 0;
    public long dfsEdges = 0;
    public long pushes = 0;
    public long pops = 0;
    public long relaxations = 0;

    private long startTime = 0;
    private long endTime = 0;

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public long getElapsedTimeNs() {
        return endTime - startTime;
    }

    public void reset() {
        dfsVisits = dfsEdges = pushes = pops = relaxations = 0;
        startTime = endTime = 0;
    }
    @Override
    public String toString() {
        return String.format(
                "time=%dns, dfsVisits=%d, dfsEdges=%d, pushes=%d, pops=%d, relaxations=%d",
                getElapsedTimeNs(), dfsVisits, dfsEdges, pushes, pops, relaxations);
    }
}
