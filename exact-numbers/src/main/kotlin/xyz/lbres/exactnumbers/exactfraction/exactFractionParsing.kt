package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.general.succeeds
import java.math.BigDecimal
import java.math.BigInteger

private const val efPrefix = "EF["
private const val efSuffix = "]"

/**
 * Parse a string from standard number format into a ExactFraction.
 * Standard format is a string which may start with "-", but otherwise consists of at least one digit and up to 1 "."
 *
 * @param s [String]: string to parse
 * @return [ExactFraction]: parsed value
 */
internal fun parseDecimal(s: String): ExactFraction {
    var currentState: String = s.trim()

    // validate string
    if (!succeeds { BigDecimal(currentState) } || currentState.last() == '.') {
        throw NumberFormatException("Error parsing $currentState")
    }

    // remove negative sign
    val isNegative = currentState.startsWith("-")
    if (isNegative) {
        currentState = currentState.substring(1)
    }

    val divResult = BigDecimal(currentState).divideAndRemainder(BigDecimal.ONE)
    val whole = divResult[0].toBigInteger()

    val rawDecimalString = divResult[1].stripTrailingZeros().toPlainString()
    val decimalIndex = rawDecimalString.indexOf('.')
    val decimalString = rawDecimalString.substring(decimalIndex + 1) // starts from 0 if decimalIndex == -1

    val denomZeroes = "0".repeat(decimalString.length)
    val denomString = "1$denomZeroes"

    val denominator = BigInteger(denomString)
    val numerator = whole * denominator + BigInteger(decimalString)

    return simpleIf(isNegative, { ExactFraction(-numerator, denominator) }, { ExactFraction(numerator, denominator) })
}

/**
 * Parse a string from a EF string format into a ExactFraction.
 * EF string format is "EF[num denom]"
 *
 * @param s [String]: string to parse
 * @return [ExactFraction]: parsed value
 */
internal fun parseEFString(s: String): ExactFraction {
    if (!checkIsEFString(s)) {
        throw NumberFormatException("Invalid EF string format: $s")
    }

    try {
        val numbers = s.trim().substring(efPrefix.length, s.length - efSuffix.length).split(' ')
        val numString = numbers[0].trim()
        val denomString = numbers[1].trim()
        val numerator = BigInteger(numString)
        val denominator = BigInteger(denomString)
        return ExactFraction(numerator, denominator)
    } catch (e: ArithmeticException) {
        throw e
    } catch (_: Exception) {
        throw NumberFormatException("Invalid EF string format: $s")
    }
}

/**
 * Check if a given string is in the EF string format.
 * EF string format is "EF[num denom]"
 *
 * @param s [String]: string to check
 * @return [Boolean]: `true` if s is in EF string format, `false` otherwise
 */
internal fun checkIsEFString(s: String): Boolean {
    val trimmed = s.trim()

    if (!trimmed.startsWith(efPrefix) || !trimmed.endsWith(efSuffix)) {
        return false
    }

    val numbers = trimmed.substring(efPrefix.length, s.length - efSuffix.length).split(' ')
    return numbers.size == 2 && succeeds { BigInteger(numbers[0]) } && succeeds { BigInteger(numbers[1]) }
}
