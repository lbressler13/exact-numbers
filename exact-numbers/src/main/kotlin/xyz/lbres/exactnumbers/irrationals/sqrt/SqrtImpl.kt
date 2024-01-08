package xyz.lbres.exactnumbers.irrationals.sqrt

import xyz.lbres.exactnumbers.common.createHashCode
import xyz.lbres.exactnumbers.common.divideByZero
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.ext.divideBy
import java.math.BigDecimal
import java.math.BigInteger

internal class SqrtImpl private constructor(override val radicand: ExactFraction, private val fullySimplified: Boolean) : Sqrt() {
    override val type = TYPE
    override val isInverted = false

    init {
        if (radicand.isNegative()) {
            throw ArithmeticException("Cannot calculate root of a negative number")
        }
    }

    constructor(radicand: ExactFraction) : this(radicand, false)

    override fun isZero(): Boolean = radicand.isZero()
    override fun inverse(): Sqrt {
        if (isZero()) {
            throw divideByZero
        }

        return SqrtImpl(radicand.inverse(), true)
    }

    /**
     * Determine if the value of the root is a rational number.
     *
     * @return [Boolean]: true if the value is rational, false otherwise
     */
    override fun checkIsRational(): Boolean {
        val numRoot = getRootOf(radicand.numerator).toPlainString()
        val denomRoot = getRootOf(radicand.denominator).toPlainString()

        return numRoot.indexOf('.') == -1 && denomRoot.indexOf('.') == -1
    }

    /**
     * Get the value of the root as a rational value if rational
     *
     * @return [ExactFraction]?: value of the root, or null if the value is irrational
     */
    override fun performGetRationalValue(): ExactFraction? {
        if (!isRational()) {
            return null
        }

        val numRoot = getRootOf(radicand.numerator).toBigInteger()
        val denomRoot = getRootOf(radicand.denominator).toBigInteger()
        return ExactFraction(numRoot, denomRoot)
    }

    /**
     * Get value of root, using the formula sqrt(x/y) = sqrt(x)/sqrt(y).
     * This reduces loss of precision when casting to Double.
     *
     * @return [BigDecimal]
     */
    override fun performGetValue(): BigDecimal {
        val numRoot = getRootOf(radicand.numerator)
        val denomRoot = getRootOf(radicand.denominator)
        return numRoot.divideBy(denomRoot)
    }

    override fun equals(other: Any?): Boolean = other is Sqrt && radicand == other.radicand

    /**
     * Simplify log into a coefficient and a root.
     * Extracts rational component of root into coefficient, and leaves remaining piece as root.
     * For example, sqrt(50) returns coefficient 5 and sqrt(2)
     *
     * @return [Pair]<ExactFraction, Sqrt>: a pair of coefficient and sqrt such that the product has the same value as the current sqrt
     */
    override fun getSimplified(): Pair<ExactFraction, Sqrt> {
        when {
            fullySimplified -> return Pair(ExactFraction.ONE, this)
            radicand.isZero() -> return Pair(ExactFraction.ONE, ZERO)
            radicand == ExactFraction.ONE -> return Pair(ExactFraction.ONE, ONE)
        }

        val numWhole = extractWholeOf(radicand.numerator)
        val denomWhole = extractWholeOf(radicand.denominator)
        val whole = ExactFraction(numWhole, denomWhole)

        val newNum = radicand.numerator / (numWhole * numWhole)
        val newDenom = radicand.denominator / (denomWhole * denomWhole)
        val newRadicand = ExactFraction(newNum, newDenom)

        return Pair(whole, SqrtImpl(newRadicand, true))
    }

    override fun compareTo(other: Sqrt): Int = radicand.compareTo(other.radicand)

    override fun toString(): String {
        val radicandString = if (radicand.denominator == BigInteger.ONE) {
            radicand.numerator.toString()
        } else {
            "${radicand.numerator}/${radicand.denominator}"
        }

        return "[√($radicandString)]"
    }

    override fun hashCode(): Int = createHashCode(listOf(radicand, this::class.toString()))
}