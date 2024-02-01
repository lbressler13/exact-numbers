package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.exactnumbers.testutils.assertDivByZero
import xyz.lbres.exactnumbers.testutils.assertFailsWithMessage
import java.math.BigInteger
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

// parsing
fun runParseDecimalTests() {
    // whole numbers
    var s = "0"
    var expected = ExactFraction(0)
    assertEquals(expected, parseDecimal(s))

    s = "0000011"
    expected = ExactFraction(11)
    assertEquals(expected, parseDecimal(s))

    s = "   0000011\n"
    expected = ExactFraction(11)
    assertEquals(expected, parseDecimal(s))

    s = "-31"
    expected = ExactFraction(-31)
    assertEquals(expected, parseDecimal(s))

    s = "1000"
    expected = ExactFraction(1000)
    assertEquals(expected, parseDecimal(s))

    // whole w/ decimal
    s = "-31.0000"
    expected = ExactFraction(-31)
    assertEquals(expected, parseDecimal(s))

    s = "1000.0"
    expected = ExactFraction(1000)
    assertEquals(expected, parseDecimal(s))

    // decimal w/out whole
    s = "0.1"
    expected = ExactFraction(1, 10)
    assertEquals(expected, parseDecimal(s))

    s = "-0.1"
    expected = ExactFraction(-1, 10)
    assertEquals(expected, parseDecimal(s))

    s = "0.25"
    expected = ExactFraction(25, 100)
    assertEquals(expected, parseDecimal(s))

    s = ".25"
    expected = ExactFraction(25, 100)
    assertEquals(expected, parseDecimal(s))

    s = ".123568"
    expected = ExactFraction(123568, 1000000)
    assertEquals(expected, parseDecimal(s))

    s = "-.123568"
    expected = ExactFraction(-123568, 1000000)
    assertEquals(expected, parseDecimal(s))

    // decimal w/ whole
    s = "1.25"
    expected = ExactFraction(125, 100)
    assertEquals(expected, parseDecimal(s))

    s = "-12.123568"
    expected = ExactFraction(-12123568, 1000000)
    assertEquals(expected, parseDecimal(s))

    s = "100.001"
    expected = ExactFraction(100001, 1000)
    assertEquals(expected, parseDecimal(s))

    s = "100.234"
    expected = ExactFraction(100234, 1000)
    assertEquals(expected, parseDecimal(s))

    s = "234.001"
    expected = ExactFraction(234001, 1000)
    assertEquals(expected, parseDecimal(s))

    val n = "11111111111111111111111111"
    val d = "100000000000000000000000000"
    s = "0.11111111111111111111111111"
    expected = ExactFraction(BigInteger(n), BigInteger(d))
    assertEquals(expected, parseDecimal(s))

    s = ".11111111111111111111111111"
    expected = ExactFraction(BigInteger(n), BigInteger(d))
    assertEquals(expected, parseDecimal(s))

    // e-notation
    s = "3.90E-3" // 0.00390
    expected = ExactFraction(39, 10000)
    assertEquals(expected, parseDecimal(s))

    s = "3.90e-3" // 0.00390
    expected = ExactFraction(39, 10000)
    assertEquals(expected, parseDecimal(s))

    s = "-5E-10" // -0.0000000005
    expected = ExactFraction(-1, 2000000000)
    assertEquals(expected, parseDecimal(s))

    s = "-5E10" // -50000000000
    expected = ExactFraction(BigInteger("-50000000000"))
    assertEquals(expected, parseDecimal(s))

    s = "172E14" // 17200000000000000
    expected = ExactFraction(BigInteger("17200000000000000"))
    assertEquals(expected, parseDecimal(s))

    // errors
    s = "abc"
    assertFailsWithMessage<NumberFormatException>("Error parsing abc") { parseDecimal(s) }

    s = "1.1.1"
    assertFailsWithMessage<NumberFormatException>("Error parsing 1.1.1") { parseDecimal(s) }

    s = "."
    assertFailsWithMessage<NumberFormatException>("Error parsing .") { parseDecimal(s) }

    s = "5."
    assertFailsWithMessage<NumberFormatException>("Error parsing 5.") { parseDecimal(s) }

    s = "EF[1 1]"
    assertFailsWithMessage<NumberFormatException>("Error parsing EF[1 1]") { parseDecimal(s) }

    s = "--1234"
    assertFailsWithMessage<NumberFormatException>("Error parsing --1234") { parseDecimal(s) }

    s = "-12-34"
    assertFailsWithMessage<NumberFormatException>("Error parsing -12-34") { parseDecimal(s) }

    s = "123a456"
    assertFailsWithMessage<NumberFormatException>("Error parsing 123a456") { parseDecimal(s) }

    s = "E10"
    assertFailsWithMessage<NumberFormatException>("Error parsing E10") { parseDecimal(s) }

    s = "e-1"
    assertFailsWithMessage<NumberFormatException>("Error parsing e-1") { parseDecimal(s) }

    s = "123e"
    assertFailsWithMessage<NumberFormatException>("Error parsing 123e") { parseDecimal(s) }

    s = "123e-"
    assertFailsWithMessage<NumberFormatException>("Error parsing 123e-") { parseDecimal(s) }

    s = "12E1.3"
    assertFailsWithMessage<NumberFormatException>("Error parsing 12E1.3") { parseDecimal(s) }

    s = "1 e 1"
    assertFailsWithMessage<NumberFormatException>("Error parsing 1 e 1") { parseDecimal(s) }
}

