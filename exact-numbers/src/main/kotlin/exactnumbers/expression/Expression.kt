package exactnumbers.expression

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.ext.toExactFraction
import exactnumbers.irrationals.LogNum
import java.math.BigInteger

class Expression {
    val numbers: List<ExactFraction>
    val logs: List<LogNum>

    constructor(num: ExactFraction) {
        numbers = listOf(num)
        logs = listOf()
    }

    constructor(num: Int) {
        numbers = listOf(num.toExactFraction())
        logs = listOf()
    }

    constructor(numbers: List<ExactFraction>, logs: List<LogNum>) {
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
                    val logList: List<LogNum> = it.value

                    // add up coefficients for each log num value
                    val combinedLogs: LogNum =
                        logList.foldRight(LogNum(ExactFraction.ZERO, number)) { logNum: LogNum, acc: LogNum ->
                            val coeff = logNum.coefficient + acc.coefficient
                            LogNum(coeff, number)
                        }
                    combinedLogs
                }
                .ifEmpty { listOf(LogNum.ZERO) }
        }


        return Expression(newNumbers, newLogs)
    }
}
