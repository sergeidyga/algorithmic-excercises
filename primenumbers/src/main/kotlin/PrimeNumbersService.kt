import com.github.freva.asciitable.AsciiTable
import com.google.gson.Gson
import java.io.File
import java.io.FileNotFoundException
import kotlin.math.sqrt
import kotlin.system.measureTimeMillis

class PrimeNumbersService(private val outputFileName: String) {

    private val outputFile = File(outputFileName)
    val existingResultsMap: MutableMap<String, Set<Int>> = readOutputFileAsMap()

    fun saveResultsToOutputFile() {
        println("Saving results to output file $outputFileName.")
        outputFile.writeText(Gson().toJson(existingResultsMap))
    }

    fun getPrimeFactors(forNumber: Int): Set<Int> {
        if (existingResultsMap.containsKey(forNumber.toString())) {
            val factors = existingResultsMap[forNumber.toString()]
            println(AsciiTable.getTable(arrayOf("Input", "Prime Factors", "Time Spent (ms)", "Loops Done"),
                arrayOf(arrayOf(forNumber.toString(), factors.toString(), "Not Spent", "No Loops"))))
            return factors!!
        } else {
            val factors = mutableSetOf<Int>()
            var loops = 0
            var divisor = 2
            var number = forNumber
            var i = 1 // Divisor index starting from 5
            var isPlus = false // Changing each iter starting from 5
            val time = measureTimeMillis {
                while (divisor <= sqrt(number.toDouble())) {
                    loops += 1
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
                factors.add(number)
            }
            println(AsciiTable.getTable(arrayOf("Input", "Prime Factors", "Time Spent (ms)", "Loops Done"),
                arrayOf(arrayOf(forNumber.toString(), factors.toString(), time.toString(), loops.toString()))))
            // Save to hashMap:
            existingResultsMap[forNumber.toString()] = factors
            return factors
        }
    }

    private fun readOutputFileAsMap(): MutableMap<String, Set<Int>> {
        return try {
            val initArray =
                Gson().fromJson(outputFile.readText(), HashMap::class.java) as HashMap<String, ArrayList<Int>>
            return initArray.map { it.key to it.value.toSet() }.toMap().toMutableMap()
        } catch (fnf: FileNotFoundException) {
            println("$outputFileName not found. Creating...")
            File(outputFileName).createNewFile()
            HashMap()
        }
    }

}