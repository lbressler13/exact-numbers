package xyz.lbres.exactnumbers.irrationals.pi

import xyz.lbres.common.divideBigDecimals
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumber
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumberCompanion
import xyz.lbres.kotlinutils.general.simpleIf
import java.math.BigDecimal
import kotlin.math.PI
import kotlin.math.abs

/**
 * Representation of pi
 *
 * @param isDivided [Boolean]: if the inverse of the value should be calculated
 */
class Pi(override val isDivided: Boolean) : IrrationalNumber<Pi>() {
    override val type: String = TYPE

    // constructor with reduced params
    constructor() : this(false)

    override fun protectedGetValue(): BigDecimal {
        val base = PI.toBigDecimal()
        return simpleIf(isDivided, { divideBigDecimals(BigDecimal.ONE, base) }, { base })
    }

    override fun isZero(): Boolean = false
    override fun protectedIsRational(): Boolean = false
    override fun protectedGetRationalValue(): ExactFraction? = null
    override fun swapDivided(): Pi = Pi(!isDivided)

    override fun compareTo(other: Pi): Int {
        return when {
            isDivided && !other.isDivided -> -1
            !isDivided && other.isDivided -> 1
            else -> 0
        }
    }

    override fun equals(other: Any?): Boolean {
        return other != null &&
            other is Pi &&
            isDivided == other.isDivided
    }

    override fun toString(): String = simpleIf(isDivided, "[1/π]", "[π]")

    override fun hashCode(): Int = listOf(TYPE, PI, isDivided).hashCode()

    companion object : IrrationalNumberCompanion<Pi>() {
        override val TYPE = "pi"

        /**
         * Simplify list of pis
         *
         * @param numbers [List]<Irrational> : list to simplify, expected to consist of only Pis
         * @return [List]<Pi>: simplified list
         * @return [Pair]<ExactFraction, List<Pi>>: pair where first value is one, and the second value is the simplified list
         */
        override fun simplifyList(numbers: List<IrrationalNumber<*>>?): Pair<ExactFraction, List<Pi>> {
            if (numbers.isNullOrEmpty()) {
                return Pair(ExactFraction.ONE, emptyList())
            }

            @Suppress("UNCHECKED_CAST")
            numbers as List<Pi>

            val positive = numbers.count { !it.isDivided }
            val negative = numbers.size - positive
            val diff = abs(positive - negative)

            val pis = List(diff) { Pi(isDivided = positive < negative) }
            return Pair(ExactFraction.ONE, pis)
        }
    }
}
