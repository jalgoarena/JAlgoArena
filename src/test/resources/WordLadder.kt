import java.util.*

class WordLadder {
    fun ladderLength(start: String, end: String,
                     dict: HashSet<String>): Int {
        var result = 0
        if (dict.size == 0) {
            return result
        }

        dict.add(start)
        dict.add(end)

        result = BFS(start, end, dict)

        return result
    }

    private fun BFS(start: String, end: String, dict: HashSet<String>): Int {
        val queue = LinkedList<String>()
        val length = LinkedList<Int>()
        queue.add(start)
        length.add(1)

        while (!queue.isEmpty()) {
            val word = queue.poll()
            val len = length.poll()

            if (match(word, end)) {
                return len
            }

            for (i in 0..word.length - 1) {
                val arr = word.toCharArray()
                var c = 'a'
                while (c <= 'z') {
                    if (c == arr[i]) {
                        c++
                        continue
                    }

                    arr[i] = c
                    val str = String(arr)
                    if (dict.contains(str)) {
                        queue.add(str)
                        length.add(len + 1)
                        dict.remove(str)
                    }
                    c++
                }
            }
        }

        return 0
    }

    private fun match(word: String, end: String): Boolean {
        if (word == end) {
            return true
        }
        return false
    }
}
