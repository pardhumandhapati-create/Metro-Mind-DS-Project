package dsaproject.algorithms;

public class LCPKasai {
    private LCPKasai() {}

    public static int[] build(String text, int[] suffixArray) {
        int n = text.length();
        int[] rank = new int[n];
        int[] lcp = new int[n];
        for (int i = 0; i < n; i++) rank[suffixArray[i]] = i;
        int k = 0;
        for (int i = 0; i < n; i++) {
            int r = rank[i];
            if (r == 0) continue;
            int j = suffixArray[r - 1];
            while (i + k < n && j + k < n && text.charAt(i + k) == text.charAt(j + k)) k++;
            lcp[r] = k;
            if (k > 0) k--;
        }
        return lcp;
    }

    public static String longestCommonSubstring(String a, String b) {
        String separator = "{";
        String combined = a + separator + b;
        int[] sa = SuffixArray.build(combined);
        int[] lcp = build(combined, sa);
        int best = 0, bestPos = 0;
        for (int i = 1; i < sa.length; i++) {
            boolean leftA = sa[i - 1] < a.length();
            boolean rightA = sa[i] < a.length();
            if (leftA != rightA && lcp[i] > best) {
                best = lcp[i];
                bestPos = sa[i];
            }
        }
        return best == 0 ? "" : combined.substring(bestPos, bestPos + best);
    }
}
