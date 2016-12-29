class PalindromePerm {
    /**
     * @param phrase string to be checked if any permutation is palindrome
     * *
     * @return  Indicate if string is a permutation of palindrome
     */
    fun isPermutationOfPalindrome(phrase: String?): Boolean {
        if (phrase === "" || phrase == null) return false

        val table = buildCharFrequencyTable(phrase.toLowerCase())
        return checkMaxOneOdd(table)
    }

    private fun checkMaxOneOdd(table: IntArray): Boolean {
        var foundOdd = false

        for (count in table) {
            if (count % 2 == 1) {
                if (foundOdd) {
                    return false
                }
                foundOdd = true
            }
        }

        return true
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

    private fun buildCharFrequencyTable(phrase: String): IntArray {
        val table = IntArray(Character.getNumericValue('z') - Character.getNumericValue('a') + 1)

        phrase.toCharArray()
                .map { getCharNumber(it) }
                .filter { it != -1 }
                .forEach { table[it]++ }

        return table
    }
}
