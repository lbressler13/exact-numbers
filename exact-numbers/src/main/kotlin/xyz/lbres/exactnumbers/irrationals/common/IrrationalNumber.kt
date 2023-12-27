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

abstract class IrrationalNumber<T : IrrationalNumber<T>> : Comparable<T>, Number() {

    abstract val type: String

    abstract fun getValue(): BigDecimal
    abstract fun isZero(): Boolean
    abstract fun isRational(): Boolean
    abstract fun getRationalValue(): ExactFraction?

    abstract val isDivided: Boolean
    abstract fun swapDivided(): T

    operator fun times(other: IrrationalNumber<*>): Term = Term(ExactFraction.ONE, listOf(this, other))
    operator fun div(other: IrrationalNumber<*>): Term {
        if (other.isZero()) {
            throw divideByZero
        }

        return Term(ExactFraction.ONE, listOf(this, other.swapDivided()))
    }

    override fun compareTo(other: T): Int = getValue().compareTo(other.getValue())

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
