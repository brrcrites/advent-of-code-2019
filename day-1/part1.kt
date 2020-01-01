import java.io.File
import kotlin.math.floor

fun main(args: Array<String>) {
    if (args.size == 0) {
        println("Please provide the input file")
        return
    }

    var sum : Double = 0.0
    File(args[0]).useLines { lines -> lines.forEach{ sum += (floor(it.toDouble() / 3) - 2) } }
    println("Total necessary fuel: ${sum.toInt()}")
}
