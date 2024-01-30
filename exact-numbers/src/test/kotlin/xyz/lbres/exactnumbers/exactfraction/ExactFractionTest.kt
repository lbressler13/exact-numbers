package xyz.lbres.exactnumbers.exactfraction

import kotlin.test.Test

class ExactFractionTest {
    // all constructors
    @Test fun testConstructor() = runConstructorTests()

    // simplify
    @Test
    fun testSimplify() {
        runCommonSimplifyTests { bi1, bi2 ->
            val ef = ExactFraction(bi1, bi2)
            Pair(ef.numerator, ef.denominator)
        }
    }

    // unary operators
    @Test fun testUnaryMinus() = runUnaryMinusTests()
    @Test fun testUnaryPlus() = runUnaryPlusTests()
    @Test fun testNot() = runNotTests()
    @Test fun testInc() = runIncTests()
    @Test fun testDec() = runDecTests()

    // binary operators
    @Test fun testPlus() = runPlusTests()
    @Test fun testMinus() = runMinusTests()
    @Test fun testTimes() = runTimesTests()
    @Test fun testDiv() = runDivTests()
    @Test fun testCompareTo() = runCompareToTests()
    @Test fun testEquals() = runEqualsTests()
    @Test fun testEq() = runEqTests()
    @Test fun testPow() = runPowTests()

    // unary non-operator  functions
    @Test fun testInverse() = runInverseTests()
    @Test fun testAbsoluteValue() = runAbsoluteValueTests()
    @Test fun testIsNegative() = runIsNegativeTests()
    @Test fun testIsZero() = runIsZeroTests()

    // parsing + toString
    @Test fun testIsEFString() = runCommonCheckEFStringTests(ExactFraction.Companion::isEFString)
    @Test fun testToDecimalString() = runToDecimalStringTests()
    @Test fun testToFractionString() = runToFractionStringTests()
    @Test fun testToPairString() = runToPairStringTests()
    @Test fun testToEFString() = runToEFStringTests()

    // casting
    @Test fun testToPair() = runToPairTests()
    @Test fun testToByte() = runToByteTests()
    @Test fun testToChar() = runToCharTests()
    @Test fun testToShort() = runToShortTests()
    @Test fun testToInt() = runToIntTests()
    @Test fun testToLong() = runToLongTests()
    @Test fun testToFloat() = runToFloatTests()
    @Test fun testToDouble() = runToDoubleTests()
    @Test fun testToBigDecimal() = runToBigDecimalTests()
    @Test fun testToBigInteger() = runToBigIntegerTests()
}
