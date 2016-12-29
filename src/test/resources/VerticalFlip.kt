class VerticalFlip {
    /**
     * @param matrix Image matrix to flip
     * *
     * @return Operation in-place
     */
    fun flipVerticalAxis(matrix: Array<IntArray>) {
        val r = matrix.size - 1
        val c = matrix[0].size - 1

        for (i in 0..r) {
            for (j in 0..c / 2) {
                val temp = matrix[i][j]
                matrix[i][j] = matrix[i][c - j]
                matrix[i][c - j] = temp
            }
        }
    }
}
