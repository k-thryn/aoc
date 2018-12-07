package day4

import java.util.*


fun main(vararg args: String) {
    // Read from stdin.
    var line = readLine()
    var lines = ArrayList<String>()
    while (line != null && !line.isEmpty()) {
        lines.add(line)
        line = readLine()
    }
    if (args.isNotEmpty() && args[0] == "strategy1") {
        findSleepiestGuard(lines)
    } else {
        findSleepiestMinute(lines)
    }
}

fun findSleepiestGuard(lines: List<String>) {
    val entries = parseEntries(lines)
    val guards = processGuards(entries)

    var longestSleeping = Pair(0, Guard()) // (id, Guard)
    for (id in guards.keys) {
        if (guards[id]!!.minutesAsleep > longestSleeping.second.minutesAsleep) {
            longestSleeping = Pair(id, guards[id]!!)
        }
    }
    println(longestSleeping.first * longestSleeping.second.sleepiestMinute())
}

fun findSleepiestMinute(lines: List<String>) {
    val entries = parseEntries(lines)
    val guards = processGuards(entries)

    var sleepiestGuard = 0 //id
    var maxFrequency = 0
    for ((id, guard) in guards) {
        var sleepiestMinuteFreq = guard.sleepiestMinute(true)
        if (sleepiestMinuteFreq > maxFrequency) {
            maxFrequency = sleepiestMinuteFreq
            sleepiestGuard = id
        }
    }

    println(sleepiestGuard * guards[sleepiestGuard]!!.sleepiestMinute())

}

private fun parseEntries(lines: List<String>): List<Entry> {
    val entries = ArrayList<Entry>()
    for (line in lines) {
        val timeNote = line.split("] ".toRegex())
        val entry = Entry(timeNote[0].substring(1), timeNote[1])
        entries.add(entry)
    }

    entries.sort()
    return entries
}

private fun processGuards(entries: List<Entry>): Map<Int, Guard> { // <Id, Guard>
    val guards = HashMap<Int, Guard>() // Id, Guard
    var currentId: Int = -1 // guards must have positive ids
    var sleepingSince: Calendar? = null

    for (entry in entries) {
        when {
            entry.status == EntryType.START_SHIFT -> {
                currentId = entry.note.split(" ")[1].substring(1).toInt()
                if (currentId >= 0 && !guards.containsKey(currentId)) {
                    guards[currentId] = Guard()
                }
            }
            entry.status == EntryType.FALL_ASLEEP -> {
                sleepingSince = entry.timeStamp
            }
            entry.status == EntryType.WAKE_UP -> {
                guards[currentId]!!.addMinutes(entry, sleepingSince!!)
                sleepingSince = null
            }
        }
    }
    return guards
}

class Guard {
    var minutesAsleep = 0
    var sleepyMinutes = HashMap<Int, Int>() // <Minute, Frequency>

    fun addMinutes(current: Entry, sleepingSince: Calendar) {
        minutesAsleep += (current.minutesSince(sleepingSince))
        while (sleepingSince < current.timeStamp) {
            val minute = sleepingSince.get(Calendar.MINUTE)
            if (!sleepyMinutes.contains(minute)) {
                sleepyMinutes[minute] = 1
            } else {
                sleepyMinutes[minute] = sleepyMinutes[minute]!! + 1
            }
            sleepingSince.add(Calendar.MINUTE, 1)
        }
    }

    fun sleepiestMinute(returnFrequency: Boolean = false): Int {
        var currentMax = 0
        var maxMinute = 0
        var maxFrequency = 0
        for ((minute, frequency) in sleepyMinutes) {
            if (frequency > currentMax) {
                currentMax = frequency
                maxMinute = minute
                maxFrequency = frequency
            }
        }
        if (returnFrequency) {
            return maxFrequency
        }
        return maxMinute
    }
}

class Entry(timeString: String, note: String) : Comparable<Entry> {
    val note = note
    var timeStamp = Calendar.getInstance()!!
    init {
        val dateTime = timeString.split(" ")
        val date = dateTime[0].split("-")
        val time = dateTime[1].split(":")

        timeStamp.set(Calendar.YEAR, date[0].toInt())
        timeStamp.set(Calendar.MONTH, date[1].toInt() - 1) // JAVA CALENDAR MONTHS ARE 0-INDEXED. LET THIS BE A LESSON
        timeStamp.set(Calendar.DAY_OF_MONTH, date[2].toInt())
        timeStamp.set(Calendar.HOUR_OF_DAY, time[0].toInt())
        timeStamp.set(Calendar.MINUTE, time[1].toInt())
    }
    val status = parseNote(note)

    fun minutesSince(other: Calendar): Int {
        return ((this.timeStamp.timeInMillis - other.timeInMillis) / 60000).toInt()
    }


    private fun parseNote(note: String): EntryType {
        return when {
            note.contains("begins shift") -> EntryType.START_SHIFT
            note == "falls asleep" -> EntryType.FALL_ASLEEP
            else -> EntryType.WAKE_UP
        }
    }

    override fun compareTo(other: Entry): Int {
        return timeStamp.compareTo(other.timeStamp)
    }

}

enum class EntryType { START_SHIFT, FALL_ASLEEP, WAKE_UP }