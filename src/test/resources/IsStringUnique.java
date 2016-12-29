public class Solution {
    /**
     * @param str input string to be checked
     * @return  Indicate if string contains only unique chars
     */
    public boolean isUniqueChars(String str) {
        if (str == null) return true;

        char[] chars = str.toCharArray();

        for (int i = 0; i < chars.length - 1; i++) {

            char toCheck = chars[i];

            for (int j = i + 1; j < chars.length; j++) {
                if (toCheck == chars[j]) return false;
            }
        }

        return true;
    }
}