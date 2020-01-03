
fun is_candidate(candidate: String): Boolean {
    var digits_match: Boolean = false
    for (i in 1 until candidate.length) {
        if (candidate.get(i - 1) == candidate.get(i)) {
            digits_match = true
        }
    }
    var always_decreasing: Boolean = true
    for (i in 1 until candidate.length) {
        // The char and int probably less using the same rules, but convert
        // to int just to be safe
        if (candidate.get(i - 1).toInt() > candidate.get(i).toInt()) {
            always_decreasing = false
        }
    }
    if (digits_match && always_decreasing) {
        return true
    }
    return false
}

fun main(args: Array<String>) {
    val start: Int = 236491
    val end: Int = 713787
    var candidate_count: Int = 0

    for (candidate in start..end) {
        /*
        Convert the integer to a string because we will want
        to work with it on a characterwise basis so it will
        probably be easier (I guess I could convert it into an
        Array<Int> which might be actually the easiest

        TOOD(brrcrites): look at if 'toList' will convert a string
        or integer to a list (probably not)
        */
        if (is_candidate(candidate.toString())) {
            candidate_count += 1
        }
    }

    println("There are ${candidate_count} possible passwords that meet the criteria")
}
