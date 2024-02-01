package xyz.lbres.exactnumbers.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.exactnumbers.testutils.assertDivByZero
import kotlin.test.assertEquals

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

    term2 = Term.fromValues(ExactFraction.EIGHT, listOf(log3, log4, piInverse))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    // just logs
    term1 = Term.fromValues(one, listOf(log1, log2, log3))
    term2 = Term.ONE
    expected = Term.fromValues(one, listOf(log1, log2, log3))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term.fromValues(one, listOf(log1, log2))
    term2 = Term.fromValues(one, listOf(log1.inverse(), log4))
    expected = Term.fromValues(one, listOf(log1, log1.inverse(), log2, log4))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    // just pi
    term1 = Term.fromValues(one, listOf(pi))
    term2 = Term.fromValues(one, listOf(pi))
    expected = Term.fromValues(one, listOf(pi, pi))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term.fromValues(one, listOf(pi, piInverse, pi))
    term2 = Term.fromValues(one, listOf(piInverse, pi))
    expected = Term.fromValues(one, listOf(pi, pi, pi, piInverse, piInverse))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    // just sqrt
    term1 = Term.fromValues(one, listOf(Sqrt(ExactFraction(19, 9)), sqrt1))
    term2 = Term.fromValues(one, listOf(Sqrt.ONE))
    expected = Term.fromValues(one, listOf(Sqrt(ExactFraction(19, 9)), sqrt1))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term.fromValues(one, listOf(Sqrt(ExactFraction(19, 9)), sqrt1))
    term2 = Term.fromValues(one, listOf(sqrt2))
    expected = Term.fromValues(one, listOf(Sqrt(ExactFraction(19, 9)), sqrt1, sqrt2))
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    // just exact fraction
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
    term1 = Term.fromValues(ExactFraction(1, 4), listOf(log1, sqrt1, testNumber1, testNumber1, pi))
    term2 = Term.fromValues(ExactFraction(1, 3), listOf(piInverse, testNumber1.inverse(), pi))
    expected = Term.fromValues(
        ExactFraction(1, 12),
        listOf(log1, sqrt1, pi, pi, piInverse, testNumber1, testNumber1, testNumber1.inverse())
    )
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)

    term1 = Term.fromValues(ExactFraction.EIGHT, listOf(log1, log4, sqrt3, piInverse))
    term2 = Term.fromValues(ExactFraction(-15), listOf(log1, log2, sqrt1, pi, pi))
    expected = Term.fromValues(
        ExactFraction(-120),
        listOf(log1, log1, log2, log4, sqrt3, sqrt1, pi, pi, piInverse)
    )
    assertEquals(expected, term1 * term2)
    assertEquals(expected, term2 * term1)
}

fun runDivTests() {
    // divide by zero
    assertDivByZero { Term.ONE / Term.ZERO }

    // zero
    var term1 = Term.ZERO
    var expected = Term.ZERO

    var term2 = Term.ONE
    assertEquals(expected, term1 / term2)

    term2 = Term.fromValues(ExactFraction(15), listOf(log4, log1.inverse(), sqrt3, pi))
    assertEquals(expected, term1 / term2)

    // just logs
    term1 = Term.fromValues(one, listOf(log1, log2, log3))
    term2 = Term.ONE
    expected = Term.fromValues(one, listOf(log1, log2, log3))
    assertEquals(expected, term1 / term2)

    expected = Term.fromValues(one, listOf(log1.inverse(), log2.inverse(), log3.inverse()))
    assertEquals(expected, term2 / term1)

    term1 = Term.fromValues(one, listOf(log1, log3))
    term2 = Term.fromValues(one, listOf(log3))
    expected = Term.fromValues(one, listOf(log1, log3, log3.inverse()))
    assertEquals(expected, term1 / term2)

    // just pi
    term1 = Term.fromValues(one, listOf(pi))
    term2 = Term.ONE
    expected = Term.fromValues(one, listOf(pi))
    assertEquals(expected, term1 / term2)

    expected = Term.fromValues(one, listOf(piInverse))
    assertEquals(expected, term2 / term1)

    term1 = Term.fromValues(one, listOf(pi, pi, piInverse))
    term2 = Term.fromValues(one, listOf(piInverse, pi, piInverse))
    expected = Term.fromValues(one, listOf(pi, pi, pi, pi, piInverse, piInverse))
    assertEquals(expected, term1 / term2)

    // just sqrt
    term1 = Term.fromValues(one, listOf(sqrt1, sqrt2, sqrt3))
    term2 = Term.ONE
    expected = Term.fromValues(one, listOf(sqrt1, sqrt2, sqrt3))
    assertEquals(expected, term1 / term2)

    expected = Term.fromValues(one, listOf(sqrt1.inverse(), sqrt2.inverse(), sqrt3.inverse()))
    assertEquals(expected, term2 / term1)

    term1 = Term.fromValues(one, listOf(sqrt1, sqrt2))
    term2 = Term.fromValues(one, listOf(sqrt3))
    expected = Term.fromValues(one, listOf(sqrt1, sqrt2, sqrt3.inverse()))
    assertEquals(expected, term1 / term2)

    // just exact fraction
    term1 = Term.fromValues(ExactFraction(-4, 3), emptyList())
    term2 = Term.ONE
    expected = Term.fromValues(ExactFraction(-4, 3), emptyList())
    assertEquals(expected, term1 / term2)

    expected = Term.fromValues(ExactFraction(-3, 4), emptyList())
    assertEquals(expected, term2 / term1)

    term1 = Term.fromValues(ExactFraction(-5, 2), emptyList())
    term2 = Term.fromValues(ExactFraction(3, 4), emptyList())
    expected = Term.fromValues(ExactFraction(-10, 3), emptyList())
    assertEquals(expected, term1 / term2)

    // mix
    term1 = Term.fromValues(ExactFraction(1, 4), listOf(log1, testNumber2))
    term2 = Term.fromValues(ExactFraction(-1, 3), listOf(sqrt1, pi))

    expected = Term.fromValues(ExactFraction(-3, 4), listOf(log1, sqrt1.inverse(), piInverse, testNumber2))
    assertEquals(expected, term1 / term2)

    expected = Term.fromValues(ExactFraction(-4, 3), listOf(log1.inverse(), sqrt1, pi, testNumber2.inverse()))
    assertEquals(expected, term2 / term1)

    term1 = Term.fromValues(ExactFraction.EIGHT, listOf(log3, log1, sqrt2, piInverse, piInverse))
    term2 = Term.fromValues(ExactFraction(15), listOf(log4, log1.inverse(), sqrt3, pi))

    expected = Term.fromValues(
        ExactFraction(8, 15),
        listOf(log1, log1, log3, log4.inverse(), sqrt2, sqrt3.inverse(), piInverse, piInverse, piInverse)
    )
    assertEquals(expected, term1 / term2)

    expected = Term.fromValues(
        ExactFraction(15, 8),
        listOf(log1.inverse(), log1.inverse(), log3.inverse(), log4, sqrt2.inverse(), sqrt3, pi, pi, pi)
    )
    assertEquals(expected, term2 / term1)
}
