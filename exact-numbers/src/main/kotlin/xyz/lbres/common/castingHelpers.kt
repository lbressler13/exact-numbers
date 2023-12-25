package xyz.lbres.common

import java.math.BigDecimal
import java.math.BigInteger

private val errorMessage = "Value would overflow supported range"

internal fun <S : Number> castToWholeNumber(value: BigInteger, minValue: String, maxValue: String, cast: (BigInteger) -> S): S {
    val minInt = BigInteger(minValue)
    val maxInt = BigInteger(maxValue)
    if (value in minInt..maxInt) {
        return cast(value)
    }

    throw ArithmeticException(errorMessage)
}

internal fun <S : Number> castToDecimal(value: BigDecimal, minValue: S, maxValue: S, cast: (BigDecimal) -> S): S {
    val minDecimal = BigDecimal(minValue.toString())
    val maxDecimal = BigDecimal(maxValue.toString())
    if (value in minDecimal..maxDecimal) {
        return cast(value)
    }

    throw ArithmeticException(errorMessage)
}

internal fun castNumberToByte(value: BigDecimal): Byte {
    val minValue = Byte.MIN_VALUE.toString()
    val maxValue = Byte.MAX_VALUE.toString()
    return castToWholeNumber(value.toBigInteger(), minValue, maxValue, BigInteger::toByte)
}

internal fun castNumberToChar(value: BigDecimal): Char {
    val minValue = Char.MIN_VALUE.code.toString()
    val maxValue = Char.MAX_VALUE.code.toString()
    val code = castToWholeNumber(value.toBigInteger(), minValue, maxValue, BigInteger::toInt)
    return code.toChar()
}

internal fun castNumberToShort(value: BigDecimal): Short {
    val minValue = Short.MIN_VALUE.toString()
    val maxValue = Short.MAX_VALUE.toString()
    return castToWholeNumber(value.toBigInteger(), minValue, maxValue, BigInteger::toShort)
}

internal fun castNumberToInt(value: BigDecimal): Int {
    val minValue = Int.MIN_VALUE.toString()
    val maxValue = Int.MAX_VALUE.toString()
    return castToWholeNumber(value.toBigInteger(), minValue, maxValue, BigInteger::toInt)
}

internal fun castNumberToLong(value: BigDecimal): Long {
    val minValue = Long.MIN_VALUE.toString()
    val maxValue = Long.MAX_VALUE.toString()
    return castToWholeNumber(value.toBigInteger(), minValue, maxValue, BigInteger::toLong)
}

internal fun castNumberToFloat(value: BigDecimal): Float {
    return castToDecimal(value, -Float.MAX_VALUE, Float.MAX_VALUE, BigDecimal::toFloat)
}

internal fun castNumberToDouble(value: BigDecimal): Double {
    return castToDecimal(value, -Double.MAX_VALUE, Double.MAX_VALUE, BigDecimal::toDouble)
}
