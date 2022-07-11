package expressions.term

import assertDivByZero
import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.logs.LogNum
import kotlin.test.assertEquals

private val logNum1 = LogNum(ExactFraction(15, 4))
private val logNum2 = LogNum(ExactFraction.EIGHT)
private val logNum3 = LogNum(ExactFraction(19, 33))
private val logNum4 = LogNum(ExactFraction(25, 121))

internal fun runTimesTests() {
    // zero
    var term1 = Term.ZERO
    var expected = Term.ZERO

    var term2 = Term.ZERO
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term2 = Term(listOf(), 0, ExactFraction.EIGHT)
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term2 = Term(listOf(logNum1, logNum2), -4, ExactFraction.EIGHT)
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    // just logs
    term1 = Term(listOf(logNum1, logNum2, logNum3), 0, ExactFraction.ONE)
    term2 = Term(listOf(), 0, ExactFraction.ONE)
    expected = Term(listOf(logNum1, logNum2, logNum3), 0, ExactFraction.ONE)
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term(listOf(logNum1, logNum2), 0, ExactFraction.ONE)
    term2 = Term(listOf(logNum1, logNum4), 0, ExactFraction.ONE)
    expected = Term(listOf(logNum1, logNum1, logNum2, logNum4), 0, ExactFraction.ONE)
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    // just pi
    term1 = Term(listOf(), 0, ExactFraction.ONE)
    term2 = Term(listOf(), 1, ExactFraction.ONE)
    expected = Term(listOf(), 1, ExactFraction.ONE)
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term(listOf(), 2, ExactFraction.ONE)
    term2 = Term(listOf(), 4, ExactFraction.ONE)
    expected = Term(listOf(), 6, ExactFraction.ONE)
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term(listOf(), 2, ExactFraction.ONE)
    term2 = Term(listOf(), -5, ExactFraction.ONE)
    expected = Term(listOf(), -3, ExactFraction.ONE)
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    // just exact fraction
    term1 = Term(listOf(), 0, ExactFraction.ONE)
    term2 = Term(listOf(), 0, ExactFraction.TWO)
    expected = Term(listOf(), 0, ExactFraction.TWO)
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term(listOf(), 0, ExactFraction.ONE)
    term2 = Term(listOf(), 0, ExactFraction(-17, 3))
    expected = Term(listOf(), 0, ExactFraction(-17, 3))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term(listOf(), 0, ExactFraction.SEVEN)
    term2 = Term(listOf(), 0, -ExactFraction.HALF)
    expected = Term(listOf(), 0, ExactFraction(-7, 2))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    // combination
    term1 = Term(listOf(logNum1), 0, ExactFraction(1, 4))
    term2 = Term(listOf(), 2, ExactFraction(-1, 3))
    expected = Term(listOf(logNum1), 2, ExactFraction(-1, 12))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term(listOf(logNum1, logNum4), -2, ExactFraction.EIGHT)
    term2 = Term(listOf(logNum1, logNum2, logNum3), -1, ExactFraction(-15))
    expected = Term(listOf(logNum1, logNum1, logNum2, logNum3, logNum4), -3, ExactFraction(-120))
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
    term1 = Term(listOf(logNum1, logNum2, logNum3), 0, ExactFraction.ONE)
    term2 = Term(listOf(), 0, ExactFraction.ONE)
    expected = Term(listOf(logNum1, logNum2, logNum3), 0, ExactFraction.ONE)
    assertEquals(expected, term1 / term2)

    term1 = Term(listOf(), 0, ExactFraction.ONE)
    term2 = Term(listOf(logNum3, logNum4), 0, ExactFraction.ONE)
    expected = Term(listOf(logNum3.swapDivided(), logNum4.swapDivided()), 0, ExactFraction.ONE)
    assertEquals(expected, term1 / term2)

    term1 = Term(listOf(logNum1, logNum2), 0, ExactFraction.ONE)
    term2 = Term(listOf(logNum3), 0, ExactFraction.ONE)
    expected = Term(listOf(logNum1, logNum2, logNum3.swapDivided()), 0, ExactFraction.ONE)
    assertEquals(expected, term1 / term2)

    // just pi
    term1 = Term(listOf(), 0, ExactFraction.ONE)
    term2 = Term(listOf(), 1, ExactFraction.ONE)
    expected = Term(listOf(), -1, ExactFraction.ONE)
    assertEquals(expected, term1 / term2)

    term1 = Term(listOf(), 6, ExactFraction.ONE)
    term2 = Term(listOf(), 2, ExactFraction.ONE)
    expected = Term(listOf(), 4, ExactFraction.ONE)
    assertEquals(expected, term1 / term2)

    term1 = Term(listOf(), 2, ExactFraction.ONE)
    term2 = Term(listOf(), -5, ExactFraction.ONE)
    expected = Term(listOf(), 7, ExactFraction.ONE)
    assertEquals(expected, term1 / term2)

    term1 = Term(listOf(), -3, ExactFraction.ONE)
    term2 = Term(listOf(), 1, ExactFraction.ONE)
    expected = Term(listOf(), -4, ExactFraction.ONE)
    assertEquals(expected, term1 / term2)

    // just exact fraction
    term1 = Term(listOf(), 0, ExactFraction(-4, 3))
    term2 = Term(listOf(), 0, ExactFraction.ONE)
    expected = Term(listOf(), 0, ExactFraction(-4, 3))
    assertEquals(expected, term1 / term2)

    term1 = Term(listOf(), 0, ExactFraction.ONE)
    term2 = Term(listOf(), 0, ExactFraction.TWO)
    expected = Term(listOf(), 0, ExactFraction.HALF)
    assertEquals(expected, term1 / term2)

    term1 = Term(listOf(), 0, ExactFraction(-5, 2))
    term2 = Term(listOf(), 0, ExactFraction(3, 4))
    expected = Term(listOf(), 0, ExactFraction(-10, 3))
    assertEquals(expected, term1 / term2)

    // combination
    term1 = Term(listOf(logNum1), 0, ExactFraction(1, 4))
    term2 = Term(listOf(), 2, ExactFraction(-1, 3))

    expected = Term(listOf(logNum1), -2, ExactFraction(-3, 4))
    assertEquals(expected, term1 / term2)

    expected = Term(listOf(logNum1.swapDivided()), 2, ExactFraction(-4, 3))
    assertEquals(expected, term2 / term1)

    term1 = Term(listOf(logNum1, logNum4), -2, -ExactFraction.EIGHT)
    term2 = Term(listOf(logNum1, logNum2, logNum3), -1, ExactFraction(-15))

    expected = Term(
        listOf(logNum1, logNum1.swapDivided(), logNum2.swapDivided(), logNum3.swapDivided(), logNum4),
        -1,
        ExactFraction(8, 15)
    )
    assertEquals(expected, term1 / term2)

    expected = Term(
        listOf(logNum1.swapDivided(), logNum1, logNum2, logNum3, logNum4.swapDivided()),
        1,
        ExactFraction(15, 8)
    )
    assertEquals(expected, term2 / term1)
}
