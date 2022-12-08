fun main() {
    fun part1(input: String): Int {
        val ns = input.split(",").map { it.toInt() }.toMutableList()

        for (i in ns.size - 1 until 2020 - 1) {
            tailrec fun findLast(k: Int): Int {
                return when {
                    k < 0 -> 0
                    ns[k] == ns[i] -> i - k
                    else -> findLast(k - 1)
                }
            }

            ns.add(findLast(i - 1))
        }

        return ns.last()
    }

    fun part2(input: String, iterations: Int): Int {
        val input2 = input.split(",").map { it.toInt() }
        val lastMap = input2.dropLast(1).mapIndexed { index, s -> Pair(s, index + 1) }.toMap().toMutableMap()
        var last = input2.last()

        for (i in input2.size until iterations) {
            val spoken = when (val k = lastMap[last]) {
                null -> 0
                else -> i - k
            }
            lastMap[last] = i
            last = spoken
        }

        return last
    }

    // test if implementation meets criteria from the description, like:
    val testInput = "0,3,6"
    check(part2(testInput, 2020) == 436)
    val input = "13,16,0,12,15,1"
    println(part2(input, 2020))
    check(part2(testInput, 30000000) == 175594)
    println(part2(input, 30000000))
}
