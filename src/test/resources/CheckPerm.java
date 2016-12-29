public class Solution {
    /**
     * @param str1 first string to be checked for permutation match
     * @param str2 second string to be checked for permutation match
     * @return  Indicate if one string is a permutation of another
     */
    public boolean permutation(String str1, String str2) {
        if (str1.length() != str2.length()) return false;

        StringBuilder toCheck = new StringBuilder(str1);

        for (String str : str2.split("")) {
            int index = toCheck.indexOf(str);
            if (index == -1) {
                return false;
            } else {
                toCheck = toCheck.replace(index, index + 1, "");
            }
        }

        return true;
    }
}