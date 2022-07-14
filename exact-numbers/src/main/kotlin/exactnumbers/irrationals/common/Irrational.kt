package exactnumbers.irrationals.common

import java.math.BigDecimal

/**
 * Values needed for representation of an irrational number
 */
internal interface Irrational {
    val type: String

    fun getValue(): BigDecimal
    fun isZero(): Boolean
    fun isRational(): Boolean

    val isDivided: Boolean
    fun swapDivided(): Irrational
}
