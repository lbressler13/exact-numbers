package xyz.lbres.exactnumbers.irrationals.pi

import assertDivByZero
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.expressions.term.Term
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class PiTest {
    @Test
    fun testConstructor() {
        var pi = Pi()
        assertFalse(pi.inverted)

        pi = Pi(inverted = false)
        assertFalse(pi.inverted)

        pi = Pi(inverted = true)
        assertTrue(pi.inverted)
    }

    @Test
    fun testEquals() {
        var pi1 = Pi()
        assertEquals(pi1, pi1)

        pi1 = Pi(true)
        assertEquals(pi1, pi1)

        val pi2 = Pi()
        assertNotEquals(pi1, pi2)
        assertNotEquals(pi2, pi1)
    }

    @Test
    fun testTimes() {
        val pi = Pi()
        val piInverse = Pi(true)

        // zero
        assertEquals(Term.ZERO, pi * Log.ZERO)
        assertEquals(Term.ZERO, pi * Sqrt.ZERO)
        assertEquals(Term.ZERO, pi * ExactFraction.ZERO)

        // pi only
        var expected = Term.fromValues(listOf(pi, pi))
        assertEquals(expected, pi * pi)

        expected = Term.fromValues(listOf(pi, piInverse))
        assertEquals(expected, pi * piInverse)
        assertEquals(expected, piInverse * pi)

        // exact fraction
        var ef = ExactFraction.HALF
        expected = Term.fromValues(ef, listOf(pi))
        assertEquals(expected, pi * ef)

        ef = ExactFraction(1000)
        expected = Term.fromValues(ef, listOf(piInverse))
        assertEquals(expected, piInverse * ef)

        // log
        var log = Log(ExactFraction(33, 14), 5, true)
        expected = Term.fromValues(listOf(log), listOf(pi))
        assertEquals(expected, pi * log)

        log = Log(ExactFraction(1, 100))
        expected = Term.fromValues(listOf(log), listOf(piInverse))
        assertEquals(expected, piInverse * log)

        // sqrt
        var sqrt = Sqrt(ExactFraction(19, 5))
        expected = Term.fromValues(listOf(sqrt), listOf(pi))
        assertEquals(expected, pi * sqrt)

        sqrt = Sqrt(3)
        expected = Term.fromValues(listOf(sqrt), listOf(piInverse))
        assertEquals(expected, piInverse * sqrt)
    }

    @Test
    fun testDiv() {
        val pi = Pi()
        val piInverse = Pi(true)

        // zero
        assertDivByZero { pi / Log.ZERO }
        assertDivByZero { pi / Sqrt.ZERO }
        assertDivByZero { pi / ExactFraction.ZERO }

        // pi only
        var expected = Term.fromValues(listOf(pi, piInverse))
        assertEquals(expected, pi / pi)

        expected = Term.fromValues(listOf(pi, pi))
        assertEquals(expected, pi / piInverse)

        expected = Term.fromValues(listOf(piInverse, pi))
        assertEquals(expected, piInverse / piInverse)

        // exact fraction
        var ef = ExactFraction.HALF
        expected = Term.fromValues(ef.inverse(), listOf(pi))
        assertEquals(expected, pi / ef)

        ef = ExactFraction(1000)
        expected = Term.fromValues(ef.inverse(), listOf(piInverse))
        assertEquals(expected, piInverse / ef)

        // log
        var log = Log(ExactFraction(33, 14), 5, true)
        expected = Term.fromValues(listOf(log.inverse()), listOf(pi))
        assertEquals(expected, pi / log)

        log = Log(ExactFraction(1, 100))
        expected = Term.fromValues(listOf(log.inverse()), listOf(piInverse))
        assertEquals(expected, piInverse / log)

        // sqrt
        var sqrt = Sqrt(ExactFraction(19, 5))
        expected = Term.fromValues(listOf(sqrt.inverse()), listOf(pi))
        assertEquals(expected, pi / sqrt)

        sqrt = Sqrt(3)
        expected = Term.fromValues(listOf(sqrt.inverse()), listOf(piInverse))
        assertEquals(expected, piInverse / sqrt)
    }

    @Test
    fun testGetValue() {
        // kotlin representation of pi: 3.141592653589793
        var pi = Pi()
        var expected = BigDecimal("3.141592653589793")
        assertEquals(expected, pi.getValue())

        pi = Pi(true)
        expected = BigDecimal("0.31830988618379069570")
        assertEquals(expected, pi.getValue())
    }

    @Test
    fun testIsZero() {
        var pi = Pi()
        assertFalse(pi.isZero())

        pi = Pi(true)
        assertFalse(pi.isZero())
    }

    @Test
    fun testIsRational() {
        var pi = Pi()
        assertFalse(pi.isRational())

        pi = Pi(true)
        assertFalse(pi.isRational())
    }

    @Test
    fun testGetRationalValue() {
        var pi = Pi()
        assertNull(pi.getRationalValue())

        pi = Pi(true)
        assertNull(pi.getRationalValue())
    }

    @Test
    fun testInverse() {
        var pi = Pi()
        assertTrue(pi.inverse().inverted)

        pi = Pi(true)
        assertFalse(pi.inverse().inverted)
    }

    @Test
    fun testToString() {
        var piNum = Pi()
        var expected = "[π]"
        assertEquals(expected, piNum.toString())

        piNum = Pi(true)
        expected = "[1/π]"
        assertEquals(expected, piNum.toString())
    }

    @Test fun testSimplifyList() = runSimplifyListTests()
}
