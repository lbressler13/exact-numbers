package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.common.getIntFromDecimal
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.log

/**
 * Get log value of a whole number with the given base
 *
 * @param num [BigInteger]: number to get log of
 * @param base [Int]: base to use in calculation
 * @return [BigDecimal]: the log of the number, using the current base
 */
internal fun getLogOf(num: BigInteger, base: Int): BigDecimal {
    val logNum = log(num.toDouble(), base.toDouble())

    when {
        logNum.isNaN() -> throw ArithmeticException("Error calculating log")
        logNum.isInfinite() -> throw ArithmeticException("Error calculating log: overflow on log_$base($num)")
    }

    // account for imprecision with doubles
    val int = getIntFromDecimal(logNum.toBigDecimal()) { base.toBigInteger().pow(it.toInt()) == num }
    return int?.toBigDecimal() ?: logNum.toBigDecimal()
}
