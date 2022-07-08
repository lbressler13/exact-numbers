package exactnumbers.irrationals.logs

import exactnumbers.exactfraction.ExactFraction
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals

internal class LogProductTest {
    @Test
    internal fun testConstructor() {
        // error
        assertFailsWith<Exception>("LogProduct must contain at least one value") { LogProduct(listOf()) }

        // zero
        val zero = listOf(LogNum.ZERO)

        var logs = listOf(LogNum.ZERO)
        var product = LogProduct(logs)
        assertEquals(zero, product.logs)
        assertEquals(ExactFraction.ZERO, product.coefficient)

        logs = listOf(LogNum(ExactFraction(14)), LogNum(ExactFraction(17)), LogNum.ZERO)
        product = LogProduct(logs)
        assertEquals(zero, product.logs)
        assertEquals(ExactFraction.ZERO, product.coefficient)

        logs = listOf(LogNum(ExactFraction(14)), LogNum(ExactFraction(17)), LogNum.ZERO)
        product = LogProduct(logs, ExactFraction.FOUR)
        assertEquals(zero, product.logs)
        assertEquals(ExactFraction.ZERO, product.coefficient)

        logs = listOf(LogNum(ExactFraction(14)), LogNum(ExactFraction(17)))
        product = LogProduct(logs, ExactFraction.ZERO)
        assertEquals(zero, product.logs)
        assertEquals(ExactFraction.ZERO, product.coefficient)

        // other
        logs = listOf(LogNum.ONE)
        assertEquals(logs, LogProduct(logs).logs)

        logs = listOf(LogNum(ExactFraction.TWO), LogNum(ExactFraction(11)))
        product = LogProduct(logs)
        assertEquals(logs, product.logs)
        assertEquals(ExactFraction.ONE, product.coefficient)

        logs = listOf(
            LogNum(ExactFraction(11)),
            LogNum(ExactFraction(2, 15)),
            LogNum(ExactFraction(11)),
            LogNum(ExactFraction(18, 5))
        )
        product = LogProduct(logs)
        assertEquals(logs, product.logs)
        assertEquals(ExactFraction.ONE, product.coefficient)

        // with coefficient
        logs = listOf(
            LogNum(ExactFraction(14)),
            LogNum(ExactFraction(17, 7)),
            LogNum(ExactFraction.TEN),
            LogNum(ExactFraction(90, 49)),
        )
        var coefficient = ExactFraction.FOUR
        product = LogProduct(logs, coefficient)
        assertEquals(logs, product.logs)
        assertEquals(coefficient, product.coefficient)

        logs = listOf(LogNum.ONE, LogNum(ExactFraction.TWO))
        coefficient = ExactFraction(-2058)
        product = LogProduct(logs, coefficient)
        assertEquals(logs, product.logs)
        assertEquals(coefficient, product.coefficient)

        logs = listOf(
            LogNum(ExactFraction(107)),
            LogNum(ExactFraction(14)),
            LogNum(ExactFraction(1000, 1001))
        )
        coefficient = ExactFraction(-13, 32)
        product = LogProduct(logs, coefficient)
        assertEquals(logs, product.logs)
        assertEquals(coefficient, product.coefficient)
    }

