package exactnumbers.irrationals.pi

import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

internal class PiNumTest {
    // @Test internal fun testConstructor() {}

    @Test
    internal fun testEquals() {
        assertEquals(Pi(), Pi())
    }

    @Test
    internal fun testGetValue() {
        // kotlin representation of pi: 3.141592653589793
        val piNum = Pi()
        val expected = BigDecimal("3.141592653589793")
        assertEquals(expected, piNum.getValue())
    }

    @Test
    internal fun testIsZero() {
        val piNum = Pi()
        assertFalse(piNum.isZero())
    }

    @Test
    internal fun testToString() {
        val piNum = Pi()
        val expected = "[π]"
        assertEquals(expected, piNum.toString())
    }
}
