package xyz.lbres.exactnumbers.utils

import xyz.lbres.exactnumbers.exceptions.CastingOverflowException
import java.math.BigDecimal
import java.math.BigInteger

/**
 * Cast a number to Byte, or throw an exception if number exceeds values for Byte
 *
 * @param decimal [BigDecimal]: number to cast as decimal
 * @param value [T]: number to cast
 * @param type [String]: base type
 * @return [Byte] value as a byte
 */
internal fun <T> castToByte(decimal: BigDecimal, value: T, type: String): Byte {
    val createException = { CastingOverflowException(type, "Byte", value.toString(), value) }
    return castNumber(decimal, Byte.MIN_VALUE, Byte.MAX_VALUE, BigDecimal::toByte, createException, false)
}

/**
 * Cast a number to Char, or throw an exception if number exceeds values for Char
 *
 * @param decimal [BigDecimal]: number to cast as decimal
 * @param value [T]: number to cast
 * @param type [String]: base type
 * @return [Char] value as a char
 */
internal fun <T> castToChar(decimal: BigDecimal, value: T, type: String): Char {
    val createException = { CastingOverflowException(type, "Char", value.toString(), value) }

    val minValue = Char.MIN_VALUE.code
    val maxValue = Char.MAX_VALUE.code
    val code = castNumber(decimal, minValue, maxValue, BigDecimal::toInt, createException, false)
    return code.toChar()
}

/**
 * Cast a number to Short, or throw an exception if number exceeds values for Short
 *
 * @param decimal [BigDecimal]: number to cast as decimal
 * @param value [T]: number to cast
 * @param type [String]: base type
 * @return [Short] value as a short
 */
internal fun <T> castToShort(decimal: BigDecimal, value: T, type: String): Short {
    val createException = { CastingOverflowException(type, "Short", value.toString(), value) }
    return castNumber(decimal, Short.MIN_VALUE, Short.MAX_VALUE, BigDecimal::toShort, createException, false)
}

/**
 * Cast a number to Int, or throw an exception if number exceeds values for Int
 *
 * @param decimal [BigDecimal]: number to cast as decimal
 * @param value [T]: number to cast
 * @param type [String]: base type
 * @return [Int] value as an int
 */
internal fun <T> castToInt(decimal: BigDecimal, value: T, type: String): Int {
    val createException = { CastingOverflowException(type, "Int", value.toString(), value) }
    return castNumber(decimal, Int.MIN_VALUE, Int.MAX_VALUE, BigDecimal::toInt, createException, false)
}

/**
 * Cast a number to Long, or throw an exception if number exceeds values for Long
 *
 * @param decimal [BigDecimal]: number to cast as decimal
 * @param value [T]: number to cast
 * @param type [String]: base type
 * @return [Long] value as a long
 */
internal fun <T> castToLong(decimal: BigDecimal, value: T, type: String): Long {
    val createException = { CastingOverflowException(type, "Long", value.toString(), value) }
    return castNumber(decimal, Long.MIN_VALUE, Long.MAX_VALUE, BigDecimal::toLong, createException, false)
}

/**
 * Cast a number to Float, or throw an exception if number exceeds values for Float
 *
 * @param decimal [BigDecimal]: number to cast as decimal
 * @param value [T]: number to cast
 * @param type [String]: base type
 * @return [Float] value as a float
 */
internal fun <T> castToFloat(decimal: BigDecimal, value: T, type: String): Float {
    val createException = { CastingOverflowException(type, "Float", value.toString(), value) }
    return castNumber(decimal, -Float.MAX_VALUE, Float.MAX_VALUE, BigDecimal::toFloat, createException, true)
}

/**
 * Cast a number to Double, or throw an exception if number exceeds values for Double
 *
 * @param decimal [BigDecimal]: number to cast as decimal
 * @param value [T]: number to cast
 * @param type [String]: base type
 * @return [Double] value as a double
 */
internal fun <T> castToDouble(decimal: BigDecimal, value: T, type: String): Double {
    val createException = { CastingOverflowException(type, "Double", value.toString(), value) }
    return castNumber(decimal, -Double.MAX_VALUE, Double.MAX_VALUE, BigDecimal::toDouble, createException, true)
}

/**
 * Cast a number to another number type, or throw the given exception if the number exceeds the given min and max values
 *
 * @param value [BigDecimal]: number to cast
 * @param minValue T: minimum allowed value
 * @param maxValue T: maximum allowed value
 * @param cast (BigDecimal) -> T: function to cast [value]
 * @param getOverflowException () -> [CastingOverflowException]: get exception to throw if number exceeds supported values
 * @param isDecimal [Boolean]: flag to indicate if [value] is being cast to a whole number or a decimal
 * @return [T]: result of cast, if succeeded
 */
private fun <T : Number> castNumber(
    value: BigDecimal,
    minValue: T,
    maxValue: T,
    cast: (BigDecimal) -> T,
    getOverflowException: () -> CastingOverflowException,
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

    throw getOverflowException()
}
