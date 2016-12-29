import java.util.*
import com.jalgoarena.type.ListNode

class RemoveDups {
    fun removeDuplicates(listNode: ListNode?): ListNode? {
        var node = listNode
        val root = node
        val set = HashSet<Int>()
        var previous: ListNode? = null

        while (node != null) {
            if (set.contains(node.value)) {
                previous!!.next = node.next
            } else {
                set.add(node.value)
                previous = node
            }
            node = node.next
        }

        return root
    }
}
