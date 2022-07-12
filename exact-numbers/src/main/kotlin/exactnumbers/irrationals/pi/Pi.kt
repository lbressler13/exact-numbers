package exactnumbers.irrationals.pi

import shared.NumType
import java.math.BigDecimal
import kotlin.math.PI

/**
 * Representation of pi, with a rational coefficient
 */
class Pi(override val isDivided: Boolean) : NumType {
    override val type: String = TYPE

    constructor() : this(false)

    override fun getValue(): BigDecimal = PI.toBigDecimal()
    override fun isZero(): Boolean = false

    override fun swapDivided(): Pi = Pi(!isDivided)

    override fun equals(other: Any?): Boolean = other != null && other is Pi

    override fun getBaseString(): String = "π"
    override fun toString(): String = "[${getBaseString()}]"

    override fun hashCode(): Int = PI.hashCode()

    companion object {
        const val TYPE = "pi"
    }
}
