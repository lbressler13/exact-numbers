package xyz.lbres.exactnumbers.expressions.expression.additive

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

    expr1 = MultiplicativeExpression(-simpleExpr2, partialMultExpr)
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

    expr1 = MultiplicativeExpression(Expression.ZERO, partialMultExpr)
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

    expr1 = MultiplicativeExpression(simpleExpr1, partialMultExpr)
    expr2 = MultiplicativeExpression(simpleExpr1.inverse(), partialMultExpr)
    assertNotEquals(expr1, expr2)
    assertNotEquals(expr2, expr1)

    expr1 = MultiplicativeExpression(simpleExpr1, partialMultExpr)
    expr2 = MultiplicativeExpression(simpleExpr1.inverse(), partialMultExpr.inverse())
    assertNotEquals(expr1, expr2)
    assertNotEquals(expr2, expr1)
}

fun runToStringTests() {
    var expr = AdditiveExpression(simpleExpr1, simpleExpr1)
    var expected = "($simpleExpr1+$simpleExpr1)"
    assertEquals(expected, expr.toString())

    expr = AdditiveExpression(simpleExpr1.inverse(), simpleExpr3)
    expected = "(${simpleExpr1.inverse()}+$simpleExpr3)"
    assertEquals(expected, expr.toString())

    expr = AdditiveExpression(partialMultExpr, simpleExpr2)
    expected = "((${simpleExpr3}x$simpleExpr1)+$simpleExpr2)"
    assertEquals(expected, expr.toString())

    expr = AdditiveExpression(partialAddExpr, partialMultExpr)
    expected = "(($simpleExpr2+$simpleExpr3)+(${simpleExpr3}x$simpleExpr1))"
    assertEquals(expected, expr.toString())
}
