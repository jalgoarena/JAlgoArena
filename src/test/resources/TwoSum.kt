import java.util.*

class TwoSum {
    fun twoSum(numbers: IntArray, target: Int): IntArray {
        val map = HashMap<Int, Int>()
        val result = IntArray(2)

        for (i in numbers.indices) {
            if (map.containsKey(numbers[i])) {
                val index = map[numbers[i]]
                result[0] = index!! + 1
                result[1] = i + 1
                break
            } else {
                map.put(target - numbers[i], i)
            }
        }

        return result
    }
}
