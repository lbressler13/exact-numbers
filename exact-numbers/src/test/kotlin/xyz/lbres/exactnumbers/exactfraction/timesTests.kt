package xyz.lbres.exactnumbers.exactfraction

import kotlin.test.assertEquals

fun runTimesTests() {
    runCommonTimesTests(ExactFraction::times)

    // other number types
    runMultiTypeTimesTest(ExactFraction(0), 3, ExactFraction(0))
    runMultiTypeTimesTest(ExactFraction(3), 0, ExactFraction(0))
    runMultiTypeTimesTest(ExactFraction(-5), 4, ExactFraction(-20))
    runMultiTypeTimesTest(ExactFraction(5, 3), 4, ExactFraction(20, 3))
    runMultiTypeTimesTest(ExactFraction(-5, 4), 4, ExactFraction(-5))
}

fun runEfTimesTests() {
    runCommonTimesTests { ef1, ef2 -> efTimes(ef1, ef2) }
}

private fun runCommonTimesTests(multiply: (ExactFraction, ExactFraction) -> ExactFraction) {
    // zero
    var first = ExactFraction(0)
    var second = ExactFraction(0)
    assertEquals(ExactFraction(0), multiply(first, second))

    first = ExactFraction(1)
    second = ExactFraction(0)
    assertEquals(ExactFraction(0), multiply(first, second))
    assertEquals(ExactFraction(0), multiply(second, first))

    // whole numbers
    first = ExactFraction(1)
    second = ExactFraction(8)
    assertEquals(ExactFraction(8), multiply(first, second))

    first = ExactFraction(7)
    second = ExactFraction(23)
    assertEquals(ExactFraction(161), multiply(first, second))

    first = ExactFraction(-17)
    second = ExactFraction(9)
    assertEquals(ExactFraction(-153), multiply(first, second))

    first = ExactFraction(-5)
    second = ExactFraction(-7)
    assertEquals(ExactFraction(35), multiply(first, second))

    // fractions
    first = ExactFraction(7, 11)
    second = ExactFraction(3, 12)
    assertEquals(ExactFraction(21, 132), multiply(first, second))

    first = ExactFraction(-1, 3)
    second = ExactFraction(-4, 12)
    assertEquals(ExactFraction(1, 9), multiply(first, second))

    first = ExactFraction(11, 3)
    second = ExactFraction(4, 3)
    assertEquals(ExactFraction(44, 9), multiply(first, second))

    first = ExactFraction(6, 4)
    second = ExactFraction(8, 3)
    assertEquals(ExactFraction(4), multiply(first, second))

    first = ExactFraction(-6, 7)
    second = ExactFraction(8, 3)
    assertEquals(ExactFraction(-48, 21), multiply(first, second))

    first = ExactFraction(6, 7)
    second = ExactFraction(-8, 3)
    assertEquals(ExactFraction(-48, 21), multiply(first, second))

    first = ExactFraction(6, 7)
    second = ExactFraction(7, 6)
    assertEquals(ExactFraction(1), multiply(first, second))
}

/**
 * Run test with Int, Long, and BigInteger values
 *
 * @param ef [ExactFraction]: first value in multiplication
 * @param other [Int]: value to cast to Int, Long, and BigInteger
 * @param expected [ExactFraction]: expected result
 */
private fun runMultiTypeTimesTest(ef: ExactFraction, other: Int, expected: ExactFraction) {
    assertEquals(expected, ef * other)
    assertEquals(expected, ef * other.toLong())
    assertEquals(expected, ef * other.toBigInteger())
}
