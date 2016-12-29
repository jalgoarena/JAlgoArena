import com.jalgoarena.type.ListNode

class FindMiddleNode {
    /**
     * @param head Linked List head
     * *
     * @return  Middle node
     */
    fun findMiddleNode(head: ListNode?): ListNode? {
        if (head == null) return null
        if (head.next == null) return head

        var slow: ListNode? = head
        var fast = head.next

        while (fast != null && fast.next != null) {
            slow = slow!!.next
            fast = fast.next!!.next
        }

        return slow
    }
}
