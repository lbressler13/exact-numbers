package xyz.lbres.exactnumbers.expressions.term

import kotlin.test.Test

class TermTest {
    @Test fun testConstructor() = runConstructorTests()

    @Test fun testTimes() = runTimesTests()
    @Test fun testDiv() = runDivTests()
    @Test fun testEquals() = runEqualsTests()

    @Test fun testGetSimplified() = runCommonSimplifyTests(Term::getSimplified)
    @Test fun testGetValue() = runGetValueTests()

    @Test fun testUnaryMinus() = runUnaryMinusTests()
    @Test fun testUnaryPlus() = runUnaryPlusTests()
    @Test fun testIsZero() = runIsZeroTests()
    @Test fun testInverse() = runInverseTests()

    @Test fun testGetFactorsByType() = runGetFactorsByTypeTests()
    @Test fun testGetLogs() = runGetLogsTests()
    @Test fun testGetPiCount() = runGetPiCountTests()
    @Test fun testGetSquareRoots() = runGetSquareRootsTests()

    @Test fun testToString() = runToStringTests()

    @Test fun testToExpression() = runToExpressionTests()

    @Test fun testToByte() = runToByteTests()
    @Test fun testToChar() = runToCharTests()
    @Test fun testToShort() = runToShortTests()
    @Test fun testToInt() = runToIntTests()
    @Test fun testToLong() = runToLongTests()
    @Test fun testToFloat() = runToFloatTests()
    @Test fun testToDouble() = runToDoubleTests()
}
