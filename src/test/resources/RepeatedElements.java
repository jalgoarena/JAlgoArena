import java.util.*;
import com.jalgoarena.type.*;

public class Solution {
    /**
     * @param arr An array of Integers
     * @return  Array of sorted numbers representing duplicates in original array
     */
    public String findDuplicates(int[] arr) {
        if (arr == null || arr.length == 0) {
            return "[]";
        }

        Arrays.sort(arr);

        int current = arr[0];
        int idx = 1;

        Set<Integer> result = new HashSet<>();

        while (idx < arr.length) {
            if (arr[idx] == current) {
                result.add(current);
            } else {
                current = arr[idx];
            }

            idx++;
        }

        return result.toString();
    }
}
