package xyz.lbres.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import kotlin.test.assertEquals

private val logNum1 = Log(ExactFraction(15, 4))
private val logNum2 = Log(8, 7)
private val logNum3 = Log(ExactFraction(19, 33)).inverse()
private val logNum4 = Log(ExactFraction(25, 121))
private val one = ExactFraction.ONE

internal fun runGetLogsTests() {
    // empty
    var expected: List<Log> = emptyList()

    var term = Term.fromValue(one)
    assertEquals(expected, term.logs)

    term = Term.fromValue(ExactFraction.TEN)
    assertEquals(expected, term.logs)

    term = Term.fromValues(emptyList(), listOf(Sqrt(ExactFraction(64, 9))), listOf(Pi(), Pi()))
    assertEquals(expected, term.logs)

    // just logs
    term = Term.fromValue(logNum1)
    expected = listOf(logNum1)
    assertEquals(expected, term.logs)

    term = Term.fromValues(listOf(logNum1, logNum1))
    expected = listOf(logNum1, logNum1)
    assertEquals(expected, term.logs)

    term = Term.fromValues(listOf(logNum1, logNum1.inverse()))
    expected = listOf(logNum1, logNum1.inverse())
    assertEquals(expected, term.logs)

    term = Term.fromValues(listOf(logNum3, logNum4, logNum1))
    expected = listOf(logNum3, logNum4, logNum1)
    assertEquals(expected, term.logs)

    // mix
    term = Term.fromValues(listOf(logNum3), listOf(Sqrt(2)), listOf(Pi()))
    expected = listOf(logNum3)
    assertEquals(expected, term.logs)

    term = Term.fromValues(ExactFraction.EIGHT, listOf(logNum2, logNum2, logNum3, logNum4), listOf(Sqrt(15)), listOf(Pi(), Pi().inverse()))
    expected = listOf(logNum2, logNum2, logNum3, logNum4)
    assertEquals(expected, term.logs)
}

internal fun runGetPiCountTests() {
    // zero
    var expected = 0

    var term = Term.fromValue(one)
    assertEquals(expected, term.piCount)

    term = Term.fromValue(ExactFraction.TEN)
    assertEquals(expected, term.piCount)

    term = Term.fromValues(one, listOf(Pi(), Pi().inverse()))
    assertEquals(expected, term.piCount)

    term = Term.fromValues(one, listOf(Pi(), Pi().inverse(), Pi(), Pi().inverse(), Pi(), Pi().inverse()))
    assertEquals(expected, term.piCount)

    term = Term.fromValues(one, listOf(logNum1, logNum2), listOf(Sqrt(ExactFraction(64, 9))))
    assertEquals(expected, term.piCount)

    term = Term.fromValues(one, listOf(logNum3, logNum4, logNum2), listOf(Pi().inverse(), Pi(), Pi().inverse(), Pi()))
    assertEquals(expected, term.piCount)

    // just pi
    term = Term.fromValues(one, listOf(Pi()))
    expected = 1
    assertEquals(expected, term.piCount)

    term = Term.fromValues(one, listOf(Pi().inverse()))
    expected = -1
    assertEquals(expected, term.piCount)

    term = Term.fromValues(one, listOf(Pi().inverse(), Pi().inverse(), Pi().inverse()))
    expected = -3
    assertEquals(expected, term.piCount)

    term = Term.fromValues(one, listOf(Pi(), Pi().inverse(), Pi(), Pi(), Pi().inverse()))
    expected = 1
    assertEquals(expected, term.piCount)

    // mix
    term = Term.fromValues(one, listOf(logNum2), listOf(Sqrt(2)), listOf(Pi().inverse()))
    expected = -1
    assertEquals(expected, term.piCount)

    term = Term.fromValues(ExactFraction.EIGHT, listOf(logNum3, logNum2), listOf(Sqrt(36)), listOf(Pi(), Pi(), Pi().inverse(), Pi()))
    expected = 2
    assertEquals(expected, term.piCount)
}

internal fun runGetSquareRootsTests() {
    // zero
    var expected: List<Sqrt> = emptyList()

    var term = Term.ONE
    assertEquals(expected, term.squareRoots)

    term = Term.fromValue(ExactFraction.TEN)
    assertEquals(expected, term.squareRoots)

    term = Term.fromValues(listOf(logNum1, logNum2), listOf(Pi(), Pi().inverse()))
    assertEquals(expected, term.squareRoots)

    // just sqrt
    term = Term.fromValue(Sqrt(4))
    expected = listOf(Sqrt(4))
    assertEquals(expected, term.squareRoots)

    term = Term.fromValues(one, listOf(Sqrt(4), Sqrt(4)))
    expected = listOf(Sqrt(4), Sqrt(4))
    assertEquals(expected, term.squareRoots)

    term = Term.fromValues(one, listOf(Sqrt(ExactFraction(9, 23)), Sqrt(ExactFraction(23, 9))))
    expected = listOf(Sqrt(ExactFraction(9, 23)), Sqrt(ExactFraction(23, 9)))
    assertEquals(expected, term.squareRoots)

    term = Term.fromValues(one, listOf(Sqrt.ONE, Sqrt(97), Sqrt(ExactFraction(9, 25))))
    expected = listOf(Sqrt.ONE, Sqrt(97), Sqrt(ExactFraction(9, 25)))
    assertEquals(expected, term.squareRoots)

    // mix
    term = Term.fromValues(listOf(logNum2), listOf(Sqrt(2)), listOf(Pi().inverse()))
    expected = listOf(Sqrt(2))
    assertEquals(expected, term.squareRoots)

    term = Term.fromValues(ExactFraction.EIGHT, listOf(logNum1, logNum2), listOf(Sqrt.ONE, Sqrt(97), Sqrt(ExactFraction(9, 25))), listOf(Pi()))
    expected = listOf(Sqrt.ONE, Sqrt(97), Sqrt(ExactFraction(9, 25)))
    assertEquals(expected, term.squareRoots)
}
