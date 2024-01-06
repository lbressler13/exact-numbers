package xyz.lbres.exactnumbers.irrationals.common

import xyz.lbres.common.castToByte
import xyz.lbres.common.castToChar
import xyz.lbres.common.castToDouble
import xyz.lbres.common.castToFloat
import xyz.lbres.common.castToInt
import xyz.lbres.common.castToLong
import xyz.lbres.common.castToShort
import xyz.lbres.common.divideByZero
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.expressions.term.Term
import java.math.BigDecimal

/**
 * Representation of an irrational number
 */
abstract class IrrationalNumber<T : IrrationalNumber<T>> : Comparable<T>, Number() {
    private var _isRational: Boolean? = null
    private var _value: BigDecimal? = null
    private var _rationalValue: ExactFraction? = null

    /**
     * Type of number, should correspond to the type name for the class
     */
    abstract val type: String

    /**
     * If the number is in the format `1/n`. True if the number is in that format, false otherwise
     */
    abstract val isInverted: Boolean
    @Deprecated("Property deprecated in v1.0", ReplaceWith("isInverted"), DeprecationLevel.WARNING)
    val isDivided: Boolean
        get() { return isInverted }

    /**
     * If the number is zero
     */
    abstract fun isZero(): Boolean

    /**
     * If the number is a rational value
     */
    fun isRational(): Boolean {
        if (_isRational == null) {
            _isRational = checkIsRational()
        }

        return _isRational!!
    }

    /**
     * Get value of number
     */
    fun getValue(): BigDecimal {
        if (_value == null) {
            _value = performGetValue()
        }

        return _value!!
    }

    /**
     * Get the value of the number as a rational number. Return `null` if the number is irrational.
     */
    fun getRationalValue(): ExactFraction? {
        if (!isRational()) {
            return null
        }

        if (_rationalValue == null) {
            _rationalValue = performGetRationalValue()
        }

        return _rationalValue
    }

    // implementation-specific code for isRational, getValue, and getRationalValue
    protected abstract fun checkIsRational(): Boolean
    protected abstract fun performGetValue(): BigDecimal
    protected abstract fun performGetRationalValue(): ExactFraction?

    /**
     * Get the value of the number such that `n * n.inverse() == 0`
     */
    abstract fun inverse(): T

    @Deprecated("Method deprecated in v1.0", ReplaceWith("inverse"), DeprecationLevel.WARNING)
    fun swapDivided(): T = inverse()

    operator fun times(other: IrrationalNumber<*>): Term = Term.fromValues(ExactFraction.ONE, listOf(this, other))
    operator fun times(other: ExactFraction): Term = Term.fromValues(other, listOf(this))

    operator fun div(other: IrrationalNumber<*>): Term {
        if (other.isZero()) {
            throw divideByZero
        }

        return Term.fromValues(ExactFraction.ONE, listOf(this, other.inverse()))
    }
    operator fun div(other: ExactFraction): Term {
        if (other.isZero()) {
            throw divideByZero
        }

        return Term.fromValues(other.inverse(), listOf(this))
    }

    override operator fun compareTo(other: T): Int = getValue().compareTo(other.getValue())

    override fun equals(other: Any?): Boolean {
        return other is IrrationalNumber<*> && other.type == type && getValue() == other.getValue()
    }

    override fun toByte(): Byte = castToByte(getValue(), getCastingError)
    override fun toChar(): Char = castToChar(getValue(), getCastingError)
    override fun toShort(): Short = castToShort(getValue(), getCastingError)
    override fun toInt(): Int = castToInt(getValue(), getCastingError)
    override fun toLong(): Long = castToLong(getValue(), getCastingError)

    override fun toFloat(): Float = castToFloat(getValue(), getCastingError)
    override fun toDouble(): Double = castToDouble(getValue(), getCastingError)

    private val getCastingError: () -> ArithmeticException = {
        ArithmeticException("Value would overflow supported range")
    }
}
