package xyz.lbres.exactnumbers.irrationals.sqrt

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumber
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumberCompanion
import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.set.multiset.anyConsistent
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.constMultiSetOf
import xyz.lbres.kotlinutils.set.multiset.const.emptyConstMultiSet

/**
 * Representation of a square root with a rational radicand
 */
sealed class Sqrt : IrrationalNumber<Sqrt>() {
    abstract val radicand: ExactFraction

    abstract fun getSimplified(): Pair<ExactFraction, Sqrt>

    companion object : IrrationalNumberCompanion<Sqrt>() {
        override val TYPE = "Sqrt"

        val ZERO = Sqrt(ExactFraction.ZERO)
        val ONE = Sqrt(ExactFraction.ONE)

        /**
         * Extract rational values and simplify remaining set of sqrts
         *
         * @param numbers [ConstMultiSet]<Sqrt>: set to simplify
         * @return [Pair]<ExactFraction, ConstMultiSet<Sqrt>>: product of rational values and a set containing a single, fully simplified irrational root
         */
        override fun simplifySet(numbers: ConstMultiSet<Sqrt>): Pair<ExactFraction, ConstMultiSet<Sqrt>> {
            when {
                numbers.isEmpty() -> return Pair(ExactFraction.ONE, emptyConstMultiSet())
                numbers.anyConsistent(Sqrt::isZero) -> return Pair(ExactFraction.ZERO, emptyConstMultiSet())
            }

            // combine all roots into single root, and return that value
            val totalProduct = numbers.fold(ExactFraction.ONE) { acc, sqrt -> acc * sqrt.radicand }
            val numeratorWhole = extractWholeOf(totalProduct.numerator)
            val denominatorWhole = extractWholeOf(totalProduct.denominator)
            val numeratorRoot = totalProduct.numerator / (numeratorWhole * numeratorWhole)
            val denominatorRoot = totalProduct.denominator / (denominatorWhole * denominatorWhole)

            val root = Sqrt(ExactFraction(numeratorRoot, denominatorRoot))
            val coefficient = ExactFraction(numeratorWhole, denominatorWhole)

            val rootList = simpleIf(root == ONE, emptyConstMultiSet(), constMultiSetOf(root))
            return Pair(coefficient, rootList)
        }
    }
}
