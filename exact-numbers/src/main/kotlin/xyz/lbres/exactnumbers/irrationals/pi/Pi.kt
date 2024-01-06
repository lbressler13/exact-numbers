package xyz.lbres.exactnumbers.irrationals.pi

import xyz.lbres.common.createHashCode
import xyz.lbres.common.divideBigDecimals
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumber
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumberCompanion
import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.emptyConstMultiSet
import java.math.BigDecimal
import kotlin.math.PI
import kotlin.math.abs

/**
 * Representation of pi
 *
 * @param isInverted [Boolean]: if the inverse of the value should be calculated
 */
class Pi(override val isInverted: Boolean) : IrrationalNumber<Pi>() {
    override val type: String = TYPE

    // constructor with reduced params
    constructor() : this(false)

    override fun performGetValue(): BigDecimal {
        val base = PI.toBigDecimal()
        return simpleIf(isInverted, { divideBigDecimals(BigDecimal.ONE, base) }, { base })
    }

    override fun isZero(): Boolean = false
    override fun checkIsRational(): Boolean = false
    override fun performGetRationalValue(): ExactFraction? = null
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

    override fun toString(): String = simpleIf(isInverted, "[1/π]", "[π]")

    override fun hashCode(): Int = createHashCode(listOf(PI, isInverted, this::class.toString()))

    companion object : IrrationalNumberCompanion<Pi>() {
        override val TYPE = "pi"

        /**
         * Simplify set of pis
         *
         * @param numbers [ConstMultiSet]<Pi> : list to simplify
         * @return [Pair]<ExactFraction, ConstMultiSet<Pi>>: pair where first value is one, and second value is the simplified set
         */
        override fun simplifySet(numbers: ConstMultiSet<Pi>): Pair<ExactFraction, ConstMultiSet<Pi>> {
            if (numbers.isEmpty()) {
                return Pair(ExactFraction.ONE, emptyConstMultiSet())
            }

            val positive = numbers.getCountOf(Pi())
            val negative = numbers.getCountOf(Pi(isInverted = true))
            val diff = abs(positive - negative)

            val pis = ConstMultiSet(diff) { Pi(isInverted = positive < negative) }
            return Pair(ExactFraction.ONE, pis)
        }
    }
}
