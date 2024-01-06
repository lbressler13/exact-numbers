package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.exactnumbers.common.CastingOverflowException
import xyz.lbres.testutils.assertFailsWithMessage
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode
import kotlin.test.assertEquals

fun runToPairTests() {
    var ef = ExactFraction(0)
    var expected = Pair(BigInteger("0"), BigInteger("1"))
    assertEquals(expected, ef.toPair())

    ef = ExactFraction(1)
    expected = Pair(BigInteger("1"), BigInteger("1"))
    assertEquals(expected, ef.toPair())

    ef = ExactFraction(2, 7)
    expected = Pair(BigInteger("2"), BigInteger("7"))
    assertEquals(expected, ef.toPair())

    ef = ExactFraction(-1)
    expected = Pair(BigInteger("-1"), BigInteger("1"))
    assertEquals(expected, ef.toPair())

    ef = ExactFraction(-2, 7)
    expected = Pair(BigInteger("-2"), BigInteger("7"))
    assertEquals(expected, ef.toPair())

    ef = ExactFraction(173, 9)
    expected = Pair(BigInteger("173"), BigInteger("9"))
    assertEquals(expected, ef.toPair())
}

fun runToByteTests() {
    runWholeNumberCastingTests(Long::toByte, ExactFraction::toByte, Byte.MIN_VALUE, Byte.MAX_VALUE, "Byte")
}

// test accounts for fact that Char can't be negative
fun runToCharTests() {
    var ef = ExactFraction(0)
    var expected = 0.toChar()
    assertEquals(expected, ef.toChar())

    ef = ExactFraction(5)
    expected = 5.toChar()
    assertEquals(expected, ef.toChar())

    ef = ExactFraction(-5)
    assertExactFractionOverflow("Char", ef) { ef.toChar() }

    ef = ExactFraction(2, 5)
    expected = 0.toChar()
    assertEquals(expected, ef.toChar())

    ef = ExactFraction(-18, 5)
    assertExactFractionOverflow("Char", ef) { ef.toChar() }

    ef = ExactFraction(Char.MAX_VALUE.code)
    expected = Char.MAX_VALUE
    assertEquals(expected, ef.toChar())

    ef = ExactFraction(Char.MIN_VALUE.code)
    expected = Char.MIN_VALUE
    assertEquals(expected, ef.toChar())

    ef = ExactFraction(Char.MAX_VALUE.code + 1)
    assertExactFractionOverflow("Char", ef) { ef.toChar() }

    ef = ExactFraction(Char.MIN_VALUE.code - 1)
    assertExactFractionOverflow("Char", ef) { ef.toChar() }
}

fun runToShortTests() {
    runWholeNumberCastingTests(Long::toShort, ExactFraction::toShort, Short.MIN_VALUE, Short.MAX_VALUE, "Short")
}

fun runToIntTests() {
    runWholeNumberCastingTests(Long::toInt, ExactFraction::toInt, Int.MIN_VALUE, Int.MAX_VALUE, "Int")
}

fun runToLongTests() {
    runWholeNumberCastingTests({ it }, ExactFraction::toLong, Long.MIN_VALUE, Long.MAX_VALUE, "Long")
}

fun runToDoubleTests() {
    runDecimalNumberCastingTests({ it }, ExactFraction::toDouble, Double.MAX_VALUE, "Double")

    var ef = ExactFraction(1, 3)
    var expected = 0.3333333333333333 // maximum precision of double
    assertEquals(expected, ef.toDouble())

    ef = ExactFraction(413, 9)
    expected = 45.888888888888886 // maximum precision of double
    assertEquals(expected, ef.toDouble())

    ef = ExactFraction(-4, 19)
    expected = -0.21052631578947367 // maximum precision of double
    assertEquals(expected, ef.toDouble())
}

fun runToFloatTests() {
    runDecimalNumberCastingTests(Double::toFloat, ExactFraction::toFloat, Float.MAX_VALUE, "Float")

    var ef = ExactFraction(1, 3)
    var expected = 0.33333334f // maximum precision of float
    assertEquals(expected, ef.toFloat())

    ef = ExactFraction(413, 9)
    expected = 45.88889f // maximum precision of float
    assertEquals(expected, ef.toFloat())

    ef = ExactFraction(-4, 19)
    expected = -0.21052632f // maximum precision of float
    assertEquals(expected, ef.toFloat())
}

fun runToBigIntegerTests() {
    runWholeNumberCastingTests(Long::toBigInteger, ExactFraction::toBigInteger, null, null, "")

    val longValue = "10000000000000000000000000000"
    val ef = ExactFraction(longValue)
    val expected = BigInteger(longValue)
    assertEquals(expected, ef.toBigInteger())
}

