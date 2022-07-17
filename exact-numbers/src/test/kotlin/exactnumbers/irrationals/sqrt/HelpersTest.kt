package exactnumbers.irrationals.sqrt

import java.math.BigDecimal
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals

internal class HelpersTest {
    @Test
    internal fun testGetRootOf() {
        // rational
        var num = BigInteger.ZERO
        var expected = BigDecimal.ZERO
        assertEquals(expected, getRootOf(num))

        num = BigInteger.ONE
        expected = BigDecimal.ONE
        assertEquals(expected, getRootOf(num))

        num = BigInteger("961")
        expected = BigDecimal("31")
        assertEquals(expected, getRootOf(num))

        num = BigInteger("17424")
        expected = BigDecimal("132")
        assertEquals(expected, getRootOf(num))

        // irrational
        num = BigInteger.TWO
        expected = BigDecimal("1.4142135623730950488")
        assertEquals(expected, getRootOf(num))

        num = BigInteger("8")
        expected = BigDecimal("2.8284271247461900976")
        assertEquals(expected, getRootOf(num))

        num = BigInteger("1333333")
        expected = BigDecimal("1154.7003940416752105")
        assertEquals(expected, getRootOf(num))
    }

    @Test
    internal fun testExtractWholeOf() {
        val one = BigInteger.ONE

        // rational
        var num = BigInteger.ZERO
        var expected = one
        assertEquals(expected, extractWholeOf(num))

        num = one
        expected = one
        assertEquals(expected, extractWholeOf(num))

        num = BigInteger("100")
        expected = BigInteger("10")
        assertEquals(expected, extractWholeOf(num))

        num = BigInteger("36")
        expected = BigInteger("6")
        assertEquals(expected, extractWholeOf(num))

        num = BigInteger("8281")
        expected = BigInteger("91")
        assertEquals(expected, extractWholeOf(num))

        num = BigInteger("17424")
        expected = BigInteger("132")
        assertEquals(expected, extractWholeOf(num))

        // irrational w/ whole
        num = BigInteger("8")
        expected = BigInteger.TWO
        assertEquals(expected, extractWholeOf(num))

        num = BigInteger("200")
        expected = BigInteger.TEN
        assertEquals(expected, extractWholeOf(num))

        num = BigInteger("296208")
        expected = BigInteger("132")
        assertEquals(expected, extractWholeOf(num))

        // no whole
        expected = one

        num = BigInteger.TWO
        assertEquals(expected, extractWholeOf(num))

        num = BigInteger("55")
        assertEquals(expected, extractWholeOf(num))

        num = BigInteger("52910")
        assertEquals(expected, extractWholeOf(num))
    }
}
