import java.util.*;

import com.jalgoarena.type.ListNode;

public class Solution {
    /**
     * @param linkedList Linked List where we need to remove duplicates
     * @return  Linked List with removed duplicates
     */
    public ListNode removeDuplicates(ListNode node) {
        ListNode root = node;
        HashSet<Integer> set = new HashSet<>();
        ListNode previous = null;

        while (node != null) {
            if (set.contains(node.value)) {
                previous.next = node.next;
            } else {
                set.add(node.value);
                previous = node;
            }
            node = node.next;
        }

        return root;
    }
}
