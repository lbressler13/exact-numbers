package exactnumbers.irrationals.logs

import exactnumbers.exactfraction.ExactFraction
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.log

class LogNum(val number: ExactFraction, val base: Int) {

    init {
        when {
            number.isZero() -> throw ArithmeticException("Cannot calculate log of 0")
            number.isNegative() -> throw ArithmeticException("Cannot calculate log of negative number")
            base <= 1 -> throw ArithmeticException("Log base must be greater than 1")
        }
    }

    constructor(number: ExactFraction) : this(number, base = 10)

    override operator fun equals(other: Any?): Boolean {
        if (other == null || other !is LogNum) {
            return false
        }

        return base == other.base && number == other.number
    }

    operator fun times(other: LogNum): LogProduct = LogProduct(listOf(this, other))

    fun isZero(): Boolean = number == ExactFraction.ONE

    // log_b(x/y) = log_b(x) - log_b(y)
    fun getValue(): BigDecimal {
        // val numString = number.toString()
        // val trailingZeros = numString.reversed().indexOfFirst { it != '0' }

        // val addition = trailingZeros.toBigDecimal() // log(100) = 2
        // val remaining = number / BigInteger.TEN.pow(trailingZeros)
        // val logNum = log(remaining.toDouble(), base.toDouble())

        // val logNum = log(number.toDouble(), base.toDouble())
//        val firstLog = log(number.numerator.toDouble(), base.toDouble())
//        val secondLog = log(number.denominator.toDouble(), base.toDouble())
//
//        if (firstLog.isNaN() || secondLog.isNaN()) {
//            throw ArithmeticException("Error calculating log of $number")
//        }
//
//        val logNum = firstLog - secondLog
//
//        // account for imprecision with doubles
//        val intNum = logNum.toInt()
//        if (ExactFraction(base).pow(ExactFraction(intNum)) == number) {
//            return intNum.toBigDecimal()
//        }
//
//        return logNum.toBigDecimal() // + addition
        // get numberator and denominator separately to reduce loss of precision when casting to double
        return getLogOf(number.numerator) - getLogOf(number.denominator)
    }

    internal fun getLogOf(num: BigInteger): BigDecimal {
        val logNum = log(num.toDouble(), base.toDouble())
        if (logNum.isNaN()) {
            throw ArithmeticException("Error calculating log of $number")
        }

        // account for imprecision with doubles
        val intNum = logNum.toInt()
        if (ExactFraction(base).pow(ExactFraction(intNum)) == ExactFraction(num)) {
            return intNum.toBigDecimal()
        }

        return logNum.toBigDecimal()
    }

    override fun toString(): String {
        val numString = if (number.denominator == BigInteger.ONE) {
            number.numerator.toString()
        } else {
            "${number.numerator}/${number.denominator}"
        }
        return "log_$base($numString)"
    }

    override fun hashCode(): Int = Pair(number, base).hashCode()

    companion object {
        val ZERO = LogNum(ExactFraction.ONE)
        val ONE = LogNum(ExactFraction.TEN)
    }
}
