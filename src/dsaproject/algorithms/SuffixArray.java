package dsaproject.algorithms;

import java.util.Arrays;

public class SuffixArray {
    private SuffixArray() {}

    public static int[] build(String text) {
        int n = text.length();
        Integer[] suffixes = new Integer[n];
        for (int i = 0; i < n; i++) suffixes[i] = i;
        Arrays.sort(suffixes, (a, b) -> text.substring(a).compareTo(text.substring(b)));
        int[] result = new int[n];
        for (int i = 0; i < n; i++) result[i] = suffixes[i];
        return result;
    }

    public static int[] search(String text, String pattern) {
        int[] sa = build(text);
        int left = 0, right = sa.length - 1;
        int first = -1;
        while (left <= right) {
            int mid = (left + right) >>> 1;
            int cmp = text.substring(sa[mid]).compareTo(pattern);
            if (cmp >= 0) { first = mid; right = mid - 1; }
            else left = mid + 1;
        }
        if (first == -1 || !text.startsWith(pattern, sa[first])) return new int[0];
        int last = first;
        while (last + 1 < sa.length && text.startsWith(pattern, sa[last + 1])) last++;
        int[] positions = new int[last - first + 1];
        for (int i = 0; i < positions.length; i++) positions[i] = sa[first + i];
        Arrays.sort(positions);
        return positions;
    }
}
