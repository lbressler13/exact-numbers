package xyz.lbres.exactnumbers.expressions.expression

import xyz.lbres.exactnumbers.expressions.Expression
import xyz.lbres.exactnumbers.expressions.ExpressionImpl
import xyz.lbres.exactnumbers.utils.createHashCode
import xyz.lbres.kotlinutils.collection.ext.toConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.constMultiSetOf
import xyz.lbres.kotlinutils.set.multiset.mapToSetConsistent
import java.math.BigDecimal

/**
 * Expression which is the product of several other expressions
 */
internal class MultiplicativeExpression(private val expressions: ConstMultiSet<Expression>) : ExpressionImpl() {
    init {
        if (expressions.isEmpty()) {
            throw Exception("Invalid expression initialization")
        }
    }

    override fun unaryMinus(): Expression {
        val newExpressions = (expressions + constMultiSetOf(-ONE)).toConstMultiSet()
        return MultiplicativeExpression(newExpressions)
    }
    override fun unaryPlus(): Expression = this

    override fun inverse(): Expression {
        val newExpressions = expressions.mapToSetConsistent { it.inverse() }.toConstMultiSet()
        return MultiplicativeExpression(newExpressions)
    }

    override fun getValue(): BigDecimal {
        return expressions.fold(BigDecimal.ONE) { acc, expr -> acc * expr.getValue() }
    }

    override fun equals(other: Any?): Boolean = other is Expression && getValue() == other.getValue()
    override fun hashCode(): Int = createHashCode(listOf(expressions, "MultiplicativeExpression"))

    override fun toString(): String = "(${expressions.joinToString("x")})"
}
