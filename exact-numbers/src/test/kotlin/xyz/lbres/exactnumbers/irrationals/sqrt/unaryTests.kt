package xyz.lbres.exactnumbers.irrationals.sqrt

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.testutils.assertDivByZero
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

fun runInverseTests() {
    // error
    assertDivByZero { Sqrt.ZERO.inverse() }

    // no error
    var sqrt = Sqrt(8)
    var expected = Sqrt(ExactFraction(1, 8))
    assertEquals(expected, sqrt.inverse())

    sqrt = Sqrt(ExactFraction.HALF)
    expected = Sqrt(2)
    assertEquals(expected, sqrt.inverse())

    sqrt = Sqrt(ExactFraction(100, 49))
    expected = Sqrt(ExactFraction(49, 100))
    assertEquals(expected, sqrt.inverse())

    sqrt = Sqrt(ExactFraction(1, 8))
    expected = Sqrt(8)
    assertEquals(expected, sqrt.inverse())
}

fun runIsZeroTests() {
    // zero
    var sqrt = Sqrt.ZERO
    assertTrue(sqrt.isZero())

    // not zero
    sqrt = Sqrt.ONE
    assertFalse(sqrt.isZero())

    sqrt = Sqrt(ExactFraction(15, 1234))
    assertFalse(sqrt.isZero())

    sqrt = Sqrt(ExactFraction(12000, 179))
    assertFalse(sqrt.isZero())

    sqrt = Sqrt(2)
    assertFalse(sqrt.isZero())
}

fun runIsRationalTests() {
    // rational
    var sqrt = Sqrt.ZERO
    assertTrue(sqrt.isRational())

    sqrt = Sqrt.ONE
    assertTrue(sqrt.isRational())

    sqrt = Sqrt(961)
    assertTrue(sqrt.isRational())

    sqrt = Sqrt(ExactFraction(1, 64))
    assertTrue(sqrt.isRational())

    sqrt = Sqrt(ExactFraction(81, 49))
    assertTrue(sqrt.isRational())

    // irrational
    sqrt = Sqrt(2)
    assertFalse(sqrt.isRational())

    sqrt = Sqrt(ExactFraction(1, 35))
    assertFalse(sqrt.isRational())

    sqrt = Sqrt(ExactFraction(49, 10))
    assertFalse(sqrt.isRational())
}

fun runGetRationalValueTests() {
    // irrational
    var sqrt = Sqrt(2)
    assertNull(sqrt.getRationalValue())

    sqrt = Sqrt(ExactFraction(64, 15))
    assertNull(sqrt.getRationalValue())

    sqrt = Sqrt(155)
    assertNull(sqrt.getRationalValue())

    // rational
    sqrt = Sqrt.ZERO
    var expected = ExactFraction.ZERO
    assertEquals(expected, sqrt.getRationalValue())

    sqrt = Sqrt.ONE
    expected = ExactFraction.ONE
    assertEquals(expected, sqrt.getRationalValue())

    sqrt = Sqrt(2209)
    expected = ExactFraction(47)
    assertEquals(expected, sqrt.getRationalValue())

    sqrt = Sqrt(ExactFraction(1, 4))
    expected = ExactFraction.HALF
    assertEquals(expected, sqrt.getRationalValue())

    sqrt = Sqrt(ExactFraction(1, 100))
    expected = ExactFraction(1, 10)
    assertEquals(expected, sqrt.getRationalValue())

    sqrt = Sqrt(ExactFraction(81, 64))
    expected = ExactFraction(9, 8)
    assertEquals(expected, sqrt.getRationalValue())
}

fun runGetValueTests() {
    var sqrt = Sqrt.ZERO
    var expected = BigDecimal.ZERO
    assertEquals(expected, sqrt.getValue())

    sqrt = Sqrt.ONE
    expected = BigDecimal.ONE
    assertEquals(expected, sqrt.getValue())

    sqrt = Sqrt(144)
    expected = BigDecimal("12")
    assertEquals(expected, sqrt.getValue())

    sqrt = Sqrt(ExactFraction(1, 36))
    expected = BigDecimal("0.16666666666666666667")
    assertEquals(expected, sqrt.getValue())

    sqrt = Sqrt(ExactFraction(25, 16))
    expected = BigDecimal("1.25")
    assertEquals(expected, sqrt.getValue())

    sqrt = Sqrt(7)
    expected = BigDecimal("2.6457513110645905905")
    assertEquals(expected, sqrt.getValue())

    sqrt = Sqrt(32)
    expected = BigDecimal("5.6568542494923801952")
    assertEquals(expected, sqrt.getValue())

    sqrt = Sqrt(ExactFraction(17, 49))
    expected = BigDecimal("0.58901508937395150711")
    assertEquals(expected, sqrt.getValue())
}
