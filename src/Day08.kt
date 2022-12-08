sealed interface Result {
    data class Loop(val acc: Int) : Result
    data class Halt(val acc: Int) : Result
}

fun main() {
    fun parse(input: List<String>): List<Pair<String, Int>> {
        return input.map {
            val (op, n) = it.split(" ")
            Pair(op, n.toInt())
        }
    }


    fun interpret(inst: List<Pair<String, Int>>): Result {
        var acc = 0
        var i = 0
        val ex = mutableSetOf<Int>()

        while (true) {
            if (ex.contains(i)) {
                return Result.Loop(acc)
            } else if (i >= inst.size) {
                return Result.Halt(acc)
            }

            ex += i

            val (op, n) = inst[i]
            when (op) {
                "acc" -> {
                    acc += n
                    i++
                }

                "jmp" -> {
                    i += n
                }

                "nop" -> {
                    i++
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        return (interpret(parse(input)) as Result.Loop).acc
    }

    fun part2(input: List<String>): Int {
        val instructions = parse(input)

        return instructions.mapIndexedNotNull { line, (op) -> if (op == "nop" || op == "jmp") line else null }
            .map { line ->
                instructions.mapIndexed { i, inst ->
                    if (i == line) {
                        Pair(if (inst.first == "nop") "jmp" else "nop", inst.second)
                    } else {
                        inst
                    }
                }
            }
            .firstNotNullOf {
                val result = interpret(it)
                if (result is Result.Halt) result.acc else null
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
