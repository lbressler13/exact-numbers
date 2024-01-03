package xyz.lbres.exactnumbers.exactfraction

import kotlin.test.assertEquals
import kotlin.test.assertTrue

@Suppress("KotlinConstantConditions")
fun runCompareToTests() {
    // equal values
    var first = ExactFraction(0)
    var second = ExactFraction(0)
    assertEquals(0, first.compareTo(second))

    first = ExactFraction(100, 3)
    second = ExactFraction(100, 3)
    assertEquals(0, first.compareTo(second))

    // pos less than zero
    first = ExactFraction(3)
    second = ExactFraction(0)
    assertTrue(first > second)

    // neg less than zero
    first = ExactFraction(-3)
    second = ExactFraction(0)
    assertTrue(first < second)

    // neg less than pos
    first = ExactFraction(-1)
    second = ExactFraction(1)
    assertTrue(first < second)

    // neg order
    first = ExactFraction(-3, 4)
    second = ExactFraction(-2)
    assertTrue(first > second)

    first = ExactFraction(-3, 4)
    second = ExactFraction(-4, 3)
    assertTrue(first > second)

    // pos order
    first = ExactFraction(3)
    second = ExactFraction(2)
    assertTrue(first > second)

    first = ExactFraction(3, 4)
    second = ExactFraction(4, 3)
    assertTrue(first < second)

    // other number types
    runMultiTypeCompareTest(0, ExactFraction.ZERO, 0) // eq
    runMultiTypeCompareTest(100, ExactFraction(100, 3), -1) // lt
    runMultiTypeCompareTest(0, ExactFraction(3, 2), 1) // gt
    runMultiTypeCompareTest(0, ExactFraction(-3), -1) // lt
    runMultiTypeCompareTest(1, ExactFraction(-1), -1) // lt
    runMultiTypeCompareTest(-2, ExactFraction(-3, 4), 1) // lt
    runMultiTypeCompareTest(2, ExactFraction(3), 1) // gt
}

private fun runMultiTypeCompareTest(value: Int, ef: ExactFraction, expected: Int) {
    assertEquals(expected, ef.compareTo(value))
    assertEquals(expected, ef.compareTo(value.toLong()))
    assertEquals(expected, ef.compareTo(value.toBigInteger()))
}
