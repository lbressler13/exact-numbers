package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.exactnumbers.utils.getIntFromDecimal
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.log

/**
 * Get log value of a whole number with the given base
 *
 * @param argument [BigInteger]: argument to use in calculation
 * @param base [Int]: base to use in calculation
 * @return [BigDecimal]: the log of the number, using the current base
 */
internal fun getLogOf(argument: BigInteger, base: Int): BigDecimal {
    val logNum = log(argument.toDouble(), base.toDouble())

    when {
        logNum.isNaN() -> throw ArithmeticException("Error calculating log")
        logNum.isInfinite() -> throw ArithmeticException("Error calculating log: overflow on log_$base($argument)")
    }

    // account for imprecision with doubles
    val int = getIntFromDecimal(logNum.toBigDecimal()) { base.toBigInteger().pow(it.toInt()) == argument }
    return int?.toBigDecimal() ?: logNum.toBigDecimal()
}
