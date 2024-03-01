package xyz.lbres.exactnumbers.expressions.expression.simple

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.expression.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.exactnumbers.testutils.TestNumber
import xyz.lbres.exactnumbers.testutils.assertSucceeds
import xyz.lbres.exactnumbers.testutils.getCastingOverflowAssertion
import kotlin.test.assertEquals

private val assertCastingOverflow = getCastingOverflowAssertion<SimpleExpression>("Expression")

// fun runToTermTests() {
//    var expr = SimpleExpression(Term.ZERO)
//    var expected = Term.ZERO
//    assertEquals(expected, expr.toTerm())
//
//    expr = SimpleExpression(Term.fromValues(ExactFraction(44, 7), emptyList()))
//    expected = Term.fromValues(ExactFraction(44, 7), emptyList())
//    assertEquals(expected, expr.toTerm())
//
//    expr = SimpleExpression(Term.fromValues(one, listOf(log2, log4, log1)))
//    expected = Term.fromValues(one, listOf(log4, log1, log2))
//    assertEquals(expected, expr.toTerm())
//
//    val factors = listOf(log2, log2, log4, testNumber1, log1, sqrt1, sqrt2, piInverse, pi)
//    expr = SimpleExpression(Term.fromValues(ExactFraction(-100, 333), factors))
//    expected = Term.fromValues(ExactFraction(-100, 333), factors)
//    assertEquals(expected, expr.toTerm())
// }

fun runToByteTests() {
    runWholeNumberCastingTests(Long::toByte, SimpleExpression::toByte, Byte.MIN_VALUE, Byte.MAX_VALUE, "Byte")
}

fun runToCharTests() {
    var expr = SimpleExpression(Term.ZERO)
    var expected = 0.toChar()
    assertEquals(expected, expr.toChar())

    expr = SimpleExpression(Term.fromValues(one, listOf(Log(29, 3), Pi(), Pi().inverse())))
    expected = 3.toChar()
    assertEquals(expected, expr.toChar())

    var factors = listOf(Sqrt(17), Pi(), Pi(), Log(1245, 12))
    expr = SimpleExpression(Term.fromValues(one, factors))
    expected = 116.toChar()
    assertEquals(expected, expr.toChar())

    factors = listOf(Sqrt(17), Pi(), Pi(), TestNumber(ExactFraction(3)), Log(1245, 12))
    expr = SimpleExpression(Term.fromValues(ExactFraction(7, 51), factors))
    expected = 48.toChar()
    assertEquals(expected, expr.toChar())

    factors = listOf(Sqrt(17), Pi(), Pi(), Log(1245, 12))
    expr = SimpleExpression(Term.fromValues(ExactFraction(123, 7), factors))
    expected = 2050.toChar()
    assertEquals(expected, expr.toChar())

    expr = SimpleExpression(Term.fromValues(ExactFraction(-14, 11), emptyList()))
    assertCastingOverflow("Char", expr) { expr.toChar() }

    expr = SimpleExpression(Term.fromValues(one, listOf(Log(ExactFraction(1, 27), 3), Pi(), Pi().inverse())))
    assertCastingOverflow("Char", expr) { expr.toChar() }

    expr = SimpleExpression(Term.fromValues(ExactFraction(Char.MAX_VALUE.code), listOf(Log(11).inverse())))
    assertSucceeds("Cast expected to succeed") { expr.toChar() }

    expr = SimpleExpression(Term.fromValues(ExactFraction(Char.MAX_VALUE.code), listOf(Log(11))))
    assertCastingOverflow("Char", expr) { expr.toChar() }
}

fun runToShortTests() {
    runWholeNumberCastingTests(Long::toShort, SimpleExpression::toShort, Short.MIN_VALUE, Short.MAX_VALUE, "Short")
}

fun runToIntTests() {
    runWholeNumberCastingTests(Long::toInt, SimpleExpression::toInt, Int.MIN_VALUE, Int.MAX_VALUE, "Int")
}

fun runToLongTests() {
    runWholeNumberCastingTests({ it }, SimpleExpression::toLong, Long.MIN_VALUE, Long.MAX_VALUE, "Long")
}

fun runToFloatTests() {
    runDecimalNumberCastingTests(Double::toFloat, SimpleExpression::toFloat, Float.MAX_VALUE, "Float")
}

fun runToDoubleTests() {
    runDecimalNumberCastingTests({ it }, SimpleExpression::toDouble, Double.MAX_VALUE, "Double")
}

/**
 * Run tests for a single type of whole number
 *
 * @param castLong (Long) -> T: cast a long value to a value of the current number type
 * @param castExpr (SimpleExpression) -> T: cast a simple expression to a value of the current number type
 * @param minValue T: minimum valid value for the current number type
 * @param maxValue T: maximum valid value for the current number type
 * @param type [String]: string representation of type, which is used in overflow exceptions
 */
