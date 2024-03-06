package xyz.lbres.exactnumbers.expressions.expression.additive

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.Expression
import xyz.lbres.exactnumbers.expressions.expression.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.exactnumbers.expressions.term.Term
import java.math.BigDecimal
import kotlin.test.assertEquals

fun runGetValueTests() {
    // zero
    var expr = AdditiveExpression(Expression.ZERO, Expression.ZERO)
    assertEquals(BigDecimal.ZERO, expr.getValue())

    expr = AdditiveExpression(Expression.ZERO, Term.fromValues(ExactFraction.EIGHT, emptyList()).toExpression())
    assertEquals(BigDecimal("8"), expr.getValue())

    expr = AdditiveExpression(Expression.ZERO, Term.fromValues(ExactFraction(-8, 3), listOf(pi)).toExpression())
    var expected = BigDecimal("-8.3775804095727813333")
    assertEquals(expected, expr.getValue())

    // whole numbers
    expr = AdditiveExpression(Expression.ONE, Term.fromValues(ExactFraction.EIGHT, emptyList()).toExpression())
    assertEquals(BigDecimal("9"), expr.getValue())

    var partialExpr: Expression = MultiplicativeExpression( // 1/2
        SimpleExpression(Term.fromValues(ExactFraction(2), listOf(pi))),
        SimpleExpression(Term.fromValues(ExactFraction(1, 4), listOf(piInverse)))
    )
    expr = AdditiveExpression(-partialExpr, SimpleExpression(Term.fromValues(-ExactFraction.HALF, emptyList())))
    assertEquals(BigDecimal("-1"), expr.getValue())

    // decimals
    partialExpr = MultiplicativeExpression( // 1/2
        SimpleExpression(Term.fromValues(ExactFraction(2), listOf(pi))),
        SimpleExpression(Term.fromValues(ExactFraction(1, 4), listOf(piInverse)))
    )
    expr = AdditiveExpression(partialExpr, SimpleExpression(Term.fromValues(-ExactFraction.HALF, emptyList())))
    assertEquals(BigDecimal.ZERO, expr.getValue())

    expr = AdditiveExpression(simpleExpr1, simpleExpr2)
    // 24.898355650401539731532704947359132564894191522957989896...
    // expected = BigDecimal("24.89835565040153973153")
    expected = BigDecimal("24.89835565040153972193")
    assertEquals(expected, expr.getValue())

    expr = AdditiveExpression(simpleExpr3, partialExpr)
    // 10.449874371066199547344798210012060051781265636768060791...
    expected = BigDecimal("10.4498743710661995473")
    assertEquals(expected, expr.getValue())

    expr = AdditiveExpression(-partialMultExpr, partialAddExpr)
    // -240.2349362443556788229255752680569294317593715748...
    expected = BigDecimal("-240.3521290335140809656808241320056712")
    assertEquals(expected, expr.getValue())
}

fun runGetSimplifiedTests() {
}
