fun main() {
    fun part1(input: List<String>): Int = input
        .map { it.toInt() }
        .asSequence()
        .let { list ->
            list.flatMapIndexed { aIndex, a ->
                list.mapIndexedNotNull { bIndex, b ->
                    if (aIndex != bIndex) {
                        Pair(a, b)
                    } else {
                        null
                    }
                }
            }
        }
        .first { (a, b) -> a + b == 2020 }
        .let { (a, b) -> a * b }

    fun part2(input: List<String>): Int = input
        .map { it.toInt() }
        .asSequence()
        .let { list ->
            list.flatMapIndexed { aIndex, a ->
                list.flatMapIndexed { bIndex, b ->
                    if (aIndex != bIndex) {
                        list.mapIndexedNotNull { cIndex, c ->
                            if (cIndex != aIndex && cIndex != bIndex) {
                                Triple(a, b, c)
                            } else {
                                null
                            }
                        }
                    } else {
                        emptySequence()
                    }
                }
            }
        }
        .first { (a, b, c) -> a + b + c == 2020 }
        .let { (a, b, c) -> a * b * c}

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 514579)

    val input = readInput("Day01")
    println(part1(input))

    check(part2(testInput) == 241861950)
    println(part2(input))
}
