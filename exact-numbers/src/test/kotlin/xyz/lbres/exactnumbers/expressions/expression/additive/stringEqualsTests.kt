package xyz.lbres.exactnumbers.expressions.expression.additive

import xyz.lbres.exactnumbers.expressions.expression.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.assertEquals

fun runEqualsTests() {
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
