package xyz.lbres.exactnumbers.irrationals.common

import xyz.lbres.exactnumbers.exactfraction.ExactFraction

abstract class IrrationalNumberCompanion<T : IrrationalNumber<T>> {
    protected abstract val TYPE: String

    internal abstract fun simplifyList(numbers: List<IrrationalNumber<*>>?): Pair<ExactFraction, List<T>>
}
