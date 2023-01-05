fun main() {

    fun Char.toPriority(): Int {
        return when (this) {
            in 'a'..'z' -> this - 'a' + 1
            in 'A'..'Z' -> this - 'A' + 27
            else -> error("Invalid char")
        }
    }

    fun part1(rucksacks: List<String>): Int {
        return rucksacks.map { line -> line.substring(0, line.length / 2) to line.substring(line.length / 2)}
            .flatMap { it.first.toSet() intersect it.second.toSet() }
            .sumOf { it.toPriority() }
    }

    fun part2(rucksacks: List<String>): Int {
        return rucksacks.chunked(3)
            .flatMap { (rucksack1, rucksack2, rucksack3) ->
                rucksack1.toSet()
                    .intersect(rucksack2.toSet())
                    .intersect(rucksack3.toSet()) }
            .sumOf {
                it.toPriority()
        }

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputByLine("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInputByLine("Day03")
    part1(input).println()
    part2(input).println()
}
