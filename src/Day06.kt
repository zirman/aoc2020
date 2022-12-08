fun main() {
    fun part1(input: List<String>): Long {
        return input
            .joinToString("\n") { it }
            .split("\n\n")
            .map { group ->
                group.filter { it.isLetter() }
                    .toSet().size
            }
            .sumOf { it.toLong() }
    }

    fun part2(input: List<String>): Int {
        return input
            .joinToString("\n") { it }
            .split("\n\n")
            .sumOf { group ->
                val x = group.split('\n')
                    .map { it.toSet() }

                x.reduce { a, b -> a.intersect(b) }.size
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 11L)
    check(part2(testInput) == 6)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
