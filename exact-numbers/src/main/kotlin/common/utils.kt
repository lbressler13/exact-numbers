package common

import kotlinutils.biginteger.ext.isZero
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode

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

    var sum = aval1.max(aval2)
    var value = aval1.min(aval2)
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
 * Round decimal to nearest ints, and determine if either value passes a check.
 * If so, returns the passing value. If both pass, the closer value will be returned.
 *
 * @param decimal [BigDecimal]: the number to round
 * @param checkInt [(BigInteger) -> Boolean]: function to check rounded value
 * @return [BigInteger?]: a rounded number that passes the checks, or null if there is none
 */
internal fun getIntFromDecimal(decimal: BigDecimal, checkInt: (BigInteger) -> Boolean): BigInteger? {
    try {
        val upInt = decimal.setScale(0, RoundingMode.UP).toBigInteger()
        val downInt = decimal.setScale(0, RoundingMode.DOWN).toBigInteger()

        val upPasses = checkInt(upInt)
        val downPasses = checkInt(downInt)

        return when {
            upPasses && downPasses -> decimal.setScale(0, RoundingMode.HALF_UP).toBigInteger()
            upPasses -> upInt
            downPasses -> downInt
            else -> null
        }
    } catch (_: Exception) {
        return null
    }
}

/**
 * Reusable code for manually throwing a divide by zero error
 * @throws [ArithmeticException]: divide by zero error
 */
internal fun throwDivideByZero() {
    throw ArithmeticException("divide by zero")
}