fun runToBigDecimalTests() {
    var ef = ExactFraction(0)
    var bd = BigDecimal(0)
    assertEquals(bd, ef.toBigDecimal())

    ef = ExactFraction(10)
    bd = BigDecimal(10)
    assertEquals(bd, ef.toBigDecimal())

    ef = ExactFraction(-10)
    bd = BigDecimal(-10)
    assertEquals(bd, ef.toBigDecimal())

    ef = ExactFraction(1, 2)
    bd = BigDecimal(0.5)
    assertEquals(bd, ef.toBigDecimal())

    ef = ExactFraction(-5, 4)
    bd = BigDecimal(-1.25)
    assertEquals(bd, ef.toBigDecimal())

    var mc = MathContext(8, RoundingMode.HALF_UP)
    ef = ExactFraction(-4, 19)
    bd = BigDecimal(-0.21052632, mc)
    assertEquals(bd, ef.toBigDecimal(8))

    mc = MathContext(4, RoundingMode.HALF_UP)
    ef = ExactFraction(1, 9)
    bd = BigDecimal(0.1111, mc)
    assertEquals(bd, ef.toBigDecimal(4))

    mc = MathContext(20, RoundingMode.HALF_UP)
    ef = ExactFraction(5, 3)
    bd = BigDecimal("1.66666666666666666667", mc)
    assertEquals(bd, ef.toBigDecimal())
}

/**
 * Assert that a correct casting exception is thrown when a cast overflows
 *
 * @param type [String]: the type of the value being cast to
 * @param value [ExactFraction]: the value to cast
 * @param cast () -> Unit: the call to cast the value
 */
private fun assertExactFractionOverflow(type: String, value: ExactFraction, cast: () -> Unit) {
    val errorMessage = "Overflow casting value $value of type ExactFraction to $type"
    val error = assertFailsWithMessage<CastingOverflowException>(errorMessage) { cast() }
    assertEquals(value, error.overflowValue)
}

/**
 * Run tests for a single type of whole number
 *
 * @param castLong (Long) -> T: cast a long value to a value of the current number type
 * @param castEF (ExactFraction) -> T: cast an ExactFraction value to a value of the current number type
 * @param minValue T?: minimum valid value for the current number type. If the value is `null`, tests involved min value will not be run
 * @param maxValue T?: maximum valid value for the current number type. If the value is `null`, tests involved max value will not be run
 * @param type [String]: string representation of type, which is used in overflow exceptions
 */
private fun <T : Number> runWholeNumberCastingTests(castLong: (Long) -> T, castEF: (ExactFraction) -> T, minValue: T?, maxValue: T?, type: String) {
    var ef = ExactFraction(0)
    var expected = castLong(0)
    assertEquals(expected, castEF(ef))

    ef = ExactFraction(5)
    expected = castLong(5)
    assertEquals(expected, castEF(ef))

    ef = ExactFraction(-5)
    expected = castLong(-5)
    assertEquals(expected, castEF(ef))

    ef = ExactFraction(2, 5)
    expected = castLong(0)
    assertEquals(expected, castEF(ef))

    ef = ExactFraction(-18, 5)
    expected = castLong(-3)
    assertEquals(expected, castEF(ef))

    if (minValue != null) {
        ef = ExactFraction(minValue.toLong())
        assertEquals(minValue, castEF(ef))

        ef--
        assertExactFractionOverflow(type, ef) { castEF(ef) }
    }

    if (maxValue != null) {
        ef = ExactFraction(maxValue.toLong())
        assertEquals(maxValue, castEF(ef))

        ef++
        assertExactFractionOverflow(type, ef) { castEF(ef) }
    }
}

/**
 * Run tests for a single type of decimal number
 *
 * @param castDouble (Double) -> T: cast a double value to a value of the current number type
 * @param castEF (ExactFraction) -> T: cast an ExactFraction value to a value of the current number type
 * @param maxValue T: maximum valid value for the current number type
 * @param type [String]: string representation of type, which is used in overflow exceptions
 */
private fun <T : Number> runDecimalNumberCastingTests(castDouble: (Double) -> T, castEF: (ExactFraction) -> T, maxValue: T, type: String) {
    var ef = ExactFraction(0)
    var expected = castDouble(0.0)
    assertEquals(expected, castEF(ef))

    ef = ExactFraction(5)
    expected = castDouble(5.0)
    assertEquals(expected, castEF(ef))

    ef = ExactFraction(-5)
    expected = castDouble(-5.0)
    assertEquals(expected, castEF(ef))

    ef = ExactFraction(1, 2)
    expected = castDouble(0.5)
    assertEquals(expected, castEF(ef))

    ef = ExactFraction(-3, 8)
    expected = castDouble(-0.375)
    assertEquals(expected, castEF(ef))

    val largeValue = maxValue.toDouble().toBigDecimal().toBigInteger()
    val smallValue = (-maxValue.toDouble()).toBigDecimal().toBigInteger()

    ef = ExactFraction(largeValue)
    ef *= 2
    assertExactFractionOverflow(type, ef) { castEF(ef) }

    ef = ExactFraction(smallValue)
    ef *= 2
    assertExactFractionOverflow(type, ef) { castEF(ef) }
}
