fun main() {

    operator fun String.component1() = this[0]
    operator fun String.component2() = this[1]
    operator fun String.component3() = this[2]

    fun part1(input: List<String>): Int {
        fun shapeScore(shape: Char) = shape - 'X' + 1

        fun resultScore(line: String): Int {
            return when (line) {
                "B X", "C Y", "A Z" -> 0
                "A X", "B Y", "C Z" -> 3
                "C X", "A Y", "B Z" -> 6
                else -> error("check your input")
            }
        }

        return input.sumOf {
            round -> shapeScore(round[2]) + resultScore(round)
        }
    }

    fun part2(input: List<String>): Int {

        fun shapeScore(line: String): Int {
            return when (line) {
                "A Y", "B X", "C Z" -> 1
                "B Y", "C X", "A Z" -> 2
                "C Y", "A X", "B Z" -> 3
                else -> error("check your input")
            }
        }

        fun resultScore(shape: Char) = (shape - 'X') * 3

        return input.sumOf {
                round -> shapeScore(round) + resultScore(round[2])
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputByLine("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInputByLine("Day02")
    part1(input).println()
    part2(input).println()
}
