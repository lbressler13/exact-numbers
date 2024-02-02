package xyz.lbres.exactnumbers.expressions.expression

import xyz.lbres.exactnumbers.expressions.term.Term

/**
 * Expression consisting of a single term
 */
internal class SimpleExpression(private val term: Term) : Expression() {
    override fun unaryMinus(): Expression = SimpleExpression(-term)

    override fun unaryPlus(): Expression = this

    override fun inverse(): Expression = SimpleExpression(term.inverse())

    override fun equals(other: Any?): Boolean = other is SimpleExpression && term == other.term
}
