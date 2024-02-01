package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.kotlinutils.general.simpleIf
import kotlin.test.Test

class ExactFractionHelpersTest {
    @Test fun testSimplifyFraction() = runCommonSimplifyTests(::simplifyFraction)

    @Test
    fun testCreateDecimalString() = runCommonDecimalStringTests { ef, digits ->
        simpleIf(digits == null, { createDecimalString(ef, 8) }, { createDecimalString(ef, digits!!) })
    }
}
