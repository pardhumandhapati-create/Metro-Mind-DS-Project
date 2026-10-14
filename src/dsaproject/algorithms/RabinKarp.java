package dsaproject.algorithms;

public class RabinKarp {
    private static final long BASE = 256;
    private static final long MOD = 1_000_000_007L;
    private RabinKarp() {}

    public static boolean contains(String text, String pattern) {
        if (pattern == null || pattern.isEmpty()) return true;
        if (text == null || pattern.length() > text.length()) return false;
        String t = text.toLowerCase();
        String p = pattern.toLowerCase();
        int m = p.length();
        long high = 1, patternHash = 0, windowHash = 0;
        for (int i = 0; i < m - 1; i++) high = (high * BASE) % MOD;
        for (int i = 0; i < m; i++) {
            patternHash = (patternHash * BASE + p.charAt(i)) % MOD;
            windowHash = (windowHash * BASE + t.charAt(i)) % MOD;
        }
        for (int i = 0; i <= t.length() - m; i++) {
            if (patternHash == windowHash && t.regionMatches(i, p, 0, m)) return true;
            if (i < t.length() - m) {
                windowHash = (BASE * (windowHash - t.charAt(i) * high % MOD + MOD) + t.charAt(i + m)) % MOD;
            }
        }
        return false;
    }
}
