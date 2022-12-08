fun main() {
    fun part1(input: List<String>): Int {
        val adapters = input.map { it.toInt() }
            .sorted()

        fun calc(adapters: List<Int>, jolt: Int, jumps: List<Int>): List<Int>? {
            if (adapters.isEmpty()) {
                return jumps
            } else {
                adapters.filter { it in jolt + 1..jolt + 3 }
                    .forEach { adapter ->
                        val x = calc(
                            adapters.filter { it != adapter },
                            adapter,
                            jumps.mapIndexed { i, x -> if (i + 1 == adapter - jolt) x + 1 else x }
                        )
                        if (x != null) {
                            return x
                        }
                    }
            }
            return null
        }

        val (one, _, three) = calc(adapters, 0, listOf(0, 0, 0))!!
        return one * (three + 1)
    }

    fun part2(input: List<String>): Long {
        val adapters = input.map { it.toInt() }
            .sorted()

        val memo = mutableMapOf<Int, Long>()

        fun calc(jolt: Int, i: Int): Long {
            return memo.getOrPut(i) {
                if (i == adapters.size - 1) {
                    1
                } else {
                    (i + 1 until adapters.size)
                        .takeWhile { adapters[it] <= jolt + 3 }
                        .sumOf { calc(adapters[it], it) }
                }
            }
        }

        return adapters.indices
            .takeWhile { adapters[it] <= 3 }
            .sumOf { calc(adapters[it], it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 22 * 10)
    check(part2(testInput) == 19208L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
