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

    // negative less than zero
    first = ExactFraction(-3)
    second = ExactFraction(0)
    assertTrue(first < second)

    // negative less than positive
    first = ExactFraction(-1)
    second = ExactFraction(1)
    assertTrue(first < second)
    assertTrue(second > first)

    // negative order
    first = ExactFraction(-3, 4)
    second = ExactFraction(-2)
    assertTrue(first > second)

    first = ExactFraction(-3, 4)
    second = ExactFraction(-4, 3)
    assertTrue(first > second)

    // positive order
    first = ExactFraction(3)
    second = ExactFraction(2)
    assertTrue(first > second)

    first = ExactFraction(3, 4)
    second = ExactFraction(4, 3)
    assertTrue(first < second)

    // other number types
    runMultiTypeCompareTest(ExactFraction.ZERO, 0, 0) // eq
    runMultiTypeCompareTest(ExactFraction(100, 3), 100, -1) // lt
    runMultiTypeCompareTest(ExactFraction(3, 2), 0, 1) // gt
    runMultiTypeCompareTest(ExactFraction(3), 2, 1) // gt
    runMultiTypeCompareTest(ExactFraction(-3, 4), -2, 1) // lt
}

/**
 * Run test with Int, Long, and BigInteger values
 *
 * @param ef [ExactFraction]: first value in comparison
 * @param other [Int]: value to cast to Int, Long, and BigInteger
 * @param expected [Int]: expected result
 */
private fun runMultiTypeCompareTest(ef: ExactFraction, other: Int, expected: Int) {
    when (expected) {
        -1 -> {
            assertTrue {
                ef < other &&
                    ef < other.toLong() &&
                    ef < other.toBigInteger()
            }
        }
        1 -> {
            assertTrue {
                ef > other &&
                    ef > other.toLong() &&
                    ef > other.toBigInteger()
            }
        }
        0 -> {
            assertTrue {
                ef.compareTo(other) == 0 &&
                    ef.compareTo(other.toLong()) == 0 &&
                    ef.compareTo(other.toBigInteger()) == 0
            }
        }
    }
}
