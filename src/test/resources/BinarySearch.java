import java.util.*;
import com.jalgoarena.type.*;

public class Solution {
    /**
     * @param arr An array of Integers
     * @param n element to find
     * @return  Indicates if result is found
     */
    public int binarySearch(int[] arr, int n) {
        if (arr == null || arr.length == 0) return -1;

        int lo = 0;
        int hi = arr.length - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (n < arr[mid]) {
                hi = mid - 1;
            } else if (n > arr[mid]) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }

        return -1;
    }
}
