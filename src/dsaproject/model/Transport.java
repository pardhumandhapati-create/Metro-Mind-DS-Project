package dsaproject.model;

import java.util.ArrayList;
import java.util.List;

public class Transport {
    private final String routeNo;
    private final String transportType;
    private final String routeName;
    private final List<String> stops;
    private final List<Integer> distances;

    public Transport(String routeNo, String transportType, String routeName,
                     List<String> stops, List<Integer> distances) {
        if (stops == null || stops.size() < 2) {
            throw new IllegalArgumentException("A route must have at least two stops.");
        }
        if (distances == null || distances.size() != stops.size() - 1) {
            throw new IllegalArgumentException("Distance count must equal stops - 1 for route " + routeNo);
        }
        this.routeNo = routeNo;
        this.transportType = transportType;
        this.routeName = routeName;
        this.stops = new ArrayList<>(stops);
        this.distances = new ArrayList<>(distances);
    }

    public String getRouteNo() { return routeNo; }
    public String getTransportType() { return transportType; }
    public String getRouteName() { return routeName; }
    public List<String> getStops() { return new ArrayList<>(stops); }
    public List<Integer> getDistances() { return new ArrayList<>(distances); }

    @Override
    public String toString() {
        return "Route " + routeNo + " | " + transportType + " | " + routeName +
               " | " + String.join(" -> ", stops);
    }
}
