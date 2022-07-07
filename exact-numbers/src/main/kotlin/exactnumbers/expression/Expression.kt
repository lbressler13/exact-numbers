package exactnumbers.expression

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.ext.toExactFraction
import exactnumbers.irrationals.logs.LogProduct

/**
 * Representation of the sum of numbers, which can be both rational and irrational numbers
 */
internal class Expression {
    val numbers: List<ExactFraction>
    val logs: List<LogProduct>

    constructor(num: ExactFraction) {
        numbers = listOf(num)
        logs = listOf()
    }

    constructor(num: Int) {
        numbers = listOf(num.toExactFraction())
        logs = listOf()
    }

    constructor(numbers: List<ExactFraction>, logs: List<LogProduct>) {
        if (numbers.isEmpty() && logs.isEmpty()) {
            throw Exception("Expression must contain at least one value")
        }
        this.numbers = numbers
        this.logs = logs
    }

    operator fun plus(other: Expression): Expression {
        val newNumbers = numbers + other.numbers
        val newLogs = logs + other.logs
        return Expression(newNumbers, newLogs)
    }

    operator fun minus(other: Expression): Expression {
        val newNumbers = numbers + other.numbers.map { -it }
        val newLogs = logs + other.logs.map { -it }
        return Expression(newNumbers, newLogs)
    }

    /**
     * Get simplified version of expression by combining numbers of each type when possible.
     * Does not compute values of irrational numbers.
     *
     * @return [Expression]: simplified expression
     */
    fun getSimplified(): Expression {
        // add up exact fractions
        val newNumbers = if (numbers.isEmpty()) {
            listOf()
        } else {
            listOf(numbers.fold(ExactFraction.ZERO, ExactFraction::plus))
        }

        // combine log products with the same lists of logs
        val newLogs = when {
            logs.isEmpty() -> listOf()
            logs.all { it.isZero() } -> listOf(LogProduct.ZERO)
            else -> {
                logs.filterNot { it.isZero() }
                    .groupBy { it.logs }
                    .map {
                        val currentLogs = it.key
                        val products = it.value

                        // get sum of coefficients for this set of products
                        val combinedProducts: LogProduct =
                            products.fold(LogProduct.ZERO) { acc, product ->
                                val coeff = product.coefficient + acc.coefficient
                                LogProduct(currentLogs, coeff)
                            }
                        combinedProducts
                    }
            }
        }

        return Expression(newNumbers, newLogs)
    }

    override fun toString(): String {
        val numbersString = numbers.joinToString("+")
        val logsString = logs.joinToString("+")
        return when {
            numbers.isEmpty() -> logsString
            logs.isEmpty() -> numbersString
            else -> "$numbersString+$logsString"
        }
    }

    companion object {
        val ZERO = Expression(0)
    }
}
