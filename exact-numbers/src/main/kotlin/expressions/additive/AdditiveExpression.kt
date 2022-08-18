package expressions.additive

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

//    operator fun plus(other: AdditiveExpression): AdditiveExpression {
//        // TODO
//    }
//
//    operator fun minus(other: AdditiveExpression): AdditiveExpression {
//        // TODO
//    }

    // fun isZero(): Boolean = getValue().isZero()

    // TODO
//    fun getValue(): BigDecimal {
////        val simplifiedPair = getSimplified()
////        val sum = simplifiedPair.second.terms.fold(BigDecimal.ZERO) { acc, term -> acc + term.getValue() }
////        return simplifiedPair.first.getValue() * sum
//        return BigDecimal.ONE
//    }

    // TODO
//    fun getSimplified(): AdditiveExpression {
//        return this
//        if (isSimplified) {
//            return Pair(Term.ONE, this)
//        }
//
//        val simplifiedTerms = terms.map(Term::getSimplified).filterNot { it.isZero() }
//
//        return when (simplifiedTerms.size) {
//            0 -> Pair(Term.ZERO, ZERO)
//            1 -> Pair(simplifiedTerms.first(), ONE)
//            else -> {
//                Pair(Term.ONE, this)
//            }
//        }
//    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is AdditiveExpression) {
            return false
        }

        // TODO improve simplified
        val simplifiedTerms = terms.filterNot { it.isZero() }
        val otherSimplifiedTerms = other.terms.filterNot { it.isZero() }
        return simplifiedTerms == otherSimplifiedTerms
    }

    override fun hashCode(): Int = Pair("AdditiveExpression", terms).hashCode()

//    override fun toString(): String {
//        return super.toString()
//    }

    companion object {
        val ZERO: AdditiveExpression = AdditiveExpression(Term.ZERO)
        val ONE: AdditiveExpression = AdditiveExpression(Term.ONE)
    }
}