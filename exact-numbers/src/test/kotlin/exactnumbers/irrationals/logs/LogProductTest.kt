package exactnumbers.irrationals.logs

import exactnumbers.exactfraction.ExactFraction
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

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

        logs = listOf(LogNum(14.toBigInteger()), LogNum(17.toBigInteger()), LogNum.ZERO)
        product = LogProduct(logs)
        assertEquals(zero, product.logs)
        assertEquals(ExactFraction.ZERO, product.coefficient)

        logs = listOf(LogNum(14.toBigInteger()), LogNum(17.toBigInteger()), LogNum.ZERO)
        product = LogProduct(logs, ExactFraction.FOUR)
        assertEquals(zero, product.logs)
        assertEquals(ExactFraction.ZERO, product.coefficient)

        logs = listOf(LogNum(14.toBigInteger()), LogNum(17.toBigInteger()))
        product = LogProduct(logs, ExactFraction.ZERO)
        assertEquals(zero, product.logs)
        assertEquals(ExactFraction.ZERO, product.coefficient)

        // other
        logs = listOf(LogNum.ONE)
        assertEquals(logs, LogProduct(logs).logs)

        logs = listOf(LogNum(BigInteger.TWO), LogNum(11.toBigInteger()))
        product = LogProduct(logs)
        assertEquals(logs, product.logs)
        assertEquals(ExactFraction.ONE, product.coefficient)

        logs = listOf(LogNum(11.toBigInteger()), LogNum(BigInteger.TWO), LogNum(11.toBigInteger()), LogNum.ONE)
        product = LogProduct(logs)
        assertEquals(logs, product.logs)
        assertEquals(ExactFraction.ONE, product.coefficient)

        // with coefficient
        logs = listOf(
            LogNum(14.toBigInteger()),
            LogNum(17.toBigInteger()),
            LogNum(BigInteger.TEN),
            LogNum(90.toBigInteger())
        )
        var coefficient = ExactFraction.FOUR
        product = LogProduct(logs, coefficient)
        assertEquals(logs, product.logs)
        assertEquals(coefficient, product.coefficient)

        logs = listOf(LogNum.ONE, LogNum(BigInteger.TWO))
        coefficient = ExactFraction(-2058)
        product = LogProduct(logs, coefficient)
        assertEquals(logs, product.logs)
        assertEquals(coefficient, product.coefficient)

        logs = listOf(LogNum(107.toBigInteger()), LogNum(14.toBigInteger()), LogNum(1000.toBigInteger()))
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

        product1 = LogProduct(listOf(LogNum(BigInteger.TWO), LogNum(15.toBigInteger())))
        assertEquals(product1, product1)

        product1 = LogProduct(listOf(LogNum(BigInteger.TWO), LogNum(15.toBigInteger())), ExactFraction(12))
        assertEquals(product1, product1)

        product1 = LogProduct(
            listOf(LogNum(1000.toBigInteger()), LogNum.ONE, LogNum(101.toBigInteger()), LogNum(BigInteger.TEN))
        )
        var product2 = LogProduct(
            listOf(
                LogNum.ONE, LogNum(1000.toBigInteger()), LogNum(BigInteger.TEN), LogNum(101.toBigInteger()),
            )
        )
        assertEquals(product1, product1)
        assertEquals(product2, product2)
        assertEquals(product2, product1)

        product1 = LogProduct(
            listOf(LogNum(101.toBigInteger()), LogNum(1000.toBigInteger())),
            ExactFraction(-18, 5)
        )
        product2 = LogProduct(
            listOf(LogNum(1000.toBigInteger()), LogNum(101.toBigInteger())),
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

        product1 = LogProduct(listOf(LogNum(BigInteger.TWO)))
        product2 = LogProduct(listOf(LogNum(15.toBigInteger()), LogNum(BigInteger.TWO)))
        assertNotEquals(product1, product2)
        assertNotEquals(product2, product1)

        product1 = LogProduct(
            listOf(LogNum(15.toBigInteger()), LogNum(BigInteger.TWO)),
            ExactFraction.HALF
        )
        product2 = LogProduct(
            listOf(LogNum(15.toBigInteger()), LogNum(BigInteger.TWO)),
            -ExactFraction.HALF
        )
        assertNotEquals(product1, product2)
        assertNotEquals(product2, product1)

        product1 = LogProduct(listOf(LogNum(15.toBigInteger()), LogNum(BigInteger.TWO)))
        product2 = LogProduct(
            listOf(LogNum(15.toBigInteger()), LogNum(BigInteger.TWO)),
            ExactFraction.THREE
        )
        assertNotEquals(product1, product2)
        assertNotEquals(product2, product1)

        product1 = LogProduct(listOf(LogNum(7.toBigInteger()), LogNum(12.toBigInteger()), LogNum(42.toBigInteger())))
        product2 = LogProduct(
            listOf(
                LogNum.ONE,
                LogNum(1000.toBigInteger()),
                LogNum(BigInteger.TEN),
                LogNum(101.toBigInteger()),
            )
        )
        assertNotEquals(product1, product2)
        assertNotEquals(product2, product1)
    }

    @Test
    internal fun testIsZero() {
        var product = LogProduct.ZERO
        assertTrue(product.isZero())

        product = LogProduct(listOf(LogNum.ONE, LogNum(BigInteger.TWO)), ExactFraction.ZERO)
        assertTrue(product.isZero())

        product = LogProduct(listOf(LogNum.ONE))
        assertFalse(product.isZero())

        product = LogProduct(listOf(LogNum(BigInteger.TEN), LogNum(1000.toBigInteger()), LogNum(5.toBigInteger())))
        assertFalse(product.isZero())

        product = LogProduct(
            listOf(LogNum(BigInteger.TEN), LogNum(1000.toBigInteger()), LogNum(5.toBigInteger())),
            ExactFraction(-5, 6)
        )
        assertFalse(product.isZero())
    }

    @Test
    internal fun testToString() {
        var product = LogProduct.ZERO
        var expected = "0x${LogNum.ZERO}"
        assertEquals(expected, product.toString())

        product = LogProduct(listOf(LogNum.ONE, LogNum(17.toBigInteger()), LogNum(109.toBigInteger())))
        expected = "1x${LogNum.ONE}x${LogNum(17.toBigInteger())}x${LogNum(109.toBigInteger())}"
        assertEquals(expected, product.toString())

        product = LogProduct(listOf(LogNum(17.toBigInteger()), LogNum(109.toBigInteger())), ExactFraction.FOUR)
        expected = "4x${LogNum(17.toBigInteger())}x${LogNum(109.toBigInteger())}"
        assertEquals(expected, product.toString())

        product = LogProduct(listOf(LogNum(17.toBigInteger()), LogNum(109.toBigInteger())), -ExactFraction.FOUR)
        expected = "-4x${LogNum(17.toBigInteger())}x${LogNum(109.toBigInteger())}"
        assertEquals(expected, product.toString())

        product = LogProduct(listOf(LogNum(17.toBigInteger()), LogNum(109.toBigInteger())), ExactFraction(13, 3))
        expected = "${ExactFraction(13, 3)}x${LogNum(17.toBigInteger())}x${LogNum(109.toBigInteger())}"
        assertEquals(expected, product.toString())
    }

    @Test internal fun testGetSimplified() = runGetSimplifiedTests()
    @Test internal fun testTimes() = runTimesTests()
}
