package xyz.lbres.exactnumbers.expressions.expression

import xyz.lbres.exactnumbers.expressions.expression.simple.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test

class SimpleExpressionTest {
    @Test fun testEquals() = runEqualsTests()
    @Test fun testToString() = runToStringTests()

    @Test fun testUnaryMinus() = runUnaryMinusTests()
    @Test fun testUnaryPlus() = runUnaryPlusTests()
    @Test fun testInverse() = runInverseTests()
    @Test fun testGetValue() = runGetValueTests()
    @Test fun testGetSimplified() = runGetSimplifiedTests()
    @Test fun isZero() = runIsZeroTests()

    @Test fun testToTerm() = runToTermTests()
    @Test fun testToByte() = runToByteTests()
    @Test fun testToChar() = runToCharTests()
    @Test fun testToShort() = runToShortTests()
    @Test fun testToInt() = runToIntTests()
    @Test fun testToLong() = runToLongTests()
    @Test fun testToFloat() = runToFloatTests()
    @Test fun testToDouble() = runToDoubleTests()
}
