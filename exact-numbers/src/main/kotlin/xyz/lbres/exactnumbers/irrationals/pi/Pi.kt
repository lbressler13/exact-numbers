package xyz.lbres.exactnumbers.irrationals.pi

import xyz.lbres.common.divideBigDecimals
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.Irrational
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.expressions.term.Term
import xyz.lbres.kotlinutils.general.ternaryIf
import java.math.BigDecimal
import kotlin.math.PI
import kotlin.math.abs

/**
 * Representation of pi
 *
 * @param isDivided [Boolean]: if the inverse of the value should be calculated
 */
class Pi(override val isDivided: Boolean) : Comparable<Pi>, Irrational {
    override val type: String = TYPE

    // constructor with reduced params
    constructor() : this(false)

    override fun getValue(): BigDecimal {
        val base = PI.toBigDecimal()

        if (isDivided) {
            return divideBigDecimals(BigDecimal.ONE, base)
        }

        return base
    }

    override fun isZero(): Boolean = false

    override fun isRational(): Boolean = false

    override fun getRationalValue(): ExactFraction? = null

    override fun swapDivided(): Pi = Pi(!isDivided)

    override fun equals(other: Any?): Boolean {
        return other != null &&
            other is Pi &&
            isDivided == other.isDivided
    }

    override fun compareTo(other: Pi): Int {
        return when {
            !isDivided && other.isDivided -> 1
            isDivided && !other.isDivided -> -1
            else -> 0
        }
    }

    operator fun times(other: Log): Term = Term.fromValues(listOf(other), listOf(this))
    operator fun times(other: Pi): Term = Term.fromValues(listOf(this, other))
    operator fun times(other: Sqrt): Term = Term.fromValues(listOf(other), listOf(this))
    operator fun div(other: Log): Term = Term.fromValues(listOf(other.swapDivided()), listOf(this))
    operator fun div(other: Pi): Term = Term.fromValues(listOf(this, other.swapDivided()))
    operator fun div(other: Sqrt): Term = Term.fromValues(listOf(other.swapDivided()), listOf(this))

    override fun toString(): String {
        val pi = "π"
        return ternaryIf(isDivided, "[1/$pi]", "[$pi]")
    }

    override fun hashCode(): Int = listOf(TYPE, PI, isDivided).hashCode()

    companion object {
        const val TYPE = "pi"

        /**
         * Simplify list of pis
         *
         * @param numbers [List<Pi>] : list to simplify
         * @return [List<Pi>]: simplified list
         * @throws [ClassCastException] if any of the numbers are not a Pi
         */
        internal fun simplifyList(numbers: List<Pi>?): List<Pi> {
            if (numbers.isNullOrEmpty()) {
                return emptyList()
            }

            val positive = numbers.count { !it.isDivided }
            val negative = numbers.size - positive
            val diff = abs(positive - negative)

            return when {
                positive == negative -> emptyList()
                positive < negative -> List(diff) { Pi(isDivided = true) }
                else -> List(diff) { Pi(isDivided = false) }
            }
        }
    }
}
