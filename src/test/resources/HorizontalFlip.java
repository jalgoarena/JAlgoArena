import java.util.*;
import com.jalgoarena.type.*;

public class Solution {
    /**
     * @param matrix Image matrix to flip
     * @return Operation in-place
     */
    public void flipHorizontalAxis(int[][] matrix) {
        for (int i = 0; i < matrix.length / 2; i++) {
            int[] tmp = matrix[i];
            matrix[i] = matrix[matrix.length - 1 - i];
            matrix[matrix.length - 1 - i] = tmp;
        }
    }
}
