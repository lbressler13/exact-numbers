package xyz.lbres.common

import java.math.BigDecimal
import java.math.BigInteger

internal fun castNumberToByte(value: BigDecimal, getCastingError: (BigDecimal) -> Exception): Byte {
    return castNumber(value, Byte.MIN_VALUE, Byte.MAX_VALUE, BigDecimal::toByte, getCastingError, false)
}

internal fun castNumberToChar(value: BigDecimal, getCastingError: (BigDecimal) -> Exception): Char {
    val minValue = Char.MIN_VALUE.code
    val maxValue = Char.MAX_VALUE.code
    val code = castNumber(value, minValue, maxValue, BigDecimal::toInt, getCastingError, false)
    return code.toChar()
}

internal fun castNumberToShort(value: BigDecimal, getCastingError: (BigDecimal) -> Exception): Short {
    return castNumber(value, Short.MIN_VALUE, Short.MAX_VALUE, BigDecimal::toShort, getCastingError, false)
}

internal fun castNumberToInt(value: BigDecimal, getCastingError: (BigDecimal) -> Exception): Int {
    return castNumber(value, Int.MIN_VALUE, Int.MAX_VALUE, BigDecimal::toInt, getCastingError, false)
}

internal fun castNumberToLong(value: BigDecimal, getCastingError: (BigDecimal) -> Exception): Long {
    return castNumber(value, Long.MIN_VALUE, Long.MAX_VALUE, BigDecimal::toLong, getCastingError, false)
}

internal fun castNumberToFloat(value: BigDecimal, getCastingError: (BigDecimal) -> Exception): Float {
    return castNumber(value, -Float.MAX_VALUE, Float.MAX_VALUE, BigDecimal::toFloat, getCastingError, true)
}

internal fun castNumberToDouble(value: BigDecimal, getCastingError: (BigDecimal) -> Exception): Double {
    return castNumber(value, -Double.MAX_VALUE, Double.MAX_VALUE, BigDecimal::toDouble, getCastingError, true)
}

private fun <T : Number> castNumber(value: BigDecimal, minValue: T, maxValue: T, cast: (BigDecimal) -> T, getCastingError: (BigDecimal) -> Exception, isDecimal: Boolean): T {
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

    throw getCastingError(value)
}
