class FibFast {
    /**
     * @param n id of fibonacci term to be returned
     * *
     * @return  N'th term of Fibonacci sequence
     */
    fun fib(n: Int): Long {
        var a: Long = 0
        var b: Long = 1

        for (i in 31 - Integer.numberOfLeadingZeros(n) downTo 0) {
            val d = a * ((b shl 1) - a)
            val e = a * a + b * b
            a = d
            b = e
            if (n.ushr(i) and 1 != 0) {
                val c = a + b
                a = b
                b = c
            }
        }

        return a
    }
}