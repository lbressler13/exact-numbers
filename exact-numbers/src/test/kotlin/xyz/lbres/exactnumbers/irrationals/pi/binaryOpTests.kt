package xyz.lbres.exactnumbers.irrationals.pi

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.exactnumbers.testutils.TestNumber
import xyz.lbres.exactnumbers.testutils.assertDivByZero
import kotlin.test.assertEquals

private val one = ExactFraction.ONE

fun runTimesTests() {
    val pi = Pi()
    val piInverse = Pi(true)

    // zero
    assertEquals(Term.ZERO, pi * Log.ZERO)
    assertEquals(Term.ZERO, pi * Sqrt.ZERO)
    assertEquals(Term.ZERO, pi * ExactFraction.ZERO)
    assertEquals(Term.ZERO, pi * TestNumber(ExactFraction.ZERO))

    // pi only
    var expected = Term.fromValues(one, listOf(pi, pi))
    assertEquals(expected, pi * pi)

    expected = Term.fromValues(one, listOf(pi, piInverse))
    assertEquals(expected, pi * piInverse)
    assertEquals(expected, piInverse * pi)

    // exact fraction
    var ef = ExactFraction.HALF
    expected = Term.fromValues(ef, listOf(pi))
    assertEquals(expected, pi * ef)

    ef = ExactFraction(1000)
    expected = Term.fromValues(ef, listOf(piInverse))
    assertEquals(expected, piInverse * ef)

    // log
    var log = Log(ExactFraction(33, 14), 5, true)
    expected = Term.fromValues(one, listOf(log, pi))
    assertEquals(expected, pi * log)

    log = Log(ExactFraction(1, 100))
    expected = Term.fromValues(one, listOf(log, piInverse))
    assertEquals(expected, piInverse * log)

    // sqrt
    var sqrt = Sqrt(ExactFraction(19, 5))
    expected = Term.fromValues(one, listOf(sqrt, pi))
    assertEquals(expected, pi * sqrt)

    sqrt = Sqrt(3)
    expected = Term.fromValues(one, listOf(sqrt, piInverse))
    assertEquals(expected, piInverse * sqrt)

    // other
    val testNumber = TestNumber(ExactFraction(18))
    expected = Term.fromValues(one, listOf(testNumber, piInverse))
    assertEquals(expected, piInverse * testNumber)
}

fun runDivTests() {
    val pi = Pi()
    val piInverse = Pi(true)

    // zero
    assertDivByZero { pi / Log.ZERO }
    assertDivByZero { pi / Sqrt.ZERO }
    assertDivByZero { pi / ExactFraction.ZERO }
    assertDivByZero { pi / TestNumber(ExactFraction.ZERO) }

    // pi only
    var expected = Term.fromValues(one, listOf(pi, piInverse))
    assertEquals(expected, pi / pi)

    expected = Term.fromValues(one, listOf(pi, pi))
    assertEquals(expected, pi / piInverse)

    expected = Term.fromValues(one, listOf(piInverse, pi))
    assertEquals(expected, piInverse / piInverse)

    // exact fraction
    expected = Term.fromValues(ExactFraction.TWO, listOf(pi))
    assertEquals(expected, pi / ExactFraction.HALF)

    expected = Term.fromValues(ExactFraction(1, 1000), listOf(piInverse))
    assertEquals(expected, piInverse / ExactFraction(1000))

    // log
    var log = Log(ExactFraction(33, 14), 5).inverse()
    expected = Term.fromValues(one, listOf(log.inverse(), pi))
    assertEquals(expected, pi / log)

    log = Log(ExactFraction(1, 100))
    expected = Term.fromValues(one, listOf(log.inverse(), piInverse))
    assertEquals(expected, piInverse / log)

    // sqrt
    var sqrt = Sqrt(ExactFraction(19, 5))
    expected = Term.fromValues(one, listOf(sqrt.inverse(), pi))
    assertEquals(expected, pi / sqrt)

    sqrt = Sqrt(3)
    expected = Term.fromValues(one, listOf(sqrt.inverse(), piInverse))
    assertEquals(expected, piInverse / sqrt)

    // other
    val testNumber = TestNumber(ExactFraction(18))
    expected = Term.fromValues(one, listOf(piInverse, testNumber.inverse()))
    assertEquals(expected, piInverse / testNumber)
}
