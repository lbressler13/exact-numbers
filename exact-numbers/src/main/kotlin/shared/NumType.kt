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
}

internal operator fun NumType.times(other: NumType): Term = Term(ExactFraction.ONE, listOf(this, other))
internal operator fun NumType.div(other: NumType): Term {
    if (other.isZero()) {
        throwDivideByZero()
    }

    return Term(ExactFraction.ONE, listOf(this, other.swapDivided()))
}
