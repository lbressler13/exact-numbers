package xyz.lbres.exactnumbers.expressions.expression

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.exactnumbers.testutils.assertDivByZero
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
    fun testUnaryMinus() {
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

    @Test
    fun testUnaryPlus() {
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

    @Test
    fun testInverse() {
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
}
