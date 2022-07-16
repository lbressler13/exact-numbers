package exactnumbers.irrationals.sqrt

import assertDivByZero
import exactnumbers.exactfraction.ExactFraction
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import kotlin.math.exp
import kotlin.math.sqrt
import kotlin.test.*

internal class SqrtTest {
    @Test
    internal fun testConstructor() {
        // errors
        assertFailsWith<ArithmeticException>("Cannot calculate root of a negative number") { Sqrt(-ExactFraction.EIGHT) }

        assertFailsWith<ArithmeticException>("Cannot calculate root of a negative number") { Sqrt(-ExactFraction.HALF) }

        assertDivByZero { Sqrt(ExactFraction.ZERO, true) }

        // no error
        var sqrt = Sqrt(ExactFraction.ZERO)
        var expectedRadicand = ExactFraction.ZERO
        assertEquals(expectedRadicand, sqrt.radicand)
        assertFalse(sqrt.isDivided)

        sqrt = Sqrt(ExactFraction.EIGHT)
        expectedRadicand = ExactFraction.EIGHT
        assertEquals(expectedRadicand, sqrt.radicand)
        assertFalse(sqrt.isDivided)

        sqrt = Sqrt(ExactFraction(1000, 99))
        expectedRadicand = ExactFraction(1000, 99)
        assertEquals(expectedRadicand, sqrt.radicand)
        assertFalse(sqrt.isDivided)

        sqrt = Sqrt(ExactFraction(103, 782))
        expectedRadicand = ExactFraction(103, 782)
        assertEquals(expectedRadicand, sqrt.radicand)
        assertFalse(sqrt.isDivided)

        sqrt = Sqrt(ExactFraction.EIGHT, true)
        expectedRadicand = ExactFraction.EIGHT
        assertEquals(expectedRadicand, sqrt.radicand)
        assertTrue(sqrt.isDivided)

        sqrt = Sqrt(ExactFraction(103, 782), true)
        expectedRadicand = ExactFraction(103, 782)
        assertEquals(expectedRadicand, sqrt.radicand)
        assertTrue(sqrt.isDivided)
    }

    @Test
    internal fun testIsZero() {
        // zero
        var sqrt = Sqrt.ZERO
        assertTrue(sqrt.isZero())

        // not zero
        sqrt = Sqrt.ONE
        assertFalse(sqrt.isZero())

        sqrt = Sqrt(ExactFraction(15, 1234))
        assertFalse(sqrt.isZero())

        sqrt = Sqrt(ExactFraction.TWO)
        assertFalse(sqrt.isZero())

        sqrt = Sqrt(ExactFraction.HALF, true)
        assertFalse(sqrt.isZero())
    }

    @Test
    internal fun testSwapDivided() {
        // error
        assertDivByZero { Sqrt.ZERO.swapDivided() }

        // no error
        var sqrt = Sqrt(ExactFraction.EIGHT)
        var expected = Sqrt(ExactFraction.EIGHT, true)
        assertEquals(expected, sqrt.swapDivided())

        sqrt = Sqrt(ExactFraction.HALF)
        expected = Sqrt(ExactFraction.HALF, true)
        assertEquals(expected, sqrt.swapDivided())

        sqrt = Sqrt(ExactFraction(100, 49))
        expected = Sqrt(ExactFraction(100, 49), true)
        assertEquals(expected, sqrt.swapDivided())

        sqrt = Sqrt(ExactFraction.EIGHT, true)
        expected = Sqrt(ExactFraction.EIGHT)
        assertEquals(expected, sqrt.swapDivided())

        sqrt = Sqrt(ExactFraction.HALF, true)
        expected = Sqrt(ExactFraction.HALF)
        assertEquals(expected, sqrt.swapDivided())

        sqrt = Sqrt(ExactFraction(100, 49), true)
        expected = Sqrt(ExactFraction(100, 49))
        assertEquals(expected, sqrt.swapDivided())
    }

    @Test
    internal fun testIsRational() {
        // rational
        var sqrt = Sqrt.ZERO
        assertTrue(sqrt.isRational())

        sqrt = Sqrt.ONE
        assertTrue(sqrt.isRational())

        sqrt = Sqrt(ExactFraction(961))
        assertTrue(sqrt.isRational())

        sqrt = Sqrt(ExactFraction(1, 64))
        assertTrue(sqrt.isRational())

        sqrt = Sqrt(ExactFraction(9, 25), true)
        assertTrue(sqrt.isRational())

        sqrt = Sqrt(ExactFraction(81, 49))
        assertTrue(sqrt.isRational())

        // irrational
        sqrt = Sqrt(ExactFraction.TWO)
        assertFalse(sqrt.isRational())

        sqrt = Sqrt(ExactFraction.EIGHT, true)
        assertFalse(sqrt.isRational())

        sqrt = Sqrt(ExactFraction(1, 35))
        assertFalse(sqrt.isRational())

        sqrt = Sqrt(ExactFraction(49, 10))
        assertFalse(sqrt.isRational())
    }

