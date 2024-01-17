package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.kotlinutils.biginteger.ext.isZero
import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.general.tryOrDefault
import xyz.lbres.kotlinutils.int.ext.isNegative
import xyz.lbres.kotlinutils.int.ext.isZero
import xyz.lbres.kotlinutils.string.ext.countElement
import xyz.lbres.kotlinutils.string.ext.substringTo
import java.math.BigInteger
import kotlin.math.abs

/**
 * Parse a string from standard number format into a ExactFraction.
 * Standard format is a string which may start with "-", but otherwise consists of at least one digit and up to 1 "."
 *
 * @param s [String]: string to parse
 * @return [ExactFraction]: parsed value
 */
internal fun parseDecimal(s: String): ExactFraction {
    var currentState: String = s.trim()

    validateDecimalString(currentState)

    // remove negative sign
    val isNegative = currentState.startsWith("-")
    val timesNeg = simpleIf(isNegative, -BigInteger.ONE, BigInteger.ONE)
    if (isNegative) {
        currentState = currentState.substring(1)
    }

    // remove e-value
    val eIndex = currentState.indexOf('E')
    var eValue = 0
    if (eIndex != -1) {
        eValue = currentState.substring(eIndex + 1).toInt()
        currentState = currentState.substringTo(eIndex)
    }

    val decimalIndex: Int = currentState.indexOf('.')
    val ef: ExactFraction

    // generate fraction
    if (decimalIndex == -1) {
        val numerator = BigInteger(currentState)
        ef = ExactFraction(numerator * timesNeg)
    } else {
        val wholeString = simpleIf(decimalIndex == 0, "0", currentState.substringTo(decimalIndex))
        val decimalString = currentState.substring(decimalIndex + 1)
        val whole = BigInteger(wholeString)
        val decimal = BigInteger(decimalString)

        if (decimal.isZero()) {
            ef = ExactFraction(whole * timesNeg) // also covers the case where number is 0
        } else {
            val zeros = "0".repeat(decimalString.length)
            val denomString = "1$zeros"

            val denominator = BigInteger(denomString)
            val numerator = whole * denominator + decimal

            ef = ExactFraction(numerator * timesNeg, denominator)
        }
    }

    // apply exponentiation
    val eMultiplier = BigInteger.TEN.pow(abs(eValue))
    return when {
        eValue.isZero() -> ef
        eValue.isNegative() -> ExactFraction(ef.numerator, eMultiplier * ef.denominator)
        else -> ExactFraction(eMultiplier * ef.numerator, ef.denominator)
    }
}

/**
 * Validate that a decimal string is in a parseable format, and throw [NumberFormatException] if it is not.
 * Assumes string is trimmed and lowercase.
 *
 * @param s [String]: string to validate
 */
private fun validateDecimalString(s: String) {
    val exception = NumberFormatException("Error parsing $s")

    val eIndex = s.indexOf('E')
    val validCharacters = s.all { it.isDigit() || it == '-' || it == '.' || it == 'E' }
    val validE = eIndex != 0 && eIndex != s.lastIndex && s.countElement('E') in 0..1
    if (!validCharacters || !validE) {
        throw exception
    }

    val validateMinus: (String) -> Boolean = {
        it.indexOf('-') <= 0 && it.countElement('-') <= 1
    }

    val validateDecimal: (String) -> Boolean = {
        it.indexOf('.') != it.lastIndex && it.countElement('.') <= 1
    }

    if (eIndex == -1 && !validateMinus(s) || !validateDecimal(s)) {
        throw exception
    } else if (eIndex != -1) {
        val preE = s.substringTo(eIndex)
        val postE = s.substring(eIndex + 1)

        if (!validateMinus(preE) || !validateDecimal(preE) || !validateMinus(postE) || postE.contains('.')) {
            throw exception
        }
    }
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
        val numbers = s.substring(3, s.lastIndex)
        val splitNumbers = numbers.split(' ')
        val numString = splitNumbers[0].trim()
        val denomString = splitNumbers[1].trim()
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
    val prefix = "EF["
    val suffix = "]"

    if (!trimmed.startsWith(prefix) || !trimmed.endsWith(suffix)) {
        return false
    }

    return tryOrDefault(false) {
        val split = trimmed.substring(prefix.length, s.length - suffix.length).split(' ')
        val validNumber: (String) -> Boolean = {
            when {
                it.isEmpty() -> false
                it.length == 1 -> it[0].isDigit()
                it[0] == '-' -> it.substring(1).all(Char::isDigit)
                else -> it.all(Char::isDigit)
            }
        }

        split.size == 2 && validNumber(split[0]) && validNumber(split[1])
    }
}
