package utils

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
}