    @Test
    internal fun testEquals() {
        // equal
        var product1 = LogProduct.ZERO
        assertEquals(product1, product1)

        product1 = LogProduct(listOf(LogNum(ExactFraction.TWO), LogNum(ExactFraction(15))))
        assertEquals(product1, product1)

        product1 = LogProduct(
            listOf(LogNum(ExactFraction.TWO), LogNum(ExactFraction(15, 22))), ExactFraction(12)
        )
        assertEquals(product1, product1)

        product1 = LogProduct(
            listOf(
                LogNum(ExactFraction(1000, 43)),
                LogNum.ONE,
                LogNum(ExactFraction(101)),
                LogNum(ExactFraction(12, 5))
            )
        )
        var product2 = LogProduct(
            listOf(
                LogNum.ONE,
                LogNum(ExactFraction(12, 5)),
                LogNum(ExactFraction(1000, 43)),
                LogNum(ExactFraction(101))
            )
        )
        assertEquals(product1, product1)
        assertEquals(product2, product2)
        assertEquals(product2, product1)

        product1 = LogProduct(
            listOf(LogNum(ExactFraction(101)), LogNum(ExactFraction(1000))),
            ExactFraction(-18, 5)
        )
        product2 = LogProduct(
            listOf(LogNum(ExactFraction(1000)), LogNum(ExactFraction(101))),
            ExactFraction(-18, 5)
        )
        assertEquals(product1, product1)
        assertEquals(product2, product2)
        assertEquals(product2, product1)

        // not equal
        product1 = LogProduct.ZERO
        product2 = LogProduct.ONE
        assertNotEquals(product1, product2)
        assertNotEquals(product2, product1)

        product1 = LogProduct(listOf(LogNum(ExactFraction.TWO)))
        product2 = LogProduct(listOf(LogNum(ExactFraction(15)), LogNum(ExactFraction.TWO)))
        assertNotEquals(product1, product2)
        assertNotEquals(product2, product1)

        product1 = LogProduct(
            listOf(LogNum(ExactFraction(15, 7)), LogNum(ExactFraction.TWO)),
            ExactFraction.HALF
        )
        product2 = LogProduct(
            listOf(LogNum(ExactFraction(15, 7)), LogNum(ExactFraction.TWO)),
            -ExactFraction.HALF
        )
        assertNotEquals(product1, product2)
        assertNotEquals(product2, product1)

        product1 = LogProduct(listOf(LogNum(ExactFraction(15)), LogNum(ExactFraction.TWO)))
        product2 = LogProduct(
            listOf(LogNum(ExactFraction(15)), LogNum(ExactFraction.TWO)),
            ExactFraction.THREE
        )
        assertNotEquals(product1, product2)
        assertNotEquals(product2, product1)

        product1 = LogProduct(
            listOf(LogNum(ExactFraction.SEVEN), LogNum(ExactFraction(12)), LogNum(ExactFraction(42)))
        )

        product2 = LogProduct(
            listOf(
                LogNum.ONE,
                LogNum(ExactFraction(1000, 99)),
                LogNum(ExactFraction.TEN),
                LogNum(ExactFraction(101))
            )
        )
        assertNotEquals(product1, product2)
        assertNotEquals(product2, product1)

        product1 = LogProduct(listOf(LogNum(ExactFraction(15, 4))))
        product2 = LogProduct(listOf(LogNum(ExactFraction(4, 15))))
        assertNotEquals(product1, product2)
        assertNotEquals(product2, product1)

        product1 = LogProduct(listOf(LogNum(ExactFraction(1, 15)), LogNum(ExactFraction(8, 7))))
        product2 = LogProduct(listOf(LogNum(ExactFraction(1, 15))), ExactFraction(8, 7))
        assertNotEquals(product1, product2)
        assertNotEquals(product2, product1)
    }

    @Test
    internal fun testToString() {
        var product = LogProduct.ZERO
        var expected = "0x${LogNum.ZERO}"
        assertEquals(expected, product.toString())

        product = LogProduct(listOf(LogNum.ONE, LogNum(ExactFraction(17)), LogNum(ExactFraction(109))))
        expected = "1x${LogNum.ONE}x${LogNum(ExactFraction(17))}x${LogNum(ExactFraction(109))}"
        assertEquals(expected, product.toString())

        product = LogProduct(
            listOf(LogNum(ExactFraction(17)), LogNum(ExactFraction(109, 3))),
            ExactFraction.FOUR
        )
        expected = "4x${LogNum(ExactFraction(17))}x${LogNum(ExactFraction(109, 3))}"
        assertEquals(expected, product.toString())

        product = LogProduct(
            listOf(LogNum(ExactFraction(17)), LogNum(ExactFraction(109))),
            -ExactFraction.FOUR
        )
        expected = "-4x${LogNum(ExactFraction(17))}x${LogNum(ExactFraction(109))}"
        assertEquals(expected, product.toString())

        product = LogProduct(
            listOf(LogNum(ExactFraction(17, 4)), LogNum(ExactFraction(109, 5))),
            ExactFraction(13, 3)
        )
        expected = "${ExactFraction(13, 3)}x${LogNum(ExactFraction(17, 4))}" +
            "x${LogNum(ExactFraction(109, 5))}"
        assertEquals(expected, product.toString())
    }

    @Test internal fun testIsZero() = runIsZeroTests()
    @Test internal fun testUnaryMinus() = runUnaryMinusTests()
    @Test internal fun testUnaryPlus() = runUnaryPlusTests()

    @Test internal fun testGetSimplified() = runGetSimplifiedTests()
    @Test internal fun testTimes() = runTimesTests()
}
