package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.testutils.assertFailsWithMessage
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals

class ExactFractionHelpersTest {
    @Test fun testSimplify() = runSimplifyTests()

    @Test
    fun testCreateDecimalString() {
        var ef = ExactFraction(0)
        var expected = "0"
        assertEquals(expected, createDecimalString(ef, 8))

        ef = ExactFraction(4)
        expected = "4"
        assertEquals(expected, createDecimalString(ef, 8))

        ef = ExactFraction(-3)
        expected = "-3"
        assertEquals(expected, createDecimalString(ef, 3))

        ef = ExactFraction(3, 8)
        expected = "0.375"
        assertEquals(expected, createDecimalString(ef, 5))

        ef = ExactFraction(5, 2)
        expected = "2.5"
        assertEquals(expected, createDecimalString(ef, 8))

        ef = ExactFraction(5, 3)
        expected = "2"
        assertEquals(expected, createDecimalString(ef, 0))

        ef = ExactFraction(4, 3)
        expected = "1"
        assertEquals(expected, createDecimalString(ef, 0))

        ef = ExactFraction(-1, 9)
        expected = "-0.111111111111"
        assertEquals(expected, createDecimalString(ef, 12))

        ef = ExactFraction(5, 9)
        expected = "0.55555556"
        assertEquals(expected, createDecimalString(ef, 8))

        ef = ExactFraction(-4, 19)
        expected = "-0.21052632"
        assertEquals(expected, createDecimalString(ef, 8))

        ef = ExactFraction(3, 8)
        expected = "0.38"
        assertEquals(expected, createDecimalString(ef, 2))

        ef = ExactFraction(-4, 19)
        expected = "-0.21053"
        assertEquals(expected, createDecimalString(ef, 5))

        val largeValue = "100000000000000000000"
        val bi = BigInteger(largeValue)
        ef = ExactFraction(bi, 3)
        expected = "33333333333333333333.333333"
        assertEquals(expected, createDecimalString(ef, 6))

        // exception
        val errorMessage = "Number of digits must be non-negative"
        assertFailsWithMessage<IllegalArgumentException>(errorMessage) { createDecimalString(ExactFraction(3), -3) }
    }
}
