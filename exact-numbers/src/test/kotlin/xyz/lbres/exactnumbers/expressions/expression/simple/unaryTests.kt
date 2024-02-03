package xyz.lbres.exactnumbers.expressions.expression.simple

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.expression.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.exactnumbers.testutils.assertDivByZero
import kotlin.test.assertEquals

fun runUnaryMinusTests() {
    var expr = SimpleExpression(Term.ZERO)
    var expected = SimpleExpression(Term.ZERO)
    assertEquals(expected, -expr)

    expr = SimpleExpression(Term.fromValues(one, listOf(Log.ONE)))
    expected = SimpleExpression(Term.fromValues(-one, listOf(Log.ONE)))
    assertEquals(expected, -expr)

    expr = SimpleExpression(Term.fromValues(one, listOf(pi)))
    expected = SimpleExpression(Term.fromValues(-one, listOf(pi)))
    assertEquals(expected, -expr)

    expr = SimpleExpression(Term.fromValues(ExactFraction(44, 15), emptyList()))
    expected = SimpleExpression(Term.fromValues(ExactFraction(-44, 15), emptyList()))
    assertEquals(expected, -expr)

    expr = SimpleExpression(
        Term.fromValues(
            ExactFraction(-15, 44),
            listOf(log2, log3, log4, Sqrt(ExactFraction(64, 9)), pi, piInverse)
        )
    )
    expected = SimpleExpression(
        Term.fromValues(
            ExactFraction(15, 44),
            listOf(log2, log3, log4, Sqrt(ExactFraction(64, 9)), pi, piInverse)
        )
    )
    assertEquals(expected, -expr)
}

fun runUnaryPlusTests() {
    var expr = SimpleExpression(Term.ZERO)
    assertEquals(expr, +expr)

    expr = SimpleExpression(Term.fromValues(one, listOf(Log.ONE)))
    assertEquals(expr, +expr)

    expr = SimpleExpression(Term.fromValues(one, listOf(pi)))
    assertEquals(expr, +expr)

    expr = SimpleExpression(Term.fromValues(ExactFraction(44, 15), emptyList()))
    assertEquals(expr, +expr)

    expr = SimpleExpression(
        Term.fromValues(
            ExactFraction(-15, 44),
            listOf(log2, log3, log4, Sqrt(ExactFraction(64, 9)), pi, piInverse)
        )
    )
    assertEquals(expr, +expr)
}

fun runInverseTests() {
    assertDivByZero { SimpleExpression(Term.ZERO).inverse() }

    var expr1 = SimpleExpression(Term.ONE)
    var expr2 = SimpleExpression(Term.ONE)
    assertEquals(expr1, expr2.inverse())

    expr1 = SimpleExpression(Term.fromValues(ExactFraction(17, 12), emptyList()))
    expr2 = SimpleExpression(Term.fromValues(ExactFraction(12, 17), emptyList()))
    assertEquals(expr1, expr2.inverse())
    assertEquals(expr2, expr1.inverse())

    expr1 = SimpleExpression(Term.fromValues(one, listOf(log3)))
    expr2 = SimpleExpression(Term.fromValues(one, listOf(log3.inverse())))
    assertEquals(expr1, expr2.inverse())
    assertEquals(expr2, expr1.inverse())

    expr1 = SimpleExpression(Term.fromValues(one, listOf(pi, piInverse, sqrt2)))
    expr2 = SimpleExpression(Term.fromValues(one, listOf(pi, piInverse, sqrt2.inverse())))
    assertEquals(expr1, expr2.inverse())
    assertEquals(expr2, expr1.inverse())

    expr1 = SimpleExpression(Term.fromValues(ExactFraction(-1, 9), listOf(testNumber2, testNumber2, pi, log4, pi, sqrt1)))
    expr2 = SimpleExpression(
        Term.fromValues(
            -ExactFraction.NINE,
            listOf(testNumber2.inverse(), testNumber2.inverse(), piInverse, piInverse, log4.inverse(), sqrt1.inverse())
        )
    )
    assertEquals(expr1, expr2.inverse())
    assertEquals(expr2, expr1.inverse())

    expr1 = SimpleExpression(
        Term.fromValues(
            ExactFraction(100, 99999999999),
            listOf(Log(4), pi, pi, Log(14, 3))
        )
    )
    expr2 = SimpleExpression(
        Term.fromValues(
            ExactFraction(99999999999, 100),
            listOf(Log(4).inverse(), piInverse, piInverse, Log(14, 3).inverse())
        )
    )
    assertEquals(expr1, expr2.inverse())
    assertEquals(expr2, expr1.inverse())
}
