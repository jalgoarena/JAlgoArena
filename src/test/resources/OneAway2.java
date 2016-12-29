public class Solution {
    /**
     * @param first first string
     * @param second second string
     * @return  Is first string one or zero edits away from the second?
     */
    public boolean oneEditAway(String first, String second) {
        if (first.equals(second)) return true;

        if (first.length() == second.length()) {
            return isOneEditReplace(first, second);
        }

        if (first.length() + 1 == second.length()) {
            return isOneEditInsert(first, second);
        }

        if (first.length() == second.length() + 1) {
            return isOneEditInsert(second, first);
        }

        return false;
    }

    private static boolean isOneEditInsert(String first, String second) {
        int index1 = 0;
        int index2 = 0;

        while (index2 < second.length() && index1 < first.length()) {
            if (first.charAt(index1) != second.charAt(index2)) {
                if (index1 != index2) {
                    return false;
                }
                index2++;
            } else {
                index1++;
                index2++;
            }
        }

        return true;
    }

    private static boolean isOneEditReplace(String first, String second) {
        boolean foundDifference = false;
        for (int i = 0; i < first.length(); i++) {
            if (first.charAt(i) != second.charAt(i)) {
                if (foundDifference) {
                    return false;
                }
                foundDifference = true;
            }
        }
        return true;
    }
}