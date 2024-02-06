package xyz.lbres.exactnumbers.expressions.expression.multiplicative

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.Expression
import xyz.lbres.exactnumbers.expressions.expression.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.exactnumbers.expressions.term.Term
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val simpleExpr1 = SimpleExpression(Term.fromValues(ExactFraction.EIGHT, listOf(pi)))
private val simpleExpr2 = SimpleExpression(Term.fromValues(ExactFraction(8, 17), listOf(log4, sqrt2, piInverse, pi)))
private val simpleExpr3 = SimpleExpression(Term.fromValues(one, listOf(sqrt1)))
private val partialExpr = MultiplicativeExpression(simpleExpr3, simpleExpr1)

fun runEqualsTests() {
    // equal
    var expr1 = MultiplicativeExpression(simpleExpr1, Expression.ZERO)
    assertEquals(expr1, expr1)

    expr1 = MultiplicativeExpression(simpleExpr1, simpleExpr1)
    assertEquals(expr1, expr1)

    expr1 = MultiplicativeExpression(-simpleExpr2, partialExpr)
    assertEquals(expr1, expr1)

    expr1 = MultiplicativeExpression(simpleExpr2, simpleExpr3)
    var expr2 = MultiplicativeExpression(simpleExpr3, simpleExpr2)
    assertEquals(expr1, expr2)
    assertEquals(expr2, expr1)

    expr1 = MultiplicativeExpression(simpleExpr2, simpleExpr1)
    expr2 = MultiplicativeExpression(-simpleExpr1, -simpleExpr2)
    assertEquals(expr1, expr2)
    assertEquals(expr2, expr1)

    // TODO account for small diff
    // expr1 = MultiplicativeExpression(simpleExpr3, simpleExpr3.inverse())
    // expr2 = MultiplicativeExpression(Expression.ONE, Expression.ONE)
    // assertEquals(expr1, expr2)
    // assertEquals(expr2, expr1)

    expr1 = MultiplicativeExpression(Expression.ONE, MultiplicativeExpression(simpleExpr1, simpleExpr2))
    expr2 = MultiplicativeExpression(simpleExpr1, simpleExpr2)
    assertEquals(expr1, expr2)
    assertEquals(expr2, expr1)

    // not equal
    expr1 = MultiplicativeExpression(simpleExpr1, simpleExpr1)
    expr2 = MultiplicativeExpression(simpleExpr1, -simpleExpr1)
    assertNotEquals(expr1, expr2)
    assertNotEquals(expr2, expr1)

    expr1 = MultiplicativeExpression(simpleExpr2.inverse(), MultiplicativeExpression(simpleExpr1, simpleExpr2))
    expr2 = MultiplicativeExpression(simpleExpr1, simpleExpr2)
    assertNotEquals(expr1, expr2)
    assertNotEquals(expr2, expr1)

    expr1 = MultiplicativeExpression(simpleExpr1, partialExpr)
    expr2 = MultiplicativeExpression(simpleExpr1.inverse(), partialExpr)
    assertNotEquals(expr1, expr2)
    assertNotEquals(expr2, expr1)

    expr1 = MultiplicativeExpression(simpleExpr1, partialExpr)
    expr2 = MultiplicativeExpression(simpleExpr1.inverse(), partialExpr.inverse())
    assertNotEquals(expr1, expr2)
    assertNotEquals(expr2, expr1)
}

fun runToStringTests() {
    var expr = MultiplicativeExpression(simpleExpr1, simpleExpr1)
    var expected = "(${simpleExpr1}x$simpleExpr1)"
    assertEquals(expected, expr.toString())

    expr = MultiplicativeExpression(simpleExpr1.inverse(), simpleExpr3)
    expected = "(${simpleExpr1.inverse()}x$simpleExpr3)"
    assertEquals(expected, expr.toString())

    expr = MultiplicativeExpression(partialExpr, simpleExpr2)
    expected = "((${simpleExpr3}x$simpleExpr1)x$simpleExpr2)"
    assertEquals(expected, expr.toString())
}
