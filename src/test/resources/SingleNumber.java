import java.util.Hashtable;

public class Solution {
    public int singleNumber(int[] arr) {
        Hashtable<Integer, Integer> set = new Hashtable<>();
        int number = 0;

        for (int i = 0; i < arr.length; i++) {
            if (set.containsKey(arr[i])) {
                set.put(arr[i], set.get(arr[i]) + 1);
            } else {
                set.put(arr[i], 1);
            }
        }

        for (Integer key: set.keySet()) {
            if (set.get(key) == 1)
                return (int) key;
        }

        return number;
    }
}
