package expressions.additive

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.log.Log
import exactnumbers.irrationals.pi.Pi
import exactnumbers.irrationals.sqrt.Sqrt
import expressions.term.Term
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotEquals
import kotlin.test.Test

class AdditiveExpressionTest {
    private val term1 = Term(ExactFraction.ONE, listOf(Log(8, 5), Pi(), Sqrt(ExactFraction(9, 4))))
    private val term2 = Term(ExactFraction.TWO, listOf(Pi(), Pi(), Log(1000)))
    private val term3 = Term(ExactFraction(14, 5), listOf())

    @Test
    fun testConstructor() {
        // error
        assertFails("Expression must contain at least one value") { AdditiveExpression(listOf()) }

        // single term
        var term = Term.ZERO
        var expr = AdditiveExpression(term)
        assertEquals(listOf(term), expr.terms)

        term = term1
        expr = AdditiveExpression(term)
        assertEquals(listOf(term), expr.terms)

        term = term2
        expr = AdditiveExpression(term)
        assertEquals(listOf(term), expr.terms)

        term = term3
        expr = AdditiveExpression(term)
        assertEquals(listOf(term), expr.terms)

        // several terms
        var terms = listOf(Term.ZERO)
        expr = AdditiveExpression(terms)
        assertEquals(terms, expr.terms)

        terms = listOf(term3, Term(-ExactFraction.TEN, listOf()))
        expr = AdditiveExpression(terms)
        assertEquals(terms, expr.terms)

        terms = listOf(term1, term2, term1, Term.ZERO)
        expr = AdditiveExpression(terms)
        assertEquals(terms, expr.terms)
    }

    @Test
    fun testUnaryPlus() {
        var expr = AdditiveExpression.ZERO
        assertEquals(expr, +expr)

        expr = AdditiveExpression(term1)
        assertEquals(expr, +expr)

        expr = AdditiveExpression(listOf(Term(ExactFraction.TEN, listOf(Pi())), Term(-ExactFraction.TEN, listOf(Pi()))))
        assertEquals(expr, +expr)

        expr = AdditiveExpression(listOf(term1, term2, term3, term3))
        assertEquals(expr, +expr)
    }

    @Test
    fun testUnaryMinus() {
        var expr = AdditiveExpression.ZERO
        var expected = AdditiveExpression.ZERO
        assertEquals(expected, -expr)

        expr = AdditiveExpression(term1)
        expected = AdditiveExpression(-term1)
        assertEquals(expected, -expr)

        expr = AdditiveExpression(listOf(Term(ExactFraction.TEN, listOf(Pi())), Term(-ExactFraction.TEN, listOf(Pi()))))
        expected = AdditiveExpression(listOf(Term(-ExactFraction.TEN, listOf(Pi())), Term(ExactFraction.TEN, listOf(Pi()))))
        assertEquals(expected, -expr)

        expr = AdditiveExpression(listOf(term1, term2, term3, term3))
        expected = AdditiveExpression(listOf(-term1, -term2, -term3, -term3))
        assertEquals(expected, -expr)

        expr = AdditiveExpression(listOf(term1, term2, Term.ZERO, term3))
        expected = AdditiveExpression(listOf(-term1, -term2, Term.ZERO, -term3))
        assertEquals(expected, -expr)
    }

    @Test
    fun equalsTests() {
        var expr1 = AdditiveExpression.ZERO
        assertEquals(expr1, expr1)

        expr1 = AdditiveExpression(term1)
        assertEquals(expr1, expr1)

        expr1 = AdditiveExpression(listOf(Term.ONE, term1, term3))
        assertEquals(expr1, expr1)

        expr1 = AdditiveExpression(Term.ZERO)
        var expr2 = AdditiveExpression(listOf(Term.ZERO, Term.ZERO, Term.ZERO))
        assertEquals(expr1, expr2)
        assertEquals(expr2, expr1)

        expr1 = AdditiveExpression(listOf(term1, term2))
        expr2 = AdditiveExpression(listOf(term1, term2, Term.ZERO))
        assertEquals(expr1, expr2)
        assertEquals(expr2, expr1)

        expr1 = AdditiveExpression.ZERO
        expr2 = AdditiveExpression.ONE
        assertNotEquals(expr1, expr2)
        assertNotEquals(expr2, expr1)

        expr1 = AdditiveExpression(term1)
        expr2 = AdditiveExpression(term2)
        assertNotEquals(expr1, expr2)
        assertNotEquals(expr2, expr1)

        expr1 = AdditiveExpression(term1)
        expr2 = AdditiveExpression(listOf(term1, term1))
        assertNotEquals(expr1, expr2)
        assertNotEquals(expr2, expr1)

        expr1 = AdditiveExpression(listOf(term1, Term.ZERO))
        expr2 = AdditiveExpression(listOf(term1, term1))
        assertNotEquals(expr1, expr2)
        assertNotEquals(expr2, expr1)

        expr1 = AdditiveExpression(listOf(term2, Term.ZERO))
        expr2 = AdditiveExpression(listOf(term1, term2, term3))
        assertNotEquals(expr1, expr2)
        assertNotEquals(expr2, expr1)
    }

    // @Test fun testIsZero() {} // TODO
    // @Test fun testPlus() {} // TODO
    // @Test fun testMinus() {} // TODO
    // @Test fun testGetValue() {} // TODO
    // @Test fun testGetSimplified() {} // TODO
    // @Test fun testToString() {} // TODO
}