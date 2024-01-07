package xyz.lbres.common

import xyz.lbres.kotlinutils.biginteger.ext.isZero
import xyz.lbres.testutils.assertDivByZero
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals

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
            BigInteger.ZERO to setOf(BigDecimal.ZERO, BigDecimal("0.000001")),
            null to setOf(BigDecimal.ONE, BigDecimal("-15"), BigDecimal("1.001"))
        )
        runSingleGetIntFromDecimalTest(values, BigInteger::isZero)

        values = mapOf(null to setOf(BigDecimal.ONE, BigDecimal.ZERO))
        runSingleGetIntFromDecimalTest(values) { false }

        values = mapOf(
            BigInteger("10") to setOf(BigDecimal("10"), BigDecimal("9.999999999"), BigDecimal("10.0000001")),
            BigInteger("-10") to setOf(BigDecimal("-10")),
            null to setOf(BigDecimal("9"), BigDecimal("11"))
        )
        runSingleGetIntFromDecimalTest(values) { it * it == BigInteger("100") }

        values = mapOf(null to setOf(BigDecimal.ONE, -BigDecimal.ONE, BigDecimal("2"), BigDecimal("-2"), BigDecimal("4")))
        runSingleGetIntFromDecimalTest(values) { it * it == BigInteger("2") }

        values = mapOf(
            BigInteger.TWO to setOf(BigDecimal("2"), BigDecimal("1.9999"), BigDecimal("2.0000002")),
            null to setOf(BigDecimal("-2"), BigDecimal("0.5"), BigDecimal("4.999999"))
        )
        runSingleGetIntFromDecimalTest(values) { BigInteger("5").pow(it.toInt()) == BigInteger("25") }

        values = mapOf(null to setOf(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal("2"), BigDecimal("-2"), BigDecimal("1.9999999")))
        runSingleGetIntFromDecimalTest(values) { BigInteger("10").pow(it.toInt()) == BigInteger("99") }

        // test choosing closer number
        values = mapOf(
            BigInteger.ZERO to setOf(BigDecimal("0.3")),
            BigInteger.ONE to setOf(BigDecimal.ONE, BigDecimal("0.5"), BigDecimal("0.7")),
            BigInteger("16") to setOf(BigDecimal("15.5")),
            BigInteger("-15") to setOf(BigDecimal("-15.3")),
            BigInteger("-16") to setOf(BigDecimal("-15.5"), BigDecimal("-15.7")),
            BigInteger("10000") to setOf(BigDecimal("10000.000001")),
            BigInteger("10001") to setOf(BigDecimal("10000.999999"))
        )
        runSingleGetIntFromDecimalTest(values) { true }
    }

    @Test
    fun testCreateHashCode() {
        // TODO
    }

    /**
     * Run single test for getIntFromDecimal
     *
     * @param mapping [Map]<BigInteger?, Set<BigDecimal>>: map where first key is the expected result, and value is a set of inputs expected to return the result
     * @param test ([BigInteger]) -> Boolean: test to pass to getIntFromDecimal
     */
    private fun runSingleGetIntFromDecimalTest(mapping: Map<BigInteger?, Set<BigDecimal>>, test: (BigInteger) -> Boolean) {
        mapping.forEach { (result, values) ->
            values.forEach { assertEquals(result, getIntFromDecimal(it, test)) }
        }
    }
}
