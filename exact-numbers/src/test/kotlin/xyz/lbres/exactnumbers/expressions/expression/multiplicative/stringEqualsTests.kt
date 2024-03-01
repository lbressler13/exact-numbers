package xyz.lbres.exactnumbers.expressions.expression.multiplicative

import xyz.lbres.exactnumbers.expressions.Expression
import xyz.lbres.exactnumbers.expressions.expression.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun runEqualsTests() {
    // equal
    var expr1 = MultiplicativeExpression(simpleExpr1, Expression.ZERO)
    assertEquals(expr1, expr1)

    expr1 = MultiplicativeExpression(simpleExpr1, simpleExpr1)
    assertEquals(expr1, expr1)

    expr1 = MultiplicativeExpression(-simpleExpr2, multExpr1)
    assertEquals(expr1, expr1)

    expr1 = MultiplicativeExpression(simpleExpr2, simpleExpr3)
    var expr2 = MultiplicativeExpression(simpleExpr3, simpleExpr2)
    assertEquals(expr1, expr2)
    assertEquals(expr2, expr1)

    expr1 = MultiplicativeExpression(simpleExpr2, simpleExpr1)
    expr2 = MultiplicativeExpression(-simpleExpr1, -simpleExpr2)
    assertEquals(expr1, expr2)
    assertEquals(expr2, expr1)

    expr1 = MultiplicativeExpression(simpleExpr3, simpleExpr3.inverse())
    expr2 = MultiplicativeExpression(Expression.ONE, Expression.ONE)
    assertEquals(expr1, expr2)
    assertEquals(expr2, expr1)

    expr1 = MultiplicativeExpression(Expression.ONE, MultiplicativeExpression(simpleExpr1, simpleExpr2))
    expr2 = MultiplicativeExpression(simpleExpr1, simpleExpr2)
    assertEquals(expr1, expr2)
    assertEquals(expr2, expr1)

    expr1 = MultiplicativeExpression(Expression.ZERO, multExpr1)
    expr2 = MultiplicativeExpression(simpleExpr2, MultiplicativeExpression(simpleExpr3, Expression.ZERO))
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

    expr1 = MultiplicativeExpression(simpleExpr1, multExpr1)
    expr2 = MultiplicativeExpression(simpleExpr1.inverse(), multExpr1)
    assertNotEquals(expr1, expr2)
    assertNotEquals(expr2, expr1)

    expr1 = MultiplicativeExpression(simpleExpr1, multExpr1)
    expr2 = MultiplicativeExpression(simpleExpr1.inverse(), multExpr1.inverse())
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

    expr = MultiplicativeExpression(multExpr1, simpleExpr2)
    expected = "((${simpleExpr3}x$simpleExpr1)x$simpleExpr2)"
    assertEquals(expected, expr.toString())
}
