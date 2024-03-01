package xyz.lbres.exactnumbers.expressions.expression.simple

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.expression.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import java.math.BigDecimal
import kotlin.test.assertEquals

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
