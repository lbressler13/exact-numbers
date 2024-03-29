package xyz.lbres.exactnumbers.irrationals.sqrt

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.testutils.TestNumber
import xyz.lbres.exactnumbers.testutils.assertDivByZero
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

private val one = ExactFraction.ONE

fun runTimesTests() {
    // zero
    assertEquals(Term.ZERO, Sqrt.ZERO * Sqrt.ONE)
    assertEquals(Term.ZERO, Sqrt.ONE * Log.ZERO)
    assertEquals(Term.ZERO, Sqrt.ONE * ExactFraction.ZERO)
    assertEquals(Term.ZERO, Sqrt.ONE * TestNumber(ExactFraction.ZERO))

    // sqrt only
    var sqrt1 = Sqrt(100)
    var sqrt2 = Sqrt(ExactFraction(1, 100))
    var expected = Term.fromValues(one, listOf(sqrt1, sqrt2))
    assertEquals(expected, sqrt1 * sqrt2)

    sqrt1 = Sqrt(ExactFraction(99, 5))
    sqrt2 = Sqrt(ExactFraction(27, 17))
    expected = Term.fromValues(one, listOf(sqrt1, sqrt2))
    assertEquals(expected, sqrt1 * sqrt2)

    // exact fraction
    sqrt1 = Sqrt(111)
    var ef = ExactFraction(-5)
    expected = Term.fromValues(ef, listOf(sqrt1))
    assertEquals(expected, sqrt1 * ef)

    sqrt1 = Sqrt(ExactFraction(9, 100))
    ef = ExactFraction(100, 213)
    expected = Term.fromValues(ef, listOf(sqrt1))
    assertEquals(expected, sqrt1 * ef)

    // log
    sqrt1 = Sqrt(ExactFraction(16, 169))
    var log = Log(ExactFraction(25), 4)
    expected = Term.fromValues(one, listOf(log, sqrt1))
    assertEquals(expected, sqrt1 * log)

    sqrt1 = Sqrt(155)
    log = Log(100)
    expected = Term.fromValues(one, listOf(log, sqrt1))
    assertEquals(expected, sqrt1 * log)

    // pi
    sqrt1 = Sqrt(110)
    var pi = Pi()
    expected = Term.fromValues(one, listOf(sqrt1, pi))
    assertEquals(expected, sqrt1 * pi)

    sqrt1 = Sqrt(ExactFraction(92, 11))
    pi = Pi().inverse()
    expected = Term.fromValues(one, listOf(sqrt1, pi))
    assertEquals(expected, sqrt1 * pi)

    // other
    sqrt1 = Sqrt(110)
    val testNumber = TestNumber(ExactFraction(18))
    expected = Term.fromValues(one, listOf(sqrt1, testNumber))
    assertEquals(expected, sqrt1 * testNumber)
}

