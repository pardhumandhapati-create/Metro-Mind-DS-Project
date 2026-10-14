package dsaproject.algorithms;

import java.util.Arrays;

public class BitmaskDP {
    public static class Result {
        public final int cost;
        public final int[] order;
        public Result(int cost, int[] order) { this.cost = cost; this.order = order; }
    }

    private BitmaskDP() {}

    // Finds the minimum-cost order for visiting all selected destinations.
    public static Result solve(int[][] distance, int start) {
        int n = distance.length;
        if (n == 0 || n > 20) throw new IllegalArgumentException("Bitmask DP supports 1 to 20 destinations.");
        int states = 1 << n;
        int[][] dp = new int[states][n];
        int[][] parent = new int[states][n];
        for (int[] row : dp) Arrays.fill(row, Integer.MAX_VALUE / 4);
        for (int[] row : parent) Arrays.fill(row, -1);
        dp[1 << start][start] = 0;
        for (int mask = 0; mask < states; mask++) {
            for (int last = 0; last < n; last++) {
                if ((mask & (1 << last)) == 0 || dp[mask][last] >= Integer.MAX_VALUE / 4) continue;
                for (int next = 0; next < n; next++) {
                    if ((mask & (1 << next)) != 0 || distance[last][next] >= Integer.MAX_VALUE / 4) continue;
                    int newMask = mask | (1 << next);
                    int newCost = dp[mask][last] + distance[last][next];
                    if (newCost < dp[newMask][next]) {
                        dp[newMask][next] = newCost;
                        parent[newMask][next] = last;
                    }
                }
            }
        }
        int full = states - 1, best = Integer.MAX_VALUE / 4, last = -1;
        for (int i = 0; i < n; i++) if (dp[full][i] < best) { best = dp[full][i]; last = i; }
        if (last == -1) return new Result(-1, new int[0]);
        int[] order = new int[n];
        int mask = full;
        for (int i = n - 1; i >= 0; i--) {
            order[i] = last;
            int previous = parent[mask][last];
            mask ^= 1 << last;
            last = previous;
            if (i == 0) break;
        }
        return new Result(best, order);
    }
}
