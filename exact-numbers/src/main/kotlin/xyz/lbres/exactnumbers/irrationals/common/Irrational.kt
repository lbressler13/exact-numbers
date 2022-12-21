package xyz.lbres.exactnumbers.irrationals.common

import xyz.lbres.exactnumbers.common.CastingOverflowException
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import java.math.BigDecimal
import java.math.RoundingMode

abstract class Irrational<T> internal constructor() : Comparable<T>, Number() {
    abstract val type: String

    abstract fun getValue(): BigDecimal
    abstract fun isZero(): Boolean
    abstract fun isRational(): Boolean
    abstract fun getRationalValue(): ExactFraction?

    abstract val isInverted: Boolean
    abstract fun inverse(): T

    private fun getRoundedAndCheckOverflow(minValue: String, maxValue: String, targetType: String): BigDecimal {
        val roundedValue = getValue().setScale(0, RoundingMode.HALF_UP)

        val minDecimal = BigDecimal(minValue)
        val maxDecimal = BigDecimal(maxValue)
        if (roundedValue < minDecimal || roundedValue > maxDecimal) {
            throw CastingOverflowException(type, targetType, toString(), this)
        }

        return roundedValue
    }

    override fun toByte(): Byte {
        val roundedValue = getRoundedAndCheckOverflow(Byte.MIN_VALUE.toString(), Byte.MAX_VALUE.toString(), "Byte")
        return roundedValue.toByte()
    }

    override fun toChar(): Char {
        val maxAsInt = Char.MAX_VALUE.code
        val minAsInt = Char.MIN_VALUE.code
        val roundedValue = getRoundedAndCheckOverflow(minAsInt.toString(), maxAsInt.toString(), "Char")
        return roundedValue.toInt().toChar()
    }

    override fun toShort(): Short {
        val roundedValue = getRoundedAndCheckOverflow(Short.MIN_VALUE.toString(), Short.MAX_VALUE.toString(), "Short")
        return roundedValue.toShort()
    }

    override fun toInt(): Int {
        val roundedValue = getRoundedAndCheckOverflow(Int.MIN_VALUE.toString(), Int.MAX_VALUE.toString(), "Int")
        return roundedValue.toInt()
    }

    override fun toLong(): Long {
        val roundedValue = getRoundedAndCheckOverflow(Long.MIN_VALUE.toString(), Long.MAX_VALUE.toString(), "Long")
        return roundedValue.toLong()
    }

    override fun toFloat(): Float {
        getRoundedAndCheckOverflow((-Float.MAX_VALUE).toString(), Float.MAX_VALUE.toString(), "Float")
        return getValue().toFloat()
    }

    override fun toDouble(): Double {
        getRoundedAndCheckOverflow((-Double.MAX_VALUE).toString(), Double.MAX_VALUE.toString(), "Double")
        return getValue().toDouble()
    }
}
