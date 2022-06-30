package exactnumbers.irrationals

import exactnumbers.exactfraction.ExactFraction
import kotlinutils.biginteger.ext.isNegative
import kotlinutils.biginteger.ext.isZero
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.log10

// TODO add base, convert to EF
class LogNum(coefficient: ExactFraction, number: BigInteger) {
    val coefficient: ExactFraction
    val number: BigInteger

    init {
        when {
            number.isZero() -> throw ArithmeticException("Cannot calculate log of 0")
            number.isNegative() -> throw ArithmeticException("Cannot calculate log of negative number")
            coefficient.isZero() || number == BigInteger.ONE -> {
                this.coefficient = ExactFraction.ZERO
                this.number = BigInteger.ONE
            }
            else -> {
                this.coefficient = coefficient
                this.number = number
            }
        }
    }

    override operator fun equals(other: Any?): Boolean {
        if (other == null || other !is LogNum) {
            return false
        }

        return coefficient == other.coefficient && number == other.number
    }

    operator fun unaryMinus(): LogNum = LogNum(-coefficient, number)
    operator fun unaryPlus(): LogNum = LogNum(coefficient, number)

    fun isZero(): Boolean = coefficient.isZero()

    fun getValue(): BigDecimal {
        val numString = number.toString()
        val trailingZeros = numString.reversed().indexOfFirst { it != '0' }

        val addition = trailingZeros.toBigDecimal() // log(100) = 2
        val remaining = number / BigInteger.TEN.pow(trailingZeros)

        val logNum = log10(remaining.toDouble())
        if (logNum.isNaN()) {
            throw ArithmeticException("Error calculating log of $number")
        }

        val numerator = (logNum.toBigDecimal() + addition) * coefficient.numerator.toBigDecimal()
        return numerator / coefficient.denominator.toBigDecimal()
    }

    override fun toString(): String {
        val coeffString = if (coefficient.denominator == BigInteger.ONE) {
            coefficient.numerator.toString()
        } else {
            coefficient.toString()
        }
        return "$coeffString*log_10($number)"
    }

    override fun hashCode(): Int = Pair(coefficient, number).hashCode()

    companion object {
        val ZERO = LogNum(ExactFraction.ZERO, BigInteger.ONE)
    }
}
