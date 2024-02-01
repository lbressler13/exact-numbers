package xyz.lbres.exactnumbers.ext

import xyz.lbres.exactnumbers.utils.divideByZero
import xyz.lbres.kotlinutils.general.succeeds
import java.math.BigDecimal
import java.math.MathContext

/**
 * Perform division of two BigDecimals, rounding to 20 digits if the result is irrational
 *
 * @param other [BigDecimal]: number to divide by
 * @return [BigDecimal]: result of division
 */
internal fun BigDecimal.divideBy(other: BigDecimal): BigDecimal {
    if (other == BigDecimal.ZERO) {
        throw divideByZero
    }

    return try {
        divide(other)
    } catch (_: ArithmeticException) {
        val mc = MathContext(20)
        divide(other, mc)
    }
}

/**
 * Determine if decimal is a whole number
 *
 * @return `true` if number is a whole number, `false` otherwise
 */
internal fun BigDecimal.isWholeNumber(): Boolean = succeeds { toBigIntegerExact() }
