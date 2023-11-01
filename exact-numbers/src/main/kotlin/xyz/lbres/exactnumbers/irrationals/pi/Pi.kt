package xyz.lbres.exactnumbers.irrationals.pi

import xyz.lbres.common.divideBigDecimals
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.Irrational
import xyz.lbres.kotlinutils.general.simpleIf
import java.math.BigDecimal
import kotlin.math.PI
import kotlin.math.abs

/**
 * Representation of pi
 *
 * @param isDivided [Boolean]: if the inverse of the value should be calculated
 */
class Pi(override val isDivided: Boolean) : Comparable<Pi>, Irrational, Number() {
    override val type: String = TYPE

    // constructor with reduced params
    constructor() : this(false)

    override fun getValue(): BigDecimal {
        val base = PI.toBigDecimal()

        if (isDivided) {
            return divideBigDecimals(BigDecimal.ONE, base)
        }

        return base
    }

    override fun isZero(): Boolean = false
    override fun isRational(): Boolean = false
    override fun getRationalValue(): ExactFraction? = null
    override fun swapDivided(): Pi = Pi(!isDivided)

    override fun compareTo(other: Pi): Int {
        return when {
            isDivided && !other.isDivided -> -1
            !isDivided && other.isDivided -> 1
            else -> 0
        }
    }

    override fun equals(other: Any?): Boolean {
        return other != null &&
            other is Pi &&
            isDivided == other.isDivided
    }

    override fun toByte(): Byte = getValue().toByte()
    override fun toChar(): Char = getValue().toInt().toChar()
    override fun toShort(): Short = getValue().toShort()
    override fun toInt(): Int = getValue().toInt()
    override fun toLong(): Long = getValue().toLong()
    override fun toDouble(): Double = getValue().toDouble()
    override fun toFloat(): Float = getValue().toFloat()

    override fun toString(): String = simpleIf(isDivided, "[1/π]", "[π]")

    override fun hashCode(): Int = listOf(TYPE, PI, isDivided).hashCode()

    companion object {
        const val TYPE = "pi"

        /**
         * Simplify list of pis
         *
         * @param numbers [List]<Irrational> : list to simplify, expected to consist of only Pis
         * @return [List]<Pi>: simplified list
         */
        internal fun simplifyList(numbers: List<Irrational>?): List<Pi> {
            if (numbers.isNullOrEmpty()) {
                return emptyList()
            }

            @Suppress("UNCHECKED_CAST")
            numbers as List<Pi>

            val positive = numbers.count { !it.isDivided }
            val negative = numbers.size - positive
            val diff = abs(positive - negative)

            return List(diff) { Pi(isDivided = positive < negative) }
        }
    }
}
