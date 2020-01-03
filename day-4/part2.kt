
fun is_candidate(candidate: String): Boolean {

    var digits_match: Boolean = false
    // This is a terrible hack that I can do because we know the password will be 6 digits long
    // TOOD(brrcrites): create something more elegant here that would actually go past 6 digits
    if (candidate.get(0) == candidate.get(1) && candidate.get(1) != candidate.get(2)) {
        digits_match = true
    } else if (candidate.get(1) == candidate.get(2) && candidate.get(1) != candidate.get(0) && candidate.get(2) != candidate.get(3)) {
        digits_match = true
    } else if (candidate.get(2) == candidate.get(3) && candidate.get(2) != candidate.get(1) && candidate.get(3) != candidate.get(4)) {
        digits_match = true
    } else if (candidate.get(3) == candidate.get(4) && candidate.get(3) != candidate.get(2) && candidate.get(4) != candidate.get(5)) {
        digits_match = true
    } else if (candidate.get(4) == candidate.get(5) && candidate.get(4) != candidate.get(3)) {
        digits_match = true
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
        if (is_candidate(candidate.toString())) {
            candidate_count += 1
        }
    }

    println("There are ${candidate_count} possible passwords that meet the criteria")
}
