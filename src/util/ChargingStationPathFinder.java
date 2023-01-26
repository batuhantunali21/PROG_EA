package util;

import java.util.ArrayList;
import java.util.Stack;

/**
 * A class representing a path.
 *
 * @author Batuhan Tunali
 * @version 1.0
 */
public class ChargingStationPathFinder {
    private ArrayList<ChargingStation> path;
    private ArrayList<Integer> visited;
    private int destination;
    private boolean found;
    private int lastAddedIndex;

    public ChargingStationPathFinder(int destination) {
        this.path = new ArrayList<>();
        this.visited = new ArrayList<>();
        this.destination = destination;
        this.lastAddedIndex = -1;
    }

    /**
     * Finds a path of charging stations from the current charging station to
     * the destination.
     * A stack is used to keep track of the stations visited.
     * It starts from the current station and goes through its neighbors,
     * if the destination is reached it returns the path, otherwise it
     * backtracks if the path is a dead end and continues to search.
     *
     * @param current the current charging station
     *
     * @return a list of charging stations representing the path from the
     * current station to the destination
     */
    public ArrayList<ChargingStation> findPath(ChargingStation current) {
        Stack<ChargingStation> stack = new Stack<>();
        stack.push(current);
        while (!stack.isEmpty()) {
            ChargingStation station = stack.pop();
            if (station.get_postalCode() == destination) {
                path.add(station);
                found = true;
                return path;
            }
            if (!visited.contains(station.get_postalCode())) {
                visited.add(station.get_postalCode());
                if (lastAddedIndex == -1) {
                    lastAddedIndex = path.size();
                }
                path.add(station);
                boolean isDeadEnd = true;
                for (ChargingStation neighbor
                        : station.get_chargingStationInRange()) {
                    if (!visited.contains(neighbor.get_postalCode())) {
                        stack.push(neighbor);
                        isDeadEnd = false;
                    }
                }
                if (isDeadEnd) {
                    path.subList(lastAddedIndex, path.size()).clear();
                    lastAddedIndex = -1;
                }
            }
        }
        return path;
    }
}