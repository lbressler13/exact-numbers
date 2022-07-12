package shared

import assertDivByZero
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals

internal class UtilsTest {
    @Test
    internal fun testGetGcd() {
        // zero
        runSingleGetGcdTest(0, 0, 1)
        runSingleGetGcdTest(1, 0, 1)
        runSingleGetGcdTest(4, 0, 4)
        runSingleGetGcdTest(-4, 0, 4)

        // one
        runSingleGetGcdTest(1, 1, 1)
        runSingleGetGcdTest(1, -1, 1)
        runSingleGetGcdTest(1, 103, 1)
        runSingleGetGcdTest(1, -103, 1)

        // equal
        runSingleGetGcdTest(17, 17, 17)
        runSingleGetGcdTest(17, -17, 17)
        runSingleGetGcdTest(-81, -81, 81)

        // co-prime
        // primes
        runSingleGetGcdTest(17, 19, 1)
        runSingleGetGcdTest(-17, 19, 1)
        runSingleGetGcdTest(-2, -3, 1)
        // composites
        runSingleGetGcdTest(14, 81, 1)
        runSingleGetGcdTest(-15, 1024, 1)

        // exact divisor
        // primes
        runSingleGetGcdTest(2, 4, 2)
        runSingleGetGcdTest(-1002, 3, 3)
        runSingleGetGcdTest(1002, -3, 3)
        // composites
        runSingleGetGcdTest(1002, 334, 334)

        // common factor
        // prime gcd
        runSingleGetGcdTest(6, 4, 2)
        runSingleGetGcdTest(15, -25, 5)
        // composite gcd
        runSingleGetGcdTest(120, 100, 20)
        runSingleGetGcdTest(-120, -100, 20)
        runSingleGetGcdTest(34, 51, 17)
    }

    private fun runSingleGetGcdTest(num1: Int, num2: Int, expected: Int) {
        val b1 = num1.toBigInteger()
        val b2 = num2.toBigInteger()
        val expectedBigInt = expected.toBigInteger()
        assertEquals(expectedBigInt, getGCD(b1, b2))
        assertEquals(expectedBigInt, getGCD(b2, b1))
    }

    @Test
    internal fun testDivideBigDecimals() {
        // errors
        assertDivByZero { divideBigDecimals(BigDecimal.ZERO, BigDecimal.ZERO) }
        assertDivByZero { divideBigDecimals(BigDecimal("1.234"), BigDecimal.ZERO) }

        // no rounding
        var bd1 = BigDecimal.ZERO
        var bd2 = BigDecimal.ONE
        var expected = BigDecimal.ZERO
        assertEquals(expected, divideBigDecimals(bd1, bd2))

        bd1 = BigDecimal("2003")
        bd2 = BigDecimal("8")
        expected = BigDecimal("250.375")
        assertEquals(expected, divideBigDecimals(bd1, bd2))

        bd1 = BigDecimal("0.2222222222222222222222222222222")
        bd2 = BigDecimal("2")
        expected = BigDecimal("0.1111111111111111111111111111111")
        assertEquals(expected, divideBigDecimals(bd1, bd2))

        // rounding
        bd1 = BigDecimal.ONE
        bd2 = BigDecimal("3")
        expected = BigDecimal("0.33333333333333333333")
        assertEquals(expected, divideBigDecimals(bd1, bd2))

        bd1 = BigDecimal("103")
        bd2 = BigDecimal("14")
        expected = BigDecimal("7.3571428571428571429")
        assertEquals(expected, divideBigDecimals(bd1, bd2))
    }

    @Test
    internal fun testDivideByZero() {
        assertDivByZero { throwDivideByZero() }
    }
}
