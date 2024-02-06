package xyz.lbres.exactnumbers.expressions.expression

import xyz.lbres.exactnumbers.expressions.Expression
import xyz.lbres.exactnumbers.expressions.ExpressionImpl
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.utils.createHashCode
import java.math.BigDecimal

/**
 * Expression consisting of a single term
 */
internal class SimpleExpression(private val term: Term) : ExpressionImpl() {
    override fun unaryMinus(): Expression = SimpleExpression(-term)
    override fun unaryPlus(): Expression = this
    override fun inverse(): Expression = SimpleExpression(term.inverse())

    override fun toTerm(): Term = term

    override fun equals(other: Any?): Boolean = other is Expression && getValue() == other.getValue()
    override fun hashCode(): Int = createHashCode(listOf(term, "Expression"))

    override fun toString(): String = "($term)"
}
