class IsStringUnique2 {
    /**
     * @param str input string to be checked
     * *
     * @return  Indicate if string contains only unique chars
     */
    fun isUniqueChars(str: String?): Boolean {
        if (str == null) return true
        if (str.length > 128) return false

        val charSet = BooleanArray(128)

        for (i in 0..str.length - 1) {
            val value = str[i].toInt()
            if (charSet[value]) return false

            charSet[value] = true
        }

        return true
    }
}