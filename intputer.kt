
package aoc.intputer

data class OpWord(var opcode: Int, var modes: MutableList<Int>)

class Intputer(input: String) {
    var tape: MutableList<Int>
    var debug: Boolean = false

    init {
        tape = input.trim().split(",").map{ it.toInt() }.toMutableList()
    }

    val original_tape: List<Int> = tape.toList()

    override fun toString(): String {
        var ret_str: String = ""
        for ((index,value) in tape.withIndex()) {
            ret_str += "[${index}]: ${value}\n"
        }
        return ret_str
    }

    fun reset_tape() {
        tape = original_tape.toMutableList()
    }

    fun process_opword(opword: Int): OpWord {
        var sigex_opword: Int = opword
        // Extend the opword that was input with preceding zeroes to get it to the correct length
        while (sigex_opword.toString().length < 5) {
            sigex_opword = ("0" + sigex_opword.toString()).toInt()
        }

        var ret: OpWord = OpWord(0, MutableList<Int>(0, { 0 }))
        // Seperate the opcode and the mode bits into a structure that easier for the other functions to process
        ret.opcode = sigex_opword.toString().substring(sigex_opword.toString().length - 2, sigex_opword.toString().length).toInt()
        for (mode in sigex_opword.toString().substring(0, sigex_opword.toString().length - 2)) {
            ret.modes.add(mode.toInt())
        }
        return ret
    }

    /*
    We should probably come up with a better way to conver the tape
    into something that is easier to process and build
    */
    fun process_tape() {
        var tape_index: Int = 0
        val (opcode, mode_array) = process_opword(tape.get(tape_index))
        while (tape_index < tape.size && opcode != 99) {
            when (opcode) {
                1 -> tape_index = opcode1(mode_array, tape_index)
                2 -> tape_index = opcode2(mode_array, tape_index)
                3 -> tape_index = opcode3(mode_array, tape_index)
                4 -> tape_index = opcode4(mode_array, tape_index)
                else -> println("Encountered invalid opcode ${tape.get(tape_index)}")
            }
        }
    }

    /*
    The case statement above is good and I like having the function set 
    the new index position but I'm not wild about how the tape is being
    referenced here. We should probably add some access functions that
    more easily allows for direct and indirect access to the tape
    */
    fun opcode1(modes: List<Int>, index: Int): Int {
        if(debug) {
            println("Executing Opcode 1: ${tape.get(index + 1)} + ${tape.get(index + 2)} -> [${index + 3}]")
        }
        tape.set(tape.get(index + 3), tape.get(tape.get(index + 1)) + tape.get(tape.get(index + 2)) )
        return index + 4
    }

    fun opcode2(modes: List<Int>, index: Int): Int {
        if(debug) {
            println("Executing Opcode 2: ${tape.get(index + 1)} * ${tape.get(index + 2)} -> [${index + 3}]")
        }
        tape.set(tape.get(index + 3), tape.get(tape.get(index + 1)) * tape.get(tape.get(index + 2)) )
        return index + 4
    }

    fun opcode3(modes: List<Int>, index: Int): Int {
        if(debug) {
            println("Executing Opcode 3: saving value")
        }
        return index + 2
    }

    fun opcode4(modes: List<Int>, index: Int): Int {
        if(debug) {
            println("Executing Opcode 4: outputting value")
        }
        return index + 2
    }
}
