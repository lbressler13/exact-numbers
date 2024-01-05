package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.testutils.assertFailsWithMessage
import java.math.BigInteger
import kotlin.test.assertEquals

private const val overflowErrorMessage = "Value would overflow supported range"

fun runToByteTests() {
    runWholeNumberCastingTests(Int::toByte, Log::toByte, shouldOverflow = true)
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
    runWholeNumberCastingTests(Int::toShort, Log::toShort)
}

fun runToIntTests() {
    runWholeNumberCastingTests({ it }, Log::toInt)
}

fun runToLongTests() {
    runWholeNumberCastingTests(Int::toLong, Log::toLong)
}

fun runToFloatTests() {
    runDecimalCastingTests(Double::toFloat, Log::toFloat)
}

fun runToDoubleTests() {
    runDecimalCastingTests({ it }, Log::toDouble)
}

/**
 * Run tests for a single type of whole number
 *
 * @param castInt (Int) -> T: cast an int value to a value of the current number type
 * @param castLog (Log) -> T: cast a log value to a value of the current number type
 * @param shouldOverflow [Boolean]: if the cast should throw an overflow exception when casting 2^128. Defaults to `false`
 */
private fun <T : Number> runWholeNumberCastingTests(castInt: (Int) -> T, castLog: (Log) -> T, shouldOverflow: Boolean = false) {
    var log = Log.ZERO
    assertEquals(castInt(0), castLog(log))

    log = Log(5)
    assertEquals(castInt(0), castLog(log))

    log = Log(16, 2)
    assertEquals(castInt(4), castLog(log))

    log = Log(16, 2, true)
    assertEquals(castInt(0), castLog(log))

    log = Log(2, 16, true)
    assertEquals(castInt(4), castLog(log))

    log = Log(18, 2)
    assertEquals(castInt(4), castLog(log))

    log = Log(ExactFraction(1, 16), 2)
    assertEquals(castInt(-4), castLog(log))

    val arg = BigInteger.TWO.pow(Byte.MAX_VALUE + 1)
    log = Log(arg, 2)
    if (shouldOverflow) {
        assertFailsWithMessage<ArithmeticException>(overflowErrorMessage) { castLog(log) }
    } else {
        assertEquals(castInt(128), castLog(log))
    }
}

/**
 * Run tests for a single type of decimal number
 *
 * @param castDouble (Double) -> T: cast a double value to a value of the current number type
 * @param castLog (Log) -> T: cast a log value to a value of the current number type
 */
private fun <T : Number> runDecimalCastingTests(castDouble: (Double) -> T, castLog: (Log) -> T) {
    var log = Log.ZERO
    assertEquals(castDouble(0.0), castLog(log))

    log = Log(5)
    assertEquals(castDouble(0.6989700043360187), castLog(log))

    log = Log(16, 2)
    assertEquals(castDouble(4.0), castLog(log))

    log = Log(16, 2, true)
    assertEquals(castDouble(0.25), castLog(log))

    log = Log(2, 16, true)
    assertEquals(castDouble(4.0), castLog(log))

    log = Log(18, 2)
    assertEquals(castDouble(4.169925001442312), castLog(log))

    log = Log(ExactFraction(1, 16), 2)
    assertEquals(castDouble(-4.0), castLog(log))

    val arg = BigInteger.TWO.pow(Byte.MAX_VALUE + 1)
    log = Log(arg, 2)
    assertEquals(castDouble(128.0), castLog(log))

    log = Log(arg + BigInteger("4"), 19)
    assertEquals(castDouble(30.132340910929695), castLog(log))

    log = Log(arg + BigInteger("4"), 19, true)
    assertEquals(castDouble(0.03318693369877801), castLog(log))
}
