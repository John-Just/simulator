package net.john_just.simulator;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class VoltagePropagator {

    public static void propagateFrom(Terminal origin) {
        Set<Terminal> visited = new HashSet<>();
        Queue<Terminal> queue = new LinkedList<>();
        queue.add(origin);
        visited.add(origin);

        double sourceVoltage = origin.getVoltage();

        while (!queue.isEmpty()) {
            Terminal current = queue.poll();
            current.setVoltage(sourceVoltage); // распространяем напряжение

            for (Terminal neighbor : current.getConnected()) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }
    }
}

