import java.io.File
import java.util.*
import java.util.stream.Collectors

class BooksDb(private val fileName: String) {

    private val file = File(fileName)
    private var books = readBooksFromFile()
    private var lastModified = file.lastModified()

    fun getBooks(): Set<Book> {
        if (file.lastModified() != lastModified) {
            readBooksFromFile()
        }
        lastModified = file.lastModified()
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
        file.writeText(books.joinToString("\n"))
        return books
    }

}