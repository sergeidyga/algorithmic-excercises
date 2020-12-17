class ConsoleHelper(private val tnService: TriangularNumberService) {

    fun run() {
        do {
            println("+--------------------------------+\n" +
                    "|        Triangular Number       |\n" +
                    "+--------------------------------+\n" +
                    "Select option:\n" +
                    "   [1] Get sum and divisors of Nth Triangular Number\n" +
                    "   [2] Get first triangle number with divisors count >= N?\n" +
                    "   [Q] Quit"
            )
            when (readLine()) {
                "1" -> getSumAndDivisors()
                "2" -> getFirstTnWithDivisorsCount()
                "Q", "q" -> {
                    println("Quiting...")
                    return
                }
            }
        } while (true)
    }

    private fun getSumAndDivisors() {
        print("Give me the ordinal: ")
        val n = try {
            readLine()!!.toInt()
        } catch (nfe: NumberFormatException) {
            println("ERROR: ordinal must be integer!")
            return
        }
        if (n == 0) {
            println("ERROR: ordinal must be > 0")
            return
        }
        val resultString = tnService.getSumAndDivisors(n)
        tnService.saveResultsToOutputFile(n, true, resultString)
    }

    private fun getFirstTnWithDivisorsCount() {
        print("Give me the minimum divisors count: ")
        val n = try {
            readLine()!!.toInt()
        } catch (nfe: NumberFormatException) {
            println("ERROR: count must be integer!")
            return
        }
        val resultString = tnService.getFirstTnWithDivisorsCount(n)
        tnService.saveResultsToOutputFile(n, false, resultString)
    }

}