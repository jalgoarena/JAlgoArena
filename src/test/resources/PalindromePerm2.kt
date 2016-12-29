class PalindromePerm2 {
    /**
     * @param phrase string to be checked if any permutation is palindrome
     * *
     * @return  Indicate if string is a permutation of palindrome
     */
    fun isPermutationOfPalindrome(phrase: String?): Boolean {
        if (phrase === "" || phrase == null) return false

        var countOdd = 0
        val table = IntArray(Character.getNumericValue('z') - Character.getNumericValue('a') + 1)

        for (c in phrase.toCharArray()) {
            val x = getCharNumber(c)
            if (x != -1) {
                table[x]++
                if (table[x] % 2 == 1) {
                    countOdd++
                } else {
                    countOdd--
                }
            }
        }

        return countOdd <= 1
    }

    private fun getCharNumber(c: Char?): Int {
        val a = Character.getNumericValue('a')
        val z = Character.getNumericValue('z')
        val value = Character.getNumericValue(c!!)

        if (a <= value && value <= z) {
            return value - a
        }
        return -1
    }
}
