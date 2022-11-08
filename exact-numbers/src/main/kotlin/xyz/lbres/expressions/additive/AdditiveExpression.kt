package xyz.lbres.expressions.additive

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.expressions.term.Term
import xyz.lbres.kotlinutils.bigdecimal.ext.isZero

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
// //        val simplifiedPair = getSimplified()
// //        val sum = simplifiedPair.second.terms.fold(BigDecimal.ZERO) { acc, term -> acc + term.getValue() }
// //        return simplifiedPair.first.getValue() * sum
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
//                val irrationalSort: (Irrational, Irrational) -> Int = { num1, num2 ->
//                    if (num1.type != num2.type) {
//                        num1.type.compareTo(num2.type)
//                    } else {
//                        num1.getValue().compareTo(num2.getValue())
//                    }
//                }

                val addedTerms = simplifiedTerms.groupBy { listOf(it.logs.sorted(), it.squareRoots.sorted(), it.pis.sorted()) }
                    .map {
                        println(it)
                        val numbers = it.key
                        val currentTerms = it.value
                        val newCoeff = currentTerms.fold(ExactFraction.ZERO) { acc, term -> acc + term.coefficient }

                        @Suppress("UNCHECKED_CAST")
                        Term.fromValues(newCoeff, numbers[0] as List<Log>, numbers[1] as List<Sqrt>, numbers[2] as List<Pi>)
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
//        // TODO functionality in Term to get everything in one call
//    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is AdditiveExpression) {
            return false
        }

        val simplifiedTerms = getSimplified().terms.sortedBy { it.getValue() }
        val otherSimplifiedTerms = other.getSimplified().terms.sortedBy { it.getValue() }
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
