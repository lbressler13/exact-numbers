package xyz.lbres.exactnumbers.irrationals.common

import xyz.lbres.exactnumbers.exactfraction.ExactFraction

/**
 * Generic class for companion object of an irrational number class
 */
abstract class IrrationalNumberCompanion<T : IrrationalNumber<T>> {
    abstract val TYPE: String

    /**
     * Simplify a list of irrational values
     *
     * @param numbers [List]<IrrationalNumber> : list to simplify, expected to consist of only the current number type
     * @return [Pair]<ExactFraction, List<T>>: pair where first value is a coefficient and the second value is the simplified list,
     * and the product of all values is equal to the product of the initial list of [numbers]
     */
    internal abstract fun simplifyList(numbers: List<IrrationalNumber<*>>?): Pair<ExactFraction, List<T>>
}
