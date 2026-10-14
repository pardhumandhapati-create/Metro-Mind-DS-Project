# MetroMind – Intelligent Public Transport Platform (CO4, CO5, CO6)

A Java console application designed to open directly as an Eclipse project. It retains the existing MetroMind route search, direct-route search, shortest direct route, route comparison, route listing, spelling correction, and the existing string/DP algorithms. It adds CO4, CO5 and CO6 educational demonstrations without any transport-staff or staff-shift code.

## Menu
1. Search Stop / Station
2. Find Direct Route
3. Find Best Route / Shortest Distance
4. Compare Two Routes
5. Display All Routes
6. About MetroMind
7. Network Flow Capacity / Max-Flow Min-Cut (CO4)
8. Passenger-Route Matching (CO4)
9. NP-hard Approximation, Randomized and Parallel Demos (CO5/CO6)
10. Exit

## CO4 — Network flow, matching and capacities
- Edmonds–Karp (Ford–Fulkerson using BFS) computes maximum flow on a sample station network.
- Dinic computes the same maximum flow using level graphs and blocking flows.
- The program calculates the residual source-side set and prints minimum-cut edges/capacity, then checks max-flow equals min-cut.
- A small bipartite matching example assigns travel groups to distinct suitable route options. This is an educational abstraction: normal passengers can share routes, so it is not a real ticket-allocation engine.
- Connection capacities constrain passenger flow in the network example.

## CO5 — Complexity and approximation
- Defines P, NP, NP-complete and NP-hard.
- Explains the reduction relationship 3-SAT → Vertex Cover → Independent Set.
- Runs a Vertex Cover 2-approximation by selecting both endpoints of each uncovered edge and prints the guarantee.
- The reduction chain is explained rather than a full SAT solver or a code implementation of each reduction transformation.

## CO6 — Randomized and parallel algorithms
- Las Vegas: randomized Quickselect; always returns the correct k-th order statistic, with runtime dependent on random pivots.
- Monte Carlo: estimates π from random samples in a square.
- Prefix sum and reduction: prints cumulative sums and a total.
- Work-span: prints illustrative work and span for a balanced reduction tree.
- Prefix sum/reduce calculations use sequential loops to demonstrate the primitive and theoretical analysis; they are not presented as actual parallel speedups or multithreaded execution.

## Run in Eclipse
1. Extract this ZIP file.
2. In Eclipse choose **File → Import → General → Existing Projects into Workspace**.
3. Select the extracted `MetroMind_Complete_CO4_CO5_CO6` folder and click **Finish**.
4. Open `src/dsaproject/Main.java`, right-click it, then choose **Run As → Java Application**.

Requires Java 17 or newer because the existing project uses modern switch syntax.

## Run from a terminal
From the extracted project folder:

```bash
javac -d out $(find src -name "*.java")
java -cp out dsaproject.Main
```

## Existing concepts retained
KMP, Rabin–Karp, suffix array, LCP/Kasai, Levenshtein distance for spelling correction, and bitmask dynamic programming remain in the project.
