import java.io.File
import aoc.intputer.*

fun main(args: Array<String>) {
    if (args.size == 0) {
        println("Please provide the input file")
        return
    }

    var intputer: Intputer = Intputer(File(args[0]).readText())

    // TODO(brrcrites): turn this into a threaded function just for fun
    for (noun in 0..99) {
        for (verb in 0..99) {
            intputer.reset_tape()
            intputer.tape.set(1, noun)
            intputer.tape.set(2, verb)
            intputer.process_tape()
            if(intputer.tape.get(0) == 19690720) {
                println("Noun: ${noun}")
                println("Verb: ${verb}")
            }
        }
    }
}
