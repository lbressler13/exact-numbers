package xyz.lbres.exactnumbers.irrationals.common

import xyz.lbres.common.*
import xyz.lbres.common.castNumberToByte
import xyz.lbres.common.castNumberToLong
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

    override fun toByte(): Byte = castNumberToByte(getValue())
    override fun toChar(): Char = castNumberToChar(getValue())
    override fun toShort(): Short = castNumberToShort(getValue())
    override fun toInt(): Int = castNumberToInt(getValue())
    override fun toLong(): Long = castNumberToLong(getValue())

    override fun toFloat(): Float = castNumberToFloat(getValue())
    override fun toDouble(): Double = castNumberToDouble(getValue())
}
