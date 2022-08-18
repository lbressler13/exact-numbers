package expressions.additive

import exactnumbers.exactfraction.ExactFraction
import expressions.term.Term
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotEquals
import kotlin.test.Test

class AdditiveExpressionTest {
    @Test
    fun testConstructor() {
        // error
        assertFails("Expression must contain at least one value") { AdditiveExpression(listOf()) }

        // single term
        var term = termZero
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
        var terms = listOf(termZero)
        expr = AdditiveExpression(terms)
        assertEquals(terms, expr.terms)

        terms = listOf(term3, term5)
        expr = AdditiveExpression(terms)
        assertEquals(terms, expr.terms)

        terms = listOf(term1, term2, term1, termZero)
        expr = AdditiveExpression(terms)
        assertEquals(terms, expr.terms)
    }

    @Test
    fun testUnaryPlus() {
        var expr = AdditiveExpression.ZERO
        assertEquals(expr, +expr)

        expr = AdditiveExpression(term1)
        assertEquals(expr, +expr)

        expr = AdditiveExpression(listOf(term5, -term5))
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

        expr = AdditiveExpression(listOf(term5, -term5))
        expected = AdditiveExpression(listOf(-term5, term5))
        assertEquals(expected, -expr)

        expr = AdditiveExpression(listOf(term1, term2, term3, term3))
        expected = AdditiveExpression(listOf(-term1, -term2, -term3, -term3))
        assertEquals(expected, -expr)

        expr = AdditiveExpression(listOf(term1, term2, termZero, term3))
        expected = AdditiveExpression(listOf(-term1, -term2, termZero, -term3))
        assertEquals(expected, -expr)
    }

    @Test
    fun equalsTests() {
        // equal
        var expr1 = AdditiveExpression.ZERO
        assertEquals(expr1, expr1)

        expr1 = AdditiveExpression(term1)
        assertEquals(expr1, expr1)

        expr1 = AdditiveExpression(listOf(termOne, term1, term3))
        assertEquals(expr1, expr1)

        expr1 = AdditiveExpression(termZero)
        var expr2 = AdditiveExpression(listOf(termZero, termZero, termZero))
        assertEquals(expr1, expr2)
        assertEquals(expr2, expr1)

        expr1 = AdditiveExpression(listOf(term1, term2))
        expr2 = AdditiveExpression(listOf(term1, term2, termZero))
        assertEquals(expr1, expr2)
        assertEquals(expr2, expr1)

        expr1 = AdditiveExpression(listOf(term1, term2, term3))
        expr2 = AdditiveExpression(listOf(term3, term1, term2))
        assertEquals(expr1, expr2)
        assertEquals(expr2, expr1)

        // with simplification
        expr1 = AdditiveExpression(listOf(term1, -term1))
        expr2 = AdditiveExpression.ZERO
        assertEquals(expr1, expr2)
        assertEquals(expr2, expr1)

        expr1 = AdditiveExpression(listOf(term1, term2, term3, -term3))
        expr2 = AdditiveExpression(listOf(term2, term1))
        assertEquals(expr1, expr2)
        assertEquals(expr2, expr1)

        val t1 = Term(ExactFraction.TWO, term1.numbers)
        val t2 = Term(ExactFraction(-3, 4), term1.numbers)
        expr1 = AdditiveExpression(listOf(t1, t2))
        expr2 = AdditiveExpression(Term(ExactFraction(5, 4), term1.numbers))
        assertEquals(expr1, expr2)
        assertEquals(expr2, expr1)

        // not equal
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

        expr1 = AdditiveExpression(listOf(term1, termZero))
        expr2 = AdditiveExpression(listOf(term1, term1))
        assertNotEquals(expr1, expr2)
        assertNotEquals(expr2, expr1)

        expr1 = AdditiveExpression(listOf(term2, termZero))
        expr2 = AdditiveExpression(listOf(term1, term2, term3))
        assertNotEquals(expr1, expr2)
        assertNotEquals(expr2, expr1)
    }

    @Test fun testPlus() = runPlusTests()
    @Test fun testMinus() = runMinusTests()

    @Test fun testGetSimplified() = runGetSimplifiedTests()

    // @Test fun testIsZero() {} // TODO
    // @Test fun testGetValue() {} // TODO
    // @Test fun testExtractCommon() {} // TODO
    // @Test fun testToString() {} // TODO
}