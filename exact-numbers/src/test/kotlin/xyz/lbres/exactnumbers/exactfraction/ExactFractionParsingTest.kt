package xyz.lbres.exactnumbers.exactfraction

import kotlin.test.Test

class ExactFractionParsingTest {
    @Test fun testParseDecimal() = runParseDecimalTests()
    @Test fun testParseEFString() = runParseEFStringTests()
    @Test fun testCheckIsEFString() = runCommonCheckEFStringTests(::checkIsEFString)
}
