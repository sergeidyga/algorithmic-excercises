import java.io.File
import java.util.*
import java.util.stream.Collectors

class NumbersService(private val inputFileName: String, private val outputFileName: String) {

    private val inputFile = File(inputFileName)
    private val outputFile = File(outputFileName)
    private var numbers = readInputFile()

    fun printOutputFile() {
        println("+--------------------------------+\n" +
                "|  Printing output file content  |\n" +
                "+--------------------------------+")
        outputFile.bufferedReader().lines().forEach {
            println(it)
        }
    }

    fun writeMultiplesToOutputFile() {
        numbers.parallelStream()
            .forEach { row ->
                listOf(row.a, row.b).forEach {
                    // Calculate multiples for A and B below Goal
                    var input = it
                    while (input < row.goal) {
                        row.output.add(input)
                        input += it
                    }
                }
            }
        outputFile.writeText(numbers.joinToString("\n"))
    }

    private fun readInputFile(): TreeSet<NumberRow> {
        return inputFile.bufferedReader().lines()
            .map {
                val split = it.split(" ")
                NumberRow(split[0].toInt(), split[1].toInt(), split[2].toInt())
            }
            .collect(Collectors.toCollection { TreeSet() })
    }

}