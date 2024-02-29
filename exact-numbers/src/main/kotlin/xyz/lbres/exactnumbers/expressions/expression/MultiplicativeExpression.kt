package xyz.lbres.exactnumbers.expressions.expression

import xyz.lbres.exactnumbers.expressions.Expression
import xyz.lbres.exactnumbers.expressions.ExpressionImpl
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.utils.createHashCode
import xyz.lbres.exactnumbers.utils.getOrSet
import xyz.lbres.kotlinutils.bigdecimal.ext.isZero
import xyz.lbres.kotlinutils.collection.ext.toConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.anyConsistent
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.constMultiSetOf
import xyz.lbres.kotlinutils.set.multiset.mapToSetConsistent
import java.math.BigDecimal

/**
 * Expression which is the product of several other expressions
 */
@Suppress("EqualsOrHashCode")
internal class MultiplicativeExpression private constructor(expressions: ConstMultiSet<Expression>) : ExpressionImpl() {
    private val expressions: ConstMultiSet<Expression>
    private var term: Term? = null

    init {
        this.expressions = when {
            expressions.isEmpty() -> throw Exception("Invalid expression")
            expressions.anyConsistent { it.getValue().isZero() } -> constMultiSetOf(ZERO)
            else -> expressions
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
        return getOrSet({ term }, { term = it }) {
            expressions.fold(Term.ONE) { acc, expr -> acc * expr.toTerm() }.getSimplified()
        }
    }

    fun getSimplified(): Expression = SimpleExpression(toTerm())
    override fun getValue(): BigDecimal = getSimplified().getValue()

    override fun hashCode(): Int = createHashCode(listOf(expressions, "MultiplicativeExpression"))

    override fun toString(): String = "(${expressions.joinToString("x")})"
}
