package xyz.lbres.exactnumbers.irrationals.common

import xyz.lbres.exactnumbers.common.deprecatedV1
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import java.math.BigDecimal

private const val replacementClass = "xyz.lbres.exactnumbers.irrational.common.IrrationalNumber"

/**
 * Values needed for representation of an irrational number
 */
@Deprecated("Interface $deprecatedV1", ReplaceWith("IrrationalNumber", replacementClass), DeprecationLevel.WARNING)
@Suppress("Unused")
internal interface Irrational {
    val type: String

    fun getValue(): BigDecimal
    fun isZero(): Boolean
    fun isRational(): Boolean
    fun getRationalValue(): ExactFraction?

    val isDivided: Boolean
    @Suppress("Deprecation")
    fun swapDivided(): Irrational
}
