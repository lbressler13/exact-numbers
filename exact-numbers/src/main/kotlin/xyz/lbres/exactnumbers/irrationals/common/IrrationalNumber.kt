package xyz.lbres.exactnumbers.irrationals.common

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

    override fun toByte(): Byte = getValue().toByte()
    override fun toChar(): Char = getValue().toInt().toChar()
    override fun toShort(): Short = getValue().toShort()
    override fun toInt(): Int = getValue().toInt()
    override fun toLong(): Long = getValue().toLong()
    override fun toDouble(): Double = getValue().toDouble()
    override fun toFloat(): Float = getValue().toFloat()
}
