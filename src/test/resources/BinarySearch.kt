class BinarySearch {
    /**
     * @param arr An array of Integers
     * *
     * @param n element to find
     * *
     * @return  Indicates if result is found
     */
    fun binarySearch(arr: IntArray?, n: Int): Int {
        if (arr == null || arr.isEmpty()) return -1

        var lo = 0
        var hi = arr.size - 1

        while (lo <= hi) {
            val mid = lo + (hi - lo) / 2
            if (n < arr[mid]) {
                hi = mid - 1
            } else if (n > arr[mid]) {
                lo = mid + 1
            } else {
                return mid
            }
        }

        return -1
    }
}
