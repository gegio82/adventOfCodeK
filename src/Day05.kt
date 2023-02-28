import java.io.IOException
import java.util.Stack
import java.util.regex.Matcher
import java.util.regex.Pattern

fun interface Mover {
    fun move(stacks: List<Stack<Char>>, howMany: Int, from: Int, to: Int)
}

data class MoveAction(val howMany: Int, val from: Int, val to: Int)

fun main() {
    val MOVE_COMMAND = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)")

    @Throws(IOException::class)
    fun parseInput(input: List<String>): Pair<List<Stack<Char>>, List<MoveAction>> {
        val stacks: MutableList<Stack<Char>> = ArrayList()
        val actions: MutableList<MoveAction> = ArrayList()
        for (line in input) {
            val matcher: Matcher = MOVE_COMMAND.matcher(line)
            if (matcher.matches()) {
                val howMany = matcher.group(1).toInt()
                val from = matcher.group(2).toInt()
                val to = matcher.group(3).toInt()
                actions.add(MoveAction(howMany, from, to))
            } else {
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
        }
        return stacks to actions
    }

    fun execute(input: List<String>, mover: Mover): String {
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

    fun part1(input: List<String>): String {
        return execute(input, ::move)
    }

    fun part2(input: List<String>): String {
        return execute(input, ::moveKeepingOrder)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputByLine("Day05_test")
    val part1 = part1(testInput)
    check(part1 == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInputByLine("Day05")
    part1(input).println()
    part2(input).println()
}



