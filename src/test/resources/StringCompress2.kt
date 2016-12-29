class StringCompress2 {
    /**
     * @param str String to compress
     * *
     * @return  Compressed string.
     */
    fun compress(str: String): String {

        val finalLength = countCompression(str)
        if (finalLength >= str.length) return str

        val compressed = StringBuilder(finalLength)
        var countConsecutive = 0

        for (i in 0..str.length - 1) {
            countConsecutive++

            if (i + 1 >= str.length || str[i] != str[i + 1]) {
                compressed.append(str[i]).append(countConsecutive)
                countConsecutive = 0
            }
        }

        return compressed.toString()
    }

    private fun countCompression(str: String): Int {
        var compressedLength = 0
        var countConsecutive = 0
        for (i in 0..str.length - 1) {
            countConsecutive++

            if (i + 1 >= str.length || str[i] != str[i + 1]) {
                compressedLength += 1 + countConsecutive.toString().length
                countConsecutive = 0
            }
        }

        return compressedLength
    }
}
