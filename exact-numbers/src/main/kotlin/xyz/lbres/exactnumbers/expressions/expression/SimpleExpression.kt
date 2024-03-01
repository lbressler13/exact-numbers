package xyz.lbres.exactnumbers.expressions.expression

import xyz.lbres.exactnumbers.expressions.Expression
import xyz.lbres.exactnumbers.expressions.ExpressionImpl
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.utils.createHashCode
import java.math.BigDecimal

/**
 * Expression consisting of a single term
 */
@Suppress("EqualsOrHashCode")
internal class SimpleExpression(val term: Term) : ExpressionImpl() {
    override fun unaryPlus(): Expression = this
    override fun unaryMinus(): Expression = SimpleExpression(-term)
    override fun inverse(): Expression = SimpleExpression(term.inverse())
    override fun isZero(): Boolean = term.isZero()

    override fun getSimplified(): SimpleExpression = SimpleExpression(term.getSimplified())
    override fun getValue(): BigDecimal = term.getValue()

    override fun hashCode(): Int = createHashCode(listOf(term, "Expression"))

    override fun toString(): String = "($term)"
}
