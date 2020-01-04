import java.io.File
import aoc.intputer.*

fun main(args: Array<String>) {
    if(args.size == 0) {
        println("Please input the path to the puzzle as a command line arguemnt")
        return
    }

    var intputer: Intputer = Intputer(File(args[0]).readText())

    intputer.process_tape()
}
