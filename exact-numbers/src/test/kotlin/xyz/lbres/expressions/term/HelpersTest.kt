package xyz.lbres.expressions.term

import org.junit.Test

class HelpersTest {
    @Test fun testSimplifyTerm() = runCommonSimplifyTests(::simplifyTerm)
}
