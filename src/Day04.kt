fun main() {
    val requiredFields = setOf(
        "byr",
        "iyr",
        "eyr",
        "hgt",
        "hcl",
        "ecl",
        "pid",
    )

    fun fieldPairs(input: List<String>): List<List<List<String>>> {
        return input.joinToString("\n") { it }
            .split("\n\n")
            .map { fieldPair ->
                fieldPair.split(Regex("""(\n| )+"""))
                    .map { it.split(":") }
            }
    }

    fun part1(input: List<String>): Long {
        return fieldPairs(input)
            .sumOf { it ->
                it.map { it[0] }
                    .toSet()
                    .containsAll(requiredFields)
                    .let { if (it) 1L else 0L }
            }
    }

    fun part2(input: List<String>): Long {
        return fieldPairs(input)
            .sumOf { fieldPair ->
                fieldPair
                    .find { (field, value) ->
                        when (field) {
                            "byr" ->
                                value.toLong() in 1920..2002

                            "iyr" ->
                                value.toLong() in 2010..2020

                            "eyr" ->
                                value.toLong() in 2020..2030

                            "hgt" -> Regex("""^(\d+)(cm|in)$""")
                                .find(value)
                                ?.destructured
                                ?.let { (num, units) ->
                                    when (units) {
                                        "cm" -> {
                                            num.toLong() in 150..193
                                        }

                                        "in" ->
                                            num.toLong() in 59..76

                                        else ->
                                            false
                                    }
                                }
                                ?: false

                            "hcl" -> Regex("""^#([a-f]|\d){6}$""")
                                .find(value) != null

                            "ecl" -> setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
                                .contains(value)

                            "pid" -> Regex("""^\d{9}$""")
                                .find(value) != null

                            else ->
                                true
                        }.not()
                    }
                    ?.let { 0L }
                    ?: fieldPair.map { it[0] }
                        .toSet()
                        .containsAll(requiredFields)
                        .let { if (it) 1L else 0L }
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2L)
    check(part2(testInput) == 2L)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
