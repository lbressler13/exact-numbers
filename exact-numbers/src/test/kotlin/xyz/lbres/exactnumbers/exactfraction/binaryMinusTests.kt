package xyz.lbres.exactnumbers.exactfraction

import kotlin.test.assertEquals

fun runMinusTests() {
    // zero
    var first = ExactFraction(0)
    var second = ExactFraction(0)
    assertEquals(ExactFraction(0), first - second)

    first = ExactFraction(4)
    second = ExactFraction(0)
    assertEquals(ExactFraction(4), first - second)

    first = ExactFraction(-4)
    second = ExactFraction(0)
    assertEquals(ExactFraction(-4), first - second)

    first = ExactFraction(0)
    second = ExactFraction(4)
    assertEquals(ExactFraction(-4), first - second)

    first = ExactFraction(0)
    second = ExactFraction(-4)
    assertEquals(ExactFraction(4), first - second)

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

    first = ExactFraction(59)
    second = ExactFraction(119)
    assertEquals(ExactFraction(-60), first - second)

    first = ExactFraction(-249)
    second = ExactFraction(-329)
    assertEquals(ExactFraction(80), first - second)

    // different denominator
    first = ExactFraction(5)
    second = ExactFraction(-4, 3)
    assertEquals(ExactFraction(19, 3), first - second)

    first = ExactFraction(5, 2)
    second = ExactFraction(7, 3)
    assertEquals(ExactFraction(1, 6), first - second)

    first = ExactFraction(-52)
    second = ExactFraction(31)
    assertEquals(ExactFraction(-83), first - second)

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
 * @param ef [ExactFraction]: first value in subtraction
 * @param other [Int]: value to cast to Int, Long, and BigInteger
 * @param expected [ExactFraction]: expected result
 */
private fun runMultiTypeMinusTest(ef: ExactFraction, other: Int, expected: ExactFraction) {
    assertEquals(expected, ef - other)
    assertEquals(expected, ef - other.toLong())
    assertEquals(expected, ef - other.toBigInteger())
}
