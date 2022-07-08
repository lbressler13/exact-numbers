package exactnumbers.irrationals.pi

import exactnumbers.exactfraction.ExactFraction
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import kotlin.math.PI

/**
 * Representation of pi, with a rational coefficient
 */
class PiNum(val coefficient: ExactFraction) {
    constructor() : this(ExactFraction.ONE)

    operator fun unaryMinus(): PiNum = PiNum(-coefficient)
    operator fun unaryPlus(): PiNum = this

    operator fun plus(other: PiNum): PiNum = PiNum(coefficient + other.coefficient)
    operator fun minus(other: PiNum): PiNum = PiNum(coefficient - other.coefficient)

    override fun equals(other: Any?): Boolean = other != null && other is PiNum && coefficient == other.coefficient

    fun getValue(): BigDecimal {
        if (isZero()) {
            return BigDecimal.ZERO
        }

        val pi = PI.toBigDecimal()
        val withNumerator = pi * coefficient.numerator.toBigDecimal()

        // divide withNumerator by denominator
        try {
            return withNumerator.divide(coefficient.denominator.toBigDecimal())
        } catch (e: ArithmeticException) { // may not be able to represent precise value
            val mc = MathContext(20)
            return withNumerator.divide(coefficient.denominator.toBigDecimal(), mc)
        }
    }

    fun isZero(): Boolean = coefficient.isZero()

    override fun toString(): String {
        val coeffString = if (coefficient.denominator == BigInteger.ONE) {
            coefficient.numerator.toString()
        } else {
            "(${coefficient.numerator}/${coefficient.denominator})"
        }
        return "[${coeffString}xπ]"
    }

    override fun hashCode(): Int = Pair("pi", coefficient).hashCode()

    companion object {
        val ZERO = PiNum(ExactFraction.ZERO)
    }
}
