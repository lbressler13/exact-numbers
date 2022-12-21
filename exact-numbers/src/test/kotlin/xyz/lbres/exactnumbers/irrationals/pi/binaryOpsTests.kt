package xyz.lbres.exactnumbers.irrationals.pi

import assertDivByZero
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.expressions.term.Term
import kotlin.test.assertEquals

internal fun runTimesTests() {
    val pi = Pi()
    val piInverse = Pi().inverse()

    // zero
    assertEquals(Term.ZERO, pi * Log.ZERO)
    assertEquals(Term.ZERO, pi * Sqrt.ZERO)
    assertEquals(Term.ZERO, pi * ExactFraction.ZERO)

    // pi only
    var expected = Term.fromValues(listOf(pi, pi))
    assertEquals(expected, pi * pi)

    expected = Term.fromValues(listOf(pi, piInverse))
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
    var log = Log(ExactFraction(33, 14), 5).inverse()
    expected = Term.fromValues(listOf(log), listOf(pi))
    assertEquals(expected, pi * log)

    log = Log(ExactFraction(1, 100))
    expected = Term.fromValues(listOf(log), listOf(piInverse))
    assertEquals(expected, piInverse * log)

    // sqrt
    var sqrt = Sqrt(ExactFraction(19, 5))
    expected = Term.fromValues(listOf(sqrt), listOf(pi))
    assertEquals(expected, pi * sqrt)

    sqrt = Sqrt(3)
    expected = Term.fromValues(listOf(sqrt), listOf(piInverse))
    assertEquals(expected, piInverse * sqrt)
}

internal fun runDivTests() {
    val pi = Pi()
    val piInverse = Pi().inverse()

    // zero
    assertDivByZero { pi / Log.ZERO }
    assertDivByZero { pi / Sqrt.ZERO }
    assertDivByZero { pi / ExactFraction.ZERO }

    // pi only
    var expected = Term.fromValues(listOf(pi, piInverse))
    assertEquals(expected, pi / pi)

    expected = Term.fromValues(listOf(pi, pi))
    assertEquals(expected, pi / piInverse)

    expected = Term.fromValues(listOf(piInverse, pi))
    assertEquals(expected, piInverse / piInverse)

    // exact fraction
    var ef = ExactFraction.HALF
    expected = Term.fromValues(ef.inverse(), listOf(pi))
    assertEquals(expected, pi / ef)

    ef = ExactFraction(1000)
    expected = Term.fromValues(ef.inverse(), listOf(piInverse))
    assertEquals(expected, piInverse / ef)

    // log
    var log = Log(ExactFraction(33, 14), 5).inverse()
    expected = Term.fromValues(listOf(log.inverse()), listOf(pi))
    assertEquals(expected, pi / log)

    log = Log(ExactFraction(1, 100))
    expected = Term.fromValues(listOf(log.inverse()), listOf(piInverse))
    assertEquals(expected, piInverse / log)

    // sqrt
    var sqrt = Sqrt(ExactFraction(19, 5))
    expected = Term.fromValues(listOf(sqrt.inverse()), listOf(pi))
    assertEquals(expected, pi / sqrt)

    sqrt = Sqrt(3)
    expected = Term.fromValues(listOf(sqrt.inverse()), listOf(piInverse))
    assertEquals(expected, piInverse / sqrt)
}
