package xyz.lbres.exactnumbers.irrationals.pi

import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PiTest {
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
    fun testGetValue() {
        // kotlin representation of pi: 3.141592653589793
        var pi = Pi()
        var expected = BigDecimal("3.141592653589793")
        assertEquals(expected, pi.getValue())
        assertEquals(expected, pi.getValue())

        pi = Pi().inverse()
        expected = BigDecimal("0.31830988618379069570")
        assertEquals(expected, pi.getValue())
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
        assertFalse(pi.isRational())

        pi = Pi().inverse()
        assertFalse(pi.isRational())
        assertFalse(pi.isRational())
    }

    @Test
    fun testGetRationalValue() {
        var pi = Pi()
        assertNull(pi.getRationalValue())
        assertNull(pi.getRationalValue())

        pi = Pi().inverse()
        assertNull(pi.getRationalValue())
        assertNull(pi.getRationalValue())
    }

    @Test
    fun testInverse() {
        var pi = Pi().inverse()
        assertTrue(pi.isInverted)

        pi = pi.inverse()
        assertFalse(pi.isInverted)
    }

    @Test
    fun testToString() {
        var pi = Pi()
        var expected = "[π]"
        assertEquals(expected, pi.toString())

        pi = Pi().inverse()
        expected = "[1/π]"
        assertEquals(expected, pi.toString())
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
    fun testToByte() {
        assertEquals(3, Pi().toByte())
        assertEquals(0, Pi().inverse().toByte())
    }

    @Test
    fun testToChar() {
        assertEquals(3.toChar(), Pi().toChar())
        assertEquals(0.toChar(), Pi().inverse().toChar())
    }

    @Test
    fun testToShort() {
        assertEquals(3, Pi().toShort())
        assertEquals(0, Pi().inverse().toShort())
    }

    @Test
    fun testToInt() {
        assertEquals(3, Pi().toInt())
        assertEquals(0, Pi().inverse().toInt())
    }

    @Test
    fun testToLong() {
        assertEquals(3, Pi().toLong())
        assertEquals(0, Pi().inverse().toLong())
    }

    @Test
    fun testToFloat() {
        // kotlin representation of pi: 3.141592653589793
        assertEquals(3.1415927f, Pi().toFloat())
        assertEquals(0.31830987f, Pi().inverse().toFloat())
    }

    @Test
    fun testToDouble() {
        // kotlin representation of pi: 3.141592653589793
        assertEquals(3.141592653589793, Pi().toDouble())
        assertEquals(0.3183098861837907, Pi().inverse().toDouble())
    }

    @Test fun testSimplifySet() = runSimplifySetTests()
}
