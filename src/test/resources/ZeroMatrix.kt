class ZeroMatrix {
    /**
     * @param matrix Matrix to set zeros
     * *
     * @return  Matrix with set zeros
     */
    fun zeroMatrix(matrix: Array<IntArray>) {
        if (matrix.isEmpty() || matrix.size != matrix[0].size) return

        val row = BooleanArray(matrix.size)
        val column = BooleanArray(matrix[0].size)

        storeTheRowAndColumnIndexWithValueZero(row, column, matrix)
        nullifyRows(row, matrix)
        nullifyColumns(column, matrix)
    }

    private fun nullifyColumns(column: BooleanArray, matrix: Array<IntArray>) {
        column.indices
                .filter { column[it] }
                .forEach { nullifyColumn(matrix, it) }
    }

    private fun nullifyColumn(matrix: Array<IntArray>, column: Int) {
        for (i in matrix.indices) {
            matrix[i][column] = 0
        }
    }

    private fun nullifyRows(row: BooleanArray, matrix: Array<IntArray>) {
        row.indices
                .filter { row[it] }
                .forEach { nullifyRow(matrix, it) }
    }

    private fun nullifyRow(matrix: Array<IntArray>, row: Int) {
        for (j in 0..matrix[0].size - 1) {
            matrix[row][j] = 0
        }
    }

    private fun storeTheRowAndColumnIndexWithValueZero(
            row: BooleanArray, column: BooleanArray, matrix: Array<IntArray>) {

        for (i in row.indices) {
            for (j in column.indices) {
                if (matrix[i][j] == 0) {
                    row[i] = true
                    column[j] = true
                }
            }
        }
    }
}
