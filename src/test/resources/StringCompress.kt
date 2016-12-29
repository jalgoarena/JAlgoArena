class StringCompress {
    /**
     * @param str String to compress
     * *
     * @return  Compressed string.
     */
    fun compress(str: String?): String? {
        if (str == null) return null

        val compressed = StringBuilder()
        var countConsecutive = 0

        for (i in 0..str.length - 1) {
            countConsecutive++

            if (i + 1 >= str.length || str[i] != str[i + 1]) {
                compressed.append(str[i]).append(countConsecutive)
                countConsecutive = 0
            }
        }

        return if (compressed.length < str.length) compressed.toString() else str
    }
}
