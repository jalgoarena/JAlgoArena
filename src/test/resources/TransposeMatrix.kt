class TransposeMatrix {

    fun transposeMatrix(matrix: Array<IntArray>) {
        val n = matrix.size - 1
        for (i in 0..n) {
            for (j in i + 1..n) {
                val temp = matrix[i][j]
                matrix[i][j] = matrix[j][i]
                matrix[j][i] = temp
            }
        }
    }
}
