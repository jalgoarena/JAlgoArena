import com.jalgoarena.type.ListNode
import com.jalgoarena.type.*

class SumLists2 {
    internal inner class PartialSum {
        var sum: ListNode? = null
        var carry = 0
    }

    /**
     * @param l1 First Linked List to add
     * *
     * @param l2 Second Linked List to add
     * *
     * @return  linked list node containing result of sum
     */
    fun addLists(l1: ListNode?, l2: ListNode?): ListNode? {
        var node1 = l1
        var node2 = l2
        val len1 = length(node1)
        val len2 = length(node2)

        if (len1 < len2) {
            node1 = padList(node1, len2 - len1)
        } else {
            node2 = padList(node2, len1 - len2)
        }

        val sum = addListsHelper(node1, node2)

        if (sum.carry == 0) {
            return sum.sum
        } else {
            val result = insertBefore(sum.sum, sum.carry)
            return result
        }
    }

    private fun addListsHelper(l1: ListNode?, l2: ListNode?): PartialSum {
        if (l1 == null && l2 == null) {
            return PartialSum()
        }

        val sum = addListsHelper(l1!!.next, l2!!.next)

        val `val` = sum.carry + l1.value + l2.value

        val fullResult = insertBefore(sum.sum, `val` % 10)

        sum.sum = fullResult
        sum.carry = `val` / 10
        return sum
    }

    private fun insertBefore(list: ListNode?, data: Int): ListNode {
        val node = ListNode(data)
        if (list != null) {
            node.next = list
        }
        return node
    }

    private fun padList(list: ListNode?, padding: Int): ListNode? {
        var head = list
        for (i in 0..padding - 1) {
            head = insertBefore(head, 0)
        }
        return head
    }

    private fun length(node: ListNode?): Int {
        if (node == null) return 0

        return 1 + length(node.next)
    }
}
