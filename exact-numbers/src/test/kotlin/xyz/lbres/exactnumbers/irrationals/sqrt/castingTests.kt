package xyz.lbres.exactnumbers.irrationals.sqrt

import xyz.lbres.exactnumbers.common.CastingOverflowException
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.testutils.assertFailsWithMessage
import java.math.BigInteger
import kotlin.test.assertEquals

fun runToByteTests() {
    runWholeNumberCastingTests(Long::toByte, Sqrt::toByte, Byte.MAX_VALUE, "Byte")
}

fun runToCharTests() {
    var sqrt = Sqrt.ZERO
    assertEquals(0, sqrt.toChar().code)

    sqrt = Sqrt(144)
    assertEquals(12, sqrt.toChar().code)

    sqrt = Sqrt(ExactFraction(1, 36))
    assertEquals(0, sqrt.toChar().code)

    sqrt = Sqrt(ExactFraction(25, 16))
    assertEquals(1, sqrt.toChar().code)

    sqrt = Sqrt(32)
    assertEquals(5, sqrt.toChar().code)

    val max = Char.MAX_VALUE.code.toBigInteger()
    sqrt = Sqrt(max * max)
    assertEquals(max, sqrt.toChar().code.toBigInteger())

    // overflow
    sqrt = Sqrt(max * max * BigInteger.TEN)
    val errorMessage = "Overflow casting value $sqrt of type Sqrt to Char"
    val error = assertFailsWithMessage<CastingOverflowException>(errorMessage) { sqrt.toChar() }
    assertEquals(sqrt, error.overflowValue)
}

fun runToShortTests() {
    runWholeNumberCastingTests(Long::toShort, Sqrt::toShort, Short.MAX_VALUE, "Short")
}

fun runToIntTests() {
    runWholeNumberCastingTests(Long::toInt, Sqrt::toInt, null, "Int")
    // TODO uncomment when bug is resolved
    // runWholeNumberCastingTests(Long::toInt, Sqrt::toInt, Int.MAX_VALUE, "Int")
}

fun runToLongTests() {
    runWholeNumberCastingTests({ it }, Sqrt::toLong, Long.MAX_VALUE, "Long")
}

fun runToDoubleTests() {
    var sqrt = Sqrt.ZERO
    assertEquals(0.0, sqrt.toDouble())

    sqrt = Sqrt(144)
    assertEquals(12.0, sqrt.toDouble())

    sqrt = Sqrt(ExactFraction(1, 36))
    assertEquals(0.16666666666666666, sqrt.toDouble())

    sqrt = Sqrt(ExactFraction(25, 16))
    assertEquals(1.25, sqrt.toDouble())

    sqrt = Sqrt(32)
    assertEquals(5.656854249492381, sqrt.toDouble())

    // TODO uncomment when bug is resolved
    // val max = Double.MAX_VALUE.toBigDecimal()
    // sqrt = Sqrt((max * max).toBigInteger())
    // assertEquals(max, sqrt.toDouble().toBigDecimal())

    // overflow
    // sqrt = Sqrt((max * max).toBigInteger() * BigInteger.TEN)
    // val exception = assertFailsWith<ArithmeticException> { sqrt.toDouble() }
    // assertEquals(errorMessage, exception.message)
}

fun runToFloatTests() {
    var sqrt = Sqrt.ZERO
    assertEquals(0f, sqrt.toFloat())

    sqrt = Sqrt(144)
    assertEquals(12f, sqrt.toFloat())

    sqrt = Sqrt(ExactFraction(1, 36))
    assertEquals(0.16666667f, sqrt.toFloat())

    sqrt = Sqrt(ExactFraction(25, 16))
    assertEquals(1.25f, sqrt.toFloat())

    sqrt = Sqrt(32)
    assertEquals(5.656854f, sqrt.toFloat())

    val max = Float.MAX_VALUE.toBigDecimal()
    sqrt = Sqrt((max * max).toBigInteger())
    assertEquals(max, sqrt.toFloat().toBigDecimal())

    // overflow
    sqrt = Sqrt((max * max * max).toBigInteger())
    val errorMessage = "Overflow casting value $sqrt of type Sqrt to Float"
    val error = assertFailsWithMessage<CastingOverflowException>(errorMessage) { sqrt.toFloat() }
    assertEquals(sqrt, error.overflowValue)
}

/**
 * Run tests for a single type of whole number
 *
 * @param castLong (Long) -> T: cast a long value to a value of the current number type
 * @param castSqrt (Sqrt) -> T: cast a sqrt value to a value of the current number type
 * @param maxValue T?: maximum valid value for the current number type. If the value is `null`, tests involved max value will not be run
 * @param type [String]: type which is being cast to
 */
private fun <T : Number> runWholeNumberCastingTests(castLong: (Long) -> T, castSqrt: (Sqrt) -> T, maxValue: T?, type: String) {
    var sqrt = Sqrt.ZERO
    assertEquals(castLong(0), castSqrt(sqrt))

    sqrt = Sqrt(144)
    assertEquals(castLong(12), castSqrt(sqrt))

    sqrt = Sqrt(ExactFraction(1, 36))
    assertEquals(castLong(0), castSqrt(sqrt))

    sqrt = Sqrt(ExactFraction(25, 16))
    assertEquals(castLong(1), castSqrt(sqrt))

    sqrt = Sqrt(32)
    assertEquals(castLong(5), castSqrt(sqrt))

    if (maxValue != null) {
        val max = maxValue.toLong().toBigInteger()
        sqrt = Sqrt(max * max)
        assertEquals(max, castSqrt(sqrt).toLong().toBigInteger())

        // overflow
        sqrt = Sqrt(max * max * BigInteger.TEN)
        val errorMessage = "Overflow casting value $sqrt of type Sqrt to $type"
        val error = assertFailsWithMessage<CastingOverflowException>(errorMessage) { castSqrt(sqrt) }
        assertEquals(sqrt, error.overflowValue)
    }
}
