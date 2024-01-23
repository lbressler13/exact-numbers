package xyz.lbres.exactnumbers.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.exactnumbers.testutils.TestNumber
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val log1 = Log(ExactFraction(15, 4))
private val log2 = Log(8, 7)
private val log3 = Log(ExactFraction(19, 33)).inverse()
private val log4 = Log(ExactFraction(25, 121))
private val testNumber1 = TestNumber(ExactFraction(5, 6))
private val testNumber2 = TestNumber(ExactFraction.SEVEN)
private val one = ExactFraction.ONE

fun runEqualsTests() {
    // equal
    var term1 = Term.ZERO
    assertEquals(term1, term1)

    term1 = Term.fromValues(ExactFraction(-17, 4), emptyList())
    assertEquals(term1, term1)

    term1 = Term.fromValues(one, listOf(log1, log2))
    assertEquals(term1, term1)

    term1 = Term.fromValues(one, listOf(Pi(), Pi()))
    assertEquals(term1, term1)

    term1 = Term.fromValues(ExactFraction.EIGHT, listOf(log4, log3, log1, Sqrt(15), Pi().inverse(), Pi()))
    assertEquals(term1, term1)

    term1 = Term.fromValues(one, listOf(Pi(), TestNumber(ExactFraction(5))))
    assertEquals(term1, term1)

    term1 = Term.fromValues(ExactFraction.EIGHT, listOf(log4, log3, log1, Sqrt(5), Sqrt(7), Pi().inverse(), Pi()))
    var term2 = Term.fromValues(ExactFraction.EIGHT, listOf(log4, log3, log1, Sqrt(35)))
    assertEquals(term1, term2)
    assertEquals(term2, term1)

    term1 = Term.fromValues(ExactFraction(-4, 7), listOf(Log.ZERO, Sqrt.ONE))
    term2 = Term.ZERO
    assertEquals(term1, term2)
    assertEquals(term2, term1)

    term1 = Term.fromValues(ExactFraction(-4, 7), listOf(testNumber2, Sqrt(ExactFraction(7, 9)), Pi(), log1, log1.inverse()))
    term2 = Term.fromValues(ExactFraction(-4, 3), listOf(Sqrt(7), Pi()))
    assertEquals(term1, term2)
    assertEquals(term2, term1)

    term1 = Term.fromValues(ExactFraction(-4, 7), listOf(testNumber2, Sqrt(ExactFraction(7, 9)), Pi(), log1, log1.inverse()))
    term2 = Term.fromValues(ExactFraction(-4, 3), listOf(Sqrt(7), Sqrt.ONE, Pi().inverse(), Pi(), Pi()))
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
    term2 = Term.fromValues(one, listOf(log1.inverse()))
    assertNotEquals(term1, term2)
    assertNotEquals(term2, term1)

    term1 = Term.fromValues(one, listOf(log1))
    term2 = Term.fromValues(one, listOf(log1, log2))
    assertNotEquals(term1, term2)
    assertNotEquals(term2, term1)

    term1 = Term.fromValues(one, listOf(Pi()))
    term2 = Term.ONE
    assertNotEquals(term1, term2)
    assertNotEquals(term2, term1)

    term1 = Term.fromValues(one, listOf(Pi()))
    term2 = Term.fromValues(one, listOf(Pi().inverse()))
    assertNotEquals(term1, term2)
    assertNotEquals(term2, term1)

    term1 = Term.fromValues(one, listOf(Pi(), Pi().inverse()))
    term2 = Term.fromValues(one, listOf(Pi().inverse()))
    assertNotEquals(term1, term2)
    assertNotEquals(term2, term1)

    term1 = Term.fromValues(one, listOf(Sqrt(12)))
    term2 = Term.ONE
    assertNotEquals(term1, term2)
    assertNotEquals(term2, term1)

    term1 = Term.fromValues(one, listOf(Sqrt(12)))
    term2 = Term.fromValues(one, listOf(Sqrt(ExactFraction(1, 12))))
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

    term1 = Term.fromValues(ExactFraction(5, 7), listOf(log1, log1, Pi(), Pi().inverse()))
    term2 = Term.fromValues(ExactFraction.FIVE, listOf(log1, log1, Pi(), Pi().inverse()))
    assertNotEquals(term1, term2)
    assertNotEquals(term2, term1)

    term1 = Term.fromValues(ExactFraction.EIGHT, listOf(log3, log4, Pi().inverse()))
    term2 = Term.fromValues(ExactFraction(-17, 15), listOf(log1, log2, log3))
    assertNotEquals(term1, term2)
    assertNotEquals(term2, term1)

    term1 = Term.fromValues(ExactFraction.FOUR, listOf(Pi(), testNumber1))
    term2 = Term.fromValues(ExactFraction.FOUR, listOf(Pi(), testNumber1.inverse()))
    assertNotEquals(term1, term2)
    assertNotEquals(term2, term1)
}

fun runToStringTests() {
    // zero
    var term = Term.ZERO
    var expected = "<0>"
    assertEquals(expected, term.toString())

    // just coefficient
    term = Term.fromValues(ExactFraction(-25), emptyList())
    expected = "<-25>"
    assertEquals(expected, term.toString())

    term = Term.fromValues(ExactFraction(44, 7), emptyList())
    expected = "<[44/7]>"
    assertEquals(expected, term.toString())

    // just logs
    term = Term.fromValues(one, listOf(Log.ONE))
    expected = "<1x${Log.ONE}>"
    assertEquals(expected, term.toString())

    term = Term.fromValues(one, listOf(log2, log4, log1))
    expected = "<1x${log2}x${log4}x$log1>"
    assertEquals(expected, term.toString())

    // just pi
    term = Term.fromValues(one, listOf(Pi()))
    expected = "<1x${Pi()}>"
    assertEquals(expected, term.toString())

    term = Term.fromValues(one, listOf(Pi(), Pi().inverse(), Pi()))
    expected = "<1x${Pi()}x${Pi().inverse()}x${Pi()}>"
    assertEquals(expected, term.toString())

    // just sqrt
    term = Term.fromValues(one, listOf(Sqrt.ONE))
    expected = "<1x${Sqrt.ONE}>"
    assertEquals(expected, term.toString())

    term = Term.fromValues(one, listOf(Sqrt(32), Sqrt(127), Sqrt(ExactFraction(2, 9))))
    expected = "<1x${Sqrt(32)}x${Sqrt(127)}x${Sqrt(ExactFraction(2, 9))}>"
    assertEquals(expected, term.toString())

    // mix
    term = Term.fromValues(ExactFraction.EIGHT, listOf(log3, Sqrt(12), testNumber2, Pi()))
    expected = "<8x${log3}x${Sqrt(12)}x${testNumber2}x${Pi()}>"
    assertEquals(expected, term.toString())

    val sqrt1 = Sqrt(ExactFraction(1000, 109))
    val sqrt2 = Sqrt(5096)
    term = Term.fromValues(
        ExactFraction(-100, 333),
        listOf(log2, log2, log4, testNumber1, log1, sqrt1, sqrt2, Pi().inverse(), Pi())
    )
    expected = "<[-100/333]x${log2}x${log2}x${log4}x${testNumber1}x${log1}x${sqrt1}x${sqrt2}x${Pi().inverse()}x${Pi()}>"
    assertEquals(expected, term.toString())
}
