
package aoc.intputer

class Intputer(input: String) {
    var tape: MutableList<Int>

    init {
        tape = input.trim().split(",").map{ it.toInt() }.toMutableList()
    }

    /*
    We should update this to print each line based on its split rules
    rather than printing each memory position

    TODO(brrcrites): turn this into a stringify function for the class
    */
    fun dump_tape() {
        for ((index,value) in tape.withIndex()) {
            println("[${index}]: ${value}")
        }
    }

    /*
    We should probably come up with a better way to conver the tape
    into something that is easier to process and build
    */
    fun process_tape() {
        var tape_index: Int = 0
        while (tape.get(tape_index) != 99) {
            when(tape.get(tape_index)) {
                1 -> tape_index = opcode1(tape_index)
                2 -> tape_index = opcode2(tape_index)
            }
        }
    }

    /*
    The case statement above is good and I like having the function set 
    the new index position but I'm not wild about how the tape is being
    referenced here. We should probably add some access functions that
    more easily allows for direct and indirect access to the tape
    */
    fun opcode1(index: Int): Int {
        println("Executing Opcode 1: ${tape.get(index + 1)} + ${tape.get(index + 2)} -> [${index + 3}]")
        tape.set(tape.get(index + 3), tape.get(tape.get(index + 1)) + tape.get(tape.get(index + 2)) )
        return index + 4
    }

    fun opcode2(index: Int): Int {
        println("Executing Opcode 2: ${tape.get(index + 1)} * ${tape.get(index + 2)} -> [${index + 3}]")
        tape.set(tape.get(index + 3), tape.get(tape.get(index + 1)) * tape.get(tape.get(index + 2)) )
        return index + 4
    }
}
