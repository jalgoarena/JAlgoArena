class Fib {
    /**
     * @param n id of fibonacci term to be returned
     * *
     * @return  N'th term of Fibonacci sequence
     */
    fun fib(n: Int): Long {
        if (n <= 0) return 0
        if (n == 1) return 1

        return fib(n - 1) + fib(n - 2)
    }
}