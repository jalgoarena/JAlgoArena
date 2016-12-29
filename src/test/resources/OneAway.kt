class OneAway {
    /**
     * @param first first string
     * *
     * @param second second string
     * *
     * @return  Is first string one or zero edits away from the second?
     */
    fun oneEditAway(first: String, second: String): Boolean {
        if (first == second)
            return true

        if (isLengthDifferenceBiggerThanOne(first, second))
            return false

        val shorter = if (first.length < second.length) first else second
        val longer = if (first.length < second.length) second else first

        return isOneEditAway(shorter, longer)
    }

    private fun isOneEditAway(shorter: String, longer: String): Boolean {
        var indexShorter = 0
        var indexLonger = 0
        var foundDifference = false

        while (indexLonger < longer.length && indexShorter < shorter.length) {
            if (shorter[indexShorter] != longer[indexLonger]) {
                if (foundDifference) return false
                foundDifference = true

                if (shorter.length == longer.length) {
                    indexShorter++
                }
            } else {
                indexShorter++
            }
            indexLonger++
        }

        return true
    }

    private fun isLengthDifferenceBiggerThanOne(first: String, second: String): Boolean {
        return Math.abs(first.length - second.length) > 1
    }
}