package xyz.lbres.exactnumbers.exactfraction

import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode

/**
 * If value is between min and max Byte values, cast to Byte using simple division, rounding down.
 *
 * @param number [ExactFraction]: number to cast
 * @return [Byte]
 */
internal fun efToByte(number: ExactFraction): Byte {
    return castToWholeNumber(number, Byte.MIN_VALUE.toString(), Byte.MAX_VALUE.toString(), "Byte") { it.toByte() }
}

/**
 * If value is between min and max Char values, cast to Char using simple division, rounding down.
 * Accounts for Chars being non-negative
 *
 * @param number [ExactFraction]: number to cast
 * @return [Char]
 */
internal fun efToChar(number: ExactFraction): Char {
    val minValue = Char.MIN_VALUE.code
    val maxValue = Char.MAX_VALUE.code
    return castToWholeNumber(number, minValue.toString(), maxValue.toString(), "Char") { it.toInt().toChar() }
}

/**
 * If value is between min and max Short values, cast to Short using simple division, rounding down.
 *
 * @param number [ExactFraction]: number to cast
 * @return [Short]
 */
internal fun efToShort(number: ExactFraction): Short {
    return castToWholeNumber(number, Short.MIN_VALUE.toString(), Short.MAX_VALUE.toString(), "Short") { it.toShort() }
}

/**
 * If value is between min and max Int values, cast to Int using simple division, rounding down.
 *
 * @param number [ExactFraction]: number to cast
 * @return [Int]
 */
internal fun efToInt(number: ExactFraction): Int {
    return castToWholeNumber(number, Int.MIN_VALUE.toString(), Int.MAX_VALUE.toString(), "Int") { it.toInt() }
}

/**
 * If value is between min and max Long values, cast to Long using simple division, rounding down.
 *
 * @param number [ExactFraction]: number to cast
 * @return [Long]
 */
internal fun efToLong(number: ExactFraction): Long {
    return castToWholeNumber(number, Long.MIN_VALUE.toString(), Long.MAX_VALUE.toString(), "Long") { it.toLong() }
}

/**
 * If value is between min and max Double values, cast to Double using simple division, rounding down.
 *
 * @param number [ExactFraction]: number to cast
 * @return [Double]
 */
internal fun efToDouble(number: ExactFraction): Double {
    val valueString = Double.MAX_VALUE.toString()
    return castToDecimalNumber(number, "-$valueString", valueString, "Double") { it.toDouble() }
}

/**
 * If value is between min and max Float values, cast to Float using simple division, rounding down.
 *
 * @param number [ExactFraction]: number to cast
 * @return [Float]
 */
internal fun efToFloat(number: ExactFraction): Float {
    val valueString = Float.MAX_VALUE.toString()
    return castToDecimalNumber(number, "-$valueString", valueString, "Float") { it.toFloat() }
}

/**
 * Cast value to BigDecimal, using given precision
 *
 * @param number [ExactFraction]: number to cast
 * @param precision [Int]: precision to use when casting
 * @return [BigDecimal]
 */
internal fun efToBigDecimal(number: ExactFraction, precision: Int): BigDecimal {
    val mc = MathContext(precision, RoundingMode.HALF_UP)
    return number.numerator.toBigDecimal().divide(number.denominator.toBigDecimal(), mc)
}

/**
 * Cast to a type of whole number, or throw [ExactFractionOverflowException] if fraction exceeds allowed range
 *
 * @param minValue [String]: minimum value in allowed range
 * @param maxValue [String]: maximum value in allowed range
 * @param type [String]: name of type to cast to
 * @param cast ([BigInteger]) -> T: perform cast on fraction
 */
private fun <T> castToWholeNumber(number: ExactFraction, minValue: String, maxValue: String, type: String, cast: (BigInteger) -> T): T {
    val value = number.toBigInteger()

    val minInt = BigInteger(minValue)
    val maxInt = BigInteger(maxValue)
    if (value in minInt..maxInt) {
        return cast(value)
    }

    throw ExactFractionOverflowException("Overflow when casting to $type", number.toFractionString())
}

/**
 * Cast to a type of decimal number, or throw [ExactFractionOverflowException] if fraction exceeds allowed range
 *
 * @param minValue [String]: minimum value in allowed range
 * @param maxValue [String]: maximum value in allowed range
 * @param type [String]: name of type to cast to
 * @param cast ([BigDecimal]) -> T: perform cast on fraction
 */
private fun <T> castToDecimalNumber(number: ExactFraction, minValue: String, maxValue: String, type: String, cast: (BigDecimal) -> T): T {
    val value = number.toBigDecimal()

    val minDecimal = BigDecimal(minValue)
    val maxDecimal = BigDecimal(maxValue)
    if (value in minDecimal..maxDecimal) {
        return cast(value)
    }

    throw ExactFractionOverflowException("Overflow when casting to $type", number.toFractionString())
}

private fun generateErrorMessage(type: String, value: ExactFraction): Exception {
    return ExactFractionOverflowException("Overflow when casting to $type", value.toFractionString())
}
