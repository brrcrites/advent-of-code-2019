import java.io.File
import kotlin.math.floor

fun calculate_fuel(mass: Double): Double {
    var fuel: Double = floor(mass / 3) - 2
    var fuel_for_fuel: Double = floor(fuel / 3) - 2
    while(fuel_for_fuel > 0) {
        fuel += fuel_for_fuel
        fuel_for_fuel = floor(fuel_for_fuel / 3) - 2
    }
    return fuel
}

fun main(args: Array<String>) {
    if (args.size == 0) {
        println("Please provide the input file")
        return
    }

    var total_fuel: Double = 0.0
    File(args[0]).useLines { lines -> lines.forEach{ total_fuel += calculate_fuel(it.toDouble()) } }

    println("Total necessary fuel: ${total_fuel.toInt()}")
}