fun runParseEFStringTests() {
    var s = "EF[0 1]"
    var expected = ExactFraction(0)
    assertEquals(expected, parseEFString(s))

    s = "EF[-3 1]"
    expected = ExactFraction(-3)
    assertEquals(expected, parseEFString(s))

    s = "EF[17 29]"
    expected = ExactFraction(17, 29)
    assertEquals(expected, parseEFString(s))

    s = "EF[-17 29]"
    expected = ExactFraction(-17, 29)
    assertEquals(expected, parseEFString(s))

    // errors
    s = "EF[1 0]"
    assertDivByZero { parseEFString(s) }

    s = "abc"
    assertFailsWithMessage<NumberFormatException>("Invalid EF string format: abc") { parseEFString(s) }

    s = "1.1"
    assertFailsWithMessage<NumberFormatException>("Invalid EF string format: 1.1") { parseEFString(s) }

    s = "4E2"
    assertFailsWithMessage<NumberFormatException>("Invalid EF string format: 4E2") { parseEFString(s) }

    s = "EF[1]"
    assertFailsWithMessage<NumberFormatException>("Invalid EF string format: EF[1]") { parseEFString(s) }

    s = "EF[123]"
    assertFailsWithMessage<NumberFormatException>("Invalid EF string format: EF[123]") { parseEFString(s) }

    s = "EF[1-1]"
    assertFailsWithMessage<NumberFormatException>("Invalid EF string format: EF[1-1]") { parseEFString(s) }

    s = "EF[1 1 1]"
    assertFailsWithMessage<NumberFormatException>("Invalid EF string format: EF[1 1 1]") { parseEFString(s) }
}

fun runCommonCheckEFStringTests(checkString: (String) -> Boolean) {
    var s = "EF[10 1]"
    assertTrue(checkString(s))

    s = "EF[-5 2]"
    assertTrue(checkString(s))

    s = "EF[0 ]"
    assertFalse(checkString(s))

    s = "EF[0]"
    assertFalse(checkString(s))

    s = "EF[0 0 0]"
    assertFalse(checkString(s))

    s = "EF[0.1 2]"
    assertFalse(checkString(s))

    s = "EF[]"
    assertFalse(checkString(s))

    s = "EF["
    assertFalse(checkString(s))

    s = "EF]"
    assertFalse(checkString(s))

    s = "hello world"
    assertFalse(checkString(s))
}

