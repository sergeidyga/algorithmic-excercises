const val DIVISORS_AND_SUM_FILE_NAME = "divisors_and_sum_of_Nth_term.txt"
const val FIRST_NUMBER_WITH_N_DIV_FILE_NAME = "first_triangle_number_with_min_N_divisors.txt"

fun main() {
    val pnService = TriangularNumberService(DIVISORS_AND_SUM_FILE_NAME, FIRST_NUMBER_WITH_N_DIV_FILE_NAME)
    ConsoleHelper(pnService).run()
}