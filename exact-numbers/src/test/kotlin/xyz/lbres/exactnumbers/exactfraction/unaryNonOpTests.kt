package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.exactnumbers.testutils.assertDivByZero
import java.math.BigInteger
import java.math.RoundingMode
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runInverseTests() {
    var ef = ExactFraction(1, 2)
    var expected = ExactFraction(2)
    assertEquals(expected, ef.inverse())

    ef = ExactFraction(2)
    expected = ExactFraction(1, 2)
    assertEquals(expected, ef.inverse())

    ef = ExactFraction(-3, 2)
    expected = ExactFraction(-2, 3)
    assertEquals(expected, ef.inverse())

    ef = ExactFraction(-3, -9)
    expected = ExactFraction(3)
    assertEquals(expected, ef.inverse())

    ef = ExactFraction(19, 7)
    expected = ExactFraction(7, 19)
    assertEquals(expected, ef.inverse())

    assertDivByZero { ExactFraction.ZERO.inverse() }
}

fun runAbsoluteValueTests() {
    var ef = ExactFraction(0)
    var expected = ExactFraction(0)
    assertEquals(expected, ef.absoluteValue())

    ef = ExactFraction(3)
    expected = ExactFraction(3)
    assertEquals(expected, ef.absoluteValue())

    ef = ExactFraction(-3)
    expected = ExactFraction(3)
    assertEquals(expected, ef.absoluteValue())

    ef = ExactFraction(3, 5)
    expected = ExactFraction(3, 5)
    assertEquals(expected, ef.absoluteValue())

    ef = ExactFraction(-5, 3)
    expected = ExactFraction(5, 3)
    assertEquals(expected, ef.absoluteValue())
}

fun runIsNegativeTests() {
    var ef = ExactFraction(0)
    assertFalse(ef.isNegative())

    ef = ExactFraction(1)
    assertFalse(ef.isNegative())

    ef = ExactFraction(2, 7)
    assertFalse(ef.isNegative())

    ef = ExactFraction(7, 2)
    assertFalse(ef.isNegative())

    ef = ExactFraction(-1)
    assertTrue(ef.isNegative())

    ef = ExactFraction(-2, 7)
    assertTrue(ef.isNegative())

    ef = ExactFraction(7, -2)
    assertTrue(ef.isNegative())
}

fun runIsZeroTests() {
    var ef = ExactFraction(0)
    assertTrue(ef.isZero())

    ef = ExactFraction(1)
    assertFalse(ef.isZero())

    ef = ExactFraction(2, 7)
    assertFalse(ef.isZero())

    ef = ExactFraction(-1)
    assertFalse(ef.isZero())

    ef = ExactFraction(-2, 7)
    assertFalse(ef.isZero())

    ef = ExactFraction(2, -7)
    assertFalse(ef.isZero())
}

fun runRoundToWholeTests() {
    // whole
    var ef = ExactFraction.ZERO
    assertEquals(ef, ef.roundToWhole())

    ef = ExactFraction.ONE
    assertEquals(ef, ef.roundToWhole())

    ef = ExactFraction(BigInteger("1000000000000000000000"))
    assertEquals(ef, ef.roundToWhole())

    ef = ExactFraction(-123)
    assertEquals(ef, ef.roundToWhole())

    // up
    ef = ExactFraction.HALF
    var expected = ExactFraction.ONE
    assertEquals(expected, ef.roundToWhole())

    ef = ExactFraction(-1, 3)
    expected = ExactFraction.ZERO
    assertEquals(expected, ef.roundToWhole())

    ef = ExactFraction(5, 3)
    expected = ExactFraction.TWO
    assertEquals(expected, ef.roundToWhole())

    ef = ExactFraction(-4, 3)
    expected = ExactFraction.NEG_ONE
    assertEquals(expected, ef.roundToWhole())

    ef = ExactFraction(200000, 17)
    expected = ExactFraction(11765)
    assertEquals(expected, ef.roundToWhole())

    // down
    ef = ExactFraction(1, 10)
    expected = ExactFraction.ZERO
    assertEquals(expected, ef.roundToWhole())

    ef = ExactFraction(-9, 10)
    expected = ExactFraction.NEG_ONE
    assertEquals(expected, ef.roundToWhole())
    assertEquals(expected, ef.roundToWhole())

    ef = ExactFraction(17, 100000)
    expected = ExactFraction.ZERO
    assertEquals(expected, ef.roundToWhole())

    ef = ExactFraction(-5, 3)
    expected = -ExactFraction.TWO
    assertEquals(expected, ef.roundToWhole())

    ef = ExactFraction(100000, 17)
    expected = ExactFraction(5882)
    assertEquals(expected, ef.roundToWhole())

    ef = ExactFraction(BigInteger("1000000000000000000000"), 3)
    expected = ExactFraction(BigInteger("333333333333333333333"))
    assertEquals(expected, ef.roundToWhole())

    // other rounding modes
    ef = ExactFraction(1, 3)
    expected = ExactFraction.ONE
    assertEquals(expected, ef.roundToWhole(RoundingMode.UP))

    ef = ExactFraction(15, 2)
    expected = ExactFraction.SEVEN
    assertEquals(expected, ef.roundToWhole(RoundingMode.HALF_DOWN))

    ef = ExactFraction("9.99999999999")
    expected = ExactFraction.NINE
    assertEquals(expected, ef.roundToWhole(RoundingMode.DOWN))
}

fun runIsWholeNumberTests() {
    // whole
    var ef = ExactFraction.ZERO
    assertTrue(ef.isWholeNumber())

    ef = ExactFraction(100, 100)
    assertTrue(ef.isWholeNumber())

    ef = ExactFraction(-123456789)
    assertTrue(ef.isWholeNumber())

    // fraction
    ef = ExactFraction.HALF
    assertFalse(ef.isWholeNumber())

    ef = ExactFraction(6, 7)
    assertFalse(ef.isWholeNumber())

    ef = ExactFraction(-1000, 999)
    assertFalse(ef.isWholeNumber())
}
