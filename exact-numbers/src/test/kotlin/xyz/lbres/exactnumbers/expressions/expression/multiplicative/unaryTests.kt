package xyz.lbres.exactnumbers.expressions.expression.multiplicative

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.Expression
import xyz.lbres.exactnumbers.expressions.expression.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.testutils.assertDivByZero
import kotlin.test.assertEquals

fun runUnaryMinusTests() {
    var expr = MultiplicativeExpression(simpleExpr1, Expression.ZERO)
    var expected = MultiplicativeExpression(simpleExpr1, Expression.ZERO)
    assertEquals(expected, -expr)

    expr = MultiplicativeExpression(simpleExpr1, simpleExpr1)
    expected = MultiplicativeExpression(SimpleExpression(Term.fromValues(-ExactFraction.EIGHT, listOf(pi))), simpleExpr1)
    assertEquals(expected, -expr)

    expr = MultiplicativeExpression(-simpleExpr2, partialExpr)
    expected = MultiplicativeExpression(simpleExpr2, partialExpr)
    assertEquals(expected, -expr)

    expr = MultiplicativeExpression(simpleExpr2, simpleExpr3)
    expected = MultiplicativeExpression(SimpleExpression(Term.fromValues(-one, listOf(sqrt1))), simpleExpr2)
    assertEquals(expected, -expr)

    expr = MultiplicativeExpression(partialExpr, MultiplicativeExpression(partialExpr, Expression.ONE))
    expected = MultiplicativeExpression(partialExpr, MultiplicativeExpression(partialExpr, SimpleExpression(-Term.ONE)))
    assertEquals(expected, -expr)
}

fun runUnaryPlusTests() {
    var expr = MultiplicativeExpression(simpleExpr1, Expression.ZERO)
    assertEquals(expr, +expr)

    expr = MultiplicativeExpression(simpleExpr1, simpleExpr1)
    assertEquals(expr, +expr)

    expr = MultiplicativeExpression(-simpleExpr2, partialExpr)
    assertEquals(expr, +expr)

    expr = MultiplicativeExpression(simpleExpr2, simpleExpr3)
    assertEquals(expr, +expr)

    expr = MultiplicativeExpression(partialExpr, MultiplicativeExpression(partialExpr, simpleExpr2.inverse()))
    assertEquals(expr, +expr)
}

fun runInverseTests() {
    assertDivByZero { MultiplicativeExpression(Expression.ONE, Expression.ZERO).inverse() }

    var expr = MultiplicativeExpression(Expression.ONE, Expression.ONE)
    var expected = Expression.ONE
    assertEquals(expected, expr.inverse())

    expr = MultiplicativeExpression(simpleExpr1, simpleExpr1.inverse())
    expected = MultiplicativeExpression(simpleExpr1, simpleExpr1.inverse())
    assertEquals(expected, expr.inverse())

    expr = MultiplicativeExpression(simpleExpr2, simpleExpr1.inverse())
    expected = MultiplicativeExpression(simpleExpr2.inverse(), simpleExpr1)
    assertEquals(expected, expr.inverse())

    expr = MultiplicativeExpression(partialExpr, simpleExpr2)
    val partialInverse = MultiplicativeExpression(simpleExpr1.inverse(), simpleExpr3.inverse())
    expected = MultiplicativeExpression(partialInverse, simpleExpr2.inverse())
    assertEquals(expected, expr.inverse())
}

fun runGetValueTests() {
}
