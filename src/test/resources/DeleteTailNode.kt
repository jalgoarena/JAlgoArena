import com.jalgoarena.type.ListNode
import com.jalgoarena.type.*

class DeleteTailNode {
    /**
     * @param head Linked List head
     * *
     * @return  Initial linked list with removed tail
     */
    fun deleteAtTail(head: ListNode?): ListNode? {
        if (head == null || head.next == null) {
            return null
        }

        var preTail: ListNode? = head

        while (preTail!!.next!!.next != null) {
            preTail = preTail.next
        }

        preTail.next = null
        return head
    }
}
