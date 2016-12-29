import com.jalgoarena.type.TreeNode
import java.util.*
import com.jalgoarena.type.*

class InorderTraversal {

    fun inorderTraversal(root: TreeNode?): IntArray {
        val items = ArrayList<Int>()
        inorder(root, items)
        return items.toIntArray()
    }

    private fun inorder(root: TreeNode?, items: MutableList<Int>) {
        if (root != null) {
            inorder(root.left, items)
            items.add(root.data)
            inorder(root.right, items)
        }
    }
}
