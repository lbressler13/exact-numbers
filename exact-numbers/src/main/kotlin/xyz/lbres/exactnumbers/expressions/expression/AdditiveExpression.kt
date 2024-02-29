package xyz.lbres.exactnumbers.expressions.expression

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.Expression
import xyz.lbres.exactnumbers.expressions.ExpressionImpl
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.utils.createHashCode
import xyz.lbres.exactnumbers.utils.getOrSet
import xyz.lbres.kotlinutils.collection.ext.toConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.constMultiSetOf
import xyz.lbres.kotlinutils.set.multiset.mapToSetConsistent
import java.math.BigDecimal

/**
 * Expression which is the sum of several other expressions.
 * Can also represent 1/sum
 */
@Suppress("EqualsOrHashCode")
internal class AdditiveExpression private constructor(private val expressions: ConstMultiSet<Expression>, private val isInverted: Boolean) : ExpressionImpl() {
    private var term: Term? = null

    init {
        if (expressions.isEmpty()) {
            throw Exception("Invalid expression")
        }
    }

    constructor(expr1: Expression, expr2: Expression) : this(constMultiSetOf(expr1, expr2), false)

    override fun unaryPlus(): Expression = this
    override fun unaryMinus(): Expression {
        val newExpressions = expressions.mapToSetConsistent { -it }.toConstMultiSet()
        return AdditiveExpression(newExpressions, isInverted)
    }

    override fun inverse(): Expression = AdditiveExpression(expressions, !isInverted)

    override fun toTerm(): Term {
        return getOrSet({ term }, { term = it }) {
            val terms = expressions.map { it.toTerm() }
                .groupBy { it.factors }.map {
                    val coefficient = it.value.fold(ExactFraction.ZERO) { acc, t -> acc + t.coefficient } // value is list of terms
                    Term.fromValues(coefficient, it.key) // key is list of factors
                }
            Term.ZERO
            // TODO
        }
    }

    // TODO
    override fun getValue(): BigDecimal = BigDecimal.ZERO
    // TODO
    override fun getSimplified(): Expression = this

    override fun hashCode(): Int = createHashCode(listOf(expressions, "AdditiveExpression"))

    override fun toString(): String = "(${expressions.joinToString("+")})"
}
