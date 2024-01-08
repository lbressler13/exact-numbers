package xyz.lbres.exactnumbers.common

import java.math.BigDecimal
import java.math.BigInteger

/**
 * Creating a CastingOverflowException based on a value and target type
 *
 * @param value [Any]: overflow value
 * @param targetType [String]: type being cast to
 * @param valueString [String]: string representation of value. Defaults to `value.toString()`
 * @return [CastingOverflowException] with overflowValue and message based on the provided values
 */
private fun createCastingException(value: Any, targetType: String, valueString: String = value.toString()): CastingOverflowException {
    return CastingOverflowException(value::class.simpleName ?: value::class.toString(), targetType, valueString, value)
}

/**
 * Cast a number to Byte, or throw the given exception if number exceeds values for Byte
 *
 * @param decimal [BigDecimal]: number to cast as decimal
 * @param value [T]: number to cast
 * @param valueString [String]: string representation of value. Defaults to `value.toString()`
 * @return [Byte] value as a byte
 */
internal fun <T> castToByte(decimal: BigDecimal, value: T, valueString: String = value.toString()): Byte {
    val createException = { createCastingException(value as Any, "Byte", valueString) }
    return castNumber(decimal, Byte.MIN_VALUE, Byte.MAX_VALUE, BigDecimal::toByte, createException, false)
}

/**
 * Cast a number to Char, or throw the given exception if number exceeds values for Char
 *
 * @param decimal [BigDecimal]: number to cast as decimal
 * @param value [T]: number to cast
 * @param valueString [String]: string representation of value. Defaults to `value.toString()`
 * @return [Char] value as a char
 */
internal fun <T> castToChar(decimal: BigDecimal, value: T, valueString: String = value.toString()): Char {
    val createException = { createCastingException(value as Any, "Char", valueString) }

    val minValue = Char.MIN_VALUE.code
    val maxValue = Char.MAX_VALUE.code
    val code = castNumber(decimal, minValue, maxValue, BigDecimal::toInt, createException, false)
    return code.toChar()
}

/**
 * Cast a number to Short, or throw the given exception if number exceeds values for Short
 *
 * @param decimal [BigDecimal]: number to cast as decimal
 * @param value [T]: number to cast
 * @param valueString [String]: string representation of value. Defaults to `value.toString()`
 * @return [Short] value as a short
 */
internal fun <T> castToShort(decimal: BigDecimal, value: T, valueString: String = value.toString()): Short {
    val createException = { createCastingException(value as Any, "Short", valueString) }
    return castNumber(decimal, Short.MIN_VALUE, Short.MAX_VALUE, BigDecimal::toShort, createException, false)
}

/**
 * Cast a number to Int, or throw the given exception if number exceeds values for Int
 *
 * @param decimal [BigDecimal]: number to cast as decimal
 * @param value [T]: number to cast
 * @param valueString [String]: string representation of value. Defaults to `value.toString()`
 * @return [Int] value as an int
 */
internal fun <T> castToInt(decimal: BigDecimal, value: T, valueString: String = value.toString()): Int {
    val createException = { createCastingException(value as Any, "Int", valueString) }
    return castNumber(decimal, Int.MIN_VALUE, Int.MAX_VALUE, BigDecimal::toInt, createException, false)
}

/**
 * Cast a number to Long, or throw the given exception if number exceeds values for Long
 *
 * @param decimal [BigDecimal]: number to cast as decimal
 * @param value [T]: number to cast
 * @param valueString [String]: string representation of value. Defaults to `value.toString()`
 * @return [Long] value as a long
 */
internal fun <T> castToLong(decimal: BigDecimal, value: T, valueString: String = value.toString()): Long {
    val createException = { createCastingException(value as Any, "Long", valueString) }
    return castNumber(decimal, Long.MIN_VALUE, Long.MAX_VALUE, BigDecimal::toLong, createException, false)
}

/**
 * Cast a number to Float, or throw the given exception if number exceeds values for Float
 *
 * @param decimal [BigDecimal]: number to cast as decimal
 * @param value [T]: number to cast
 * @param valueString [String]: string representation of value. Defaults to `value.toString()`
 * @return [Float] value as a float
 */
internal fun <T> castToFloat(decimal: BigDecimal, value: T, valueString: String = value.toString()): Float {
    val createException = { createCastingException(value as Any, "Float", valueString) }
    return castNumber(decimal, -Float.MAX_VALUE, Float.MAX_VALUE, BigDecimal::toFloat, createException, true)
}

/**
 * Cast a number to Double, or throw the given exception if number exceeds values for Double
 *
 * @param decimal [BigDecimal]: number to cast as decimal
 * @param value [T]: number to cast
 * @param valueString [String]: string representation of value. Defaults to `value.toString()`
 * @return [Double] value as a double
 */
internal fun <T> castToDouble(decimal: BigDecimal, value: T, valueString: String = value.toString()): Double {
    val createException = { createCastingException(value as Any, "Double", valueString) }
    return castNumber(decimal, -Double.MAX_VALUE, Double.MAX_VALUE, BigDecimal::toDouble, createException, true)
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
