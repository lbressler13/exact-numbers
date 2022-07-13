package expressions.term

import assertDivByZero
import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.logs.LogNum
import exactnumbers.irrationals.pi.Pi
import kotlin.test.assertEquals

private val logNum1 = LogNum(ExactFraction(15, 4))
private val logNum2 = LogNum(ExactFraction.EIGHT)
private val logNum3 = LogNum(ExactFraction(19, 33))
private val logNum4 = LogNum(ExactFraction(25, 121))
private val one = ExactFraction.ONE

internal fun runTimesTests() {
    // zero
    var term1 = Term.ZERO
    var expected = Term.ZERO

    var term2 = Term.ZERO
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term2 = Term(ExactFraction.EIGHT, listOf())
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term2 = Term(ExactFraction.EIGHT, listOf(logNum3, logNum4, Pi(true)))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    // just logs
    term1 = Term(one, listOf(logNum1, logNum2, logNum3))
    term2 = Term(one, listOf())
    expected = Term(one, listOf(logNum1, logNum2, logNum3))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term(one, listOf(logNum1, logNum2))
    term2 = Term(one, listOf(logNum1, logNum4))
    expected = Term(one, listOf(logNum1, logNum1, logNum2, logNum4))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    // just pi
    term1 = Term(one, listOf(Pi(), Pi()))
    term2 = Term(one, listOf())
    expected = Term(one, listOf(Pi(), Pi()))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term(one, listOf(Pi(), Pi()))
    term2 = Term(one, listOf(Pi(true)))
    expected = Term(one, listOf(Pi(), Pi(), Pi(true)))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term(one, listOf(Pi(), Pi(true), Pi()))
    term2 = Term(one, listOf(Pi(true), Pi()))
    expected = Term(one, listOf(Pi(), Pi(), Pi(), Pi(true), Pi(true)))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    // just exact fraction
    term1 = Term(one, listOf())
    term2 = Term(ExactFraction.TWO, listOf())
    expected = Term(ExactFraction.TWO, listOf())
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term(one, listOf())
    term2 = Term(ExactFraction(-17, 3), listOf())
    expected = Term(ExactFraction(-17, 3), listOf())
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term(ExactFraction.SEVEN, listOf())
    term2 = Term(ExactFraction.HALF, listOf())
    expected = Term(ExactFraction(7, 2), listOf())
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    // combination
    term1 = Term(ExactFraction(1, 4), listOf(logNum1, Pi()))
    term2 = Term(ExactFraction(-1, 3), listOf(Pi(true), Pi()))
    expected = Term(ExactFraction(-1, 12), listOf(logNum1, Pi(), Pi(), Pi(true)))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term(ExactFraction.EIGHT, listOf(logNum1, Pi(true), logNum4))
    term2 = Term(ExactFraction(-15), listOf(Pi(), logNum1, logNum2, Pi(), logNum3))
    expected = Term(ExactFraction(-120), listOf(logNum1, logNum1, logNum2, logNum3, logNum4, Pi(), Pi(), Pi(true)))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)
}

internal fun runDivTests() {
    // divide by zero
    assertDivByZero { Term.ONE / Term.ZERO }

    // zero
    var term1 = Term.ZERO
    var term2 = Term.ONE
    var expected = Term.ZERO
    assertEquals(expected, term1 / term2)

    // just logs
    term1 = Term(one, listOf(logNum1, logNum2, logNum3))
    term2 = Term(one, listOf())
    expected = Term(one, listOf(logNum1, logNum2, logNum3))
    assertEquals(expected, term1 / term2)

    term1 = Term(one, listOf())
    term2 = Term(one, listOf(logNum1, logNum2, logNum3))
    expected = Term(one, listOf(logNum1.swapDivided(), logNum2.swapDivided(), logNum3.swapDivided()))
    assertEquals(expected, term1 / term2)

    term1 = Term(one, listOf(logNum1, logNum2))
    term2 = Term(one, listOf(logNum3))
    expected = Term(one, listOf(logNum1, logNum2, logNum3.swapDivided()))
    assertEquals(expected, term1 / term2)

    // just pi
    term1 = Term(one, listOf(Pi()))
    term2 = Term(one, listOf())
    expected = Term(one, listOf(Pi()))
    assertEquals(expected, term1 / term2)

    term1 = Term(one, listOf())
    term2 = Term(one, listOf(Pi()))
    expected = Term(one, listOf(Pi().swapDivided()))
    assertEquals(expected, term1 / term2)

    term1 = Term(one, listOf(Pi(), Pi(), Pi(true)))
    term2 = Term(one, listOf(Pi(true), Pi(), Pi(true)))
    expected = Term(one, listOf(Pi(), Pi(), Pi(), Pi(), Pi(true), Pi(true)))
    assertEquals(expected, term1 / term2)

    // just exact fraction
    term1 = Term(ExactFraction(-4, 3), listOf())
    term2 = Term(one, listOf())
    expected = Term(ExactFraction(-4, 3), listOf())
    assertEquals(expected, term1 / term2)

    term1 = Term(one, listOf())
    term2 = Term(ExactFraction.TWO, listOf())
    expected = Term(ExactFraction.HALF, listOf())
    assertEquals(expected, term1 / term2)

    term1 = Term(ExactFraction(-5, 2), listOf())
    term2 = Term(ExactFraction(3, 4), listOf())
    expected = Term(ExactFraction(-10, 3), listOf())
    assertEquals(expected, term1 / term2)

    // mix
    term1 = Term(ExactFraction(1, 4), listOf(logNum1))
    term2 = Term(ExactFraction(-1, 3), listOf(Pi()))

    expected = Term(ExactFraction(-3, 4), listOf(logNum1, Pi(true)))
    assertEquals(expected, term1 / term2)

    expected = Term(ExactFraction(-4, 3), listOf(logNum1.swapDivided(), Pi()))
    assertEquals(expected, term2 / term1)

    term1 = Term(ExactFraction.EIGHT, listOf(logNum3, Pi(true), logNum1, Pi(true)))
    term2 = Term(ExactFraction(15), listOf(Pi(), logNum4, logNum1.swapDivided()))

    expected = Term(
        ExactFraction(8, 15),
        listOf(logNum1, logNum1, logNum3, logNum4.swapDivided(), Pi(true), Pi(true), Pi(true))
    )
    assertEquals(expected, term1 / term2)

    expected = Term(
        ExactFraction(15, 8),
        listOf(logNum1.swapDivided(), logNum1.swapDivided(), logNum3.swapDivided(), logNum4, Pi(), Pi(), Pi())
    )
    assertEquals(expected, term2 / term1)
}
