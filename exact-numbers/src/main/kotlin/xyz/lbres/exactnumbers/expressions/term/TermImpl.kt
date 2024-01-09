package xyz.lbres.exactnumbers.expressions.term

import xyz.lbres.exactnumbers.common.createHashCode
import xyz.lbres.exactnumbers.common.divideByZero
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.ext.divideBy
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumber
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.kotlinutils.collection.ext.toConstMultiSet
import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.emptyConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.filterConsistent
import xyz.lbres.kotlinutils.set.multiset.mapToSetConsistent
import java.math.BigDecimal

internal class TermImpl(coefficient: ExactFraction, irrationals: ConstMultiSet<IrrationalNumber<*>>) : Term() {
    override val coefficient: ExactFraction

    private val irrationalTypes: MutableMap<String, List<IrrationalNumber<*>>> = mutableMapOf()

    // TODO finalize property names
    private val _irrationals: ConstMultiSet<IrrationalNumber<*>>
    override val irrationals: List<IrrationalNumber<*>>

    override val logs: List<Log> get() = getIrrationalsByType(Log.TYPE) as List<Log>
    override val squareRoots: List<Sqrt> get() = getIrrationalsByType(Sqrt.TYPE) as List<Sqrt>
    override val pis: List<Pi> get() = getIrrationalsByType(Pi.TYPE) as List<Pi>
    override val piCount: Int get() = calculatePiCount()

    // previously computed values for method returns
    private var simplified: Term? = null
    private var value: BigDecimal? = null
    private var string: String? = null

    init {
        if (coefficient.isZero() || irrationals.any { it.isZero() }) {
            this.coefficient = ExactFraction.ZERO
            this.irrationals = emptyList()
            this._irrationals = emptyConstMultiSet()
        } else {
            this.coefficient = coefficient
            this.irrationals = irrationals.toList()
            this._irrationals = irrationals
        }
    }

    override fun unaryMinus(): Term = TermImpl(-coefficient, _irrationals)
    override fun unaryPlus(): Term = TermImpl(coefficient, _irrationals)

    override fun times(other: Term): Term {
        other as TermImpl
        val newIrrationals = _irrationals + other._irrationals
        return TermImpl(coefficient * other.coefficient, newIrrationals.toConstMultiSet())
    }

    override fun div(other: Term): Term {
        if (other.isZero()) {
            throw divideByZero
        }

        other as TermImpl
        val newIrrationals = _irrationals + other._irrationals.mapToSetConsistent { it.inverse() }
        return TermImpl(coefficient / other.coefficient, newIrrationals.toConstMultiSet())
    }

    override fun isZero(): Boolean = coefficient.isZero()

    /**
     * Simplify all numbers, based on the simplify function for their type
     *
     * @return [Term] simplified version of this term
     */
    override fun getSimplified(): Term {
        if (simplified == null) {
            simplified = simplifyTerm(this)
        }

        return simplified!!
    }

    /**
     * Get value of term by multiplying numbers.
     * Term is simplified before any computation is run
     *
     * @return [BigDecimal]
     */
    override fun getValue(): BigDecimal {
        if (value == null) {
            val simplified = getSimplified()

            val irrationalProduct = simplified.irrationals.fold(BigDecimal.ONE) { acc, number -> acc * number.getValue() }
            val numeratorProduct = simplified.coefficient.numerator.toBigDecimal() * irrationalProduct

            val result = numeratorProduct.divideBy(simplified.coefficient.denominator.toBigDecimal())
            value = result
        }

        return value!!
    }

    /**
     * Get list of irrational numbers with a given type
     *
     * @param type [String]: type to retrieve numbers for
     * @return [List]<IrrationalNumber<*>>: list of irrational numbers, which all have type [type]
     */
    override fun getIrrationalsByType(type: String): List<IrrationalNumber<*>> {
        if (type !in irrationalTypes) {
            val result = _irrationals.filterConsistent { it.type == type }
            irrationalTypes[type] = result
        }

        return irrationalTypes[type]!!
    }

    /**
     * Calculate number of pis based on list of irrationals
     */
    private fun calculatePiCount(): Int = _irrationals.getCountOf(Pi()) - _irrationals.getCountOf(Pi().inverse())

    override fun toString(): String {
        if (string == null) {
            val fractionString = coefficient.toFractionString()
            val coeffString = simpleIf(fractionString.contains("/"), "[$fractionString]", fractionString)
            val numString = _irrationals.joinToString("x")
            val result = simpleIf(numString.isEmpty(), "<$coeffString>", "<${coeffString}x$numString>")

            string = result
        }

        return string!!
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Term) {
            return false
        }

        val simplified = getSimplified() as TermImpl
        val otherSimplified = other.getSimplified() as TermImpl

        return simplified.coefficient == otherSimplified.coefficient && simplified._irrationals == otherSimplified._irrationals
    }

    override fun hashCode(): Int = createHashCode(listOf(coefficient, _irrationals, "Term"))
}
