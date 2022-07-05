package exactnumbers.irrationals.logs

import exactnumbers.exactfraction.ExactFraction
import java.math.BigInteger
import kotlin.test.assertEquals

internal fun runGetSimplifiedTests() {
    val zero = LogNum.ZERO
    val one = LogNum.ONE

    // zero
    var expectedLogs = listOf(zero)
    var expectedCoeff = ExactFraction.ZERO

    var product = LogProduct(listOf(zero))
    assertEquals(expectedLogs, product.getSimplified().logs)
    assertEquals(expectedCoeff, product.getSimplified().coefficient)

    product = LogProduct(listOf(zero, zero, zero))
    assertEquals(expectedLogs, product.getSimplified().logs)
    assertEquals(expectedCoeff, product.getSimplified().coefficient)

    product = LogProduct(listOf(zero, one))
    assertEquals(expectedLogs, product.getSimplified().logs)
    assertEquals(expectedCoeff, product.getSimplified().coefficient)

    product = LogProduct(
        listOf(
            LogNum(BigInteger.TWO),
            LogNum(18.toBigInteger()),
            zero,
            LogNum(BigInteger.TEN)
        )
    )
    assertEquals(expectedLogs, product.getSimplified().logs)
    assertEquals(expectedCoeff, product.getSimplified().coefficient)

    // ones
    expectedLogs = listOf(one)

    product = LogProduct(listOf(one))
    expectedCoeff = ExactFraction.ONE
    assertEquals(expectedLogs, product.getSimplified().logs)
    assertEquals(expectedCoeff, product.getSimplified().coefficient)

    product = LogProduct(listOf(one, one, one))
    expectedCoeff = ExactFraction.ONE
    assertEquals(expectedLogs, product.getSimplified().logs)
    assertEquals(expectedCoeff, product.getSimplified().coefficient)

    product = LogProduct(listOf(one, one, one), ExactFraction.HALF)
    expectedCoeff = ExactFraction.HALF
    assertEquals(expectedLogs, product.getSimplified().logs)
    assertEquals(expectedCoeff, product.getSimplified().coefficient)

    expectedLogs = listOf(
        LogNum(BigInteger.TWO),
        LogNum(18.toBigInteger()),
        LogNum(81.toBigInteger())
    )

    product = LogProduct(
        listOf(
            LogNum(BigInteger.TWO),
            LogNum(18.toBigInteger()),
            one,
            LogNum(81.toBigInteger())
        )
    )
    expectedCoeff = ExactFraction.ONE
    assertEquals(expectedLogs, product.getSimplified().logs)
    assertEquals(expectedCoeff, product.getSimplified().coefficient)

    product = LogProduct(
        listOf(
            LogNum(BigInteger.TWO),
            LogNum(18.toBigInteger()),
            one,
            LogNum(81.toBigInteger()),
            one, one, one
        ),
        ExactFraction(-18, 11)
    )
    expectedCoeff = ExactFraction(-18, 11)
    assertEquals(expectedLogs, product.getSimplified().logs)
    assertEquals(expectedCoeff, product.getSimplified().coefficient)

    // all non zero/one
    product = LogProduct(
        listOf(
            LogNum(BigInteger.TWO),
            LogNum(18.toBigInteger()),
            LogNum(81.toBigInteger())
        )
    )
    expectedLogs = listOf(
        LogNum(BigInteger.TWO),
        LogNum(18.toBigInteger()),
        LogNum(81.toBigInteger()),
    )
    expectedCoeff = ExactFraction.ONE
    assertEquals(expectedLogs, product.getSimplified().logs)
    assertEquals(expectedCoeff, product.getSimplified().coefficient)

    product = LogProduct(listOf(LogNum(BigInteger.TWO), LogNum(BigInteger.TWO)))
    expectedLogs = listOf(LogNum(BigInteger.TWO), LogNum(BigInteger.TWO))
    expectedCoeff = ExactFraction.ONE
    assertEquals(expectedLogs, product.getSimplified().logs)
    assertEquals(expectedCoeff, product.getSimplified().coefficient)

    product = LogProduct(
        listOf(LogNum(103.toBigInteger()), LogNum(BigInteger.TWO)),
        -ExactFraction.FOUR
    )
    expectedLogs = listOf(LogNum(103.toBigInteger()), LogNum(BigInteger.TWO))
    expectedCoeff = -ExactFraction.FOUR
    assertEquals(expectedLogs, product.getSimplified().logs)
    assertEquals(expectedCoeff, product.getSimplified().coefficient)
}
