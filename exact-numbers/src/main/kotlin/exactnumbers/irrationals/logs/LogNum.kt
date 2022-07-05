package exactnumbers.irrationals.logs

import kotlinutils.biginteger.ext.isNegative
import kotlinutils.biginteger.ext.isZero
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.log

// TODO add base, convert to EF
class LogNum(number: BigInteger) {
    val number: BigInteger
    val base: Int = 10

    init {
        when {
            number.isZero() -> throw ArithmeticException("Cannot calculate log of 0")
            number.isNegative() -> throw ArithmeticException("Cannot calculate log of negative number")
            else -> this.number = number
        }
    }

    override operator fun equals(other: Any?): Boolean {
        if (other == null || other !is LogNum) {
            return false
        }

        return base == other.base && number == other.number
    }

    operator fun times(other: LogNum): LogProduct = LogProduct(listOf(this, other))

    fun isZero(): Boolean = number == BigInteger.ONE

    fun getValue(): BigDecimal {
        val numString = number.toString()
        val trailingZeros = numString.reversed().indexOfFirst { it != '0' }

        val addition = trailingZeros.toBigDecimal() // log(100) = 2
        val remaining = number / BigInteger.TEN.pow(trailingZeros)

        // val logNum = log10(remaining.toDouble())
        val logNum = log(remaining.toDouble(), base.toDouble())
        if (logNum.isNaN()) {
            throw ArithmeticException("Error calculating log of $number")
        }

        return logNum.toBigDecimal() + addition
    }

    override fun toString(): String = "log_$base($number)"

    override fun hashCode(): Int = Pair(number, base).hashCode()

    companion object {
        val ZERO = LogNum(BigInteger.ONE)
        val ONE = LogNum(BigInteger.TEN)
    }
}
