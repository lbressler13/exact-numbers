package expressions.additive

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.log.Log
import exactnumbers.irrationals.pi.Pi
import exactnumbers.irrationals.sqrt.Sqrt
import expressions.term.Term
import kotlin.test.assertEquals

private val term1 = Term(ExactFraction.ONE, listOf(Log(8, 5), Pi(), Sqrt(ExactFraction(9, 4))))
private val term2 = Term(ExactFraction.TWO, listOf(Pi(), Pi(), Log(1000)))
private val term3 = Term(ExactFraction(14, 5), listOf())
private val term4 = Term(ExactFraction(-2, 33), listOf(Log(ExactFraction(12, 13), 2), Pi(), Sqrt(16)))

fun runPlusTests() {
    // zero
    var expr1 = AdditiveExpression.ZERO

    var expr2 = AdditiveExpression.ZERO
    var expected = AdditiveExpression.ZERO
    assertEquals(expected, expr1 + expr2)
    assertEquals(expected, expr2 + expr1)

    expr2 = AdditiveExpression.ONE
    expected = AdditiveExpression.ONE
    assertEquals(expected, expr1 + expr2)
    assertEquals(expected, expr2 + expr1)

    expr2 = AdditiveExpression(term1)
    expected = AdditiveExpression(term1)
    assertEquals(expected, expr1 + expr2)
    assertEquals(expected, expr2 + expr1)

    expr2 = AdditiveExpression(listOf(term1, term4, term1))
    expected = AdditiveExpression(listOf(term1, term4, term1))
    assertEquals(expected, expr1 + expr2)
    assertEquals(expected, expr2 + expr1)

    // non-zero
    expr1 = AdditiveExpression(term1)
    expr2 = AdditiveExpression(term1)
    expected = AdditiveExpression(listOf(term1, term1))
    assertEquals(expected, expr1 + expr2)
    assertEquals(expected, expr2 + expr1)

    expr1 = AdditiveExpression(term2)
    expr2 = AdditiveExpression(term3)
    expected = AdditiveExpression(listOf(term2, term3))
    assertEquals(expected, expr1 + expr2)
    assertEquals(expected, expr2 + expr1)

    expr1 = AdditiveExpression(term2)
    expr2 = AdditiveExpression(listOf(term3, term4))
    expected = AdditiveExpression(listOf(term2, term3, term4))
    assertEquals(expected, expr1 + expr2)
    assertEquals(expected, expr2 + expr1)

    expr1 = AdditiveExpression(listOf(term3, Term.ZERO, term1, term2))
    expr2 = AdditiveExpression(listOf(term3, term4))
    expected = AdditiveExpression(listOf(term1, term2, term3, term3, term4))
    assertEquals(expected, expr1 + expr2)
    assertEquals(expected, expr2 + expr1)
}

fun runMinusTests() {
    // zero
    var expr1 = AdditiveExpression.ZERO

    var expr2 = AdditiveExpression.ZERO
    var expected = AdditiveExpression.ZERO
    assertEquals(expected, expr1 - expr2)

    expr2 = AdditiveExpression.ONE
    expected = -AdditiveExpression.ONE
    assertEquals(expected, expr1 - expr2)
    assertEquals(expr2, expr2 - expr1)

    expr2 = -AdditiveExpression.ONE
    expected = AdditiveExpression.ONE
    assertEquals(expected, expr1 - expr2)
    assertEquals(expr2, expr2 - expr1)

    expr2 = AdditiveExpression(listOf(term1, term3, term4))
    expected = AdditiveExpression(listOf(-term1, -term3, -term4))
    assertEquals(expected, expr1 - expr2)
    assertEquals(expr2, expr2 - expr1)

    // non-zero
    expr1 = AdditiveExpression(term1)
    expr2 = AdditiveExpression(term1)
    expected = AdditiveExpression(listOf(term1, -term1))
    assertEquals(expected, expr1 - expr2)

    expr1 = AdditiveExpression(-term2)
    expr2 = AdditiveExpression(term3)
    expected = AdditiveExpression(listOf(-term2, -term3))
    assertEquals(expected, expr1 - expr2)

    expr1 = AdditiveExpression(listOf(term1, term1, term2))
    expr2 = AdditiveExpression(listOf(term3, term4))
    expected = AdditiveExpression(listOf(term1, term1, term2, -term3, -term4))
    assertEquals(expected, expr1 - expr2)

    expr1 = AdditiveExpression(listOf(term1, -term1, term2))
    expr2 = AdditiveExpression(listOf(term3, -term4))
    expected = AdditiveExpression(listOf(term1, -term1, term2, -term3, term4))
    assertEquals(expected, expr1 - expr2)
}
