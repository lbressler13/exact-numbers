package xyz.lbres.exactnumbers.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.exactnumbers.testutils.TestNumber
import xyz.lbres.exactnumbers.testutils.assertDivByZero
import kotlin.test.assertEquals

private val logNum1 = Log(ExactFraction(15, 4))
private val logNum2 = Log(8)
private val logNum3 = Log(ExactFraction(19, 33))
private val logNum4 = Log(ExactFraction(25, 121))
private val sqrt1 = Sqrt(99)
private val sqrt2 = Sqrt(ExactFraction(64, 121))
private val sqrt3 = Sqrt(ExactFraction(15, 44))
private val testNumber1 = TestNumber(ExactFraction(5, 6))
private val testNumber2 = TestNumber(ExactFraction.SEVEN)
private val one = ExactFraction.ONE

fun runTimesTests() {
    // zero
    var term1 = Term.ZERO
    var expected = Term.ZERO

    var term2 = Term.ZERO
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term2 = Term.fromValues(ExactFraction.EIGHT, emptyList())
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term2 = Term.fromValues(ExactFraction.EIGHT, listOf(logNum3, logNum4, Pi(true)))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    // just logs
    term1 = Term.fromValues(one, listOf(logNum1, logNum2, logNum3))
    term2 = Term.ONE
    expected = Term.fromValues(one, listOf(logNum1, logNum2, logNum3))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term.fromValues(one, listOf(logNum1, logNum2))
    term2 = Term.fromValues(one, listOf(logNum1, logNum4))
    expected = Term.fromValues(one, listOf(logNum1, logNum1, logNum2, logNum4))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    // just pi
    term1 = Term.fromValues(one, listOf(Pi(), Pi()))
    term2 = Term.ONE
    expected = Term.fromValues(one, listOf(Pi(), Pi()))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term.fromValues(one, listOf(Pi(), Pi()))
    term2 = Term.fromValues(one, listOf(Pi(true)))
    expected = Term.fromValues(one, listOf(Pi(), Pi(), Pi(true)))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term.fromValues(one, listOf(Pi(), Pi(true), Pi()))
    term2 = Term.fromValues(one, listOf(Pi(true), Pi()))
    expected = Term.fromValues(one, listOf(Pi(), Pi(), Pi(), Pi(true), Pi(true)))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    // just sqrt
    term1 = Term.fromValues(one, listOf(Sqrt(ExactFraction(19, 9)), sqrt1))
    term2 = Term.ONE
    expected = Term.fromValues(one, listOf(Sqrt(ExactFraction(19, 9)), sqrt1))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term.fromValues(one, listOf(Sqrt(ExactFraction(19, 9)), sqrt1))
    term2 = Term.fromValues(one, listOf(sqrt2))
    expected = Term.fromValues(one, listOf(Sqrt(ExactFraction(19, 9)), sqrt1, sqrt2))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term.fromValues(one, listOf(sqrt1, sqrt3))
    term2 = Term.fromValues(one, listOf(sqrt3, sqrt2))
    expected = Term.fromValues(one, listOf(sqrt1, sqrt2, sqrt3, sqrt3))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    // just exact fraction
    term1 = Term.ONE
    term2 = Term.fromValues(ExactFraction.TWO, emptyList())
    expected = Term.fromValues(ExactFraction.TWO, emptyList())
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term.ONE
    term2 = Term.fromValues(ExactFraction(-17, 3), emptyList())
    expected = Term.fromValues(ExactFraction(-17, 3), emptyList())
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term.fromValues(ExactFraction.SEVEN, emptyList())
    term2 = Term.fromValues(ExactFraction.HALF, emptyList())
    expected = Term.fromValues(ExactFraction(7, 2), emptyList())
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    // combination
    term1 = Term.fromValues(ExactFraction(1, 4), listOf(logNum1, sqrt1, testNumber1, testNumber1, Pi()))
    term2 = Term.fromValues(ExactFraction(-1, 3), listOf(Pi(true), testNumber1.inverse(), Pi()))
    expected = Term.fromValues(
        ExactFraction(-1, 12),
        listOf(logNum1, sqrt1, Pi(), Pi(), Pi(true), testNumber1, testNumber1, testNumber1.inverse())
    )
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term.fromValues(ExactFraction.EIGHT, listOf(logNum1, logNum4, sqrt3, Pi(true)))
    term2 = Term.fromValues(ExactFraction(-15), listOf(logNum1, logNum2, sqrt1, Pi(), Pi()))
    expected = Term.fromValues(
        ExactFraction(-120),
        listOf(logNum1, logNum1, logNum2, logNum4, sqrt3, sqrt1, Pi(), Pi(), Pi(true))
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
    term1 = Term.fromValues(one, listOf(logNum1, logNum2, logNum3))
    term2 = Term.ONE
    expected = Term.fromValues(one, listOf(logNum1, logNum2, logNum3))
    assertEquals(expected, term1 / term2)

    term1 = Term.ONE
    term2 = Term.fromValues(one, listOf(logNum1, logNum2, logNum3))
    expected = Term.fromValues(one, listOf(logNum1.inverse(), logNum2.inverse(), logNum3.inverse()))
    assertEquals(expected, term1 / term2)

    term1 = Term.fromValues(one, listOf(logNum1, logNum2))
    term2 = Term.fromValues(one, listOf(logNum3))
    expected = Term.fromValues(one, listOf(logNum1, logNum2, logNum3.inverse()))
    assertEquals(expected, term1 / term2)

    // just pi
    term1 = Term.fromValues(one, listOf(Pi()))
    term2 = Term.ONE
    expected = Term.fromValues(one, listOf(Pi()))
    assertEquals(expected, term1 / term2)

    term1 = Term.ONE
    term2 = Term.fromValues(one, listOf(Pi()))
    expected = Term.fromValues(one, listOf(Pi(true)))
    assertEquals(expected, term1 / term2)

    term1 = Term.fromValues(one, listOf(Pi(), Pi(), Pi(true)))
    term2 = Term.fromValues(one, listOf(Pi(true), Pi(), Pi(true)))
    expected = Term.fromValues(one, listOf(Pi(), Pi(), Pi(), Pi(), Pi(true), Pi(true)))
    assertEquals(expected, term1 / term2)

    // just sqrt
    term1 = Term.fromValues(one, listOf(sqrt1, sqrt2, sqrt3))
    term2 = Term.ONE
    expected = Term.fromValues(one, listOf(sqrt1, sqrt2, sqrt3))
    assertEquals(expected, term1 / term2)

    term1 = Term.ONE
    term2 = Term.fromValues(one, listOf(sqrt1, sqrt2, sqrt3))
    expected = Term.fromValues(one, listOf(sqrt1.inverse(), sqrt2.inverse(), sqrt3.inverse()))
    assertEquals(expected, term1 / term2)

    term1 = Term.fromValues(one, listOf(sqrt1, sqrt2))
    term2 = Term.fromValues(one, listOf(sqrt3))
    expected = Term.fromValues(one, listOf(sqrt1, sqrt2, sqrt3.inverse()))
    assertEquals(expected, term1 / term2)

    // just exact fraction
    term1 = Term.fromValues(ExactFraction(-4, 3), emptyList())
    term2 = Term.ONE
    expected = Term.fromValues(ExactFraction(-4, 3), emptyList())
    assertEquals(expected, term1 / term2)

    term1 = Term.ONE
    term2 = Term.fromValues(ExactFraction.TWO, emptyList())
    expected = Term.fromValues(ExactFraction.HALF, emptyList())
    assertEquals(expected, term1 / term2)

    term1 = Term.fromValues(ExactFraction(-5, 2), emptyList())
    term2 = Term.fromValues(ExactFraction(3, 4), emptyList())
    expected = Term.fromValues(ExactFraction(-10, 3), emptyList())
    assertEquals(expected, term1 / term2)

    // mix
    term1 = Term.fromValues(ExactFraction(1, 4), listOf(logNum1, testNumber2))
    term2 = Term.fromValues(ExactFraction(-1, 3), listOf(sqrt1, Pi()))

    expected = Term.fromValues(ExactFraction(-3, 4), listOf(logNum1, sqrt1.inverse(), Pi(true), testNumber2))
    assertEquals(expected, term1 / term2)

    expected = Term.fromValues(ExactFraction(-4, 3), listOf(logNum1.inverse(), sqrt1, Pi(), testNumber2.inverse()))
    assertEquals(expected, term2 / term1)

    term1 = Term.fromValues(ExactFraction.EIGHT, listOf(logNum3, logNum1, sqrt2, Pi(true), Pi(true)))
    term2 = Term.fromValues(ExactFraction(15), listOf(logNum4, logNum1.inverse(), sqrt3, Pi()))

    expected = Term.fromValues(
        ExactFraction(8, 15),
        listOf(logNum1, logNum1, logNum3, logNum4.inverse(), sqrt2, sqrt3.inverse(), Pi(true), Pi(true), Pi(true))
    )
    assertEquals(expected, term1 / term2)

    expected = Term.fromValues(
        ExactFraction(15, 8),
        listOf(logNum1.inverse(), logNum1.inverse(), logNum3.inverse(), logNum4, sqrt2.inverse(), sqrt3, Pi(), Pi(), Pi())
    )
    assertEquals(expected, term2 / term1)
}
