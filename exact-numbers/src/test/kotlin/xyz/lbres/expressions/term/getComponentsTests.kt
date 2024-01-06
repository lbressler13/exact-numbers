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

fun runGetLogsTests() {
    // empty
    var expected: List<Log> = emptyList()

    var term = Term.fromValues(one, emptyList())
    assertEquals(expected, term.logs)

    term = Term.fromValues(ExactFraction.TEN, emptyList())
    assertEquals(expected, term.logs)

    term = Term.fromValues(one, listOf(Sqrt(ExactFraction(64, 9)), Pi(), Pi()))
    assertEquals(expected, term.logs)

    // just logs
    term = Term.fromValues(one, listOf(logNum1))
    expected = listOf(logNum1)
    assertEquals(expected, term.logs)

    term = Term.fromValues(one, listOf(logNum1, logNum1))
    expected = listOf(logNum1, logNum1)
    assertEquals(expected, term.logs)

    term = Term.fromValues(one, listOf(logNum1, logNum1.inverse()))
    expected = listOf(logNum1, logNum1.inverse())
    assertEquals(expected, term.logs)

    term = Term.fromValues(one, listOf(logNum3, logNum4, logNum1))
    expected = listOf(logNum3, logNum4, logNum1)
    assertEquals(expected, term.logs)

    // mix
    term = Term.fromValues(one, listOf(logNum3, Sqrt(2), Pi()))
    expected = listOf(logNum3)
    assertEquals(expected, term.logs)

    term = Term.fromValues(ExactFraction.EIGHT, listOf(logNum2, logNum2, logNum3, logNum4, Sqrt(15), Pi(), Pi().inverse()))
    expected = listOf(logNum2, logNum2, logNum3, logNum4)
    assertEquals(expected, term.logs)
}

fun runGetPiCountTests() {
    // zero
    var expected = 0

    var term = Term.fromValues(one, emptyList())
    assertEquals(expected, term.piCount)

    term = Term.fromValues(ExactFraction.TEN, emptyList())
    assertEquals(expected, term.piCount)

    term = Term.fromValues(one, listOf(Pi(), Pi().inverse()))
    assertEquals(expected, term.piCount)

    term = Term.fromValues(one, listOf(Pi(), Pi().inverse(), Pi(), Pi().inverse(), Pi(), Pi().inverse()))
    assertEquals(expected, term.piCount)

    term = Term.fromValues(one, listOf(logNum1, logNum2, Sqrt(ExactFraction(64, 9))))
    assertEquals(expected, term.piCount)

    term = Term.fromValues(one, listOf(logNum3, logNum4, logNum2, Pi().inverse(), Pi(), Pi().inverse(), Pi()))
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
    term = Term.fromValues(one, listOf(logNum2, Sqrt(2), Pi().inverse()))
    expected = -1
    assertEquals(expected, term.piCount)

    term = Term.fromValues(ExactFraction.EIGHT, listOf(logNum3, logNum2, Sqrt(36), Pi(), Pi(), Pi().inverse(), Pi()))
    expected = 2
    assertEquals(expected, term.piCount)
}

fun runGetSquareRootsTests() {
    // zero
    var expected: List<Sqrt> = emptyList()

    var term = Term.ONE
    assertEquals(expected, term.squareRoots)

    term = Term.fromValues(ExactFraction.TEN, emptyList())
    assertEquals(expected, term.squareRoots)

    term = Term.fromValues(one, listOf(logNum1, logNum2, Pi(), Pi().inverse()))
    assertEquals(expected, term.squareRoots)

    // just sqrt
    term = Term.fromValues(one, listOf(Sqrt(4)))
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
    term = Term.fromValues(one, listOf(logNum2, Sqrt(2), Pi().inverse()))
    expected = listOf(Sqrt(2))
    assertEquals(expected, term.squareRoots)

    term = Term.fromValues(ExactFraction.EIGHT, listOf(logNum1, logNum2, Sqrt.ONE, Sqrt(97), Sqrt(ExactFraction(9, 25)), Pi()))
    expected = listOf(Sqrt.ONE, Sqrt(97), Sqrt(ExactFraction(9, 25)))
    assertEquals(expected, term.squareRoots)
}
