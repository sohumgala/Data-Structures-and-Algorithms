import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * My PatternMatching Algorithm Implementations
 *
 * @author Sohum Gala
 * @version 1.0
 */
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm relies on the failure table (also
     * called failure function). Works better with small alphabets.
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {

        if (pattern == null || pattern.length() == 0) {
            throw new java.lang.IllegalArgumentException("pattern cannot be null or have length 0");
        }
        if (text == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("text or comparator cannot be null");
        }
        if (pattern.length() > text.length()) {
            return new LinkedList<>();
        }
        int[] f = buildFailureTable(pattern, comparator);
        List<Integer> occurrences = new LinkedList<>();
        int i = 0;
        int j = 0;
        while (i <= text.length() - pattern.length()) {
            while (j < pattern.length() && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j++;
            }
            if (j == 0) {
                i++;
            } else {
                if (j == pattern.length()) {
                    occurrences.add(i);
                }
                int next = f[j - 1];
                i = i + j - next;
                j = next;
            }
        }
        return occurrences;
    }


    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this to check if characters are equal
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("pattern or comparator cannot be null");
        }
        int[] f = new int[pattern.length()];
        int i = 0;
        int j = 1;
        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                f[j] = i + 1;
                i++;
                j++;
            } else if (i == 0) {
                f[j] = 0;
                j++;
            } else {
                i = f[i - 1];
            }
        }
        return f;
    }


    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new java.lang.IllegalArgumentException("pattern cannot be null or have length 0");
        }
        if (text == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("text or comparator cannot be null");
        }
        if (pattern.length() > text.length()) {
            return new LinkedList<>();
        }
        List<Integer> occurrences = new LinkedList<>();
        Map<Character, Integer> last = buildLastTable(pattern);
        int i = 0;
        while (i <= text.length() - pattern.length()) {
            int j = pattern.length() - 1;
            while (j >= 0 && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j--;
            }
            if (j == -1) {
                occurrences.add(i);
                i++;
            } else {
                int shift = last.getOrDefault(text.charAt(i + j), -1);
                if (shift < j) {
                    i = i + j - shift;
                } else {
                    i++;
                }
            }
        }

        return occurrences;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new java.lang.IllegalArgumentException("pattern cannot be null");
        }
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            map.put(pattern.charAt(i), i);
        }
        return map;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     */
    private static final int BASE = 113;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     * @param pattern    a string you're searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */

    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new java.lang.IllegalArgumentException("pattern cannot be null or have length 0");
        }
        if (text == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("text or comparator cannot be null");
        }
        if (pattern.length() > text.length()) {
            return new LinkedList<>();
        }
        List<Integer> occurrences = new LinkedList<>();
        int patternHash = 0;
        int maxExp = 1;
        for (int i = 0; i < pattern.length(); i++) {
            patternHash += pattern.charAt(pattern.length() - 1 - i) * maxExp;
            maxExp *= BASE;
        }
        int textHash = 0;
        int multiplier = 1;
        for (int i = 0; i < pattern.length(); i++) {
            textHash += text.charAt(pattern.length() - 1 - i) * multiplier;
            multiplier *= BASE;
        }
        maxExp /= BASE;
        int i = 0;
        while (i <= text.length() - pattern.length()) {
            if (patternHash == textHash) {
                int j = 0;
                while (j < pattern.length() && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                    j++;
                }
                if (j == pattern.length()) {
                    occurrences.add(i);
                }
            }
            i++;
            if (i <= text.length() - pattern.length()) {
                textHash = (textHash - text.charAt(i - 1) * maxExp) * BASE + text.charAt(i + pattern.length() - 1);
            }
        }
        return occurrences;
    }
}
