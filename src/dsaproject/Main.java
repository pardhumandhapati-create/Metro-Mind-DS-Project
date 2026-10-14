package dsaproject;

import dsaproject.data.TransportData;
import dsaproject.model.Transport;
import dsaproject.services.MetroMindService;
import dsaproject.services.MetroMindService.RouteMatch;

import java.util.*;

public class Main {
    private static final Scanner SC = new Scanner(System.in);
    private static final List<Transport> ROUTES = TransportData.getRoutes();
    private static final MetroMindService SERVICE = new MetroMindService(ROUTES);

    public static void main(String[] args) {
        while (true) {
            printMenu();
            String choice = SC.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1" -> searchStopStation();
                case "2" -> findDirectRoute();
                case "3" -> findBestRoute();
                case "4" -> compareRoutes();
                case "5" -> displayRoutes();
                case "6" -> aboutMetroMind();
                case "7" -> AdvancedDemos.networkFlow();
                case "8" -> AdvancedDemos.passengerRouteMatching();
                case "9" -> {
                    AdvancedDemos.complexityAndApproximation();
                    AdvancedDemos.randomizedAlgorithms();
                    AdvancedDemos.parallelAlgorithms();
                }
                case "10" -> {
                    System.out.println("Thank you for using MetroMind.");
                    return;
                }
                default -> System.out.println("Invalid choice. Please select 1-10.");
            }

            System.out.println("\nPress Enter to continue...");
            SC.nextLine();
        }
    }

    private static void printMenu() {
        System.out.println("========== METROMIND ==========");
        System.out.println("Intelligent Public Transport Platform");
        System.out.println("================================");
        System.out.println();
        System.out.println("1. Search Stop / Station");
        System.out.println("2. Find Direct Route");
        System.out.println("3. Find Best Route / Shortest Distance");
        System.out.println("4. Compare Two Routes");
        System.out.println("5. Display All Routes");
        System.out.println("6. About MetroMind");
        System.out.println("7. Network Flow Capacity / Max-Flow Min-Cut");
        System.out.println("8. Passenger-Route Matching");
        System.out.println("9. NP-hard Approximation, Randomized and Parallel Demos");
        System.out.println("10. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void searchStopStation() {
        System.out.print("Enter stop/station name or starting letters: ");
        String input = SC.nextLine().trim();

        if (input.isEmpty()) {
            System.out.println("Please enter a station name.");
            return;
        }

        String corrected = SERVICE.correctStation(input);
        if (corrected != null && !corrected.equalsIgnoreCase(input)) {
            System.out.println("Auto-corrected: " + input + " -> " + corrected);
            input = corrected;
        }

        List<Transport> results = SERVICE.searchWithKMP(input);
        System.out.println();
        if (results.isEmpty()) {
            System.out.println("No matching station/route found.");
            return;
        }

        System.out.println("Matching routes:");
        for (Transport route : results) {
            System.out.println("- Route " + route.getRouteNo() + " | " + route.getTransportType());
            System.out.println("  " + route.getRouteName());
            System.out.println("  " + String.join(" -> ", route.getStops()));
        }
    }

    private static void findDirectRoute() {
        System.out.print("Enter source: ");
        String sourceInput = SC.nextLine().trim();
        System.out.print("Enter destination: ");
        String destinationInput = SC.nextLine().trim();

        if (sourceInput.isEmpty() || destinationInput.isEmpty()) {
            System.out.println("Source and destination are required.");
            return;
        }

        String source = autoCorrectAndShow(sourceInput);
        String destination = autoCorrectAndShow(destinationInput);
        if (source == null || destination == null) return;

        List<RouteMatch> results = SERVICE.findDirectRoutes(source, destination);

        System.out.println();
        if (results.isEmpty()) {
            System.out.println("No direct route found for the given source and destination.");
            return;
        }

        System.out.println("Direct routes from " + source + " to " + destination + ":");
        for (RouteMatch match : results) {
            printRouteMatch(match);
        }
    }

    private static void findBestRoute() {
        System.out.print("Enter source: ");
        String sourceInput = SC.nextLine().trim();
        System.out.print("Enter destination: ");
        String destinationInput = SC.nextLine().trim();

        if (sourceInput.isEmpty() || destinationInput.isEmpty()) {
            System.out.println("Source and destination are required.");
            return;
        }

        String source = autoCorrectAndShow(sourceInput);
        String destination = autoCorrectAndShow(destinationInput);
        if (source == null || destination == null) return;

        RouteMatch best = SERVICE.findBestDirectRoute(source, destination);
        System.out.println();
        if (best == null) {
            System.out.println("No route found for the given source and destination.");
            return;
        }

        System.out.println("Shortest direct route:");
        printRouteMatch(best);
    }

    private static String autoCorrectAndShow(String input) {
        String corrected = SERVICE.correctStation(input);
        if (corrected == null) {
            System.out.println("Could not identify station: " + input);
            System.out.println("Please enter a valid station name.");
            return null;
        }

        if (!corrected.equalsIgnoreCase(input)) {
            System.out.println("Auto-corrected: " + input + " -> " + corrected);
        }
        return corrected;
    }

    private static void printRouteMatch(RouteMatch match) {
        System.out.println("----------------------------------------");
        System.out.println("Route Number : " + match.route.getRouteNo());
        System.out.println("Transport    : " + match.route.getTransportType());
        System.out.println("Route        : " + match.routePart());
        System.out.println("Distance     : " + match.distance + " km");
    }

    private static void compareRoutes() {
        System.out.print("Enter first route number: ");
        String r1 = SC.nextLine().trim();
        System.out.print("Enter second route number: ");
        String r2 = SC.nextLine().trim();

        String common = SERVICE.longestCommonRouteText(r1, r2);
        if (common.isEmpty()) {
            System.out.println("One or both route numbers were not found, or no common route description exists.");
        } else {
            System.out.println("Longest common route description:");
            System.out.println(common);
        }
    }

    private static void displayRoutes() {
        System.out.println("================ ALL ROUTES ================");
        for (Transport route : ROUTES) {
            System.out.println("Route " + route.getRouteNo() + " | " + route.getTransportType());
            System.out.println(route.getRouteName());
            System.out.println(String.join(" -> ", route.getStops()));
            System.out.println("---------------------------------------------");
        }
    }

    private static void aboutMetroMind() {
        System.out.println("================ ABOUT METROMIND ================");
        System.out.println("MetroMind is an Intelligent Public Transport Platform");
        System.out.println("designed using Data Structures and Algorithms.");
        System.out.println();
        System.out.println("Features:");
        System.out.println("1. Search stops and stations.");
        System.out.println("2. Automatically correct small station spelling mistakes.");
        System.out.println("3. Find direct routes between two stations.");
        System.out.println("4. Find the shortest-distance direct route.");
        System.out.println("5. Compare two transport routes.");
        System.out.println("6. Display available transport routes.");
        System.out.println();
        System.out.println("Algorithms used internally:");
        System.out.println("KMP (Knuth-Morris-Pratt), Rabin-Karp, Suffix Array,");
        System.out.println("LCP/Kasai (Longest Common Prefix), Levenshtein Distance,");
        System.out.println("and Bitmask DP (Dynamic Programming).");
        System.out.println("CO4: Edmonds-Karp, Dinic, max-flow/min-cut, passenger-route matching.");
        System.out.println("CO5: P, NP, NP-complete, NP-hard and Vertex Cover 2-approximation.");
        System.out.println("CO6: Las Vegas, Monte Carlo, prefix sum, reduce and work-span analysis.");
    }
}
