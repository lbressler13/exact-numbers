package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.exactnumbers.testutils.assertDivByZero
import java.math.BigInteger
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

fun runDivTests() {
    // 0
    var first = ExactFraction(0)
    var second = ExactFraction(2, 3)
    assertEquals(ExactFraction(0), first / second)

    first = ExactFraction(1)
    second = ExactFraction(0)
    assertDivByZero { first / second }

    first = ExactFraction(0)
    second = ExactFraction(0)
    assertDivByZero { first / second }

    // whole numbers
    first = ExactFraction(8)
    second = ExactFraction(2)
    assertEquals(ExactFraction(4), first / second)

    first = ExactFraction(2)
    second = ExactFraction(8)
    assertEquals(ExactFraction(1, 4), first / second)

    first = ExactFraction(-7)
    second = ExactFraction(9)
    assertEquals(ExactFraction(-7, 9), first / second)

    first = ExactFraction(-7)
    second = ExactFraction(-9)
    assertEquals(ExactFraction(7, 9), first / second)

    first = ExactFraction(9)
    second = ExactFraction(-7)
    assertEquals(ExactFraction(-9, 7), first / second)

    // fractions
    first = ExactFraction(9, 2)
    second = ExactFraction(3, 7)
    assertEquals(ExactFraction(63, 6), first / second)

    first = ExactFraction(3, 2)
    second = ExactFraction(3, 2)
    assertEquals(ExactFraction(1), first / second)

    first = ExactFraction(3, 2)
    second = ExactFraction(3, -2)
    assertEquals(ExactFraction(-1), first / second)

    first = ExactFraction(-2, 13)
    second = ExactFraction(-4, 5)
    assertEquals(ExactFraction(10, 52), first / second)

    first = ExactFraction(-3, 10)
    second = ExactFraction(3, 2)
    assertEquals(ExactFraction(-1, 5), first / second)

    first = ExactFraction(3, 10)
    second = ExactFraction(-3, 2)
    assertEquals(ExactFraction(-1, 5), first / second)

    // other number types
    runMultiTypeDivTest(ExactFraction(6), 3, ExactFraction(2))
    runMultiTypeDivTest(ExactFraction(-6, 7), 4, ExactFraction(-6, 28))
    runMultiTypeDivTest(ExactFraction(9, 4), 3, ExactFraction(3, 4))

    assertDivByZero { ExactFraction.ONE / 0 }
    assertDivByZero { ExactFraction.ONE / 0L }
    assertDivByZero { ExactFraction.ONE / BigInteger.ZERO }
}

fun runPowTests() {
    runCommonPowTests(ExactFraction::pow)

    // other number types
    runMultiTypePowTest(ExactFraction(0), 100, ExactFraction(0))
    runMultiTypePowTest(ExactFraction(12, 49), 0, ExactFraction(1))
    runMultiTypePowTest(ExactFraction(3, 8), -3, ExactFraction(512, 27))
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

/**
 * Run test with Int, Long, and BigInteger values
 *
 * @param ef [ExactFraction]: first value in division
 * @param other [Int]: value to cast to Int, Long, and BigInteger
 * @param expected [ExactFraction]: expected result
 */
private fun runMultiTypeDivTest(ef: ExactFraction, other: Int, expected: ExactFraction) {
    assertEquals(expected, ef / other)
    assertEquals(expected, ef / other.toLong())
    assertEquals(expected, ef / other.toBigInteger())
}

/**
 * Run test with Int, Long, and BigInteger values
 *
 * @param ef [ExactFraction]: base number
 * @param other [Int]: value to cast to Int, Long, and BigInteger
 * @param expected [ExactFraction]: expected result
 */
private fun runMultiTypePowTest(ef: ExactFraction, other: Int, expected: ExactFraction) {
    assertEquals(expected, ef.pow(other))
    assertEquals(expected, ef.pow(other.toLong()))
    assertEquals(expected, ef.pow(other.toBigInteger()))
}
