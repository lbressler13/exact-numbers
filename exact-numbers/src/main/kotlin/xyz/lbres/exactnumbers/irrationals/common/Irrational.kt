package xyz.lbres.exactnumbers.irrationals.common

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import java.math.BigDecimal

// TODO implementations inherit from Number

/**
 * Values needed for representation of an irrational number
 */
internal interface Irrational {
    val type: String

    fun getValue(): BigDecimal
    fun isZero(): Boolean
    fun isRational(): Boolean
    fun getRationalValue(): ExactFraction?

    val isInverted: Boolean
    fun inverse(): Irrational
}
