package xyz.lbres.exactnumbers.irrationals.pi

import xyz.lbres.exactnumbers.common.createHashCode
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.ext.divideBy
import xyz.lbres.kotlinutils.general.simpleIf
import java.math.BigDecimal
import kotlin.math.PI

internal class PiImpl(isInverted: Boolean) : Pi(isInverted) {
    override val type: String = TYPE

    override fun performGetValue(): BigDecimal {
        val base = PI.toBigDecimal()
        return simpleIf(isInverted, { BigDecimal.ONE.divideBy(base) }, { base })
    }

    override fun isZero(): Boolean = false
    override fun checkIsRational(): Boolean = false
    override fun performGetRationalValue(): ExactFraction? = null
    override fun inverse(): Pi = PiImpl(!isInverted)

    override fun compareTo(other: Pi): Int {
        return when {
            isInverted && !other.isInverted -> -1
            !isInverted && other.isInverted -> 1
            else -> 0
        }
    }

    override fun toString(): String = simpleIf(isInverted, "[1/π]", "[π]")

    override fun equals(other: Any?): Boolean = other is Pi && isInverted == other.isInverted
    override fun hashCode(): Int = createHashCode(listOf(PI, isInverted, this::class.toString()))
}
