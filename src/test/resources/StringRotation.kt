class StringRotation {
    /**
     * @param s1 s1 string
     * *
     * @param s2 s2 string
     * *
     * @return  Is s2 rotation of s1
     */
    fun isRotation(s1: String, s2: String): Boolean {
        val length = s1.length

        if (length == s2.length && length > 0) {
            val s1s1 = s1 + s1
            return s1s1.contains(s2)
        }

        return false
    }
}
