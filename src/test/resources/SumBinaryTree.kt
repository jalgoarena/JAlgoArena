import com.jalgoarena.type.TreeNode

class SumBinaryTree {

    fun sum(root: TreeNode?): Int {
        if (root == null) return 0
        return root.data + sum(root.left) + sum(root.right)
    }
}
