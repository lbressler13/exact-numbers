package exactnumbers.irrationals.logs

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
        assertEquals(expectedNumber, logNum.number)

        // others
        logNum = LogNum(ExactFraction(15))
        expectedNumber = ExactFraction(15)
        assertEquals(expectedNumber, logNum.number)

        logNum = LogNum(ExactFraction(1078, 14))
        expectedNumber = ExactFraction(1078, 14)
        assertEquals(expectedNumber, logNum.number)

        logNum = LogNum(ExactFraction(13, 100))
        expectedNumber = ExactFraction(13, 100)
        assertEquals(expectedNumber, logNum.number)

        // TODO add isDivided
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

        logNum = LogNum(ExactFraction(107, 12))
        assertEquals(logNum, logNum)

        logNum = LogNum(ExactFraction(12, 107))
        assertEquals(logNum, logNum)

        // not equals
        logNum = LogNum.ZERO
        var other = LogNum(ExactFraction.TWO)
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

        logNum = LogNum(ExactFraction(7, 8))
        other = LogNum(ExactFraction(8, 7))
        assertNotEquals(logNum, other)
        assertNotEquals(other, logNum)
    }

    @Test
    internal fun testTimes() {
        // TODO
//        var logNum1 = LogNum.ZERO
//        var logNum2 = LogNum.ZERO
//        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum1 * logNum2)
//        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum2 * logNum1)
//
//        logNum1 = LogNum.ZERO
//        logNum2 = LogNum.ONE
//        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum1 * logNum2)
//        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum2 * logNum1)
//
//        logNum1 = LogNum.ONE
//        logNum2 = LogNum.ONE
//        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum1 * logNum2)
//        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum2 * logNum1)
//
//        logNum1 = LogNum(ExactFraction(105))
//        logNum2 = LogNum(ExactFraction(20))
//        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum1 * logNum2)
//        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum2 * logNum1)
//
//        logNum1 = LogNum(ExactFraction(105, 7))
//        logNum2 = LogNum(ExactFraction(12))
//        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum1 * logNum2)
//        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum2 * logNum1)
//
//        logNum1 = LogNum(ExactFraction(105, 7))
//        logNum2 = LogNum(ExactFraction(7, 105))
//        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum1 * logNum2)
//        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum2 * logNum1)
    }

    @Test
    internal fun testDiv() {
        // TODO
    }

    @Test
    internal fun testCompareTo() {
        // TODO
    }

    @Test
    internal fun swapDivided() {
        // TODO
    }

    @Test
    internal fun testIsZero() {
        var logNum = LogNum.ZERO
        assertTrue(logNum.isZero())

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
        var expected = "log_10(1)"
        assertEquals(expected, logNum.toString())

        logNum = LogNum(ExactFraction.TEN, 108)
        expected = "log_108(10)"
        assertEquals(expected, logNum.toString())

        logNum = LogNum(ExactFraction(15))
        expected = "log_10(15)"
        assertEquals(expected, logNum.toString())

        logNum = LogNum(ExactFraction(30001), 3)
        expected = "log_3(30001)"
        assertEquals(expected, logNum.toString())

        logNum = LogNum(ExactFraction(15, 8))
        expected = "log_10(15/8)"
        assertEquals(expected, logNum.toString())

        logNum = LogNum(ExactFraction(103, 272), 14)
        expected = "log_14(103/272)"
        assertEquals(expected, logNum.toString())
    }
}
