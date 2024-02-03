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

    @Test
    fun testToString() {
        var term = Term.ZERO
        var expected = "<0>"
        assertEquals(expected, term.toString())

        term = Term.fromValues(ExactFraction(-25), emptyList())
        expected = "<-25>"
        assertEquals(expected, term.toString())

        term = Term.fromValues(ExactFraction(44, 7), emptyList())
        expected = "<[44/7]>"
        assertEquals(expected, term.toString())

        term = Term.fromValues(one, listOf(log2, log4, log1))
        expected = "<1x${log2}x${log4}x$log1>"
        assertEquals(expected, term.toString())

        term = Term.fromValues(one, listOf(pi, piInverse, pi))
        expected = "<1x${pi}x${piInverse}x$pi>"
        assertEquals(expected, term.toString())

        term = Term.fromValues(one, listOf(Sqrt.ONE))
        expected = "<1x${Sqrt.ONE}>"
        assertEquals(expected, term.toString())

        term = Term.fromValues(ExactFraction.EIGHT, listOf(log3, Sqrt(12), testNumber2, pi))
        expected = "<8x${log3}x${Sqrt(12)}x${testNumber2}x$pi>"
        assertEquals(expected, term.toString())

        val sqrt1 = Sqrt(ExactFraction(1000, 109))
        val sqrt2 = Sqrt(5096)
        term = Term.fromValues(
            ExactFraction(-100, 333),
            listOf(log2, log2, log4, testNumber1, log1, sqrt1, sqrt2, piInverse, pi)
        )
        expected = "<[-100/333]x${log2}x${log2}x${log4}x${testNumber1}x${log1}x${sqrt1}x${sqrt2}x${piInverse}x$pi>"
        assertEquals(expected, term.toString())
    }

    @Test fun testUnaryMinus() = runUnaryMinusTests()
    @Test fun testUnaryPlus() = runUnaryPlusTests()
    @Test fun testInverse() = runInverseTests()

    @Test fun testToByte() = runToByteTests()
    @Test fun testToChar() = runToCharTests()
    @Test fun testToShort() = runToShortTests()
    @Test fun testToInt() = runToIntTests()
    @Test fun testToLong() = runToLongTests()
    @Test fun testToFloat() = runToFloatTests()
    @Test fun testToDouble() = runToDoubleTests()
}
