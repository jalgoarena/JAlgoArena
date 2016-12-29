import com.jalgoarena.type.ListNode
import com.jalgoarena.type.*

class KThToLast {
    inner class Index {
        var value = -1
    }

    /**
     * @param head Linked List where we need to find kth to last element
     * *
     * @param k index of element to find
     * *
     * @return  kth to last element of a singly linked list
     */
    fun kthToLast(head: ListNode, k: Int): Int {
        val idx = Index()
        return kthToLastPassingIndex(head, k, idx).value
    }

    private fun kthToLastPassingIndex(head: ListNode?, k: Int, idx: Index): ListNode {
        if (head == null) {
            return ListNode(0)
        }

        val node = kthToLastPassingIndex(head.next, k, idx)
        idx.value += 1

        if (idx.value == k) {
            return head
        }

        return node
    }
}
