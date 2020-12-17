import java.util.*

data class NumberRow(var a: Int,
                     var b: Int,
                     var goal: Int,
                     var output: SortedSet<Int> = sortedSetOf()) : Comparable<NumberRow> {

    override operator fun compareTo(other: NumberRow) = compareValuesBy(this, other,
        { it.output.size },
        { it.goal },
        { it.a },
        { it.b }
    )

    override fun toString(): String {
        return "$goal:${output.joinToString(" ")}"
    }
}