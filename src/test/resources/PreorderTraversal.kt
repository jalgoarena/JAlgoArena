import com.jalgoarena.type.TreeNode
import java.util.*
import com.jalgoarena.type.*

class PreorderTraversal {

    fun preorderTraversal(root: TreeNode?): IntArray {
        val items = ArrayList<Int>()
        preorder(root, items)
        return items.toIntArray()
    }

    private fun preorder(root: TreeNode?, items: MutableList<Int>) {
        if (root != null) {
            items.add(root.data)
            preorder(root.left, items)
            preorder(root.right, items)
        }
    }
}
