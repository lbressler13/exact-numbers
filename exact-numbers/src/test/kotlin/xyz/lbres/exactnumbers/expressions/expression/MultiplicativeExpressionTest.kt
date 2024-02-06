package xyz.lbres.exactnumbers.expressions.expression

import xyz.lbres.exactnumbers.expressions.expression.multiplicative.* // ktlint-disable no-wildcard-imports no-unused-imports
import kotlin.test.Test

class MultiplicativeExpressionTest {
    @Test fun testEquals() = runEqualsTests()
    @Test fun testToString() = runToStringTests()

    @Test fun testUnaryMinus() = runUnaryMinusTests() // TODO
    @Test fun testUnaryPlus() = runUnaryPlusTests() // TODO
    @Test fun testInverse() = runInverseTests() // TODO
    @Test fun testGetValue() = runGetValueTests() // TODO

    @Test fun testToByte() = runToByteTests() // TODO
    @Test fun testToChar() = runToCharTests() // TODO
    @Test fun testToShort() = runToShortTests() // TODO
    @Test fun testToInt() = runToIntTests() // TODO
    @Test fun testToLong() = runToLongTests() // TODO
    @Test fun testToFloat() = runToFloatTests() // TODO
    @Test fun testToDouble() = runToDoubleTests() // TODO
}
