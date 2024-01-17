package xyz.lbres.exactnumbers.irrationals.sqrt

import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.Memoize
import xyz.lbres.exactnumbers.testutils.assertFailsWithMessage
import java.math.BigInteger
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SqrtTest {
    @BeforeTest
    fun setupMockk() {
        mockkObject(Memoize)
        every { Memoize.individualWholeNumber } answers { mutableMapOf() }
    }

    @AfterTest
    fun clearMockk() {
        unmockkAll()
    }

    @Test
    fun testConstructor() {
        // errors
        val expectedMessage = "Cannot calculate root of a negative number"
        assertFailsWithMessage<ArithmeticException>(expectedMessage) { Sqrt(-ExactFraction.EIGHT) }
        assertFailsWithMessage<ArithmeticException>(expectedMessage) { Sqrt(-8) }
        assertFailsWithMessage<ArithmeticException>(expectedMessage) { Sqrt(-8L) }
        assertFailsWithMessage<ArithmeticException>(expectedMessage) { Sqrt(BigInteger("-8")) }
        assertFailsWithMessage<ArithmeticException>(expectedMessage) { Sqrt(-ExactFraction.HALF) }

        // no error
        var sqrts = listOf(Sqrt(ExactFraction.ZERO), Sqrt(0), Sqrt(0L), Sqrt(BigInteger.ZERO))
        var expectedRadicand = ExactFraction.ZERO
        sqrts.forEach {
            assertEquals(expectedRadicand, it.radicand)
        }

        sqrts = listOf(Sqrt(ExactFraction.EIGHT), Sqrt(8), Sqrt(8L), Sqrt(BigInteger("8")))
        expectedRadicand = ExactFraction.EIGHT
        sqrts.forEach {
            assertEquals(expectedRadicand, it.radicand)
        }

        var sqrt = Sqrt(ExactFraction(1000, 99))
        expectedRadicand = ExactFraction(1000, 99)
        assertEquals(expectedRadicand, sqrt.radicand)

        sqrt = Sqrt(ExactFraction(103, 782))
        expectedRadicand = ExactFraction(103, 782)
        assertEquals(expectedRadicand, sqrt.radicand)
    }

    @Test fun testIsZero() = runIsZeroTests()
    @Test fun testInverse() = runInverseTests()
    @Test fun testIsRational() = runIsRationalTests()

    @Test fun testGetRationalValue() = runGetRationalValueTests()
    @Test fun getValue() = runGetValueTests()

    @Test fun testTimes() = runTimesTests()
    @Test fun testDiv() = runDivTests()
    @Test fun testCompareTo() = runCompareToTests()
    @Test fun testEquals() = runEqualsTests()

    @Test fun testGetSimplified() = runGetSimplifiedTests()
    @Test fun testSimplifySet() = runSimplifySetTests()

    @Test
    fun testToString() {
        // whole number
        var sqrt = Sqrt(ExactFraction.ZERO)
        var expected = "[√(0)]"
        assertEquals(expected, sqrt.toString())

        sqrt = Sqrt(10)
        expected = "[√(10)]"
        assertEquals(expected, sqrt.toString())

        sqrt = Sqrt(1234567)
        expected = "[√(1234567)]"
        assertEquals(expected, sqrt.toString())

        // fraction
        sqrt = Sqrt(ExactFraction.HALF)
        expected = "[√(1/2)]"
        assertEquals(expected, sqrt.toString())

        sqrt = Sqrt(ExactFraction(12, 35))
        expected = "[√(12/35)]"
        assertEquals(expected, sqrt.toString())
    }

    @Test
    fun testToPlainString() {
        // whole number
        var sqrt = Sqrt(ExactFraction.ZERO)
        var expected = "[sqrt(0)]"
        assertEquals(expected, sqrt.toPlainString())

        sqrt = Sqrt(10)
        expected = "[sqrt(10)]"
        assertEquals(expected, sqrt.toPlainString())

        sqrt = Sqrt(1234567)
        expected = "[sqrt(1234567)]"
        assertEquals(expected, sqrt.toPlainString())

        // fraction
        sqrt = Sqrt(ExactFraction.HALF)
        expected = "[sqrt(1/2)]"
        assertEquals(expected, sqrt.toPlainString())

        sqrt = Sqrt(ExactFraction(12, 35))
        expected = "[sqrt(12/35)]"
        assertEquals(expected, sqrt.toPlainString())
    }

    @Test fun testToByte() = runToByteTests()
    @Test fun testToChar() = runToCharTests()
    @Test fun testToShort() = runToShortTests()
    @Test fun testToInt() = runToIntTests()
    @Test fun testToLong() = runToLongTests()
    @Test fun testToDouble() = runToDoubleTests()
    @Test fun testToFloat() = runToFloatTests()
}
