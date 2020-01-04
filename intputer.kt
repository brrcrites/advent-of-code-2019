
package aoc.intputer

data class OpWord(var opcode: Int, var modes: MutableList<Int>)

class Intputer(input: String) {
    var tape: MutableList<Int>
    // TODO(brrcrites): Look into loggers for kotlin and replace this with a logger
    var debug: Boolean = false

    init {
        tape = input.trim().split(",").map{ it.toInt() }.toMutableList()
    }

    val original_tape: List<Int> = tape.toList()

    override fun toString(): String {
        // TODO(brrcrites): I feel like this would be easy to convert into a functional programming line
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
        var sigex_opword: String = opword.toString()
        // Extend the opword that was input with preceding zeroes to get it to the correct length
        while (sigex_opword.length < 5) {
            sigex_opword = "0" + sigex_opword
        }

        var ret: OpWord = OpWord(0, MutableList<Int>(0, { 0 }))
        // Seperate the opcode and the mode bits into a structure that easier for the other functions to process
        ret.opcode = sigex_opword.substring(sigex_opword.length - 2, sigex_opword.length).toInt()
        for (mode in sigex_opword.substring(0, sigex_opword.length - 2).reversed()) {
            // Since this substring is 1 char the toInt converts to the ascii value not the numeric value so we
            // convert it to a string first which will correctly convert to a numeric value
            ret.modes.add(mode.toString().toInt())
        }
        return ret
    }

    // We should probably come up with a better way to conver the tape into something that is easier to process and build
    fun process_tape() {
        var tape_index: Int = 0
        tape_exec@ while (tape_index >= 0 && tape_index < tape.size) {
            if (debug) {
                println("@${tape_index}")
            }
            val (opcode, mode_array) = process_opword(tape.get(tape_index))
            if (opcode == 99) { break }
            when (opcode) {
                1 -> tape_index = opcode1(mode_array, tape_index)
                2 -> tape_index = opcode2(mode_array, tape_index)
                3 -> tape_index = opcode3(mode_array, tape_index)
                4 -> tape_index = opcode4(mode_array, tape_index)
                5 -> tape_index = opcode5(mode_array, tape_index)
                6 -> tape_index = opcode6(mode_array, tape_index)
                7 -> tape_index = opcode7(mode_array, tape_index)
                8 -> tape_index = opcode8(mode_array, tape_index)
                else -> break@tape_exec
            }
        }
        if (tape.get(tape_index) != 99) { 
            println("Failed on invalid opcode ${tape.get(tape_index)} at index ${tape_index}")
        }
    }

    /*
    The case statement above is good and I like having the function set the new index position but I'm not wild about how the tape is being
    referenced here. We should probably add some access functions that more easily allows for direct and indirect access to the tape
    */
    fun opcode1(modes: List<Int>, index: Int): Int {
        if(debug) {
            println("Executing Opcode 1: Addition with modes ${modes}")
        }

        var op1: Int
        when (modes.get(0)) {
            0 -> op1 = tape.get(tape.get(index + 1))
            1 -> op1 = tape.get(index + 1)
            else -> return -1
        }
        var op2: Int
        when (modes.get(1)) {
            0 -> op2 = tape.get(tape.get(index + 2))
            1 -> op2 = tape.get(index + 2)
            else -> return -1
        }

        tape.set(tape.get(index + 3), op1 + op2)
        return index + 4
    }

    fun opcode2(modes: List<Int>, index: Int): Int {
        if(debug) {
            println("Executing Opcode 2: Multiplication with modes ${modes}")
        }

        var op1: Int
        when (modes.get(0)) {
            0 -> op1 = tape.get(tape.get(index + 1))
            1 -> op1 = tape.get(index + 1)
            else -> return -1
        }
        var op2: Int
        when (modes.get(1)) {
            0 -> op2 = tape.get(tape.get(index + 2))
            1 -> op2 = tape.get(index + 2)
            else -> return -1
        }

        tape.set(tape.get(index + 3), op1 * op2)
        return index + 4
    }

    fun opcode3(modes: List<Int>, index: Int): Int {
        if(debug) {
            println("Executing Opcode 3: saving value to position ${tape.get(index+1)}")
        }

        print("Input: ")
        val user_input = readLine()!!.toInt()

        tape.set(tape.get(index + 1), user_input)
        return index + 2
    }

