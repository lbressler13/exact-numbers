package xyz.lbres.exactnumbers.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.exactnumbers.testutils.TestNumber
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun runEqualsTests() {
    // equal
    var term1 = Term.ZERO
    assertEquals(term1, term1)

    term1 = Term.fromValues(ExactFraction(-17, 4), emptyList())
    assertEquals(term1, term1)

    term1 = Term.fromValues(one, listOf(log1, log2))
    assertEquals(term1, term1)

    term1 = Term.fromValues(ExactFraction.EIGHT, listOf(log4, log3, log1, Sqrt(15), piInverse, pi))
    assertEquals(term1, term1)

    term1 = Term.fromValues(one, listOf(pi, TestNumber(ExactFraction(5))))
    assertEquals(term1, term1)

    term1 = Term.fromValues(ExactFraction.EIGHT, listOf(log4, log3, log1, Sqrt(5), Sqrt(7), piInverse, pi))
    var term2 = Term.fromValues(ExactFraction.EIGHT, listOf(log4, log3, log1, Sqrt(35)))
    assertEquals(term1, term2)
    assertEquals(term2, term1)

    term1 = Term.fromValues(ExactFraction(-4, 7), listOf(Log.ZERO, Sqrt.ONE))
    term2 = Term.ZERO
    assertEquals(term1, term2)
    assertEquals(term2, term1)

    term1 = Term.fromValues(ExactFraction(-4, 7), listOf(testNumber2, Sqrt(ExactFraction(7, 9)), pi, log1, log1.inverse()))
    term2 = Term.fromValues(ExactFraction(-4, 3), listOf(Sqrt(7), pi))
    assertEquals(term1, term2)
    assertEquals(term2, term1)

    term1 = Term.fromValues(ExactFraction(-4, 7), listOf(testNumber2, Sqrt(ExactFraction(7, 9)), pi, log1, log1.inverse()))
    term2 = Term.fromValues(ExactFraction(-4, 3), listOf(Sqrt(7), Sqrt.ONE, piInverse, pi, pi))
    assertEquals(term1, term2)
    assertEquals(term2, term1)

    // not equal
    term1 = Term.ONE
    term2 = -Term.ONE
    assertNotEquals(term1, term2)
    assertNotEquals(term2, term1)

    term1 = Term.fromValues(ExactFraction.TWO, emptyList())
    term2 = Term.fromValues(ExactFraction.HALF, emptyList())
    assertNotEquals(term1, term2)
    assertNotEquals(term2, term1)

    term1 = Term.fromValues(one, listOf(log1))
    term2 = Term.ONE
    assertNotEquals(term1, term2)
    assertNotEquals(term2, term1)

    term1 = Term.fromValues(one, listOf(log1))
    term2 = Term.fromValues(one, listOf(log1, log2))
    assertNotEquals(term1, term2)
    assertNotEquals(term2, term1)

    term1 = Term.fromValues(one, listOf(pi))
    term2 = Term.ONE
    assertNotEquals(term1, term2)
    assertNotEquals(term2, term1)

    term1 = Term.fromValues(one, listOf(pi))
    term2 = Term.fromValues(one, listOf(piInverse))
    assertNotEquals(term1, term2)
    assertNotEquals(term2, term1)

    term1 = Term.fromValues(one, listOf(pi, piInverse))
    term2 = Term.fromValues(one, listOf(piInverse))
    assertNotEquals(term1, term2)
    assertNotEquals(term2, term1)

    term1 = Term.fromValues(one, listOf(Sqrt(12), Sqrt(1000)))
    term2 = Term.fromValues(one, listOf(Sqrt(ExactFraction(1, 12))))
    assertNotEquals(term1, term2)
    assertNotEquals(term2, term1)

    term1 = Term.fromValues(ExactFraction.EIGHT, listOf(Log(15)))
    term2 = Term.fromValues(ExactFraction.EIGHT, listOf(Sqrt(15)))
    assertNotEquals(term1, term2)
    assertNotEquals(term2, term1)

    term1 = Term.fromValues(ExactFraction(5, 7), listOf(log1, log1, pi, piInverse))
    term2 = Term.fromValues(ExactFraction.FIVE, listOf(log1, log1, pi, piInverse))
    assertNotEquals(term1, term2)
    assertNotEquals(term2, term1)

    term1 = Term.fromValues(ExactFraction.EIGHT, listOf(log3, log4, piInverse))
    term2 = Term.fromValues(ExactFraction(-17, 15), listOf(log1, log2, log3))
    assertNotEquals(term1, term2)
    assertNotEquals(term2, term1)

    term1 = Term.fromValues(ExactFraction.FOUR, listOf(pi, testNumber1))
    term2 = Term.fromValues(ExactFraction.FOUR, listOf(pi, testNumber1.inverse()))
    assertNotEquals(term1, term2)
    assertNotEquals(term2, term1)
}

fun runToStringTests() {
    // zero
    var term = Term.ZERO
    var expected = "<0>"
    assertEquals(expected, term.toString())

    // just coefficient
    term = Term.ONE
    expected = "<1>"
    assertEquals(expected, term.toString())

    term = Term.fromValues(ExactFraction(-25), emptyList())
    expected = "<-25>"
    assertEquals(expected, term.toString())

    term = Term.fromValues(ExactFraction(44, 7), emptyList())
    expected = "<[44/7]>"
    assertEquals(expected, term.toString())

    // just logs
    term = Term.fromValues(one, listOf(Log.ONE))
    expected = "<${Log.ONE}>"
    assertEquals(expected, term.toString())

    term = Term.fromValues(one, listOf(log2, log4, log1))
    expected = "<${log2}x${log4}x$log1>"
    assertEquals(expected, term.toString())

    // just pi
    term = Term.fromValues(one, listOf(pi))
    expected = "<$pi>"
    assertEquals(expected, term.toString())

    term = Term.fromValues(one, listOf(pi, piInverse, pi))
    expected = "<${pi}x${piInverse}x$pi>"
    assertEquals(expected, term.toString())

    // just sqrt
    term = Term.fromValues(one, listOf(Sqrt.ONE))
    expected = "<${Sqrt.ONE}>"
    assertEquals(expected, term.toString())

    term = Term.fromValues(one, listOf(Sqrt(32), Sqrt(127), Sqrt(ExactFraction(2, 9))))
    expected = "<${Sqrt(32)}x${Sqrt(127)}x${Sqrt(ExactFraction(2, 9))}>"
    assertEquals(expected, term.toString())

    // mix
    term = Term.fromValues(-one, listOf(Sqrt.ONE))
    expected = "<-1x${Sqrt.ONE}>"
    assertEquals(expected, term.toString())

    term = Term.fromValues(ExactFraction.EIGHT, listOf(log3, Sqrt(12), testNumber2, pi))
    expected = "<8x${log3}x${Sqrt(12)}x${testNumber2}x$pi>"
    assertEquals(expected, term.toString())

    val sqrt1 = Sqrt(ExactFraction(1000, 109))
    val sqrt2 = Sqrt(5096)
    term = Term.fromValues(
        ExactFraction(-100, 333),
        listOf(log2, log2, log4, testNumber1, log1, sqrt1, sqrt2, piInverse, pi)
    )
    expected = "<[-100/333]x${log2}x${log2}x${log4}x${testNumber1}x${log1}x${sqrt1}x${sqrt2}x${piInverse}x$pi>"
    assertEquals(expected, term.toString())
}
