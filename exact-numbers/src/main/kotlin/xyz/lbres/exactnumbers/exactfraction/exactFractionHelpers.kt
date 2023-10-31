package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.kotlinutils.biginteger.ext.isNegative
import xyz.lbres.kotlinutils.biginteger.ext.isZero
import xyz.lbres.kotlinutils.biginteger.getGCD
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode

/**
 * Simplify a pair of numerator and denominator values to smallest values with same ratio, and move all negatives into numerator
 *
 * @param values [Pair]<BigInteger, BigInteger>: pair to simplify, where the first value represents a numerator and the second represents a denominator
 * @return [Pair]<BigInteger, BigInteger>: pair where first value represents simplified numerator, and second value represents simplified denominator
 */
internal fun simplify(values: Pair<BigInteger, BigInteger>): Pair<BigInteger, BigInteger> {
    var newNumerator = values.first
    var newDenominator = values.second

    // set denominator to 1 when numerator is 0
    if (newNumerator.isZero()) {
        newDenominator = BigInteger.ONE
    }

    // move negatives to numerator
    if (newDenominator.isNegative()) {
        newNumerator = -newNumerator
        newDenominator = -newDenominator
    }

    // simplify using greatest common divisor
    if (newNumerator != BigInteger.ZERO) {
        val gcd = getGCD(newNumerator, newDenominator)
        newNumerator /= gcd
        newDenominator /= gcd
    }

    return Pair(newNumerator, newDenominator)
}

/**
 * Create a string representation of an [ExactFraction] in standard decimal format
 *
 * @param ef [ExactFraction]: number to convert to string
 * @param digits [Int]: digits of precision in string. Will be ignored if this number results in a string in exponential format
 * @return string representation in decimal format
 */
internal fun createDecimalString(ef: ExactFraction, digits: Int): String {
    if (ef.isWholeNumber()) {
        return ef.numerator.toString()
    }

    val whole: BigInteger = ef.numerator / ef.denominator
    val remainder: BigInteger = ef.numerator - ef.denominator * whole

    val mc = MathContext(digits, RoundingMode.HALF_UP)
    val remainderDecimal = remainder.toBigDecimal()
    val denominatorDecimal = ef.denominator.toBigDecimal()
    val decimal = remainderDecimal.divide(denominatorDecimal, mc)
    return (whole.toBigDecimal() + decimal).toString()
}
