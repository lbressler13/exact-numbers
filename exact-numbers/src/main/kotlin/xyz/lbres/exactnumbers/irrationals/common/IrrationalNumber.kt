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

    abstract val type: String
    abstract val isDivided: Boolean

    /**
     * If the number is equal to zero
     *
     * @return [Boolean]: `true` if number is zero, `false` otherwise
     */
    abstract fun isZero(): Boolean

    /**
     * Determine if the value of the number is a rational number
     *
     * @return [Boolean]: `true` if the value is rational, `false` otherwise
     */
    fun isRational(): Boolean {
        if (_isRational == null) {
            _isRational = checkIsRational()
        }

        return _isRational!!
    }

    /**
     * Get value of the number
     *
     * @return [BigDecimal]
     */
    fun getValue(): BigDecimal {
        if (_value == null) {
            _value = performGetValue()
        }

        return _value!!
    }

    /**
     * Get the value of the number as a rational number
     *
     * @return [ExactFraction]: value of the number, or `null` if the number is irrational
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
     * Calculate the multiplicative inverse of the number
     *
     * @return T: multiplicative inverse
     */
    abstract fun swapDivided(): T

    operator fun times(other: IrrationalNumber<*>): Term = Term(ExactFraction.ONE, listOf(this, other))
    operator fun div(other: IrrationalNumber<*>): Term {
        if (other.isZero()) {
            throw divideByZero
        }

        return Term(ExactFraction.ONE, listOf(this, other.swapDivided()))
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
