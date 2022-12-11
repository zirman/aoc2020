data class Cell(val x: Int, val y: Int, val z: Int)
data class Cell4(val x: Int, val y: Int, val z: Int, val w: Int)

fun main() {
    fun part1(input: List<String>): Int {
        var cells = buildSet {
            input.forEachIndexed { r, l ->
                l.forEachIndexed { c, p ->
                    if (p == '#') {
                        add(Cell(r, c, 0))
                    }
                }
            }
        }

        repeat(6) {
            cells = buildSet {
                (cells.minOf { (x, _, _) -> x } - 1..cells.maxOf { (x, _, _) -> x } + 1).forEach { x ->
                    (cells.minOf { (_, y, _) -> y } - 1..cells.maxOf { (_, y, _) -> y } + 1).forEach { y ->
                        (cells.minOf { (_, _, z) -> z } - 1..cells.maxOf { (_, _, z) -> z } + 1).forEach { z ->
                            val active = cells.contains(Cell(x, y, z))

                            val adjacent =
                                (x - 1..x + 1).sumOf { q ->
                                    (y - 1..y + 1).sumOf { w ->
                                        (z - 1..z + 1).count { e ->
                                            cells.contains(Cell(q, w, e))
                                        }
                                    }
                                }

                            if (active) {
                                when (adjacent - 1) {
                                    2, 3 -> {
                                        add(Cell(x, y, z))
                                    }
                                }
                            } else {
                                when (adjacent) {
                                    3 -> {
                                        add(Cell(x, y, z))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return cells.size
    }

    fun part2(input: List<String>): Int {
        var cells = buildSet {
            input.forEachIndexed { r, l ->
                l.forEachIndexed { c, p ->
                    if (p == '#') {
                        add(Cell4(r, c, 0, 0))
                    }
                }
            }
        }

        repeat(6) {
            cells = buildSet {
                (cells.minOf { (x, _, _, _) -> x } - 1..cells.maxOf { (x, _, _, _) -> x } + 1).forEach { x ->
                    (cells.minOf { (_, y, _, _) -> y } - 1..cells.maxOf { (_, y, _, _) -> y } + 1).forEach { y ->
                        (cells.minOf { (_, _, z, _) -> z } - 1..cells.maxOf { (_, _, z, _) -> z } + 1).forEach { z ->
                            (cells.minOf { (_, _, _, w) -> w } - 1..cells.maxOf { (_, _, _, w) -> w } + 1).forEach { w ->
                                val active = cells.contains(Cell4(x, y, z, w))

                                val adjacent =
                                    (x - 1..x + 1).sumOf { a ->
                                        (y - 1..y + 1).sumOf { e ->
                                            (z - 1..z + 1).sumOf { i ->
                                                (w - 1..w + 1).count { o ->
                                                    cells.contains(Cell4(a, e, i, o))
                                                }
                                            }
                                        }
                                    }

                                if (active) {
                                    when (adjacent - 1) {
                                        2, 3 -> {
                                            add(Cell4(x, y, z, w))
                                        }
                                    }
                                } else {
                                    when (adjacent) {
                                        3 -> {
                                            add(Cell4(x, y, z, w))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return cells.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day17_test")
    check(part1(testInput) == 112)
    val input = readInput("Day17")
    println(part1(input))
    check(part2(testInput) == 848)
    println(part2(input))
}
