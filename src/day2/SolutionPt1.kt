package day2

fun main(vararg args: String) {
    var line = readLine()

    var twoCount = 0
    var threeCount = 0

    while (line != null && !line.isEmpty()) {
        var twoCounted = false
        var threeCounted = false
        var countMap = HashMap<Char, Int>()
        for (i in 0 until line.length) {
            if (!countMap.containsKey(line[i])) {
                countMap[line[i]] = 1
            } else {
                countMap[line[i]] = countMap[line[i]]!! + 1
            }
        }
        for ((_, value) in countMap) {
            if (value == 2 && !twoCounted) {
                twoCount++
                twoCounted = true
            }
            if (value == 3 && !threeCounted) {
                threeCount++
                threeCounted = true
            }
        }
        line = readLine()
    }
    println(twoCount * threeCount)
}
