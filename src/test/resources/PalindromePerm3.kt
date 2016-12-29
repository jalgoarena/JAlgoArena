class PalindromePerm3 {
    /**
     * @param phrase string to be checked if any permutation is palindrome
     * *
     * @return  Indicate if string is a permutation of palindrome
     */
    fun isPermutationOfPalindrome(phrase: String?): Boolean {
        if (phrase === "" || phrase == null) return false

        val bitVector = createBitVector(phrase)
        return bitVector == 0 || checkExactlyOneBitSet(bitVector)
    }

    /*
        Create a bit vecor for the string. For each letter with value i,
        toggle the ith bit.
     */
    private fun createBitVector(phrase: String): Int {
        var bitVector = 0
        phrase.toCharArray()
                .asSequence()
                .map { getCharNumber(it) }
                .forEach { bitVector = toggle(bitVector, it) }

        return bitVector
    }

    /*
        Toggle the ith bit in the integer.
     */
    private fun toggle(bitVector: Int, index: Int): Int {
        var bitVectorCopy = bitVector
        if (index < 0) return bitVectorCopy

        val mask = 1 shl index
        if (bitVectorCopy and mask == 0) {
            bitVectorCopy = bitVectorCopy or mask
        } else {
            bitVectorCopy = bitVectorCopy and mask.inv()
        }

        return bitVectorCopy
    }

    /*
        Check that exactly one bit is set by subtracting one
        from the integer and ANDing it with the original integer.
     */
    private fun checkExactlyOneBitSet(bitVector: Int): Boolean {
        return bitVector and bitVector - 1 == 0
    }

    private fun getCharNumber(c: Char?): Int {
        val a = Character.getNumericValue('a')
        val z = Character.getNumericValue('z')
        val `val` = Character.getNumericValue(c!!)

        if (a <= `val` && `val` <= z) {
            return `val` - a
        }
        return -1
    }
}
