import java.util.*;

import com.jalgoarena.type.TreeNode;

public class Solution {

    public int[] postorderTraversal(TreeNode root) {
        List<Integer> items = new ArrayList<>();
        postorder(root, items);
        return items.stream().mapToInt(i -> i).toArray();
    }

    private void postorder(TreeNode root, List<Integer> items) {
        if (root != null) {
            postorder(root.left, items);
            postorder(root.right, items);
            items.add(root.data);
        }
    }
}
