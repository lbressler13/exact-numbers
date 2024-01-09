package xyz.lbres.exactnumbers.irrationals.sqrt

import xyz.lbres.exactnumbers.common.createHashCode
import xyz.lbres.exactnumbers.common.divideByZero
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.ext.divideBy
import java.math.BigDecimal

// implementation of Sqrt class
internal class SqrtImpl private constructor(override val radicand: ExactFraction, private val fullySimplified: Boolean) : Sqrt() {
    override val type = TYPE
    override val isInverted = false

    private var numRoot: BigDecimal? = null
    private var denomRoot: BigDecimal? = null
    private var simplified: Pair<ExactFraction, Sqrt>? = null

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
    override fun isRational(): Boolean {
        setRoots()
        return numRoot!!.toPlainString().indexOf('.') == -1 && denomRoot!!.toPlainString().indexOf('.') == -1
    }

    /**
     * Get the value of the root as a rational value if rational
     *
     * @return [ExactFraction]?: value of the root, or null if the value is irrational
     */
    override fun getRationalValue(): ExactFraction? {
        if (!isRational()) {
            return null
        }

        setRoots()
        return ExactFraction(numRoot!!.toBigInteger(), denomRoot!!.toBigInteger())
    }

    /**
     * Get value of root, using the formula sqrt(x/y) = sqrt(x)/sqrt(y).
     * This reduces loss of precision when casting to Double.
     *
     * @return [BigDecimal]
     */
    override fun getValue(): BigDecimal {
        setRoots()
        return numRoot!!.divideBy(denomRoot!!)
    }

    /**
     * Simplify log into a coefficient and a root.
     * Extracts rational component of root into coefficient, and leaves remaining piece as root.
     * For example, sqrt(50) returns coefficient 5 and sqrt(2)
     *
     * @return [Pair]<ExactFraction, Sqrt>: a pair of coefficient and sqrt such that the product has the same value as the current sqrt
     */
    override fun getSimplified(): Pair<ExactFraction, Sqrt> {
        if (simplified == null) {
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

            simplified = Pair(whole, SqrtImpl(newRadicand, true))
        }

        return simplified!!
    }

    override fun compareTo(other: Sqrt): Int = radicand.compareTo(other.radicand)

    /**
     * Populate numRoot and denomRoot, if not already set
     */
    private fun setRoots() {
        if (numRoot == null || denomRoot == null) {
            numRoot = getRootOf(radicand.numerator)
            denomRoot = getRootOf(radicand.denominator)
        }
    }

    override fun equals(other: Any?): Boolean = other is Sqrt && radicand == other.radicand
    override fun toString(): String = "[√(${radicand.toFractionString()})]"
    override fun hashCode(): Int = createHashCode(listOf(radicand, type))
}
