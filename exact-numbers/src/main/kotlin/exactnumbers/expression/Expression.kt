package exactnumbers.expression

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.ext.toExactFraction
import exactnumbers.irrationals.LogNum

internal class Expression {
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
}
