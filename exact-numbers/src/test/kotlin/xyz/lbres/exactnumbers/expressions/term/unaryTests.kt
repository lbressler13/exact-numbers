package xyz.lbres.exactnumbers.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runUnaryMinusTests() {
    var term = Term.ZERO
    var expected = Term.ZERO
    assertEquals(expected, -term)

    term = Term.fromValues(one, listOf(Log.ONE))
    expected = Term.fromValues(ExactFraction.NEG_ONE, listOf(Log.ONE))
    assertEquals(expected, -term)

    term = Term.fromValues(ExactFraction.NEG_ONE, listOf(Pi()))
    expected = Term.fromValues(one, listOf(Pi()))
    assertEquals(expected, -term)

    term = Term.fromValues(ExactFraction.NEG_ONE, listOf(Sqrt(32)))
    expected = Term.fromValues(one, listOf(Sqrt(32)))
    assertEquals(expected, -term)

    term = Term.fromValues(-ExactFraction.SIX, listOf(log3, log4, Sqrt(36), Pi().inverse(), testNumber2))
    expected = Term.fromValues(ExactFraction.SIX, listOf(log3, log4, Sqrt(36), Pi().inverse(), testNumber2))
    assertEquals(expected, -term)

    term = Term.fromValues(ExactFraction(44, 15), emptyList())
    expected = Term.fromValues(ExactFraction(-44, 15), emptyList())
    assertEquals(expected, -term)

    val factors = listOf(log2, log3, log4, Sqrt(ExactFraction(3, 5)), Sqrt(961), Pi(), Pi().inverse(), Pi())
    term = Term.fromValues(ExactFraction(-15, 44), factors)
    expected = Term.fromValues(ExactFraction(15, 44), factors)
    assertEquals(expected, -term)
}

fun runUnaryPlusTests() {
    var term = Term.ZERO
    assertEquals(term, +term)

    term = Term.fromValues(one, listOf(Log.ONE))
    assertEquals(term, +term)

    term = Term.fromValues(one, listOf(Pi()))
    assertEquals(term, +term)

    term = Term.fromValues(one, listOf(Sqrt.ONE))
    assertEquals(term, +term)

    term = Term.fromValues(-ExactFraction.SIX, listOf(log3, log4, Sqrt(121), Pi().inverse(), testNumber2))
    assertEquals(term, +term)

    term = Term.fromValues(ExactFraction(44, 15), emptyList())
    assertEquals(term, +term)

    term = Term.fromValues(
        ExactFraction(-15, 44),
        listOf(log2, log3, log4, Sqrt(ExactFraction(64, 9)), Pi(), Pi().inverse())
    )
    assertEquals(term, +term)
}

fun runIsZeroTests() {
    // zero
    var term = Term.ZERO
    assertTrue(term.isZero())

    // not zero
    term = Term.ONE
    assertFalse(term.isZero())

    term = Term.fromValues(one, listOf(Log.ONE))
    assertFalse(term.isZero())

    term = Term.fromValues(one, listOf(Pi()))
    assertFalse(term.isZero())

    term = Term.fromValues(one, listOf(Sqrt.ONE))
    assertFalse(term.isZero())

    term = Term.fromValues(ExactFraction(5, 4), listOf(log2, log4, Sqrt(12), Pi().inverse()))
    assertFalse(term.isZero())

    term = Term.fromValues(-ExactFraction.HALF, listOf(log2, log2.inverse(), testNumber1))
    assertFalse(term.isZero())

    term = Term.fromValues(ExactFraction(-1, 1000000), listOf(Sqrt(64), Sqrt(ExactFraction(1, 64))))
    assertFalse(term.isZero())

    assertFalse(term.isZero())
}
