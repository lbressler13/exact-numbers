package xyz.lbres.exactnumbers.expressions.expression.multiplicative

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.Expression
import xyz.lbres.exactnumbers.expressions.expression.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.testutils.assertDivByZero
import java.math.BigDecimal
import kotlin.test.assertEquals

fun runUnaryMinusTests() {
    var expr = MultiplicativeExpression(simpleExpr1, Expression.ZERO)
    var expected = MultiplicativeExpression(simpleExpr1, Expression.ZERO)
    assertEquals(expected, -expr)

    expr = MultiplicativeExpression(simpleExpr1, simpleExpr1)
    expected = MultiplicativeExpression(SimpleExpression(Term.fromValues(-ExactFraction.EIGHT, listOf(pi))), simpleExpr1)
    assertEquals(expected, -expr)

    expr = MultiplicativeExpression(-simpleExpr2, partialMultExpr)
    expected = MultiplicativeExpression(simpleExpr2, partialMultExpr)
    assertEquals(expected, -expr)

    expr = MultiplicativeExpression(simpleExpr2, simpleExpr3)
    expected = MultiplicativeExpression(SimpleExpression(Term.fromValues(-one, listOf(sqrt1))), simpleExpr2)
    assertEquals(expected, -expr)

    expr = MultiplicativeExpression(partialMultExpr, MultiplicativeExpression(partialMultExpr, Expression.ONE))
    expected = MultiplicativeExpression(partialMultExpr, MultiplicativeExpression(partialMultExpr, SimpleExpression(-Term.ONE)))
    assertEquals(expected, -expr)
}

fun runUnaryPlusTests() {
    var expr = MultiplicativeExpression(simpleExpr1, Expression.ZERO)
    assertEquals(expr, +expr)

    expr = MultiplicativeExpression(simpleExpr1, simpleExpr1)
    assertEquals(expr, +expr)

    expr = MultiplicativeExpression(-simpleExpr2, partialMultExpr)
    assertEquals(expr, +expr)

    expr = MultiplicativeExpression(simpleExpr2, simpleExpr3)
    assertEquals(expr, +expr)

    expr = MultiplicativeExpression(partialMultExpr, MultiplicativeExpression(partialMultExpr, simpleExpr2.inverse()))
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

    expr = MultiplicativeExpression(partialMultExpr, simpleExpr2)
    val partialInverse = MultiplicativeExpression(simpleExpr1.inverse(), simpleExpr3.inverse())
    expected = MultiplicativeExpression(partialInverse, simpleExpr2.inverse())
    assertEquals(expected, expr.inverse())
}

fun runGetValueTests() {
    var expr = MultiplicativeExpression(Expression.ZERO, Expression.ZERO)
    var expected = BigDecimal.ZERO
    assertEquals(expected, expr.getValue())

    expr = MultiplicativeExpression(simpleExpr1, simpleExpr1.inverse())
    expected = BigDecimal.ONE
    assertEquals(expected, expr.getValue())

    expr = MultiplicativeExpression(simpleExpr1, MultiplicativeExpression(simpleExpr2, simpleExpr2.inverse()))
    expected = BigDecimal("25.132741228718344")
    assertEquals(expected, expr.getValue())

    expr = MultiplicativeExpression(simpleExpr2.inverse(), MultiplicativeExpression(simpleExpr2, simpleExpr1))
    expected = BigDecimal("25.132741228718344")
    assertEquals(expected, expr.getValue())

    expr = MultiplicativeExpression(partialMultExpr, simpleExpr2)
    expected = BigDecimal("-58.612243222514359594") // -58.61224322251435719068...
    assertEquals(expected, expr.getValue())

    expr = MultiplicativeExpression(partialMultExpr.inverse(), -simpleExpr2.inverse())
    expected = BigDecimal("0.017061281824748112795308108019747092438130381820126559258080078125")
    assertEquals(expected, expr.getValue())
}
