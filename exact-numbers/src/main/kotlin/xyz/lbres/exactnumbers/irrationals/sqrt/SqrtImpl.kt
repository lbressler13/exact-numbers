package xyz.lbres.exactnumbers.irrationals.sqrt

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.ext.divideBy
import xyz.lbres.exactnumbers.utils.createHashCode
import xyz.lbres.exactnumbers.utils.divideByZero
import java.math.BigDecimal

// implementation of Sqrt class
internal class SqrtImpl(override val radicand: ExactFraction) : Sqrt() {
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

    override fun isZero(): Boolean = radicand.isZero()
    override fun inverse(): Sqrt {
        if (isZero()) {
            throw divideByZero
        }

        return SqrtImpl(radicand.inverse())
    }

    override fun isRational(): Boolean {
        setRoots()
        return !numRoot!!.toPlainString().contains('.') && !denomRoot!!.toPlainString().contains('.')
    }

    override fun getRationalValue(): ExactFraction? {
        if (!isRational()) {
            return null
        }

        setRoots()
        return ExactFraction(numRoot!!.toBigInteger(), denomRoot!!.toBigInteger())
    }

    // uses the formula sqrt(x/y) = sqrt(x)/sqrt(y) to reduce loss of precision when casting to Double
    override fun getValue(): BigDecimal {
        setRoots()
        return numRoot!!.divideBy(denomRoot!!)
    }

    override fun getSimplified(): Pair<ExactFraction, Sqrt> {
        if (simplified == null) {
            simplified = if (radicand.isZero() || radicand == ExactFraction.ONE) {
                Pair(ExactFraction.ONE, this)
            } else {
                val numWhole = extractWholeOf(radicand.numerator)
                val denomWhole = extractWholeOf(radicand.denominator)
                val whole = ExactFraction(numWhole, denomWhole)

                val newNum = radicand.numerator / (numWhole * numWhole)
                val newDenom = radicand.denominator / (denomWhole * denomWhole)
                val newRadicand = ExactFraction(newNum, newDenom)

                Pair(whole, SqrtImpl(newRadicand))
            }
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
    override fun hashCode(): Int = createHashCode(listOf(radicand, type))

    private fun createString(sqrtStr: String): String = "[$sqrtStr(${radicand.toFractionString()})]"
    override fun toString(): String = createString("√")
    override fun toPlainString(): String = createString("sqrt")
}
