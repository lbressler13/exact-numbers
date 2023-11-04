package xyz.lbres.exactnumbers.irrationals.common

import xyz.lbres.common.divideByZero
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.expressions.term.Term
import java.math.BigDecimal

/**
 * Values needed for representation of an irrational number
 */
internal interface Irrational {
    val type: String

    fun getValue(): BigDecimal
    fun isZero(): Boolean
    fun isRational(): Boolean
    fun getRationalValue(): ExactFraction?

    val isDivided: Boolean
    fun swapDivided(): Irrational

    operator fun times(other: Irrational): Term = Term(ExactFraction.ONE, listOf(this, other))
    operator fun div(other: Irrational): Term {
        if (other.isZero()) {
            throw divideByZero
        }

        return Term(ExactFraction.ONE, listOf(this, other.swapDivided()))
    }
}
