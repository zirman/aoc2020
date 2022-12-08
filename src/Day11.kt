fun main() {
    fun part1(input: List<String>): Int {
        var seats = input.map { row -> row.map { it } }

        fun isOccupied(r: Int, c: Int): Boolean {
            return (r >= 0 && r < seats.size &&
                    c >= 0 && c < seats[r].size) &&
                    seats[r][c] == '#'
        }

        fun occupiedAdjacent(r: Int, c: Int): Int {
            return listOf(
                isOccupied(r - 1, c),
                isOccupied(r + 1, c),
                isOccupied(r, c - 1),
                isOccupied(r, c + 1),
                isOccupied(r - 1, c - 1),
                isOccupied(r - 1, c + 1),
                isOccupied(r + 1, c - 1),
                isOccupied(r + 1, c + 1)
            ).count { it }
        }

        while (true) {
            val delta = seats.indices.map { r ->
                seats[r].indices.map { c ->
                    when (val p = seats[r][c]) {
                        'L' -> {
                            if (occupiedAdjacent(r, c) == 0) '#' else 'L'
                        }

                        '#' -> {
                            if (occupiedAdjacent(r, c) >= 4) 'L' else '#'
                        }

                        else -> {
                            p
                        }
                    }
                }
            }

            if (delta == seats) break
            seats = delta
        }

        return seats.sumOf { it.count { it == '#' } }
    }

    fun part2(input: List<String>): Int {
        var seats = input.map { row -> row.map { it } }

        fun isNotOccupied(r: Int, c: Int, dr: Int, dc: Int): Boolean {
            val r1 = r + dr
            val c1 = c + dc
            return r1 < 0 || r1 >= seats.size ||
                    c1 < 0 || c1 >= seats[r1].size ||
                    seats[r1][c1] == 'L' ||
                    (seats[r1][c1] == '.' && isNotOccupied(r1, c1, dr, dc))
        }

        fun occupiedAdjacent(r: Int, c: Int): Int {
            return listOf(
                isNotOccupied(r, c, -1, 0),
                isNotOccupied(r, c, 1, 0),
                isNotOccupied(r, c, 0, -1),
                isNotOccupied(r, c, 0, 1),
                isNotOccupied(r, c, -1, -1),
                isNotOccupied(r, c, -1, 1),
                isNotOccupied(r, c, 1, -1),
                isNotOccupied(r, c, 1, 1)
            )
                .count { it.not() }
        }

        while (true) {
            val delta = seats.indices.map { r ->
                seats[r].indices.map { c ->
                    when (val p = seats[r][c]) {
                        'L' -> {
                            if (occupiedAdjacent(r, c) == 0) '#' else 'L'
                        }

                        '#' -> {
                            if (occupiedAdjacent(r, c) >= 5) 'L' else '#'
                        }

                        else -> {
                            p
                        }
                    }
                }
            }

            if (delta == seats) break
            seats = delta
        }

        return seats.sumOf { it.count { it == '#' } }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 26)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
