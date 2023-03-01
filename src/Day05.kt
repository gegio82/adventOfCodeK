import java.io.IOException
import java.util.Stack

fun interface Mover {
    fun move(stacks: List<Stack<Char>>, howMany: Int, from: Int, to: Int)
}

data class MoveAction(val howMany: Int, val from: Int, val to: Int)

fun main() {

    @Throws(IOException::class)
    fun parseInput(input: String): Pair<List<Stack<Char>>, List<MoveAction>> {
        val stacks: MutableList<Stack<Char>> = ArrayList()
        val inputSections = input.split("\n\n")
        for (line in inputSections[0].lines()) {
            var i = 0
            while (1 + i * 4 < line.length) {
                if (stacks.size <= i) {
                    for (j in stacks.size..i) {
                        stacks.add(Stack())
                    }
                }
                val current = line[1 + i * 4]
                if (Character.isLetter(current)) {
                    stacks[i].add(0, current)
                }
                i++
            }

        }
        val actions = inputSections[1]
            .split("\n")
            .map{ it.lines()}
            .map{MoveAction(it[1].toInt(), it[3].toInt(), it[5].toInt())}
        return stacks to actions
    }

    fun execute(input: String, mover: Mover): String {
        val (stacks, actions) = parseInput(input)
        actions.forEach {
            (howMany, from, to) -> mover.move(stacks, howMany, from, to)
        }
        return stacks
            .map { it.peek() }
            .joinToString(separator = "") { it.toString()}
    }

    fun move(stacks: List<Stack<Char>>, howMany: Int, from: Int, to: Int) {
        for (i in 0 until howMany) {
            val c = stacks[from - 1].pop()
            stacks[to - 1].push(c)
        }
    }

    fun moveKeepingOrder(stacks: List<Stack<Char>>, howMany: Int, from: Int, to: Int) {
        val pivot = Stack<Char>()
        for (i in 0 until howMany) {
            val c = stacks[from - 1].pop()
            pivot.push(c)
        }
        for (i in 0 until howMany) {
            val c = pivot.pop()
            stacks[to - 1].push(c)
        }
    }

    fun part1(input: String): String {
        return execute(input, ::move)
    }

    fun part2(input: String): String {
        return execute(input, ::moveKeepingOrder)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val part1 = part1(testInput)
    check(part1 == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}



