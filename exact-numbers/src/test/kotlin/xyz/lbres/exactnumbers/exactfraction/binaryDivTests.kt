package xyz.lbres.exactnumbers.exactfraction

import java.math.BigInteger
import kotlin.test.assertEquals
import xyz.lbres.exactnumbers.testutils.assertDivByZero

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
