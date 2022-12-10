fun main() {
    fun part1(input: List<String>): Int {
        val (a, b, c) = input.joinToString("\n").split("\n\n")
        val ranges = a.split("\n").flatMap {
            val (a, b, c, d) = Regex("""[\w\s]+: (\d+)-(\d+) or (\d+)-(\d+)""").matchEntire(it)!!.destructured
            listOf(a.toInt()..b.toInt(), c.toInt()..d.toInt())
        }.sortedBy { it.first }

        return c.split("\n")
            .drop(1)
            .flatMap { it.split(",") }
            .map { it.toInt() }
            .filter { s -> ranges.none { it.contains(s) } }
            .sum()
    }

    fun part2(input: List<String>): Int {
        val (a, b, c) = input.joinToString("\n").split("\n\n")

        val fieldRanges = a
            .split("\n")
            .map {
                val (fieldName, start1, end1, start2, end2) = Regex("""([\w\s]+): (\d+)-(\d+) or (\d+)-(\d+)""")
                    .matchEntire(it)!!
                    .destructured

                Pair(fieldName, listOf(start1.toInt()..end1.toInt(), start2.toInt()..end2.toInt()))
            }

        val ranges = fieldRanges.flatMap { (_, a) -> a }

        val tickets = c.split("\n")
            .drop(1)
            .mapNotNull { ticketLine ->
                val xs = ticketLine.split(",").map { it.toInt() }
                if (xs.any { x -> ranges.none { it.contains(x) } }) {
                    null
                } else {
                    xs
                }
            }

        val groups = tickets.first().indices
            .map { c ->
                Pair(
                    c,
                    tickets.indices
                        .map { r -> tickets[r][c] }
                        .toSet()
                )
            }

        groups.map { (c, set) ->
            Pair(
                c,
                fieldRanges.filter { (_, ranges) ->
                    val (r1, r2) = ranges
                    set.all { r1.contains(it) || r2.contains(it) }
                }
            )
        }.also { println(it) }

//        fun foo(fr: List<Pair<String, List<IntRange>>>, gs: List<Pair<Int, Set<Int>>>) {
//            if (fr.isEmpty()) return
//
//            val (fieldName, ranges) = fr[0]
//            val (r1, r2) = ranges
//            val (inner, outer) = groups
//                .partition { (_, set) ->
//                    set.all { r1.contains(it) || r2.contains(it) }
//                }
//            inner.indices.map { i ->
//                foo(fr.subList(1, fr.size), outer.plus(inner.filterIndexed { k, _ -> i != k }))
//            }
//        }
//
//        foo(fieldRanges, groups)

        TODO()

//        perm(fieldRanges)
//            .first { fieldRange ->
//                groups.zip(fieldRange)
//                    .all { (g, fr) ->
//                        val (_, set) = g
//                        val (r1, r2) = fr.second
//                        set.all { r1.contains(it) || r2.contains(it) }
//                    }
//            }
//            .filter { (fieldName) -> fieldName.startsWith("departure") }
//            .also { println(it) }

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 71)
    val input = readInput("Day09")
    println(part1(input))
//    check(part2(readInput("Day09_test2")) == 13)
    println(part2(input))
}
