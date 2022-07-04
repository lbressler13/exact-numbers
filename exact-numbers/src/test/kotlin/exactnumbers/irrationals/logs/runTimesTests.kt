package exactnumbers.irrationals.logs

import exactnumbers.exactfraction.ExactFraction
import java.math.BigInteger
import kotlin.test.assertEquals

internal fun runTimesTests() {
    // LogNum
    // zero
    var expected = LogProduct.ZERO

    var product = LogProduct.ZERO
    var num = LogNum.ZERO
    assertEquals(expected, product * num)

    product = LogProduct.ZERO
    num = LogNum(12.toBigInteger())
    assertEquals(expected, product * num)

    product = LogProduct(listOf(LogNum.ONE, LogNum(BigInteger.TWO)))
    num = LogNum.ZERO
    assertEquals(expected, product * num)

    // other
    product = LogProduct.ONE
    num = LogNum(4.toBigInteger())
    expected = LogProduct(listOf(LogNum.ONE, num))
    assertEquals(expected, product * num)

    product = LogProduct(
        listOf(
            LogNum(BigInteger.TEN),
            LogNum(BigInteger.TWO),
            LogNum.ONE
        )
    )
    num = LogNum.ONE
    expected = LogProduct(
        listOf(
            LogNum(BigInteger.TEN),
            LogNum(BigInteger.TWO),
            LogNum.ONE,
            LogNum.ONE
        )
    )
    assertEquals(expected, product * num)

    product =
        LogProduct(listOf(LogNum(BigInteger.TEN), LogNum(BigInteger.TWO)), ExactFraction.HALF)
    num = LogNum(15.toBigInteger())
    expected = LogProduct(
        listOf(
            LogNum(BigInteger.TEN),
            LogNum(BigInteger.TWO),
            LogNum(15.toBigInteger())
        ),
        ExactFraction.HALF
    )
    assertEquals(expected, product * num)

    // LogProduct
    // zero
    product = LogProduct.ZERO
    expected = LogProduct.ZERO

    var product2 = LogProduct.ONE
    assertEquals(expected, product * product2)
    assertEquals(expected, product2 * product)

    product2 = LogProduct(
        listOf(LogNum(109.toBigInteger()), LogNum(BigInteger.TWO))
    )
    assertEquals(expected, product * product2)
    assertEquals(expected, product2 * product)

    // other
    product = LogProduct(listOf(LogNum(BigInteger.TWO)))
    product2 = LogProduct(listOf(LogNum.ONE))
    expected = LogProduct(listOf(LogNum(BigInteger.TWO), LogNum.ONE))
    assertEquals(expected, product * product2)
    assertEquals(expected, product2 * product)

    product = LogProduct(listOf(LogNum(14.toBigInteger())))
    product2 = LogProduct(
        listOf(
            LogNum(11.toBigInteger()),
            LogNum(200.toBigInteger()),
            LogNum(5.toBigInteger())
        )
    )
    expected = LogProduct(
        listOf(
            LogNum(14.toBigInteger()),
            LogNum(11.toBigInteger()),
            LogNum(200.toBigInteger()),
            LogNum(5.toBigInteger())
        )
    )
    assertEquals(expected, product * product2)
    assertEquals(expected, product2 * product)

    product = LogProduct(listOf(LogNum(14.toBigInteger())), ExactFraction.FOUR)
    product2 = LogProduct(
        listOf(
            LogNum(11.toBigInteger()),
            LogNum(200.toBigInteger()),
            LogNum(5.toBigInteger())
        )
    )
    expected = LogProduct(
        listOf(
            LogNum(14.toBigInteger()),
            LogNum(11.toBigInteger()),
            LogNum(200.toBigInteger()),
            LogNum(5.toBigInteger())
        ), ExactFraction.FOUR
    )
    assertEquals(expected, product * product2)
    assertEquals(expected, product2 * product)

    product = LogProduct(
        listOf(
            LogNum(14.toBigInteger()),
            LogNum(510015.toBigInteger()),
            LogNum(BigInteger.TWO)
        ), ExactFraction(-17, 7)
    )
    product2 = LogProduct(
        listOf(
            LogNum(11.toBigInteger()),
            LogNum(200.toBigInteger()),
            LogNum(5.toBigInteger())
        ), ExactFraction(3, 4)
    )
    expected = LogProduct(
        listOf(
            LogNum(14.toBigInteger()),
            LogNum(510015.toBigInteger()),
            LogNum(BigInteger.TWO),
            LogNum(11.toBigInteger()),
            LogNum(200.toBigInteger()),
            LogNum(5.toBigInteger())
        ), ExactFraction(-51, 28)
    )
    assertEquals(expected, product * product2)
    assertEquals(expected, product2 * product)
}
