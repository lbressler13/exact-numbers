package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.exactnumbers.common.CastingOverflowException
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.testutils.assertFailsWithMessage
import java.math.BigInteger
import kotlin.test.assertEquals

fun runToByteTests() {
    runWholeNumberCastingTests(Long::toByte, Log::toByte, "Byte", shouldOverflow = true)
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
    val errorMessage = "Overflow casting value $log of type Log to Char"
    val error = assertFailsWithMessage<CastingOverflowException>(errorMessage) { log.toChar() }
    assertEquals(log, error.overflowValue)
}

fun runToShortTests() {
    runWholeNumberCastingTests(Long::toShort, Log::toShort, "Short")
}

fun runToIntTests() {
    runWholeNumberCastingTests(Long::toInt, Log::toInt, "Int")
}

fun runToLongTests() {
    runWholeNumberCastingTests({ it }, Log::toLong, "Long")
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
 * @param castLong (Long) -> T: cast a long value to a value of the current number type
 * @param castLog (Log) -> T: cast a log value to a value of the current number type
 * @param type [String]: name of target type
 * @param shouldOverflow [Boolean]: if the cast should throw an overflow exception when casting 2^128. Defaults to `false`
 */
private fun <T : Number> runWholeNumberCastingTests(castLong: (Long) -> T, castLog: (Log) -> T, type: String, shouldOverflow: Boolean = false) {
    var log = Log.ZERO
    assertEquals(castLong(0), castLog(log))

    log = Log(5)
    assertEquals(castLong(0), castLog(log))

    log = Log(16, 2)
    assertEquals(castLong(4), castLog(log))

    log = Log(16, 2, true)
    assertEquals(castLong(0), castLog(log))

    log = Log(2, 16, true)
    assertEquals(castLong(4), castLog(log))

    log = Log(18, 2)
    assertEquals(castLong(4), castLog(log))

    log = Log(ExactFraction(1, 16), 2)
    assertEquals(castLong(-4), castLog(log))

    val largeValue = BigInteger.TWO.pow(Byte.MAX_VALUE + 1)
    log = Log(largeValue, 2)
    if (shouldOverflow) {
        val errorMessage = "Overflow casting value $log of type Log to $type"
        val error = assertFailsWithMessage<CastingOverflowException>(errorMessage) { castLog(log) }
        assertEquals(log, error.overflowValue)
    } else {
        assertEquals(castLong(128), castLog(log))
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

    val largeValue = BigInteger.TWO.pow(Byte.MAX_VALUE + 1)
    log = Log(largeValue, 2)
    assertEquals(castDouble(128.0), castLog(log))

    log = Log(largeValue + BigInteger("4"), 19)
    assertEquals(castDouble(30.132340910929695), castLog(log))

    log = Log(largeValue + BigInteger("4"), 19, true)
    assertEquals(castDouble(0.03318693369877801), castLog(log))
}
