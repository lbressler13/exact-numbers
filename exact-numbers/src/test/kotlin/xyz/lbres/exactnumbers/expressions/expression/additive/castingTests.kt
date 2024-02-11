package xyz.lbres.exactnumbers.expressions.expression.additive

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.Expression
import xyz.lbres.exactnumbers.expressions.expression.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.testutils.assertSucceeds
import xyz.lbres.exactnumbers.testutils.getCastingOverflowAssertion
import kotlin.test.assertEquals

private val assertCastingOverflow = getCastingOverflowAssertion<AdditiveExpression>("Expression")

fun runToTermTests() {
}

fun runToByteTests() {
    runWholeNumberCastingTests(Long::toByte, AdditiveExpression::toByte, Byte.MIN_VALUE, Byte.MAX_VALUE, "Byte")
}

fun runToCharTests() {
    var expr = AdditiveExpression(Expression.ZERO, Expression.ZERO)
    var expected = 0.toChar()
    assertEquals(expected, expr.toChar())

    expr = AdditiveExpression(simpleExpr1, simpleExpr1.inverse())
    expected = 1.toChar()
    assertEquals(expected, expr.toChar())

    expr = AdditiveExpression(simpleExpr1, AdditiveExpression(simpleExpr2, simpleExpr2.inverse()))
    expected = 25.toChar()
    assertEquals(expected, expr.toChar())

    expr = AdditiveExpression(partialMultExpr.inverse(), -simpleExpr2.inverse())
    expected = 0.toChar()
    assertEquals(expected, expr.toChar())

    expr = AdditiveExpression(partialMultExpr, simpleExpr2)
    assertCastingOverflow("Char", expr) { expr.toChar() }

    val maxExpr = SimpleExpression(Term.fromValues(ExactFraction(Char.MAX_VALUE.code), emptyList()))
    expr = AdditiveExpression(maxExpr, SimpleExpression(Term.fromValues(one, listOf(Log(11).inverse()))))
    assertSucceeds("Cast expected to succeed") { expr.toChar() }

    expr = AdditiveExpression(maxExpr, SimpleExpression(Term.fromValues(one, listOf(Log(11)))))
    assertCastingOverflow("Char", expr) { expr.toChar() }
}

fun runToShortTests() {
    runWholeNumberCastingTests(Long::toShort, AdditiveExpression::toShort, Short.MIN_VALUE, Short.MAX_VALUE, "Short")
}

fun runToIntTests() {
    runWholeNumberCastingTests(Long::toInt, AdditiveExpression::toInt, Int.MIN_VALUE, Int.MAX_VALUE, "Int")
}

fun runToLongTests() {
    runWholeNumberCastingTests({ it }, AdditiveExpression::toLong, Long.MIN_VALUE, Long.MAX_VALUE, "Long")
}

fun runToFloatTests() {
    runDecimalNumberCastingTests(Double::toFloat, AdditiveExpression::toFloat, Float.MAX_VALUE, "Float")
}

fun runToDoubleTests() {
    runDecimalNumberCastingTests({ it }, AdditiveExpression::toDouble, Double.MAX_VALUE, "Double")
}

/**
 * Run tests for a single type of whole number
 *
 * @param castLong (Long) -> T: cast a long value to a value of the current number type
 * @param castExpr (AdditiveExpression) -> T: cast a multiplicative expression to a value of the current number type
 * @param minValue T: minimum valid value for the current number type
 * @param maxValue T: maximum valid value for the current number type
 * @param type [String]: string representation of type, which is used in overflow exceptions
 */
