package exactnumbers.irrationals.pi

import java.math.BigDecimal
import kotlin.math.PI

/**
 * Representation of pi, with a rational coefficient
 */
class PiNum() {
    override fun equals(other: Any?): Boolean = other != null && other is PiNum

    fun getValue(): BigDecimal = PI.toBigDecimal()

    fun isZero(): Boolean = false

    override fun toString(): String = "[π]"

    override fun hashCode(): Int = PI.hashCode()
}
