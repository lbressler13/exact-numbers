package xyz.lbres.exactnumbers.irrationals.sqrt

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.ext.divideBy
import xyz.lbres.exactnumbers.ext.isWholeNumber
import xyz.lbres.exactnumbers.utils.createHashCode
import xyz.lbres.exactnumbers.utils.divideByZero
import xyz.lbres.exactnumbers.utils.getOrSet
import java.math.BigDecimal

// implementation of Sqrt class
internal class SqrtImpl(override val radicand: ExactFraction) : Sqrt() {
    override val type = TYPE
    override val isInverted = false

    private var numeratorRoot: BigDecimal? = null
    private var denominatorRoot: BigDecimal? = null
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
        return numeratorRoot!!.isWholeNumber() && denominatorRoot!!.isWholeNumber()
    }

    override fun getRationalValue(): ExactFraction? {
        if (!isRational()) {
            return null
        }

        setRoots()
        return ExactFraction(numeratorRoot!!.toBigInteger(), denominatorRoot!!.toBigInteger())
    }

    // uses the formula sqrt(x/y) = sqrt(x)/sqrt(y) to increase precision
    override fun getValue(): BigDecimal {
        setRoots()
        return numeratorRoot!!.divideBy(denominatorRoot!!)
    }

    override fun getSimplified(): Pair<ExactFraction, Sqrt> {
        if (radicand.isZero() || radicand == ExactFraction.ONE) {
            return Pair(ExactFraction.ONE, this)
        }

        return getOrSet({ simplified }, { simplified = it }) {
            val numeratorWhole = extractWholeOf(radicand.numerator)
            val denominatorWhole = extractWholeOf(radicand.denominator)
            val whole = ExactFraction(numeratorWhole, denominatorWhole)

            val newNumerator = radicand.numerator / (numeratorWhole * numeratorWhole)
            val newDenominator = radicand.denominator / (denominatorWhole * denominatorWhole)
            val newRadicand = ExactFraction(newNumerator, newDenominator)

            Pair(whole, SqrtImpl(newRadicand))
        }
    }

    override fun compareTo(other: Sqrt): Int = radicand.compareTo(other.radicand)

    /**
     * Populate numRoot and denomRoot, if not already set
     */
    private fun setRoots() {
        if (numeratorRoot == null || denominatorRoot == null) {
            numeratorRoot = getRootOf(radicand.numerator)
            denominatorRoot = getRootOf(radicand.denominator)
        }
    }

    override fun equals(other: Any?): Boolean = other is Sqrt && radicand == other.radicand
    override fun hashCode(): Int = createHashCode(listOf(radicand, type))

    private fun createString(sqrtStr: String): String = "[$sqrtStr(${radicand.toFractionString()})]"
    override fun toString(): String = createString("√")
    override fun toPlainString(): String = createString("sqrt")
}
