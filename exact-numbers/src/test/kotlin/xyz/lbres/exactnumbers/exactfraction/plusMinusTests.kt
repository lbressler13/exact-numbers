package xyz.lbres.exactnumbers.exactfraction

import kotlin.test.assertEquals

fun runPlusTests() {
    runCommonPlusTests(ExactFraction::plus)

    // other number types
    runMultiTypePlusTest(ExactFraction(0), 3, ExactFraction(3))
    runMultiTypePlusTest(ExactFraction(4, 7), 12, ExactFraction(88, 7))
    runMultiTypePlusTest(ExactFraction(-20, 7), 3, ExactFraction(1, 7))
    runMultiTypePlusTest(ExactFraction(-12, 5), 1, ExactFraction(-7, 5))
}

fun runMinusTests() {
    // zero
    var first = ExactFraction(0)
    var second = ExactFraction(0)
    assertEquals(ExactFraction(0), first - second)

    first = ExactFraction(4)
    second = ExactFraction(0)
    assertEquals(ExactFraction(4), first - second)
    assertEquals(ExactFraction(-4), second - first)

    first = ExactFraction(-4)
    second = ExactFraction(0)
    assertEquals(ExactFraction(-4), first - second)
    assertEquals(ExactFraction(4), second - first)

    // whole numbers
    first = ExactFraction(4)
    second = ExactFraction(1)
    assertEquals(ExactFraction(3), first - second)

    first = ExactFraction(-12)
    second = ExactFraction(33)
    assertEquals(ExactFraction(-45), first - second)

    first = ExactFraction(12)
    second = ExactFraction(-33)
    assertEquals(ExactFraction(45), first - second)

    first = ExactFraction(-12)
    second = ExactFraction(-6)
    assertEquals(ExactFraction(-6), first - second)

    // same denominator
    first = ExactFraction(5, 3)
    second = ExactFraction(-6, 3)
    assertEquals(ExactFraction(11, 3), first - second)

    first = ExactFraction(-5, 3)
    second = ExactFraction(2, 3)
    assertEquals(ExactFraction(-7, 3), first - second)

    first = ExactFraction(5, 19)
    second = ExactFraction(11, 19)
    assertEquals(ExactFraction(-6, 19), first - second)

    first = ExactFraction(-24, 19)
    second = ExactFraction(-32, 19)
    assertEquals(ExactFraction(8, 19), first - second)

    // different denominator
    first = ExactFraction(5)
    second = ExactFraction(-4, 3)
    assertEquals(ExactFraction(19, 3), first - second)

    first = ExactFraction(5, 2)
    second = ExactFraction(7, 3)
    assertEquals(ExactFraction(1, 6), first - second)

    first = ExactFraction(-5, 12)
    second = ExactFraction(3, 11)
    assertEquals(ExactFraction(-91, 132), first - second)

    first = ExactFraction(-4, 8)
    second = ExactFraction(-1, 3)
    assertEquals(ExactFraction(-4, 24), first - second)

    // other number types
    runMultiTypeMinusTest(ExactFraction(0), 3, ExactFraction(-3))
    runMultiTypeMinusTest(ExactFraction(-6), 20, ExactFraction(-26))
    runMultiTypeMinusTest(ExactFraction(3), 5, ExactFraction(-2L))
    runMultiTypeMinusTest(ExactFraction(3, 7), 4, ExactFraction(-25, 7))
}

/**
 * Run test with Int, Long, and BigInteger values
 *
 * @param ef [ExactFraction]: first value in addition
 * @param other [Int]: value to cast to Int, Long, and BigInteger
 * @param expected [ExactFraction]: expected result
 */
private fun runMultiTypePlusTest(ef: ExactFraction, other: Int, expected: ExactFraction) {
    assertEquals(expected, ef + other)
    assertEquals(expected, ef + other.toLong())
    assertEquals(expected, ef + other.toBigInteger())
}

/**
 * Run test with Int, Long, and BigInteger values
 *
 * @param ef [ExactFraction]: first value in subtraction
 * @param other [Int]: value to cast to Int, Long, and BigInteger
 * @param expected [ExactFraction]: expected result
 */
private fun runMultiTypeMinusTest(ef: ExactFraction, other: Int, expected: ExactFraction) {
    assertEquals(expected, ef - other)
    assertEquals(expected, ef - other.toLong())
    assertEquals(expected, ef - other.toBigInteger())
}
