package exactnumbers.irrationals.sqrt

import common.divideBigDecimals
import common.getIntFromDecimal
import common.throwDivideByZero
import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.common.Irrational
import exactnumbers.irrationals.log.Log
import exactnumbers.irrationals.pi.Pi
import expressions.term.Term
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode

class Sqrt(val radicand: ExactFraction, override val isDivided: Boolean) : Irrational {
    override val type = TYPE

    constructor(radicand: ExactFraction) : this(radicand, false)

    init {
        when {
            radicand.isNegative() -> throw ArithmeticException("Cannot calculate root of a negative number")
            radicand.isZero() && isDivided -> throwDivideByZero()
        }
    }

    operator fun times(other: Sqrt): Term = times(other)
    operator fun times(other: Log): Term = times(other)
    operator fun times(other: Pi): Term = times(other)
    operator fun div(other: Sqrt): Term = div(other)
    operator fun div(other: Log): Term = div(other)
    operator fun div(other: Pi): Term = div(other)

    override fun isZero(): Boolean = radicand.isZero()
    override fun swapDivided(): Sqrt = Sqrt(radicand, !isDivided)

    override fun isRational(): Boolean {
        val numRoot = getRootOf(radicand.numerator).toPlainString()
        val denomRoot = getRootOf(radicand.denominator).toPlainString()

        return numRoot.indexOf('.') == -1 && denomRoot.indexOf('.') == -1
    }

    override fun getRationalValue(): ExactFraction? {
        if (!isRational()) {
            return null
        }

        val numRoot = getRootOf(radicand.numerator).toBigInteger()
        val denomRoot = getRootOf(radicand.denominator).toBigInteger()
        val result = ExactFraction(numRoot, denomRoot)

        if (isDivided) {
            return result.inverse()
        }

        return result
    }

    override fun getValue(): BigDecimal {
        val numRoot = getRootOf(radicand.numerator)
        val denomRoot = getRootOf(radicand.denominator)
        val result = divideBigDecimals(numRoot, denomRoot)

        if (isDivided) {
            return divideBigDecimals(BigDecimal.ONE, result)
        }

        return result
    }

    override fun equals(other: Any?): Boolean {
        return other != null &&
                other is Sqrt &&
                isDivided == other.isDivided &&
                radicand == other.radicand
    }

    // sqrt(32) returns 4*sqrt(2)
    fun getSimplified(): Pair<ExactFraction, Sqrt> {
        val numWhole = extractWholeOf(radicand.numerator)
        val denomWhole = extractWholeOf(radicand.denominator)
        var whole = ExactFraction(numWhole, denomWhole)
        if (isDivided) {
            whole = whole.inverse()
        }

        val newNum = radicand.numerator / (numWhole * numWhole)
        val newDenom = radicand.denominator / (denomWhole * denomWhole)
        val newRadicand = ExactFraction(newNum, newDenom)

        return Pair(whole, Sqrt(newRadicand, isDivided))
    }

    override fun toString(): String {
        val numString = if (radicand.denominator == BigInteger.ONE) {
            radicand.numerator.toString()
        } else {
            "${radicand.numerator}/${radicand.denominator}"
        }

        if (isDivided) {
            return "[1/√($numString)]"
        }

        return "[√($numString)]"
    }

    companion object {
        const val TYPE = "sqrt"

        val ZERO = Sqrt(ExactFraction.ZERO)
        val ONE = Sqrt(ExactFraction.ONE)

        internal fun simplifyList(numbers: List<Irrational>): Pair<ExactFraction, List<Sqrt>> {
            // TODO
            numbers as List<Sqrt>

            return Pair(ExactFraction.ONE, numbers)
        }
    }
}