import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths


fun main() {

    @Throws(IOException::class)
    fun execute(line: String, charNumber: Int): Int {
        var position = -1
        for (i in 0 until line.length - charNumber) {
            if (line.substring(i, i + charNumber).chars().distinct().count() == charNumber.toLong()) {
                position = i + charNumber
                break
            }
        }
        return position
    }

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



