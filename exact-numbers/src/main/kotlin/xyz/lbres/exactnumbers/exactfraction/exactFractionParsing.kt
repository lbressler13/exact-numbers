package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.kotlinutils.biginteger.ext.isZero
import xyz.lbres.kotlinutils.int.ext.isNegative
import xyz.lbres.kotlinutils.int.ext.isZero
import xyz.lbres.kotlinutils.string.ext.substringTo
import java.math.BigInteger
import kotlin.math.abs

/**
 * Parse a string from standard number format into a ExactFraction.
 * Standard format is a string which may start with "-", but otherwise consists of at least one digit and up to 1 "."
 *
 * @param unparsed [String]: string to parse
 * @return parsed ExactFraction
 * @throws NumberFormatException in case of improperly formatted number string
 */
internal fun parseDecimal(unparsed: String): ExactFraction {
    var currentState: String = unparsed.trim().lowercase()

    validateDecimalString(currentState)

    val isNegative = currentState.startsWith("-")
    val timesNeg = if (isNegative) -BigInteger.ONE else BigInteger.ONE
    if (isNegative) {
        currentState = currentState.substring(1)
    }

    val eIndex = currentState.indexOf('e')
    var eMultiplier = 0
    if (eIndex != -1) {
        eMultiplier = currentState.substring(eIndex + 1).toInt()
        currentState = currentState.substringTo(eIndex)
    }

    val decimalIndex: Int = currentState.indexOf('.')

    val ef = when (decimalIndex) {
        -1 -> {
            val numerator = BigInteger(currentState)
            ExactFraction(numerator * timesNeg)
        }
        0 -> {
            currentState = currentState.substring(1)
            val numerator = BigInteger(currentState)

            if (numerator.isZero()) {
                return ExactFraction(0)
            }

            val zeros = "0".repeat(currentState.length)
            val denomString = "1$zeros"
            val denominator = BigInteger(denomString)

            ExactFraction(numerator * timesNeg, denominator)
        }
        else -> {
            val wholeString = currentState.substringTo(decimalIndex)
            val decimalString = currentState.substring(decimalIndex + 1)
            val whole = BigInteger(wholeString)
            val decimal = BigInteger(decimalString)

            if (decimal.isZero()) {
                ExactFraction(whole * timesNeg) // also covers the case where number is 0
            } else {
                val zeros = "0".repeat(decimalString.length)
                val denomString = "1$zeros"

                val denominator = BigInteger(denomString)
                val numerator = whole * denominator + decimal

                ExactFraction(numerator * timesNeg, denominator)
            }
        }
    }
    return applyEMultiplier(eMultiplier, ef)
}

private fun applyEMultiplier(eMultiplier: Int, ef: ExactFraction): ExactFraction {
    val mult = BigInteger.TEN.pow(abs(eMultiplier))

    return when {
        eMultiplier.isZero() -> ef
        eMultiplier.isNegative() -> ExactFraction(ef.numerator, mult * ef.denominator)
        else -> ExactFraction(mult * ef.numerator, ef.denominator)
    }
}

// assumes s is trimmed and lowercase
private fun validateDecimalString(s: String) {
    val exception = NumberFormatException()

    val eIndex = s.indexOf('e')
    val validCharacters = s.all { it.isDigit() || it == '-' || it == '.' || it == 'e' }
    val validE = eIndex != 0 && eIndex != s.lastIndex && s.count { it == 'e' } in 0..1
    if (!validCharacters || !validE) {
        throw exception
    }

    val validateMinus: (String) -> Boolean = { str ->
        str.indexOf('-') in -1..0 && str.count { it == '-' } in 0..1
    }

    val validateDecimal: (String) -> Boolean = { str ->
        str.indexOf('.') != str.lastIndex && str.count { it == '.' } in 0..1
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
 * @param unparsed [String]: string to parse
 * @return parsed ExactFraction
 * @throws NumberFormatException in case of improperly formatted number string
 */
internal fun parseEFString(unparsed: String): ExactFraction {
    if (!checkIsEFString(unparsed)) {
        throw NumberFormatException("Invalid EF string format")
    }

    try {
        val numbers = unparsed.substring(3, unparsed.lastIndex)
        val split = numbers.split(' ')
        val numString = split[0].trim()
        val denomString = split[1].trim()
        val numerator = BigInteger(numString)
        val denominator = BigInteger(denomString)
        return ExactFraction(numerator, denominator)
    } catch (e: ArithmeticException) {
        throw e
    } catch (e: Exception) {
        throw NumberFormatException("Invalid EF string format")
    }
}

/**
 * Check if a given string is in the EF string format.
 * EF string format is "EF[num denom]"
 *
 * @param s [String]: string to check
 * @return true if s is in EF string format, false otherwise
 */
fun checkIsEFString(s: String): Boolean {
    val trimmed = s.trim()
    return try {
        val startEnd = trimmed.startsWith("EF[") && trimmed.endsWith("]")
        val split = trimmed.substring(3, s.lastIndex).split(" ")
        BigInteger(split[0])
        BigInteger(split[1])
        startEnd && split.size == 2
    } catch (e: Exception) {
        false
    }
}
