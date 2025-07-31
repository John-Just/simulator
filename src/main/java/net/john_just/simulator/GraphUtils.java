package net.john_just.simulator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphUtils {
    public static List<List<Terminal>> findConnectedClusters(List<Terminal> terminals) {
        List<List<Terminal>> clusters = new ArrayList<>();
        Set<Terminal> visited = new HashSet<>();

        for (Terminal terminal : terminals) {
            if (!visited.contains(terminal)) {
                List<Terminal> cluster = new ArrayList<>();
                dfs(terminal, cluster, visited);
                clusters.add(cluster);
            }
        }

        return clusters;
    }

    private static void dfs(Terminal t, List<Terminal> cluster, Set<Terminal> visited) {
        visited.add(t);
        cluster.add(t);

        for (Terminal neighbor : t.getDirectConnections()) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, cluster, visited);
            }
        }
    }
}
