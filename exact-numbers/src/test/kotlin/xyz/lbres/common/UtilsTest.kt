package xyz.lbres.common

import assertDivByZero
import xyz.lbres.kotlinutils.biginteger.ext.isZero
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UtilsTest {
    @Test
    fun testDivideBigDecimals() {
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
    fun testGetIntFromDecimal() {
        var values = mapOf(
            BigDecimal.ZERO to BigInteger.ZERO,
            BigDecimal("0.000001") to BigInteger.ZERO,
            BigDecimal.ONE to null,
            BigDecimal("-15") to null,
            BigDecimal("1.0001") to null
        )
        runSingleGetIntFromDecimalTest(values, BigInteger::isZero)

        values = mapOf(BigDecimal.ONE to null, BigDecimal.ZERO to null)
        runSingleGetIntFromDecimalTest(values) { false }

        var result = BigInteger("10")
        values = mapOf(
            BigDecimal("10") to result,
            BigDecimal("9.999999999") to result,
            BigDecimal("10.0000001") to result,
            BigDecimal("-10") to BigInteger("-10"),
            BigDecimal("9") to null,
            BigDecimal("11") to null
        )
        runSingleGetIntFromDecimalTest(values) { it * it == BigInteger("100") }

        values = mapOf(
            BigDecimal.ONE to null,
            -BigDecimal.ONE to null,
            BigDecimal("2") to null,
            BigDecimal("-2") to null,
            BigDecimal("4") to null
        )
        runSingleGetIntFromDecimalTest(values) { it * it == BigInteger("2") }

        result = BigInteger.TWO
        values = mapOf(
            BigDecimal("2") to result,
            BigDecimal("1.9999") to result,
            BigDecimal("2.0000002") to result,
            BigDecimal("-2") to null,
            BigDecimal("0.5") to null,
            BigDecimal("4.999999") to null
        )
        runSingleGetIntFromDecimalTest(values) { BigInteger("5").pow(it.toInt()) == BigInteger("25") }

        values = mapOf(
            BigDecimal.ZERO to null,
            BigDecimal.ONE to null,
            BigDecimal("2") to null,
            BigDecimal("-2") to null,
            BigDecimal("1.9999999") to null
        )
        runSingleGetIntFromDecimalTest(values) { BigInteger("10").pow(it.toInt()) == BigInteger("99") }

        // test choosing closer number
        values = mapOf(
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
        runSingleGetIntFromDecimalTest(values) { true }
    }

    /**
     * Run single test for getIntFromDecimal
     *
     * @param values [Map]<BigDecimal, BigInteger?>: map of values to their expected results
     * @param test ([BigInteger]) -> Boolean: test to pass to getIntFromDecimal
     */
    private fun runSingleGetIntFromDecimalTest(values: Map<BigDecimal, BigInteger?>, test: (BigInteger) -> Boolean) {
        for (pair in values) {
            assertEquals(pair.value, getIntFromDecimal(pair.key, test))
        }
    }
}
