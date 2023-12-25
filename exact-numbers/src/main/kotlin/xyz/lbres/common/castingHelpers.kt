package xyz.lbres.common

import java.math.BigDecimal
import java.math.BigInteger

internal fun <S : Number> castToWholeNumber(value: BigInteger, minValue: String, maxValue: String, cast: (BigInteger) -> S, getCastingError: (BigDecimal) -> Exception): S {
    val minInt = BigInteger(minValue)
    val maxInt = BigInteger(maxValue)
    if (value in minInt..maxInt) {
        return cast(value)
    }

    throw getCastingError(value.toBigDecimal())
}

internal fun <S : Number> castToDecimal(value: BigDecimal, minValue: S, maxValue: S, cast: (BigDecimal) -> S, getCastingError: (BigDecimal) -> Exception): S {
    val minDecimal = BigDecimal(minValue.toString())
    val maxDecimal = BigDecimal(maxValue.toString())
    if (value in minDecimal..maxDecimal) {
        return cast(value)
    }

    throw getCastingError(value)
}

internal fun castNumberToByte(value: BigDecimal, getCastingError: (BigDecimal) -> Exception): Byte {
    val minValue = Byte.MIN_VALUE.toString()
    val maxValue = Byte.MAX_VALUE.toString()
    return castToWholeNumber(value.toBigInteger(), minValue, maxValue, BigInteger::toByte, getCastingError)
}

internal fun castNumberToChar(value: BigDecimal, getCastingError: (BigDecimal) -> Exception): Char {
    val minValue = Char.MIN_VALUE.code.toString()
    val maxValue = Char.MAX_VALUE.code.toString()
    val code = castToWholeNumber(value.toBigInteger(), minValue, maxValue, BigInteger::toInt, getCastingError)
    return code.toChar()
}

internal fun castNumberToShort(value: BigDecimal, getCastingError: (BigDecimal) -> Exception): Short {
    val minValue = Short.MIN_VALUE.toString()
    val maxValue = Short.MAX_VALUE.toString()
    return castToWholeNumber(value.toBigInteger(), minValue, maxValue, BigInteger::toShort, getCastingError)
}

internal fun castNumberToInt(value: BigDecimal, getCastingError: (BigDecimal) -> Exception): Int {
    val minValue = Int.MIN_VALUE.toString()
    val maxValue = Int.MAX_VALUE.toString()
    return castToWholeNumber(value.toBigInteger(), minValue, maxValue, BigInteger::toInt, getCastingError)
}

internal fun castNumberToLong(value: BigDecimal, getCastingError: (BigDecimal) -> Exception): Long {
    val minValue = Long.MIN_VALUE.toString()
    val maxValue = Long.MAX_VALUE.toString()
    return castToWholeNumber(value.toBigInteger(), minValue, maxValue, BigInteger::toLong, getCastingError)
}

internal fun castNumberToFloat(value: BigDecimal, getCastingError: (BigDecimal) -> Exception): Float {
    return castToDecimal(value, -Float.MAX_VALUE, Float.MAX_VALUE, BigDecimal::toFloat, getCastingError)
}

internal fun castNumberToDouble(value: BigDecimal, getCastingError: (BigDecimal) -> Exception): Double {
    return castToDecimal(value, -Double.MAX_VALUE, Double.MAX_VALUE, BigDecimal::toDouble, getCastingError)
}
