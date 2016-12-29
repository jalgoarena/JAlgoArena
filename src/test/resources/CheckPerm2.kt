import java.util.*

class CheckPerm2 {
    /**
     * @param str1 first string to be checked for permutation match
     * *
     * @param str2 second string to be checked for permutation match
     * *
     * @return  Indicate if one string is a permutation of another
     */
    fun permutation(str1: String, str2: String): Boolean {
        if (str1.length != str2.length) return false

        return sort(str1) == sort(str2)
    }

    private fun sort(s: String): String {
        val content = s.toCharArray()
        Arrays.sort(content)
        return String(content)
    }
}