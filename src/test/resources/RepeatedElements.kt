import java.util.*

class RepeatedElements {
    /**
     * @param arr An array of Integers
     * *
     * @return  Array of sorted numbers representing duplicates in original array
     */
    fun findDuplicates(arr: IntArray?): String {
        if (arr == null || arr.isEmpty()) {
            return "[]"
        }

        Arrays.sort(arr)

        var current = arr[0]
        var idx = 1

        val result = HashSet<Int>()

        while (idx < arr.size) {
            if (arr[idx] == current) {
                result.add(current)
            } else {
                current = arr[idx]
            }

            idx++
        }

        return result.toString()
    }
}
