import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        var x = 0
        var y = 0
        var dir = 0

        input.forEach {
            val n = it.slice(1 until it.length).toInt()
            when (val op = it[0]) {
                'F' -> {
                    when (dir) {
                        0 -> x += n
                        1 -> y -= n
                        2 -> x -= n
                        3 -> y += n
                        else -> throw Exception()
                    }
                }

                'N' -> {
                    y -= n
                }

                'S' -> {
                    y += n
                }

                'E' -> {
                    x += n
                }

                'W' -> {
                    x -= n
                }

                'L' -> {
                    dir = (dir + (n / 90)) % 4
                }

                'R' -> {
                    dir = (dir - (n / 90)).mod(4)
                }

                else -> {
                    throw Exception()
                }
            }
        }

        return x.absoluteValue + y.absoluteValue
    }

    fun part2(input: List<String>): Int {
        var x = 0
        var y = 0

        var wpx = 10
        var wpy = -1

        input.forEach {
            val n = it.slice(1 until it.length).toInt()
            when (val op = it[0]) {
                'F' -> {
                    x += wpx * n
                    y += wpy * n
                }

                'N' -> {
                    wpy -= n
                }

                'S' -> {
                    wpy += n
                }

                'E' -> {
                    wpx += n
                }

                'W' -> {
                    wpx -= n
                }

                'L' -> {
                    when ((n / 90).mod(4)) {
                        0 -> {
                        }

                        1 -> {
                            val tmp = wpx
                            wpx = wpy
                            wpy = -tmp
                        }

                        2 -> {
                            wpx = -wpx
                            wpy = -wpy
                        }

                        3 -> {
                            val tmp = wpx
                            wpx = -wpy
                            wpy = tmp
                        }
                    }
                }

                'R' -> {
                    when ((-(n / 90)).mod(4)) {
                        0 -> {
                        }

                        1 -> {
                            val tmp = wpx
                            wpx = wpy
                            wpy = -tmp
                        }

                        2 -> {
                            wpx = -wpx
                            wpy = -wpy
                        }

                        3 -> {
                            val tmp = wpx
                            wpx = -wpy
                            wpy = tmp
                        }
                    }
                }

                else -> {
                    throw Exception()
                }
            }
//            println("$it $x $y $wpx $wpy")
        }

        return x.absoluteValue + y.absoluteValue
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 25)
    check(part2(testInput) == 286)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
