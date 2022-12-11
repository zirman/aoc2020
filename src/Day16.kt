fun main() {
    fun part1(input: List<String>): Int {
        val (fieldRangesStr, _, nearbyTicketsStr) =
            input.joinToString("\n").split("\n\n")

        val ranges = fieldRangesStr
            .split("\n")
            .flatMap { line ->
                val (start1, end1, start2, end2) = Regex("""[\w\s]+: (\d+)-(\d+) or (\d+)-(\d+)""")
                    .matchEntire(line)!!.destructured

                listOf(start1.toInt()..end1.toInt(), start2.toInt()..end2.toInt())
            }
            .sortedBy { it.first }

        return nearbyTicketsStr
            .split("\n")
            .drop(1)
            .flatMap { it.split(",") }
            .map { it.toInt() }
            .filter { s -> ranges.none { it.contains(s) } }
            .sum()
    }

    fun part2(input: List<String>): Long {
        val (fieldRangesStr, yourTicketStr, nearbyTicketsStr) =
            input.joinToString("\n").split("\n\n")

        val fieldRanges = fieldRangesStr
            .split("\n")
            .map {
                val (fieldName, start1, end1, start2, end2) = Regex("""([\w\s]+): (\d+)-(\d+) or (\d+)-(\d+)""")
                    .matchEntire(it)!!.destructured

                Pair(fieldName, listOf(start1.toInt()..end1.toInt(), start2.toInt()..end2.toInt()))
            }

        val ranges = fieldRanges.flatMap { (_, ranges) -> ranges }.sortedBy { it.first }

        val tickets = nearbyTicketsStr
            .split("\n")
            .drop(1)
            .mapNotNull { ticketLine ->
                val xs = ticketLine.split(",").map { it.toInt() }
                if (xs.any { x -> ranges.none { it.contains(x) } }) {
                    null
                } else {
                    xs
                }
            }

        val groups = tickets
            .first()
            .indices.map { c ->
                Pair(
                    c,
                    tickets.indices
                        .map { r -> tickets[r][c] }
                        .toSet()
                )
            }

        val yourTicket = yourTicketStr
            .split("\n")
            .drop(1)
            .first()
            .split(",")
            .map { it.toInt() }

        return fieldRanges
            .map { (fieldName, ranges) ->
                Pair(
                    fieldName,
                    groups
                        .filter { (_, set) -> set.all { ranges[0].contains(it) || ranges[1].contains(it) } }
                        .map { (c) -> c }
                )
            }
            .sortedBy { (_, cs) -> cs.size }
            .fold(emptyList<Pair<String, Int>>()) { acc, (fieldName, cs) ->
                acc.plus(
                    Pair(
                        fieldName,
                        cs.filter { c -> acc.all { (_, k) -> k != c } }[0]
                    )
                )
            }
            .filter { (fieldName, _) -> fieldName.startsWith("departure ") }
            .map { (_, c) -> yourTicket[c].toLong() }
            .also { println(it) }
            .reduce { a, b -> a * b }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16_test")
    check(part1(testInput) == 71)
    val input = readInput("Day16")
    println(part1(input))
    println(part2(input))
}
