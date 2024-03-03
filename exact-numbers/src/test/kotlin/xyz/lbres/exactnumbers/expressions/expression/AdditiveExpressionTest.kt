package xyz.lbres.exactnumbers.expressions.expression

import xyz.lbres.exactnumbers.expressions.expression.additive.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test

class AdditiveExpressionTest {
    // @Test fun testEquals() = runEqualsTests() // TODO
    @Test fun testToString() = runToStringTests()

    @Test fun testUnaryMinus() = runUnaryMinusTests()
    @Test fun testUnaryPlus() = runUnaryPlusTests()
    // @Test fun testInverse() = runInverseTests() // TODO
    // @Test fun testGetValue() = runGetValueTests() // TODO
    @Test fun testIsZero() = runIsZeroTests()

    // @Test fun testToTerm() = runToTermTests()
    // @Test fun testToByte() = runToByteTests() // TODO
    // @Test fun testToChar() = runToCharTests() // TODO
    // @Test fun testToShort() = runToShortTests() // TODO
    // @Test fun testToInt() = runToIntTests() // TODO
    // @Test fun testToLong() = runToLongTests() // TODO
    // @Test fun testToFloat() = runToFloatTests() // TODO
    // @Test fun testToDouble() = runToDoubleTests() // TODO
}
