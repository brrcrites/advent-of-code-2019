import java.io.File
import aoc.intputer.*

fun main(args: Array<String>) {
    if (args.size == 0) {
        println("Please provide the input file")
        return
    }

    var intputer: Intputer = Intputer(File(args[0]).readText())

    println("Setting position 1 to 12 and 2 to 2")
    intputer.tape.set(1, 12)
    intputer.tape.set(2, 2)

    println("Value at position 0 after processing: ${intputer.process_tape()}")
}

