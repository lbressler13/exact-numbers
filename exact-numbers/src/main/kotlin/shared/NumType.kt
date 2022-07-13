package shared

import exactnumbers.exactfraction.ExactFraction
import expressions.term.Term
import java.math.BigDecimal

internal interface NumType {
    val type: String

    fun getValue(): BigDecimal
    fun isZero(): Boolean

    val isDivided: Boolean
    fun swapDivided(): NumType

    fun getBaseString(): String
}

internal operator fun NumType.times(other: NumType): Term = Term(ExactFraction.ONE, listOf(this, other))
