package day2

import java.lang.StringBuilder

fun main(vararg args: String) {
    var ids = ArrayList<String>()
    var line = readLine()

    while (line != null && !line.isEmpty()) {
        ids.add(line)
        line = readLine()
    }

    commonLetters(ids)
}

fun commonLetters(ids: List<String>) {
    for (i in 0 until ids.size) {
        for (j in 0 until ids.size) {
            if (i != j) {
                // compare ids[i] to ids[j]
                val similarity = getSimilar(ids[i], ids[j])
                if (similarity != null) {
                    println(similarity)
                    return
                }
            }
        }
    }
}

// Returns a string iff the given two are similar
private fun getSimilar(string1: String, string2: String): String? {
    if (string1.length == string2.length) {
        var wildcardUsed = false
        var commonLetters = StringBuilder()
        for (i in 0 until string1.length) {
            if (string1[i] != string2[i]) {
                if (wildcardUsed) return null
                wildcardUsed = true
            } else {
                commonLetters.append(string1[i])
            }
        }
        return commonLetters.toString()
    }
    return null
}