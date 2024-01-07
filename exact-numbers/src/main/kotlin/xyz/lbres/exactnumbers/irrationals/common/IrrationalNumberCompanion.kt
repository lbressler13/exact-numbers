package xyz.lbres.exactnumbers.irrationals.common

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet

/**
 * Companion object for an irrational number class
 */
abstract class IrrationalNumberCompanion<T : IrrationalNumber<T>> {
    abstract val TYPE: String

    /**
     * Simplify a list of irrational values
     *
     * @param numbers [ConstMultiSet]<T> : values to simplify
     * @return [Pair]<ExactFraction, ConstMultiSet<T>>: pair where first value is a coefficient and the second value is the simplified set,
     * and the product of all values in the set is equal to the product of [numbers]
     */
    internal abstract fun simplifySet(numbers: ConstMultiSet<T>): Pair<ExactFraction, ConstMultiSet<T>>
}
