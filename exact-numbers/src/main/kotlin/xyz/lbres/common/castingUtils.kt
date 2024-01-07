package xyz.lbres.common

import java.math.BigDecimal
import java.math.BigInteger

/**
 * Cast a number to Byte, or throw the given exception if number exceeds values for Byte
 *
 * @param value [BigDecimal]: number to cast
 * @param getCastingError () -> [ArithmeticException]: get exception to throw if number exceeds supported values
 * @return [Byte] value as a byte
 */
internal fun castToByte(value: BigDecimal, getCastingError: () -> ArithmeticException): Byte {
    return castNumber(value, Byte.MIN_VALUE, Byte.MAX_VALUE, BigDecimal::toByte, getCastingError, false)
}

/**
 * Cast a number to Char, or throw the given exception if number exceeds values for Char
 *
 * @param value [BigDecimal]: number to cast
 * @param getCastingError () -> [ArithmeticException]: get exception to throw if number exceeds supported values
 * @return [Char] value as a char
 */
internal fun castToChar(value: BigDecimal, getCastingError: () -> ArithmeticException): Char {
    val minValue = Char.MIN_VALUE.code
    val maxValue = Char.MAX_VALUE.code
    val code = castNumber(value, minValue, maxValue, BigDecimal::toInt, getCastingError, false)
    return code.toChar()
}

/**
 * Cast a number to Short, or throw the given exception if number exceeds values for Short
 *
 * @param value [BigDecimal]: number to cast
 * @param getCastingError () -> [ArithmeticException]: get exception to throw if number exceeds supported values
 * @return [Short] value as a short
 */
internal fun castToShort(value: BigDecimal, getCastingError: () -> ArithmeticException): Short {
    return castNumber(value, Short.MIN_VALUE, Short.MAX_VALUE, BigDecimal::toShort, getCastingError, false)
}

/**
 * Cast a number to Int, or throw the given exception if number exceeds values for Int
 *
 * @param value [BigDecimal]: number to cast
 * @param getCastingError () -> [ArithmeticException]: get exception to throw if number exceeds supported values
 * @return [Int] value as an int
 */
internal fun castToInt(value: BigDecimal, getCastingError: () -> ArithmeticException): Int {
    return castNumber(value, Int.MIN_VALUE, Int.MAX_VALUE, BigDecimal::toInt, getCastingError, false)
}

/**
 * Cast a number to Long, or throw the given exception if number exceeds values for Long
 *
 * @param value [BigDecimal]: number to cast
 * @param getCastingError () -> [ArithmeticException]: get exception to throw if number exceeds supported values
 * @return [Long] value as a long
 */
internal fun castToLong(value: BigDecimal, getCastingError: () -> ArithmeticException): Long {
    return castNumber(value, Long.MIN_VALUE, Long.MAX_VALUE, BigDecimal::toLong, getCastingError, false)
}

/**
 * Cast a number to Float, or throw the given exception if number exceeds values for Float
 *
 * @param value [BigDecimal]: number to cast
 * @param getCastingError () -> [ArithmeticException]: get exception to throw if number exceeds supported values
 * @return [Float] value as a float
 */
internal fun castToFloat(value: BigDecimal, getCastingError: () -> ArithmeticException): Float {
    return castNumber(value, -Float.MAX_VALUE, Float.MAX_VALUE, BigDecimal::toFloat, getCastingError, true)
}

/**
 * Cast a number to Double, or throw the given exception if number exceeds values for Double
 *
 * @param value [BigDecimal]: number to cast
 * @param getCastingError () -> [ArithmeticException]: get exception to throw if number exceeds supported values
 * @return [Double] value as a double
 */
internal fun castToDouble(value: BigDecimal, getCastingError: () -> ArithmeticException): Double {
    return castNumber(value, -Double.MAX_VALUE, Double.MAX_VALUE, BigDecimal::toDouble, getCastingError, true)
}

/**
 * Cast a number to another number type, or throw the given exception if the number exceeds the given min and max values
 *
 * @param value [BigDecimal]: number to cast
 * @param minValue T: minimum allowed value
 * @param maxValue T: maximum allowed value
 * @param cast (BigDecimal) -> T: function to cast [value]
 * @param getCastingError () -> [ArithmeticException]: get exception to throw if number exceeds supported values
 * @param isDecimal [Boolean]: flag to indicate if [value] is being cast to a whole number or a decimal
 * @return [T]: result of cast, if succeeded
 */
private fun <T : Number> castNumber(
    value: BigDecimal,
    minValue: T,
    maxValue: T,
    cast: (BigDecimal) -> T,
    getCastingError: () -> ArithmeticException,
    isDecimal: Boolean
): T {
    val minString = minValue.toString()
    val maxString = maxValue.toString()

    val validValue = if (isDecimal) {
        value in BigDecimal(minString)..BigDecimal(maxString)
    } else {
        val intValue = value.toBigInteger()
        intValue in BigInteger(minString)..BigInteger(maxString)
    }

    if (validValue) {
        return cast(value)
    }

    throw getCastingError()
}
