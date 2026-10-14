package dsaproject.algorithms;

public class KMP {
    private KMP() {}

    public static boolean contains(String text, String pattern) {
        if (pattern == null || pattern.isEmpty()) return true;
        if (text == null) return false;
        String t = text.toLowerCase();
        String p = pattern.toLowerCase();
        int[] lps = buildLPS(p);
        int i = 0, j = 0;
        while (i < t.length()) {
            if (t.charAt(i) == p.charAt(j)) {
                i++; j++;
                if (j == p.length()) return true;
            } else if (j > 0) {
                j = lps[j - 1];
            } else {
                i++;
            }
        }
        return false;
    }

    private static int[] buildLPS(String pattern) {
        int[] lps = new int[pattern.length()];
        int len = 0;
        for (int i = 1; i < pattern.length();) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                lps[i++] = ++len;
            } else if (len > 0) {
                len = lps[len - 1];
            } else {
                lps[i++] = 0;
            }
        }
        return lps;
    }
}
