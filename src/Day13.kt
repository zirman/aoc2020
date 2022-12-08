fun main() {
    fun part1(input: List<String>): Int {
        val (l1, l2) = input
        val t0 = l1.toInt()

        return l2.split(",")
            .mapNotNull { it.toIntOrNull() }
            .map { Pair(it, it - (t0 % it)) }
            .minBy { it.second }
            .let { (x, y) -> x * y }
    }

    fun part2(input: List<String>): Long {
        val (l1, l2) = input

        val t0 = l1.toInt()

        l2.split(",")
            .map { it.toIntOrNull() }
            .mapIndexed { i, x -> Pair(x, i) }
            .sortedByDescending { it.first }
            .also { println(it) }


        TODO()
//            .map { Pair(it, it - (t0 % it)) }
//            .minBy { it.second }
//            .let { (x, y) -> x * y }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 295)
    val input = readInput("Day13")
    println(part1(input))

    check(part2(listOf("0", "67,7,59,61")) == 754018L)
    println(part2(input))
}
