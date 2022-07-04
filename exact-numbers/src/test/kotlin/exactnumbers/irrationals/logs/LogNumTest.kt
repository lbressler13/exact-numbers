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
            LogNum(ExactFraction.EIGHT, BigInteger.ZERO)
        }

        assertFailsWith<ArithmeticException>("Cannot calculate log of negative number") {
            LogNum(ExactFraction.EIGHT, -BigInteger.TEN)
        }

        assertFailsWith<ArithmeticException>("Cannot calculate log of negative number") {
            LogNum(ExactFraction.ZERO, -BigInteger.TEN)
        }

        // zero
        var logNum = LogNum(ExactFraction.ZERO, BigInteger.TEN)
        var expectedCoeff = ExactFraction.ZERO
        var expectedNumber = BigInteger.ONE
        assertEquals(expectedCoeff, logNum.coefficient)
        assertEquals(expectedNumber, logNum.number)

        logNum = LogNum(ExactFraction(3, 16), BigInteger.ONE)
        expectedCoeff = ExactFraction.ZERO
        expectedNumber = BigInteger.ONE
        assertEquals(expectedCoeff, logNum.coefficient)
        assertEquals(expectedNumber, logNum.number)

        // others
        logNum = LogNum(ExactFraction.ONE, 15.toBigInteger())
        expectedCoeff = ExactFraction.ONE
        expectedNumber = 15.toBigInteger()
        assertEquals(expectedCoeff, logNum.coefficient)
        assertEquals(expectedNumber, logNum.number)

        logNum = LogNum(ExactFraction.EIGHT, 1078.toBigInteger())
        expectedCoeff = ExactFraction.EIGHT
        expectedNumber = 1078.toBigInteger()
        assertEquals(expectedCoeff, logNum.coefficient)
        assertEquals(expectedNumber, logNum.number)

        logNum = LogNum(ExactFraction(-18, 7), 100.toBigInteger())
        expectedCoeff = ExactFraction(-18, 7)
        expectedNumber = 100.toBigInteger()
        assertEquals(expectedCoeff, logNum.coefficient)
        assertEquals(expectedNumber, logNum.number)
    }

    @Test
    internal fun testUnaryMinus() {
        var logNum = LogNum.ZERO
        var expected = LogNum.ZERO
        assertEquals(expected, -logNum)

        logNum = LogNum(ExactFraction.ONE, 9876.toBigInteger())
        expected = LogNum(ExactFraction.NEG_ONE, 9876.toBigInteger())
        assertEquals(expected, -logNum)

        logNum = LogNum(ExactFraction(15), 10.toBigInteger())
        expected = LogNum(ExactFraction(-15), 10.toBigInteger())
        assertEquals(expected, -logNum)

        logNum = LogNum(ExactFraction(-100, 3), 12.toBigInteger())
        expected = LogNum(ExactFraction(100, 3), 12.toBigInteger())
        assertEquals(expected, -logNum)
    }

    @Test
    internal fun testUnaryPlus() {
        var logNum = LogNum.ZERO
        assertEquals(logNum, logNum)

        logNum = LogNum(ExactFraction.ONE, 9876.toBigInteger())
        assertEquals(logNum, logNum)

        logNum = LogNum(ExactFraction(15), 10.toBigInteger())
        assertEquals(logNum, logNum)

        logNum = LogNum(ExactFraction(-100, 3), 12.toBigInteger())
        assertEquals(logNum, logNum)
    }

    @Test
    internal fun testEquals() {
        // equals
        var logNum = LogNum.ZERO
        assertEquals(logNum, logNum)

        logNum = LogNum(ExactFraction.FIVE, BigInteger.TEN)
        assertEquals(logNum, logNum)

        logNum = LogNum(ExactFraction(-17, 2), 30001.toBigInteger())
        assertEquals(logNum, logNum)

        // not equals
        logNum = LogNum.ZERO
        var other = LogNum(ExactFraction.ONE, BigInteger.TWO)
        assertNotEquals(logNum, other)
        assertNotEquals(other, logNum)

        logNum = LogNum(ExactFraction.HALF, BigInteger.TEN)
        other = LogNum(-ExactFraction.HALF, BigInteger.TEN)
        assertNotEquals(logNum, other)
        assertNotEquals(other, logNum)

        logNum = LogNum(ExactFraction(7, 3), BigInteger.TEN)
        other = LogNum(ExactFraction(3, 7), BigInteger.TEN)
        assertNotEquals(logNum, other)
        assertNotEquals(other, logNum)

        logNum = LogNum(ExactFraction(1000), 15.toBigInteger())
        other = LogNum(ExactFraction(15), 1000.toBigInteger())
        assertNotEquals(logNum, other)
        assertNotEquals(other, logNum)
    }

    @Test
    fun testTimes() {
        var logNum1 = LogNum.ZERO
        var logNum2 = LogNum.ZERO
        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum1 * logNum2)
        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum2 * logNum1)

        logNum1 = LogNum.ZERO
        logNum2 = LogNum.ONE
        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum1 * logNum2)
        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum2 * logNum1)

        logNum1 = LogNum(ExactFraction.HALF, BigInteger.TEN)
        logNum2 = LogNum(ExactFraction.HALF, BigInteger.TEN)
        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum1 * logNum2)
        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum2 * logNum1)

        logNum1 = LogNum(ExactFraction(-17, 4), 105.toBigInteger())
        logNum2 = LogNum(ExactFraction(2, 1235), 20.toBigInteger())
        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum1 * logNum2)
        assertEquals(LogProduct(listOf(logNum1, logNum2)), logNum2 * logNum1)
    }

    @Test
    internal fun testIsZero() {
        var logNum = LogNum.ZERO
        assertTrue(logNum.isZero())

        logNum = LogNum(ExactFraction.HALF, BigInteger.TWO)
        assertFalse(logNum.isZero())

        logNum = LogNum(ExactFraction.TEN, BigInteger.TEN)
        assertFalse(logNum.isZero())

        logNum = LogNum(ExactFraction.ONE, 18.toBigInteger())
        assertFalse(logNum.isZero())
    }

    @Test
    internal fun testGetValue() {
        var logNum = LogNum(ExactFraction.ONE, BigInteger.ONE)
        var expected = 0.0.toBigDecimal()
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(ExactFraction.ONE, 100.toBigInteger())
        expected = 2.0.toBigDecimal()
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(ExactFraction.ONE, 200.toBigInteger())
        expected = BigDecimal("2.30102999566398114")
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(ExactFraction.ONE, 3333.toBigInteger())
        expected = BigDecimal("3.52283531366053")
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(ExactFraction(15), 300.toBigInteger())
        expected = BigDecimal("37.15681882079493660")
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(ExactFraction(-12), 77.toBigInteger())
        expected = -BigDecimal("22.6378887020697816")
        assertEquals(expected, logNum.getValue())

        logNum = LogNum(ExactFraction(17, 3), 120.toBigInteger())
        expected = BigDecimal("11.7820270609365400")
        assertEquals(expected, logNum.getValue())
    }

    @Test
    internal fun testToString() {
        // zero
        var logNum = LogNum.ZERO
        var expected = "0*log_10(1)"
        assertEquals(expected, logNum.toString())

        // one
        logNum = LogNum(ExactFraction.ONE, BigInteger.TEN)
        expected = "1*log_10(10)"
        assertEquals(expected, logNum.toString())

        // whole
        logNum = LogNum(ExactFraction.FIVE, BigInteger.TEN)
        expected = "5*log_10(10)"
        assertEquals(expected, logNum.toString())

        logNum = LogNum(ExactFraction(-1000), 15.toBigInteger())
        expected = "-1000*log_10(15)"
        assertEquals(expected, logNum.toString())

        // fraction
        var ef = ExactFraction(-17, 2)
        logNum = LogNum(ef, 30001.toBigInteger())
        expected = "$ef*log_10(30001)"
        assertEquals(expected, logNum.toString())

        ef = ExactFraction.HALF
        logNum = LogNum(ef, BigInteger.TEN)
        expected = "$ef*log_10(10)"
        assertEquals(expected, logNum.toString())
    }
}
