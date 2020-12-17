fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("ERROR: books filename command-line argument not provided!")
        return
    }
    val booksDb = BooksDb((args[0]))
    ConsoleHelper(booksDb).run()
}
