package xyz.lbres.exactnumbers.irrationals.pi

import xyz.lbres.assertDivByZero
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.expressions.term.Term
import kotlin.test.assertEquals

private val one = ExactFraction.ONE

fun runTimesTests() {
    // Log
    var pi1 = Pi()
    var log = Log.ZERO
    var expected = Term(one, listOf(Pi(), Log.ZERO))
    assertEquals(expected, pi1 * log)

    pi1 = Pi(true)
    log = Log(100, 4, true)
    expected = Term(one, listOf(Pi(true), Log(100, 4, true)))
    assertEquals(expected, pi1 * log)

    // Pi
    pi1 = Pi()
    var pi2 = Pi(true)
    expected = Term(one, listOf(Pi(), Pi(true)))
    assertEquals(expected, pi1 * pi2)

    pi1 = Pi(true)
    pi2 = Pi(true)
    expected = Term(one, listOf(Pi(true), Pi(true)))
    assertEquals(expected, pi1 * pi2)

    // Sqrt
    pi1 = Pi()
    var sqrt = Sqrt.ZERO
    expected = Term(one, listOf(Pi(), Sqrt.ZERO))
    assertEquals(expected, pi1 * sqrt)

    pi1 = Pi(true)
    sqrt = Sqrt(1234)
    expected = Term(one, listOf(Pi(true), Sqrt(1234)))
    assertEquals(expected, pi1 * sqrt)
}

fun runDivTests() {
    // Log
    assertDivByZero { Pi() / Log.ZERO }

    var pi1 = Pi()
    var log = Log.ONE
    var expected = Term(one, listOf(Pi(), Log(10, 10, true)))
    assertEquals(expected, pi1 / log)

    pi1 = Pi()
    log = Log(32, 2)
    expected = Term(one, listOf(Pi(), Log(32, 2, true)))
    assertEquals(expected, pi1 / log)

    pi1 = Pi()
    log = Log(ExactFraction(12, 19), true)
    expected = Term(one, listOf(Pi(), Log(ExactFraction(12, 19))))
    assertEquals(expected, pi1 / log)

    // Pi
    pi1 = Pi(true)
    var pi2 = Pi(true)
    expected = Term(one, listOf(Pi(true), Pi()))
    assertEquals(expected, pi1 / pi2)

    pi1 = Pi(true)
    pi2 = Pi()
    expected = Term(one, listOf(Pi(true), Pi(true)))
    assertEquals(expected, pi1 / pi2)

    // Sqrt
    assertDivByZero { Pi() / Sqrt.ZERO }

    pi1 = Pi()
    var sqrt = Sqrt.ONE
    expected = Term(one, listOf(Pi(), Sqrt.ONE))
    assertEquals(expected, pi1 / sqrt)

    pi1 = Pi(true)
    sqrt = Sqrt(ExactFraction(15, 8))
    expected = Term(one, listOf(Pi(true), Sqrt(ExactFraction(8, 15))))
    assertEquals(expected, pi1 / sqrt)
}