fun runDivTests() {
    // zero
    assertDivByZero { Sqrt.ONE / Sqrt.ZERO }
    assertDivByZero { Sqrt.ONE / Log.ZERO }
    assertDivByZero { Sqrt.ONE / ExactFraction.ZERO }
    assertDivByZero { Sqrt.ONE / TestNumber(ExactFraction.ZERO) }

    // sqrt only
    var sqrt1 = Sqrt(100)
    var sqrt2 = Sqrt(ExactFraction(1, 100))
    var expected = Term.fromValues(one, listOf(sqrt1, sqrt2.inverse()))
    assertEquals(expected, sqrt1 / sqrt2)

    sqrt1 = Sqrt(ExactFraction(99, 5))
    sqrt2 = Sqrt(ExactFraction(27, 17))
    expected = Term.fromValues(one, listOf(sqrt1, sqrt2.inverse()))
    assertEquals(expected, sqrt1 / sqrt2)

    // exact fraction
    sqrt1 = Sqrt(111)
    var ef = ExactFraction(-5)
    expected = Term.fromValues(ExactFraction(-1, 5), listOf(sqrt1))
    assertEquals(expected, sqrt1 / ef)

    sqrt1 = Sqrt(ExactFraction(9, 100))
    ef = ExactFraction(100, 213)
    expected = Term.fromValues(ef.inverse(), listOf(sqrt1))
    assertEquals(expected, sqrt1 / ef)

    // log
    sqrt1 = Sqrt(ExactFraction(16, 169))
    var log = Log(ExactFraction(25), 4).inverse()
    expected = Term.fromValues(one, listOf(log.inverse(), sqrt1))
    assertEquals(expected, sqrt1 / log)

    sqrt1 = Sqrt(155)
    log = Log(100)
    expected = Term.fromValues(one, listOf(log.inverse(), sqrt1))
    assertEquals(expected, sqrt1 / log)

    // pi
    sqrt1 = Sqrt(110)
    var pi = Pi()
    expected = Term.fromValues(one, listOf(sqrt1, Pi().inverse()))
    assertEquals(expected, sqrt1 / pi)

    sqrt1 = Sqrt(ExactFraction(92, 11))
    pi = Pi().inverse()
    expected = Term.fromValues(one, listOf(sqrt1, Pi()))
    assertEquals(expected, sqrt1 / pi)

    // other
    sqrt1 = Sqrt(110)
    val testNumber = TestNumber(ExactFraction(18))
    expected = Term.fromValues(one, listOf(sqrt1, testNumber.inverse()))
    assertEquals(expected, sqrt1 / testNumber)
}

fun runCompareToTests() {
    // equal
    var sqrt1 = Sqrt.ZERO
    assertEquals(0, sqrt1.compareTo(sqrt1))

    sqrt1 = Sqrt(ExactFraction(9, 11))
    assertEquals(0, sqrt1.compareTo(sqrt1))

    // not equal
    sqrt1 = Sqrt.ZERO
    var sqrt2 = Sqrt.ONE
    assertTrue(sqrt1 < sqrt2)
    assertTrue(sqrt2 > sqrt1)

    sqrt1 = Sqrt(3)
    sqrt2 = Sqrt(9)
    assertTrue(sqrt1 < sqrt2)
    assertTrue(sqrt2 > sqrt1)

    sqrt1 = Sqrt(ExactFraction(15, 26))
    sqrt2 = Sqrt(ExactFraction(26, 15))
    assertTrue(sqrt1 < sqrt2)
    assertTrue(sqrt2 > sqrt1)
}

fun runEqualsTests() {
    // equal
    var sqrt1 = Sqrt.ZERO
    assertEquals(sqrt1, sqrt1)

    sqrt1 = Sqrt(6)
    assertEquals(sqrt1, sqrt1)

    sqrt1 = Sqrt(ExactFraction(9, 400))
    assertEquals(sqrt1, sqrt1)

    sqrt1 = Sqrt(ExactFraction(400, 9))
    assertEquals(sqrt1, sqrt1)

    // not equal
    sqrt1 = Sqrt.ZERO
    var sqrt2 = Sqrt.ONE
    assertNotEquals(sqrt1, sqrt2)
    assertNotEquals(sqrt2, sqrt1)

    sqrt1 = Sqrt(2)
    sqrt2 = Sqrt(ExactFraction.HALF)
    assertNotEquals(sqrt1, sqrt2)
    assertNotEquals(sqrt2, sqrt1)

    sqrt1 = Sqrt(ExactFraction(9, 25))
    sqrt2 = Sqrt(ExactFraction.NINE)
    assertNotEquals(sqrt1, sqrt2)
    assertNotEquals(sqrt2, sqrt1)

    sqrt1 = Sqrt(ExactFraction(103, 422))
    sqrt2 = Sqrt(ExactFraction(90, 37))
    assertNotEquals(sqrt1, sqrt2)
    assertNotEquals(sqrt2, sqrt1)
}
