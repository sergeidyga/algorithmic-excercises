import com.github.freva.asciitable.AsciiTable

class ConsoleHelper(private val booksDb: BooksDb) {

    private val headers = arrayOf("bookName", "writerName", "bookISBN", "publishingYear")

    fun run() {
        do {
            println("+-------------------+\n" +
                    "| Library Main Menu |\n" +
                    "+-------------------+\n" +
                    "Select option:\n" +
                    "   [1] Add new book\n" +
                    "   [2] Print current database content in ascending order by publishing year\n" +
                    "   [Q] Exit the program"
            )
            when (readLine()) {
                "1" -> addNewBook()
                "2" -> printFileContent()
                "Q", "q" -> {
                    println("Quiting...")
                    return
                }
            }
        } while (true)
    }

    private fun addNewBook() {
        val book = Book()
        print("Enter book’s name: ")
        book.bookName = readLine()!!
        print("Enter writer’s name: ")
        book.writerName = readLine()
        print("Enter book’s ISBN: ")
        book.bookISBN = readLine()
        print("Enter publishing year: ")
        book.publishingYear = try {
            readLine()!!.toInt()
        } catch (nfe: NumberFormatException) {
            println("ERROR: publishing year must be integer!")
            return
        }
        printSingleBook(book)
        println("Add following book to database?\n" +
                "   [Y] Yes \n" +
                "   [N] No")
        when (readLine()) {
            "Y", "y" -> {
                booksDb.addBook(book)
                println("Book added!")
            }
        }
    }

    private fun printFileContent() {
        val table = AsciiTable.getTable(headers, booksDb.getBooks().map { it.asArray() }.toTypedArray())
        println(table)
    }

    private fun printSingleBook(book: Book) {
        val table = AsciiTable.getTable(headers, arrayOf(book.asArray()))
        println(table)
    }

}