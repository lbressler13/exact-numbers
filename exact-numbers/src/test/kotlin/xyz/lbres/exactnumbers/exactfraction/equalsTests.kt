package xyz.lbres.exactnumbers.exactfraction

import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun runEqualsTests() {
    assertEquals(ExactFraction(0), ExactFraction(0))
    assertEquals(ExactFraction(-1, 3), ExactFraction(-1, 3))
    assertEquals(ExactFraction(5, 2), ExactFraction(5, 2))
    assertEquals(ExactFraction(10), ExactFraction(-30, -3))
    assertEquals(ExactFraction(10, 34), ExactFraction(5, 17))

    assertNotEquals(ExactFraction(1, 3), ExactFraction(-1, 3))
    assertNotEquals(ExactFraction(-1, 3), ExactFraction(1, 3))
    assertNotEquals(ExactFraction(2, 3), ExactFraction(3, 2))
}

fun runEqTests() {
    runMultiTypeEqTest(ExactFraction(0), 0, true)
    runMultiTypeEqTest(ExactFraction(-3), -3, true)
    runMultiTypeEqTest(ExactFraction(34, 17), 2, true)

    runMultiTypeEqTest(ExactFraction(10), -10, false)
    runMultiTypeEqTest(ExactFraction(10, 7), 1, false)
    runMultiTypeEqTest(ExactFraction(-70), 0, false)
}

/**
 * Run test with Int, Long, and BigInteger values
 *
 * @param ef [ExactFraction]: exact fraction
 * @param other [Int]: value to cast to Int, Long, and BigInteger
 * @param expected [Boolean]: expected result
 */
private fun runMultiTypeEqTest(ef: ExactFraction, other: Int, expected: Boolean) {
    assertEquals(expected, ef.eq(other))
    assertEquals(expected, ef.eq(other.toLong()))
    assertEquals(expected, ef.eq(other.toBigInteger()))
}
