package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.kotlinutils.biginteger.ext.isNegative
import xyz.lbres.kotlinutils.biginteger.ext.isZero
import xyz.lbres.kotlinutils.biginteger.getGCD
import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.pair.TypePair
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode

/**
 * Simplify a pair of numerator and denominator values to smallest values with same ratio, and move all negatives into numerator
 *
 * @param values [TypePair]<BigInteger>: pair to simplify, where the first value represents a numerator and the second represents a denominator
 * @return [TypePair]<BigInteger>: pair where first value represents simplified numerator, and second value represents simplified denominator
 */
internal fun simplify(values: TypePair<BigInteger>): TypePair<BigInteger> {
    var numerator = values.first
    var denominator = values.second

    // set denominator to 1 when numerator is 0
    if (numerator.isZero()) {
        denominator = BigInteger.ONE
    }

    // move negatives to numerator
    if (denominator.isNegative()) {
        numerator = -numerator
        denominator = -denominator
    }

    // simplify using greatest common divisor
    if (numerator != BigInteger.ZERO) {
        val gcd = getGCD(numerator, denominator)
        numerator /= gcd
        denominator /= gcd
    }

    return Pair(numerator, denominator)
}

/**
 * Create a string representation of an [ExactFraction] in standard decimal format
 *
 * @param ef [ExactFraction]: number to convert to string
 * @param digits [Int]: digits of precision in string. Must be non-negative.
 * Will be ignored if this number results in a string in exponential format.
 * @return [String]: representation in decimal format
 */
internal fun createDecimalString(ef: ExactFraction, digits: Int): String {
    if (ef.isWholeNumber()) {
        return ef.numerator.toString()
    }

    val whole: BigInteger = ef.numerator / ef.denominator
    val remainder: BigInteger = ef.numerator - ef.denominator * whole

    val mc = MathContext(simpleIf(digits > 0, digits, 1), RoundingMode.HALF_UP)
    val remainderDecimal = remainder.toBigDecimal()
    val denominatorDecimal = ef.denominator.toBigDecimal()
    val decimal = remainderDecimal.divide(denominatorDecimal, mc)

    if (digits == 0) {
        val startIndex = decimal.toString().indexOf('.') + 1
        val roundUp = decimal.toString()[startIndex].digitToInt() >= 5
        val result = whole + simpleIf(roundUp, BigInteger.ONE, BigInteger.ZERO)
        return result.toString()
    }

    return (whole.toBigDecimal() + decimal).toString()
}
