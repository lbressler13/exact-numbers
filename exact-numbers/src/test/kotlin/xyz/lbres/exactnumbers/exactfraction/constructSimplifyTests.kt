package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.kotlinutils.pair.TypePair
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

fun runSimplifyConstructedTests() {
    runCommonSimplifyTests { bi1, bi2 ->
        val ef = ExactFraction(bi1, bi2)
        Pair(ef.numerator, ef.denominator)
    }
}

fun runSimplifyTests() {
    runCommonSimplifyTests { bi1, bi2 -> simplify(Pair(bi1, bi2)) }
}

private fun runCommonSimplifyTests(createSimplifiedPair: (BigInteger, BigInteger) -> TypePair<BigInteger>) {
    runSimplifyZeroTests(createSimplifiedPair)
    runSimplifyGCDTests(createSimplifiedPair)
    runSimplifySignTests(createSimplifiedPair)

    // multiple simplifications
    var pair = createSimplifiedPair(BigInteger("3"), BigInteger("-9"))
    var expected = Pair(BigInteger("-1"), BigInteger("3"))
    assertEquals(expected, pair)

    pair = createSimplifiedPair(BigInteger("-18"), BigInteger("-12"))
    expected = Pair(BigInteger("3"), BigInteger.TWO)
    assertEquals(expected, pair)
}

private fun runSimplifyZeroTests(createSimplifiedPair: (BigInteger, BigInteger) -> TypePair<BigInteger>) {
    var pair = createSimplifiedPair(BigInteger.ZERO, BigInteger.TWO)
    var expected = Pair(BigInteger.ZERO, BigInteger.ONE)
    assertEquals(expected, pair)

    pair = createSimplifiedPair(BigInteger.ZERO, BigInteger("6"))
    expected = Pair(BigInteger.ZERO, BigInteger.ONE)
    assertEquals(expected, pair)

    pair = createSimplifiedPair(BigInteger.ZERO, BigInteger("-6"))
    expected = Pair(BigInteger.ZERO, BigInteger.ONE)
    assertEquals(expected, pair)
}

private fun runSimplifySignTests(createSimplifiedPair: (BigInteger, BigInteger) -> TypePair<BigInteger>) {
    var pair = createSimplifiedPair(BigInteger("-3"), BigInteger("-4"))
    var expected = Pair(BigInteger("3"), BigInteger("4"))
    assertEquals(expected, pair)

    pair = createSimplifiedPair(BigInteger.ONE, BigInteger("-3"))
    expected = Pair(-BigInteger.ONE, BigInteger("3"))
    assertEquals(expected, pair)

    pair = createSimplifiedPair(BigInteger.ONE, BigInteger("3"))
    expected = Pair(BigInteger.ONE, BigInteger("3"))
    assertEquals(expected, pair)

    pair = createSimplifiedPair(BigInteger("-5"), BigInteger.TWO)
    expected = Pair(BigInteger("-5"), BigInteger.TWO)
    assertEquals(expected, pair)
}

private fun runSimplifyGCDTests(createSimplifiedPair: (BigInteger, BigInteger) -> TypePair<BigInteger>) {
    var pair = createSimplifiedPair(BigInteger("48"), BigInteger("10"))
    var expected = Pair(BigInteger("24"), BigInteger("5"))
    assertEquals(expected, pair)

    pair = createSimplifiedPair(BigInteger("-462"), BigInteger("1071"))
    expected = Pair(BigInteger("-22"), BigInteger("51"))
    assertEquals(expected, pair)

    pair = createSimplifiedPair(BigInteger("5"), BigInteger("9"))
    expected = Pair(BigInteger("5"), BigInteger("9"))
    assertEquals(expected, pair)

    pair = createSimplifiedPair(BigInteger("9"), BigInteger("3"))
    expected = Pair(BigInteger("3"), BigInteger("1"))
    assertEquals(expected, pair)

    pair = createSimplifiedPair(BigInteger("10"), BigInteger("100"))
    expected = Pair(BigInteger("1"), BigInteger("10"))
    assertEquals(expected, pair)

    pair = createSimplifiedPair(BigInteger.TWO, BigInteger.TWO)
    expected = Pair(BigInteger.ONE, BigInteger.ONE)
    assertEquals(expected, pair)

    pair = createSimplifiedPair(-BigInteger.TWO, BigInteger.TWO)
    expected = Pair(-BigInteger.ONE, BigInteger.ONE)
    assertEquals(expected, pair)
}
