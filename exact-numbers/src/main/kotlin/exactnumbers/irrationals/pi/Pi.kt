package exactnumbers.irrationals.pi

import common.divideBigDecimals
import exactnumbers.irrationals.common.Irrational
import exactnumbers.irrationals.common.div
import exactnumbers.irrationals.common.times
import exactnumbers.irrationals.log.Log
import expressions.term.Term
import java.math.BigDecimal
import kotlin.math.PI
import kotlin.math.abs

/**
 * Representation of pi, with a rational coefficient
 */
class Pi(override val isDivided: Boolean) : Irrational {
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

    operator fun times(other: Log): Term = times(other as Irrational)
    operator fun times(other: Pi): Term = times(other as Irrational)
    operator fun div(other: Log): Term = div(other as Irrational)
    operator fun div(other: Pi): Term = div(other as Irrational)

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

        internal fun simplifyList(numbers: List<Irrational>?): List<Pi> {
            if (numbers.isNullOrEmpty()) {
                return listOf()
            }

            numbers as List<Pi>

            val positive = numbers.count { !it.isDivided }
            val negative = numbers.size - positive
            val diff = abs(positive - negative)

            return when {
                positive == negative -> listOf()
                positive < negative -> List(diff) { Pi(isDivided = true) }
                else -> List(diff) { Pi(isDivided = false) }
            }
        }
    }
}
