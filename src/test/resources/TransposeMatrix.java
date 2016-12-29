public class Solution {

    public void transposeMatrix(int[][] matrix) {
        int n = matrix.length - 1;
        for (int i = 0; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }
}
