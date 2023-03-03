import java.io.IOException
import java.util.regex.Pattern



fun main() {
    fun part1(input: List<String>): Int {
        return getAllDirectories(input).filter { it.size() < 100000 }.sumOf { it.size() }
    }

    fun part2(input: List<String>): Int {
        val allDirectories = getAllDirectories(input)
        val root = allDirectories.first()
        val freeSpace = 70000000 - root.size()
        val requiredSpace = 30000000
        return allDirectories
            .filter { it.size() >= requiredSpace - freeSpace }
            .minOf { it.size() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputByLine("Day07_test")
    check(part1(testInput) == 95437)

    val part2 = part2(testInput)
    check(part2 == 24933642)

    val input = readInputByLine("Day07")
    part1(input).println()
    part2(input).println()
}

private val CD_COMMAND = Pattern.compile("\\$ cd ([a-zA-Z/.]+)")
private val LS_COMMAND = Pattern.compile("\\$ ls")
private val FILE = Pattern.compile("(\\d+) (.+)")
private val DIR = Pattern.compile("dir ([a-zA-Z/]+)")


@Throws(IOException::class)
fun getAllDirectories(allLines:List<String>): List<Directory> {
    val allDirectories: MutableList<Directory> = ArrayList()
    val root = Directory(null, "/")
    allDirectories.add(root)
    var currentDir: Directory? = null
    var i = 0
    while (i < allLines.size) {
        val command = allLines[i]
        val cdMatcher = CD_COMMAND.matcher(command)
        if (cdMatcher.matches()) {
            val dirName = cdMatcher.group(1)
            currentDir = if (dirName == "..") {
                currentDir?.getParent() ?: root
            } else {
                currentDir?.getSubDirectory(dirName) ?: root
            }
            i++
            continue
        }
        val lsMatcher = LS_COMMAND.matcher(command)
        if (lsMatcher.matches()) {
            assert(currentDir != null)
            i++
            while (i < allLines.size) {
                val fsObject = allLines[i]
                val fileMatcher = FILE.matcher(fsObject)
                val dirMatcher = DIR.matcher(fsObject)
                if (fileMatcher.matches()) {
                    val size = fileMatcher.group(1).toInt()
                    val fileName = fileMatcher.group(2)
                    currentDir!!.addChild(File(fileName, size))
                } else if (dirMatcher.matches()) {
                    val dirName = dirMatcher.group(1)
                    val child = Directory(currentDir, dirName)
                    currentDir!!.addChild(child)
                    allDirectories.add(child)
                } else {
                    break
                }
                i++
            }
        }
    }
    return allDirectories
}

interface FSObject{
    fun name(): String
    fun size(): Int
}

data class File(val name: String, val size: Int) : FSObject {
    override fun size(): Int = size
    override fun name(): String = name
}

data class Directory(private val parent: Directory?, val name: String) : FSObject {
    private val children: MutableList<FSObject> = ArrayList()
    override fun toString(): String {
        return "dir $name $children"
    }

    fun addChild(child: FSObject) {
        children.add(child)
    }

    fun getSubDirectory(name: String): Directory? {
        return children
            .filter { obj: FSObject? -> Directory::class.java.isInstance(obj) }
            .map { obj: FSObject? -> Directory::class.java.cast(obj) }
            .first { it.name() == name }
    }

    override fun size(): Int {
        return children.sumOf {it.size() }
    }

    override fun name(): String = name

    fun getParent(): Directory? {
        return parent
    }
}