    fun opcode4(modes: List<Int>, index: Int): Int {
        if(debug) {
            println("Executing Opcode 4: outputting value with modes ${modes}")
        }
        when (modes.get(0)) {
            0 -> println("Output: ${tape.get(tape.get(index + 1))}")
            1 -> println("Output: ${tape.get(index + 1)}")
            else -> return -1
        }
        return index + 2
    }

    fun opcode5(modes: List<Int>, index: Int): Int {
        if (debug) {
            println("Executing Opcode 5: jump-if-true")
        }

        var ip: Int
        when (modes.get(1)) {
            0 -> ip = tape.get(tape.get(index + 2))
            1 -> ip = tape.get(index + 2)
            else -> return -1
        }
        var comp: Int
        when (modes.get(0)) {
            0 -> comp = tape.get(tape.get(index + 1))
            1 -> comp = tape.get(index + 1)
            else -> return -1
        }
        if (comp != 0) {
            if (debug) {
                println("Value ${comp} is true, jumping to ${ip}")
            }
            return ip
        }
        if (debug) {
            println("Value ${comp} is false, continuing to ${index + 3}")
        }
        return index + 3
    }

    fun opcode6(modes: List<Int>, index: Int): Int {
        if (debug) {
            println("Executing Opcode 6: jump-if-false")
        }

        var ip: Int
        when (modes.get(1)) {
            0 -> ip = tape.get(tape.get(index + 2))
            1 -> ip = tape.get(index + 2)
            else -> return -1
        }
        var comp: Int
        when (modes.get(0)) {
            0 -> comp = tape.get(tape.get(index + 1))
            1 -> comp = tape.get(index + 1)
            else -> return -1
        }
        if (comp == 0) {
            if (debug) {
                println("Value ${comp} is false, jumping to ${ip}")
            }
            return ip
        }
        if (debug) {
            println("Value ${comp} is true, continuing to ${index + 3}")
        }
        return index + 3
    }

    fun opcode7(modes: List<Int>, index: Int): Int {
        if (debug) {
            println("Executing Opcode 7: less than")
        }

        var op1: Int
        when (modes.get(0)) {
            0 -> op1 = tape.get(tape.get(index + 1))
            1 -> op1 = tape.get(index + 1)
            else -> return -1
        }
        var op2: Int
        when (modes.get(1)) {
            0 -> op2 = tape.get(tape.get(index + 2))
            1 -> op2 = tape.get(index + 2)
            else -> return -1
        }
        if (op1 < op2) {
            if (debug) {
                println("Value of ${op1} is less than value of ${op2}, setting ${tape.get(tape.get(index + 3))} to 1")
            }
            tape.set(tape.get(index + 3), 1)
        } else {
            if (debug) {
                println("Value of ${op1} is not less than value of ${op2}, setting ${tape.get(tape.get(index + 3))} to 0")
            }
            tape.set(tape.get(index + 3), 0)
        }
        
        return index + 4
    }

    fun opcode8(modes: List<Int>, index: Int): Int {
        if (debug) {
            println("Executing Opcode 8: equals")
        }

        // TODO(brrcrites): The setting of these immediate vs. positional values feels very
        // non-DRY at this point and is probably in need of a refactor
        var op1: Int
        when (modes.get(0)) {
            0 -> op1 = tape.get(tape.get(index + 1))
            1 -> op1 = tape.get(index + 1)
            else -> return -1
        }
        var op2: Int
        when (modes.get(1)) {
            0 -> op2 = tape.get(tape.get(index + 2))
            1 -> op2 = tape.get(index + 2)
            else -> return -1
        }
        if (op1 == op2) {
            if (debug) {
                println("Value of ${op1} matches value of ${op2}, setting ${tape.get(tape.get(index + 3))} to 1")
            }
            tape.set(tape.get(index + 3), 1)
        } else {
            if (debug) {
                println("Value of ${op1} does not match value of ${op2}, setting ${tape.get(tape.get(index + 3))} to 0")
            }
            tape.set(tape.get(index + 3), 0)
        }

        return index + 4
    }
}
