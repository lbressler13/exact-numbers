package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.assertDivByZero
import java.math.BigInteger
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

// Numerator and denominator are explicitly checked to ensure correct initialization
// Simplication is tested separately

fun runConstructorTests() {
    testSingleValConstructor()
    testPairValConstructor()
    testStringConstructor()
}

// thorough testing is done in parsing tests
private fun testStringConstructor() {
    // Decimal string
    var ef = ExactFraction("1.51")
    assertEquals(151.toBigInteger(), ef.numerator)
    assertEquals(100.toBigInteger(), ef.denominator)

    // EF string
    ef = ExactFraction("EF[-7 3]")
    assertEquals((-7).toBigInteger(), ef.numerator)
    assertEquals(3.toBigInteger(), ef.denominator)

    // Invalid
    assertFailsWith<NumberFormatException>("Invalid EF string format") { ExactFraction("[]") }
}

private fun testSingleValConstructor() {
    runMultiTypeSingleValTest(0) {
        assertEquals(BigInteger.ZERO, it.numerator)
        assertEquals(BigInteger.ONE, it.denominator)
    }

    runMultiTypeSingleValTest(3) {
        assertEquals(3.toBigInteger(), it.numerator)
        assertEquals(BigInteger.ONE, it.denominator)
    }

    runMultiTypeSingleValTest(-3) {
        assertEquals((-3).toBigInteger(), it.numerator)
        assertEquals(BigInteger.ONE, it.denominator)
    }
}

private fun testPairValConstructor() {
    // denominator of 0
    assertDivByZero { ExactFraction(BigInteger.ONE, BigInteger.ZERO) }
    assertDivByZero { ExactFraction(1, 0) }
    assertDivByZero { ExactFraction(1L, 0L) }
    assertDivByZero { ExactFraction(BigInteger.ONE, 0) }
    assertDivByZero { ExactFraction(1, BigInteger.ZERO) }
    assertDivByZero { ExactFraction(1, 0L) }
    assertDivByZero { ExactFraction(1L, 0) }
    assertDivByZero { ExactFraction(BigInteger.ONE, 0L) }
    assertDivByZero { ExactFraction(1L, BigInteger.ZERO) }

    // numerator of 0
    runMultiTypePairValTest(0, 1) {
        assertEquals(BigInteger.ZERO, it.numerator)
        assertEquals(BigInteger.ONE, it.denominator)
    }

    // positive whole
    runMultiTypePairValTest(4, 1) {
        assertEquals(4.toBigInteger(), it.numerator)
        assertEquals(BigInteger.ONE, it.denominator)
    }

    // positive fraction < 1
    runMultiTypePairValTest(7, 18) {
        assertEquals(7.toBigInteger(), it.numerator)
        assertEquals(18.toBigInteger(), it.denominator)
    }

    // positive fraction > 1
    runMultiTypePairValTest(4, 3) {
        assertEquals(4.toBigInteger(), it.numerator)
        assertEquals(3.toBigInteger(), it.denominator)
    }

    // negative whole
    runMultiTypePairValTest(-4, 1) {
        assertEquals((-4).toBigInteger(), it.numerator)
        assertEquals(BigInteger.ONE, it.denominator)
    }

    // negative fraction > -1
    runMultiTypePairValTest(-1, 3) {
        assertEquals(-BigInteger.ONE, it.numerator)
        assertEquals(3.toBigInteger(), it.denominator)
    }

    // negative fraction < -1
    runMultiTypePairValTest(-7, 4) {
        assertEquals((-7).toBigInteger(), it.numerator)
        assertEquals(4.toBigInteger(), it.denominator)
    }
}

/**
 * Run single-val constructor test with Int, Long, and BigInteger values
 *
 * @param value [Int]: value to cast to Int, Long, and BigInteger
 * @param validateResult ([ExactFraction]) -> Unit: function to validate constructed fraction
 */
private fun runMultiTypeSingleValTest(value: Int, validateResult: (ExactFraction) -> Unit) {
    validateResult(ExactFraction(value))
    validateResult(ExactFraction(value.toLong()))
    validateResult(ExactFraction(value.toBigInteger()))
}

/**
 * Run two-val constructor test with all combinations of Int, Long, and BigInteger values
 *
 * @param value1 [Int]: first value to cast to Int, Long, and BigInteger
 * @param value2 [Int]: first value to cast to Int, Long, and BigInteger
 * @param validateResult ([ExactFraction]) -> Unit: function to validate constructed fraction
 */
private fun runMultiTypePairValTest(value1: Int, value2: Int, validateResult: (ExactFraction) -> Unit) {
    // BigInteger, BigInteger
    validateResult(ExactFraction(value1.toBigInteger(), value2.toBigInteger()))
    // Int, Int
    validateResult(ExactFraction(value1, value2))
    // Long, Long
    validateResult(ExactFraction(value1.toLong(), value2.toLong()))
    // Int, Long
    validateResult(ExactFraction(value1, value2.toLong()))
    // Long, Int
    validateResult(ExactFraction(value1.toLong(), value2))
    // BigInteger, Int
    validateResult(ExactFraction(value1.toBigInteger(), value2))
    // Int, BigInteger
    validateResult(ExactFraction(value1, value2.toBigInteger()))
    // BigInteger, Long
    validateResult(ExactFraction(value1.toBigInteger(), value2.toLong()))
    // Long, BigInteger
    validateResult(ExactFraction(value1.toLong(), value2.toBigInteger()))
}
