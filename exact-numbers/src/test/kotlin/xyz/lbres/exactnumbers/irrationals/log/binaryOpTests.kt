package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.exactnumbers.testutils.TestNumber
import xyz.lbres.exactnumbers.testutils.assertDivByZero
import kotlin.test.assertEquals

private val one = ExactFraction.ONE

fun runTimesTests() {
    // zero
    assertEquals(Term.ZERO, Log.ZERO * Log.ONE)
    assertEquals(Term.ZERO, Log.ONE * Sqrt.ZERO)
    assertEquals(Term.ZERO, Log.ONE * ExactFraction.ZERO)
    assertEquals(Term.ZERO, Log.ONE * TestNumber(ExactFraction.ZERO))

    // log only
    var log1 = Log(ExactFraction(4, 5))
    var log2 = Log(ExactFraction(5), 4)
    var expected = Term.fromValues(one, listOf(log1, log2))
    assertEquals(expected, log1 * log2)

    log1 = Log(ExactFraction(4, 5), 3)
    log2 = Log(ExactFraction(5, 4))
    expected = Term.fromValues(one, listOf(log1, log2))
    assertEquals(expected, log1 * log2)

    // exact fraction
    log1 = Log(ExactFraction(25), 4)
    var ef = ExactFraction(-5)
    expected = Term.fromValues(ef, listOf(log1))
    assertEquals(expected, log1 * ef)

    log1 = Log(1000)
    ef = ExactFraction(100, 213)
    expected = Term.fromValues(ef, listOf(log1))
    assertEquals(expected, log1 * ef)

    // pi
    log1 = Log(ExactFraction(25), 4)
    var pi = Pi().inverse()
    expected = Term.fromValues(one, listOf(log1, pi))
    assertEquals(expected, log1 * pi)

    log1 = Log(ExactFraction(25, 92))
    pi = Pi()
    expected = Term.fromValues(one, listOf(log1, pi))
    assertEquals(expected, log1 * pi)

    // sqrt
    log1 = Log(15)
    var sqrt = Sqrt(256)
    expected = Term.fromValues(one, listOf(log1, sqrt))
    assertEquals(expected, log1 * sqrt)

    log1 = Log(ExactFraction(25), 4)
    sqrt = Sqrt(ExactFraction(25, 92))
    expected = Term.fromValues(one, listOf(log1, sqrt))
    assertEquals(expected, log1 * sqrt)

    // other
    log1 = Log(15)
    val testNumber = TestNumber(ExactFraction(18))
    expected = Term.fromValues(one, listOf(log1, testNumber))
    assertEquals(expected, log1 * testNumber)
}

fun runDivTests() {
    // zero
    assertDivByZero { Log.ONE / Log.ZERO }
    assertDivByZero { Log.ONE / Sqrt.ZERO }
    assertDivByZero { Log.ONE / ExactFraction.ZERO }
    assertDivByZero { Log.ONE / TestNumber(ExactFraction.ZERO) }

    // log only
    var log1 = Log(ExactFraction.EIGHT)
    var log2 = Log(ExactFraction(15, 4), 7)
    var expected = Term.fromValues(one, listOf(log1, log2.inverse()))
    assertEquals(expected, log1 / log2)

    log1 = Log(ExactFraction(1, 7))
    log2 = Log(ExactFraction(1, 4))
    expected = Term.fromValues(one, listOf(log1, log2.inverse()))
    assertEquals(expected, log1 / log2)

    log1 = Log(ExactFraction(1, 7))
    log2 = Log(ExactFraction.FOUR)
    expected = Term.fromValues(one, listOf(log1, log2.inverse()))
    assertEquals(expected, log1 / log2)

    // exact fraction
    log1 = Log(ExactFraction(25), 4)
    var ef = ExactFraction(-5)
    expected = Term.fromValues(ef.inverse(), listOf(log1))
    assertEquals(expected, log1 / ef)

    log1 = Log(1000)
    ef = ExactFraction(100, 213)
    expected = Term.fromValues(ef.inverse(), listOf(log1))
    assertEquals(expected, log1 / ef)

    // pi
    log1 = Log(ExactFraction.HALF)
    var pi = Pi().inverse()
    expected = Term.fromValues(one, listOf(log1, Pi()))
    assertEquals(expected, log1 / pi)

    log1 = Log(100, 5)
    pi = Pi()
    expected = Term.fromValues(one, listOf(log1, Pi().inverse()))
    assertEquals(expected, log1 / pi)

    // sqrt
    log1 = Log(ExactFraction(4, 25))
    var sqrt = Sqrt(ExactFraction(25, 4))
    expected = Term.fromValues(one, listOf(log1, sqrt.inverse()))
    assertEquals(expected, log1 / sqrt)

    log1 = Log(10010100)
    sqrt = Sqrt(13)
    expected = Term.fromValues(one, listOf(log1, sqrt.inverse()))
    assertEquals(expected, log1 / sqrt)

    log1 = Log(15)
    val testNumber = TestNumber(ExactFraction(18))
    expected = Term.fromValues(one, listOf(log1, testNumber.inverse()))
    assertEquals(expected, log1 / testNumber)
}
