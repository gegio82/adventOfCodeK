fun main() {
    fun String.asIntRange(): IntRange =
        substringBefore('-').toInt() .. substringAfter('-').toInt()

    fun String.asRanges(): Pair<IntRange, IntRange> =
        substringBefore(',').asIntRange() to substringAfter(',').asIntRange()

    operator fun IntRange.contains(other: IntRange): Boolean {
        return this.first <= other.first && this.last >= other.last
    }

    infix fun IntRange.overlaps(other: IntRange): Boolean {
        return this.first <= other.first && this.last >= other.first ||
                other.first <= this.first && other.last >= this.first
    }

    fun part1(assignments: List<Pair<IntRange, IntRange>>): Int {
        return assignments.count {it.first in it.second || it.second in it.first}
    }

    fun part2(assignments: List<Pair<IntRange, IntRange>>): Int {
        return assignments.count { it.first overlaps it.second }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputByLine("Day04_test").map {it.asRanges()}
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInputByLine("Day04").map {it.asRanges()}
    part1(input).println()
    part2(input).println()
}



