package graph.reader;
import graph.model.Graph;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.google.gson.Gson;

public class GraphReader {

    public static class GraphWithSource {
        public final Graph graph;
        public final int source;

        public GraphWithSource(Graph graph, int source) {
            this.graph = graph;
            this.source = source;
        }
    }
    public static class GraphData {
        public boolean directed;
        public int n;
        public List<EdgeData> edges;
        public int source;
        public String weight_model;
        public Metadata metadata;
    }
    public static class EdgeData {
        public int u;
        public int v;
        public int w;
    }
    public static class Metadata {
        public String density;
        public String structure;
        public boolean multi_scc;
    }
    public static GraphWithSource readGraph(String filePath) throws IOException {
        Gson gson = new Gson ();
        try(Reader reader = new FileReader(filePath)) {
            GraphData data = gson.fromJson(reader,GraphData.class);
            Graph graph = new Graph(data.n, data.directed);

            for (EdgeData edge : data.edges) {
                graph.addEdge(edge.u, edge.v, edge.w);
            }
            return new GraphWithSource(graph, data.source);
        }
    }
    public static List<GraphWithSource> readAllGraphs(String folderPath) throws IOException{
        File folder = new File(folderPath);
        if(!folder.exists() || !folder.isDirectory()){
            throw new IllegalArgumentException("Folder does not exist or is not a folder");
        }
        List<GraphWithSource> graphs = new ArrayList<>();
        for(File file : Objects.requireNonNull(folder.listFiles())) {
            if(file.getName().endsWith(".json")) {
                graphs.add(readGraph(file.getAbsolutePath()));
            }
        }
        return graphs;
    }
}

