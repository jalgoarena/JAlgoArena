public class Solution {
    /**
     * @param n id of fibonacci term to be returned
     * @return  N'th term of Fibonacci sequence
     */
    public long fib(int n) {
        long a = 0;
        long b = 1;

        for (int i = 31 - Integer.numberOfLeadingZeros(n); i >= 0; i--) {
            long d = a * ((b<<1) - a);
            long e = (a*a) + (b*b);
            a = d;
            b = e;
            if (((n >>> i) & 1) != 0) {
                long c = a+b;
                a = b;
                b = c;
            }
        }

        return a;
    }
}