    @Test
    internal fun testGetRationalValue() {
        // irrational
        var sqrt = Sqrt(ExactFraction.TWO)
        assertNull(sqrt.getRationalValue())

        sqrt = Sqrt(ExactFraction(64, 15))
        assertNull(sqrt.getRationalValue())

        sqrt = Sqrt(ExactFraction(155), true)
        assertNull(sqrt.getRationalValue())

        // rational
        sqrt = Sqrt.ZERO
        var expected = ExactFraction.ZERO
        assertEquals(expected, sqrt.getRationalValue())

        sqrt = Sqrt.ONE
        expected = ExactFraction.ONE
        assertEquals(expected, sqrt.getRationalValue())

        sqrt = Sqrt(ExactFraction(2209))
        expected = ExactFraction(47)
        assertEquals(expected, sqrt.getRationalValue())

        sqrt = Sqrt(ExactFraction.FOUR, true)
        expected = ExactFraction.HALF
        assertEquals(expected, sqrt.getRationalValue())

        sqrt = Sqrt(ExactFraction(1, 100))
        expected = ExactFraction(1, 10)
        assertEquals(expected, sqrt.getRationalValue())

        sqrt = Sqrt(ExactFraction(81, 64))
        expected = ExactFraction(9, 8)
        assertEquals(expected, sqrt.getRationalValue())

        sqrt = Sqrt(ExactFraction(81, 64), true)
        expected = ExactFraction(8, 9)
        assertEquals(expected, sqrt.getRationalValue())
    }

    @Test
    internal fun getValue() {
        var sqrt = Sqrt.ZERO
        var expected = BigDecimal.ZERO
        assertEquals(expected, sqrt.getValue())

        sqrt = Sqrt.ONE
        expected = BigDecimal.ONE
        assertEquals(expected, sqrt.getValue())

        sqrt = Sqrt(ExactFraction(144))
        expected = BigDecimal("12")
        println(sqrt(144.0))
        assertEquals(expected, sqrt.getValue())

        sqrt = Sqrt(ExactFraction(25), true)
        expected = BigDecimal("0.2")
        assertEquals(expected, sqrt.getValue())

        sqrt = Sqrt(ExactFraction(36), true)
        expected = BigDecimal("0.16666666666666666667")
        assertEquals(expected, sqrt.getValue())

        sqrt = Sqrt(ExactFraction(25, 16))
        expected = BigDecimal("1.25")
        assertEquals(expected, sqrt.getValue())

        sqrt = Sqrt(ExactFraction.SEVEN)
        expected = BigDecimal("2.6457513110645905905")
        assertEquals(expected, sqrt.getValue())

        sqrt = Sqrt(ExactFraction.SEVEN, true)
        expected = BigDecimal("0.37796447300922722721")
        assertEquals(expected, sqrt.getValue())

        sqrt = Sqrt(ExactFraction(32))
        expected = BigDecimal("5.6568542494923801952")
        assertEquals(expected, sqrt.getValue())

        sqrt = Sqrt(ExactFraction(17, 49))
        expected = BigDecimal("0.58901508937395150711")
        assertEquals(expected, sqrt.getValue())
    }

    @Test
    internal fun testEquals() {
        // equal
        var sqrt1 = Sqrt.ZERO
        assertEquals(sqrt1, sqrt1)

        sqrt1 = Sqrt(ExactFraction.SIX)
        assertEquals(sqrt1, sqrt1)

        sqrt1 = Sqrt(ExactFraction(9, 400))
        assertEquals(sqrt1, sqrt1)

        sqrt1 = Sqrt(ExactFraction(9, 400), true)
        assertEquals(sqrt1, sqrt1)

        // not equal
        sqrt1 = Sqrt.ZERO
        var sqrt2 = Sqrt.ONE
        assertNotEquals(sqrt1, sqrt2)

        sqrt1 = Sqrt(ExactFraction.TWO)
        sqrt2 = Sqrt(ExactFraction.HALF)
        assertNotEquals(sqrt1, sqrt2)

        sqrt1 = Sqrt(ExactFraction.TWO, true)
        sqrt2 = Sqrt(ExactFraction.HALF)
        assertNotEquals(sqrt1, sqrt2)

        sqrt1 = Sqrt(ExactFraction.TWO, true)
        sqrt2 = Sqrt(ExactFraction.TWO)
        assertNotEquals(sqrt1, sqrt2)

        sqrt1 = Sqrt(ExactFraction(9, 25))
        sqrt2 = Sqrt(ExactFraction.NINE)
        assertNotEquals(sqrt1, sqrt2)

        sqrt1 = Sqrt(ExactFraction(103, 422))
        sqrt2 = Sqrt(ExactFraction(90, 37), true)
        assertNotEquals(sqrt1, sqrt2)
    }

