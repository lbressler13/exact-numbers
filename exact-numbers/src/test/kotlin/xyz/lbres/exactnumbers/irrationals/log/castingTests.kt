package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.testutils.assertFailsWithMessage
import java.math.BigInteger
import kotlin.test.assertEquals

private const val overflowErrorMessage = "Value would overflow supported range"

fun runToByteTests() {
    var log = Log.ZERO
    assertEquals(0, log.toByte())

    log = Log(5)
    assertEquals(0, log.toByte())

    log = Log(16, 2)
    assertEquals(4, log.toByte())

    log = Log(16, 2, true)
    assertEquals(0, log.toByte())

    log = Log(2, 16, true)
    assertEquals(4, log.toByte())

    log = Log(18, 2)
    assertEquals(4, log.toByte())

    log = Log(ExactFraction(1, 16), 2)
    assertEquals(-4, log.toByte())

    val arg = BigInteger.TWO.pow(Byte.MAX_VALUE + 1)
    log = Log(arg, 2)
    assertFailsWithMessage<ArithmeticException>(overflowErrorMessage) { log.toByte() }
}

fun runToCharTests() {
    var log = Log.ZERO
    assertEquals(0.toChar(), log.toChar())

    log = Log(5)
    assertEquals(0.toChar(), log.toChar())

    log = Log(16, 2)
    assertEquals(4.toChar(), log.toChar())

    log = Log(16, 2, true)
    assertEquals(0.toChar(), log.toChar())

    log = Log(2, 16, true)
    assertEquals(4.toChar(), log.toChar())

    log = Log(18, 2)
    assertEquals(4.toChar(), log.toChar())

    log = Log(ExactFraction(1, 16), 2)
    assertFailsWithMessage<ArithmeticException>(overflowErrorMessage) { log.toChar() }
}

fun runToShortTests() {
    var log = Log.ZERO
    assertEquals(0, log.toShort())

    log = Log(5)
    assertEquals(0, log.toShort())

    log = Log(16, 2)
    assertEquals(4, log.toShort())

    log = Log(16, 2, true)
    assertEquals(0, log.toShort())

    log = Log(2, 16, true)
    assertEquals(4, log.toShort())

    log = Log(18, 2)
    assertEquals(4, log.toShort())

    log = Log(ExactFraction(1, 16), 2)
    assertEquals(-4, log.toShort())

    val arg = BigInteger.TWO.pow(Byte.MAX_VALUE + 1)
    log = Log(arg, 2)
    assertEquals(128, log.toShort())
}

fun runToIntTests() {
    var log = Log.ZERO
    assertEquals(0, log.toInt())

    log = Log(5)
    assertEquals(0, log.toInt())

    log = Log(16, 2)
    assertEquals(4, log.toInt())

    log = Log(16, 2, true)
    assertEquals(0, log.toInt())

    log = Log(2, 16, true)
    assertEquals(4, log.toInt())

    log = Log(18, 2)
    assertEquals(4, log.toInt())

    log = Log(ExactFraction(1, 16), 2)
    assertEquals(-4, log.toInt())

    val arg = BigInteger.TWO.pow(Byte.MAX_VALUE + 1)
    log = Log(arg, 2)
    assertEquals(128, log.toInt())
}

fun runToLongTests() {
    var log = Log.ZERO
    assertEquals(0, log.toLong())

    log = Log(5)
    assertEquals(0, log.toLong())

    log = Log(16, 2)
    assertEquals(4, log.toLong())

    log = Log(16, 2, true)
    assertEquals(0, log.toLong())

    log = Log(2, 16, true)
    assertEquals(4, log.toLong())

    log = Log(18, 2)
    assertEquals(4, log.toLong())

    log = Log(ExactFraction(1, 16), 2)
    assertEquals(-4, log.toLong())

    val arg = BigInteger.TWO.pow(Byte.MAX_VALUE + 1)
    log = Log(arg, 2)
    assertEquals(128, log.toLong())
}

fun runToFloatTests() {
    var log = Log.ZERO
    assertEquals(0f, log.toFloat())

    log = Log(5)
    assertEquals(0.69897f, log.toFloat())

    log = Log(16, 2)
    assertEquals(4f, log.toFloat())

    log = Log(16, 2, true)
    assertEquals(0.25f, log.toFloat())

    log = Log(2, 16, true)
    assertEquals(4f, log.toFloat())

    log = Log(18, 2)
    assertEquals(4.169925f, log.toFloat())

    log = Log(ExactFraction(1, 16), 2)
    assertEquals(-4f, log.toFloat())

    val arg = BigInteger.TWO.pow(Byte.MAX_VALUE + 1)
    log = Log(arg, 2)
    assertEquals(128f, log.toFloat())

    log = Log(arg + BigInteger("4"), 19)
    assertEquals(30.132341f, log.toFloat())

    log = Log(arg + BigInteger("4"), 19, true)
    assertEquals(0.033186935f, log.toFloat())
}

fun runToDoubleTests() {
    var log = Log.ZERO
    assertEquals(0.0, log.toDouble())

    log = Log(5)
    assertEquals(0.6989700043360187, log.toDouble())

    log = Log(16, 2)
    assertEquals(4.0, log.toDouble())

    log = Log(16, 2, true)
    assertEquals(0.25, log.toDouble())

    log = Log(2, 16, true)
    assertEquals(4.0, log.toDouble())

    log = Log(18, 2)
    assertEquals(4.169925001442312, log.toDouble())

    log = Log(ExactFraction(1, 16), 2)
    assertEquals(-4.0, log.toDouble())

    val arg = BigInteger.TWO.pow(Byte.MAX_VALUE + 1)
    log = Log(arg, 2)
    assertEquals(128.0, log.toDouble())

    log = Log(arg + BigInteger("4"), 19)
    assertEquals(30.132340910929695, log.toDouble())

    log = Log(arg + BigInteger("4"), 19, true)
    assertEquals(0.03318693369877801, log.toDouble())
}
