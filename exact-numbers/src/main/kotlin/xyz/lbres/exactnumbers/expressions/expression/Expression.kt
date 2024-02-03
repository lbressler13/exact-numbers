package xyz.lbres.exactnumbers.expressions.expression

import xyz.lbres.exactnumbers.expressions.term.Term

/**
 * Representation of an expression, consisting of at least one term
 */
sealed class Expression : Number() {
    abstract operator fun unaryMinus(): Expression
    abstract operator fun unaryPlus(): Expression
    abstract fun inverse(): Expression

    // open operator fun plus(other: Expression): Expression = AdditiveExpression(constMultiSetOf(this, other))
    // open operator fun minus(other: Expression): Expression = AdditiveExpression(constMultiSetOf(this, -other))
    // open operator fun times(other: Expression): Expression = MultiplicativeExpression(constMultiSetOf(this, other))
    // open operator fun div(other: Expression): Expression = MultiplicativeExpression(constMultiSetOf(this, other.inverse()))

    companion object {
        val ZERO: Expression = SimpleExpression(Term.ZERO)
        val ONE: Expression = SimpleExpression(Term.ONE)
    }
}
