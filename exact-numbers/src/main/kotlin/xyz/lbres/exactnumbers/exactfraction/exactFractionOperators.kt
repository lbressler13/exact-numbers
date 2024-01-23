package xyz.lbres.exactnumbers.exactfraction

import java.math.BigInteger

/**
 * Add two ExactFractions
 *
 * @param ef1 [ExactFraction]
 * @param ef2 [ExactFraction]
 * @return [ExactFraction]: sum of [ef1] and [ef2]
 */
internal fun efAdd(ef1: ExactFraction, ef2: ExactFraction): ExactFraction {
    if (ef1.denominator == ef2.denominator) {
        val newNumerator = ef1.numerator + ef2.numerator
        return ExactFraction(newNumerator, ef1.denominator)
    }

    val newNumerator = ef1.numerator * ef2.denominator + ef2.numerator * ef1.denominator
    val newDenominator = ef1.denominator * ef2.denominator
    return ExactFraction(newNumerator, newDenominator)
}

/**
 * Multiply two ExactFractions
 *
 * @param ef1 [ExactFraction]
 * @param ef2 [ExactFraction]
 * @return [ExactFraction]: product of [ef1] and [ef2]
 */
internal fun efTimes(ef1: ExactFraction, ef2: ExactFraction): ExactFraction {
    val newNumerator = ef1.numerator * ef2.numerator
    val newDenominator = ef1.denominator * ef2.denominator
    return ExactFraction(newNumerator, newDenominator)
}

/**
 * Apply exponent to an ExactFraction
 *
 * @param base [ExactFraction]: base of exponentiation
 * @param exponent [ExactFraction]: exponent, must be a whole number
 * @return [ExactFraction]
 */
internal fun efPow(base: ExactFraction, exponent: ExactFraction): ExactFraction {
    if (!exponent.isWholeNumber()) {
        throw ArithmeticException("Exponents must be whole numbers")
    }

    when {
        base.isZero() -> return ExactFraction.ZERO
        base == ExactFraction.ONE || exponent.isZero() -> return ExactFraction.ONE
        exponent == ExactFraction.ONE -> return base
    }

    var powNumerator = BigInteger.ONE
    var powDenominator = BigInteger.ONE
    var remaining = exponent.numerator.abs()
    val intMax = Int.MAX_VALUE

    try {
        while (remaining > BigInteger.ZERO) {
            if (remaining > intMax.toBigInteger()) {
                powNumerator *= base.numerator.pow(intMax)
                powDenominator *= base.denominator.pow(intMax)
                remaining -= intMax.toBigInteger()
            } else {
                val exp = remaining.toInt()
                powNumerator = base.numerator.pow(exp)
                powDenominator = base.denominator.pow(exp)
                remaining = BigInteger.ZERO
            }
        }
    } catch (e: ArithmeticException) {
        if (e.message == "BigInteger would overflow supported range") {
            throw ExactFractionOverflowException()
        }

        throw e
    }

    return if (exponent.isNegative()) {
        ExactFraction(powDenominator, powNumerator)
    } else {
        ExactFraction(powNumerator, powDenominator)
    }
}

/**
 * Compare two ExactFractions
 *
 * @param ef1 [ExactFraction]
 * @param ef2 [ExactFraction]
 * @return [Int]: comparison of [ef1] to [ef2]
 */
internal fun efCompare(ef1: ExactFraction, ef2: ExactFraction): Int {
    val difference = ef1 - ef2
    return when {
        difference.isNegative() -> -1
        difference.isZero() -> 0
        else -> 1
    }
}
