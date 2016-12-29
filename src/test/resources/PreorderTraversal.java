import java.util.*;

import com.jalgoarena.type.TreeNode;

public class Solution {

    public int[] preorderTraversal(TreeNode root) {
        List<Integer> items = new ArrayList<>();
        preorder(root, items);
        return items.stream().mapToInt(i -> i).toArray();
    }

    private void preorder(TreeNode root, List<Integer> items) {
        if (root != null) {
            items.add(root.data);
            preorder(root.left, items);
            preorder(root.right, items);
        }
    }
}
