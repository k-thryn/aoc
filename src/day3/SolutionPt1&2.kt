package day3

fun main(vararg args: String) {
    val lines = ArrayList<String>()
    var line = readLine()

    while (line != null && !line.isEmpty()) {
        lines.add(line)
        line = readLine()
    }

    overLappingInches(lines)
}

fun overLappingInches(lines: List<String>) {
    val canvas = Canvas()
    for (line in lines) {
        val splitLine = line.split("@|:".toRegex())
        val leftTop = splitLine[1].split(",")
        val widthHeight = splitLine[2].split("x")

        val id = splitLine[0].substring(1).trim().toInt()
        val fromLeft = leftTop[0].trim().toInt()
        val fromTop = leftTop[1].trim().toInt()
        val width = widthHeight[0].trim().toInt()
        val height = widthHeight[1].trim().toInt()

        canvas.addHit(id, fromLeft, fromTop, width, height)
    }


    println(canvas.multipleHits.size)
    println(canvas.findCleanRectangle())
}

// Pair<x, y>
class Canvas {
    var oneHit = HashSet<Pair<Int, Int>>()
    var multipleHits = HashSet<Pair<Int, Int>>()
    var rectangles = HashMap<Int, HashSet<Pair<Int, Int>>>() // <id, rectangle>

    fun addHit(id: Int, fromLeft: Int, fromTop: Int, width: Int, height: Int) {
        var currentRectangle = HashSet<Pair<Int, Int>>()
        for (x in fromTop until fromTop + height) {
            for (y in fromLeft until fromLeft + width) {
                val pair = Pair(x, y)
                currentRectangle.add(pair)
                if (!multipleHits.contains(pair)) {
                    if (!oneHit.contains(pair)) {
                        oneHit.add(pair)
                    } else {
                        oneHit.remove(pair)
                        multipleHits.add(pair)
                    }
                }
            }
        }
        rectangles[id] = currentRectangle
    }

    fun findCleanRectangle(): Int {
        var isClean = true
        for ((id, rectangle) in rectangles) {
            for (point in rectangle) {
                if (multipleHits.contains(point)) {
                    isClean = false
                    break
                }
            }
            if (isClean) return id
            isClean = true
        }
        return -1 // not found
    }
}