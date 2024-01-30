package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.exactnumbers.ext.toExactFraction
import xyz.lbres.exactnumbers.testutils.assertDivByZero
import java.math.BigInteger
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

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
    var base = ExactFraction.NINE
    var exp = ExactFraction.ZERO
    var expected = ExactFraction.ONE
    assertEquals(expected, base.pow(exp))

    base = ExactFraction.NINE
    exp = ExactFraction.ONE
    expected = ExactFraction.NINE
    assertEquals(expected, base.pow(exp))

    base = ExactFraction.ONE
    exp = 1000000.toExactFraction()
    expected = ExactFraction.ONE
    assertEquals(expected, base.pow(exp))

    base = ExactFraction.NEG_ONE
    exp = 1000000.toExactFraction()
    expected = ExactFraction.ONE
    assertEquals(expected, base.pow(exp))

    base = ExactFraction.NEG_ONE
    exp = 1000001.toExactFraction()
    expected = ExactFraction.NEG_ONE
    assertEquals(expected, base.pow(exp))

    // exp > 0
    base = ExactFraction.EIGHT
    exp = ExactFraction.ONE
    expected = ExactFraction.EIGHT
    assertEquals(expected, base.pow(exp))

    base = 23.toExactFraction()
    exp = ExactFraction.FOUR
    expected = 279841.toExactFraction()
    assertEquals(expected, base.pow(exp))

    base = -ExactFraction.TWO
    exp = 20.toExactFraction()
    expected = 1048576.toExactFraction()
    assertEquals(expected, base.pow(exp))

    base = -ExactFraction.TWO
    exp = ExactFraction.SEVEN
    expected = (-128).toExactFraction()
    assertEquals(expected, base.pow(exp))

    base = ExactFraction.ONE
    exp = ExactFraction("3147483647") // bigger than int max
    expected = ExactFraction.ONE
    assertEquals(expected, base.pow(exp)) // tests that large exponent doesn't throw

    base = ExactFraction(3, 8)
    exp = ExactFraction.THREE
    expected = ExactFraction(27, 512)
    assertEquals(expected, base.pow(exp))

    base = ExactFraction(-2, 5)
    exp = ExactFraction.NINE
    expected = ExactFraction(-512, 1953125)
    assertEquals(expected, base.pow(exp))

    // exp < 0
    base = ExactFraction.EIGHT
    exp = ExactFraction.NEG_ONE
    expected = ExactFraction(1, 8)
    assertEquals(expected, base.pow(exp))

    base = 23.toExactFraction()
    exp = -ExactFraction.FOUR
    expected = ExactFraction(1, 279841)
    assertEquals(expected, base.pow(exp))

    base = -ExactFraction.TWO
    exp = (-20).toExactFraction()
    expected = ExactFraction(1, 1048576)
    assertEquals(expected, base.pow(exp))

    base = -ExactFraction.TWO
    exp = -ExactFraction.SEVEN
    expected = ExactFraction(-1, 128)
    assertEquals(expected, base.pow(exp))

    base = ExactFraction.ONE
    exp = ExactFraction("-3147483647") // smaller than int min
    expected = ExactFraction.ONE
    assertEquals(expected, base.pow(exp)) // tests that it doesn't throw, can't do much else

    base = ExactFraction(3, 8)
    exp = -ExactFraction.THREE
    expected = ExactFraction(512, 27)
    assertEquals(expected, base.pow(exp))

    base = ExactFraction(-2, 5)
    exp = -ExactFraction.NINE
    expected = ExactFraction(1953125, -512)
    assertEquals(expected, base.pow(exp))

    // non-whole
    val expectedError = "Exponents must be whole numbers"
    base = ExactFraction.FOUR
    exp = ExactFraction(1, 2)
    assertFailsWith<ArithmeticException>(expectedError) { base.pow(exp) }

    exp = ExactFraction(-8, 5)
    assertFailsWith<ArithmeticException>(expectedError) { base.pow(exp) }

    base = ExactFraction(3, 7)
    exp = ExactFraction(3, 7)
    assertFailsWith<ArithmeticException>(expectedError) { base.pow(exp) }
}

/**
 * Run times test with Int, Long, and BigInteger values
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
 * Run div test with Int, Long, and BigInteger values
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
