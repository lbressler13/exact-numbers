package exactnumbers.irrationals.logs

import exactnumbers.exactfraction.ExactFraction
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runUnaryMinusTests() {
    var product = LogProduct.ZERO
    var expected = LogProduct.ZERO
    assertEquals(expected, -product)

    product = LogProduct(listOf(LogNum.ONE, LogNum(ExactFraction.HALF)))
    expected = LogProduct(listOf(LogNum.ONE, LogNum(ExactFraction.HALF)), ExactFraction.NEG_ONE)
    assertEquals(expected, -product)

    product = LogProduct(listOf(LogNum.ONE, LogNum(ExactFraction.HALF)), ExactFraction.NEG_ONE)
    expected = LogProduct(listOf(LogNum.ONE, LogNum(ExactFraction.HALF)), ExactFraction.ONE)
    assertEquals(expected, -product)

    product = LogProduct(listOf(LogNum(ExactFraction.TWO), LogNum(ExactFraction(17, 4))), ExactFraction.TWO)
    expected = LogProduct(listOf(LogNum(ExactFraction.TWO), LogNum(ExactFraction(17, 4))), -ExactFraction.TWO)
    assertEquals(expected, -product)

    product = LogProduct(listOf(LogNum(ExactFraction.TWO), LogNum(ExactFraction(17, 4))), ExactFraction(-3, 8))
    expected = LogProduct(listOf(LogNum(ExactFraction.TWO), LogNum(ExactFraction(17, 4))), ExactFraction(3, 8))
    assertEquals(expected, -product)
}

fun runUnaryPlusTests() {
    var product = LogProduct.ZERO
    assertEquals(product, +product)

    product = LogProduct(listOf(LogNum.ONE, LogNum(ExactFraction.HALF)))
    assertEquals(product, +product)

    product = LogProduct(listOf(LogNum.ONE, LogNum(ExactFraction.HALF)), ExactFraction.NEG_ONE)
    assertEquals(product, +product)

    product = LogProduct(listOf(LogNum(ExactFraction.TWO), LogNum(ExactFraction(17, 4))), ExactFraction.TWO)
    assertEquals(product, +product)

    product = LogProduct(listOf(LogNum(ExactFraction.TWO), LogNum(ExactFraction(17, 4))), ExactFraction(-3, 8))
    assertEquals(product, +product)
}

fun runIsZeroTests() {
    var product = LogProduct.ZERO
    assertTrue(product.isZero())

    product = LogProduct(listOf(LogNum.ONE, LogNum(ExactFraction.TWO)), ExactFraction.ZERO)
    assertTrue(product.isZero())

    product = LogProduct(listOf(LogNum.ONE))
    assertFalse(product.isZero())

    product = LogProduct(listOf(LogNum(ExactFraction.TEN), LogNum(ExactFraction(1000)), LogNum(ExactFraction.FIVE)))
    assertFalse(product.isZero())

    product = LogProduct(
        listOf(LogNum(ExactFraction.TEN), LogNum(ExactFraction(1000)), LogNum(ExactFraction.FIVE)),
        ExactFraction(-5, 6)
    )
    assertFalse(product.isZero())

    product = LogProduct(listOf(LogNum(ExactFraction(12, 17)), LogNum(ExactFraction(25, 216))))
    assertFalse(product.isZero())

    product = LogProduct(listOf(LogNum(ExactFraction.TEN), LogNum(ExactFraction.TEN.inverse())))
    assertFalse(product.isZero())
}
