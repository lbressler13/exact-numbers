package exactnumbers.expression

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.ext.toExactFraction
import exactnumbers.irrationals.logs.LogProduct

class Expression {
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

    // combine all exact fractions and combine log products with the same logs
    fun getSimplified(): Expression {
        val newNumbers = if (numbers.isEmpty()) {
            listOf()
        } else {
            listOf(numbers.fold(ExactFraction.ZERO, ExactFraction::plus))
        }

        val newLogs = when {
            logs.isEmpty() -> listOf()
            logs.all { it.isZero() } -> listOf(LogProduct.ZERO)
            else -> {
                logs.filterNot { it.isZero() }
                    .groupBy { it.logs }
                    .map {
                        val currentLogs = it.key
                        val products = it.value
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
