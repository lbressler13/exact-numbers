package xyz.lbres.exactnumbers.expressions.expression

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.expression.simple.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class SimpleExpressionTest {
    @Test
    fun testEquals() {
        // equal
        var term1 = Term.ONE
        assertEquals(SimpleExpression(term1), SimpleExpression(term1))

        term1 = Term.fromValues(ExactFraction(9, 13), emptyList())
        assertEquals(SimpleExpression(term1), SimpleExpression(term1))

        term1 = Term.fromValues(one, listOf(pi, piInverse, pi, testNumber1, log2, log4))
        assertEquals(SimpleExpression(term1), SimpleExpression(term1))

        term1 = Term.fromValues(one, listOf(pi, piInverse, pi))
        var term2 = Term.fromValues(one, listOf(pi))
        assertEquals(SimpleExpression(term1), SimpleExpression(term2))
        assertEquals(SimpleExpression(term2), SimpleExpression(term1))

        term1 = Term.fromValues(ExactFraction(-4, 7), listOf(Log.ZERO, Sqrt.ONE))
        term2 = Term.ZERO
        assertEquals(SimpleExpression(term1), SimpleExpression(term2))
        assertEquals(SimpleExpression(term2), SimpleExpression(term1))

        term1 = Term.fromValues(ExactFraction(-4, 7), listOf(testNumber2, Sqrt(ExactFraction(7, 9)), pi, log1, log1.inverse()))
        term2 = Term.fromValues(ExactFraction(-4, 3), listOf(Sqrt(7), pi))
        assertEquals(SimpleExpression(term1), SimpleExpression(term2))
        assertEquals(SimpleExpression(term2), SimpleExpression(term1))

        term1 = Term.fromValues(ExactFraction(-4, 7), listOf(testNumber2, Sqrt(ExactFraction(7, 9)), pi, log1, log1.inverse()))
        term2 = Term.fromValues(ExactFraction(-4, 3), listOf(Sqrt(7), Sqrt.ONE, piInverse, pi, pi))
        assertEquals(SimpleExpression(term1), SimpleExpression(term2))
        assertEquals(SimpleExpression(term2), SimpleExpression(term1))

        // not equal
        term1 = Term.ONE
        term2 = -Term.ONE
        assertNotEquals(SimpleExpression(term1), SimpleExpression(term2))
        assertNotEquals(SimpleExpression(term2), SimpleExpression(term1))

        term1 = Term.fromValues(ExactFraction.TWO, emptyList())
        term2 = Term.fromValues(ExactFraction.HALF, emptyList())
        assertNotEquals(SimpleExpression(term1), SimpleExpression(term2))
        assertNotEquals(SimpleExpression(term2), SimpleExpression(term1))

        term1 = Term.fromValues(one, listOf(pi))
        term2 = Term.fromValues(one, listOf(piInverse))
        assertNotEquals(SimpleExpression(term1), SimpleExpression(term2))
        assertNotEquals(SimpleExpression(term2), SimpleExpression(term1))

        term1 = Term.fromValues(ExactFraction(-4, 7), listOf(testNumber2, Sqrt(ExactFraction(7, 9)), pi, log1, log1.inverse()))
        term2 = Term.fromValues(ExactFraction(-4, 3), listOf(Sqrt(7), Sqrt.ONE, piInverse, pi))
        assertNotEquals(SimpleExpression(term1), SimpleExpression(term2))
        assertNotEquals(SimpleExpression(term2), SimpleExpression(term1))
    }

    @Test fun testUnaryMinus() = runUnaryMinusTests()
    @Test fun testUnaryPlus() = runUnaryPlusTests()
    @Test fun testInverse() = runInverseTests()

    @Test fun testToByte() = runToByteTests() // TODO
    @Test fun testToChar() = runToCharTests() // TODO
    @Test fun testToShort() = runToShortTests() // TODO
    @Test fun testToInt() = runToIntTests() // TODO
    @Test fun testToLong() = runToLongTests() // TODO
    @Test fun testToFloat() = runToFloatTests() // TODO
    @Test fun testToDouble() = runToDoubleTests() // TODO
}
