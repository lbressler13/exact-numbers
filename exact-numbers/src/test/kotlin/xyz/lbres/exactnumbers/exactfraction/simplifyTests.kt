package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.exactnumbers.ext.eq
import kotlin.test.assertTrue

fun runSimplifyTests() {
    runSimplifyZeroTests()
    runSimplifyGCDTests()
    runSimplifySignTests()

    // multiple simplifications
    var ef = ExactFraction(3, -9)
    assertTrue(ef.numerator.eq(-1))
    assertTrue(ef.denominator.eq(3))

    ef = ExactFraction(-18, -12)
    assertTrue(ef.numerator.eq(3))
    assertTrue(ef.denominator.eq(2))

    // unchanged
    ef = ExactFraction(0)
    assertTrue(ef.numerator.eq(0))
    assertTrue(ef.denominator.eq(1))

    ef = ExactFraction(-4, 3)
    assertTrue(ef.numerator.eq(-4))
    assertTrue(ef.denominator.eq(3))

    ef = ExactFraction(5, 7)
    assertTrue(ef.numerator.eq(5))
    assertTrue(ef.denominator.eq(7))
}

private fun runSimplifyZeroTests() {
    var ef = ExactFraction(0, 2)
    assertTrue(ef.numerator.eq(0))
    assertTrue(ef.denominator.eq(1))

    ef = ExactFraction(0, 6)
    assertTrue(ef.numerator.eq(0))
    assertTrue(ef.denominator.eq(1))

    ef = ExactFraction(0, -6)
    assertTrue(ef.numerator.eq(0))
    assertTrue(ef.denominator.eq(1))
}

private fun runSimplifySignTests() {
    var ef = ExactFraction(-3, -4)
    assertTrue(ef.numerator.eq(3))
    assertTrue(ef.denominator.eq(4))

    ef = ExactFraction(1, -3)
    assertTrue(ef.numerator.eq(-1))
    assertTrue(ef.denominator.eq(3))

    ef = ExactFraction(1, 3)
    assertTrue(ef.numerator.eq(1))
    assertTrue(ef.denominator.eq(3))

    ef = ExactFraction(-5, 2)
    assertTrue(ef.numerator.eq(-5))
    assertTrue(ef.denominator.eq(2))
}

private fun runSimplifyGCDTests() {
    var ef = ExactFraction(48, 10)
    assertTrue(ef.numerator.eq(24))
    assertTrue(ef.denominator.eq(5))

    ef = ExactFraction(-462, 1071)
    assertTrue(ef.numerator.eq(-22))
    assertTrue(ef.denominator.eq(51))

    ef = ExactFraction(5, 9)
    assertTrue(ef.numerator.eq(5))
    assertTrue(ef.denominator.eq(9))

    ef = ExactFraction(9, 3)
    assertTrue(ef.numerator.eq(3))
    assertTrue(ef.denominator.eq(1))

    ef = ExactFraction(10, 100)
    assertTrue(ef.numerator.eq(1))
    assertTrue(ef.denominator.eq(10))

    ef = ExactFraction(2.toBigInteger(), 2.toBigInteger())
    assertTrue(ef.numerator.eq(1))
    assertTrue(ef.denominator.eq(1))
}