    @Test
    internal fun testGetSimplified() {
        val one = ExactFraction.ONE

        // rational
        var sqrt = Sqrt.ZERO
        var expected = Pair(one, Sqrt.ZERO)
        assertEquals(expected, sqrt.getSimplified())

        sqrt = Sqrt.ONE
        expected = Pair(one, Sqrt.ONE)
        assertEquals(expected, sqrt.getSimplified())

        sqrt = Sqrt(ExactFraction(1024))
        expected = Pair(ExactFraction(32), Sqrt.ONE)
        assertEquals(expected, sqrt.getSimplified())

        sqrt = Sqrt(ExactFraction(1024), true)
        expected = Pair(ExactFraction(1, 32), Sqrt(one, true))
        assertEquals(expected, sqrt.getSimplified())

        sqrt = Sqrt(ExactFraction(9, 25))
        expected = Pair(ExactFraction(3, 5), Sqrt.ONE)
        assertEquals(expected, sqrt.getSimplified())

        // rational w/ whole
        sqrt = Sqrt(ExactFraction(50))
        expected = Pair(ExactFraction.FIVE, Sqrt(ExactFraction.TWO))
        assertEquals(expected, sqrt.getSimplified())

        sqrt = Sqrt(ExactFraction(50), true)
        expected = Pair(ExactFraction(1, 5), Sqrt(ExactFraction.TWO, true))
        assertEquals(expected, sqrt.getSimplified())

        sqrt = Sqrt(ExactFraction(3000))
        expected = Pair(ExactFraction.TEN, Sqrt(ExactFraction(30)))
        assertEquals(expected, sqrt.getSimplified())

        sqrt = Sqrt(ExactFraction(2, 9), true)
        expected = Pair(ExactFraction.THREE, Sqrt(ExactFraction.TWO, true))
        assertEquals(expected, sqrt.getSimplified())

        sqrt = Sqrt(ExactFraction(50, 27))
        expected = Pair(ExactFraction(5, 3), Sqrt(ExactFraction(2, 3)))
        assertEquals(expected, sqrt.getSimplified())

        // no whole
        sqrt = Sqrt(ExactFraction(15))
        expected = Pair(one, sqrt)
        assertEquals(expected, sqrt.getSimplified())

        sqrt = Sqrt(ExactFraction(107))
        expected = Pair(one, sqrt)
        assertEquals(expected, sqrt.getSimplified())

        sqrt = Sqrt(ExactFraction(94, 46))
        expected = Pair(one, sqrt)
        assertEquals(expected, sqrt.getSimplified())
    }

    @Test
    internal fun testToString() {
        val symbol = "√"

        // whole number
        var sqrt = Sqrt(ExactFraction.ZERO)
        var expected = "[${symbol}(0)]"
        assertEquals(expected, sqrt.toString())

        sqrt = Sqrt(ExactFraction.TEN)
        expected = "[${symbol}(10)]"
        assertEquals(expected, sqrt.toString())

        sqrt = Sqrt(ExactFraction(1234567))
        expected = "[${symbol}(1234567)]"
        assertEquals(expected, sqrt.toString())

        sqrt = Sqrt(ExactFraction(1234567), true)
        expected = "[1/${symbol}(1234567)]"
        assertEquals(expected, sqrt.toString())

        // fraction
        sqrt = Sqrt(ExactFraction.HALF)
        expected = "[${symbol}(1/2)]"
        assertEquals(expected, sqrt.toString())

        sqrt = Sqrt(ExactFraction(12, 35))
        expected = "[${symbol}(12/35)]"
        assertEquals(expected, sqrt.toString())

        sqrt = Sqrt(ExactFraction(12, 35), true)
        expected = "[1/${symbol}(12/35)]"
        assertEquals(expected, sqrt.toString())
    }
}