package day1

class SolutionPt2 {
    companion object {
        /**
         * Assuming well-formed, non-empty number input.
         */
        @JvmStatic fun main(vararg args: String) {
            var sum = 0
            var sums = HashSet<Int>()
            var nums = ArrayList<Int>()

            var line = readLine()
            while (line != null && !line.isEmpty()) {
                nums.add(line!!.toInt())
                line = readLine()
            }

            var i = 0
            while (!sums.contains(sum)) {
                sums.add(sum)
                sum += nums[i % nums.size]
                i++
            }
            println(sum)
        }
    }
}