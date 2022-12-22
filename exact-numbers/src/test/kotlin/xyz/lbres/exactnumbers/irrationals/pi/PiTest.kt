package xyz.lbres.exactnumbers.irrationals.pi

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
        val pi = Pi()
        assertFalse(pi.isInverted)
    }

    @Test
    fun testEquals() {
        var pi1 = Pi()
        assertEquals(pi1, pi1)

        pi1 = Pi().inverse()
        assertEquals(pi1, pi1)

        val pi2 = Pi()
        assertNotEquals(pi1, pi2)
        assertNotEquals(pi2, pi1)
    }

    @Test
    fun testCompareTo() {
        // equal
        assertEquals(Pi(), Pi())
        assertEquals(Pi().inverse(), Pi().inverse())

        // not equal
        assertTrue(Pi().inverse() < Pi())
        assertTrue(Pi() > Pi().inverse())
    }

    @Test fun testTimes() = runTimesTests()
    @Test fun testDiv() = runDivTests()

    @Test
    fun testGetValue() {
        // kotlin representation of pi: 3.141592653589793
        var pi = Pi()
        var expected = BigDecimal("3.141592653589793")
        assertEquals(expected, pi.getValue())

        pi = Pi().inverse()
        expected = BigDecimal("0.31830988618379069570")
        assertEquals(expected, pi.getValue())
    }

    @Test
    fun testIsZero() {
        var pi = Pi()
        assertFalse(pi.isZero())

        pi = Pi().inverse()
        assertFalse(pi.isZero())
    }

    @Test
    fun testIsRational() {
        var pi = Pi()
        assertFalse(pi.isRational())

        pi = Pi().inverse()
        assertFalse(pi.isRational())
    }

    @Test
    fun testGetRationalValue() {
        var pi = Pi()
        assertNull(pi.getRationalValue())

        pi = Pi().inverse()
        assertNull(pi.getRationalValue())
    }

    @Test
    fun testInverse() {
        var pi = Pi()
        assertTrue(pi.inverse().isInverted)

        pi = Pi().inverse()
        assertFalse(pi.inverse().isInverted)
    }

    @Test
    fun testToString() {
        var piNum = Pi()
        var expected = "[π]"
        assertEquals(expected, piNum.toString())

        piNum = Pi().inverse()
        expected = "[1/π]"
        assertEquals(expected, piNum.toString())
    }

    @Test fun testToByte() = runToByteTests()
    @Test fun testToChar() = runToCharTests()
    @Test fun testToShort() = runToShortTests()
    @Test fun testToInt() = runToIntTests()
    @Test fun testToLong() = runToLongTests()
    @Test fun testToFloat() = runToFloatTests()
    @Test fun testToDouble() = runToDoubleTests()

    @Test fun testSimplifySet() = runSimplifySetTests()
}
