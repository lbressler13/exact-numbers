package xyz.lbres.exactnumbers.irrationals.common

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.kotlinutils.set.multiset.MultiSet

/**
 * Companion object for an irrational number class
 */
abstract class IrrationalNumberCompanion<T : IrrationalNumber<T>> {
    abstract val TYPE: String

    /**
     * Simplify a list of irrational values
     *
     * @param numbers [MultiSet]<T> : values to simplify
     * @return [Pair]<ExactFraction, MultiSet<T>>: pair where first value is a coefficient and the second value is the simplified set,
     * and the product of all values is equal to the product of the initial list of [numbers]
     */
    internal abstract fun simplifySet(numbers: MultiSet<T>): Pair<ExactFraction, MultiSet<T>>
}
