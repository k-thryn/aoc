class Solution {
    companion object {
        /**
         * Assuming well-formed, non-empty number input.
         */
        @JvmStatic fun main(vararg args: String) {
            var sum = 0
            var line = readLine()
            while (line != null && !line.isEmpty()) {
                sum += line!!.toInt()
                line = readLine()
            }
            println(sum)
        }
    }
}