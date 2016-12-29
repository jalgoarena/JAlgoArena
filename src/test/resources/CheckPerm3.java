import java.util.*;

public class Solution {
    /**
     * @param str1 first string to be checked for permutation match
     * @param str2 second string to be checked for permutation match
     * @return  Indicate if one string is a permutation of another
     */
    public boolean permutation(String str1, String str2) {
        if (str1.length() != str2.length()) return false;

        int[] letters = new int[128]; // Assumption: ASCII
        for (int i = 0; i < str1.length(); i++) {
            letters[str1.charAt(i)]++;
        }

        for (int i = 0; i < str2.length(); i++) {
            letters[str2.charAt(i)]--;

            if (letters[str2.charAt(i)] < 0) {
                return false;
            }
        }

        return true;
    }
}