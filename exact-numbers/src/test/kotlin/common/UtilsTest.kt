package common

import assertDivByZero
import kotlinutils.biginteger.ext.isZero
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

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
    internal fun testGetIntFromDecimal() {
        var goodValues = mapOf(BigDecimal.ZERO to BigInteger.ZERO, BigDecimal("0.000001") to BigInteger.ZERO)
        var badValues = listOf(BigDecimal.ONE, BigDecimal("-15"), BigDecimal("1.0001"))
        runSingleGetIntFromDecimalTest(goodValues, badValues, BigInteger::isZero)

        goodValues = mapOf()
        badValues = listOf(BigDecimal.ONE, BigDecimal.ZERO)
        runSingleGetIntFromDecimalTest(goodValues, badValues) { throw Exception() }

        var result = BigInteger("10")
        goodValues = mapOf(
            BigDecimal("10") to result,
            BigDecimal("9.999999999") to result,
            BigDecimal("10.0000001") to result,
            BigDecimal("-10") to BigInteger("-10")
        )
        badValues = listOf(BigDecimal("9"), BigDecimal("11"))
        runSingleGetIntFromDecimalTest(goodValues, badValues) { it * it == BigInteger("100") }

        goodValues = mapOf()
        badValues = listOf(BigDecimal.ONE, -BigDecimal.ONE, BigDecimal("2"), BigDecimal("-2"), BigDecimal("4"))
        runSingleGetIntFromDecimalTest(goodValues, badValues) { it * it == BigInteger("2") }

        result = BigInteger.TWO
        goodValues = mapOf(BigDecimal("2") to result, BigDecimal("1.9999") to result, BigDecimal(2.0000002) to result)
        badValues = listOf(BigDecimal("-2"), BigDecimal("0.5"), BigDecimal("4.999999"))
        runSingleGetIntFromDecimalTest(goodValues, badValues) { BigInteger("5").pow(it.toInt()) == BigInteger("25") }

        goodValues = mapOf()
        listOf(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal("2"), BigDecimal("-2"), BigDecimal("1.9999999"))
        runSingleGetIntFromDecimalTest(goodValues, badValues) { BigInteger("10").pow(it.toInt()) == BigInteger("99") }

        // test choosing closer number
        goodValues = mapOf(
            BigDecimal.ONE to BigInteger.ONE,
            BigDecimal("0.3") to BigInteger.ZERO,
            BigDecimal("0.5") to BigInteger.ONE,
            BigDecimal("0.7") to BigInteger.ONE,
            BigDecimal("15.5") to BigInteger("16"),
            BigDecimal("-15.3") to BigInteger("-15"),
            BigDecimal("-15.5") to BigInteger("-16"),
            BigDecimal("-15.7") to BigInteger("-16"),
            BigDecimal("10000.000001") to BigInteger("10000"),
            BigDecimal("10000.999999") to BigInteger("10001"),
        )
        badValues = listOf()
        runSingleGetIntFromDecimalTest(goodValues, badValues) { true }
    }

    private fun runSingleGetIntFromDecimalTest(
        goodValues: Map<BigDecimal, BigInteger>,
        badValues: List<BigDecimal>,
        test: (BigInteger) -> Boolean
    ) {
        for (pair in goodValues) {
            println(pair)
            assertEquals(pair.value, getIntFromDecimal(pair.key, test))
        }

        for (value in badValues) {
            assertNull(getIntFromDecimal(value, test))
        }
    }
}
