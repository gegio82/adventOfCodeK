fun main() {
    fun CharSequence.allUnique() = toSet().count() == length

    fun execute(line: String, charNumber: Int): Int =
        line.windowedSequence(charNumber).indexOfFirst {
            it.allUnique()
        } + charNumber

    fun part1(input: String): Int {
        return execute(input, 4)
    }

    fun part2(input: String): Int {
        return execute(input, 14)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}



