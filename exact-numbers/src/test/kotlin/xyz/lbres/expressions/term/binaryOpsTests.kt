package xyz.lbres.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.testutils.assertDivByZero
import kotlin.test.assertEquals

private val logNum1 = Log(ExactFraction(15, 4))
private val logNum2 = Log(8)
private val logNum3 = Log(ExactFraction(19, 33))
private val logNum4 = Log(ExactFraction(25, 121))
private val sqrt1 = Sqrt(99)
private val sqrt2 = Sqrt(ExactFraction(64, 121))
private val sqrt3 = Sqrt(ExactFraction(15, 44))
private val one = ExactFraction.ONE

fun runTimesTests() {
    // zero
    var term1 = Term.ZERO
    var expected = Term.ZERO

    var term2 = Term.ZERO
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term2 = Term.fromValue(ExactFraction.EIGHT)
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term2 = Term.fromValues(ExactFraction.EIGHT, listOf(logNum3, logNum4), listOf(Pi().inverse()))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    // just logs
    term1 = Term.fromValues(listOf(logNum1, logNum2, logNum3))
    term2 = Term.ONE
    expected = Term.fromValues(listOf(logNum1, logNum2, logNum3))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term.fromValues(listOf(logNum1, logNum2))
    term2 = Term.fromValues(listOf(logNum1, logNum4))
    expected = Term.fromValues(listOf(logNum1, logNum1, logNum2, logNum4))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    // just pi
    term1 = Term.fromValues(listOf(Pi(), Pi()))
    term2 = Term.ONE
    expected = Term.fromValues(listOf(Pi(), Pi()))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term.fromValues(listOf(Pi(), Pi()))
    term2 = Term.fromValues(listOf(Pi().inverse()))
    expected = Term.fromValues(listOf(Pi(), Pi(), Pi().inverse()))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term.fromValues(listOf(Pi(), Pi().inverse(), Pi()))
    term2 = Term.fromValues(listOf(Pi().inverse(), Pi()))
    expected = Term.fromValues(listOf(Pi(), Pi(), Pi(), Pi().inverse(), Pi().inverse()))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    // just sqrt
    term1 = Term.fromValues(listOf(Sqrt(ExactFraction(19, 9)), sqrt1))
    term2 = Term.ONE
    expected = Term.fromValues(listOf(Sqrt(ExactFraction(19, 9)), sqrt1))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term.fromValues(listOf(Sqrt(ExactFraction(19, 9)), sqrt1))
    term2 = Term.fromValue(sqrt2)
    expected = Term.fromValues(listOf(Sqrt(ExactFraction(19, 9)), sqrt1, sqrt2))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term.fromValues(listOf(sqrt1, sqrt3))
    term2 = Term.fromValues(listOf(sqrt3, sqrt2))
    expected = Term.fromValues(listOf(sqrt1, sqrt2, sqrt3, sqrt3))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    // just exact fraction
    term1 = Term.ONE
    term2 = Term.fromValue(ExactFraction.TWO)
    expected = Term.fromValue(ExactFraction.TWO)
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term.ONE
    term2 = Term.fromValue(ExactFraction(-17, 3))
    expected = Term.fromValue(ExactFraction(-17, 3))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term.fromValue(ExactFraction.SEVEN)
    term2 = Term.fromValue(ExactFraction.HALF)
    expected = Term.fromValue(ExactFraction(7, 2))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    // combination
    term1 = Term.fromValues(ExactFraction(1, 4), listOf(logNum1), listOf(sqrt1), listOf(Pi()))
    term2 = Term.fromValues(ExactFraction(-1, 3), listOf(Pi().inverse(), Pi()))
    expected = Term.fromValues(ExactFraction(-1, 12), listOf(logNum1), listOf(sqrt1), listOf(Pi(), Pi(), Pi().inverse()))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term.fromValues(ExactFraction.EIGHT, listOf(logNum1, logNum4), listOf(sqrt3), listOf(Pi().inverse()))
    term2 = Term.fromValues(ExactFraction(-15), listOf(logNum1, logNum2), listOf(sqrt1), listOf(Pi(), Pi()))
    expected = Term.fromValues(
        ExactFraction(-120),
        listOf(logNum1, logNum1, logNum2, logNum4), listOf(sqrt3, sqrt1), listOf(Pi(), Pi(), Pi().inverse())
    )
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)
}

