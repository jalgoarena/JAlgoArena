import com.jalgoarena.type.ListNode;

public class Solution {
    class PartialSum {
        public ListNode sum = null;
        public int carry = 0;
    }

    /**
     * @param l1 First Linked List to add
     * @param l2 Second Linked List to add
     * @return  linked list node containing result of sum
     */
    public ListNode addLists(ListNode l1, ListNode l2) {
        int len1 = length(l1);
        int len2 = length(l2);

        if (len1 < len2) {
            l1 = padList(l1, len2 - len1);
        } else {
            l2 = padList(l2, len1 - len2);
        }

        PartialSum sum = addListsHelper(l1, l2);

        if (sum.carry == 0) {
            return sum.sum;
        } else {
            ListNode result = insertBefore(sum.sum, sum.carry);
            return result;
        }
    }

    private PartialSum addListsHelper(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
            return new PartialSum();
        }

        PartialSum sum = addListsHelper(l1.next, l2.next);

        int val = sum.carry + l1.value + l2.value;

        ListNode fullResult = insertBefore(sum.sum, val % 10);

        sum.sum = fullResult;
        sum.carry = val / 10;
        return sum;
    }

    private ListNode insertBefore(ListNode list, int data) {
        ListNode node = new ListNode(data);
        if (list != null) {
            node.next = list;
        }
        return node;
    }

    private ListNode padList(ListNode list, int padding) {
        ListNode head = list;
        for (int i = 0; i < padding; i++) {
            head = insertBefore(head, 0);
        }
        return head;
    }

    private int length(ListNode node) {
        if (node == null) return 0;

        return 1 + length(node.next);
    }
}
