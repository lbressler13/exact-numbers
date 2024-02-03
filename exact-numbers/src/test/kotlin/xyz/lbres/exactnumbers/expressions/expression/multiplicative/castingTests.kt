package xyz.lbres.exactnumbers.expressions.expression.multiplicative

import xyz.lbres.exactnumbers.expressions.expression.MultiplicativeExpression
import xyz.lbres.exactnumbers.testutils.getCastingOverflowAssertion

private val assertCastingOverflow = getCastingOverflowAssertion<MultiplicativeExpression>("Expression")

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
