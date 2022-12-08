fun main() {
    fun part1(preamble: Int, input: List<String>): Long {
        val codes = input.map { it.toLong() }

        return input.indices.drop(preamble)
            .first { i ->
                (i - preamble until i)
                    .flatMap { x ->
                        (x + 1 until i)
                            .map { y -> codes[x] + codes[y] }
                    }
                    .contains(codes[i])
                    .not()
            }
            .let { codes[it] }
    }

    fun part2(preamble: Int, input: List<String>): Long {
        val invalid = part1(preamble, input)
        val codes = input.map { it.toLong() }

        val group = (2..codes.size)
            .asSequence()
            .mapNotNull { window ->
                codes.asSequence()
                    .windowed(window)
                    .find { it.sum() == invalid }
            }
            .first()

        return group.min() + group.max()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(5, testInput) == 127L)
    check(part2(5, testInput) == 62L)

    val input = readInput("Day09")
    println(part1(25, input))
    println(part2(25, input))
}
