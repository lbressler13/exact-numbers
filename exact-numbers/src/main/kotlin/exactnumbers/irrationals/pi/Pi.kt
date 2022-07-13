package exactnumbers.irrationals.pi

import exactnumbers.irrationals.logs.LogNum
import expressions.term.Term
import shared.NumType
import shared.div
import shared.divideBigDecimals
import shared.times
import java.math.BigDecimal
import kotlin.math.PI

/**
 * Representation of pi, with a rational coefficient
 */
class Pi(override val isDivided: Boolean) : NumType {
    override val type: String = TYPE

    constructor() : this(false)

    override fun getValue(): BigDecimal {
        val base = PI.toBigDecimal()

        if (isDivided) {
            return divideBigDecimals(BigDecimal.ONE, base)
        }

        return base
    }

    override fun isZero(): Boolean = false

    override fun swapDivided(): Pi = Pi(!isDivided)

    override fun equals(other: Any?): Boolean {
        return other != null &&
            other is Pi &&
            isDivided == other.isDivided
    }

    operator fun times(other: LogNum): Term = times(other as NumType)
    operator fun times(other: Pi): Term = times(other as NumType)
    operator fun div(other: LogNum): Term = div(other as NumType)
    operator fun div(other: Pi): Term = div(other as NumType)

    override fun toString(): String {
        val pi = "π"

        return if (isDivided) {
            "[1/$pi]"
        } else {
            "[$pi]"
        }
    }

    override fun hashCode(): Int = PI.hashCode()

    companion object {
        const val TYPE = "pi"
    }
}
