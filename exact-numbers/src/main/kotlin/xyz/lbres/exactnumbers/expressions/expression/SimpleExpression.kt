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
internal class SimpleExpression(private val term: Term) : ExpressionImpl() {

    override fun unaryPlus(): Expression = this
    override fun unaryMinus(): Expression = SimpleExpression(-term)
    override fun inverse(): Expression = SimpleExpression(term.inverse())

    override fun toTerm(): Term = term
    override fun getSimplified(): SimpleExpression = SimpleExpression(term.getSimplified())
    override fun getValue(): BigDecimal = term.getValue()

    override fun plus(other: Expression): Expression {
        if (other == ZERO) {
            return this
        }

        if (other is SimpleExpression) {
            val simplified = term.getSimplified()
            val otherSimplified = other.term.getSimplified()
            if (simplified.factors == otherSimplified.factors) {
                val newCoefficient = simplified.coefficient + otherSimplified.coefficient
                return SimpleExpression(Term.fromValues(newCoefficient, simplified.factors))
            }
        }

        return AdditiveExpression(this, other)
    }

    override fun times(other: Expression): Expression {
        return if (other is SimpleExpression) {
            SimpleExpression(this.term * other.term)
        } else {
            MultiplicativeExpression(this, other)
        }
    }

    override fun hashCode(): Int = createHashCode(listOf(term, "Expression"))

    override fun toString(): String = "($term)"
}
