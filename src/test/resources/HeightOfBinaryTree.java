import com.jalgoarena.type.TreeNode;

public class Solution {

    public int findHeight(TreeNode root) {
        int lh, rh;
        if (root == null) return 0;

        lh = findHeight(root.left);
        rh = findHeight(root.right);
        if (lh > rh) return lh+1;

        return rh + 1;
    }
}
