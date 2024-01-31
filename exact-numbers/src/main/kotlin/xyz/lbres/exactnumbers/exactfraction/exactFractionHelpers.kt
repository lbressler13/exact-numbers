package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.kotlinutils.biginteger.ext.isNegative
import xyz.lbres.kotlinutils.biginteger.ext.isZero
import xyz.lbres.kotlinutils.biginteger.getGCD
import java.math.BigInteger

/**
 * Simplify numerator and denominator values to smallest values with same ratio, and move all negatives into numerator
 *
 * @param numerator [BigInteger]: numerator of fraction to simplify
 * @param denominator [BigInteger]: denominator of fraction to simplify
 * @return Pair<BigInteger, BigInteger>: pair where first value represents simplified numerator, and second value represents simplified denominator
 */
internal fun simplifyFraction(numerator: BigInteger, denominator: BigInteger): Pair<BigInteger, BigInteger> {
    var newNumerator = numerator
    var newDenominator = denominator

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
