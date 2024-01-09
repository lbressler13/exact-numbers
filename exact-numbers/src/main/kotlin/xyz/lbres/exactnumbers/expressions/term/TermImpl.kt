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
import xyz.lbres.kotlinutils.set.multiset.mapToSetConsistent
import java.math.BigDecimal

internal class TermImpl(coefficient: ExactFraction, irrationals: ConstMultiSet<IrrationalNumber<*>>) : Term() {
    override val coefficient: ExactFraction

    // mapping of irrational types to list of values
    private val factorTypeMapping: Map<String, List<IrrationalNumber<*>>>

    private val _factors: ConstMultiSet<IrrationalNumber<*>>
    override val factors: List<IrrationalNumber<*>>

    override val logs: List<Log>
    override val squareRoots: List<Sqrt>
    override val pis: List<Pi>
    override val piCount: Int

    // previously computed values for method returns
    private var simplified: Term? = null
    private var value: BigDecimal? = null
    private var string: String? = null

    init {
        if (coefficient.isZero() || irrationals.any { it.isZero() }) {
            this.coefficient = ExactFraction.ZERO
            this.factors = emptyList()
            _factors = emptyConstMultiSet()
            factorTypeMapping = emptyMap()

            logs = emptyList()
            squareRoots = emptyList()
            pis = emptyList()
            piCount = 0
        } else {
            this.coefficient = coefficient
            this.factors = irrationals.toList()
            this._factors = irrationals
            this.factorTypeMapping = _factors.groupBy { it.type }

            @Suppress("UNCHECKED_CAST")
            logs = factorTypeMapping.getOrDefault(Log.TYPE, emptyList()) as List<Log>
            @Suppress("UNCHECKED_CAST")
            squareRoots = factorTypeMapping.getOrDefault(Sqrt.TYPE, emptyList()) as List<Sqrt>
            @Suppress("UNCHECKED_CAST")
            pis = factorTypeMapping.getOrDefault(Pi.TYPE, emptyList()) as List<Pi>
            piCount = _factors.getCountOf(Pi()) - _factors.getCountOf(Pi().inverse())
        }
    }

    override fun unaryMinus(): Term = TermImpl(-coefficient, _factors)
    override fun unaryPlus(): Term = TermImpl(coefficient, _factors)

    override fun times(other: Term): Term {
        other as TermImpl
        val newIrrationals = _factors + other._factors
        return TermImpl(coefficient * other.coefficient, newIrrationals.toConstMultiSet())
    }

    override fun div(other: Term): Term {
        if (other.isZero()) {
            throw divideByZero
        }

        other as TermImpl
        val newIrrationals = _factors + other._factors.mapToSetConsistent { it.inverse() }
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

            val irrationalProduct = simplified.factors.fold(BigDecimal.ONE) { acc, number -> acc * number.getValue() }
            val numeratorProduct = simplified.coefficient.numerator.toBigDecimal() * irrationalProduct

            val result = numeratorProduct.divideBy(simplified.coefficient.denominator.toBigDecimal())
            value = result
        }

        return value!!
    }

    /**
     * Get list of irrational numbers with a given type
     *
     * @param irrationalType [String]: type to retrieve numbers for
     * @return [List]<IrrationalNumber<*>>: list of irrational numbers, which all have the provided type
     */
    override fun getFactorsByType(irrationalType: String): List<IrrationalNumber<*>> {
        return factorTypeMapping.getOrDefault(irrationalType, emptyList())
    }

    override fun toString(): String {
        if (string == null) {
            val fractionString = coefficient.toFractionString()
            val coeffString = simpleIf(fractionString.contains("/"), "[$fractionString]", fractionString)
            val numString = _factors.joinToString("x")
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

        return simplified.coefficient == otherSimplified.coefficient && simplified._factors == otherSimplified._factors
    }

    override fun hashCode(): Int = createHashCode(listOf(coefficient, _factors, "Term"))
}
