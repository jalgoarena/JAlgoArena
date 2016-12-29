import com.jalgoarena.type.TreeNode

class HeightOfBinaryTree {

    fun findHeight(root: TreeNode?): Int {
        val lh: Int
        val rh: Int
        if (root == null) return 0

        lh = findHeight(root.left)
        rh = findHeight(root.right)
        if (lh > rh) return lh + 1

        return rh + 1
    }
}
