import java.util.Hashtable

class SingleNumber {
    fun singleNumber(arr: IntArray): Int {
        val set = Hashtable<Int, Int>()
        val number = 0

        for (i in arr.indices) {
            if (set.containsKey(arr[i])) {
                set.put(arr[i], set[arr[i]]!! + 1)
            } else {
                set.put(arr[i], 1)
            }
        }

        set.keys
                .filter { set[it!!] === 1 }
                .forEach { return it }

        return number
    }
}
