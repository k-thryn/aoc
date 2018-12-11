package day5

import java.util.*

fun main(vararg args: String) {
    val polymer = readLine()
    if (polymer != null) {
        longPolymer(polymer)
    }
}

fun longPolymer(polymer: String) {
    var seen = Stack<Char>()
    for (i in 0 until polymer.length) {
        if (!seen.isEmpty() && isOppositePolarity(seen.peek(), polymer[i])) {
            seen.pop()
            continue
        }
        seen.push(polymer[i])
    }
    println(seen.size)
}

fun isOppositePolarity(a: Char, b: Char): Boolean {
    return a != b && (a.toUpperCase() == b || b.toUpperCase() == a)
}