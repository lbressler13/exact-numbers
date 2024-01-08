package xyz.lbres.exactnumbers.irrationals.common

import xyz.lbres.exactnumbers.common.castToByte
import xyz.lbres.exactnumbers.common.castToChar
import xyz.lbres.exactnumbers.common.castToDouble
import xyz.lbres.exactnumbers.common.castToFloat
import xyz.lbres.exactnumbers.common.castToInt
import xyz.lbres.exactnumbers.common.castToLong
import xyz.lbres.exactnumbers.common.castToShort
import xyz.lbres.exactnumbers.common.createCastingException
import xyz.lbres.exactnumbers.common.createHashCode
import xyz.lbres.exactnumbers.common.deprecatedV1
import xyz.lbres.exactnumbers.common.divideByZero
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.term.Term
import java.math.BigDecimal

/**
 * Representation of an irrational number
 */
abstract class IrrationalNumber<T : IrrationalNumber<T>> : Comparable<T>, Number() {
    private var isRational: Boolean? = null
    private var value: BigDecimal? = null
    private var rationalValue: ExactFraction? = null

    /**
     * Type of number, should correspond to the type name for the class
     */
    abstract val type: String

    /**
     * If the number is in the format `1/n`
     */
    abstract val isInverted: Boolean
    @Deprecated("Property $deprecatedV1", ReplaceWith("isInverted"), DeprecationLevel.WARNING)
    val isDivided: Boolean
        get() = isInverted

    /**
     * If the number is zero
     */
    abstract fun isZero(): Boolean

    /**
     * If the number is a rational value
     */
    fun isRational(): Boolean {
        if (isRational == null) {
            isRational = checkIsRational()
        }

        return isRational!!
    }

    /**
     * Get value of number
     */
    fun getValue(): BigDecimal {
        if (value == null) {
            value = performGetValue()
        }

        return value!!
    }

    /**
     * Get the value of the number as a BigInteger. Returns `null` if the value of the number is not rational.
     */
    fun getRationalValue(): ExactFraction? {
        if (!isRational()) {
            return null
        }

        if (rationalValue == null) {
            rationalValue = performGetRationalValue()
        }

        return rationalValue
    }

    // implementation-specific code for isRational, getValue, and getRationalValue
    protected abstract fun checkIsRational(): Boolean
    protected abstract fun performGetValue(): BigDecimal
    protected abstract fun performGetRationalValue(): ExactFraction?

    /**
     * Get multiplicative inverse
     */
    abstract fun inverse(): T

    @Deprecated("Method $deprecatedV1", ReplaceWith("inverse"), DeprecationLevel.WARNING)
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

    override fun toByte(): Byte = castToByte(getValue()) { createCastingException(this, "Byte") }
    override fun toChar(): Char = castToChar(getValue()) { createCastingException(this, "Char") }
    override fun toShort(): Short = castToShort(getValue()) { createCastingException(this, "Short") }
    override fun toInt(): Int = castToInt(getValue()) { createCastingException(this, "Int") }
    override fun toLong(): Long = castToLong(getValue()) { createCastingException(this, "Long") }

    override fun toFloat(): Float = castToFloat(getValue()) { createCastingException(this, "Float") }
    override fun toDouble(): Double = castToDouble(getValue()) { createCastingException(this, "Double") }

    override fun hashCode(): Int = createHashCode(listOf(getValue(), this::class.toString()))
}
