class OneAway2 {
    /**
     * @param first first string
     * *
     * @param second second string
     * *
     * @return  Is first string one or zero edits away from the second?
     */
    fun oneEditAway(first: String, second: String): Boolean {
        if (first == second) return true

        if (first.length == second.length) {
            return isOneEditReplace(first, second)
        }

        if (first.length + 1 == second.length) {
            return isOneEditInsert(first, second)
        }

        if (first.length == second.length + 1) {
            return isOneEditInsert(second, first)
        }

        return false
    }

    private fun isOneEditInsert(first: String, second: String): Boolean {
        var index1 = 0
        var index2 = 0

        while (index2 < second.length && index1 < first.length) {
            if (first[index1] != second[index2]) {
                if (index1 != index2) {
                    return false
                }
                index2++
            } else {
                index1++
                index2++
            }
        }

        return true
    }

    private fun isOneEditReplace(first: String, second: String): Boolean {
        var foundDifference = false
        for (i in 0..first.length - 1) {
            if (first[i] != second[i]) {
                if (foundDifference) {
                    return false
                }
                foundDifference = true
            }
        }
        return true
    }
}