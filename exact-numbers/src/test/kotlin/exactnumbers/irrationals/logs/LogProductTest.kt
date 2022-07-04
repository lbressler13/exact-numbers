package exactnumbers.irrationals.logs

import exactnumbers.exactfraction.ExactFraction
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LogProductTest {
    @Test
    fun testConstructor() {
        // error
        assertFailsWith<Exception>("LogProduct must contain at least one value") { LogProduct(listOf()) }

        // zero
        val zero = listOf(LogNum.ZERO)

        var logs = listOf(LogNum.ZERO)
        assertEquals(zero, LogProduct(logs).logs)

        logs = listOf(
            LogNum(ExactFraction.SIX, 14.toBigInteger()),
            LogNum(ExactFraction(-1, 18), 17.toBigInteger()),
            LogNum.ZERO
        )
        assertEquals(zero, LogProduct(logs).logs)

        // one
        logs = listOf(LogNum.ONE)
        assertEquals(logs, LogProduct(logs).logs)

        logs = listOf(LogNum(ExactFraction.SIX, BigInteger.TWO), LogNum(ExactFraction.ONE, 11.toBigInteger()))
        assertEquals(logs, LogProduct(logs).logs)

        logs = listOf(
            LogNum(ExactFraction.SIX, BigInteger.TWO),
            LogNum.ONE,
            LogNum.ONE,
            LogNum(ExactFraction.ONE, 11.toBigInteger()),
            LogNum.ONE
        )
        assertEquals(logs, LogProduct(logs).logs)

        // other
        logs = listOf(LogNum.ONE)
        assertEquals(logs, LogProduct(logs).logs)

        logs = listOf(
            LogNum(ExactFraction.SIX, 14.toBigInteger()),
            LogNum(ExactFraction(-1, 18), 17.toBigInteger()),
            LogNum(ExactFraction(-19, 4), BigInteger.TEN),
            LogNum(ExactFraction(90), 90.toBigInteger())
        )
        assertEquals(logs, LogProduct(logs).logs)
    }

    @Test
    fun testEquals() {
        // equal
        var product1 = LogProduct.ZERO
        assertEquals(product1, product1)

        product1 = LogProduct(
            listOf(
                LogNum(ExactFraction.TWO, BigInteger.TWO),
                LogNum(-ExactFraction.SIX, 15.toBigInteger())
            )
        )
        var product2 = LogProduct(
            listOf(
                LogNum(-ExactFraction.SIX, 15.toBigInteger()),
                LogNum(ExactFraction.TWO, BigInteger.TWO)
            )
        )
        assertEquals(product1, product1)
        assertEquals(product1, product1)
        assertEquals(product2, product1)

        product1 = LogProduct(
            listOf(
                LogNum(ExactFraction(18, 7), 1000.toBigInteger()),
                LogNum.ONE,
                LogNum(ExactFraction(-101, 203), 101.toBigInteger()),
                LogNum(ExactFraction.HALF, BigInteger.TEN)
            )
        )
        product2 = LogProduct(
            listOf(
                LogNum.ONE,
                LogNum(ExactFraction(18, 7), 1000.toBigInteger()),
                LogNum(ExactFraction.HALF, BigInteger.TEN),
                LogNum(ExactFraction(-101, 203), 101.toBigInteger()),
            )
        )
        assertEquals(product1, product1)
        assertEquals(product1, product1)
        assertEquals(product2, product1)

        // not equal
        product1 = LogProduct.ZERO
        product2 = LogProduct.ONE
        assertNotEquals(product1, product2)
        assertNotEquals(product2, product1)

        product1 = LogProduct(listOf(LogNum(ExactFraction.TWO, BigInteger.TEN)))
        product2 = LogProduct(listOf(LogNum(-ExactFraction.TWO, BigInteger.TEN)))
        assertNotEquals(product1, product2)
        assertNotEquals(product2, product1)

        product1 = LogProduct(
            listOf(
                LogNum(ExactFraction.TWO, BigInteger.TWO),
            )
        )
        product2 = LogProduct(
            listOf(
                LogNum(-ExactFraction.SIX, 15.toBigInteger()),
                LogNum(ExactFraction.TWO, BigInteger.TWO)
            )
        )
        assertNotEquals(product1, product2)
        assertNotEquals(product2, product1)

        product1 = LogProduct(
            listOf(
                LogNum(ExactFraction.TWO, 15.toBigInteger()),
                LogNum(ExactFraction.SIX, BigInteger.TWO)
            )
        )
        product2 = LogProduct(
            listOf(
                LogNum(ExactFraction.TWO, BigInteger.TWO),
                LogNum(ExactFraction.SIX, 15.toBigInteger())
            )
        )
        assertNotEquals(product1, product2)
        assertNotEquals(product2, product1)

        product1 = LogProduct(
            listOf(
                LogNum(ExactFraction(1, 7), 7.toBigInteger()),
                LogNum(ExactFraction.SIX, 12.toBigInteger()),
                LogNum(ExactFraction(-2, 21), 42.toBigInteger())
            )
        )
        product2 = LogProduct(
            listOf(
                LogNum.ONE,
                LogNum(ExactFraction(18, 7), 1000.toBigInteger()),
                LogNum(ExactFraction.HALF, BigInteger.TEN),
                LogNum(ExactFraction(-101, 203), 101.toBigInteger()),
            )
        )
        assertNotEquals(product1, product2)
        assertNotEquals(product2, product1)
    }

    @Test
    fun testTimes() {
        // LogNum
        // zero
        var expected = LogProduct.ZERO

        var product = LogProduct.ZERO
        var num = LogNum.ZERO
        assertEquals(expected, product * num)

        product = LogProduct.ZERO
        num = LogNum(ExactFraction(19, 4), 12.toBigInteger())
        assertEquals(expected, product * num)

        product = LogProduct(listOf(LogNum.ONE, LogNum(ExactFraction.TWO, BigInteger.TWO)))
        num = LogNum.ZERO
        assertEquals(expected, product * num)

        // other
        product = LogProduct.ONE
        num = LogNum(ExactFraction.TWO, 4.toBigInteger())
        expected = LogProduct(listOf(LogNum.ONE, num))
        assertEquals(expected, product * num)

        product = LogProduct(
            listOf(
                LogNum(ExactFraction.SIX, BigInteger.TEN),
                LogNum(-ExactFraction.FOUR, BigInteger.TWO),
                LogNum.ONE
            )
        )
        num = LogNum.ONE
        expected = LogProduct(
            listOf(
                LogNum(ExactFraction.SIX, BigInteger.TEN),
                LogNum(-ExactFraction.FOUR, BigInteger.TWO),
                LogNum.ONE,
                LogNum.ONE
            )
        )
        assertEquals(expected, product * num)

        product =
            LogProduct(listOf(LogNum(ExactFraction.SIX, BigInteger.TEN), LogNum(-ExactFraction.FOUR, BigInteger.TWO)))
        num = LogNum(ExactFraction.HALF, 15.toBigInteger())
        expected = LogProduct(
            listOf(
                LogNum(ExactFraction.SIX, BigInteger.TEN),
                LogNum(-ExactFraction.FOUR, BigInteger.TWO),
                LogNum(ExactFraction.HALF, 15.toBigInteger())
            )
        )
        assertEquals(expected, product * num)

        product = LogProduct(listOf(LogNum(ExactFraction.HALF, 12.toBigInteger())))
        num = LogNum(-ExactFraction.HALF, 12.toBigInteger())
        expected = LogProduct(
            listOf(
                LogNum(ExactFraction.HALF, 12.toBigInteger()),
                -LogNum(ExactFraction.HALF, 12.toBigInteger())
            )
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
            listOf(LogNum(ExactFraction.FOUR, 109.toBigInteger()), LogNum(ExactFraction.HALF, BigInteger.TWO))
        )
        assertEquals(expected, product * product2)
        assertEquals(expected, product2 * product)

        // other
        product = LogProduct(listOf(LogNum(ExactFraction.SIX, BigInteger.TWO)))
        product2 = LogProduct(listOf(LogNum.ONE))
        expected = LogProduct(listOf(LogNum(ExactFraction.SIX, BigInteger.TWO), LogNum.ONE))
        assertEquals(expected, product * product2)
        assertEquals(expected, product2 * product)

        product = LogProduct(listOf(LogNum(ExactFraction.HALF, 14.toBigInteger())))
        product2 = LogProduct(
            listOf(
                LogNum(ExactFraction.TEN, 11.toBigInteger()),
                LogNum(ExactFraction(-205, 12), 200.toBigInteger()),
                LogNum(ExactFraction.FIVE, 5.toBigInteger())
            )
        )
        expected = LogProduct(
            listOf(
                LogNum(ExactFraction.HALF, 14.toBigInteger()),
                LogNum(ExactFraction.TEN, 11.toBigInteger()),
                LogNum(ExactFraction(-205, 12), 200.toBigInteger()),
                LogNum(ExactFraction.FIVE, 5.toBigInteger())
            )
        )
        assertEquals(expected, product * product2)
        assertEquals(expected, product2 * product)

        product = LogProduct(
            listOf(
                LogNum(ExactFraction.HALF, 14.toBigInteger()),
                LogNum(ExactFraction(-15), 510015.toBigInteger()),
                LogNum(ExactFraction(40213), BigInteger.TWO)
            )
        )
        product2 = LogProduct(
            listOf(
                LogNum(ExactFraction.TEN, 11.toBigInteger()),
                LogNum(ExactFraction(-205, 12), 200.toBigInteger()),
                LogNum(ExactFraction.FIVE, 5.toBigInteger())
            )
        )
        expected = LogProduct(
            listOf(
                LogNum(ExactFraction.HALF, 14.toBigInteger()),
                LogNum(ExactFraction(-15), 510015.toBigInteger()),
                LogNum(ExactFraction(40213), BigInteger.TWO),
                LogNum(ExactFraction.TEN, 11.toBigInteger()),
                LogNum(ExactFraction(-205, 12), 200.toBigInteger()),
                LogNum(ExactFraction.FIVE, 5.toBigInteger())
            )
        )
        assertEquals(expected, product * product2)
        assertEquals(expected, product2 * product)
    }

    @Test
    fun testIsZero() {
        var product = LogProduct.ZERO
        assertTrue(product.isZero())

        product = LogProduct(listOf(LogNum.ONE))
        assertFalse(product.isZero())

        product = LogProduct(listOf(-LogNum.ONE))
        assertFalse(product.isZero())

        product = LogProduct(listOf(LogNum.ONE, -LogNum.ONE))
        assertFalse(product.isZero())

        product = LogProduct(
            listOf(
                LogNum(ExactFraction(1, 1000000), BigInteger.TEN),
                LogNum(-ExactFraction.TWO, 1000.toBigInteger()),
                LogNum(ExactFraction(18, 5), 5.toBigInteger())
            )
        )
        assertFalse(product.isZero())
    }

    @Test
    fun testGetSimplified() {
        val zero = LogNum.ZERO
        val one = LogNum.ONE

        // zero
        var expected = listOf(zero)

        var product = LogProduct(listOf(zero))
        assertEquals(expected, product.getSimplified().logs)

        product = LogProduct(listOf(zero, zero, zero))
        assertEquals(expected, product.getSimplified().logs)

        product = LogProduct(listOf(zero, one))
        assertEquals(expected, product.getSimplified().logs)

        product = LogProduct(
            listOf(
                LogNum(ExactFraction.HALF, BigInteger.TWO),
                LogNum(ExactFraction.FOUR, 18.toBigInteger()),
                zero,
                LogNum(ExactFraction(-81, 4), BigInteger.TEN)
            )
        )
        assertEquals(expected, product.getSimplified().logs)

        // ones
        expected = listOf(one)

        product = LogProduct(listOf(one))
        assertEquals(expected, product.getSimplified().logs)

        product = LogProduct(listOf(one, one, one))
        assertEquals(expected, product.getSimplified().logs)


        expected = listOf(
            LogNum(ExactFraction.HALF, BigInteger.TWO),
            LogNum(ExactFraction.FOUR, 18.toBigInteger()),
            LogNum(ExactFraction(-81, 4), BigInteger.TEN)
        )

        product = LogProduct(
            listOf(
                LogNum(ExactFraction.HALF, BigInteger.TWO),
                LogNum(ExactFraction.FOUR, 18.toBigInteger()),
                one,
                LogNum(ExactFraction(-81, 4), BigInteger.TEN)
            )
        )
        assertEquals(expected, product.getSimplified().logs)

        product = LogProduct(
            listOf(
                LogNum(ExactFraction.HALF, BigInteger.TWO),
                LogNum(ExactFraction.FOUR, 18.toBigInteger()),
                one,
                LogNum(ExactFraction(-81, 4), BigInteger.TEN),
                one, one, one
            )
        )
        assertEquals(expected, product.getSimplified().logs)

        // all non zero/one
        product = LogProduct(
            listOf(
                LogNum(ExactFraction.HALF, BigInteger.TWO),
                LogNum(ExactFraction.FOUR, 18.toBigInteger()),
                LogNum(ExactFraction(-81, 4), BigInteger.TEN)
            )
        )
        expected = listOf(
            LogNum(ExactFraction.HALF, BigInteger.TWO),
            LogNum(ExactFraction.FOUR, 18.toBigInteger()),
            LogNum(ExactFraction(-81, 4), BigInteger.TEN)
        )
        assertEquals(expected, product.getSimplified().logs)

        product = LogProduct(
            listOf(
                LogNum(ExactFraction.HALF, BigInteger.TWO),
                LogNum(-ExactFraction.HALF, BigInteger.TWO)
            )
        )
        expected = listOf(
            LogNum(ExactFraction.HALF, BigInteger.TWO),
            LogNum(-ExactFraction.HALF, BigInteger.TWO)
        )
        assertEquals(expected, product.getSimplified().logs)
    }
}
