public class Solution {
    /**
     * @param phrase string to be checked if any permutation is palindrome
     * @return  Indicate if string is a permutation of palindrome
     */
    public boolean isPermutationOfPalindrome(String phrase) {
        if (phrase == "" || phrase == null) return false;

        int bitVector = createBitVector(phrase);
        return bitVector == 0 || checkExactlyOneBitSet(bitVector);
    }

    /*
        Create a bit vecor for the string. For each letter with value i,
        toggle the ith bit.
     */
    private int createBitVector(String phrase) {
        int bitVector = 0;
        for (char c : phrase.toCharArray()) {
            int x = getCharNumber(c);
            bitVector = toggle(bitVector, x);
        }

        return bitVector;
    }

    /*
        Toggle the ith bit in the integer.
     */
    private int toggle(int bitVector, int index) {
        if (index < 0) return bitVector;

        int mask = 1 << index;
        if ((bitVector & mask) == 0) {
            bitVector |= mask;
        } else {
            bitVector &= ~mask;
        }

        return bitVector;
    }

    /*
        Check that exactly one bit is set by subtracting one
        from the integer and ANDing it with the original integer.
     */
    private boolean checkExactlyOneBitSet(int bitVector) {
        return (bitVector & (bitVector - 1)) == 0;
    }

    private int getCharNumber(Character c) {
        int a = Character.getNumericValue('a');
        int z = Character.getNumericValue('z');
        int val = Character.getNumericValue(c);

        if (a <= val && val <= z) {
            return val - a;
        }
        return -1;
    }
}
