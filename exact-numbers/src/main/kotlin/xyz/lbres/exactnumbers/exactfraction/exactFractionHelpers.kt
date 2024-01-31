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
    // set denominator to 1 when numerator is 0
    if (numerator.isZero()) {
        return Pair(BigInteger.ZERO, BigInteger.ONE)
    }

    // simplify using greatest common divisor
    val gcd = getGCD(numerator, denominator)
    val simplifiedNumerator = numerator / gcd
    val simplifiedDenominator = denominator / gcd

    // move negatives to numerator
    return if (denominator.isNegative()) {
        Pair(-simplifiedNumerator, -simplifiedDenominator)
    } else {
        Pair(simplifiedNumerator, simplifiedDenominator)
    }
}
