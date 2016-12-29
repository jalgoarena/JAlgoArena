class RotateMatrix {
    /**
     * @param matrix Image matrix to rotate
     * *
     * @return  Rotated image matrix by 90 degrees
     */
    fun rotate(matrix: Array<IntArray>) {
        if (matrix.isEmpty() || matrix.size != matrix[0].size) return

        val n = matrix.size

        for (layer in 0..n / 2 - 1) {
            val first = layer
            val last = n - 1 - layer

            for (i in first..last - 1) {
                val offset = i - first
                // save top
                val top = matrix[first][i]
                // left -> top
                matrix[first][i] = matrix[last - offset][first]
                // bottom -> left
                matrix[last - offset][first] = matrix[last][last - offset]
                // right -> bottom
                matrix[last][last - offset] = matrix[i][last]
                // top -> right
                matrix[i][last] = top
            }
        }
    }
}
