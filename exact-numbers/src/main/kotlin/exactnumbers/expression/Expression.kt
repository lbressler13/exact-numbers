package exactnumbers.expression

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.ext.toExactFraction
import exactnumbers.irrationals.logs.LogNum
import exactnumbers.utils.LogList
import java.math.BigInteger

class Expression {
    val numbers: List<ExactFraction>
    val logs: LogList

    constructor(num: ExactFraction) {
        numbers = listOf(num)
        logs = listOf()
    }

    constructor(num: Int) {
        numbers = listOf(num.toExactFraction())
        logs = listOf()
    }

    constructor(numbers: List<ExactFraction>, logs: LogList) {
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

    // combine all exact fractions and simplify log by value of number
    fun getSimplified(): Expression {
        val newNumbers = if (numbers.isEmpty()) {
            listOf()
        } else {
            listOf(numbers.fold(ExactFraction.ZERO, ExactFraction::plus))
        }

        val newLogs = if (logs.isEmpty()) {
            listOf()
        } else {
            logs.filterNot { it.isZero() }
                .groupBy { it.number }
                .map {
                    val number: BigInteger = it.key
                    val logList: LogList = it.value

                    // TODO group by base to take advantage of product rule + quotient rule

                    // add up coefficients for each log num value
                    val combinedLogs: LogNum =
                        logList.foldRight(LogNum(ExactFraction.ZERO, number)) { acc, logNum ->
                            val coeff = logNum.coefficient + acc.coefficient
                            LogNum(coeff, number)
                        }
                    combinedLogs
                }
                .ifEmpty { listOf(LogNum.ZERO) }
        }

        return Expression(newNumbers, newLogs)
    }

    companion object {
        val ZERO = Expression(0)
    }
}