// toString
fun runToDecimalStringTests() {
    var ef = ExactFraction(0)
    var expected = "0"
    assertEquals(expected, ef.toDecimalString())

    ef = ExactFraction(4)
    expected = "4"
    assertEquals(expected, ef.toDecimalString())

    ef = ExactFraction(-3)
    expected = "-3"
    assertEquals(expected, ef.toDecimalString())

    ef = ExactFraction(3, 8)
    expected = "0.375"
    assertEquals(expected, ef.toDecimalString())

    ef = ExactFraction(5, 2)
    expected = "2.5"
    assertEquals(expected, ef.toDecimalString())

    ef = ExactFraction(-1, 9)
    expected = "-0.11111111"
    assertEquals(expected, ef.toDecimalString())

    ef = ExactFraction(5, 9)
    expected = "0.55555556"
    assertEquals(expected, ef.toDecimalString())

    ef = ExactFraction(-4, 19)
    expected = "-0.21052632"
    assertEquals(expected, ef.toDecimalString())

    ef = ExactFraction(3, 8)
    expected = "0.38"
    assertEquals(expected, ef.toDecimalString(2))

    ef = ExactFraction(-1, 9)
    expected = "-0.111111111111"
    assertEquals(expected, ef.toDecimalString(12))

    ef = ExactFraction(-4, 19)
    expected = "-0.21053"
    assertEquals(expected, ef.toDecimalString(5))

    ef = ExactFraction("45454.888888888888888")
    expected = "45454.88889"
    assertEquals(expected, ef.toDecimalString(5))

    val largeValue = "100000000000000000000"
    val bi = BigInteger(largeValue)
    ef = ExactFraction(bi, 3)
    expected = "33333333333333333333.333333"
    assertEquals(expected, ef.toDecimalString(6))

    // exception
    val errorMessage = "Number of digits must be non-negative"
    assertFailsWithMessage<IllegalArgumentException>(errorMessage) { createDecimalString(ExactFraction(3), -3) }
}

fun runToFractionStringTests() {
    var ef = ExactFraction(0)
    var expected = "0"
    assertEquals(expected, ef.toFractionString())

    ef = ExactFraction(4)
    expected = "4"
    assertEquals(expected, ef.toFractionString())

    ef = ExactFraction(-3)
    expected = "-3"
    assertEquals(expected, ef.toFractionString())

    ef = ExactFraction(2, 7)
    expected = "2/7"
    assertEquals(expected, ef.toFractionString())

    ef = ExactFraction(-7, 2)
    expected = "-7/2"
    assertEquals(expected, ef.toFractionString())
}

fun runToPairStringTests() {
    var ef = ExactFraction(0)
    var expected = "(0, 1)"
    assertEquals(expected, ef.toPairString())

    ef = ExactFraction(4)
    expected = "(4, 1)"
    assertEquals(expected, ef.toPairString())

    ef = ExactFraction(-3)
    expected = "(-3, 1)"
    assertEquals(expected, ef.toPairString())

    ef = ExactFraction(2, 7)
    expected = "(2, 7)"
    assertEquals(expected, ef.toPairString())

    ef = ExactFraction(-7, 2)
    expected = "(-7, 2)"
    assertEquals(expected, ef.toPairString())
}

fun runToEFStringTests() {
    var ef = ExactFraction(0)
    var expected = "EF[0 1]"
    assertEquals(expected, ef.toEFString())

    ef = ExactFraction(-105)
    expected = "EF[-105 1]"
    assertEquals(expected, ef.toEFString())

    ef = ExactFraction(19, 32)
    expected = "EF[19 32]"
    assertEquals(expected, ef.toEFString())

    ef = ExactFraction(-9, 2)
    expected = "EF[-9 2]"
    assertEquals(expected, ef.toEFString())

    val largeValue = "10000000000000000000"
    ef = ExactFraction(19, BigInteger(largeValue))
    expected = "EF[19 $largeValue]"
    assertEquals(expected, ef.toEFString())
}
