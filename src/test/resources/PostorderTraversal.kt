import com.jalgoarena.type.TreeNode
import java.util.*
import com.jalgoarena.type.*

class PostorderTraversal {

    fun postorderTraversal(root: TreeNode?): IntArray {
        val items = ArrayList<Int>()
        postorder(root, items)
        return items.toIntArray()
    }

    private fun postorder(root: TreeNode?, items: MutableList<Int>) {
        if (root != null) {
            postorder(root.left, items)
            postorder(root.right, items)
            items.add(root.data)
        }
    }
}