private fun <T : Number> runWholeNumberCastingTests(castLong: (Long) -> T, castExpr: (SimpleExpression) -> T, minValue: T, maxValue: T, type: String) {
    var expr = SimpleExpression(Term.ZERO)
    var expected = castLong(0)
    assertEquals(expected, castExpr(expr))

    expr = SimpleExpression(Term.fromValues(ExactFraction(-14, 11), emptyList()))
    expected = castLong(-1)
    assertEquals(expected, castExpr(expr))

    expr = SimpleExpression(Term.fromValues(one, listOf(Log(ExactFraction(1, 27), 3), Pi(), Pi().inverse())))
    expected = castLong(-3)
    assertEquals(expected, castExpr(expr))

    expr = SimpleExpression(Term.fromValues(one, listOf(Log(29, 3), Pi(), Pi().inverse())))
    expected = castLong(3)
    assertEquals(expected, castExpr(expr))

    var factors = listOf(Sqrt(17), Pi(), Pi(), Log(1245, 12))
    expr = SimpleExpression(Term.fromValues(one, factors))
    expected = castLong(116)
    assertEquals(expected, castExpr(expr))

    factors = listOf(Sqrt(17), Pi(), Pi(), TestNumber(ExactFraction(3)), Log(1245, 12))
    expr = SimpleExpression(Term.fromValues(ExactFraction(7, 51), factors))
    expected = castLong(48)
    assertEquals(expected, castExpr(expr))

    factors = listOf(Sqrt(17), Pi(), Pi(), Log(1245, 12))
    expr = SimpleExpression(Term.fromValues(ExactFraction(-123, 7), factors))
    if (type == "Byte") {
        assertCastingOverflow(type, expr) { castExpr(expr) }
    } else {
        expected = castLong(-2050)
        assertEquals(expected, castExpr(expr))
    }

    expr = SimpleExpression(Term.fromValues(ExactFraction(minValue.toLong()), listOf(Log(11).inverse())))
    assertSucceeds("Cast expected to succeed") { castExpr(expr) }

    expr = SimpleExpression(Term.fromValues(ExactFraction(minValue.toLong()), listOf(Log(11))))
    assertCastingOverflow(type, expr) { castExpr(expr) }

    expr = SimpleExpression(Term.fromValues(ExactFraction(maxValue.toLong()), listOf(Log(11).inverse())))
    assertSucceeds("Cast expected to succeed") { castExpr(expr) }

    expr = SimpleExpression(Term.fromValues(ExactFraction(maxValue.toLong()), listOf(Log(11))))
    assertCastingOverflow(type, expr) { castExpr(expr) }
}

/**
 * Run tests for a single type of decimal number
 *
 * @param castDouble (Double) -> T: cast a double value to a value of the current number type
 * @param castExpr (SimpleExpression) -> T: cast a simple expression to a value of the current number type
 * @param maxValue T: maximum valid value for the current number type
 * @param type [String]: string representation of type, which is used in overflow exceptions
 */
private fun <T : Number> runDecimalNumberCastingTests(castDouble: (Double) -> T, castExpr: (SimpleExpression) -> T, maxValue: T, type: String) {
    var expr = SimpleExpression(Term.ZERO)
    var expected = castDouble(0.0)
    assertEquals(expected, castExpr(expr))

    expr = SimpleExpression(Term.fromValues(ExactFraction(-1, 3), emptyList()))
    expected = castDouble(-0.3333333333333333)
    assertEquals(expected, castExpr(expr))

    expr = SimpleExpression(Term.fromValues(one, listOf(Log(ExactFraction(1, 27), 3), Pi(), Pi().inverse())))
    expected = castDouble(-3.0)
    assertEquals(expected, castExpr(expr))

    expr = SimpleExpression(Term.fromValues(one, listOf(Log(3333))))
    expected = castDouble(3.52283531366053)
    assertEquals(expected, castExpr(expr))

    expr = SimpleExpression(Term.fromValues(ExactFraction(-8, 3), listOf(Pi())))
    expected = castDouble(-8.377580409572781)
    assertEquals(expected, castExpr(expr))

    expr = SimpleExpression(Term.fromValues(ExactFraction.EIGHT, listOf(TestNumber(ExactFraction(1, 16)), Log(4, 2).inverse(), Log(123456789), Pi().inverse())))
    expected = castDouble(0.6439023028592971)
    assertEquals(expected, castExpr(expr))

    expr = SimpleExpression(Term.fromValues(ExactFraction(-123, 7), listOf(Sqrt(17), Pi(), Pi(), Log(1245, 12))))
    expected = castDouble(-2050.790534360116)
    assertEquals(expected, castExpr(expr))

    val largeValue = maxValue.toDouble().toBigDecimal().toBigInteger()
    val smallValue = (-maxValue.toDouble()).toBigDecimal().toBigInteger()

    expr = SimpleExpression(Term.fromValues(ExactFraction(smallValue), listOf(Log(11).inverse())))
    assertSucceeds("Cast expected to succeed") { castExpr(expr) }

    expr = SimpleExpression(Term.fromValues(ExactFraction(smallValue), listOf(Log(11))))
    assertCastingOverflow(type, expr) { castExpr(expr) }

    expr = SimpleExpression(Term.fromValues(ExactFraction(largeValue), listOf(Log(11).inverse())))
    assertSucceeds("Cast expected to succeed") { castExpr(expr) }

    expr = SimpleExpression(Term.fromValues(ExactFraction(largeValue), listOf(Log(11))))
    assertCastingOverflow(type, expr) { castExpr(expr) }
}
