public class Solution {
    /**
     * @param first first string
     * @param second second string
     * @return  Is first string one or zero edits away from the second?
     */
    public boolean oneEditAway(String first, String second) {
        if (first.equals(second))
            return true;

        if (isLengthDifferenceBiggerThanOne(first, second))
            return false;

        String shorter = first.length() < second.length() ? first : second;
        String longer = first.length() < second.length() ? second : first;

        return isOneEditAway(shorter, longer);
    }

    private static boolean isOneEditAway(String shorter, String longer) {
        int indexShorter = 0;
        int indexLonger = 0;
        boolean foundDifference = false;

        while (indexLonger < longer.length() && indexShorter < shorter.length()) {
            if (shorter.charAt(indexShorter) != longer.charAt(indexLonger)) {
                if (foundDifference) return false;
                foundDifference = true;

                if (shorter.length() == longer.length()) {
                    indexShorter++;
                }
            } else {
                indexShorter++;
            }
            indexLonger++;
        }

        return true;
    }

    private static boolean isLengthDifferenceBiggerThanOne(String first, String second) {
        return Math.abs(first.length() - second.length()) > 1;
    }
}