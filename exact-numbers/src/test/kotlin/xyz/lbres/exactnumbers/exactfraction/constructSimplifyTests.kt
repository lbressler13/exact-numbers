package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.testutils.assertDivByZero
import java.math.BigInteger
import kotlin.test.assertEquals

// Numerator and denominator are explicitly checked to ensure correct initialization

fun runConstructorTests() {
    // error
    assertDivByZero { ExactFraction(BigInteger.ONE, BigInteger.ZERO) }

    // numerator of 0
    var ef = ExactFraction(BigInteger.ZERO, BigInteger.ONE)
    assertEquals(BigInteger.ZERO, ef.numerator)
    assertEquals(BigInteger.ONE, ef.denominator)

    // positive whole
    ef = ExactFraction(BigInteger("4"), BigInteger.ONE)
    assertEquals(BigInteger("4"), ef.numerator)
    assertEquals(BigInteger.ONE, ef.denominator)

    // positive fraction < 1
    ef = ExactFraction(BigInteger("7"), BigInteger("18"))
    assertEquals(BigInteger("7"), ef.numerator)
    assertEquals(BigInteger("18"), ef.denominator)

    // positive fraction > 1
    ef = ExactFraction(BigInteger("4"), BigInteger("3"))
    assertEquals(BigInteger("4"), ef.numerator)
    assertEquals(BigInteger("3"), ef.denominator)

    // negative whole
    ef = ExactFraction(BigInteger("-4"), BigInteger.ONE)
    assertEquals(BigInteger("-4"), ef.numerator)
    assertEquals(BigInteger.ONE, ef.denominator)

    // negative fraction > -1
    ef = ExactFraction(-BigInteger.ONE, BigInteger("3"))
    assertEquals(-BigInteger.ONE, ef.numerator)
    assertEquals(BigInteger("3"), ef.denominator)

    // negative fraction < -1
    ef = ExactFraction(BigInteger("-7"), BigInteger("4"))
    assertEquals(BigInteger("-7"), ef.numerator)
    assertEquals(BigInteger("4"), ef.denominator)
}

fun runSimplifyTests() {
    runSimplifyZeroTests()
    runSimplifyGCDTests()
    runSimplifySignTests()

    // multiple simplifications
    var ef = ExactFraction(BigInteger("3"), BigInteger("-9"))
    assertEquals(-BigInteger.ONE, ef.numerator)
    assertEquals(BigInteger("3"), ef.denominator)

    ef = ExactFraction(BigInteger("-18"), BigInteger("-12"))
    assertEquals(BigInteger("3"), ef.numerator)
    assertEquals(BigInteger.TWO, ef.denominator)
}

private fun runSimplifyZeroTests() {
    var ef = ExactFraction(BigInteger.ZERO, BigInteger.TWO)
    assertEquals(BigInteger.ZERO, ef.numerator)
    assertEquals(BigInteger.ONE, ef.denominator)

    ef = ExactFraction(BigInteger.ZERO, BigInteger("6"))
    assertEquals(BigInteger.ZERO, ef.numerator)
    assertEquals(BigInteger.ONE, ef.denominator)

    ef = ExactFraction(BigInteger.ZERO, BigInteger("-6"))
    assertEquals(BigInteger.ZERO, ef.numerator)
    assertEquals(BigInteger.ONE, ef.denominator)
}

private fun runSimplifySignTests() {
    var ef = ExactFraction(BigInteger("-3"), BigInteger("-4"))
    assertEquals(BigInteger("3"), ef.numerator)
    assertEquals(BigInteger("4"), ef.denominator)

    ef = ExactFraction(BigInteger.ONE, BigInteger("-3"))
    assertEquals(-BigInteger.ONE, ef.numerator)
    assertEquals(BigInteger("3"), ef.denominator)

    ef = ExactFraction(BigInteger.ONE, BigInteger("3"))
    assertEquals(BigInteger.ONE, ef.numerator)
    assertEquals(BigInteger("3"), ef.denominator)

    ef = ExactFraction(BigInteger("-5"), BigInteger.TWO)
    assertEquals(BigInteger("-5"), ef.numerator)
    assertEquals(BigInteger.TWO, ef.denominator)
}

private fun runSimplifyGCDTests() {
    var ef = ExactFraction(BigInteger("48"), BigInteger("10"))
    assertEquals(BigInteger("24"), ef.numerator)
    assertEquals(BigInteger("5"), ef.denominator)

    ef = ExactFraction(BigInteger("-462"), BigInteger("1071"))
    assertEquals(BigInteger("-22"), ef.numerator)
    assertEquals(BigInteger("51"), ef.denominator)

    ef = ExactFraction(BigInteger("5"), BigInteger("9"))
    assertEquals(BigInteger("5"), ef.numerator)
    assertEquals(BigInteger("9"), ef.denominator)

    ef = ExactFraction(BigInteger("9"), BigInteger("3"))
    assertEquals(BigInteger("3"), ef.numerator)
    assertEquals(BigInteger("1"), ef.denominator)

    ef = ExactFraction(BigInteger("10"), BigInteger("100"))
    assertEquals(BigInteger("1"), ef.numerator)
    assertEquals(BigInteger("10"), ef.denominator)

    ef = ExactFraction(BigInteger.TWO, BigInteger.TWO)
    assertEquals(BigInteger.ONE, ef.numerator)
    assertEquals(BigInteger.ONE, ef.denominator)

    ef = ExactFraction(-BigInteger.TWO, BigInteger.TWO)
    assertEquals(-BigInteger.ONE, ef.numerator)
    assertEquals(BigInteger.ONE, ef.denominator)
}
