package xyz.lbres.testutils

import xyz.lbres.common.createHashCode
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumber
import java.math.BigDecimal

/**
 * Irrational implementation to use in tests
 */
class TestNumber(val value: ExactFraction, override val isInverted: Boolean) : IrrationalNumber<TestNumber>() {
    override val type: String = TYPE

    constructor(value: ExactFraction) : this(value, false)

    override fun isZero(): Boolean = value.isZero()

    override fun checkIsRational(): Boolean = value.isWholeNumber() && !isInverted

    override fun performGetValue(): BigDecimal {
        return if (isInverted) {
            value.inverse().toBigDecimal()
        } else {
            value.toBigDecimal()
        }
    }

    override fun performGetRationalValue(): ExactFraction? {
        return when {
            value.isWholeNumber() && !isInverted -> value
            value.inverse().isWholeNumber() && isInverted -> value.inverse()
            else -> null
        }
    }

    override fun inverse(): TestNumber = TestNumber(value, !isInverted)

    override fun equals(other: Any?): Boolean {
        return other is TestNumber && value == other.value && isInverted == other.isInverted
    }

    override fun hashCode(): Int = createHashCode(listOf(value, isInverted, type))

    override fun toString(): String = "[${value.toFractionString()}, $isInverted]"

    companion object {
        const val TYPE = "Test"
    }
}
