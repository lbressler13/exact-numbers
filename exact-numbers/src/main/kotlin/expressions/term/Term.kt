package expressions.term

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.logs.LogNum
import exactnumbers.irrationals.logs.simplifyLogsList
import exactnumbers.irrationals.pi.PiNum
import utils.divideBigDecimals
import utils.throwDivideByZero
import java.math.BigDecimal
import java.math.BigInteger

class Term(logs: List<LogNum>, piCount: Int, coefficient: ExactFraction) {
    val logs: List<LogNum>
    val piCount: Int
    val coefficient: ExactFraction

    init {
        if (coefficient.isZero() || logs.any(LogNum::isZero)) {
            this.logs = listOf()
            this.piCount = 0
            this.coefficient = ExactFraction.ZERO
        } else {
            this.logs = logs
            this.piCount = piCount
            this.coefficient = coefficient
        }
    }

    operator fun unaryMinus(): Term = Term(logs, piCount, -coefficient)
    operator fun unaryPlus(): Term = this

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Term) {
            return false
        }

        val simplified = getSimplified()
        val otherSimplified = other.getSimplified()

        return simplified.logs.sorted() == otherSimplified.logs.sorted() &&
            simplified.piCount == otherSimplified.piCount &&
            simplified.coefficient == otherSimplified.coefficient
    }

    operator fun times(other: Term): Term {
        val newLogs = logs + other.logs
        val newPiCount = piCount + other.piCount
        val newCoefficient = coefficient * other.coefficient
        return Term(newLogs, newPiCount, newCoefficient)
    }

    operator fun div(other: Term): Term {
        if (other.isZero()) {
            throwDivideByZero()
        }

        val newCoefficient = coefficient / other.coefficient
        val newPiCount = this.piCount - other.piCount
        val newLogs = logs + other.logs.map { it.swapDivided() }
        return Term(newLogs, newPiCount, newCoefficient)
    }

    fun isZero(): Boolean = coefficient.isZero() || logs.any(LogNum::isZero)

    fun getSimplified(): Term {
        val newLogs = simplifyLogsList(logs)
        return Term(newLogs, piCount, coefficient)
    }

    fun getValue(): BigDecimal {
        val simplified = getSimplified()

        val piValue = PiNum().getValue().pow(simplified.piCount)
        val logsValue = simplified.logs.fold(BigDecimal.ONE) { acc, log ->
            acc * log.getValue()
        }

        val numeratorProduct = piValue * logsValue * coefficient.numerator.toBigDecimal()

        return divideBigDecimals(numeratorProduct, coefficient.denominator.toBigDecimal())
    }

    override fun toString(): String {
        val coeffString = if (coefficient.denominator == BigInteger.ONE) {
            coefficient.numerator.toString()
        } else {
            "${coefficient.numerator}/${coefficient.denominator}"
        }

        var logsString = logs.joinToString("x")
        if (logsString.isNotEmpty()) {
            logsString = "x$logsString"
        }

        val piString = "${PiNum()}^$piCount"

        return "${coeffString}x${piString}$logsString"
    }

    override fun hashCode(): Int = listOf("Term", logs, piCount, coefficient).hashCode()

    companion object {
        val ZERO = Term(listOf(), 0, ExactFraction.ZERO)
        val ONE = Term(listOf(), 0, ExactFraction.ONE)
    }
}
