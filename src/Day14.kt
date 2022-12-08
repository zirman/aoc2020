fun main() {
    fun part1(input: List<String>): Long {
        var andMask = 0L
        var orMask = 0L
        val mem = mutableMapOf<Int, Long>()

        input.forEach { line ->
            if (line.startsWith("mask")) {
                val (m) = Regex("""^mask = ([01X]+)$""").matchEntire(line)!!.destructured
                andMask = m.map { if (it == 'X') "1" else "0" }
                    .joinToString("") { it }
                    .toLong(2)

                orMask = m.map { if (it == '1') "1" else "0" }
                    .joinToString("") { it }
                    .toLong(2)
            } else if (line.startsWith("mem")) {
                val (loc, num) = Regex("""^mem\[(\d+)\] = (\d+)$""").matchEntire(line)!!.destructured
                mem[loc.toInt()] = num.toLong().and(andMask).or(orMask)
            }
        }

        return mem.values.sum()
    }

    fun part2(input: List<String>): Long {
        var andMask = 0L
        var orMasks = listOf(0L)
        val mem = mutableMapOf<Long, Long>()

        input.forEach { line ->
            if (line.startsWith("mask")) {
                val (maskStr) = Regex("""^mask = ([01X]+)$""").matchEntire(line)!!.destructured
                andMask = maskStr.map { if (it != 'X') "1" else "0" }
                    .joinToString("") { it }
                    .toLong(2)

                val maskChars = maskStr.toList()
                val floatingCount = maskChars.count { it == 'X' }

                orMasks = (0 until 1.shl(floatingCount))
                    .map {
                        val floating = it.toString(2).toList()
                        val xs = listOf(List(floatingCount - floating.size) { '0' }, floating).flatten()

                        maskChars.fold(Pair(0, emptyList<Char>())) { (index, acc), char ->
                            if (char == 'X') {
                                Pair(index + 1, acc + xs[index])
                            } else {
                                Pair(index, acc + char)
                            }
                        }
                    }
                    .map { (_, bitStr) -> bitStr.joinToString("").toLong(2) }
            } else if (line.startsWith("mem")) {
                val (locStr, numStr) = Regex("""^mem\[(\d+)\] = (\d+)$""").matchEntire(line)!!.destructured
                val num = numStr.toLong()
                val loc = locStr.toLong().and(andMask)
                orMasks.forEach {
                    mem[loc.or(it)] = num
                }
            }
        }

        return mem.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 165L)
    val input = readInput("Day14")
    println(part1(input))
    val testInput2 = readInput("Day14_test2")
    check(part2(testInput2) == 208L)
    println(part2(input))
}
