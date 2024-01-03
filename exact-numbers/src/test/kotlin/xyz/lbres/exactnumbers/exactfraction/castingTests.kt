package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.testutils.assertExactFractionOverflow
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
}

fun runToByteTests() {
    runWholeNumberCastingTests({ it.toByte() }, { it.toByte() })

    var ef = ExactFraction(Byte.MIN_VALUE.toInt())
    var expected = Byte.MIN_VALUE
    assertEquals(expected, ef.toByte())

    ef = ExactFraction(Byte.MAX_VALUE.toInt())
    expected = Byte.MAX_VALUE
    assertEquals(expected, ef.toByte())

    ef = ExactFraction(Byte.MAX_VALUE.toInt() + 1)
    assertExactFractionOverflow("Byte", ef) { ef.toByte() }

    ef = ExactFraction(Byte.MIN_VALUE.toInt() - 1)
    assertExactFractionOverflow("Byte", ef) { ef.toByte() }
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
    runWholeNumberCastingTests({ it.toShort() }, { it.toShort() })

    var ef = ExactFraction(Short.MIN_VALUE.toInt())
    var expected = Short.MIN_VALUE
    assertEquals(expected, ef.toShort())

    ef = ExactFraction(Short.MAX_VALUE.toInt())
    expected = Short.MAX_VALUE
    assertEquals(expected, ef.toShort())

    ef = ExactFraction(Short.MAX_VALUE.toInt() + 1)
    assertExactFractionOverflow("Short", ef) { ef.toShort() }

    ef = ExactFraction(Short.MIN_VALUE.toInt() - 1)
    assertExactFractionOverflow("Short", ef) { ef.toShort() }
}

fun runToIntTests() {
    runWholeNumberCastingTests({ it.toInt() }, { it.toInt() })

    var ef = ExactFraction(Int.MIN_VALUE)
    var expected = Int.MIN_VALUE
    assertEquals(expected, ef.toInt())

    ef = ExactFraction(Int.MAX_VALUE)
    expected = Int.MAX_VALUE
    assertEquals(expected, ef.toInt())

    ef = ExactFraction(Int.MAX_VALUE)
    ef++
    assertExactFractionOverflow("Int", ef) { ef.toInt() }

    ef = ExactFraction(Int.MIN_VALUE)
    ef--
    assertExactFractionOverflow("Int", ef) { ef.toInt() }
}

fun runToLongTests() {
    runWholeNumberCastingTests({ it }, { it.toLong() })

    var ef = ExactFraction(Long.MIN_VALUE)
    var expected = Long.MIN_VALUE
    assertEquals(expected, ef.toLong())

    ef = ExactFraction(Long.MAX_VALUE)
    expected = Long.MAX_VALUE
    assertEquals(expected, ef.toLong())

    ef = ExactFraction(Long.MAX_VALUE)
    ef++
    assertExactFractionOverflow("Long", ef) { ef.toLong() }

    ef = ExactFraction(Long.MIN_VALUE)
    ef--
    assertExactFractionOverflow("Long", ef) { ef.toLong() }
}

fun runToDoubleTests() {
    var ef = ExactFraction(0)
    var expected = 0.0
    assertEquals(expected, ef.toDouble())

    ef = ExactFraction(5)
    expected = 5.0
    assertEquals(expected, ef.toDouble())

    ef = ExactFraction(-5)
    expected = -5.0
    assertEquals(expected, ef.toDouble())

    ef = ExactFraction(1, 2)
    expected = 0.5
    assertEquals(expected, ef.toDouble())

    ef = ExactFraction(-3, 8)
    expected = -0.375
    assertEquals(expected, ef.toDouble())

    ef = ExactFraction(1, 3)
    expected = 0.3333333333333333 // maximum precision of double
    assertEquals(expected, ef.toDouble())

    ef = ExactFraction(2, 3)
    expected = 0.6666666666666666 // maximum precision of double
    assertEquals(expected, ef.toDouble())

    ef = ExactFraction(-4, 19)
    expected = -0.21052631578947368 // maximum precision of double
    assertEquals(expected, ef.toDouble())

    val largeValue = Double.MAX_VALUE.toBigDecimal().toBigInteger()
    val smallValue = (-Double.MAX_VALUE).toBigDecimal().toBigInteger()

    ef = ExactFraction(largeValue)
    ef *= 2
    assertExactFractionOverflow("Double", ef) { ef.toDouble() }

    ef = ExactFraction(smallValue)
    ef *= 2
    assertExactFractionOverflow("Double", ef) { ef.toDouble() }
}

