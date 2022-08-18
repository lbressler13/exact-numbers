package expressions.additive

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.common.Irrational
import expressions.term.Term
import kotlinutils.bigdecimal.ext.isZero
import java.math.BigDecimal

class AdditiveExpression private constructor(val terms: List<Term>, private val isSimplified: Boolean) {
    init {
        if (terms.isEmpty()) {
            throw Exception("Expression must contain at least one value")
        }
    }

    constructor(terms: List<Term>) : this(terms, false)
    constructor(term: Term) : this(listOf(term))

    operator fun unaryPlus(): AdditiveExpression = this
    operator fun unaryMinus(): AdditiveExpression = AdditiveExpression(terms.map(Term::unaryMinus), isSimplified)

    operator fun plus(other: AdditiveExpression): AdditiveExpression = AdditiveExpression(terms + other.terms)
    operator fun minus(other: AdditiveExpression): AdditiveExpression = plus(-other)

    // fun isZero(): Boolean = getValue().isZero()

    // TODO
//    fun getValue(): BigDecimal {
////        val simplifiedPair = getSimplified()
////        val sum = simplifiedPair.second.terms.fold(BigDecimal.ZERO) { acc, term -> acc + term.getValue() }
////        return simplifiedPair.first.getValue() * sum
//        return BigDecimal.ONE
//    }

    // TODO
    fun getSimplified(): AdditiveExpression {
        if (isSimplified) {
            return this
        }

        val simplifiedTerms = terms.map(Term::getSimplified).filterNot { it.isZero() }

        return when (simplifiedTerms.size) {
            0 -> ZERO
            1 -> AdditiveExpression(simplifiedTerms, true)
            else -> {
                val irrationalSort: (Irrational, Irrational) -> Int = { num1, num2 ->
                    if (num1.type != num2.type) {
                        num1.type.compareTo(num2.type)
                    } else {
                        num1.getValue().compareTo(num2.getValue())
                    }
                }

                val addedTerms = simplifiedTerms.groupBy { it.numbers.sortedWith(irrationalSort) }
                    .map {
                        println(it)
                        val numbers = it.key
                        val currentTerms = it.value
                        val newCoeff = currentTerms.fold(ExactFraction.ZERO) { acc, term -> acc + term.coefficient }
                        Term(newCoeff, numbers)
                    }
                    .filterNot { it.isZero() }

                if (addedTerms.isEmpty()) {
                    ZERO
                } else {
                    AdditiveExpression(addedTerms)
                }
            }
        }
    }

    // TODO
//    fun extractCommon(): Pair<Term, AdditiveExpression> {
//
//    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is AdditiveExpression) {
            return false
        }

        // TODO improve simplified
        val simplifiedTerms = terms.filterNot { it.isZero() }.sortedBy { it.getValue() }
        val otherSimplifiedTerms = other.terms.filterNot { it.isZero() }.sortedBy { it.getValue() }
        return simplifiedTerms == otherSimplifiedTerms
    }

    override fun hashCode(): Int = Pair("AdditiveExpression", terms).hashCode()

    override fun toString(): String {
        val termsString = terms.joinToString("+")
        return "AE<$termsString>"
    }

    companion object {
        val ZERO: AdditiveExpression = AdditiveExpression(Term.ZERO)
        val ONE: AdditiveExpression = AdditiveExpression(Term.ONE)
    }
}