package exactnumbers.irrationals.logs

import assertDivByZero
import exactnumbers.exactfraction.ExactFraction
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class LogNumTest {
    @Test
    internal fun testConstructor() {
        // error
        assertDivByZero { LogNum(ExactFraction.ONE, 10, isDivided = true) }

        assertFailsWith<ArithmeticException>("Cannot calculate log of 0") {
            LogNum(ExactFraction.ZERO)
        }

        assertFailsWith<ArithmeticException>("Cannot calculate log of negative number") {
            LogNum(-ExactFraction.TEN)
        }

        assertFailsWith<ArithmeticException>("Cannot calculate log of negative number") {
            LogNum(ExactFraction(-4, 3))
        }

        assertFailsWith<ArithmeticException>("Cannot calculate log of negative number") {
            LogNum(-ExactFraction.TEN)
        }

        assertFailsWith<ArithmeticException>("Log base must be greater than 1") {
            LogNum(ExactFraction.TEN, -1)
        }

        assertFailsWith<ArithmeticException>("Log base must be greater than 1") {
            LogNum(ExactFraction.TEN, 0)
        }

        assertFailsWith<ArithmeticException>("Log base must be greater than 1") {
            LogNum(ExactFraction.TEN, 1)
        }

        // zero
        var logNum = LogNum(ExactFraction.ONE)
        var expectedNumber = ExactFraction.ONE
        var expectedBase = 10
        assertEquals(expectedNumber, logNum.number)
        assertEquals(expectedBase, logNum.base)
        assertFalse(logNum.isDivided)


        // all fields
        logNum = LogNum(ExactFraction(13, 100), 100, true)
        expectedNumber = ExactFraction(13, 100)
        expectedBase = 100
        assertEquals(expectedNumber, logNum.number)
        assertEquals(expectedBase, logNum.base)
        assertTrue(logNum.isDivided)

        // just number
        logNum = LogNum(ExactFraction.TWO)
        expectedNumber = ExactFraction.TWO
        expectedBase = 10
        assertEquals(expectedNumber, logNum.number)
        assertEquals(expectedBase, logNum.base)
        assertFalse(logNum.isDivided)

        logNum = LogNum(ExactFraction(107, 3))
        expectedNumber = ExactFraction(107, 3)
        expectedBase = 10
        assertEquals(expectedNumber, logNum.number)
        assertEquals(expectedBase, logNum.base)
        assertFalse(logNum.isDivided)

        // number + base
        logNum = LogNum(ExactFraction.TWO, 2)
        expectedNumber = ExactFraction.TWO
        expectedBase = 2
        assertEquals(expectedNumber, logNum.number)
        assertEquals(expectedBase, logNum.base)
        assertFalse(logNum.isDivided)

        logNum = LogNum(ExactFraction(107, 3), 5)
        expectedNumber = ExactFraction(107, 3)
        expectedBase = 5
        assertEquals(expectedNumber, logNum.number)
        assertEquals(expectedBase, logNum.base)
        assertFalse(logNum.isDivided)

        // number + divided
        logNum = LogNum(ExactFraction.TWO, false)
        expectedNumber = ExactFraction.TWO
        expectedBase = 10
        assertEquals(expectedNumber, logNum.number)
        assertEquals(expectedBase, logNum.base)
        assertFalse(logNum.isDivided)

        logNum = LogNum(ExactFraction.TWO, true)
        expectedNumber = ExactFraction.TWO
        expectedBase = 10
        assertEquals(expectedNumber, logNum.number)
        assertEquals(expectedBase, logNum.base)
        assertTrue(logNum.isDivided)
    }

    @Test
    internal fun testEquals() {
        // equals
        var logNum = LogNum.ZERO
        assertEquals(logNum, logNum)

        logNum = LogNum(ExactFraction.TEN)
        assertEquals(logNum, logNum)

        logNum = LogNum(ExactFraction(30001))
        assertEquals(logNum, logNum)

        logNum = LogNum(ExactFraction(107, 12), 3)
        assertEquals(logNum, logNum)

        logNum = LogNum(ExactFraction(12, 107), 10, true)
        assertEquals(logNum, logNum)

        // not equals
        logNum = LogNum.ZERO
        var other = LogNum(ExactFraction.TWO)
        assertNotEquals(logNum, other)
        assertNotEquals(other, logNum)

        logNum = LogNum(ExactFraction.EIGHT)
        other = LogNum(ExactFraction.EIGHT, 2)
        assertNotEquals(logNum, other)
        assertNotEquals(other, logNum)

        logNum = LogNum(ExactFraction(15))
        other = LogNum(ExactFraction(1000))
        assertNotEquals(logNum, other)
        assertNotEquals(other, logNum)

        logNum = LogNum(ExactFraction.HALF)
        other = LogNum(ExactFraction(5, 7))
        assertNotEquals(logNum, other)
        assertNotEquals(other, logNum)

        logNum = LogNum(ExactFraction(7, 8), 3)
        other = LogNum(ExactFraction(8, 7), 3)
        assertNotEquals(logNum, other)
        assertNotEquals(other, logNum)

        logNum = LogNum(ExactFraction.EIGHT, 10, true)
        other = LogNum(ExactFraction.EIGHT, 10, false)
        assertNotEquals(logNum, other)
        assertNotEquals(other, logNum)
    }

    @Test
    internal fun testCompareTo() {
        // equal
        var logNum1 = LogNum.ZERO
        assertEquals(0, logNum1.compareTo(logNum1))

        logNum1 = LogNum(ExactFraction.EIGHT, 3)
        assertEquals(0, logNum1.compareTo(logNum1))

        logNum1 = LogNum(ExactFraction(4, 5), 3, true)
        assertEquals(0, logNum1.compareTo(logNum1))

        logNum1 = LogNum(ExactFraction.TWO, 2)
        var logNum2 = LogNum(ExactFraction.EIGHT, 8)
        assertEquals(0, logNum1.compareTo(logNum2))
        assertEquals(0, logNum2.compareTo(logNum1))

        // not equal
        logNum1 = LogNum.ZERO
        logNum2 = LogNum.ONE
        assertTrue(logNum1 < logNum2)
        assertTrue(logNum2 > logNum1)

        logNum1 = LogNum(ExactFraction.EIGHT)
        logNum2 = LogNum(ExactFraction.EIGHT, 2)
        assertTrue(logNum1 < logNum2)
        assertTrue(logNum2 > logNum1)

        logNum1 = LogNum(ExactFraction(3, 4), 5)
        logNum2 = LogNum(ExactFraction(4, 3), 5)
        assertTrue(logNum1 < logNum2)
        assertTrue(logNum2 > logNum1)

        logNum1 = LogNum(ExactFraction(3, 4), 10, true)
        logNum2 = LogNum(ExactFraction(1, 4))
        assertTrue(logNum1 < logNum2)
        assertTrue(logNum2 > logNum1)

        logNum1 = LogNum(ExactFraction.TWO, 8)
        logNum2 = LogNum(ExactFraction(32), 8, true)
        assertTrue(logNum1 < logNum2)
        assertTrue(logNum2 > logNum1)
    }

    @Test
    internal fun testSwapDivided() {
        // error
        assertDivByZero { LogNum.ZERO.swapDivided() }

        // other
        var logNum = LogNum.ONE
        var expected = LogNum(ExactFraction.TEN, 10, true)
        assertEquals(expected, logNum.swapDivided())

        logNum = LogNum(ExactFraction.FOUR, 3, false)
        expected = LogNum(ExactFraction.FOUR, 3, true)
        assertEquals(expected, logNum.swapDivided())

        logNum = LogNum(ExactFraction(3, 8), 2, true)
        expected = LogNum(ExactFraction(3, 8), 2, false)
        assertEquals(expected, logNum.swapDivided())
    }

    @Test
    internal fun testIsZero() {
        var logNum = LogNum.ZERO
        assertTrue(logNum.isZero())

        logNum = LogNum.ONE
        assertFalse(logNum.isZero())

        logNum = LogNum(ExactFraction.TWO, 7)
        assertFalse(logNum.isZero())

        logNum = LogNum(ExactFraction.TEN)
        assertFalse(logNum.isZero())

        logNum = LogNum(ExactFraction(18))
        assertFalse(logNum.isZero())

        logNum = LogNum(ExactFraction(18, 7))
        assertFalse(logNum.isZero())

        logNum = LogNum(ExactFraction(7, 18))
        assertFalse(logNum.isZero())
    }

    @Test
    internal fun testGetValue() {
        // base 10
        var logNum = LogNum(ExactFraction.ONE)
        var expected = 0.toBigDecimal()
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(ExactFraction(100))
        expected = 2.toBigDecimal()
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(ExactFraction(3333))
        expected = BigDecimal("3.52283531366053")
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(ExactFraction.HALF)
        expected = BigDecimal("-0.30102999566398114")
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(ExactFraction(21, 2))
        expected = BigDecimal("1.02118929906993786")
        assertEquals(expected, logNum.getValue())

        // base 2
        logNum = LogNum(ExactFraction.ONE, 2)
        expected = 0.toBigDecimal()
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(ExactFraction(1, 8), 2)
        expected = (-3).toBigDecimal()
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(ExactFraction(200), 2)
        expected = BigDecimal("7.643856189774724")
        assertEquals(expected, logNum.getValue())

        // other
        logNum = LogNum(ExactFraction(216), 6)
        expected = 3.toBigDecimal()
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(ExactFraction(15151515), 24)
        expected = BigDecimal("5.202432673429519")
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(ExactFraction(18, 109), 9)
        expected = BigDecimal("-0.8196595572931246")
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(ExactFraction(2000, 3), 3)
        expected = BigDecimal("5.9186395764396105")
        assertEquals(expected, logNum.getValue())

        // divided
        logNum = LogNum(ExactFraction.TEN, 10, true)
        expected = BigDecimal.ONE
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(ExactFraction.EIGHT, 2, true)
        expected = BigDecimal("0.33333333333333333333")
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(ExactFraction(1, 4), 10, true)
        expected = BigDecimal("-1.6609640474436814234")
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(ExactFraction(12), 4, true)
        expected = BigDecimal("0.55788589130225962125")
        assertEquals(expected, logNum.getValue())
    }

    @Test
    internal fun testGetLogOf() {
        // base 10
        var logNum = LogNum(ExactFraction.TEN, 10)

        // var logNum = LogNum(ExactFraction.ONE)
        var bi = BigInteger.ONE
        var expected = BigDecimal.ZERO
        assertEquals(expected, logNum.getLogOf(bi))

        bi = BigInteger.TEN
        expected = BigDecimal.ONE
        assertEquals(expected, logNum.getLogOf(bi))

        bi = 100.toBigInteger()
        expected = 2.toBigDecimal()
        assertEquals(expected, logNum.getLogOf(bi))

        bi = 200.toBigInteger()
        // expected = BigDecimal("2.30102999566398114")
        expected = BigDecimal("2.301029995663981")
        assertEquals(expected, logNum.getLogOf(bi))

        bi = 3333.toBigInteger()
        expected = BigDecimal("3.52283531366053")
        assertEquals(expected, logNum.getLogOf(bi))

        bi = 300.toBigInteger()
        // expected = BigDecimal("2.47712125471966244")
        expected = BigDecimal("2.477121254719662")
        assertEquals(expected, logNum.getLogOf(bi))

        bi = 77.toBigInteger()
        expected = BigDecimal("1.8864907251724818")
        assertEquals(expected, logNum.getLogOf(bi))

        // base 2
        logNum = LogNum(ExactFraction.TWO, 2)

        bi = BigInteger.ONE
        expected = BigDecimal.ZERO
        assertEquals(expected, logNum.getLogOf(bi))

        bi = 32.toBigInteger()
        expected = 5.toBigDecimal()
        assertEquals(expected, logNum.getLogOf(bi))

        bi = 200.toBigInteger()
        expected = BigDecimal("7.643856189774724")
        assertEquals(expected, logNum.getLogOf(bi))

        // other
        logNum = LogNum(ExactFraction.SEVEN, 7)
        bi = BigInteger.ONE
        expected = BigDecimal.ZERO
        assertEquals(expected, logNum.getLogOf(bi))

        logNum = LogNum(ExactFraction.SIX, 6)
        bi = 216.toBigInteger()
        expected = 3.toBigDecimal()
        assertEquals(expected, logNum.getLogOf(bi))

        logNum = LogNum(ExactFraction(24), 24)
        bi = 15151515.toBigInteger()
        expected = BigDecimal("5.202432673429519")
        assertEquals(expected, logNum.getLogOf(bi))
    }

    @Test
    internal fun testToString() {
        var logNum = LogNum.ZERO
        var expected = "[log_10(1)]"
        assertEquals(expected, logNum.toString())

        logNum = LogNum(ExactFraction.TEN, 108)
        expected = "[log_108(10)]"
        assertEquals(expected, logNum.toString())

        logNum = LogNum(ExactFraction(15))
        expected = "[log_10(15)]"
        assertEquals(expected, logNum.toString())

        logNum = LogNum(ExactFraction(30001), 3)
        expected = "[log_3(30001)]"
        assertEquals(expected, logNum.toString())

        logNum = LogNum(ExactFraction(103, 272), 14)
        expected = "[log_14(103/272)]"
        assertEquals(expected, logNum.toString())

        logNum = LogNum(ExactFraction(15, 8), 10, true)
        expected = "[1/log_10(15/8)]"
        assertEquals(expected, logNum.toString())

        logNum = LogNum(ExactFraction.FOUR, 3, true)
        expected = "[1/log_3(4)]"
        assertEquals(expected, logNum.toString())
    }
}
