import java.util.*;
import com.jalgoarena.type.*;

public class Solution {

    public String insertPairStar(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }

        if (str.substring(0,1).equals(str.substring(1,2))) {
            return str.substring(0,1) + "*" + insertPairStar(str.substring(1, str.length()));
        }
        
        return str.substring(0, 1) + insertPairStar(str.substring(1, str.length()));
    }
}
