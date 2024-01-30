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

    // positive greater than zero
    first = ExactFraction(3)
    second = ExactFraction(0)
    assertTrue(first > second)
    assertTrue(second < first)

    // negative less than zero
    first = ExactFraction(-3)
    second = ExactFraction(0)
    assertTrue(first < second)
    assertTrue(second > first)

    // negative less than positive
    first = ExactFraction(-1)
    second = ExactFraction(1)
    assertTrue(first < second)
    assertTrue(second > first)

    // negative order
    first = ExactFraction(-3, 4)
    second = ExactFraction(-2)
    assertTrue(first > second)
    assertTrue(second < first)

    first = ExactFraction(-3, 4)
    second = ExactFraction(-4, 3)
    assertTrue(first > second)
    assertTrue(second < first)

    // positive order
    first = ExactFraction(3)
    second = ExactFraction(2)
    assertTrue(first > second)
    assertTrue(second < first)

    first = ExactFraction(3, 4)
    second = ExactFraction(4, 3)
    assertTrue(first < second)
    assertTrue(second > first)

    // other number types
    runMultiTypeEqTest(ExactFraction.NEG_ONE, -1)
    runMultiTypeLtTest(ExactFraction(100, 3), 100)
    runMultiTypeGtTest(ExactFraction(3, 2), 0)
    runMultiTypeGtTest(ExactFraction(3), 2)
    runMultiTypeLtTest(ExactFraction(-9, 4), -2)
}

// run compare test where ef == other
private fun runMultiTypeEqTest(ef: ExactFraction, other: Int) {
    assertEquals(0, ef.compareTo(other))
    assertEquals(0, ef.compareTo(other.toLong()))
    assertEquals(0, ef.compareTo(other.toBigInteger()))
}

// run compare test where ef > other
private fun runMultiTypeGtTest(ef: ExactFraction, other: Int) {
    assertTrue(ef > other)
    assertTrue(ef > other.toLong())
    assertTrue(ef > other.toBigInteger())
}

// run compare test where ef < other
private fun runMultiTypeLtTest(ef: ExactFraction, other: Int) {
    assertTrue(ef < other)
    assertTrue(ef < other.toLong())
    assertTrue(ef < other.toBigInteger())
}
