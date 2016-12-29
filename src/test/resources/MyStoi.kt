class MyStoi {
    fun stoi(input: String?): Int {
        var str = input
        if (str == null || str.isEmpty())
            return 0

        // trim white spaces
        str = str.trim { it <= ' ' }

        var flag = '+'

        // check negative or positive
        var i = 0
        if (str[0] == '-') {
            flag = '-'
            i++
        } else if (str[0] == '+') {
            i++
        }
        // use double to store result
        var result = 0.0

        // calculate value
        while (str.length > i && str[i] >= '0' && str[i] <= '9') {
            result = result * 10 + (str[i] - '0')
            i++
        }

        if (flag == '-')
            result = -result

        // handle max and min
        if (result > Integer.MAX_VALUE)
            return Integer.MAX_VALUE

        if (result < Integer.MIN_VALUE)
            return Integer.MIN_VALUE

        return result.toInt()
    }
}
