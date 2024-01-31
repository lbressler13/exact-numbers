package xyz.lbres.exactnumbers.exactfraction

import kotlin.test.Test

class ExactFractionHelpersTest {
    @Test fun testSimplifyFraction() = runCommonSimplifyTests(::simplifyFraction)
}
