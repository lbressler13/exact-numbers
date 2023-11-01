package xyz.lbres.exactnumbers.irrationals.common

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import java.math.BigDecimal

private const val replacementClass = "xyz.lbres.exactnumbers.irrational.common.IrrationalNumber"

/**
 * Values needed for representation of an irrational number
 */
@Deprecated("Interface deprecated in v1.0", ReplaceWith(replacementClass, replacementClass), DeprecationLevel.WARNING)
@Suppress("UNUSED", "DEPRECATION")
internal interface Irrational {
    val type: String

    fun getValue(): BigDecimal
    fun isZero(): Boolean
    fun isRational(): Boolean
    fun getRationalValue(): ExactFraction?

    val isDivided: Boolean
    fun swapDivided(): Irrational
}
