import com.jalgoarena.type.ListNode
import com.jalgoarena.type.*

class PalindromeList {
    /**
     * @param head Linked List to check if it's palindrome
     * *
     * @return  Indicates if input linked list is palindrome
     */
    fun isPalindrome(head: ListNode?): Boolean {
        val reversed = reverseAndClone(head)
        return isEqual(head, reversed)
    }

    private fun reverseAndClone(listNode: ListNode?): ListNode? {
        var node = listNode
        var head: ListNode? = null
        while (node != null) {
            val n = ListNode(node.value)
            n.next = head
            head = n
            node = node.next
        }
        return head
    }

    private fun isEqual(first: ListNode?, second: ListNode?): Boolean {
        var one = first
        var two = second
        while (one != null && two != null) {
            if (one.value != two.value) {
                return false
            }
            one = one.next
            two = two.next
        }
        return one == null && two == null
    }
}
