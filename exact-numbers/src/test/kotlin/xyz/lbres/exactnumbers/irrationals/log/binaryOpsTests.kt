package xyz.lbres.exactnumbers.irrationals.log

import assertDivByZero
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.expressions.term.Term
import kotlin.test.assertEquals

fun runTimesTests() {
    // zero
    assertEquals(Term.ZERO, Log.ZERO * Log.ONE)
    assertEquals(Term.ZERO, Log.ONE * Sqrt.ZERO)

    // log only
    var log1 = Log(ExactFraction(4, 5))
    var log2 = Log(ExactFraction(5, 4), true)
    var expected = Term.fromValues(listOf(log1, log2))
    assertEquals(expected, log1 * log2)

    log1 = Log(ExactFraction(4, 5), 3)
    log2 = Log(ExactFraction(5, 4))
    expected = Term.fromValues(listOf(log1, log2))
    assertEquals(expected, log1 * log2)

    // pi
    log1 = Log(ExactFraction(25), 4)
    var pi = Pi(true)
    expected = Term.fromValues(listOf(log1), listOf(pi))
    assertEquals(expected, log1 * pi)

    log1 = Log(ExactFraction(25, 92))
    pi = Pi()
    expected = Term.fromValues(listOf(log1), listOf(pi))
    assertEquals(expected, log1 * pi)

    // sqrt
    log1 = Log(15)
    var sqrt = Sqrt(256)
    expected = Term.fromValues(listOf(log1), listOf(sqrt))
    assertEquals(expected, log1 * sqrt)

    log1 = Log(ExactFraction(25), 4, true)
    sqrt = Sqrt(ExactFraction(25, 92))
    expected = Term.fromValues(listOf(log1), listOf(sqrt))
    assertEquals(expected, log1 * sqrt)
}

fun runDivTests() {
    // zero
    assertDivByZero { Log.ONE / Log.ZERO }
    assertDivByZero { Log.ONE / Sqrt.ZERO }

    // log only
    var log1 = Log(ExactFraction.EIGHT)
    var log2 = Log(ExactFraction(15, 4), 7)
    var expected = Term.fromValues(listOf(log1, log2.swapDivided()))
    assertEquals(expected, log1 / log2)

    log1 = Log(ExactFraction(1, 7))
    log2 = Log(ExactFraction.FOUR, true)
    expected = Term.fromValues(listOf(log1, log2.swapDivided()))
    assertEquals(expected, log1 / log2)

    log1 = Log(ExactFraction(1, 7), true)
    log2 = Log(ExactFraction.FOUR, true)
    expected = Term.fromValues(listOf(log1, log2.swapDivided()))
    assertEquals(expected, log1 / log2)

    // pi
    log1 = Log(ExactFraction.HALF)
    var pi = Pi(true)
    expected = Term.fromValues(listOf(log1), listOf(Pi()))
    assertEquals(expected, log1 / pi)

    log1 = Log(100, 5)
    pi = Pi()
    expected = Term.fromValues(listOf(log1), listOf(Pi(true)))
    assertEquals(expected, log1 / pi)

    // sqrt
    log1 = Log(ExactFraction(4, 25), true)
    var sqrt = Sqrt(ExactFraction(25, 4))
    expected = Term.fromValues(listOf(log1), listOf(sqrt.swapDivided()))
    assertEquals(expected, log1 / sqrt)

    log1 = Log(10010100, true)
    sqrt = Sqrt(13)
    expected = Term.fromValues(listOf(log1), listOf(sqrt.swapDivided()))
    assertEquals(expected, log1 / sqrt)
}
