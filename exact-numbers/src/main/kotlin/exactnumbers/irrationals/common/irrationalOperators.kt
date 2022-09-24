package exactnumbers.irrationals.common

import common.divideByZero
import exactnumbers.exactfraction.ExactFraction
import expressions.term.Term

internal operator fun Irrational.times(other: Irrational): Term = Term(ExactFraction.ONE, listOf(this, other))
internal operator fun Irrational.div(other: Irrational): Term {
    if (other.isZero()) {
        throw divideByZero
    }

    return Term(ExactFraction.ONE, listOf(this, other.swapDivided()))
}
