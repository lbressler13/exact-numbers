package common

import kotlinutils.biginteger.ext.isZero
import kotlinutils.biginteger.max
import kotlinutils.biginteger.min
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext

/**
 * Get positive greatest common divisor of 2 numbers using Euclidean algorithm
 */
internal fun getGCD(val1: BigInteger, val2: BigInteger): BigInteger {
    val aval1 = val1.abs()
    val aval2 = val2.abs()

    when {
        aval1.isZero() && aval2.isZero() -> return BigInteger.ONE
        aval1.isZero() -> return aval2
        aval2.isZero() -> return aval1
        aval1 == aval2 -> return aval1
    }

    var sum = max(aval1, aval2)
    var value = min(aval1, aval2)
    var finished = false

    while (!finished) {
        val remainder = sum % value

        if (remainder.isZero()) {
            finished = true
        } else {
            sum = value
            value = remainder
        }
    }

    return value
}

/**
 * Perform division of two BigDecimals, rounding to 20 digits if the result is irrational
 *
 * @param bigDec1 [BigDecimal]: number on left side of division
 * @param bigDec2 [BigDecimal]: number on right side of division
 * @return [BigDecimal]: bigDec1 / bigDec2
 */
internal fun divideBigDecimals(bigDec1: BigDecimal, bigDec2: BigDecimal): BigDecimal {
    return try {
        bigDec1.divide(bigDec2)
    } catch (e: ArithmeticException) {
        val mc = MathContext(20)
        bigDec1.divide(bigDec2, mc)
    }
}

/**
 * Reusable code for manually throwing a divide by zero error
 * @throws [ArithmeticException]: divide by zero error
 */
internal fun throwDivideByZero() {
    throw ArithmeticException("divide by zero")
}
