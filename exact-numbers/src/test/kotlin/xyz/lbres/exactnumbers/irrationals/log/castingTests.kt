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

    log = Log(18, 2)
    assertEquals(4, log.toLong())

    log = Log(ExactFraction(1, 16), 2)
    assertEquals(-4, log.toLong())

    val arg = BigInteger.TWO.pow(Byte.MAX_VALUE + 1)
    log = Log(arg, 2)
    assertEquals(128, log.toLong())
}

fun runToFloatTests() {
}

fun runToDoubleTests() {
}
