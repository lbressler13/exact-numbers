package xyz.lbres.exactnumbers.exactfraction

import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun runEqualsTests() {
    assertEquals(ExactFraction(0), ExactFraction(0))
    assertEquals(ExactFraction(-1, 3), ExactFraction(-1, 3))
    assertEquals(ExactFraction(5, 2), ExactFraction(5, 2))
    assertEquals(ExactFraction(10), ExactFraction(-30, -3))
    assertEquals(ExactFraction(5, 17), ExactFraction(10, 34))

    assertNotEquals(ExactFraction(1, 3), ExactFraction(-1, 3))
    assertNotEquals(ExactFraction(2, 3), ExactFraction(3, 2))
}

fun runEqTests() {
    runMultiTypeEqTest(ExactFraction(0), 0, true)
    runMultiTypeEqTest(ExactFraction(-3), -3, true)
    runMultiTypeEqTest(ExactFraction(10), -10, false)
    runMultiTypeEqTest(ExactFraction(10, 7), 1, false)
    runMultiTypeEqTest(ExactFraction(-70), 0, false)
}

private fun runMultiTypeEqTest(ef: ExactFraction, value: Int, expected: Boolean) {
    assertEquals(expected, ef.eq(value))
    assertEquals(expected, ef.eq(value.toLong()))
    assertEquals(expected, ef.eq(value.toBigInteger()))
}
