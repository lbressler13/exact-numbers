package xyz.lbres.exactnumbers.irrationals.log

import assertDivByZero
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class LogTest {
    @Test fun testConstructor() = runConstructorTests()

    @Test
    fun testEquals() {
        // equals
        var logNum = Log.ZERO
        assertEquals(logNum, logNum)

        logNum = Log(10)
        assertEquals(logNum, logNum)

        logNum = Log(30001)
        assertEquals(logNum, logNum)

        logNum = Log(ExactFraction(107, 12), 3)
        assertEquals(logNum, logNum)

        // not equals
        logNum = Log.ZERO
        var other = Log(2)
        assertNotEquals(logNum, other)
        assertNotEquals(other, logNum)

        logNum = Log(8)
        other = Log(8, 2)
        assertNotEquals(logNum, other)
        assertNotEquals(other, logNum)

        logNum = Log(15)
        other = Log(1000)
        assertNotEquals(logNum, other)
        assertNotEquals(other, logNum)

        logNum = Log(ExactFraction.HALF)
        other = Log(ExactFraction(5, 7))
        assertNotEquals(logNum, other)
        assertNotEquals(other, logNum)

        logNum = Log(ExactFraction(7, 8), 3)
        other = Log(ExactFraction(8, 7), 3)
        assertNotEquals(logNum, other)
        assertNotEquals(other, logNum)

        logNum = Log(8, 10)
        other = Log(8, 10).inverse()
        assertNotEquals(logNum, other)
        assertNotEquals(other, logNum)
    }

    @Test
    fun testCompareTo() {
        // equal
        var logNum1 = Log.ZERO
        assertEquals(0, logNum1.compareTo(logNum1))

        logNum1 = Log(8, 3)
        assertEquals(0, logNum1.compareTo(logNum1))

        logNum1 = Log(ExactFraction(4, 5), 3).inverse()
        assertEquals(0, logNum1.compareTo(logNum1))

        logNum1 = Log(2, 2)
        var logNum2 = Log(8, 8)
        assertEquals(0, logNum1.compareTo(logNum2))
        assertEquals(0, logNum2.compareTo(logNum1))

        // not equal
        logNum1 = Log.ZERO
        logNum2 = Log.ONE
        assertTrue(logNum1 < logNum2)
        assertTrue(logNum2 > logNum1)

        logNum1 = Log(8)
        logNum2 = Log(8, 2)
        assertTrue(logNum1 < logNum2)
        assertTrue(logNum2 > logNum1)

        logNum1 = Log(ExactFraction(3, 4), 5)
        logNum2 = Log(ExactFraction(4, 3), 5)
        assertTrue(logNum1 < logNum2)
        assertTrue(logNum2 > logNum1)

        logNum1 = Log(ExactFraction(3, 4), 10).inverse()
        logNum2 = Log(ExactFraction(1, 4))
        assertTrue(logNum1 < logNum2)
        assertTrue(logNum2 > logNum1)

        logNum1 = Log(2, 8)
        logNum2 = Log(32, 8).inverse()
        assertTrue(logNum1 < logNum2)
        assertTrue(logNum2 > logNum1)
    }

    @Test fun testTimes() = runTimesTests()
    @Test fun testDiv() = runDivTests()

    @Test
    fun testInverse() {
        // error
        assertDivByZero { Log.ZERO.inverse() }

        // other
        var logNum = Log.ONE
        assertTrue(logNum.inverse().isInverted)

        logNum = Log(ExactFraction(3, 8), 2)
        assertTrue(logNum.inverse().isInverted)

        logNum = Log(4, 3).inverse()
        assertFalse(logNum.inverse().isInverted)
    }

    @Test
    fun testIsZero() {
        var logNum = Log.ZERO
        assertTrue(logNum.isZero())

        logNum = Log.ONE
        assertFalse(logNum.isZero())

        logNum = Log(2, 7)
        assertFalse(logNum.isZero())

        logNum = Log(10)
        assertFalse(logNum.isZero())

        logNum = Log(18)
        assertFalse(logNum.isZero())

        logNum = Log(ExactFraction(18, 7))
        assertFalse(logNum.isZero())

        logNum = Log(ExactFraction(7, 18))
        assertFalse(logNum.isZero())
    }

    @Test
    fun testIsRational() {
        // rational
        var logNum = Log.ZERO
        assertTrue(logNum.isRational())

        logNum = Log.ONE
        assertTrue(logNum.isRational())

        logNum = Log(2048, 2)
        assertTrue(logNum.isRational())

        logNum = Log(2048, 2).inverse()
        assertTrue(logNum.isRational())

        logNum = Log(ExactFraction(1, 27), 3)
        assertTrue(logNum.isRational())

        logNum = Log(ExactFraction(1, 1000)).inverse()
        assertTrue(logNum.isRational())

        // irrational
        logNum = Log(20)
        assertFalse(logNum.isRational())

        logNum = Log(ExactFraction(1, 10), 5)
        assertFalse(logNum.isRational())

        logNum = Log(1000, 100).inverse()
        assertFalse(logNum.isRational())

        logNum = Log(ExactFraction(8, 1000), 2)
        assertFalse(logNum.isRational())
    }

    @Test fun testGetRationalValue() = runGetRationalValueTests()
    @Test fun testGetValue() = runGetValueTests()

    @Test fun testGetSimplified() = runGetSimplifiedTests()
    @Test fun testSimplifySet() = runSimplifySetTests()

    @Test fun testToByte() = runToByteTests()
    @Test fun testToChar() = runToCharTests()
    @Test fun testToShort() = runToShortTests()
    @Test fun testToInt() = runToIntTests()
    @Test fun testToLong() = runToLongTests()
    @Test fun testToFloat() = runToFloatTests()
    @Test fun testToDouble() = runToDoubleTests()

    @Test
    fun testToString() {
        var logNum = Log.ZERO
        var expected = "[log_10(1)]"
        assertEquals(expected, logNum.toString())

        logNum = Log(10, 108)
        expected = "[log_108(10)]"
        assertEquals(expected, logNum.toString())

        logNum = Log(15)
        expected = "[log_10(15)]"
        assertEquals(expected, logNum.toString())

        logNum = Log(30001, 3)
        expected = "[log_3(30001)]"
        assertEquals(expected, logNum.toString())

        logNum = Log(ExactFraction(103, 272), 14)
        expected = "[log_14(103/272)]"
        assertEquals(expected, logNum.toString())

        logNum = Log(ExactFraction(15, 8), 10).inverse()
        expected = "[1/log_10(15/8)]"
        assertEquals(expected, logNum.toString())

        logNum = Log(4, 3).inverse()
        expected = "[1/log_3(4)]"
        assertEquals(expected, logNum.toString())
    }
}
