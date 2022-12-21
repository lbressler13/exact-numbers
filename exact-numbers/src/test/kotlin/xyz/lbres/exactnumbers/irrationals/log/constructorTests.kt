package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import java.math.BigInteger
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse

internal fun runConstructorTests() {
    // error
    assertFailsWith<ArithmeticException>("Cannot calculate log of 0") { Log(ExactFraction.ZERO) }
    assertFailsWith<ArithmeticException>("Cannot calculate log of 0") { Log(0) }
    assertFailsWith<ArithmeticException>("Cannot calculate log of 0") { Log(0L) }
    assertFailsWith<ArithmeticException>("Cannot calculate log of 0") { Log(BigInteger.ZERO) }

    assertFailsWith<ArithmeticException>("Cannot calculate log of negative number") { Log(-ExactFraction.TEN) }
    assertFailsWith<ArithmeticException>("Cannot calculate log of negative number") { Log(-10) }
    assertFailsWith<ArithmeticException>("Cannot calculate log of negative number") { Log(-10L) }
    assertFailsWith<ArithmeticException>("Cannot calculate log of negative number") { Log(-BigInteger.TEN) }

    assertFailsWith<ArithmeticException>("Cannot calculate log of negative number") {
        Log(ExactFraction(-4, 3))
    }

    assertFailsWith<ArithmeticException>("Log base must be greater than 1") { Log(ExactFraction.TEN, -1) }
    assertFailsWith<ArithmeticException>("Log base must be greater than 1") { Log(ExactFraction.TEN, 0) }
    assertFailsWith<ArithmeticException>("Log base must be greater than 1") { Log(ExactFraction.TEN, 1) }
    assertFailsWith<ArithmeticException>("Log base must be greater than 1") { Log(10, -1) }
    assertFailsWith<ArithmeticException>("Log base must be greater than 1") { Log(10L, 0) }
    assertFailsWith<ArithmeticException>("Log base must be greater than 1") { Log(BigInteger.TEN, 1) }

    // ExactFraction
    // zero
    var expectedNumber = ExactFraction.ONE
    var expectedBase = 10
    var logs = listOf(Log(ExactFraction.ONE), Log(1), Log(1L), Log(BigInteger.ONE))
    logs.forEach {
        assertEquals(expectedNumber, it.argument)
        assertEquals(expectedBase, it.base)
        assertFalse(it.isInverted)
    }

    // just number
    expectedNumber = ExactFraction.TWO
    expectedBase = 10
    logs = listOf(Log(ExactFraction.TWO), Log(2), Log(2L), Log(BigInteger.TWO))
    logs.forEach {
        assertEquals(expectedNumber, it.argument)
        assertEquals(expectedBase, it.base)
        assertFalse(it.isInverted)
    }

    var logNum = Log(ExactFraction(107, 3))
    expectedNumber = ExactFraction(107, 3)
    expectedBase = 10
    assertEquals(expectedNumber, logNum.argument)
    assertEquals(expectedBase, logNum.base)
    assertFalse(logNum.isInverted)

    // number + base
    expectedNumber = ExactFraction.TWO
    expectedBase = 2
    logs = listOf(Log(ExactFraction.TWO, 2), Log(2, 2), Log(2L, 2), Log(BigInteger.TWO, 2))
    logs.forEach {
        assertEquals(expectedNumber, it.argument)
        assertEquals(expectedBase, it.base)
        assertFalse(it.isInverted)
    }

    logNum = Log(ExactFraction(107, 3), 5)
    expectedNumber = ExactFraction(107, 3)
    expectedBase = 5
    assertEquals(expectedNumber, logNum.argument)
    assertEquals(expectedBase, logNum.base)
    assertFalse(logNum.isInverted)
}
