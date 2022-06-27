package exactfraction

import kotlin.test.Test

internal class ExactFractionTest {
    // all constructors
    @Test internal fun testConstructor() = runConstructorTests()

    // simplify
    // includes all private methods used in simplification
    @Test internal fun testSimplify() = runSimplifyTests()

    // unary operators
    @Test internal fun testUnaryMinus() = runUnaryMinusTests()
    @Test internal fun testUnaryPlus() = runUnaryPlusTests()
    @Test internal fun testNot() = runNotTests()
    @Test internal fun testInc() = runIncTests()
    @Test internal fun testDec() = runDecTests()

    // binary operators
    @Test internal fun testPlus() = runPlusTests()
    @Test internal fun testMinus() = runMinusTests()
    @Test internal fun testTimes() = runTimesTests()
    @Test internal fun testDiv() = runDivTests()
    @Test internal fun testCompareTo() = runCompareToTests()
    @Test internal fun testEquals() = runEqualsTests()
    @Test internal fun testEq() = runEqTests()
    @Test internal fun testPow() = runPowTests()

    // unary non-operator internal functions
    @Test internal fun testInverse() = runInverseTests()
    @Test internal fun testAbsoluteValue() = runAbsoluteValueTests()
    @Test internal fun testIsNegative() = runIsNegativeTests()
    @Test internal fun testIsZero() = runIsZeroTests()

    // parsing + toString
    @Test internal fun testParseDecimal() = runParseDecimalTests()
    @Test internal fun testParseEFString() = runParseEFStringTests()
    @Test internal fun testCheckIsEFString() = runCheckIsEFStringTests()
    @Test internal fun testToDecimalString() = runToDecimalStringTests()
    @Test internal fun testToFractionString() = runToFractionStringTests()
    @Test internal fun testToPairString() = runToPairStringTests()
    @Test internal fun testToEFString() = runToEFStringTests()

    // casting
    @Test internal fun testToPair() = runToPairTests()
    @Test internal fun testToByte() = runToByteTests()
    @Test internal fun testToChar() = runToCharTests()
    @Test internal fun testToShort() = runToShortTests()
    @Test internal fun testToInt() = runToIntTests()
    @Test internal fun testToLong() = runToLongTests()
    @Test internal fun testToFloat() = runToFloatTests()
    @Test internal fun testToDouble() = runToDoubleTests()
    @Test internal fun testToBigDecimal() = runToBigDecimalTests()
    @Test internal fun testToBigInteger() = runToBigIntegerTests()
}
