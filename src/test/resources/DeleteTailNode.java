import com.jalgoarena.type.ListNode;

public class Solution {
    /**
     * @param head Linked List head
     * @return  Initial linked list with removed tail
     */
    public ListNode deleteAtTail(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode preTail = head;

        while (preTail.next.next != null) {
            preTail = preTail.next;
        }

        preTail.next = null;
        return head;
    }
}
