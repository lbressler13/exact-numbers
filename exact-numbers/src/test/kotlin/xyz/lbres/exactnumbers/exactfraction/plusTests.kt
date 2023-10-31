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

fun runEfAddTests() {
    runCommonPlusTests { ef1, ef2 -> efAdd(ef1, ef2) }
}

private fun runCommonPlusTests(add: (ExactFraction, ExactFraction) -> ExactFraction) {
    // add zero
    var first = ExactFraction(0)
    var second = ExactFraction(0)
    assertEquals(ExactFraction(0), add(first, second))

    first = ExactFraction(4)
    second = ExactFraction(0)
    assertEquals(ExactFraction(4), add(first, second))
    assertEquals(ExactFraction(4), add(second, first))

    first = ExactFraction(0)
    second = ExactFraction(-4)
    assertEquals(ExactFraction(-4), add(first, second))
    assertEquals(ExactFraction(-4), add(second, first))

    // whole numbers
    first = ExactFraction(4)
    second = ExactFraction(1)
    assertEquals(ExactFraction(5), add(first, second))

    first = ExactFraction(-12)
    second = ExactFraction(33)
    assertEquals(ExactFraction(21), add(first, second))
    assertEquals(ExactFraction(21), add(second, first))

    first = ExactFraction(-12)
    second = ExactFraction(-6)
    assertEquals(ExactFraction(-18), add(first, second))

    // same denominator
    first = ExactFraction(5, 3)
    second = ExactFraction(-6, 3)
    assertEquals(ExactFraction(-1, 3), add(first, second))

    first = ExactFraction(5, 3)
    second = ExactFraction(-2, 3)
    assertEquals(ExactFraction(1), add(first, second))

    first = ExactFraction(5, 19)
    second = ExactFraction(11, 19)
    assertEquals(ExactFraction(16, 19), add(first, second))

    first = ExactFraction(24, 19)
    second = ExactFraction(32, 19)
    assertEquals(ExactFraction(56, 19), add(first, second))

    // different denominator
    first = ExactFraction(5)
    second = ExactFraction(-4, 3)
    assertEquals(ExactFraction(11, 3), add(first, second))

    first = ExactFraction(5, 2)
    second = ExactFraction(7, 3)
    assertEquals(ExactFraction(29, 6), add(first, second))

    first = ExactFraction(5, 12)
    second = ExactFraction(3, 11)
    assertEquals(ExactFraction(91, 132), add(first, second))

    first = ExactFraction(4, 8)
    second = ExactFraction(-1, 3)
    assertEquals(ExactFraction(1, 6), add(first, second))
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
