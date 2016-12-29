import java.util.*

class FirstNonRepeatedChar {
    /**
     * @param str Input string
     * *
     * @return  First non-duplicate character or null if absent
     */
    fun findFirstNonRepeatedChar(str: String?): String? {
        if (str == null || str.isEmpty()) {
            return null
        }

        val chars = str.toCharArray()
        val register = HashMap<Char, Int>()

        for (c in chars) {
            if (register.containsKey(c)) {
                register.put(c, 2)
            } else {
                register.put(c, 1)
            }
        }

        return chars
                .firstOrNull { register[it] === 1 }
                ?.let { Character.toString(it) }
    }
}
