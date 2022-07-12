package shared

import java.math.BigDecimal

internal interface NumType {
    val type: String

    fun getValue(): BigDecimal
    fun isZero(): Boolean

    val isDivided: Boolean
    fun swapDivided(): NumType

    fun getBaseString(): String
}
