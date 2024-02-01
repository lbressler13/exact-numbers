package xyz.lbres.exactnumbers.ext

import xyz.lbres.exactnumbers.testutils.assertDivByZero
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals

class BigDecimalExtTest {
    @Test
    fun testDivideBy() {
        // errors
        assertDivByZero { BigDecimal.ZERO.divideBy(BigDecimal.ZERO) }
        assertDivByZero { BigDecimal("1.234").divideBy(BigDecimal.ZERO) }

        // no rounding
        var bd1 = BigDecimal.ZERO
        var bd2 = BigDecimal.ONE
        var expected = BigDecimal.ZERO
        assertEquals(expected, bd1.divideBy(bd2))

        bd1 = BigDecimal("2003")
        bd2 = BigDecimal("8")
        expected = BigDecimal("250.375")
        assertEquals(expected, bd1.divideBy(bd2))

        bd1 = BigDecimal("0.2222222222222222222222222222222")
        bd2 = BigDecimal("2")
        expected = BigDecimal("0.1111111111111111111111111111111")
        assertEquals(expected, bd1.divideBy(bd2))

        // rounding
        bd1 = BigDecimal.ONE
        bd2 = BigDecimal("3")
        expected = BigDecimal("0.33333333333333333333")
        assertEquals(expected, bd1.divideBy(bd2))

        bd1 = BigDecimal("103")
        bd2 = BigDecimal("14")
        expected = BigDecimal("7.3571428571428571429") // rounds up
        assertEquals(expected, bd1.divideBy(bd2))
    }
}
