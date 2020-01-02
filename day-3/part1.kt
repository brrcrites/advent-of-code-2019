import java.io.File
import kotlin.math.abs

class Point(val x: Int = 0, val y: Int = 0) { }

fun main(args: Array<String>) {
    if (args.size == 0) {
        println("Please input the file path to input as a command line argument")
        return
    }

    // Convert the input into two lines of movements
    val lines = File(args[0]).readLines()
    var wires: MutableList<List<String>> = MutableList(0, { List<String>(0, {""}) })
    for (line in lines) {
        wires.add(line.trim().split(","))
    }

    // Convert movements into two sets of points
    var points: MutableList<MutableList<Point>> = MutableList(2, { MutableList<Point>(1, { Point(0,0) }) })
    for ((index,wire) in wires.withIndex()) {
        for (movement in wire) {
            val last_point: Point = points.get(index).last()
            when(movement.substring(0,1)) {
                "U" -> for (offset in 1..(movement.substring(1).toInt())) { points.get(index).add(Point(last_point.x, last_point.y - offset)) }
                "D" -> for (offset in 1..(movement.substring(1).toInt())) { points.get(index).add(Point(last_point.x, last_point.y + offset)) }
                "L" -> for (offset in 1..(movement.substring(1).toInt())) { points.get(index).add(Point(last_point.x - offset, last_point.y)) }
                "R" -> for (offset in 1..(movement.substring(1).toInt())) { points.get(index).add(Point(last_point.x + offset, last_point.y)) }
            }
        }
    }

    // Find the set of points that the two sets share
    // TODO(brrcrites): figure out why the 'intersect' functionality doesn't work with the Point class
    var point_intersection: MutableList<Point> = MutableList(0, { Point(0,0) })
    for (p1 in points.get(0)) {
        for (p2 in points.get(1)) {
            if (p1.x == p2.x && p1.y == p2.y) {
                if(p1.x != 0 || p1.y != 0) {
                    point_intersection.add(Point(p1.x, p1.y))
                }
            }
        }
    }

    // Calculate manhattan distances and find the minimum to print
    var distance: Double = Double.POSITIVE_INFINITY
    for (point in point_intersection) {
        println("Processing (${point.x},${point.y})")
        if ((abs(point.x) + abs(point.y)) < distance) {
            distance = (abs(point.x) + abs(point.y)).toDouble()
        }
    }
    println("Manhattan distance to the closest intersection is ${distance}")
}
