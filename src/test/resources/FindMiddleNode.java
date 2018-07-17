import com.jalgoarena.type.ListNode;

public class Solution {
    /**
     * @param head Linked List head
     * @return  Middle node
     */
    public ListNode findMiddleNode(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return head;

        ListNode slow = head;
        ListNode fast = head.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return (fast == null) ? slow : slow.next;
    }
}
