package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.exactnumbers.testutils.assertFailsWithMessage
import xyz.lbres.exactnumbers.testutils.assertSucceeds
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private const val successError = "Computation expected to succeed"

fun runCommonPowTests(powFn: (ExactFraction, ExactFraction) -> ExactFraction) {
    // zero/one
    var base = ExactFraction.NINE
    var exp = ExactFraction.ZERO
    var expected = ExactFraction.ONE
    assertEquals(expected, powFn(base, exp))

    base = ExactFraction.ZERO
    exp = ExactFraction.ZERO
    expected = ExactFraction.ONE
    assertEquals(expected, powFn(base, exp))

    base = ExactFraction.ZERO
    exp = ExactFraction.NINE
    expected = ExactFraction.ZERO
    assertEquals(expected, powFn(base, exp))

    base = ExactFraction.NINE
    exp = ExactFraction.ONE
    expected = ExactFraction.NINE
    assertEquals(expected, powFn(base, exp))

    base = ExactFraction.ONE
    exp = ExactFraction(1000000)
    expected = ExactFraction.ONE
    assertEquals(expected, powFn(base, exp))

    base = ExactFraction.NEG_ONE
    exp = ExactFraction(1000000)
    expected = ExactFraction.ONE
    assertEquals(expected, powFn(base, exp))

    base = ExactFraction.NEG_ONE
    exp = ExactFraction(1000001)
    expected = ExactFraction.NEG_ONE
    assertEquals(expected, powFn(base, exp))

    // exp > 0
    base = ExactFraction.EIGHT
    exp = ExactFraction.ONE
    expected = ExactFraction.EIGHT
    assertEquals(expected, powFn(base, exp))

    base = ExactFraction(23)
    exp = ExactFraction.FOUR
    expected = ExactFraction(279841)
    assertEquals(expected, powFn(base, exp))

    base = -ExactFraction.TWO
    exp = ExactFraction(20)
    expected = ExactFraction(1048576)
    assertEquals(expected, powFn(base, exp))

    base = ExactFraction.ONE
    exp = ExactFraction("3147483647") // bigger than int max
    expected = ExactFraction.ONE
    assertEquals(expected, powFn(base, exp))

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

    base = ExactFraction(23)
    exp = -ExactFraction.FOUR
    expected = ExactFraction(1, 279841)
    assertEquals(expected, powFn(base, exp))

    base = -ExactFraction.TWO
    exp = ExactFraction(-20)
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
    runLargeExponentTests(powFn) // TODO

    // fractional exponent
    val expectedError = "Exponents must be whole numbers"
    base = ExactFraction.FOUR
    exp = ExactFraction(1, 2)
    assertFailsWithMessage<ArithmeticException>(expectedError) { powFn(base, exp) }

    exp = ExactFraction(-8, 5)
    assertFailsWithMessage<ArithmeticException>(expectedError) { powFn(base, exp) }

    base = ExactFraction(3, 7)
    exp = ExactFraction(3, 7)
    assertFailsWithMessage<ArithmeticException>(expectedError) { powFn(base, exp) }
}

private fun runLargeExponentTests(powFn: (ExactFraction, ExactFraction) -> ExactFraction) {
    var base = ExactFraction.TWO
    var exp = ExactFraction(6666666)
    assertSucceeds(successError) { powFn(base, exp) }

    base = -ExactFraction.TWO
    exp = ExactFraction(6666666)
    assertSucceeds(successError) { powFn(base, exp) }

    base = ExactFraction(59)
    exp = ExactFraction(1000000)
    assertSucceeds(successError) { powFn(base, exp) }

    base = ExactFraction.ONE
    exp = ExactFraction("-9999999999999999999999999999999999999")
    assertSucceeds(successError) { powFn(base, exp) }

    base = ExactFraction.TWO
    exp = ExactFraction(999999999999)
    assertFailsWith<ExactFractionOverflowException> { powFn(base, exp) }

    base = ExactFraction.HALF
    exp = ExactFraction(999999999999)
    assertFailsWith<ExactFractionOverflowException> { powFn(base, exp) }
}
