package xyz.lbres.exactnumbers.expressions.expression.multiplicative

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.Expression
import xyz.lbres.exactnumbers.expressions.expression.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.exactnumbers.testutils.getCastingOverflowAssertion
import kotlin.test.assertEquals

private val assertCastingOverflow = getCastingOverflowAssertion<MultiplicativeExpression>("Expression")

private val simpleExpr1 = SimpleExpression(Term.fromValues(ExactFraction.EIGHT, listOf(pi)))
private val simpleExpr2 = SimpleExpression(Term.fromValues(ExactFraction(8, 17), listOf(log4, sqrt2, piInverse, pi)))
private val simpleExpr3 = SimpleExpression(Term.fromValues(one, listOf(sqrt1)))
private val partialExpr = MultiplicativeExpression(simpleExpr3, simpleExpr1)

fun runToTermTests() {
    var expr = MultiplicativeExpression(Expression.ONE, Expression.ZERO)
    assertEquals(Term.ZERO, expr.toTerm())

    expr = MultiplicativeExpression(partialExpr, partialExpr.inverse())
    assertEquals(Term.ONE, expr.toTerm())

    expr = MultiplicativeExpression(simpleExpr1, Expression.ONE)
    var expected = Term.fromValues(ExactFraction.EIGHT, listOf(pi))
    assertEquals(expected, expr.toTerm())

    expr = MultiplicativeExpression(simpleExpr1, partialExpr.inverse())
    expected = Term.fromValues(one, listOf(sqrt1.inverse()))
    assertEquals(expected, expr.toTerm())

    expr = MultiplicativeExpression(simpleExpr2, simpleExpr1)
    expected = Term.fromValues(ExactFraction(512, 187), listOf(pi, log4))
    assertEquals(expected, expr.toTerm())

    val expr1 = MultiplicativeExpression(-simpleExpr2, partialExpr.inverse())
    val expr2 = MultiplicativeExpression(simpleExpr1.inverse(), MultiplicativeExpression(simpleExpr3, simpleExpr3))
    expr = MultiplicativeExpression(expr1, expr2)
    expected = Term.fromValues(ExactFraction(-3, 187), listOf(log4, Sqrt(11), piInverse, piInverse))
    assertEquals(expected, expr.toTerm())
}

fun runToByteTests() {
    runWholeNumberCastingTests(Long::toByte, MultiplicativeExpression::toByte, Byte.MIN_VALUE, Byte.MAX_VALUE, "Byte")
}

fun runToCharTests() {
}

fun runToShortTests() {
    runWholeNumberCastingTests(Long::toShort, MultiplicativeExpression::toShort, Short.MIN_VALUE, Short.MAX_VALUE, "Short")
}

fun runToIntTests() {
    runWholeNumberCastingTests(Long::toInt, MultiplicativeExpression::toInt, Int.MIN_VALUE, Int.MAX_VALUE, "Int")
}

fun runToLongTests() {
    runWholeNumberCastingTests({ it }, MultiplicativeExpression::toLong, Long.MIN_VALUE, Long.MAX_VALUE, "Long")
}

fun runToFloatTests() {
    runDecimalNumberCastingTests(Double::toFloat, MultiplicativeExpression::toFloat, Float.MAX_VALUE, "Float")
}

fun runToDoubleTests() {
    runDecimalNumberCastingTests({ it }, MultiplicativeExpression::toDouble, Double.MAX_VALUE, "Double")
}

/**
 * Run tests for a single type of whole number
 *
 * @param castLong (Long) -> T: cast a long value to a value of the current number type
 * @param castExpr (MultiplicativeExpression) -> T: cast a multiplicative expression to a value of the current number type
 * @param minValue T: minimum valid value for the current number type
 * @param maxValue T: maximum valid value for the current number type
 * @param type [String]: string representation of type, which is used in overflow exceptions
 */
private fun <T : Number> runWholeNumberCastingTests(castLong: (Long) -> T, castExpr: (MultiplicativeExpression) -> T, minValue: T, maxValue: T, type: String) {
}

/**
 * Run tests for a single type of decimal number
 *
 * @param castDouble (Double) -> T: cast a double value to a value of the current number type
 * @param castExpr (MultiplicativeExpression) -> T: cast a multiplicative expression to a value of the current number type
 * @param maxValue T: maximum valid value for the current number type
 * @param type [String]: string representation of type, which is used in overflow exceptions
 */
private fun <T : Number> runDecimalNumberCastingTests(castDouble: (Double) -> T, castExpr: (MultiplicativeExpression) -> T, maxValue: T, type: String) {
}
