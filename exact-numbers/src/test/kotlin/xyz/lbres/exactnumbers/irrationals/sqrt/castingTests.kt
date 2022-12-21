package xyz.lbres.exactnumbers.irrationals.sqrt

import xyz.lbres.exactnumbers.common.CastingOverflowException
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import java.math.BigInteger
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private val zero = Sqrt.ZERO // 0
private val ltHalf = Sqrt(ExactFraction(4, 25)) // 0.4
private val gtHalf = Sqrt(ExactFraction(16, 25)) // 0.8
private val gtOne = Sqrt(ExactFraction(81, 16)) // 2.25
private val whole = Sqrt(144) // 12
private val irrational = Sqrt(15) // 3.872983346207416885179265399782399610832921705291590826
private val large = Sqrt(BigInteger("97546105778997104100")) // 9876543210

internal fun runToByteTests() {
    assertEquals(0, zero.toByte())
    assertEquals(0, ltHalf.toByte())
    assertEquals(1, gtHalf.toByte())
    assertEquals(2, gtOne.toByte())
    assertEquals(12, whole.toByte())
    assertEquals(4, irrational.toByte())
    assertFailsWith<CastingOverflowException> { large.toByte() }
}

internal fun runToCharTests() {
    assertEquals(0.toChar(), zero.toChar())
    assertEquals(0.toChar(), ltHalf.toChar())
    assertEquals(1.toChar(), gtHalf.toChar())
    assertEquals(2.toChar(), gtOne.toChar())
    assertEquals(12.toChar(), whole.toChar())
    assertEquals(4.toChar(), irrational.toChar())
    assertFailsWith<CastingOverflowException> { large.toChar() }
}

internal fun runToShortTests() {
    assertEquals(0, zero.toShort())
    assertEquals(0, ltHalf.toShort())
    assertEquals(1, gtHalf.toShort())
    assertEquals(2, gtOne.toShort())
    assertEquals(12, whole.toShort())
    assertEquals(4, irrational.toShort())
    assertFailsWith<CastingOverflowException> { large.toShort() }
}

internal fun runToIntTests() {
    assertEquals(0, zero.toInt())
    assertEquals(0, ltHalf.toInt())
    assertEquals(1, gtHalf.toInt())
    assertEquals(2, gtOne.toInt())
    assertEquals(12, whole.toInt())
    assertEquals(4, irrational.toInt())
    assertFailsWith<CastingOverflowException> { large.toInt() }
}

internal fun runToLongTests() {
    assertEquals(0, zero.toLong())
    assertEquals(0, ltHalf.toLong())
    assertEquals(1, gtHalf.toLong())
    assertEquals(2, gtOne.toLong())
    assertEquals(12, whole.toLong())
    assertEquals(4, irrational.toLong())

    val longRoot = 9876543210L
    assertEquals(longRoot, large.toLong())
}

internal fun runToFloatTests() {
    assertEquals(0f, zero.toFloat())
    assertEquals(0.4f, ltHalf.toFloat())
    assertEquals(0.8f, gtHalf.toFloat())
    assertEquals(2.25f, gtOne.toFloat())
    assertEquals(12f, whole.toFloat())

    val irrationalRoot = 3.8729835f
    assertEquals(irrationalRoot, irrational.toFloat())

    val longRoot = 9876543210f
    assertEquals(longRoot, large.toFloat())
}

internal fun runToDoubleTests() {
    assertEquals(0.0, zero.toDouble())
    assertEquals(0.4, ltHalf.toDouble())
    assertEquals(0.8, gtHalf.toDouble())
    assertEquals(2.25, gtOne.toDouble())
    assertEquals(12.0, whole.toDouble())

    val irrationalRoot = 3.872983346207417
    assertEquals(irrationalRoot, irrational.toDouble())

    val longRoot = 9876543210.0
    assertEquals(longRoot, large.toDouble())
}
