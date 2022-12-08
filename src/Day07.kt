fun main() {
    fun part1(input: List<String>): Int {
        val bags = input
            .map { rule ->
                val (outerBags, innerBags) = rule.split(" contain ")

                val innerBags1 = innerBags.substringBeforeLast(".")

                if (Regex("""no other bags""").matches(innerBags1)) {
                    emptyList()
                } else {
                    innerBags1
                        .split(", ")
                        .map { innerBag ->
                            Regex("""^(\d) ([a-z ]+) bags?$""")
                                .find(innerBag)!!
                                .destructured
                                .let { (n, bag) -> bag }
                        }
                }.let {
                    Pair(outerBags.substringBeforeLast(" bags"), it)
                }
            }
            .associate { it }

        tailrec fun visitBags(bag: Set<String>, visited: Set<String>): Int {
            val outerBag = bags.filter { (_, values) -> values.any { bag.contains(it) } }
                .mapNotNull { (key, _) -> if (visited.contains(key).not()) key else null }

            return if (outerBag.isEmpty()) visited.size else visitBags(outerBag.toSet(), visited.plus(outerBag))
        }

        return visitBags(setOf("shiny gold"), emptySet())
    }

    fun part2(input: List<String>): Int {
        val bags = input
            .map { rule ->
                val (outerBags, innerBags) = rule.split(" contain ")

                val innerBags1 = innerBags.substringBeforeLast(".")

                if (Regex("""no other bags""").matches(innerBags1)) {
                    emptyList()
                } else {
                    innerBags1
                        .split(", ")
                        .flatMap { innerBag ->
                            Regex("""^(\d) ([a-z ]+) bags?$""")
                                .find(innerBag)!!
                                .destructured
                                .let { (n, bag) -> List(n.toInt()) { bag } }
                        }
                }.let {
                    Pair(outerBags.substringBeforeLast(" bags"), it)
                }
            }
            .associate { it }

        fun countBags(bag: String): Int {
            return bags[bag]!!.sumOf { countBags(it) } + 1
        }

        return countBags("shiny gold") - 1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 4)
    check(part2(readInput("Day07_test2")) == 126)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
