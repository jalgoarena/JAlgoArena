import java.util.*;
import com.jalgoarena.type.*;

public class Solution {
    /**
     * @param str Input string
     * @return  First non-duplicate character or null if absent
     */
    public String findFirstNonRepeatedChar(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }

        char[] chars = str.toCharArray();
        Map<Character, Integer> register = new HashMap<>();

        for(char c : chars) {
            if (register.containsKey(c)) {
                register.put(c, 2);
            } else {
                register.put(c, 1);
            }
        }

        for (char c : chars) {
            if (register.get(c) == 1) return Character.toString(c);
        }

        return null;
    }
}
