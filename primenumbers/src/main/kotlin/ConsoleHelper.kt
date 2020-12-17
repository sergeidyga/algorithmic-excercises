import com.github.freva.asciitable.AsciiTable

class ConsoleHelper(private val pnService: PrimeNumbersService) {

    fun run() {

        do {
            println("+-------------------------------+\n" +
                    "|      Prime Factorization      |\n" +
                    "+-------------------------------+\n" +
                    "Select option:\n" +
                    "   [1] Get Prime Factor\n" +
                    "   [2] Print current cache\n" +
                    "   [Q] Save cache to database and exit the program"
            )
            when (readLine()) {
                "1" -> getPrimeFactor()
                "2" -> printCurrentCache()
                "Q", "q" -> {
                    pnService.saveResultsToOutputFile()
                    println("Quiting...")
                    return
                }
            }
        } while (true)
    }

    private fun getPrimeFactor() {
        print("Give me the number: ")
        pnService.getPrimeFactors(try {
            readLine()!!.toInt()
        } catch (nfe: NumberFormatException) {
            println("ERROR: number must be integer!")
            return
        })
        pnService.saveResultsToOutputFile()
    }

    private fun printCurrentCache() {
        val array = mutableListOf<Array<String>>()
        pnService.existingResultsMap.forEach {
            array.add(arrayOf(
                it.key.toString(),
                it.value.toString()))
        }
        println(AsciiTable.getTable(arrayOf("Input", "Prime Factors"), array.toTypedArray()))
    }

}