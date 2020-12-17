data class Book(var bookName: String? = "",
                var writerName: String? = "",
                var bookISBN: String? = "",
                var publishingYear: Int = 0) : Comparable<Book> {

    fun asArray() = arrayOf(bookName, writerName, bookISBN, publishingYear.toString())

    override operator fun compareTo(other: Book) = compareValuesBy(this, other,
        { it.publishingYear },
        { it.bookName },
        { it.writerName },
        { it.bookISBN }
    )

    override fun toString(): String {
        return "$bookName,$writerName,$bookISBN,$publishingYear"
    }
}