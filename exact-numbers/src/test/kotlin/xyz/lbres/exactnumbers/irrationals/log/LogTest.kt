package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.testutils.assertDivByZero
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class LogTest {
    @Test
    fun testEquals() {
        // equals
        var log1 = Log.ZERO
        assertEquals(log1, log1)

        log1 = Log(10)
        assertEquals(log1, log1)

        log1 = Log(30001)
        assertEquals(log1, log1)

        log1 = Log(ExactFraction(107, 12), 3)
        assertEquals(log1, log1)

        log1 = Log(ExactFraction(12, 107), 10).inverse()
        assertEquals(log1, log1)

        // not equals
        log1 = Log.ZERO
        var log2 = Log(2)
        assertNotEquals(log1, log2)
        assertNotEquals(log2, log1)

        log1 = Log(8)
        log2 = Log(8, 2)
        assertNotEquals(log1, log2)
        assertNotEquals(log2, log1)

        log1 = Log(15)
        log2 = Log(1000)
        assertNotEquals(log1, log2)
        assertNotEquals(log2, log1)

        log1 = Log(ExactFraction.HALF)
        log2 = Log(ExactFraction(5, 7))
        assertNotEquals(log1, log2)
        assertNotEquals(log2, log1)

        log1 = Log(ExactFraction(7, 8), 3)
        log2 = Log(ExactFraction(8, 7), 3)
        assertNotEquals(log1, log2)
        assertNotEquals(log2, log1)

        log1 = Log(8, 10).inverse()
        log2 = Log(8, 10)
        assertNotEquals(log1, log2)
        assertNotEquals(log2, log1)
    }

    @Test
    fun testCompareTo() {
        // equal
        var log1 = Log.ZERO
        assertEquals(0, log1.compareTo(log1))

        log1 = Log(8, 3)
        assertEquals(0, log1.compareTo(log1))

        log1 = Log(ExactFraction(4, 5), 3).inverse()
        assertEquals(0, log1.compareTo(log1))

        log1 = Log(2, 2)
        var log2 = Log(8, 8)
        assertEquals(0, log1.compareTo(log2))
        assertEquals(0, log2.compareTo(log1))

        // not equal
        log1 = Log.ZERO
        log2 = Log.ONE
        assertTrue(log1 < log2)
        assertTrue(log2 > log1)

        log1 = Log(8)
        log2 = Log(8, 2)
        assertTrue(log1 < log2)
        assertTrue(log2 > log1)

        log1 = Log(ExactFraction(3, 4), 5)
        log2 = Log(ExactFraction(4, 3), 5)
        assertTrue(log1 < log2)
        assertTrue(log2 > log1)

        log1 = Log(ExactFraction(3, 4), 10).inverse()
        log2 = Log(ExactFraction(1, 4))
        assertTrue(log1 < log2)
        assertTrue(log2 > log1)

        log1 = Log(2, 8)
        log2 = Log(32, 8).inverse()
        assertTrue(log1 < log2)
        assertTrue(log2 > log1)
    }

    @Test fun testTimes() = runTimesTests()
    @Test fun testDiv() = runDivTests()

    @Test
    fun testInverse() {
        // error
        assertDivByZero { Log.ZERO.inverse() }

        // other
        var log = Log.ONE
        var expected = Log.ONE
        assertEquals(expected, log.inverse())

        log = Log(ExactFraction(3, 8), 2)
        expected = Log(ExactFraction(3, 8), 2).inverse()
        assertEquals(expected, log.inverse())

        log = Log(4, 3).inverse()
        expected = Log(4, 3)
        assertEquals(expected, log.inverse())
    }

    @Test
    fun testIsZero() {
        var log = Log.ZERO
        assertTrue(log.isZero())

        log = Log.ONE
        assertFalse(log.isZero())

        log = Log(2, 7)
        assertFalse(log.isZero())

        log = Log(10)
        assertFalse(log.isZero())

        log = Log(18)
        assertFalse(log.isZero())

        log = Log(ExactFraction(18, 7))
        assertFalse(log.isZero())

        log = Log(ExactFraction(7, 18))
        assertFalse(log.isZero())
    }

    @Test
    fun testIsRational() {
        // rational
        var log = Log.ZERO
        assertTrue(log.isRational())

        log = Log.ONE
        assertTrue(log.isRational())

        log = Log(2048, 2)
        assertTrue(log.isRational())
        assertTrue(log.isRational())

        log = Log(2048, 2).inverse()
        assertTrue(log.isRational())
        assertTrue(log.isRational())

        log = Log(ExactFraction(1, 27), 3)
        assertTrue(log.isRational())
        assertTrue(log.isRational())

        log = Log(ExactFraction(1, 1000)).inverse()
        assertTrue(log.isRational())

        // irrational
        log = Log(20)
        assertFalse(log.isRational())

        log = Log(ExactFraction(1, 10), 5)
        assertFalse(log.isRational())
        assertFalse(log.isRational())

        log = Log(1000, 100).inverse()
        assertFalse(log.isRational())
        assertFalse(log.isRational())

        log = Log(ExactFraction(8, 1000), 2)
        assertFalse(log.isRational())
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
        var log = Log.ZERO
        var expected = "[log_10(1)]"
        assertEquals(expected, log.toString())

        log = Log(10, 108)
        expected = "[log_108(10)]"
        assertEquals(expected, log.toString())

        log = Log(15)
        expected = "[log_10(15)]"
        assertEquals(expected, log.toString())

        log = Log(30001, 3)
        expected = "[log_3(30001)]"
        assertEquals(expected, log.toString())

        log = Log(ExactFraction(103, 272), 14)
        expected = "[log_14(103/272)]"
        assertEquals(expected, log.toString())

        log = Log(ExactFraction(15, 8), 10).inverse()
        expected = "[1/log_10(15/8)]"
        assertEquals(expected, log.toString())

        log = Log(4, 3).inverse()
        expected = "[1/log_3(4)]"
        assertEquals(expected, log.toString())
    }
}
