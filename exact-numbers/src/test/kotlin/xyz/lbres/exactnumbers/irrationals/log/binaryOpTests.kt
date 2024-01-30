package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.exactnumbers.testutils.assertDivByZero
import xyz.lbres.expressions.term.Term
import kotlin.test.assertEquals

private val one = ExactFraction.ONE

fun runTimesTests() {
    // Log
    var log1 = Log.ZERO
    var log2 = Log.ZERO
    var expected = Term(one, listOf(Log.ZERO, Log.ZERO))
    assertEquals(expected, log1 * log2)

    log1 = Log(15, 5)
    log2 = Log.ZERO
    expected = Term(one, listOf(Log(15, 5), Log.ZERO))
    assertEquals(expected, log1 * log2)

    log1 = Log(15)
    log2 = Log(ExactFraction(1, 15))
    expected = Term(one, listOf(Log(15), Log(ExactFraction(1, 15))))
    assertEquals(expected, log1 * log2)

    log1 = Log(32, 2)
    log2 = Log(100, isDivided = true)
    expected = Term(one, listOf(Log(32, 2), Log(100, isDivided = true)))
    assertEquals(expected, log1 * log2)

    // Pi
    log1 = Log(91)
    var pi = Pi()
    expected = Term(one, listOf(Log(91), Pi()))
    assertEquals(expected, log1 * pi)

    log1 = Log(91)
    pi = Pi(true)
    expected = Term(one, listOf(Log(91), Pi(true)))
    assertEquals(expected, log1 * pi)

    // Sqrt
    log1 = Log(100, 2)
    var sqrt = Sqrt.ZERO
    expected = Term(one, listOf(Log(100, 2), Sqrt.ZERO))
    assertEquals(expected, log1 * sqrt)

    log1 = Log(9)
    sqrt = Sqrt(81)
    expected = Term(one, listOf(Log(9), Sqrt(81)))
    assertEquals(expected, log1 * sqrt)
}

fun runDivTests() {
    // Log
    assertDivByZero { Log.ONE / Log.ZERO }

    var log1 = Log(100)
    var log2 = Log.ONE
    var expected = Term(one, listOf(Log(100), Log(10, 10, true)))
    assertEquals(expected, log1 / log2)

    log1 = Log(15)
    log2 = Log(15)
    expected = Term(one, listOf(Log(15), Log(15, isDivided = true)))
    assertEquals(expected, log1 / log2)

    log1 = Log(100, 2)
    log2 = Log(100, 2, true)
    expected = Term(one, listOf(Log(100, 2), Log(100, 2)))
    assertEquals(expected, log1 / log2)

    log1 = Log(ExactFraction(2, 9))
    log2 = Log(ExactFraction(45, 88), 9)
    expected = Term(one, listOf(Log(ExactFraction(2, 9)), Log(ExactFraction(45, 88), 9, true)))
    assertEquals(expected, log1 / log2)

    // Pi
    log1 = Log(99, 3, true)
    var pi = Pi()
    expected = Term(one, listOf(Log(99, 3, true), Pi(true)))
    assertEquals(expected, log1 / pi)

    log1 = Log.ONE
    pi = Pi(true)
    expected = Term(one, listOf(Log.ONE, Pi()))
    assertEquals(expected, log1 / pi)

    // Sqrt
    assertDivByZero { Log.ONE / Sqrt.ZERO }

    log1 = Log(80)
    var sqrt = Sqrt.ONE
    expected = Term(one, listOf(Log(80), Sqrt.ONE))
    assertEquals(expected, log1 / sqrt)

    log1 = Log(80)
    sqrt = Sqrt(ExactFraction(8, 9))
    expected = Term(one, listOf(Log(80), Sqrt(ExactFraction(9, 8))))
    assertEquals(expected, log1 / sqrt)
}
