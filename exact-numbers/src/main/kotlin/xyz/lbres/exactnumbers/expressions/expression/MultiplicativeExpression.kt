package xyz.lbres.exactnumbers.expressions.expression

import xyz.lbres.exactnumbers.expressions.Expression
import xyz.lbres.exactnumbers.expressions.ExpressionImpl
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.utils.createHashCode
import xyz.lbres.kotlinutils.collection.ext.toConstMultiSet
import xyz.lbres.kotlinutils.generic.ext.ifNull
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.constMultiSetOf
import xyz.lbres.kotlinutils.set.multiset.mapToSetConsistent

/**
 * Expression which is the product of several other expressions
 */
@Suppress("EqualsOrHashCode")
internal class MultiplicativeExpression private constructor(private val expressions: ConstMultiSet<Expression>) : ExpressionImpl() {
    private var term: Term? = null

    init {
        if (expressions.isEmpty()) {
            throw Exception("Invalid expression")
        }
    }

    constructor(expr1: Expression, expr2: Expression) : this(constMultiSetOf(expr1, expr2))

    override fun unaryPlus(): Expression = this
    override fun unaryMinus(): Expression {
        val newExpressions = (expressions + constMultiSetOf(-ONE)).toConstMultiSet()
        return MultiplicativeExpression(newExpressions)
    }

    override fun inverse(): Expression {
        val newExpressions = expressions.mapToSetConsistent { it.inverse() }.toConstMultiSet()
        return MultiplicativeExpression(newExpressions)
    }

    override fun toTerm(): Term {
        return term.ifNull {
            term = expressions.fold(Term.ONE) { acc, expr -> acc * expr.toTerm() }.getSimplified()
            term!!
        }
    }

    override fun hashCode(): Int = createHashCode(listOf(expressions, "MultiplicativeExpression"))

    override fun toString(): String = "(${expressions.joinToString("x")})"
}
