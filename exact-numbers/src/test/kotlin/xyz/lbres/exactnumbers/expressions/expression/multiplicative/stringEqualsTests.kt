package xyz.lbres.exactnumbers.expressions.expression.multiplicative

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.expression.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.exactnumbers.expressions.term.Term
import kotlin.test.assertEquals

private val simpleExpr1 = SimpleExpression(Term.fromValues(ExactFraction.EIGHT, listOf(pi)))
private val simpleExpr2 = SimpleExpression(Term.fromValues(ExactFraction(8, 17), listOf(log4, sqrt2, piInverse, pi)))
private val simpleExpr3 = SimpleExpression(Term.fromValues(one, listOf(sqrt1)))

fun runEqualsTests() {
}

fun runToStringTests() {
    var expr = MultiplicativeExpression(simpleExpr1, simpleExpr1)
    var expected = "(${simpleExpr1}x$simpleExpr1)"
    assertEquals(expected, expr.toString())

    expr = MultiplicativeExpression(simpleExpr1.inverse(), simpleExpr3)
    expected = "(${simpleExpr1.inverse()}x$simpleExpr3)"
    assertEquals(expected, expr.toString())

    val partialExpr = MultiplicativeExpression(simpleExpr3, simpleExpr1)
    expr = MultiplicativeExpression(partialExpr, simpleExpr2)
    expected = "((${simpleExpr3}x$simpleExpr1)x$simpleExpr2)"
    assertEquals(expected, expr.toString())
}
