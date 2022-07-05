package exactnumbers.irrationals.logs

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
            LogNum(BigInteger.ZERO)
        }

        assertFailsWith<ArithmeticException>("Cannot calculate log of negative number") {
            LogNum(-BigInteger.TEN)
        }

        assertFailsWith<ArithmeticException>("Cannot calculate log of negative number") {
            LogNum(-BigInteger.TEN)
        }

        assertFailsWith<ArithmeticException>("Log base must be greater than 1") {
            LogNum(BigInteger.TEN, -1)
        }

        assertFailsWith<ArithmeticException>("Log base must be greater than 1") {
            LogNum(BigInteger.TEN, 0)
        }

        assertFailsWith<ArithmeticException>("Log base must be greater than 1") {
            LogNum(BigInteger.TEN, 1)
        }

        // zero
        var logNum = LogNum(BigInteger.ONE)
        var expectedNumber = BigInteger.ONE
        assertEquals(expectedNumber, logNum.number)

        // others
        logNum = LogNum(15.toBigInteger())
        expectedNumber = 15.toBigInteger()
        assertEquals(expectedNumber, logNum.number)

        logNum = LogNum(1078.toBigInteger())
        expectedNumber = 1078.toBigInteger()
        assertEquals(expectedNumber, logNum.number)

        logNum = LogNum(100.toBigInteger())
        expectedNumber = 100.toBigInteger()
        assertEquals(expectedNumber, logNum.number)
    }

    @Test
    internal fun testEquals() {
        // equals
        var logNum = LogNum.ZERO
        assertEquals(logNum, logNum)

        logNum = LogNum(BigInteger.TEN)
        assertEquals(logNum, logNum)

        logNum = LogNum(30001.toBigInteger())
        assertEquals(logNum, logNum)

        // not equals
        logNum = LogNum.ZERO
        var other = LogNum(BigInteger.TWO)
        assertNotEquals(logNum, other)
        assertNotEquals(other, logNum)

        logNum = LogNum(15.toBigInteger())
        other = LogNum(1000.toBigInteger())
        assertNotEquals(logNum, other)
        assertNotEquals(other, logNum)
    }

    @Test
    internal fun testTimes() {
        var logNum1 = LogNum.ZERO
        var logNum2 = LogNum.ZERO
        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum1 * logNum2)
        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum2 * logNum1)

        logNum1 = LogNum.ZERO
        logNum2 = LogNum.ONE
        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum1 * logNum2)
        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum2 * logNum1)

        logNum1 = LogNum.ONE
        logNum2 = LogNum.ONE
        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum1 * logNum2)
        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum2 * logNum1)

        logNum1 = LogNum(105.toBigInteger())
        logNum2 = LogNum(20.toBigInteger())
        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum1 * logNum2)
        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum2 * logNum1)
    }

    @Test
    internal fun testIsZero() {
        var logNum = LogNum.ZERO
        assertTrue(logNum.isZero())

        logNum = LogNum(BigInteger.TWO)
        assertFalse(logNum.isZero())

        logNum = LogNum(BigInteger.TEN)
        assertFalse(logNum.isZero())

        logNum = LogNum(18.toBigInteger())
        assertFalse(logNum.isZero())
    }

    @Test
    internal fun testGetValue() {
        // base 10
        var logNum = LogNum(BigInteger.ONE)
        var expected = 0.toBigDecimal()
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(100.toBigInteger())
        expected = 2.toBigDecimal()
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(200.toBigInteger())
        // expected = BigDecimal("2.30102999566398114")
        expected = BigDecimal("2.301029995663981")
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(3333.toBigInteger())
        expected = BigDecimal("3.52283531366053")
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(300.toBigInteger())
        // expected = BigDecimal("2.47712125471966244")
        expected = BigDecimal("2.477121254719662")
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(77.toBigInteger())
        expected = BigDecimal("1.8864907251724818")
        assertEquals(expected, logNum.getValue())

        // base 2
        logNum = LogNum(BigInteger.ONE, 2)
        expected = 0.toBigDecimal()
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(32.toBigInteger(), 2)
        expected = 5.toBigDecimal()
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(200.toBigInteger(), 2)
        expected = BigDecimal("7.643856189774724")
        assertEquals(expected, logNum.getValue())

        // other
        logNum = LogNum(BigInteger.ONE, 7)
        expected = 0.toBigDecimal()
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(216.toBigInteger(), 6)
        expected = 3.toBigDecimal()
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(15151515.toBigInteger(), 24)
        expected = BigDecimal("5.202432673429519")
        assertEquals(expected, logNum.getValue())
    }

    @Test
    internal fun testToString() {
        // zero
        var logNum = LogNum.ZERO
        var expected = "log_10(1)"
        assertEquals(expected, logNum.toString())

        // one
        logNum = LogNum(BigInteger.TEN)
        expected = "log_10(10)"
        assertEquals(expected, logNum.toString())

        logNum = LogNum(15.toBigInteger())
        expected = "log_10(15)"
        assertEquals(expected, logNum.toString())

        logNum = LogNum(30001.toBigInteger())
        expected = "log_10(30001)"
        assertEquals(expected, logNum.toString())
    }
}
