package xyz.lbres.exactnumbers.irrationals.pi

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumber
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumberCompanion
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.emptyConstMultiSet
import kotlin.math.abs

/**
 * Representation of pi
 */
// TODO remove param
sealed class Pi(removeThis: String?) : IrrationalNumber<Pi>() {
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

            val diff = numbers.getCountOf(PiImpl()) - numbers.getCountOf(PiImpl(isInverted = true))
            val pis: ConstMultiSet<Pi> = ConstMultiSet(abs(diff)) { PiImpl(isInverted = diff < 0) }
            return Pair(ExactFraction.ONE, pis)
        }
    }
}
