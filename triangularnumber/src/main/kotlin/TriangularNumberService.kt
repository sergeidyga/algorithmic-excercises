import com.github.freva.asciitable.AsciiTable
import java.io.File
import java.util.*
import kotlin.math.sqrt

class TriangularNumberService(private val opt1FileName: String, private val opt2FileName: String) {

    private fun getOption1File(n: Int) = File(opt1FileName.replace("N", n.toString()))
    private fun getOption2File(n: Int) = File(opt2FileName.replace("N", n.toString()))

    fun saveResultsToOutputFile(n: Int, isOption1: Boolean, result: String) {
        val file = if (isOption1) getOption1File(n) else getOption2File(n)
        println("Saving results to output file ${file.name}.")
        file.writeText(result)
    }

    fun getSumAndDivisors(n: Int): String {
        val tn = n * (n + 1) / 2
        val (divisors, loops) = getDivisors(tn)
        val resultString = "$tn: ${divisors.joinToString(" ")}"
        println(AsciiTable.getTable(arrayOf("Sum (Tn)", "Divisors", "Loops"),
            arrayOf(arrayOf("$tn", divisors.joinToString(" "), loops.toString()))))
        return resultString

    }

    private fun getDivisors(tn: Int): Pair<TreeSet<Int>, Int> {
        var divisor = 2
        val divisors = TreeSet<Int>().also {
            it.add(1)
            it.add(tn)
        }
        var latestDivisionResult = tn
        var loops = 0
        while (divisor < latestDivisionResult) {
            loops += 1
            if (tn % divisor == 0) {
                divisors.add(divisor)
                latestDivisionResult = tn / divisor
                divisors.add(latestDivisionResult)
            }
            divisor += 1
        }
        return Pair(divisors, loops)
    }

    fun getFirstTnWithDivisorsCount(minDivisorsCount: Int): String {
        var n = 1
        var tn = 0
        var divisorsCount = 0
        var loops = 0
        while (divisorsCount < minDivisorsCount) {
            loops += 1
            tn = n * (n + 1) / 2
            divisorsCount = countDivisors(tn)
            n += 1
        }
        val divisors = getDivisors(tn).first
        val resultString = "The triangle number is $tn and divisors are $divisors"
        println(AsciiTable.getTable(arrayOf("Tn", "Divisors", "Min Count", "Actual Count", "Loops"),
            arrayOf(arrayOf("$tn",
                divisors.joinToString(", "),
                "$minDivisorsCount",
                "${divisors.size}",
                loops.toString()))))
        return resultString
    }

    private fun countDivisors(forNumber: Int): Int {
        if (forNumber == 1) return 1
        val factors = mutableListOf<Int>()
        var divisor = 2
        var number = forNumber
        var i = 1 // Divisor index starting from 5
        var isPlus = false // Changing each iter starting from 5
        while (divisor <= sqrt(number.toDouble())) {
            val remainder = number % divisor
            if (remainder <= 0) {
                // Evenly divided, saving factor:
                number /= divisor
                factors.add(divisor)
            } else {
                // Not evenly divided, increasing divisor:
                divisor = when (divisor) {
                    2 -> divisor + 1
                    3 -> divisor + 2
                    else -> {
                        // Excluding numbers evenly divided to 2 or 3
                        if (!isPlus) {
                            isPlus = true
                            6 * i - 1
                        } else {
                            isPlus = false
                            val div = 6 * i + 1
                            i += 1
                            div
                        }
                    }
                }
            }
        }
        factors.add(number) // Last divisor is number/number
        return factors.groupingBy { it }.eachCount().values
            .map { it + 1 }
            .reduce { acc, count -> acc * count }
    }

}