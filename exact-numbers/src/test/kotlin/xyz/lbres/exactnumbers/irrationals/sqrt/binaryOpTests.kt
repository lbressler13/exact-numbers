package xyz.lbres.exactnumbers.irrationals.sqrt

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.testutils.assertDivByZero
import xyz.lbres.expressions.term.Term
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

private val one = ExactFraction.ONE

fun runTimesTests() {
    // Log
    var sqrt1 = Sqrt(4)
    var log = Log.ZERO
    var expected = Term(one, listOf(Sqrt(4), Log.ZERO))
    assertEquals(expected, sqrt1 * log)

    sqrt1 = Sqrt(ExactFraction.HALF)
    log = Log(3, isDivided = true)
    expected = Term(one, listOf(Sqrt(ExactFraction.HALF), Log(3, isDivided = true)))
    assertEquals(expected, sqrt1 * log)

    // Pi
    sqrt1 = Sqrt(81)
    var pi = Pi()
    expected = Term(one, listOf(Sqrt(81), Pi()))
    assertEquals(expected, sqrt1 * pi)

    sqrt1 = Sqrt(ExactFraction(81, 16))
    pi = Pi(true)
    expected = Term(one, listOf(Sqrt(ExactFraction(81, 16)), Pi(true)))
    assertEquals(expected, sqrt1 * pi)

    // Sqrt
    sqrt1 = Sqrt.ZERO
    var sqrt2 = Sqrt.ZERO
    expected = Term(one, listOf(Sqrt.ZERO, Sqrt.ZERO))
    assertEquals(expected, sqrt1 * sqrt2)

    sqrt1 = Sqrt(18)
    sqrt2 = Sqrt.ZERO
    expected = Term(one, listOf(Sqrt(18), Sqrt.ZERO))
    assertEquals(expected, sqrt1 * sqrt2)

    sqrt1 = Sqrt(ExactFraction(4, 49))
    sqrt2 = Sqrt(ExactFraction(49, 4))
    expected = Term(one, listOf(Sqrt(ExactFraction(4, 49)), Sqrt(ExactFraction(49, 4))))
    assertEquals(expected, sqrt1 * sqrt2)

    sqrt1 = Sqrt(18)
    sqrt2 = Sqrt(44)
    expected = Term(one, listOf(Sqrt(18), Sqrt(44)))
    assertEquals(expected, sqrt1 * sqrt2)
}

fun runDivTests() {
    // Log
    assertDivByZero { Sqrt.ONE / Log.ZERO }

    var sqrt1 = Sqrt(4)
    var log = Log.ONE
    var expected = Term(one, listOf(Sqrt(4), Log(10, 10, true)))
    assertEquals(expected, sqrt1 / log)

    sqrt1 = Sqrt(8)
    log = Log(32, 2)
    expected = Term(one, listOf(Sqrt(8), Log(32, 2, true)))
    assertEquals(expected, sqrt1 / log)

    sqrt1 = Sqrt(ExactFraction.HALF)
    log = Log(32, 2, true)
    expected = Term(one, listOf(Sqrt(ExactFraction.HALF), Log(32, 2)))
    assertEquals(expected, sqrt1 / log)

    // Pi
    sqrt1 = Sqrt(1234321)
    var pi = Pi()
    expected = Term(one, listOf(Sqrt(1234321), Pi(true)))
    assertEquals(expected, sqrt1 / pi)

    sqrt1 = Sqrt(ExactFraction(1, 8))
    pi = Pi(true)
    expected = Term(one, listOf(Sqrt(ExactFraction(1, 8)), Pi()))
    assertEquals(expected, sqrt1 / pi)

    // Sqrt
    assertDivByZero { Sqrt.ONE / Sqrt.ZERO }

    sqrt1 = Sqrt(18)
    var sqrt2 = Sqrt.ONE
    expected = Term(one, listOf(Sqrt(18), Sqrt.ONE))
    assertEquals(expected, sqrt1 / sqrt2)

    sqrt1 = Sqrt(144)
    sqrt2 = Sqrt(16)
    expected = Term(one, listOf(Sqrt(144), Sqrt(ExactFraction(1, 16))))
    assertEquals(expected, sqrt1 / sqrt2)

    sqrt1 = Sqrt(ExactFraction.HALF)
    sqrt2 = Sqrt(ExactFraction.TWO)
    expected = Term(one, listOf(Sqrt(ExactFraction.HALF), Sqrt(ExactFraction.HALF)))
    assertEquals(expected, sqrt1 / sqrt2)
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
