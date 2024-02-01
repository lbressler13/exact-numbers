package xyz.lbres.exactnumbers.irrationals

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.utils.castToByte
import xyz.lbres.exactnumbers.utils.castToChar
import xyz.lbres.exactnumbers.utils.castToDouble
import xyz.lbres.exactnumbers.utils.castToFloat
import xyz.lbres.exactnumbers.utils.castToInt
import xyz.lbres.exactnumbers.utils.castToLong
import xyz.lbres.exactnumbers.utils.castToShort
import xyz.lbres.exactnumbers.utils.createHashCode
import xyz.lbres.exactnumbers.utils.deprecatedV1
import xyz.lbres.exactnumbers.utils.divideByZero
import java.math.BigDecimal

/**
 * Representation of an irrational number
 */
abstract class IrrationalNumber<T : IrrationalNumber<T>> : Comparable<T>, Number() {
    /**
     * Type label for number
     */
    abstract val type: String

    /**
     * If the number is in the format `1/n`
     */
    abstract val isInverted: Boolean
    @Deprecated("Property $deprecatedV1", ReplaceWith("isInverted"), DeprecationLevel.WARNING)
    val isDivided: Boolean // maintained from Irrational interface
        get() = isInverted

    /**
     * If the number is zero
     */
    abstract fun isZero(): Boolean

    /**
     * If the number is a rational value
     */
    abstract fun isRational(): Boolean

    /**
     * Get value of number
     */
    abstract fun getValue(): BigDecimal

    /**
     * Get the value of the number as an ExactFraction. Returns `null` if the value of the number is not rational.
     */
    abstract fun getRationalValue(): ExactFraction?

    /**
     * Get multiplicative inverse
     */
    abstract fun inverse(): T

    @Deprecated("Method $deprecatedV1", ReplaceWith("inverse"), DeprecationLevel.WARNING)
    fun swapDivided(): T = inverse() // maintained from Irrational interface

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

    override fun compareTo(other: T): Int = getValue().compareTo(other.getValue())

    override fun equals(other: Any?): Boolean {
        return other is IrrationalNumber<*> && other.type == type && getValue() == other.getValue()
    }

    override fun toByte(): Byte = castToByte(getValue(), this, type)
    override fun toChar(): Char = castToChar(getValue(), this, type)
    override fun toShort(): Short = castToShort(getValue(), this, type)
    override fun toInt(): Int = castToInt(getValue(), this, type)
    override fun toLong(): Long = castToLong(getValue(), this, type)
    override fun toFloat(): Float = castToFloat(getValue(), this, type)
    override fun toDouble(): Double = castToDouble(getValue(), this, type)

    override fun hashCode(): Int = createHashCode(listOf(getValue(), type))
}
