package expressions.term

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.logs.LogNum
import exactnumbers.irrationals.logs.simplifyLogsList
import exactnumbers.irrationals.pi.Pi
import exactnumbers.irrationals.pi.simplifyPisList
import shared.NumType
import shared.divideBigDecimals
import shared.throwDivideByZero
import java.math.BigDecimal
import java.math.BigInteger

class Term internal constructor(coefficient: ExactFraction, numbers: List<NumType>){
    val coefficient: ExactFraction
    internal val numbers: List<NumType>

    init {
        if (coefficient.isZero() || numbers.any { it.isZero() }) {
            this.coefficient = ExactFraction.ZERO
            this.numbers = listOf()
        } else {
            this.coefficient = coefficient
            this.numbers = numbers
        }
    }

    operator fun unaryMinus(): Term = Term(-coefficient, numbers)
    operator fun unaryPlus(): Term = this

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Term) {
            return false
        }

        val simplified = getSimplified()
        val otherSimplified = other.getSimplified()

        return simplified.coefficient == otherSimplified.coefficient
                && simplified.getPiCount() == otherSimplified.getPiCount()
                && simplified.getLogs().sorted() == otherSimplified.getLogs().sorted()
    }

    operator fun times(other: Term): Term {
        val newCoeff = coefficient * other.coefficient
        val newNumbers = numbers + other.numbers
        return Term(newCoeff, newNumbers)
    }

    operator fun div(other: Term): Term {
        if (other.isZero()) {
            throwDivideByZero()
        }

        val newCoeff = coefficient / other.coefficient
        val newNumbers = numbers + other.numbers.map { it.swapDivided() }
        return Term(newCoeff, newNumbers)
    }

    fun isZero(): Boolean = coefficient.isZero() || numbers.any { it.isZero() }

    fun getSimplified(): Term {
        val groups = numbers.groupBy { it.type }
        val logs = simplifyLogsList(groups[LogNum.TYPE] ?: listOf())
        val pis = simplifyPisList(groups[Pi.TYPE] ?: listOf())

        return Term(coefficient, logs + pis)
    }

    fun getValue(): BigDecimal {
        val simplified = getSimplified()

        val numbersProduct = simplified.numbers.fold(BigDecimal.ONE) { acc, num -> acc * num.getValue() }
        val numeratorProduct = numbersProduct * simplified.coefficient.numerator.toBigDecimal()

        return divideBigDecimals(numeratorProduct, simplified.coefficient.denominator.toBigDecimal())
    }

    fun getLogs(): List<LogNum> = numbers.filter { it.type == LogNum.TYPE }.map { it } as List<LogNum>
    fun getPiCount(): Int {
        val pis = numbers.filter { it.type == Pi.TYPE }
        val positive = pis.count { !it.isDivided }
        val negative = pis.size - positive
        return positive - negative
    }

    override fun toString(): String {
        val coeffString = if (coefficient.denominator == BigInteger.ONE) {
            coefficient.numerator.toString()
        } else {
            "${coefficient.numerator}/${coefficient.denominator}"
        }

        val numString = numbers.joinToString("")

        return if (numString.isEmpty()) {
            "<$coeffString>"
        } else {
            "<${coeffString}x${numString}>"
        }
    }

    override fun hashCode(): Int = listOf("Term", coefficient, numbers).hashCode()

    companion object {
        val ZERO = Term(ExactFraction.ZERO, listOf())
        val ONE = Term(ExactFraction.ONE, listOf())

        fun fromValues(coefficient: ExactFraction, logs: List<LogNum>, piCount: Int): Term {
            val piList = List(piCount) { Pi() }
            return Term(coefficient, logs + piList)
        }
    }
}
