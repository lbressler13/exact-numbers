package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.exactnumbers.testutils.assertDivByZero
import java.math.BigInteger
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

// Numerator and denominator are explicitly checked to ensure correct initialization
// Simplification is tested separately

fun runConstructorTests() {
    runStringTests()
    runSingleValTests()
    runPairValTests()
}

// thorough testing is done in parsing tests
private fun runStringTests() {
    // decimal string
    var ef = ExactFraction("1.51")
    assertEquals(BigInteger("151"), ef.numerator)
    assertEquals(BigInteger("100"), ef.denominator)

    // EF string
    ef = ExactFraction("EF[-7 3]")
    assertEquals(BigInteger("-7"), ef.numerator)
    assertEquals(BigInteger("3"), ef.denominator)

    // invalid
    assertFailsWith<NumberFormatException>("Invalid EF string format") { ExactFraction("[]") }
}

private fun runSingleValTests() {
    runMultiTypeSingleValTest(0, ExactFraction(BigInteger.ZERO, BigInteger.ONE))
    runMultiTypeSingleValTest(3, ExactFraction(BigInteger("3"), BigInteger.ONE))
    runMultiTypeSingleValTest(-3, ExactFraction(BigInteger("-3"), BigInteger.ONE))

    val largeValue = BigInteger("10000000000000000345678")
    val ef = ExactFraction(largeValue)
    assertEquals(largeValue, ef.numerator)
    assertEquals(BigInteger.ONE, ef.denominator)
}

private fun runPairValTests() {
    // denominator of 0
    assertDivByZero { ExactFraction(1, 0) }
    assertDivByZero { ExactFraction(1L, 0L) }
    assertDivByZero { ExactFraction(BigInteger.ONE, 0) }
    assertDivByZero { ExactFraction(1, BigInteger.ZERO) }
    assertDivByZero { ExactFraction(1, 0L) }
    assertDivByZero { ExactFraction(1L, 0) }
    assertDivByZero { ExactFraction(BigInteger.ONE, 0L) }
    assertDivByZero { ExactFraction(1L, BigInteger.ZERO) }

    // numerator of 0
    runMultiTypePairValTest(0, 1, ExactFraction(BigInteger.ZERO, BigInteger.ONE))

    // positive whole
    runMultiTypePairValTest(4, 1, ExactFraction(BigInteger("4"), BigInteger.ONE))

    // positive fraction < 1
    runMultiTypePairValTest(7, 18, ExactFraction(BigInteger("7"), BigInteger("18")))

    // positive fraction > 1
    runMultiTypePairValTest(4, 3, ExactFraction(BigInteger("4"), BigInteger("3")))

    // negative whole
    runMultiTypePairValTest(-4, 1, ExactFraction(BigInteger("-4"), BigInteger.ONE))

    // negative fraction > -1
    runMultiTypePairValTest(1, -3, ExactFraction(BigInteger.ONE, BigInteger("-3")))

    // negative fraction < -1
    runMultiTypePairValTest(-7, 4, ExactFraction(BigInteger("-7"), BigInteger("4")))

    val largeValue1 = BigInteger("1000000000000000000345678")
    val largeValue2 = BigInteger("88888888888888888888889")
    val ef = ExactFraction(largeValue1, largeValue2)
    assertEquals(largeValue1, ef.numerator)
    assertEquals(largeValue2, ef.denominator)
}

/**
 * Run single-val test with Int, Long, and BigInteger values
 *
 * @param value [Int]: value to cast to Int, Long, and BigInteger
 * @param expected [ExactFraction]: expected result
 */
private fun runMultiTypeSingleValTest(value: Int, expected: ExactFraction) {
    assertEquals(expected, ExactFraction(value))
    assertEquals(expected, ExactFraction(value.toLong()))
    assertEquals(expected, ExactFraction(value.toBigInteger()))
}

/**
 * Run two-val test with all combinations of Int, Long, and BigInteger values
 *
 * @param value1 [Int]: first value to cast to Int, Long, and BigInteger
 * @param value2 [Int]: second value to cast to Int, Long, and BigInteger
 * @param expected [ExactFraction]: expected result
 */
private fun runMultiTypePairValTest(value1: Int, value2: Int, expected: ExactFraction) {
    // Int, Int
    assertEquals(expected, ExactFraction(value1, value2))
    // Long, Long
    assertEquals(expected, ExactFraction(value1.toLong(), value2.toLong()))
    // Int, Long
    assertEquals(expected, ExactFraction(value1, value2.toLong()))
    // Long, Int
    assertEquals(expected, ExactFraction(value1.toLong(), value2))
    // BigInteger, Int
    assertEquals(expected, ExactFraction(value1.toBigInteger(), value2))
    // Int, BigInteger
    assertEquals(expected, ExactFraction(value1, value2.toBigInteger()))
    // BigInteger, Long
    assertEquals(expected, ExactFraction(value1.toBigInteger(), value2.toLong()))
    // BigInteger, BigInteger
    assertEquals(expected, ExactFraction(value1.toBigInteger(), value2.toBigInteger()))
    // Long, BigInteger
    assertEquals(expected, ExactFraction(value1.toLong(), value2.toBigInteger()))
}
