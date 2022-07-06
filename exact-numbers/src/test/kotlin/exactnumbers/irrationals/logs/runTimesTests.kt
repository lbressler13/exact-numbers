package exactnumbers.irrationals.logs

import exactnumbers.exactfraction.ExactFraction
import kotlin.test.assertEquals

internal fun runTimesTests() {
    // LogNum
    // zero
    var expected = LogProduct.ZERO

    var product = LogProduct.ZERO
    var num = LogNum.ZERO
    assertEquals(expected, product * num)

    product = LogProduct.ZERO
    num = LogNum(ExactFraction(12))
    assertEquals(expected, product * num)

    product = LogProduct(listOf(LogNum.ONE, LogNum(ExactFraction.TWO)))
    num = LogNum.ZERO
    assertEquals(expected, product * num)

    // other
    product = LogProduct.ONE
    num = LogNum(ExactFraction.FOUR)
    expected = LogProduct(listOf(LogNum.ONE, num))
    assertEquals(expected, product * num)

    product = LogProduct(listOf(LogNum(ExactFraction(4, 5)), LogNum(ExactFraction.TWO), LogNum.ONE))
    num = LogNum.ONE
    expected = LogProduct(
        listOf(LogNum(ExactFraction(4, 5)), LogNum(ExactFraction.TWO), LogNum.ONE, LogNum.ONE)
    )
    assertEquals(expected, product * num)

    product = LogProduct(listOf(LogNum(ExactFraction.TEN), LogNum(ExactFraction.TWO)), ExactFraction.HALF)
    num = LogNum(ExactFraction(15))
    expected = LogProduct(
        listOf(LogNum(ExactFraction.TEN), LogNum(ExactFraction.TWO), LogNum(ExactFraction(15))),
        ExactFraction.HALF
    )
    assertEquals(expected, product * num)

    product = LogProduct(listOf(LogNum(ExactFraction(3, 5)), LogNum(ExactFraction.TWO)), ExactFraction.HALF)
    num = LogNum(ExactFraction(15, 4))
    expected = LogProduct(
        listOf(LogNum(ExactFraction(3, 5)), LogNum(ExactFraction.TWO), LogNum(ExactFraction(15, 4))),
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

    product2 = LogProduct(listOf(LogNum(ExactFraction(109, 4)), LogNum(ExactFraction.TWO)))
    assertEquals(expected, product * product2)
    assertEquals(expected, product2 * product)

    // other
    product = LogProduct(listOf(LogNum(ExactFraction.TWO)))
    product2 = LogProduct(listOf(LogNum.ONE))
    expected = LogProduct(listOf(LogNum(ExactFraction.TWO), LogNum.ONE))
    assertEquals(expected, product * product2)
    assertEquals(expected, product2 * product)

    product = LogProduct(listOf(LogNum(ExactFraction(14))))
    product2 = LogProduct(
        listOf(LogNum(ExactFraction(11, 7)), LogNum(ExactFraction(200, 3)), LogNum(ExactFraction.FIVE))
    )
    expected = LogProduct(
        listOf(
            LogNum(ExactFraction(14)), LogNum(ExactFraction(11, 7)), LogNum(ExactFraction(200, 3)), LogNum(ExactFraction.FIVE)
        )
    )
    assertEquals(expected, product * product2)
    assertEquals(expected, product2 * product)

    product = LogProduct(listOf(LogNum(ExactFraction(14))), ExactFraction.FOUR)
    product2 = LogProduct(
        listOf(LogNum(ExactFraction(11)), LogNum(ExactFraction(200)), LogNum(ExactFraction.FIVE))
    )
    expected = LogProduct(
        listOf(
            LogNum(ExactFraction(14)),
            LogNum(ExactFraction(11)),
            LogNum(ExactFraction(200)),
            LogNum(ExactFraction.FIVE)
        ),
        ExactFraction.FOUR
    )
    assertEquals(expected, product * product2)
    assertEquals(expected, product2 * product)

    product = LogProduct(
        listOf(LogNum(ExactFraction(14)), LogNum(ExactFraction(510015, 16)), LogNum(ExactFraction.TWO)),
        ExactFraction(-17, 7)
    )
    product2 = LogProduct(
        listOf(LogNum(ExactFraction(11)), LogNum(ExactFraction(200, 503)), LogNum(ExactFraction.FIVE)),
        ExactFraction(3, 4)
    )
    expected = LogProduct(
        listOf(
            LogNum(ExactFraction(14)), LogNum(ExactFraction(510015, 16)), LogNum(ExactFraction.TWO),
            LogNum(ExactFraction(11)), LogNum(ExactFraction(200, 503)), LogNum(ExactFraction.FIVE),
        ),
        ExactFraction(-51, 28)
    )
    assertEquals(expected, product * product2)
    assertEquals(expected, product2 * product)
}
