package xyz.lbres.exactnumbers.expressions.expression.simple

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.expression.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun runEqualsTests() {
    // equal
    var expr1 = SimpleExpression(Term.ONE)
    assertEquals(expr1, expr1)

    expr1 = SimpleExpression(Term.fromValues(ExactFraction(9, 13), emptyList()))
    assertEquals(expr1, expr1)

    expr1 = SimpleExpression(Term.fromValues(one, listOf(pi, piInverse, pi, testNumber1, log2, log4)))
    assertEquals(expr1, expr1)

    expr1 = SimpleExpression(Term.fromValues(one, listOf(pi, piInverse, pi)))
    var expr2 = SimpleExpression(Term.fromValues(one, listOf(pi)))
    assertEquals(expr1, expr2)
    assertEquals(expr2, expr1)

    expr1 = SimpleExpression(Term.fromValues(ExactFraction(-4, 7), listOf(Log.ZERO, Sqrt.ONE)))
    expr2 = SimpleExpression(Term.ZERO)
    assertEquals(expr1, expr2)
    assertEquals(expr2, expr1)

    expr1 = SimpleExpression(Term.fromValues(ExactFraction(-4, 7), listOf(testNumber2, Sqrt(ExactFraction(7, 9)), pi, log1, log1.inverse())))
    expr2 = SimpleExpression(Term.fromValues(ExactFraction(-4, 3), listOf(Sqrt(7), pi)))
    assertEquals(expr1, expr2)
    assertEquals(expr2, expr1)

    expr1 = SimpleExpression(Term.fromValues(ExactFraction(-4, 7), listOf(testNumber2, Sqrt(ExactFraction(7, 9)), pi, log1, log1.inverse())))
    expr2 = SimpleExpression(Term.fromValues(ExactFraction(-4, 3), listOf(Sqrt(7), Sqrt.ONE, piInverse, pi, pi)))
    assertEquals(expr1, expr2)
    assertEquals(expr2, expr1)

    // not equal
    expr1 = SimpleExpression(Term.ONE)
    expr2 = SimpleExpression(-Term.ONE)
    assertNotEquals(expr1, expr2)
    assertNotEquals(expr2, expr1)

    expr1 = SimpleExpression(Term.fromValues(ExactFraction.TWO, emptyList()))
    expr2 = SimpleExpression(Term.fromValues(ExactFraction.HALF, emptyList()))
    assertNotEquals(expr1, expr2)
    assertNotEquals(expr2, expr1)

    expr1 = SimpleExpression(Term.fromValues(one, listOf(pi)))
    expr2 = SimpleExpression(Term.fromValues(one, listOf(piInverse)))
    assertNotEquals(expr1, expr2)
    assertNotEquals(expr2, expr1)

    expr1 = SimpleExpression(Term.fromValues(ExactFraction(-4, 7), listOf(testNumber2, Sqrt(ExactFraction(7, 9)), pi, log1, log1.inverse())))
    expr2 = SimpleExpression(Term.fromValues(ExactFraction(-4, 3), listOf(Sqrt(7), Sqrt.ONE, piInverse, pi)))
    assertNotEquals(expr1, expr2)
    assertNotEquals(expr2, expr1)
}

fun runToStringTests() {
    var expr = SimpleExpression(Term.ZERO)
    var expected = "(<0>)"
    assertEquals(expected, expr.toString())

    expr = SimpleExpression(Term.ONE)
    expected = "(<1>)"
    assertEquals(expected, expr.toString())

    expr = SimpleExpression(Term.fromValues(ExactFraction(-25), emptyList()))
    expected = "(<-25>)"
    assertEquals(expected, expr.toString())

    expr = SimpleExpression(Term.fromValues(ExactFraction(44, 7), emptyList()))
    expected = "(<[44/7]>)"
    assertEquals(expected, expr.toString())

    expr = SimpleExpression(Term.fromValues(one, listOf(log2, log4, log1)))
    expected = "(<${log2}x${log4}x$log1>)"
    assertEquals(expected, expr.toString())

    expr = SimpleExpression(Term.fromValues(one, listOf(pi, piInverse, pi)))
    expected = "(<${pi}x${piInverse}x$pi>)"
    assertEquals(expected, expr.toString())

    expr = SimpleExpression(Term.fromValues(-one, listOf(Sqrt.ONE)))
    expected = "(<-1x${Sqrt.ONE}>)"
    assertEquals(expected, expr.toString())

    expr = SimpleExpression(Term.fromValues(ExactFraction.EIGHT, listOf(log3, Sqrt(12), testNumber2, pi)))
    expected = "(<8x${log3}x${Sqrt(12)}x${testNumber2}x$pi>)"
    assertEquals(expected, expr.toString())

    val sqrt1 = Sqrt(ExactFraction(1000, 109))
    val sqrt2 = Sqrt(5096)
    expr = SimpleExpression(
        Term.fromValues(
            ExactFraction(-100, 333),
            listOf(log2, log2, log4, testNumber1, log1, sqrt1, sqrt2, piInverse, pi)
        )
    )
    expected = "(<[-100/333]x${log2}x${log2}x${log4}x${testNumber1}x${log1}x${sqrt1}x${sqrt2}x${piInverse}x$pi>)"
    assertEquals(expected, expr.toString())
}
