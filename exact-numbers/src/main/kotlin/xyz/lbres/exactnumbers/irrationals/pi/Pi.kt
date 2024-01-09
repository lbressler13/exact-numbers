package xyz.lbres.exactnumbers.irrationals.pi

import xyz.lbres.exactnumbers.common.deprecatedV1
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumber
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumberCompanion
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.emptyConstMultiSet
import kotlin.math.abs

/**
 * Representation of pi
 */
// parameter in constructor avoids conflicts with the Pi() function
sealed class Pi(override val isInverted: Boolean) : IrrationalNumber<Pi>() {
    companion object : IrrationalNumberCompanion<Pi>() {
        override val TYPE = "Pi"

        /**
         * Simplify set of pis
         *
         * @param numbers [ConstMultiSet]<Pi>: set to simplify
         * @return [Pair]<ExactFraction, ConstMultiSet<Pi>>: pair where first value is one, and second value is the simplified set
         */
        override fun simplifySet(numbers: ConstMultiSet<Pi>): Pair<ExactFraction, ConstMultiSet<Pi>> {
            if (numbers.isEmpty()) {
                return Pair(ExactFraction.ONE, emptyConstMultiSet())
            }

            val diff = numbers.getCountOf(Pi()) * 2 - numbers.size // positive - (numbers.size - positive)
            val pis: ConstMultiSet<Pi> = ConstMultiSet(abs(diff)) { PiImpl(diff < 0) }
            return Pair(ExactFraction.ONE, pis)
        }
    }
}

fun Pi(): Pi = PiImpl(false)
