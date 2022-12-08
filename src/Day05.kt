fun main() {
    fun convertRow(code: String): Long {
        return code
            .map {
                when (it) {
                    'F' -> "0"
                    'B' -> "1"
                    else -> throw Exception()
                }
            }
            .joinToString("") { it }
            .toLong(2)
    }

    fun convertCol(code: String): Long {
        return code
            .map {
                when (it) {
                    'R' -> "1"
                    'L' -> "0"
                    else -> throw Exception()
                }
            }
            .joinToString("") { it }
            .toLong(2)
    }

    fun part1(input: List<String>): Long {
        return input
            .map { seat ->
                val (row, col) = Regex("""([FB]{7})([RL]{3})""").find(seat)!!.destructured
                (convertRow(row) * 8 + convertCol(col))
            }
            .max()
    }

    fun part2(input: List<String>): Long {
        val range = input
            .map { seat ->
                val (row, col) = Regex("""([FB]{7})([RL]{3})""").find(seat)!!.destructured
                (convertRow(row) * 8 + convertCol(col))
            }

        return (range.min()..range.max())
            .find { range.contains(it).not() }!!
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 820L)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
