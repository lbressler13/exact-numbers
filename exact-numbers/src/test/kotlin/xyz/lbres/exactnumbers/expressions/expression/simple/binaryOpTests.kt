package xyz.lbres.exactnumbers.expressions.expression.simple

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.Expression
import xyz.lbres.exactnumbers.expressions.expression.*
import xyz.lbres.exactnumbers.expressions.term.Term
import kotlin.test.assertEquals

fun runPlusTests() {
    // zero
    var expr1 = SimpleExpression(Term.ZERO)
    var expr2: Expression = SimpleExpression(Term.ZERO)
    var expected: Expression = SimpleExpression(Term.ZERO)
    assertEquals(expected, expr1 + expr2)

    expr1 = SimpleExpression(Term.fromValues(ExactFraction(14), listOf(pi)))
    expr2 = AdditiveExpression(Expression.ZERO, Expression.ZERO)
    // expr2 = AdditiveExpression(expr1, -expr1)
    expected = SimpleExpression(Term.fromValues(ExactFraction(14), listOf(pi)))
    assertEquals(expected, expr1 + expr2)

    // simple expression w/ same factors
    expr1 = SimpleExpression(Term.ZERO)
    expr2 = SimpleExpression(Term.fromValues(ExactFraction(-9, 7), emptyList()))
    expected = SimpleExpression(Term.fromValues(ExactFraction(-9, 7), emptyList()))
    assertEquals(expected, expr1 + expr2)

    expr1 = SimpleExpression(Term.fromValues(ExactFraction(2), listOf(pi, pi, log3, sqrt2)))
    expr2 = SimpleExpression(Term.fromValues(ExactFraction(4), listOf(pi, pi, log3, sqrt2)))
    expected = SimpleExpression(Term.fromValues(ExactFraction(6), listOf(pi, pi, log3, sqrt2)))
    assertEquals(expected, expr1 + expr2)

    expr1 = SimpleExpression(Term.fromValues(ExactFraction(2), listOf(pi, piInverse, log3, sqrt2)))
    expr2 = SimpleExpression(Term.fromValues(ExactFraction(4), listOf(log3, sqrt2)))
    expected = SimpleExpression(Term.fromValues(ExactFraction(6), listOf(log3, sqrt2)))
    assertEquals(expected, expr1 + expr2)

    // simple expression w/ different factors
    expr1 = SimpleExpression(Term.fromValues(ExactFraction(2), listOf(pi, piInverse, log3, sqrt2)))
    expr2 = SimpleExpression(Term.fromValues(ExactFraction(4), listOf(log3.inverse(), sqrt2)))
    expected = AdditiveExpression(expr1, expr2)
    assertEquals(expected, expr1 + expr2)

    // multi-term expression
}

fun runMinusTests() {

}

fun runTimesTests() {

}

fun runDivTests() {

}
