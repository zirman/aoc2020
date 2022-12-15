sealed interface Rule {
    data class Leaf(val t: Char) : Rule {

    }

    data class And(val rs: List<Int>) : Rule {

    }

    data class Or(val lrs: List<Int>, val rrs: List<Int>) : Rule {

    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val (rulesStr, dataStr) = input.joinToString("\n").split("\n\n")
        val rules = rulesStr
            .split("\n")
            .associate { line ->
                """^(\d+): "(\w)"$""".toRegex().matchEntire(line)?.destructured
                    ?.let { (rn, t) ->
                        Pair(
                            rn.toInt(),
                            Rule.Leaf(t[0])
                        )
                    } ?: """^(\d+): (\d+( \d+)*)$""".toRegex().matchEntire(line)?.destructured
                    ?.let { (rn, a) ->
                        Pair(
                            rn.toInt(),
                            Rule.And(a.split(" ").map { it.toInt() })
                        )
                    } ?: """^(\d+): (\d+( \d+)*) \| (\d+( \d+)*)$""".toRegex().matchEntire(line)!!.destructured
                    .let { (rn, a, _, b, _) ->
                        Pair(
                            rn.toInt(),
                            Rule.Or(
                                a.split(" ").map { it.toInt() },
                                b.split(" ").map { it.toInt() },
                            )
                        )
                    }
            }

        return dataStr
            .split("\n")
            .map { line ->
                val ts = line.toList()

                fun match(i: Int, rn: Int): Int? {
                    fun matchLeaf(i: Int, r: Rule.Leaf): Int? {
                        return if (i >= ts.size || ts[i] != r.t) null
                        else i + 1
                    }

                    fun matchAnd(i: Int, r: Rule.And): Int? {
                        var k = i
                        for (q in r.rs) {
                            val l = match(k, q) ?: return null
                            k = l
                        }
                        return k
                    }

                    fun matchOr(i: Int, r: Rule.Or): Int? {
                        return matchAnd(i, Rule.And(r.lrs)) ?: matchAnd(i, Rule.And(r.rrs))
                    }

                    return when (val r = rules[rn]!!) {
                        is Rule.Leaf -> matchLeaf(i, r)
                        is Rule.And -> matchAnd(i, r)
                        is Rule.Or -> matchOr(i, r)
                    }
                }

                match(0, 0) == ts.size
            }
            .count { it }
    }

    fun part2(input: List<String>): Int {
        val (rulesStr, dataStr) = input.joinToString("\n").split("\n\n")
        val rules = rulesStr
            .split("\n")
            .associate { line ->
                """^(\d+): "(\w)"$""".toRegex().matchEntire(line)?.destructured
                    ?.let { (rn, t) ->
                        Pair(
                            rn.toInt(),
                            Rule.Leaf(t[0])
                        )
                    } ?: """^(\d+): (\d+( \d+)*)$""".toRegex().matchEntire(line)?.destructured
                    ?.let { (rn, a) ->
                        Pair(
                            rn.toInt(),
                            Rule.And(a.split(" ").map { it.toInt() })
                        )
                    } ?: """^(\d+): (\d+( \d+)*) \| (\d+( \d+)*)$""".toRegex().matchEntire(line)!!.destructured
                    .let { (rn, a, _, b, _) ->
                        Pair(
                            rn.toInt(),
                            Rule.Or(
                                a.split(" ").map { it.toInt() },
                                b.split(" ").map { it.toInt() },
                            )
                        )
                    }
            }
            .plus(Pair(8, Rule.Or(listOf(42), listOf(42, 8))))
            .plus(Pair(11, Rule.Or(listOf(42, 31), listOf(42, 11, 31))))

        return dataStr
            .split("\n")
            .map { line ->
                val ts = line.toList()

                fun match(i: Int, rn: Int): List<Int> {
                    fun matchLeaf(i: Int, r: Rule.Leaf): List<Int> {
                        return if (i >= ts.size || ts[i] != r.t) emptyList()
                        else listOf(i + 1)
                    }

                    fun matchAnd(i: Int, r: Rule.And): List<Int> {
                        return r.rs.fold(listOf(i)) { acc, q ->
                            acc.flatMap { k -> match(k, q) }
                        }
                    }

                    fun matchOr(i: Int, r: Rule.Or): List<Int> {
                        return matchAnd(i, Rule.And(r.lrs)) + matchAnd(i, Rule.And(r.rrs))
                    }

                    return when (val r = rules[rn]!!) {
                        is Rule.Leaf -> matchLeaf(i, r)
                        is Rule.And -> matchAnd(i, r)
                        is Rule.Or -> matchOr(i, r)
                    }
                }

                match(0, 0).count { i -> i == ts.size }
            }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day19_test")

    check(part1(testInput) == 2)
    val input = readInput("Day19")
    println(part1(input))
    val testInput2 = readInput("Day19_test2")
    check(part1(testInput2) == 3)
    check(part2(testInput2) == 12)
    println(part2(input))
}
