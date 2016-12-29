import java.util.*;
import com.jalgoarena.type.*;

public class Solution {
    /**
     * @param matrix Image matrix to flip
     * @return Operation in-place
     */
    public void flipVerticalAxis(int[][] matrix) {
        int r = matrix.length - 1;
        int c = matrix[0].length - 1;

        for (int i = 0; i <= r; i++) {
            for (int j = 0; j <= c/2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][c - j];
                matrix[i][c - j] = temp;
            }
        }
    }
}
