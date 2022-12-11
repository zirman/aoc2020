data class Res<T>(val index: Int, val x: T)

sealed interface Exp {
    data class Num(val x: Long) : Exp
    data class Group(val x: Exp, val chain: List<Pair<String, Exp>>) : Exp
}

fun parseToken(i: Int, ts: List<String>, t: String): Res<Unit>? {
    return when {
        i >= ts.size || ts[i] != t -> null
        else -> Res(i + 1, Unit)
    }
}

fun parseEitherToken(i: Int, ts: List<String>, tokens: List<String>): Res<String>? {
    return when {
        i >= ts.size || tokens.contains(ts[i]).not() -> null
        else -> Res(i + 1, ts[i])
    }
}

fun parseNum(i: Int, ts: List<String>): Res<Exp.Num>? {
    return when {
        i >= ts.size -> null
        else -> ts[i].toLongOrNull()?.let { Res(i + 1, Exp.Num(it)) }
    }
}

fun parseParensGroup(i: Int, ts: List<String>): Res<Exp>? {
    return parseToken(i, ts, "(")?.let { (i) ->
        parse(i, ts)?.let { (i, exp) ->
            parseToken(i, ts, ")")?.let { (i) ->
                Res(i, exp)
            }
        }
    }
}

fun parseChain(i: Int, ts: List<String>): Res<List<Pair<String, Exp>>> {
    var k = i

    val l = buildList {
        while (true) {
            parseEitherToken(k, ts, listOf("+", "*"))?.let { (i, operator) ->
                parseNum(i, ts)?.let { (i, num) ->
                    k = i
                    Pair(operator, num)
                } ?: parseParensGroup(i, ts)?.let { (i, group) ->
                    k = i
                    Pair(operator, group)
                }
            }?.let { add(it) } ?: break
        }
    }

    return Res(k, l)
}

fun parse(i: Int, ts: List<String>): Res<Exp>? {
    return (parseNum(i, ts) ?: parseParensGroup(i, ts))?.let { (i, x) ->
        parseChain(i, ts).let { (i, y) ->
            Res(i, Exp.Group(x, y))
        }
    }
}

fun Exp.eval(): Long {
    return when (this) {
        is Exp.Group -> {
            chain.fold(x.eval()) { acc, (operator, exp) ->
                when (operator) {
                    "+" -> acc + exp.eval()
                    "*" -> acc * exp.eval()
                    else -> throw Exception("Invalid Operator")
                }
            }
        }

        is Exp.Num -> this.x
    }
}

fun Exp.eval2(): Long {
    return when (this) {
        is Exp.Group -> {
            chain
                .fold(listOf(x.eval2())) { acc, (operator, exp) ->
                    when (operator) {
                        "+" -> acc.subList(0, acc.size - 1).plus(acc.last() + exp.eval2())
                        "*" -> acc.plus(exp.eval2())
                        else -> throw Exception("Invalid Operator")
                    }
                }
                .reduce { acc, l -> acc * l }
        }

        is Exp.Num -> this.x
    }
}

fun tokenize(line: String): List<String> {
    return line.toList()
        .joinToString("") { if (it == '(') "( " else if (it == ')') " )" else it.toString() }
        .split(" ")
}

fun main() {
    fun part1(input: List<String>): Long {
        return input.sumOf { line ->
            val ts = tokenize(line)

            parse(0, ts)!!
                .let { (i, exp) ->
                    assert(i == ts.size)
                    exp
                }
                .eval()
        }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { line ->
            val ts = tokenize(line)

            parse(0, ts)!!
                .let { (i, exp) ->
                    assert(i == ts.size)
                    exp
                }
                .eval2()
        }
    }

    // test if implementation meets criteria from the description, like:
    check(part1(listOf("2 * 3 + (4 * 5)")) == 26L)
    check(part1(listOf("5 + (8 * 3 + 9 + 3 * 4 * 3)")) == 437L)
    check(part1(listOf("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")) == 12240L)
    check(part1(listOf("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")) == 13632L)
    val input = readInput("Day18")
    println(part1(input))
    check(part2(listOf("1 + (2 * 3) + (4 * (5 + 6))")) == 51L)
    check(part2(listOf("2 * 3 + (4 * 5)")) == 46L)
    check(part2(listOf("5 + (8 * 3 + 9 + 3 * 4 * 3)")) == 1445L)
    check(part2(listOf("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")) == 669060L)
    check(part2(listOf("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")) == 23340L)
    println(part2(input))
}
