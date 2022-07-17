package exactnumbers.irrationals.log

import assertDivByZero
import exactnumbers.exactfraction.ExactFraction
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class LogTest {
    @Test internal fun testConstructor() = runConstructorTests() // TODO

    @Test
    internal fun testEquals() {
        // equals
        var logNum = Log.ZERO
        assertEquals(logNum, logNum)

        logNum = Log(ExactFraction.TEN)
        assertEquals(logNum, logNum)

        logNum = Log(ExactFraction(30001))
        assertEquals(logNum, logNum)

        logNum = Log(ExactFraction(107, 12), 3)
        assertEquals(logNum, logNum)

        logNum = Log(ExactFraction(12, 107), 10, true)
        assertEquals(logNum, logNum)

        // not equals
        logNum = Log.ZERO
        var other = Log(ExactFraction.TWO)
        assertNotEquals(logNum, other)
        assertNotEquals(other, logNum)

        logNum = Log(ExactFraction.EIGHT)
        other = Log(ExactFraction.EIGHT, 2)
        assertNotEquals(logNum, other)
        assertNotEquals(other, logNum)

        logNum = Log(ExactFraction(15))
        other = Log(ExactFraction(1000))
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

        logNum = Log(ExactFraction.EIGHT, 10, true)
        other = Log(ExactFraction.EIGHT, 10, false)
        assertNotEquals(logNum, other)
        assertNotEquals(other, logNum)
    }

    @Test
    internal fun testCompareTo() {
        // equal
        var logNum1 = Log.ZERO
        assertEquals(0, logNum1.compareTo(logNum1))

        logNum1 = Log(ExactFraction.EIGHT, 3)
        assertEquals(0, logNum1.compareTo(logNum1))

        logNum1 = Log(ExactFraction(4, 5), 3, true)
        assertEquals(0, logNum1.compareTo(logNum1))

        logNum1 = Log(ExactFraction.TWO, 2)
        var logNum2 = Log(ExactFraction.EIGHT, 8)
        assertEquals(0, logNum1.compareTo(logNum2))
        assertEquals(0, logNum2.compareTo(logNum1))

        // not equal
        logNum1 = Log.ZERO
        logNum2 = Log.ONE
        assertTrue(logNum1 < logNum2)
        assertTrue(logNum2 > logNum1)

        logNum1 = Log(ExactFraction.EIGHT)
        logNum2 = Log(ExactFraction.EIGHT, 2)
        assertTrue(logNum1 < logNum2)
        assertTrue(logNum2 > logNum1)

        logNum1 = Log(ExactFraction(3, 4), 5)
        logNum2 = Log(ExactFraction(4, 3), 5)
        assertTrue(logNum1 < logNum2)
        assertTrue(logNum2 > logNum1)

        logNum1 = Log(ExactFraction(3, 4), 10, true)
        logNum2 = Log(ExactFraction(1, 4))
        assertTrue(logNum1 < logNum2)
        assertTrue(logNum2 > logNum1)

        logNum1 = Log(ExactFraction.TWO, 8)
        logNum2 = Log(ExactFraction(32), 8, true)
        assertTrue(logNum1 < logNum2)
        assertTrue(logNum2 > logNum1)
    }

    @Test
    internal fun testSwapDivided() {
        // error
        assertDivByZero { Log.ZERO.swapDivided() }

        // other
        var logNum = Log.ONE
        var expected = Log(ExactFraction.TEN, 10, true)
        assertEquals(expected, logNum.swapDivided())

        logNum = Log(ExactFraction.FOUR, 3, false)
        expected = Log(ExactFraction.FOUR, 3, true)
        assertEquals(expected, logNum.swapDivided())

        logNum = Log(ExactFraction(3, 8), 2, true)
        expected = Log(ExactFraction(3, 8), 2, false)
        assertEquals(expected, logNum.swapDivided())
    }

    @Test
    internal fun testIsZero() {
        var logNum = Log.ZERO
        assertTrue(logNum.isZero())

        logNum = Log.ONE
        assertFalse(logNum.isZero())

        logNum = Log(ExactFraction.TWO, 7)
        assertFalse(logNum.isZero())

        logNum = Log(ExactFraction.TEN)
        assertFalse(logNum.isZero())

        logNum = Log(ExactFraction(18))
        assertFalse(logNum.isZero())

        logNum = Log(ExactFraction(18, 7))
        assertFalse(logNum.isZero())

        logNum = Log(ExactFraction(7, 18))
        assertFalse(logNum.isZero())
    }

    @Test
    internal fun testIsRational() {
        // rational
        var logNum = Log.ZERO
        assertTrue(logNum.isRational())

        logNum = Log.ONE
        assertTrue(logNum.isRational())

        logNum = Log(ExactFraction(2048), 2)
        assertTrue(logNum.isRational())

        logNum = Log(ExactFraction(2048), 2, true)
        assertTrue(logNum.isRational())

        logNum = Log(ExactFraction(1, 27), 3)
        assertTrue(logNum.isRational())

        logNum = Log(ExactFraction(1, 1000), true)
        assertTrue(logNum.isRational())

        // irrational
        logNum = Log(ExactFraction(20))
        assertFalse(logNum.isRational())

        logNum = Log(ExactFraction(1, 10), 5)
        assertFalse(logNum.isRational())

        logNum = Log(ExactFraction(1000), 100, true)
        assertFalse(logNum.isRational())

        logNum = Log(ExactFraction(8, 1000), 2)
        assertFalse(logNum.isRational())
    }

    @Test
    internal fun testGetRationalValue() {
        // irrational
        var logNum = Log(ExactFraction.SIX)
        assertNull(logNum.getRationalValue())

        logNum = Log(ExactFraction(1000), 5)
        assertNull(logNum.getRationalValue())

        logNum = Log(ExactFraction(5, 12), 5)
        assertNull(logNum.getRationalValue())

        logNum = Log(ExactFraction(5, 12), 5, true)
        assertNull(logNum.getRationalValue())

        // rational
        logNum = Log.ZERO
        var expected = ExactFraction.ZERO
        assertEquals(expected, logNum.getRationalValue())

        logNum = Log(ExactFraction(32), 2)
        expected = ExactFraction.FIVE
        assertEquals(expected, logNum.getRationalValue())

        logNum = Log(ExactFraction(1, 27), 3)
        expected = -ExactFraction.THREE
        assertEquals(expected, logNum.getRationalValue())

        logNum = Log(ExactFraction(1, 1000))
        expected = -ExactFraction.THREE
        assertEquals(expected, logNum.getRationalValue())

        logNum = Log(ExactFraction(1, 1000), true)
        expected = ExactFraction(-1, 3)
        assertEquals(expected, logNum.getRationalValue())
    }

    @Test
    internal fun testGetValue() {
        // base 10
        var logNum = Log(ExactFraction.ONE)
        var expected = 0.toBigDecimal()
        assertEquals(expected, logNum.getValue())

        logNum = Log(ExactFraction(100))
        expected = 2.toBigDecimal()
        assertEquals(expected, logNum.getValue())

        logNum = Log(ExactFraction(3333))
        expected = BigDecimal("3.52283531366053")
        assertEquals(expected, logNum.getValue())

        logNum = Log(ExactFraction.HALF)
        expected = BigDecimal("-0.30102999566398114")
        assertEquals(expected, logNum.getValue())

        logNum = Log(ExactFraction(21, 2))
        expected = BigDecimal("1.02118929906993786")
        assertEquals(expected, logNum.getValue())

        // base 2
        logNum = Log(ExactFraction.ONE, 2)
        expected = 0.toBigDecimal()
        assertEquals(expected, logNum.getValue())

        logNum = Log(ExactFraction(1, 8), 2)
        expected = (-3).toBigDecimal()
        assertEquals(expected, logNum.getValue())

        logNum = Log(ExactFraction(200), 2)
        expected = BigDecimal("7.643856189774724")
        assertEquals(expected, logNum.getValue())

        // other
        logNum = Log(ExactFraction(216), 6)
        expected = 3.toBigDecimal()
        assertEquals(expected, logNum.getValue())

        logNum = Log(ExactFraction(15151515), 24)
        expected = BigDecimal("5.202432673429519")
        assertEquals(expected, logNum.getValue())

        logNum = Log(ExactFraction(18, 109), 9)
        expected = BigDecimal("-0.8196595572931246")
        assertEquals(expected, logNum.getValue())

        logNum = Log(ExactFraction(2000, 3), 3)
        expected = BigDecimal("5.9186395764396105")
        assertEquals(expected, logNum.getValue())

        // divided
        logNum = Log(ExactFraction.TEN, 10, true)
        expected = BigDecimal.ONE
        assertEquals(expected, logNum.getValue())

        logNum = Log(ExactFraction.EIGHT, 2, true)
        expected = BigDecimal("0.33333333333333333333")
        assertEquals(expected, logNum.getValue())

        logNum = Log(ExactFraction(1, 4), 10, true)
        expected = BigDecimal("-1.6609640474436814234")
        assertEquals(expected, logNum.getValue())

        logNum = Log(ExactFraction(12), 4, true)
        expected = BigDecimal("0.55788589130225962125")
        assertEquals(expected, logNum.getValue())
    }

    @Test
    internal fun testToString() {
        var logNum = Log.ZERO
        var expected = "[log_10(1)]"
        assertEquals(expected, logNum.toString())

        logNum = Log(ExactFraction.TEN, 108)
        expected = "[log_108(10)]"
        assertEquals(expected, logNum.toString())

        logNum = Log(ExactFraction(15))
        expected = "[log_10(15)]"
        assertEquals(expected, logNum.toString())

        logNum = Log(ExactFraction(30001), 3)
        expected = "[log_3(30001)]"
        assertEquals(expected, logNum.toString())

        logNum = Log(ExactFraction(103, 272), 14)
        expected = "[log_14(103/272)]"
        assertEquals(expected, logNum.toString())

        logNum = Log(ExactFraction(15, 8), 10, true)
        expected = "[1/log_10(15/8)]"
        assertEquals(expected, logNum.toString())

        logNum = Log(ExactFraction.FOUR, 3, true)
        expected = "[1/log_3(4)]"
        assertEquals(expected, logNum.toString())
    }

    @Test internal fun testSimplifyList() = runSimplifyListTests()
}
