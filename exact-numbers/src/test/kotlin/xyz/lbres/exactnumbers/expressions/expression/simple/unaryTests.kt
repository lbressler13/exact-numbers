package xyz.lbres.exactnumbers.expressions.expression.simple

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.expression.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.exactnumbers.testutils.assertDivByZero
import java.math.BigDecimal
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

fun runGetValueTests() {
    var expr = SimpleExpression(Term.ZERO)
    var expected = BigDecimal.ZERO
    assertEquals(expected, expr.getValue())

    expr = SimpleExpression(Term.fromValues(ExactFraction.EIGHT, emptyList()))
    expected = BigDecimal("8")
    assertEquals(expected, expr.getValue())

    expr = SimpleExpression(Term.fromValues(ExactFraction(-1, 3), emptyList()))
    expected = BigDecimal("-0.33333333333333333333")
    assertEquals(expected, expr.getValue())

    expr = SimpleExpression(Term.fromValues(one, listOf(log1, log1.inverse())))
    expected = BigDecimal.ONE
    assertEquals(expected, expr.getValue())

    expr = SimpleExpression(Term.fromValues(one, listOf(log1, log2)))
    expected = BigDecimal("0.61342218956802803344500481172832")
    assertEquals(expected, expr.getValue())

    expr = SimpleExpression(Term.fromValues(one, listOf(piInverse)))
    expected = BigDecimal("0.31830988618379069570")
    assertEquals(expected, expr.getValue())

    expr = SimpleExpression(Term.fromValues(one, listOf(pi, pi, pi)))
    expected = BigDecimal("31.006276680299813114880451174049119330924860257")
    assertEquals(expected, expr.getValue())

    expr = SimpleExpression(Term.fromValues(one, listOf(Sqrt(ExactFraction(9, 5)))))
    expected = BigDecimal("1.34164078649987381784")
    assertEquals(expected, expr.getValue())

    expr = SimpleExpression(Term.fromValues(one, listOf(Sqrt(121))))
    expected = BigDecimal("11")
    assertEquals(expected, expr.getValue())

    expr = SimpleExpression(Term.fromValues(ExactFraction(-8, 3), listOf(pi)))
    expected = BigDecimal("-8.3775804095727813333")
    assertEquals(expected, expr.getValue())

    expr = SimpleExpression(Term.fromValues(ExactFraction.FOUR, listOf(Sqrt.ONE, testNumber2)))
    expected = BigDecimal("28")
    assertEquals(expected, expr.getValue())

    expr = SimpleExpression(Term.fromValues(ExactFraction.SEVEN, listOf(Log(ExactFraction(6, 7)), Log(40), pi)))
    expected = BigDecimal("-2.35861167086684457383417423198393663398251286036")
    assertEquals(expected, expr.getValue())

    expr = SimpleExpression(Term.fromValues(ExactFraction.HALF, listOf(Log(4, 2).inverse(), Log(123456789), piInverse)))
    expected = BigDecimal("0.64390230285929702583103243749028475")
    assertEquals(expected, expr.getValue())

    expr = SimpleExpression(Term.fromValues(ExactFraction(3, 11), listOf(Log(5, 2), Sqrt(122), pi)))
    expected = BigDecimal("21.973899001484265398")
    assertEquals(expected, expr.getValue())

    expr = SimpleExpression(Term.fromValues(one, listOf(testNumber2, testNumber2, testNumber1, Log(5, 2))))
    expected = BigDecimal("85.33085748711055350")
    assertEquals(expected, expr.getValue())
}

fun runGetSimplifiedTests() {
    var term = Term.fromValues(ExactFraction.EIGHT, listOf(pi, piInverse))
    var expected = Term.fromValues(ExactFraction.EIGHT, emptyList())
    assertEquals(expected, term.getSimplified())

    term = Term.fromValues(ExactFraction(-3, 2), listOf(log1, pi, piInverse, pi))
    expected = Term.fromValues(ExactFraction(-3, 2), listOf(log1, pi))
    assertEquals(expected, term.getSimplified())

    term = Term.fromValues(ExactFraction.TWO, listOf(Sqrt(64), Sqrt(ExactFraction(75, 98)), Sqrt(26)))
    expected = Term.fromValues(ExactFraction(80, 7), listOf(Sqrt(ExactFraction(39))))
    assertEquals(expected, term.getSimplified())

    term = Term.fromValues(ExactFraction.EIGHT, emptyList())
    expected = Term.fromValues(ExactFraction.EIGHT, emptyList())
    assertEquals(expected, term.getSimplified())

    term = Term.fromValues(ExactFraction.SEVEN, listOf(log1, log1, log2.inverse(), Sqrt(5), pi, pi))
    expected = Term.fromValues(ExactFraction.SEVEN, listOf(log1, log1, log2.inverse(), Sqrt(5), pi, pi))
    assertEquals(expected, term.getSimplified())
}
