package expressions.term

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.logs.LogNum
import java.math.BigDecimal

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

        // TODO simplify logs
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
        val newCoefficient = coefficient / other.coefficient
        val newPiCount = this.piCount - other.piCount
        val newLogs = logs + other.logs.map { it.swapDivided() }
        return Term(newLogs, newPiCount, newCoefficient)
    }

    fun isZero(): Boolean = coefficient.isZero() || logs.any(LogNum::isZero)

    fun getSimplified(): Term {
        // TODO
        return this
    }

    fun getValue(): BigDecimal {
        // TODO
        return BigDecimal.ZERO
    }

    // TODO toString

    override fun hashCode(): Int = listOf("Term", logs, piCount, coefficient).hashCode()

    companion object {
        val ZERO = Term(listOf(), 0, ExactFraction.ZERO)
        val ONE = Term(listOf(), 0, ExactFraction.ONE)
    }
}