fun runDivTests() {
    // divide by zero
    assertDivByZero { Term.ONE / Term.ZERO }

    // zero
    var term1 = Term.ZERO
    var term2 = Term.ONE
    var expected = Term.ZERO
    assertEquals(expected, term1 / term2)

    // just logs
    term1 = Term.fromValues(listOf(logNum1, logNum2, logNum3))
    term2 = Term.ONE
    expected = Term.fromValues(listOf(logNum1, logNum2, logNum3))
    assertEquals(expected, term1 / term2)

    term1 = Term.ONE
    term2 = Term.fromValues(listOf(logNum1, logNum2, logNum3))
    expected = Term.fromValues(listOf(logNum1.inverse(), logNum2.inverse(), logNum3.inverse()))
    assertEquals(expected, term1 / term2)

    term1 = Term.fromValues(listOf(logNum1, logNum2))
    term2 = Term.fromValue(logNum3)
    expected = Term.fromValues(one, listOf(logNum1, logNum2, logNum3.inverse()))
    assertEquals(expected, term1 / term2)

    // just pi
    term1 = Term.fromValue(Pi())
    term2 = Term.ONE
    expected = Term.fromValue(Pi())
    assertEquals(expected, term1 / term2)

    term1 = Term.ONE
    term2 = Term.fromValue(Pi())
    expected = Term.fromValue(Pi().inverse())
    assertEquals(expected, term1 / term2)

    term1 = Term.fromValues(listOf(Pi(), Pi(), Pi().inverse()))
    term2 = Term.fromValues(listOf(Pi().inverse(), Pi(), Pi().inverse()))
    expected = Term.fromValues(listOf(Pi(), Pi(), Pi(), Pi(), Pi().inverse(), Pi().inverse()))
    assertEquals(expected, term1 / term2)

    // just sqrt
    term1 = Term.fromValues(listOf(sqrt1, sqrt2, sqrt3))
    term2 = Term.ONE
    expected = Term.fromValues(listOf(sqrt1, sqrt2, sqrt3))
    assertEquals(expected, term1 / term2)

    term1 = Term.ONE
    term2 = Term.fromValues(listOf(sqrt1, sqrt2, sqrt3))
    expected = Term.fromValues(listOf(sqrt1.inverse(), sqrt2.inverse(), sqrt3.inverse()))
    assertEquals(expected, term1 / term2)

    term1 = Term.fromValues(listOf(sqrt1, sqrt2))
    term2 = Term.fromValue(sqrt3)
    expected = Term.fromValues(listOf(sqrt1, sqrt2, sqrt3.inverse()))
    assertEquals(expected, term1 / term2)

    // just exact fraction
    term1 = Term.fromValue(ExactFraction(-4, 3))
    term2 = Term.ONE
    expected = Term.fromValue(ExactFraction(-4, 3))
    assertEquals(expected, term1 / term2)

    term1 = Term.ONE
    term2 = Term.fromValue(ExactFraction.TWO)
    expected = Term.fromValue(ExactFraction.HALF)
    assertEquals(expected, term1 / term2)

    term1 = Term.fromValue(ExactFraction(-5, 2))
    term2 = Term.fromValue(ExactFraction(3, 4))
    expected = Term.fromValue(ExactFraction(-10, 3))
    assertEquals(expected, term1 / term2)

    // mix
    term1 = Term.fromValues(ExactFraction(1, 4), listOf(logNum1))
    term2 = Term.fromValues(ExactFraction(-1, 3), listOf(sqrt1), listOf(Pi()))

    expected = Term.fromValues(ExactFraction(-3, 4), listOf(logNum1), listOf(sqrt1.inverse()), listOf(Pi().inverse()))
    assertEquals(expected, term1 / term2)

    expected = Term.fromValues(ExactFraction(-4, 3), listOf(logNum1.inverse()), listOf(sqrt1), listOf(Pi()))
    assertEquals(expected, term2 / term1)

    term1 = Term.fromValues(ExactFraction.EIGHT, listOf(logNum3, logNum1), listOf(sqrt2), listOf(Pi().inverse(), Pi().inverse()))
    term2 = Term.fromValues(ExactFraction(15), listOf(logNum4, logNum1.inverse()), listOf(sqrt3), listOf(Pi()))

    expected = Term.fromValues(
        ExactFraction(8, 15),
        listOf(logNum1, logNum1, logNum3, logNum4.inverse()),
        listOf(sqrt2, sqrt3.inverse()),
        listOf(Pi().inverse(), Pi().inverse(), Pi().inverse())
    )
    assertEquals(expected, term1 / term2)

    expected = Term.fromValues(
        ExactFraction(15, 8),
        listOf(logNum1.inverse(), logNum1.inverse(), logNum3.inverse(), logNum4),
        listOf(sqrt2.inverse(), sqrt3),
        listOf(Pi(), Pi(), Pi())
    )
    assertEquals(expected, term2 / term1)
}
