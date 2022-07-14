package exactnumbers.irrationals.common

import java.math.BigDecimal

internal interface Irrational {
    val type: String

    fun getValue(): BigDecimal
    fun isZero(): Boolean

    val isDivided: Boolean
    fun swapDivided(): Irrational
}
