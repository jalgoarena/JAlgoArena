public class Solution {
    /**
     * @param matrix Matrix to set zeros
     * @return  Matrix with set zeros
     */
    public void zeroMatrix(int[][] matrix) {
        if (matrix.length == 0 || matrix.length != matrix[0].length) return;

        boolean[] row = new boolean[matrix.length];
        boolean[] column = new boolean[matrix[0].length];

        storeTheRowAndColumnIndexWithValueZero(row, column, matrix);
        nullifyRows(row, matrix);
        nullifyColumns(column, matrix);
    }

    private static void nullifyColumns(boolean[] column, int[][] matrix) {
        for (int j = 0; j < column.length; j++) {
            if (column[j]) nullifyColumn(matrix, j);
        }
    }

    private static void nullifyColumn(int[][] matrix, int column) {
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][column] = 0;
        }
    }

    private static void nullifyRows(boolean[] row, int[][] matrix) {
        for (int i = 0; i < row.length; i++) {
            if (row[i]) nullifyRow(matrix, i);
        }
    }

    private static void nullifyRow(int[][] matrix, int row) {
        for (int j = 0; j < matrix[0].length; j++) {
            matrix[row][j] = 0;
        }
    }

    private static void storeTheRowAndColumnIndexWithValueZero(
            boolean[] row, boolean[] column, int[][] matrix) {

        for (int i = 0; i < row.length; i++) {
            for (int j = 0; j < column.length; j++) {
                if (matrix[i][j] == 0) {
                    row[i] = true;
                    column[j] = true;
                }
            }
        }
    }
}
