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
        assertFalse(pi.isDivided)

        pi = Pi(isDivided = false)
        assertFalse(pi.isDivided)

        pi = Pi(isDivided = true)
        assertTrue(pi.isDivided)
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
        val log1 = Log(ExactFraction(33, 14), 5, true)
        val log2 = Log(ExactFraction(1, 100))
        val sqrt1 = Sqrt(ExactFraction(19, 5))
        val sqrt2 = Sqrt(3)

        // pi
        var expected = Term.fromValues(listOf(pi, pi))
        assertEquals(expected, pi * pi)

        expected = Term.fromValues(listOf(pi, piInverse))
        assertEquals(expected, pi * piInverse)
        assertEquals(expected, piInverse * pi)

        // log
        expected = Term.fromValues(listOf(log1), listOf(pi))
        assertEquals(expected, pi * log1)

        expected = Term.fromValues(listOf(log2), listOf(piInverse))
        assertEquals(expected, piInverse * log2)

        // sqrt
        expected = Term.fromValues(listOf(sqrt1), listOf(pi))
        assertEquals(expected, pi * sqrt1)

        expected = Term.fromValues(listOf(sqrt2), listOf(piInverse))
        assertEquals(expected, piInverse * sqrt2)

        // zero
        expected = Term.ZERO
        assertEquals(expected, pi * Log.ZERO)
    }

    @Test
    fun testDiv() {
        val pi = Pi()
        val piInverse = Pi(true)
        val log1 = Log(ExactFraction(33, 14), 5, true)
        val log2 = Log(ExactFraction(1, 100))
        val sqrt1 = Sqrt(ExactFraction(19, 5))
        val sqrt2 = Sqrt(3)

        // pi
        var expected = Term.fromValues(listOf(pi, piInverse))
        assertEquals(expected, pi / pi)

        expected = Term.fromValues(listOf(pi, pi))
        assertEquals(expected, pi / piInverse)

        expected = Term.fromValues(listOf(piInverse, pi))
        assertEquals(expected, piInverse / piInverse)

        // log
        expected = Term.fromValues(listOf(log1.swapDivided()), listOf(pi))
        assertEquals(expected, pi / log1)

        expected = Term.fromValues(listOf(log2.swapDivided()), listOf(piInverse))
        assertEquals(expected, piInverse / log2)

        // sqrt
        expected = Term.fromValues(listOf(sqrt1.swapDivided()), listOf(pi))
        assertEquals(expected, pi / sqrt1)

        expected = Term.fromValues(listOf(sqrt2.swapDivided()), listOf(piInverse))
        assertEquals(expected, piInverse / sqrt2)

        // zero
        assertDivByZero { pi / Log.ZERO }
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
    fun testSwapDivided() {
        var pi = Pi()
        assertTrue(pi.swapDivided().isDivided)

        pi = Pi(true)
        assertFalse(pi.swapDivided().isDivided)
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