private fun <T : Number> runWholeNumberCastingTests(castLong: (Long) -> T, castExpr: (AdditiveExpression) -> T, minValue: T, maxValue: T, type: String) {
    var expr = AdditiveExpression(Expression.ZERO, Expression.ZERO)
    var expected = castLong(0)
    assertEquals(expected, castExpr(expr))

    expr = AdditiveExpression(simpleExpr1, -simpleExpr1)
    expected = castLong(1)
    assertEquals(expected, castExpr(expr))

    expr = AdditiveExpression(simpleExpr1, MultiplicativeExpression(simpleExpr2, simpleExpr2.inverse()))
    expected = castLong(25)
    assertEquals(expected, castExpr(expr))

    expr = AdditiveExpression(simpleExpr1, AdditiveExpression(simpleExpr2, -simpleExpr1))
    expected = castLong(25)
    assertEquals(expected, castExpr(expr))

//    expr = AdditiveExpression(partialExpr, simpleExpr2)
//    expected = castLong(-58)
//    assertEquals(expected, castExpr(expr))
//
//    expr = AdditiveExpression(partialExpr.inverse(), -simpleExpr2.inverse())
//    expected = castLong(0)
//    assertEquals(expected, castExpr(expr))
//
//    val minExpr = SimpleExpression(Term.fromValues(ExactFraction(minValue.toLong()), emptyList()))
//    expr = AdditiveExpression(minExpr, SimpleExpression(Term.fromValues(one, listOf(Log(11).inverse()))))
//    assertSucceeds("Cast expected to succeed") { castExpr(expr) }
//
//    expr = AdditiveExpression(minExpr, SimpleExpression(Term.fromValues(one, listOf(Log(11)))))
//    assertCastingOverflow(type, expr) { castExpr(expr) }
//
//    val maxExpr = SimpleExpression(Term.fromValues(ExactFraction(maxValue.toLong()), emptyList()))
//    expr = AdditiveExpression(maxExpr, SimpleExpression(Term.fromValues(one, listOf(Log(11).inverse()))))
//    assertSucceeds("Cast expected to succeed") { castExpr(expr) }
//
//    expr = AdditiveExpression(maxExpr, SimpleExpression(Term.fromValues(one, listOf(Log(11)))))
//    assertCastingOverflow(type, expr) { castExpr(expr) }
}

/**
 * Run tests for a single type of decimal number
 *
 * @param castDouble (Double) -> T: cast a double value to a value of the current number type
 * @param castExpr (AdditiveExpression) -> T: cast a multiplicative expression to a value of the current number type
 * @param maxValue T: maximum valid value for the current number type
 * @param type [String]: string representation of type, which is used in overflow exceptions
 */
private fun <T : Number> runDecimalNumberCastingTests(castDouble: (Double) -> T, castExpr: (AdditiveExpression) -> T, maxValue: T, type: String) {
    var expr = AdditiveExpression(Expression.ZERO, Expression.ZERO)
    var expected = castDouble(0.0)
    assertEquals(expected, castExpr(expr))

    expr = AdditiveExpression(simpleExpr1, -simpleExpr1)
    expected = castDouble(1.0)
    assertEquals(expected, castExpr(expr))

    expr = AdditiveExpression(simpleExpr1, MultiplicativeExpression(simpleExpr2, simpleExpr2.inverse()))
    expected = castDouble(25.132741228718345)
    assertEquals(expected, castExpr(expr))

    expr = AdditiveExpression(simpleExpr1, AdditiveExpression(simpleExpr2, -simpleExpr1))
    expected = castDouble(25.132741228718345)
    assertEquals(expected, castExpr(expr))
//
//    expr = MultiplicativeExpression(partialExpr, simpleExpr2)
//    expected = castDouble(-58.61224322251436)
//    assertEquals(expected, castExpr(expr))
//
//    expr = MultiplicativeExpression(partialExpr.inverse(), -simpleExpr2.inverse())
//    expected = castDouble(0.01706128182474811)
//    assertEquals(expected, castExpr(expr))
//
//    val largeValue = maxValue.toDouble().toBigDecimal().toBigInteger()
//    val smallValue = (-maxValue.toDouble()).toBigDecimal().toBigInteger()
//
//    val minExpr = SimpleExpression(Term.fromValues(ExactFraction(smallValue), emptyList()))
//    expr = MultiplicativeExpression(minExpr, SimpleExpression(Term.fromValues(one, listOf(Log(11).inverse()))))
//    assertSucceeds("Cast expected to succeed") { castExpr(expr) }
//
//    expr = MultiplicativeExpression(minExpr, SimpleExpression(Term.fromValues(ExactFraction.TEN, listOf(Log(11)))))
//    assertCastingOverflow(type, expr) { castExpr(expr) }
//
//    val maxExpr = SimpleExpression(Term.fromValues(ExactFraction(largeValue), emptyList()))
//    expr = MultiplicativeExpression(maxExpr, SimpleExpression(Term.fromValues(one, listOf(Log(11).inverse()))))
//    assertSucceeds("Cast expected to succeed") { castExpr(expr) }
//
//    expr = MultiplicativeExpression(maxExpr, SimpleExpression(Term.fromValues(ExactFraction.TEN, listOf(Log(11)))))
//    assertCastingOverflow(type, expr) { castExpr(expr) }
}
