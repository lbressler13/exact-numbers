package exactnumbers.irrationals.logs

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.pi.Pi
import expressions.term.Term
import shared.NumType
import shared.div
import shared.divideBigDecimals
import shared.throwDivideByZero
import shared.times
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.log

/**
 * Representation of a log, with an integer base and rational argument
 *
 * @param number [ExactFraction]: value to compute log of
 * @param base [Int]: base to use when computing log
 * @throws [ArithmeticException] if number is not positive or if base is less than 1
 */
class LogNum(val number: ExactFraction, val base: Int, override val isDivided: Boolean) : Comparable<LogNum>, NumType {
    override val type = TYPE

    init {
        when {
            number == ExactFraction.ONE && isDivided -> throwDivideByZero()
            number.isZero() -> throw ArithmeticException("Cannot calculate log of 0")
            number.isNegative() -> throw ArithmeticException("Cannot calculate log of negative number")
            base <= 1 -> throw ArithmeticException("Log base must be greater than 1")
        }
    }

    constructor(number: ExactFraction) : this(number, base = 10, isDivided = false)
    constructor(number: ExactFraction, base: Int) : this(number, base, isDivided = false)
    constructor(number: ExactFraction, isDivided: Boolean) : this(number, 10, isDivided)

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is LogNum) {
            return false
        }

        return getValue() == other.getValue()
    }

    operator fun times(other: LogNum): Term = times(other as NumType)
    operator fun times(other: Pi): Term = times(other as NumType)
    operator fun div(other: LogNum): Term = div(other as NumType)
    operator fun div(other: Pi): Term = div(other as NumType)

    override operator fun compareTo(other: LogNum): Int = getValue().compareTo(other.getValue())

    override fun isZero(): Boolean = number == ExactFraction.ONE

    // log_b(x/y) = log_b(x) - log_b(y)
    // get numerator and denominator separately to reduce loss of precision when casting to double
    override fun getValue(): BigDecimal {
        val logValue = getLogOf(number.numerator) - getLogOf(number.denominator)

        if (!isDivided) {
            return logValue
        }

        return divideBigDecimals(BigDecimal.ONE, logValue)
    }

    override fun swapDivided(): LogNum {
        if (isZero()) {
            throwDivideByZero()
        }

        return LogNum(number, base, !isDivided)
    }

    /**
     * Get log value of a whole number, using the base assigned to this log
     *
     * @param [num] [BigInteger]: number to get log of
     * @return [BigDecimal]: the log of the number, using the current base
     * @throws [ArithmeticException] if the log returns NaN
     */
    internal fun getLogOf(num: BigInteger): BigDecimal {
        val logNum = log(num.toDouble(), base.toDouble())
        if (logNum.isNaN()) {
            throw ArithmeticException("Error calculating log of $number")
        }

        // account for imprecision with doubles
        val intNum = logNum.toInt()
        if (base.toBigInteger().pow(intNum) == num) {
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

        if (isDivided) {
            return "[1/log_$base($numString)]"
        }

        return "[log_$base($numString)]"
    }

    override fun hashCode(): Int = Pair(number, base).hashCode()

    companion object {
        val ZERO = LogNum(ExactFraction.ONE)
        val ONE = LogNum(ExactFraction.TEN)

        const val TYPE = "log"
    }
}
