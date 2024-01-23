package xyz.lbres.exactnumbers.ext

import xyz.lbres.exactnumbers.utils.divideByZero
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
