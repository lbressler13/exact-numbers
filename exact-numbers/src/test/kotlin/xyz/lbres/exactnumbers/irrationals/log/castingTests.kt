package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.exactnumbers.common.CastingOverflowException
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import java.math.BigInteger
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private val zero = Log.ZERO
private val ltHalf = Log(2, 16) // 0.25
private val gtHalf = Log(8, 16) // 0.75
private val gtOne = Log(512, 16) // 2.25
private val whole = Log(512, 2) // 9
private val irrational = Log(987654, 3) // 12.564111884912267816
private val large = Log(BigInteger("5444517870735015415413993718908291383296"), 2) // 132

private val smallEF = ExactFraction(BigInteger.ONE, BigInteger("5444517870735015415413993718908291383296"))
private val small = Log(smallEF, 2) // -132

internal fun runToByteTests() {
    assertEquals(0, zero.toByte())
    assertEquals(0, ltHalf.toByte())
    assertEquals(1, gtHalf.toByte())
    assertEquals(2, gtOne.toByte())
    assertEquals(9, whole.toByte())
    assertEquals(13, irrational.toByte())
    assertFailsWith<CastingOverflowException> { large.toByte() }
    assertFailsWith<CastingOverflowException> { small.toByte() }
}

internal fun runToCharTests() {
    assertEquals(0.toChar(), zero.toChar())
    assertEquals(0.toChar(), ltHalf.toChar())
    assertEquals(1.toChar(), gtHalf.toChar())
    assertEquals(2.toChar(), gtOne.toChar())
    assertEquals(9.toChar(), whole.toChar())
    assertEquals(13.toChar(), irrational.toChar())
    assertEquals(132.toChar(), large.toChar())
    assertFailsWith<CastingOverflowException> { small.toChar() }
}

internal fun runToShortTests() {
    assertEquals(0, zero.toShort())
    assertEquals(0, ltHalf.toShort())
    assertEquals(1, gtHalf.toShort())
    assertEquals(2, gtOne.toShort())
    assertEquals(9, whole.toShort())
    assertEquals(13, irrational.toShort())
    assertEquals(132, large.toShort())
    assertEquals(-132, small.toShort())
}

internal fun runToIntTests() {
    assertEquals(0, zero.toInt())
    assertEquals(0, ltHalf.toInt())
    assertEquals(1, gtHalf.toInt())
    assertEquals(2, gtOne.toInt())
    assertEquals(9, whole.toInt())
    assertEquals(13, irrational.toInt())
    assertEquals(132, large.toInt())
    assertEquals(-132, small.toInt())
}

internal fun runToLongTests() {
    assertEquals(0, zero.toLong())
    assertEquals(0, ltHalf.toLong())
    assertEquals(1, gtHalf.toLong())
    assertEquals(2, gtOne.toLong())
    assertEquals(9, whole.toLong())
    assertEquals(13, irrational.toLong())
    assertEquals(132, large.toLong())
    assertEquals(-132, small.toLong())
}

internal fun runToFloatTests() {
    assertEquals(0f, zero.toFloat())
    assertEquals(0.25f, ltHalf.toFloat())
    assertEquals(0.75f, gtHalf.toFloat())
    assertEquals(2.25f, gtOne.toFloat())
    assertEquals(9f, whole.toFloat())
    assertEquals(12.564112f, irrational.toFloat())
    assertEquals(132f, large.toFloat())
    assertEquals(-132f, small.toFloat())
}

internal fun runToDoubleTests() {
    assertEquals(0.0, zero.toDouble())
    assertEquals(0.25, ltHalf.toDouble())
    assertEquals(0.75, gtHalf.toDouble())
    assertEquals(2.25, gtOne.toDouble())
    assertEquals(9.0, whole.toDouble())
    assertEquals(12.564111884912267, irrational.toDouble())
    assertEquals(132.0, large.toDouble())
    assertEquals(-132.0, small.toDouble())
}