fun runToFloatTests() {
    var ef = ExactFraction(0)
    var expected = 0f
    assertEquals(expected, ef.toFloat())

    ef = ExactFraction(5)
    expected = 5f
    assertEquals(expected, ef.toFloat())

    ef = ExactFraction(-5)
    expected = -5f
    assertEquals(expected, ef.toFloat())

    ef = ExactFraction(1, 2)
    expected = 0.5f
    assertEquals(expected, ef.toFloat())

    ef = ExactFraction(-3, 8)
    expected = -0.375f
    assertEquals(expected, ef.toFloat())

    ef = ExactFraction(1, 3)
    expected = 0.33333333f // maximum precision of float
    assertEquals(expected, ef.toFloat())

    ef = ExactFraction(2, 3)
    expected = 0.6666667f // maximum precision of float
    assertEquals(expected, ef.toFloat())

    ef = ExactFraction(-4, 19)
    expected = -0.21052632f // maximum precision of float
    assertEquals(expected, ef.toFloat())

    val veryBig = Float.MAX_VALUE.toBigDecimal().toBigInteger()
    val verySmall = (-Float.MAX_VALUE).toBigDecimal().toBigInteger()

    ef = ExactFraction(veryBig)
    ef *= 2
    assertExactFractionOverflow("Float", ef) { ef.toFloat() }

    ef = ExactFraction(verySmall)
    ef *= 2
    assertExactFractionOverflow("Float", ef) { ef.toFloat() }
}

fun runToBigIntegerTests() {
    var ef = ExactFraction(0)
    var expected = BigInteger.ZERO
    assertEquals(expected, ef.toBigInteger())

    ef = ExactFraction(2)
    expected = 2.toBigInteger()
    assertEquals(expected, ef.toBigInteger())

    ef = ExactFraction(-4)
    expected = (-4).toBigInteger()
    assertEquals(expected, ef.toBigInteger())

    ef = ExactFraction(3, 7)
    expected = BigInteger.ZERO
    assertEquals(expected, ef.toBigInteger())

    ef = ExactFraction(-12, 5)
    expected = (-2).toBigInteger()
    assertEquals(expected, ef.toBigInteger())

    val big = "10000000000000000000000000000"
    ef = ExactFraction(big)
    expected = BigInteger(big)
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

private fun <T: Number> runWholeNumberCastingTests(getExpected: (Long) -> T, cast: (ExactFraction) -> T) {
    var ef = ExactFraction(0)
    var expected = getExpected(0)
    assertEquals(expected, cast(ef))

    ef = ExactFraction(5)
    expected = getExpected(5)
    assertEquals(expected, cast(ef))

    ef = ExactFraction(-5)
    expected = getExpected(-5)
    assertEquals(expected, cast(ef))

    ef = ExactFraction(2, 5)
    expected = getExpected(0)
    assertEquals(expected, cast(ef))

    ef = ExactFraction(-18, 5)
    expected = getExpected(-3)
    assertEquals(expected, cast(ef))

    // TODO
//    ef = ExactFraction(Byte.MIN_VALUE.toInt())
//    expected = Byte.MIN_VALUE
//    assertEquals(expected, ef.toByte())
//
//    ef = ExactFraction(Byte.MAX_VALUE.toInt())
//    expected = Byte.MAX_VALUE
//    assertEquals(expected, ef.toByte())
//
//    ef = ExactFraction(Byte.MAX_VALUE.toInt() + 1)
//    assertExactFractionOverflow("Byte", ef) { ef.toByte() }
//
//    ef = ExactFraction(Byte.MIN_VALUE.toInt() - 1)
//    assertExactFractionOverflow("Byte", ef) { ef.toByte() }
}
