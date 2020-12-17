/**
 * args[0] - inputFile
 * args[1] - outputFile
 * args[2] - (optional) 'skipPrintingOutput' flag
 */
fun main(args: Array<String>) {
    if (args.isEmpty() || args[0].isEmpty()) {
        println("ERROR: input filename command-line argument not provided!")
        return
    }
    if (args.size < 2 || args[1].isEmpty()) {
        println("ERROR: output filename command-line argument not provided!")
        return
    }
    val numbersService = NumbersService(args[0], args[1])
    numbersService.writeMultiplesToOutputFile()
    // For large files - printing to stdout is slow and can be disabled:
    if (args.size < 3 || args[2] != "skipPrintingOutput") numbersService.printOutputFile()
}
