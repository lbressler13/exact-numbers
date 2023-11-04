package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.exactnumbers.ext.toExactFraction
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

fun runPowTests() {
    runCommonPowTests(ExactFraction::pow)

    // other number types
    runMultiTypePowTest(ExactFraction(0), 100, ExactFraction(0))
    runMultiTypePowTest(ExactFraction(12, 49), 0, ExactFraction(1))
    runMultiTypePowTest(ExactFraction(3, 8), -3, ExactFraction(512, 27))
}

fun runEfPowTests() {
    runCommonPowTests { ef1, ef2 -> efPow(ef1, ef2) }
}

private fun runCommonPowTests(powFn: (ExactFraction, ExactFraction) -> ExactFraction) {
    // zero/one
    var base = ExactFraction.NINE
    var exp = ExactFraction.ZERO
    var expected = ExactFraction.ONE
    assertEquals(expected, powFn(base, exp))

    base = ExactFraction.NINE
    exp = ExactFraction.ONE
    expected = ExactFraction.NINE
    assertEquals(expected, powFn(base, exp))

    base = ExactFraction.ONE
    exp = 1000000.toExactFraction()
    expected = ExactFraction.ONE
    assertEquals(expected, powFn(base, exp))

    base = ExactFraction.NEG_ONE
    exp = 1000000.toExactFraction()
    expected = ExactFraction.ONE
    assertEquals(expected, powFn(base, exp))

    base = ExactFraction.NEG_ONE
    exp = 1000001.toExactFraction()
    expected = ExactFraction.NEG_ONE
    assertEquals(expected, powFn(base, exp))

    // exp > 0
    base = ExactFraction.EIGHT
    exp = ExactFraction.ONE
    expected = ExactFraction.EIGHT
    assertEquals(expected, powFn(base, exp))

    base = 23.toExactFraction()
    exp = ExactFraction.FOUR
    expected = 279841.toExactFraction()
    assertEquals(expected, powFn(base, exp))

    base = -ExactFraction.TWO
    exp = 20.toExactFraction()
    expected = 1048576.toExactFraction()
    assertEquals(expected, powFn(base, exp))

    base = ExactFraction.ONE
    exp = ExactFraction("3147483647") // bigger than int max
    expected = ExactFraction.ONE
    assertEquals(expected, powFn(base, exp)) // tests that large exponent doesn't throw

    base = ExactFraction(3, 8)
    exp = ExactFraction.THREE
    expected = ExactFraction(27, 512)
    assertEquals(expected, powFn(base, exp))

    base = ExactFraction(-2, 5)
    exp = ExactFraction.NINE
    expected = ExactFraction(-512, 1953125)
    assertEquals(expected, powFn(base, exp))

    // exp < 0
    base = ExactFraction.EIGHT
    exp = ExactFraction.NEG_ONE
    expected = ExactFraction(1, 8)
    assertEquals(expected, powFn(base, exp))

    base = 23.toExactFraction()
    exp = -ExactFraction.FOUR
    expected = ExactFraction(1, 279841)
    assertEquals(expected, powFn(base, exp))

    base = -ExactFraction.TWO
    exp = (-20).toExactFraction()
    expected = ExactFraction(1, 1048576)
    assertEquals(expected, powFn(base, exp))

    base = ExactFraction.ONE
    exp = ExactFraction("-3147483647") // smaller than int min
    expected = ExactFraction.ONE
    assertEquals(expected, powFn(base, exp)) // tests that it doesn't throw, can't do much else

    base = ExactFraction(3, 8)
    exp = -ExactFraction.THREE
    expected = ExactFraction(512, 27)
    assertEquals(expected, powFn(base, exp))

    base = ExactFraction(-2, 5)
    exp = -ExactFraction.NINE
    expected = ExactFraction(1953125, -512)
    assertEquals(expected, powFn(base, exp))

    // large exponent
    runLargeExponentTests(powFn)

    // non-whole
    val expectedError = "Exponents must be whole numbers"
    base = ExactFraction.FOUR
    exp = ExactFraction(1, 2)
    assertFailsWith<ArithmeticException>(expectedError) { powFn(base, exp) }

    exp = ExactFraction(-8, 5)
    assertFailsWith<ArithmeticException>(expectedError) { powFn(base, exp) }

    base = ExactFraction(3, 7)
    exp = ExactFraction(3, 7)
    assertFailsWith<ArithmeticException>(expectedError) { powFn(base, exp) }
}

private fun runLargeExponentTests(powFn: (ExactFraction, ExactFraction) -> ExactFraction) {
    var base = ExactFraction.TWO
    var exp = ExactFraction(6666666)
    try {
        powFn(base, exp)
    } catch (_: Exception) {
        throw AssertionError("Computation expected to succeed")
    }

    base = -ExactFraction.TWO
    exp = ExactFraction(6666666)
    try {
        powFn(base, exp)
    } catch (_: Exception) {
        throw AssertionError("Computation expected to succeed")
    }

    base = ExactFraction(59)
    exp = ExactFraction(1000000)
    try {
        powFn(base, exp)
    } catch (_: Exception) {
        throw AssertionError("Computation expected to succeed")
    }

    base = ExactFraction.ONE
    exp = ExactFraction("-9999999999999999999999999999999999999")
    try {
        powFn(base, exp)
    } catch (_: Exception) {
        throw AssertionError("Computation expected to succeed")
    }

    base = ExactFraction.TWO
    exp = ExactFraction(999999999999)
    assertFailsWith<ExactFractionOverflowException> { powFn(base, exp) }

    base = ExactFraction.HALF
    exp = ExactFraction(999999999999)
    assertFailsWith<ExactFractionOverflowException> { powFn(base, exp) }
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
