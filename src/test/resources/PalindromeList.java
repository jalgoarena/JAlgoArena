import com.jalgoarena.type.ListNode;

public class Solution {
    /**
     * @param head Linked List to check if it's palindrome
     * @return  Indicates if input linked list is palindrome
     */
    public boolean isPalindrome(ListNode head) {
        ListNode reversed = reverseAndClone(head);
        return isEqual(head, reversed);
    }

    private ListNode reverseAndClone(ListNode node) {
        ListNode head = null;
        while (node != null) {
            ListNode n = new ListNode(node.value);
            n.next = head;
            head = n;
            node = node.next;
        }
        return head;
    }

    private boolean isEqual(ListNode one, ListNode two) {
        while (one != null && two != null) {
            if (one.value != two.value) {
                return false;
            }
            one = one.next;
            two = two.next;
        }
        return one == null && two == null;
    }
}
