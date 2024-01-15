package xyz.lbres.exactnumbers.irrationals.pi

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.ext.divideBy
import xyz.lbres.exactnumbers.utils.createHashCode
import xyz.lbres.kotlinutils.general.simpleIf
import java.math.BigDecimal
import kotlin.math.PI

// implementation of Pi class
internal class PiImpl(isInverted: Boolean) : Pi(isInverted) {
    override val type: String = TYPE

    override fun getValue(): BigDecimal {
        val pi = PI.toBigDecimal()
        return simpleIf(isInverted, { BigDecimal.ONE.divideBy(pi) }, { pi })
    }

    override fun isZero(): Boolean = false
    override fun isRational(): Boolean = false
    override fun getRationalValue(): ExactFraction? = null
    override fun inverse(): Pi = PiImpl(!isInverted)

    override fun compareTo(other: Pi): Int {
        return when {
            isInverted && !other.isInverted -> -1
            !isInverted && other.isInverted -> 1
            else -> 0
        }
    }

    private fun createString(base: String): String = simpleIf(isInverted, "[1/$base]", "[$base]")
    override fun toString(): String = createString("π")
    override fun toPlainString(): String = createString("pi")

    override fun equals(other: Any?): Boolean = other is Pi && isInverted == other.isInverted
    override fun hashCode(): Int = createHashCode(listOf(PI, isInverted, type))
}
