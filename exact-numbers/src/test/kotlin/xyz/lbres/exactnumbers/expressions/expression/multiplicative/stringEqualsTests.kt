package xyz.lbres.exactnumbers.expressions.expression.multiplicative

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.expression.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.kotlinutils.set.multiset.const.constMultiSetOf
import kotlin.test.assertEquals

private val simpleExpr1 = SimpleExpression(Term.fromValues(ExactFraction.EIGHT, listOf(pi)))
private val simpleExpr2 = SimpleExpression(Term.fromValues(ExactFraction(8, 17), listOf(log4, sqrt2, piInverse, pi)))
private val simpleExpr3 = SimpleExpression(Term.fromValues(one, listOf(sqrt1)))

fun runEqualsTests() {
}

fun runToStringTests() {
    var expr = MultiplicativeExpression(constMultiSetOf(simpleExpr1))
    var expected = "($simpleExpr1)"
    assertEquals(expected, expr.toString())

    expr = MultiplicativeExpression(constMultiSetOf(simpleExpr1, simpleExpr1, simpleExpr3))
    expected = "(${simpleExpr1}x${simpleExpr1}x${simpleExpr3})"
    assertEquals(expected, expr.toString())

    var multExpr = MultiplicativeExpression(constMultiSetOf(simpleExpr2))
    expr = MultiplicativeExpression(constMultiSetOf(multExpr))
    expected = "(($simpleExpr2))"
    assertEquals(expected, expr.toString())

    multExpr = MultiplicativeExpression(constMultiSetOf(simpleExpr2, simpleExpr1))
    expr = MultiplicativeExpression(constMultiSetOf(multExpr, simpleExpr3))
    expected = "((${simpleExpr2}x${simpleExpr1})x${simpleExpr3})"
    assertEquals(expected, expr.toString())
}
