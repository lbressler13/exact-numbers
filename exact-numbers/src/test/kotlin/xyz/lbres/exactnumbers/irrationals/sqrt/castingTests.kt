package xyz.lbres.exactnumbers.irrationals.sqrt

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import java.math.BigInteger
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private val errorMessage = "Value would overflow supported range"

fun runToByteTests() {
    var sqrt = Sqrt.ZERO
    assertEquals(0, sqrt.toByte())

    sqrt = Sqrt(144)
    assertEquals(12, sqrt.toByte())

    sqrt = Sqrt(ExactFraction(1, 36))
    assertEquals(0, sqrt.toByte())

    sqrt = Sqrt(ExactFraction(25, 16))
    assertEquals(1, sqrt.toByte())

    sqrt = Sqrt(32)
    assertEquals(5, sqrt.toByte())

    val max = Byte.MAX_VALUE.toInt().toBigInteger()
    sqrt = Sqrt(max * max)
    assertEquals(max, sqrt.toByte().toInt().toBigInteger())

    // overflow
    sqrt = Sqrt(max * max * BigInteger.TEN)
    val exception = assertFailsWith<ArithmeticException> { sqrt.toByte() }
    assertEquals(errorMessage, exception.message)
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
    val exception = assertFailsWith<ArithmeticException> { sqrt.toChar() }
    assertEquals(errorMessage, exception.message)
}

fun runToShortTests() {
    var sqrt = Sqrt.ZERO
    assertEquals(0, sqrt.toShort())

    sqrt = Sqrt(144)
    assertEquals(12, sqrt.toShort())

    sqrt = Sqrt(ExactFraction(1, 36))
    assertEquals(0, sqrt.toShort())

    sqrt = Sqrt(ExactFraction(25, 16))
    assertEquals(1, sqrt.toShort())

    sqrt = Sqrt(32)
    assertEquals(5, sqrt.toShort())

    val max = Short.MAX_VALUE.toInt().toBigInteger()
    sqrt = Sqrt(max * max)
    assertEquals(max, sqrt.toShort().toInt().toBigInteger())

    // overflow
    sqrt = Sqrt(max * max * BigInteger.TEN)
    val exception = assertFailsWith<ArithmeticException> { sqrt.toShort() }
    assertEquals(errorMessage, exception.message)
}

fun runToIntTests() {
    var sqrt = Sqrt.ZERO
    assertEquals(0, sqrt.toInt())

    sqrt = Sqrt(144)
    assertEquals(12, sqrt.toInt())

    sqrt = Sqrt(ExactFraction(1, 36))
    assertEquals(0, sqrt.toInt())

    sqrt = Sqrt(ExactFraction(25, 16))
    assertEquals(1, sqrt.toInt())

    sqrt = Sqrt(32)
    assertEquals(5, sqrt.toInt())

    // TODO uncomment when bug is resolved
    // val max = Int.MAX_VALUE.toBigInteger()
    // Sqrt(max * max).getValue()
    // sqrt = Sqrt(max * max + BigInteger.TEN)
    // assertEquals(max, sqrt.toLong().toBigInteger())

    // overflow
    // sqrt = Sqrt(max * max * BigInteger.TEN)
    // val exception = assertFailsWith<ArithmeticException> { sqrt.toInt() }
    // assertEquals(errorMessage, exception.message)
}

fun runToLongTests() {
    var sqrt = Sqrt.ZERO
    assertEquals(0, sqrt.toLong())

    sqrt = Sqrt(144)
    assertEquals(12, sqrt.toLong())

    sqrt = Sqrt(ExactFraction(1, 36))
    assertEquals(0, sqrt.toLong())

    sqrt = Sqrt(ExactFraction(25, 16))
    assertEquals(1, sqrt.toLong())

    sqrt = Sqrt(32)
    assertEquals(5, sqrt.toLong())

    val max = Long.MAX_VALUE.toBigInteger()
    sqrt = Sqrt(max * max)
    assertEquals(max, sqrt.toLong().toBigInteger())

    // overflow
    sqrt = Sqrt(max * max * BigInteger.TEN)
    val exception = assertFailsWith<ArithmeticException> { sqrt.toLong() }
    assertEquals(errorMessage, exception.message)
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
    val exception = assertFailsWith<ArithmeticException> { sqrt.toFloat() }
    assertEquals(errorMessage, exception.message)
}
