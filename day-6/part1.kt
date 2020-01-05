
import java.io.File

class Node(val name: String) {
    var adj: MutableSet<Node> = mutableSetOf<Node>()
}

fun main(args: Array<String>) {
    if (args.size == 0) {
        println("Pass the puzzle file path as a parameter")
        return
    }

    var graph: MutableMap<String,Node> = mutableMapOf<String,Node>()

    for (line in File(args[0]).readLines()) {
        var parts = line.split(")")
        // TODO(brrcrites): Need to dig a bit deeper into the use of Node? in the map and ?. in this function
        graph.getOrPut(parts.get(1)) { Node(parts.get(1)) }.adj.add(graph.getOrPut(parts.get(0)) { Node(parts.get(0)) })
        graph.getOrPut(parts.get(0)) { Node(parts.get(0)) }.adj.add(graph.getOrPut(parts.get(1)) { Node(parts.get(1)) })
    }

    var done: MutableSet<Node> = mutableSetOf<Node>(graph.getOrElse("YOU") { return })
    var dist: MutableMap<Node, Int> = mutableMapOf<Node, Int>()
    var todo: MutableList<Node> = mutableListOf<Node>()
    var distance: Int = 1

    graph.getOrElse("YOU") { return }.adj.forEach { 
        value -> todo.add(value)
    }

    // Iterate over each element in todo
        // Label each element not in done the distance, add to done
    // Increment distance
    // For all neighbors of each one just processed not in done add to todo
    // continue until todo is empty
}
