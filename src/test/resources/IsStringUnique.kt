class IsStringUnique {
    /**
     * @param str input string to be checked
     * *
     * @return  Indicate if string contains only unique chars
     */
    fun isUniqueChars(str: String?): Boolean {
        if (str == null) return true

        val chars = str.toCharArray()

        for (i in 0..chars.size - 1 - 1) {

            val toCheck = chars[i]

            (i + 1..chars.size - 1)
                    .filter { toCheck == chars[it] }
                    .forEach { return false }
        }

        return true
    }
}