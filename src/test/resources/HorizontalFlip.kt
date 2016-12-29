class HorizontalFlip {
    /**
     * @param matrix Image matrix to flip
     * *
     * @return Operation in-place
     */
    fun flipHorizontalAxis(matrix: Array<IntArray>) {
        for (i in 0..matrix.size / 2 - 1) {
            val tmp = matrix[i]
            matrix[i] = matrix[matrix.size - 1 - i]
            matrix[matrix.size - 1 - i] = tmp
        }
    }
}
