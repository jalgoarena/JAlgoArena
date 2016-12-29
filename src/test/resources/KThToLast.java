import com.jalgoarena.type.ListNode;

public class Solution {
    class Index {
        public int value = -1;
    }

    /**
     * @param head Linked List where we need to find kth to last element
     * @param k index of element to find
     * @return  kth to last element of a singly linked list
     */
    public int kthToLast(ListNode head, int k) {
        Index idx = new Index();
        return kthToLast(head, k, idx).value;
    }

    private ListNode kthToLast(ListNode head, int k, Index idx) {
        if (head == null) {
            return new ListNode(0);
        }

        ListNode node = kthToLast(head.next, k, idx);
        idx.value += 1;

        if (idx.value == k) {
            return head;
        }

        return node;
    }
}
