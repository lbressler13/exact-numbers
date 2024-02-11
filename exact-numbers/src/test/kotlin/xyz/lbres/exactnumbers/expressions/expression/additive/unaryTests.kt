package xyz.lbres.exactnumbers.expressions.expression.additive

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.Expression
import xyz.lbres.exactnumbers.expressions.expression.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.exactnumbers.expressions.term.Term
import kotlin.test.assertEquals

fun runUnaryMinusTests() {
    var expr = AdditiveExpression(simpleExpr1, Expression.ZERO)
    var expected1: Expression = SimpleExpression(Term.fromValues(-ExactFraction.EIGHT, listOf(pi)))
    var expected = AdditiveExpression(expected1, Expression.ZERO)
    assertEquals(expected, -expr)

    expr = AdditiveExpression(simpleExpr1, simpleExpr1)
    expected1 = SimpleExpression(Term.fromValues(-ExactFraction.EIGHT, listOf(pi)))
    var expected2: Expression = SimpleExpression(Term.fromValues(-ExactFraction.EIGHT, listOf(pi)))
    expected = AdditiveExpression(expected1, expected2)
    assertEquals(expected, -expr)

    expr = AdditiveExpression(-simpleExpr2, partialMultExpr)
    expected1 = SimpleExpression(Term.fromValues(ExactFraction(8, 17), listOf(log4, sqrt2, piInverse, pi)))
    expected2 = MultiplicativeExpression(SimpleExpression(Term.fromValues(-one, listOf(sqrt1))), simpleExpr1)
    expected = AdditiveExpression(expected1, expected2)
    assertEquals(expected, -expr)

    expr = AdditiveExpression(simpleExpr2, simpleExpr3)
    expected1 = SimpleExpression(Term.fromValues(ExactFraction(-8, 17), listOf(log4, sqrt2, piInverse, pi)))
    expected2 = SimpleExpression(Term.fromValues(-one, listOf(sqrt1)))
    expected = AdditiveExpression(expected1, expected2)
    assertEquals(expected, -expr)

    expr = AdditiveExpression(partialMultExpr, AdditiveExpression(partialMultExpr, Expression.ONE))
    expected1 = MultiplicativeExpression(SimpleExpression(Term.fromValues(-one, listOf(sqrt1))), simpleExpr1)
    expected2 = AdditiveExpression(SimpleExpression(Term.fromValues(-one, listOf(sqrt1))), -Expression.ONE)
    expected = AdditiveExpression(expected1, expected2)
    assertEquals(expected, -expr)

    expr = AdditiveExpression(-simpleExpr2, partialAddExpr)
    expected1 = SimpleExpression(Term.fromValues(ExactFraction(-8, 17), listOf(log4, sqrt2, piInverse, pi)))
    expected2 = AdditiveExpression(
        SimpleExpression(Term.fromValues(ExactFraction(-8, 17), listOf(log4, sqrt2, piInverse, pi))),
        SimpleExpression(Term.fromValues(-one, listOf(sqrt1)))
    )
    expected = AdditiveExpression(expected1, expected2)
    assertEquals(expected, -expr)
}

fun runUnaryPlusTests() {
    var expr = AdditiveExpression(simpleExpr1, Expression.ZERO)
    assertEquals(expr, +expr)

    expr = AdditiveExpression(simpleExpr1, simpleExpr1)
    assertEquals(expr, +expr)

    expr = AdditiveExpression(-simpleExpr2, partialMultExpr)
    assertEquals(expr, +expr)

    expr = AdditiveExpression(simpleExpr2, simpleExpr3)
    assertEquals(expr, +expr)

    expr = AdditiveExpression(partialAddExpr, MultiplicativeExpression(partialMultExpr, simpleExpr2.inverse()))
    assertEquals(expr, +expr)

    expr = AdditiveExpression(partialMultExpr, AdditiveExpression(partialMultExpr, simpleExpr2.inverse()))
    assertEquals(expr, +expr)
}

fun runInverseTests() {
}

fun runGetValueTests() {
}
