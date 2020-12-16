import com.github.freva.asciitable.AsciiTable
import java.io.File
import java.util.*
import java.util.stream.Collectors

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("ERROR: books filename command-line argument not provided!")
        return
    }
    val booksDb = BooksDb((args[0]))
    ConsoleHelper(booksDb).run()
}

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
        println("Enter book’s name:")
        book.bookName = readLine()!!
        println("Enter writer’s name:")
        book.writerName = readLine()
        println("Enter book’s ISBN:")
        book.bookISBN = readLine()
        println("Enter publishing year:")
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

class BooksDb(private val fileName: String) {

    private val file = File(fileName)
    private var books = readBooksFromFile()
    private var lastModified = file.lastModified()

    fun getBooks(): Set<Book> {
        if (file.lastModified() != lastModified) {
            readBooksFromFile()
        }
        return books
    }

    fun addBook(book: Book) {
        books.add(book)
        File(fileName).writeText(books.joinToString("\n"))
    }

    private fun readBooksFromFile(): TreeSet<Book> {
        val books = file.bufferedReader().lines()
            .map {
                val split = it.split(",")
                Book(split[0], split[1], split[2], split[3].toInt())
            }
            .sorted()
            .collect(Collectors.toCollection { TreeSet<Book>() })
        // Update file with sorted results:
        File(fileName).writeText(books.joinToString("\n"))
        return books
    }

}

data class Book(var bookName: String? = "",
                var writerName: String? = "",
                var bookISBN: String? = "",
                var publishingYear: Int = 0) : Comparable<Book> {

    fun asArray() = arrayOf(bookName, writerName, bookISBN, publishingYear.toString())

    /**
     * Books are kept in alphabetical order by Year - BookName - Author,
     */
    override operator fun compareTo(other: Book) = compareValuesBy(this, other,
        { it.publishingYear },
        { it.bookName },
        { it.writerName }
    )

    override fun toString(): String {
        return "$bookName,$writerName,$bookISBN,$publishingYear"
    }
}