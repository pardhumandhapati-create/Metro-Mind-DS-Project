package dsaproject.services;

import dsaproject.algorithms.BitmaskDP;
import dsaproject.algorithms.KMP;
import dsaproject.algorithms.LCPKasai;
import dsaproject.algorithms.Levenshtein;
import dsaproject.algorithms.RabinKarp;
import dsaproject.algorithms.SuffixArray;
import dsaproject.model.Transport;

import java.util.*;

public class MetroMindService {
    private final List<Transport> routes;

    public MetroMindService(List<Transport> routes) {
        this.routes = routes;
    }

    public List<Transport> searchStations(String keyword) {
        List<Transport> result = new ArrayList<>();
        for (Transport route : routes) {
            String stops = String.join(" ", route.getStops());
            if (KMP.contains(stops, keyword) || RabinKarp.contains(stops, keyword)) {
                result.add(route);
            }
        }
        return result;
    }

    public List<Transport> searchWithKMP(String keyword) {
        List<Transport> result = new ArrayList<>();
        for (Transport route : routes) {
            if (KMP.contains(route.getRouteName(), keyword) ||
                KMP.contains(String.join(" ", route.getStops()), keyword)) {
                result.add(route);
            }
        }
        return result;
    }

    public List<Transport> searchWithRabinKarp(String keyword) {
        List<Transport> result = new ArrayList<>();
        for (Transport route : routes) {
            String text = route.getRouteNo() + " " + route.getRouteName() + " " +
                          String.join(" ", route.getStops());
            if (RabinKarp.contains(text, keyword)) result.add(route);
        }
        return result;
    }

    public int[] buildSuffixArrayForRoutes() {
        return SuffixArray.build(buildRouteDocument());
    }

    public int[] searchRouteDocument(String keyword) {
        return SuffixArray.search(buildRouteDocument().toLowerCase(), keyword.toLowerCase());
    }

    public String longestCommonRouteText(String routeNo1, String routeNo2) {
        Transport a = findRoute(routeNo1);
        Transport b = findRoute(routeNo2);
        if (a == null || b == null) return "";
        return LCPKasai.longestCommonSubstring(
                String.join(" -> ", a.getStops()),
                String.join(" -> ", b.getStops())
        );
    }

    public Transport findRoute(String routeNo) {
        for (Transport r : routes) {
            if (r.getRouteNo().equalsIgnoreCase(routeNo)) return r;
        }
        return null;
    }

    public List<String> getAllStops() {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        for (Transport route : routes) set.addAll(route.getStops());
        return new ArrayList<>(set);
    }

    // Automatically converts a misspelled station name to the closest known station.
    public String correctStation(String input) {
        if (input == null || input.trim().isEmpty()) return null;

        String value = input.trim();
        List<String> stops = getAllStops();

        for (String stop : stops) {
            if (stop.equalsIgnoreCase(value)) return stop;
        }

        String bestStop = null;
        int bestDistance = Integer.MAX_VALUE;

        for (String stop : stops) {
            int distance = Levenshtein.distance(value, stop);
            if (distance < bestDistance) {
                bestDistance = distance;
                bestStop = stop;
            }
        }

        int allowedDistance = Math.max(2, value.replaceAll("\\s+", "").length() / 3);
        if (bestDistance <= allowedDistance) return bestStop;
        return null;
    }

    public List<String> spellingSuggestions(String input) {
        List<String> stops = getAllStops();
        stops.sort(Comparator.comparingInt(s -> Levenshtein.distance(input, s)));
        List<String> result = new ArrayList<>();
        int best = Levenshtein.distance(input, stops.get(0));
        for (String stop : stops) {
            if (Levenshtein.distance(input, stop) == best) result.add(stop);
            if (result.size() == 5) break;
        }
        return result;
    }

    // Finds routes containing both stations in the correct travel direction.
    public List<RouteMatch> findDirectRoutes(String source, String destination) {
        List<RouteMatch> result = new ArrayList<>();
        for (Transport route : routes) {
            List<String> stops = route.getStops();
            int sourceIndex = indexIgnoreCase(stops, source);
            int destinationIndex = indexIgnoreCase(stops, destination);
            if (sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex) {
                result.add(new RouteMatch(route, sourceIndex, destinationIndex, distanceOnRoute(route, sourceIndex, destinationIndex)));
            }
        }
        return result;
    }

    // Returns the shortest-distance direct route among matching routes.
    public RouteMatch findBestDirectRoute(String source, String destination) {
        List<RouteMatch> matches = findDirectRoutes(source, destination);
        RouteMatch best = null;
        for (RouteMatch match : matches) {
            if (best == null || match.distance < best.distance) best = match;
        }
        return best;
    }

    private int distanceOnRoute(Transport route, int sourceIndex, int destinationIndex) {
        int sum = 0;
        List<Integer> distances = route.getDistances();
        for (int i = sourceIndex; i < destinationIndex; i++) sum += distances.get(i);
        return sum;
    }

    public BitmaskDP.Result visitDestinations(List<String> selectedStops) {
        int n = selectedStops.size();
        int[][] matrix = new int[n][n];
        int INF = Integer.MAX_VALUE / 4;
        for (int[] row : matrix) Arrays.fill(row, INF);
        for (int i = 0; i < n; i++) matrix[i][i] = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int d = distanceBetweenStops(selectedStops.get(i), selectedStops.get(j));
                if (d < INF) matrix[i][j] = matrix[j][i] = d;
            }
        }
        return BitmaskDP.solve(matrix, 0);
    }

    private int distanceBetweenStops(String a, String b) {
        int best = Integer.MAX_VALUE / 4;
        for (Transport route : routes) {
            List<String> stops = route.getStops();
            int ia = indexIgnoreCase(stops, a), ib = indexIgnoreCase(stops, b);
            if (ia == -1 || ib == -1) continue;
            int lo = Math.min(ia, ib), hi = Math.max(ia, ib), sum = 0;
            List<Integer> d = route.getDistances();
            for (int k = lo; k < hi; k++) sum += d.get(k);
            best = Math.min(best, sum);
        }
        return best;
    }

    private int indexIgnoreCase(List<String> list, String value) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equalsIgnoreCase(value)) return i;
        }
        return -1;
    }

    public String buildRouteDocument() {
        StringBuilder sb = new StringBuilder();
        for (Transport route : routes) {
            sb.append("Route ").append(route.getRouteNo()).append(" ")
              .append(route.getRouteName()).append(" ")
              .append(String.join(" ", route.getStops())).append(" | ");
        }
        return sb.toString();
    }

    public static class RouteMatch {
        public final Transport route;
        public final int sourceIndex;
        public final int destinationIndex;
        public final int distance;

        public RouteMatch(Transport route, int sourceIndex, int destinationIndex, int distance) {
            this.route = route;
            this.sourceIndex = sourceIndex;
            this.destinationIndex = destinationIndex;
            this.distance = distance;
        }

        public String routePart() {
            return String.join(" -> ", route.getStops().subList(sourceIndex, destinationIndex + 1));
        }
    }
}
