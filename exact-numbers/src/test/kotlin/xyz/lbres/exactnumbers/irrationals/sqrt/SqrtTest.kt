package xyz.lbres.exactnumbers.irrationals.sqrt

import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.Memoize
import java.math.BigInteger
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class SqrtTest {
    @BeforeTest
    fun createMocks() {
        mockkObject(Memoize)
        every { Memoize.individualWholeNumber } answers { mutableMapOf() }
    }

    @AfterTest
    fun clearMocks() {
        unmockkAll()
    }

    @Test
    fun testConstructor() {
        // errors
        val expectedMessage = "Cannot calculate root of a negative number"
        assertFailsWith<ArithmeticException>(expectedMessage) { Sqrt(-ExactFraction.EIGHT) }
        assertFailsWith<ArithmeticException>(expectedMessage) { Sqrt(-8) }
        assertFailsWith<ArithmeticException>(expectedMessage) { Sqrt(-8L) }
        assertFailsWith<ArithmeticException>(expectedMessage) { Sqrt(BigInteger("-8")) }
        assertFailsWith<ArithmeticException>(expectedMessage) { Sqrt(-ExactFraction.HALF) }

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
    @Test fun testSimplifyList() = runSimplifyListTests()
    @Test fun testSimplifySet() = runSimplifySetTests()

    @Test fun testToByte() = runToByteTests()
    @Test fun testToChar() = runToCharTests()
    @Test fun testToShort() = runToShortTests()
    @Test fun testToInt() = runToIntTests()
    @Test fun testToLong() = runToLongTests()
    @Test fun testToFloat() = runToFloatTests()
    @Test fun testToDouble() = runToDoubleTests()

    @Test
    fun testToString() {
        val symbol = "√"

        // whole number
        var sqrt = Sqrt(ExactFraction.ZERO)
        var expected = "[$symbol(0)]"
        assertEquals(expected, sqrt.toString())

        sqrt = Sqrt(10)
        expected = "[$symbol(10)]"
        assertEquals(expected, sqrt.toString())

        sqrt = Sqrt(1234567)
        expected = "[$symbol(1234567)]"
        assertEquals(expected, sqrt.toString())

        // fraction
        sqrt = Sqrt(ExactFraction.HALF)
        expected = "[$symbol(1/2)]"
        assertEquals(expected, sqrt.toString())

        sqrt = Sqrt(ExactFraction(12, 35))
        expected = "[$symbol(12/35)]"
        assertEquals(expected, sqrt.toString())
    }
}
