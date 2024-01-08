package xyz.lbres.exactnumbers.testutils

import xyz.lbres.exactnumbers.common.createHashCode
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumber
import xyz.lbres.kotlinutils.general.simpleIf

/**
 * Irrational implementation to use in tests
 */
class TestNumber(val value: ExactFraction, override val isInverted: Boolean = false) : IrrationalNumber<TestNumber>() {
    override val type: String = TYPE

    override fun isZero(): Boolean = value.isZero()
    override fun inverse(): TestNumber = TestNumber(value, !isInverted)
    override fun checkIsRational(): Boolean = true
    override fun performGetValue() = simpleIf(isInverted, { value.inverse().toBigDecimal() }, { value.toBigDecimal() })

    override fun performGetRationalValue(): ExactFraction? {
        return when {
            value.isWholeNumber() && !isInverted -> value
            value.inverse().isWholeNumber() && isInverted -> value.inverse()
            else -> null
        }
    }

    override fun equals(other: Any?) = other is TestNumber && value == other.value && isInverted == other.isInverted
    override fun hashCode(): Int = createHashCode(listOf(value, isInverted, type))
    override fun toString(): String = "[${value.toFractionString()}, $isInverted]"

    companion object {
        const val TYPE = "Test"
    }
}
