package xyz.lbres.exactnumbers.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.ext.divideBy
import xyz.lbres.exactnumbers.irrationals.IrrationalNumber
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.exactnumbers.utils.createHashCode
import xyz.lbres.exactnumbers.utils.divideByZero
import xyz.lbres.kotlinutils.collection.ext.toConstMultiSet
import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.emptyConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.mapToSetConsistent
import java.math.BigDecimal

// implementation of Term class
internal class TermImpl(coefficient: ExactFraction, factors: ConstMultiSet<IrrationalNumber<*>>) : Term() {
    override val coefficient: ExactFraction

    // mapping of irrational types to list of values
    private val factorTypeMapping: Map<String, List<IrrationalNumber<*>>>

    private val factorSet: ConstMultiSet<IrrationalNumber<*>>
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
        if (coefficient.isZero() || factors.any { it.isZero() }) {
            this.coefficient = ExactFraction.ZERO
            this.factors = emptyList()
            factorSet = emptyConstMultiSet()
            factorTypeMapping = emptyMap()

            logs = emptyList()
            squareRoots = emptyList()
            pis = emptyList()
            piCount = 0
        } else {
            this.coefficient = coefficient
            this.factors = factors.toList()
            this.factorSet = factors
            this.factorTypeMapping = factorSet.groupBy { it.type }

            @Suppress("UNCHECKED_CAST")
            logs = factorTypeMapping.getOrDefault(Log.TYPE, emptyList()) as List<Log>
            @Suppress("UNCHECKED_CAST")
            squareRoots = factorTypeMapping.getOrDefault(Sqrt.TYPE, emptyList()) as List<Sqrt>
            @Suppress("UNCHECKED_CAST")
            pis = factorTypeMapping.getOrDefault(Pi.TYPE, emptyList()) as List<Pi>
            piCount = factorSet.getCountOf(Pi()) - factorSet.getCountOf(Pi().inverse())
        }
    }

    override fun unaryMinus(): Term = TermImpl(-coefficient, factorSet)
    override fun unaryPlus(): Term = TermImpl(coefficient, factorSet)

    override fun times(other: Term): Term {
        other as TermImpl
        val newIrrationals = factorSet + other.factorSet
        return TermImpl(coefficient * other.coefficient, newIrrationals.toConstMultiSet())
    }

    override fun div(other: Term): Term {
        if (other.isZero()) {
            throw divideByZero
        }

        other as TermImpl
        val newIrrationals = factorSet + other.factorSet.mapToSetConsistent { it.inverse() }
        return TermImpl(coefficient / other.coefficient, newIrrationals.toConstMultiSet())
    }

    override fun isZero(): Boolean = coefficient.isZero()

    /**
     * Simplify coefficient and factors
     *
     * @return [Term] simplified version of this term
     */
    override fun getSimplified(): Term {
        if (simplified == null) {
            simplified = simplifyTerm(coefficient, factorTypeMapping)
        }

        return simplified!!
    }

    /**
     * Get value of term by multiplying coefficient and factors.
     * Term is simplified before value is computed.
     *
     * @return [BigDecimal]
     */
    override fun getValue(): BigDecimal {
        if (value == null) {
            val simplified = getSimplified()

            val factorProduct = simplified.factors.fold(BigDecimal.ONE) { acc, number -> acc * number.getValue() }
            val numeratorProduct = simplified.coefficient.numerator.toBigDecimal() * factorProduct

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
            val numString = factorSet.joinToString("x")
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

        return simplified.coefficient == otherSimplified.coefficient && simplified.factorSet == otherSimplified.factorSet
    }

    override fun hashCode(): Int = createHashCode(listOf(coefficient, factorSet, "Term"))
}
