package xyz.lbres.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

private val logNum2 = Log(8, 7)
private val logNum3 = Log(ExactFraction(19, 33), true)
private val logNum4 = Log(ExactFraction(25, 121))
private val one = ExactFraction.ONE

fun runUnaryMinusTests() {
    var term = Term.ZERO
    var expected = Term.ZERO
    assertEquals(expected, -term)

    term = Term(one, listOf(Log.ONE))
    expected = Term(ExactFraction.NEG_ONE, listOf(Log.ONE))
    assertEquals(expected, -term)

    term = Term(ExactFraction.NEG_ONE, listOf(Pi()))
    expected = Term(one, listOf(Pi()))
    assertEquals(expected, -term)

    term = Term(ExactFraction.NEG_ONE, listOf(Sqrt(32)))
    expected = Term(one, listOf(Sqrt(32)))
    assertEquals(expected, -term)

    term = Term(-ExactFraction.SIX, listOf(logNum3, logNum4, Pi(true), Sqrt(36)))
    expected = Term(ExactFraction.SIX, listOf(logNum3, logNum4, Pi(true), Sqrt(36)))
    assertEquals(expected, -term)

    term = Term(ExactFraction(15, 44), emptyList())
    expected = Term(ExactFraction(-15, 44), emptyList())
    assertEquals(expected, -term)

    term = Term(
        ExactFraction(-15, 44),
        listOf(
            Pi(), Pi(true), Pi(),
            Sqrt(ExactFraction(3, 5)), Sqrt(961),
            logNum2, logNum3, logNum4
        )
    )
    expected = Term(
        ExactFraction(15, 44),
        listOf(
            Pi(), Pi(true), Pi(),
            Sqrt(ExactFraction(3, 5)), Sqrt(961),
            logNum2, logNum3, logNum4
        )
    )
    assertEquals(expected, -term)
}

fun runUnaryPlusTests() {
    var term = Term.ZERO
    assertEquals(term, +term)

    term = Term(one, listOf(Log.ONE))
    assertEquals(term, +term)

    term = Term(one, listOf(Pi()))
    assertEquals(term, +term)

    term = Term(one, listOf(Sqrt.ONE))
    assertEquals(term, +term)

    term = Term(-ExactFraction.SIX, listOf(logNum3, Sqrt(121), logNum4, Pi(true)))
    assertEquals(term, +term)

    term = Term(ExactFraction(15, 44), emptyList())
    assertEquals(term, +term)

    term = Term(
        ExactFraction(-15, 44),
        listOf(Pi(), Pi(true), Pi(), Sqrt(ExactFraction(64, 9)), logNum2, logNum3, logNum4)
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

    term = Term(one, listOf(Log.ONE))
    assertFalse(term.isZero())

    term = Term(one, listOf(Pi()))
    assertFalse(term.isZero())

    term = Term(one, listOf(Sqrt.ONE))
    assertFalse(term.isZero())

    term = Term(ExactFraction(5, 4), listOf(Sqrt(12), logNum2, Pi(true), logNum4))
    assertFalse(term.isZero())

    term = Term(-ExactFraction.HALF, listOf(logNum2, logNum2.swapDivided()))
    assertFalse(term.isZero())

    term = Term(-ExactFraction.HALF, listOf(Sqrt(64), Sqrt(ExactFraction(1, 64))))
    assertFalse(term.isZero())

    term = Term(ExactFraction(-1, 1000000), listOf(Pi(true), Pi(true), Pi(true)))
    assertFalse(term.isZero())
}
