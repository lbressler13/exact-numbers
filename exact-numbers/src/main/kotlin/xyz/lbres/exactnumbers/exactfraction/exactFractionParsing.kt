package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.general.tryOrDefault
import xyz.lbres.kotlinutils.string.ext.countElement
import xyz.lbres.kotlinutils.string.ext.substringTo
import java.math.BigDecimal
import java.math.BigInteger

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
    if (isNegative) {
        currentState = currentState.substring(1)
    }

    val divResult = BigDecimal(currentState).divideAndRemainder(BigDecimal.ONE)
    val whole = divResult[0].toBigInteger()

    val rawDecimalString = divResult[1].stripTrailingZeros().toPlainString()
    val decimalIndex = rawDecimalString.indexOf('.')
    val decimalString = rawDecimalString.substring(decimalIndex + 1) // starts from 0 if decimalIndex == -1

    val zeros = "0".repeat(decimalString.length)
    val denomString = "1$zeros"

    val denominator = BigInteger(denomString)
    val numerator = whole * denominator + BigInteger(decimalString)

    return simpleIf(isNegative, { ExactFraction(-numerator, denominator) }, { ExactFraction(numerator, denominator) })
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
