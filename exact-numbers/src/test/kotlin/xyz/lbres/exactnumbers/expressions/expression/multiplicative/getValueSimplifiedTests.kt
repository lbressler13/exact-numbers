package xyz.lbres.exactnumbers.expressions.expression.multiplicative

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.Expression
import xyz.lbres.exactnumbers.expressions.expression.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import java.math.BigDecimal
import kotlin.test.assertEquals

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

fun runGetSimplifiedTests() {
    var expr = MultiplicativeExpression(Expression.ONE, Expression.ZERO)
    assertEquals(Expression.ZERO, expr.getSimplified())

    expr = MultiplicativeExpression(partialMultExpr, partialMultExpr.inverse())
    assertEquals(Expression.ONE, expr.getSimplified())
    assertEquals(Expression.ONE, expr.getSimplified())

    expr = MultiplicativeExpression(simpleExpr1, Expression.ONE)
    var term = Term.fromValues(ExactFraction.EIGHT, listOf(pi))
    assertEquals(SimpleExpression(term), expr.getSimplified())

    expr = MultiplicativeExpression(simpleExpr1, partialMultExpr.inverse())
    term = Term.fromValues(one, listOf(sqrt1.inverse()))
    assertEquals(SimpleExpression(term), expr.getSimplified())
    assertEquals(SimpleExpression(term), expr.getSimplified())

    expr = MultiplicativeExpression(simpleExpr2, simpleExpr1)
    term = Term.fromValues(ExactFraction(512, 187), listOf(pi, log4))
    assertEquals(SimpleExpression(term), expr.getSimplified())
    assertEquals(SimpleExpression(term), expr.getSimplified())

    val expr1 = MultiplicativeExpression(-simpleExpr2, partialMultExpr.inverse())
    val expr2 = MultiplicativeExpression(simpleExpr1.inverse(), MultiplicativeExpression(simpleExpr3, simpleExpr3))
    expr = MultiplicativeExpression(expr1, expr2)
    term = Term.fromValues(ExactFraction(-3, 187), listOf(log4, Sqrt(11), piInverse, piInverse))
    assertEquals(SimpleExpression(term), expr.getSimplified())
}
