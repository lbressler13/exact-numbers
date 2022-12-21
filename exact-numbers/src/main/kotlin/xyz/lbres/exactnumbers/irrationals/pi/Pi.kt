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
 * @param isInverted [Boolean]: if the inverse of the value should be calculated
 */
class Pi private constructor(override val isInverted: Boolean) : Comparable<Pi>, Irrational {
    override val type: String = TYPE

    // constructor with reduced params
    constructor() : this(false)

    override fun getValue(): BigDecimal {
        val base = PI.toBigDecimal()

        if (isInverted) {
            return divideBigDecimals(BigDecimal.ONE, base)
        }

        return base
    }

    override fun isZero(): Boolean = false

    override fun isRational(): Boolean = false

    override fun getRationalValue(): ExactFraction? = null

    override fun inverse(): Pi = Pi(!isInverted)

    override fun equals(other: Any?): Boolean {
        return other != null &&
            other is Pi &&
            isInverted == other.isInverted
    }

    override fun compareTo(other: Pi): Int {
        return when {
            !isInverted && other.isInverted -> 1
            isInverted && !other.isInverted -> -1
            else -> 0
        }
    }

    operator fun times(other: ExactFraction): Term = Term.fromValues(other, listOf(this))
    operator fun times(other: Log): Term = Term.fromValues(listOf(other), listOf(this))
    operator fun times(other: Pi): Term = Term.fromValues(listOf(this, other))
    operator fun times(other: Sqrt): Term = Term.fromValues(listOf(other), listOf(this))
    operator fun div(other: ExactFraction): Term = Term.fromValues(other.inverse(), listOf(this))
    operator fun div(other: Log): Term = Term.fromValues(listOf(other.inverse()), listOf(this))
    operator fun div(other: Pi): Term = Term.fromValues(listOf(this, other.inverse()))
    operator fun div(other: Sqrt): Term = Term.fromValues(listOf(other.inverse()), listOf(this))

    override fun toString(): String {
        val pi = "π"
        return ternaryIf(isInverted, "[1/$pi]", "[$pi]")
    }

    override fun hashCode(): Int = listOf(TYPE, PI, isInverted).hashCode()

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

            val positive = numbers.count { !it.isInverted }
            val negative = numbers.size - positive
            val diff = abs(positive - negative)

            return when {
                positive == negative -> emptyList()
                positive < negative -> List(diff) { Pi(isInverted = true) }
                else -> List(diff) { Pi(isInverted = false) }
            }
        }
    }
}
