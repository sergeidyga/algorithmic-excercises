const val OUTPUT_FILE_NAME = "prime-numbers-output"

fun main() {
    val pnService = PrimeNumbersService(OUTPUT_FILE_NAME)
    ConsoleHelper(pnService).run()
}