import com.jalgoarena.type.ListNode;

public class Solution {
    /**
     * @param l1 First Linked List to add
     * @param l2 Second Linked List to add
     * @return  linked list node containing result of sum
     */
    public ListNode addLists(ListNode l1, ListNode l2) {
        return addLists(l1, l2, 0);
    }

    private ListNode addLists(ListNode l1, ListNode l2, int carry) {
        if (l1 == null && l2 == null && carry == 0) {
            return null;
        }


        int value = carry;
        if (l1 != null) {
            value += l1.value;
        }
        if (l2 != null) {
            value += l2.value;
        }

        ListNode result = new ListNode(value % 10);

        if (l1 != null || l2 != null) {
            ListNode more = addLists(
                    l1 == null ? null : l1.next,
                    l2 == null ? null : l2.next,
                    value >= 10 ? 1 : 0
            );
            result.next = more;
        }

        return result;
    }
}
