package xyz.lbres.exactnumbers.exactfraction

import assertDivByZero
import java.math.BigInteger
import kotlin.test.assertEquals
import kotlin.test.assertFails

// Numerator and denominator are explicitly checked to ensure correct initialization

private val zero = BigInteger.ZERO
private val pos1 = BigInteger.ONE
private val neg1 = -BigInteger.ONE
private val pos3 = 3.toBigInteger()
private val neg3 = (-3).toBigInteger()
private val pos4 = 4.toBigInteger()
private val neg4 = (-4).toBigInteger()
private val pos7 = 7.toBigInteger()
private val neg7 = (-7).toBigInteger()
private val pos18 = 18.toBigInteger()

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
    assertEquals(neg7, ef.numerator)
    assertEquals(pos3, ef.denominator)

    // Invalid
    assertFails { ExactFraction("[]") }
}

private fun testSingleValConstructor() {
    runMultiTypeSingleValTest(0) {
        assertEquals(it.numerator, zero)
        assertEquals(it.denominator, pos1)
    }

    runMultiTypeSingleValTest(3) {
        assertEquals(it.numerator, pos3)
        assertEquals(it.denominator, pos1)
    }

    runMultiTypeSingleValTest(-3) {
        assertEquals(it.numerator, neg3)
        assertEquals(it.denominator, pos1)
    }
}

private fun testPairValConstructor() {
    // denominator of 0
    assertDivByZero { ExactFraction(pos1, zero) }
    assertDivByZero { ExactFraction(1, 0) }
    assertDivByZero { ExactFraction(1L, 0L) }
    assertDivByZero { ExactFraction(pos1, 0) }
    assertDivByZero { ExactFraction(1, zero) }
    assertDivByZero { ExactFraction(1, 0L) }
    assertDivByZero { ExactFraction(1L, 0) }
    assertDivByZero { ExactFraction(pos1, 0L) }
    assertDivByZero { ExactFraction(1L, zero) }

    // numerator of 0
    runMultiTypePairValTest(0, 1) {
        assertEquals(zero, it.numerator)
        assertEquals(pos1, it.denominator)
    }

    // positive whole
    runMultiTypePairValTest(4, 1) {
        assertEquals(pos4, it.numerator)
        assertEquals(pos1, it.denominator)
    }

    // positive fraction < 1
    runMultiTypePairValTest(7, 18) {
        assertEquals(pos7, it.numerator)
        assertEquals(pos18, it.denominator)
    }

    // positive fraction > 1
    runMultiTypePairValTest(4, 3) {
        assertEquals(pos4, it.numerator)
        assertEquals(pos3, it.denominator)
    }

    // negative whole
    runMultiTypePairValTest(-4, 1) {
        assertEquals(neg4, it.numerator)
        assertEquals(pos1, it.denominator)
    }

    // negative fraction > -1
    runMultiTypePairValTest(-1, 3) {
        assertEquals(neg1, it.numerator)
        assertEquals(pos3, it.denominator)
    }

    // negative fraction < -1
    runMultiTypePairValTest(-7, 4) {
        assertEquals(neg7, it.numerator)
        assertEquals(pos4, it.denominator)
    }
}

/**
 * Run test with Int, Long, and BigInteger values
 *
 * @param value [Int]: value to cast to Int, Long, and BigInteger
 * @param validateResult ([ExactFraction]) -> Unit: function to validate constructed fraction
 */
private fun runMultiTypeSingleValTest(value: Int, validateResult: (ExactFraction) -> Unit) {
    validateResult(ExactFraction(value))
    validateResult(ExactFraction(value.toLong()))
    validateResult(ExactFraction(value.toBigInteger()))
}

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
