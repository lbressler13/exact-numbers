package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.exactnumbers.testutils.assertDivByZero
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
