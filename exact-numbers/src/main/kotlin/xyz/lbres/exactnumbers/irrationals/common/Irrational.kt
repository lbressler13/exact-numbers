package xyz.lbres.exactnumbers.irrationals.common

import xyz.lbres.exactnumbers.common.CastingOverflowException
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Values needed for representation of an irrational number
 */
abstract class Irrational<T> internal constructor() : Comparable<T>, Number() {
    /**
     * Type of number, should correspond to the type name for the class
     */
    abstract val type: String

    /**
     * Perform computation to get value of number
     */
    abstract fun getValue(): BigDecimal

    /**
     * If the number is zero
     */
    abstract fun isZero(): Boolean

    /**
     * If the number is a rational value
     */
    abstract fun isRational(): Boolean

    /**
     * Get the value of the number as an ExactFraction instead of BigDecimal if number is rational. Returns null if it is not.
     */
    abstract fun getRationalValue(): ExactFraction?

    /**
     * If the number is in the format `1/n`. True if the number is in that format, false otherwise
     */
    abstract val isInverted: Boolean

    /**
     * Get the value of the number such that `n * n.inverse() == 0`
     */
    abstract fun inverse(): T

    /**
     * Round the value to the nearest whole number, and check if it is in the overflow range.
     *
     * @param minValue [String]: minimum value that can be cast to without causing overflow
     * @param maxValue [String]: maximum value that can be cast to without causing overflow
     * @param targetType [String]: name of type to display in overflow error, if applicable
     * @return [BigDecimal]: the value of the irrational, rounded to the nearest whole number
     * @throws [CastingOverflowException] if the rounded value is not in the allowed overflow range
     */
    private fun getRoundedAndCheckOverflow(minValue: String, maxValue: String, targetType: String): BigDecimal {
        val roundedValue = getValue().setScale(0, RoundingMode.HALF_UP)

        val minDecimal = BigDecimal(minValue)
        val maxDecimal = BigDecimal(maxValue)
        if (roundedValue < minDecimal || roundedValue > maxDecimal) {
            throw CastingOverflowException(type, targetType, toString(), this)
        }

        return roundedValue
    }

    /**
     * Cast number to [Byte]. Requires rounding to whole number, and throws [CastingOverflowException] if value is not in the allowed range for byte.
     */
    override fun toByte(): Byte {
        val roundedValue = getRoundedAndCheckOverflow(Byte.MIN_VALUE.toString(), Byte.MAX_VALUE.toString(), "Byte")
        return roundedValue.toByte()
    }

    /**
     * Cast number to [Char]. Requires rounding to whole number, and throws [CastingOverflowException] if value is not in the allowed range for char.
     */
    override fun toChar(): Char {
        val maxAsInt = Char.MAX_VALUE.code
        val minAsInt = Char.MIN_VALUE.code
        val roundedValue = getRoundedAndCheckOverflow(minAsInt.toString(), maxAsInt.toString(), "Char")
        return roundedValue.toInt().toChar()
    }

    /**
     * Cast number to [Short]. Requires rounding to whole number, and throws [CastingOverflowException] if value is not in the allowed range for short.
     */
    override fun toShort(): Short {
        val roundedValue = getRoundedAndCheckOverflow(Short.MIN_VALUE.toString(), Short.MAX_VALUE.toString(), "Short")
        return roundedValue.toShort()
    }

    /**
     * Cast number to [Int]. Requires rounding to whole number, and throws [CastingOverflowException] if value is not in the allowed range for int.
     */
    override fun toInt(): Int {
        val roundedValue = getRoundedAndCheckOverflow(Int.MIN_VALUE.toString(), Int.MAX_VALUE.toString(), "Int")
        return roundedValue.toInt()
    }

    /**
     * Cast number to [Long]. Requires rounding to whole number, and throws [CastingOverflowException] if value is not in the allowed range for long.
     */
    override fun toLong(): Long {
        val roundedValue = getRoundedAndCheckOverflow(Long.MIN_VALUE.toString(), Long.MAX_VALUE.toString(), "Long")
        return roundedValue.toLong()
    }

    /**
     * Cast number to [Float]. Throws [CastingOverflowException] if value is not in the allowed range for float.
     */
    override fun toFloat(): Float {
        getRoundedAndCheckOverflow((-Float.MAX_VALUE).toString(), Float.MAX_VALUE.toString(), "Float")
        return getValue().toFloat()
    }

    /**
     * Cast number to [Double]. Throws [CastingOverflowException] if value is not in the allowed range for double.
     */
    override fun toDouble(): Double {
        getRoundedAndCheckOverflow((-Double.MAX_VALUE).toString(), Double.MAX_VALUE.toString(), "Double")
        return getValue().toDouble()
    }
}
