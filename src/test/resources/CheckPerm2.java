import java.util.*;

public class Solution {
    /**
     * @param str1 first string to be checked for permutation match
     * @param str2 second string to be checked for permutation match
     * @return  Indicate if one string is a permutation of another
     */
    public boolean permutation(String str1, String str2) {
        if (str1.length() != str2.length()) return false;

        return sort(str1).equals(sort(str2));
    }

    private String sort(String s) {
        char[] content = s.toCharArray();
        Arrays.sort(content);
        return new String(content);
    }
}