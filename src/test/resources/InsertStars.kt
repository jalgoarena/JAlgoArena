class InsertStars {

    fun insertPairStar(str: String?): String? {
        if (str == null || str.length <= 1) {
            return str
        }

        if (str.substring(0, 1) == str.substring(1, 2)) {
            return str.substring(0, 1) + "*" + insertPairStar(str.substring(1, str.length))
        }

        return str.substring(0, 1) + insertPairStar(str.substring(1, str.length))
    }
}
