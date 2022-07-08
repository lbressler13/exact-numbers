package exactnumbers.irrationals.pi

import exactnumbers.exactfraction.ExactFraction
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class PiNumTest {
    @Test
    internal fun testConstructor() {
        var piNum = PiNum()
        var expectedCoeff = ExactFraction.ONE
        assertEquals(expectedCoeff, piNum.coefficient)

        piNum = PiNum(ExactFraction.ZERO)
        expectedCoeff = ExactFraction.ZERO
        assertEquals(expectedCoeff, piNum.coefficient)

        piNum = PiNum(ExactFraction(181, 8))
        expectedCoeff = ExactFraction(181, 8)
        assertEquals(expectedCoeff, piNum.coefficient)

        piNum = PiNum(ExactFraction(-7, 8))
        expectedCoeff = ExactFraction(-7, 8)
        assertEquals(expectedCoeff, piNum.coefficient)
    }

    @Test
    internal fun testUnaryMinus() {
        var piNum = PiNum.ZERO
        var expected = PiNum.ZERO
        assertEquals(expected, -piNum)

        piNum = PiNum()
        expected = PiNum(ExactFraction.NEG_ONE)
        assertEquals(expected, -piNum)

        piNum = PiNum(ExactFraction.NEG_ONE)
        expected = PiNum()
        assertEquals(expected, -piNum)

        piNum = PiNum(ExactFraction(-15, 4))
        expected = PiNum(ExactFraction(15, 4))
        assertEquals(expected, -piNum)
    }

    @Test
    internal fun testUnaryPlus() {
        var piNum = PiNum.ZERO
        assertEquals(piNum, +piNum)

        piNum = PiNum()
        assertEquals(piNum, +piNum)

        piNum = PiNum(ExactFraction.NEG_ONE)
        assertEquals(piNum, +piNum)

        piNum = PiNum(ExactFraction(-15, 4))
        assertEquals(piNum, +piNum)
    }

    @Test
    internal fun testPlus() {
        // zero
        var piNum1 = PiNum.ZERO
        var piNum2 = PiNum.ZERO
        var expected = PiNum.ZERO
        assertEquals(expected, piNum1 + piNum2)
        assertEquals(expected, piNum2 + piNum1)

        piNum1 = PiNum(ExactFraction.HALF)
        piNum2 = PiNum.ZERO
        expected = PiNum(ExactFraction.HALF)
        assertEquals(expected, piNum1 + piNum2)
        assertEquals(expected, piNum2 + piNum1)

        // other
        piNum1 = PiNum(ExactFraction(5, 3))
        piNum2 = PiNum(ExactFraction(2, 3))
        expected = PiNum(ExactFraction(7, 3))
        assertEquals(expected, piNum1 + piNum2)
        assertEquals(expected, piNum2 + piNum1)

        piNum1 = PiNum(-ExactFraction.FOUR)
        piNum2 = PiNum(-ExactFraction.TWO)
        expected = PiNum(-ExactFraction.SIX)
        assertEquals(expected, piNum1 + piNum2)
        assertEquals(expected, piNum2 + piNum1)

        piNum1 = PiNum(ExactFraction.NEG_ONE)
        piNum2 = PiNum(ExactFraction.ONE)
        expected = PiNum.ZERO
        assertEquals(expected, piNum1 + piNum2)
        assertEquals(expected, piNum2 + piNum1)

        piNum1 = PiNum(ExactFraction(-3, 4))
        piNum2 = PiNum(ExactFraction(1, 4))
        expected = PiNum(-ExactFraction.HALF)
        assertEquals(expected, piNum1 + piNum2)
        assertEquals(expected, piNum2 + piNum1)
    }

    @Test
    internal fun testMinus() {
        // zero
        var piNum1 = PiNum.ZERO
        var piNum2 = PiNum.ZERO
        var expected = PiNum.ZERO
        assertEquals(expected, piNum1 - piNum2)
        assertEquals(expected, piNum2 - piNum1)

        piNum1 = PiNum(ExactFraction.HALF)
        piNum2 = PiNum.ZERO

        expected = PiNum(ExactFraction.HALF)
        assertEquals(expected, piNum1 - piNum2)

        expected = PiNum(-ExactFraction.HALF)
        assertEquals(expected, piNum2 - piNum1)

        // other
        piNum1 = PiNum(ExactFraction(5, 3))
        piNum2 = PiNum(ExactFraction(2, 3))

        expected = PiNum(ExactFraction.ONE)
        assertEquals(expected, piNum1 - piNum2)

        expected = PiNum(ExactFraction.NEG_ONE)
        assertEquals(expected, piNum2 - piNum1)

        piNum1 = PiNum(-ExactFraction.FOUR)
        piNum2 = PiNum(-ExactFraction.TWO)

        expected = PiNum(-ExactFraction.TWO)
        assertEquals(expected, piNum1 - piNum2)

        expected = PiNum(ExactFraction.TWO)
        assertEquals(expected, piNum2 - piNum1)

        piNum1 = PiNum(ExactFraction.NEG_ONE)
        piNum2 = PiNum(ExactFraction.ONE)

        expected = PiNum(-ExactFraction.TWO)
        assertEquals(expected, piNum1 - piNum2)

        expected = PiNum(ExactFraction.TWO)
        assertEquals(expected, piNum2 - piNum1)

        piNum1 = PiNum(ExactFraction(-3, 5))
        piNum2 = PiNum(ExactFraction(4, 5))

        expected = PiNum(ExactFraction(-7, 5))
        assertEquals(expected, piNum1 - piNum2)

        expected = PiNum(ExactFraction(7, 5))
        assertEquals(expected, piNum2 - piNum1)
    }

    @Test
    internal fun testEquals() {
        // equal
        var piNum1 = PiNum()
        assertEquals(piNum1, piNum1)

        piNum1 = PiNum.ZERO
        assertEquals(piNum1, piNum1)

        piNum1 = PiNum(ExactFraction(-103, 4))
        assertEquals(piNum1, piNum1)

        // not equal
        piNum1 = PiNum(ExactFraction.ONE)
        var piNum2 = PiNum(ExactFraction.NEG_ONE)
        assertNotEquals(piNum1, piNum2)
        assertNotEquals(piNum2, piNum1)

        piNum1 = PiNum(ExactFraction.TWO)
        piNum2 = PiNum(ExactFraction.THREE)
        assertNotEquals(piNum1, piNum2)
        assertNotEquals(piNum2, piNum1)

        piNum1 = PiNum(ExactFraction(1, 1000))
        piNum2 = PiNum(ExactFraction(1000))
        assertNotEquals(piNum1, piNum2)
        assertNotEquals(piNum2, piNum1)

        piNum1 = PiNum(-ExactFraction.FOUR)
        piNum2 = PiNum(-ExactFraction.HALF)
        assertNotEquals(piNum1, piNum2)
        assertNotEquals(piNum2, piNum1)
    }

    @Test
    internal fun testGetValue() {
        // kotlin representation of pi: 3.141592653589793

        var piNum = PiNum.ZERO
        var expected = BigDecimal.ZERO
        assertEquals(expected, piNum.getValue())

        piNum = PiNum()
        expected = BigDecimal("3.141592653589793")
        assertEquals(expected, piNum.getValue())

        piNum = PiNum(ExactFraction.TWO)
        expected = BigDecimal("6.283185307179586")
        assertEquals(expected, piNum.getValue())

        piNum = PiNum(-ExactFraction(100))
        expected = BigDecimal("-314.159265358979300")
        assertEquals(expected, piNum.getValue())

        piNum = PiNum(ExactFraction(3, 7))
        expected = BigDecimal("1.3463968515384827143")
        assertEquals(expected, piNum.getValue())

        piNum = PiNum(ExactFraction(-7, 3))
        expected = BigDecimal("-7.3303828583761836667")
        assertEquals(expected, piNum.getValue())
    }

    @Test
    internal fun testIsZero() {
        var piNum = PiNum(ExactFraction.ZERO)
        assertTrue(piNum.isZero())

        piNum = PiNum(ExactFraction(-1, 200))
        assertFalse(piNum.isZero())

        piNum = PiNum(ExactFraction.EIGHT)
        assertFalse(piNum.isZero())

        piNum = PiNum(ExactFraction(12, 5))
        assertFalse(piNum.isZero())
    }

    @Test
    internal fun testToString() {
        var piNum = PiNum()
        var expected = "[1xπ]"
        assertEquals(expected, piNum.toString())

        piNum = PiNum(ExactFraction.EIGHT)
        expected = "[8xπ]"
        assertEquals(expected, piNum.toString())

        piNum = PiNum(-ExactFraction.EIGHT)
        expected = "[-8xπ]"
        assertEquals(expected, piNum.toString())

        piNum = PiNum(ExactFraction(5, 7))
        expected = "[(5/7)xπ]"
        assertEquals(expected, piNum.toString())

        piNum = PiNum(ExactFraction(-1001, 15))
        expected = "[(-1001/15)xπ]"
        assertEquals(expected, piNum.toString())
    }
}
