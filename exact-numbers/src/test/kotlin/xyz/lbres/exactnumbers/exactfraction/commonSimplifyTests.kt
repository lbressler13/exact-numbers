package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.kotlinutils.pair.TypePair
import java.math.BigInteger
import kotlin.test.assertEquals

fun runCommonSimplifyTests(createSimplifiedPair: (BigInteger, BigInteger) -> TypePair<BigInteger>) {
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
}

private fun runSimplifyGCDTests(createSimplifiedPair: (BigInteger, BigInteger) -> TypePair<BigInteger>) {
    var pair = createSimplifiedPair(BigInteger("48"), BigInteger("10"))
    var expected = Pair(BigInteger("24"), BigInteger("5"))
    assertEquals(expected, pair)

    pair = createSimplifiedPair(BigInteger("-462"), BigInteger("1071"))
    expected = Pair(BigInteger("-22"), BigInteger("51"))
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
