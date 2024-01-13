package xyz.lbres.exactnumbers.exactfraction

import kotlin.test.Test

class ExactFractionOperatorsTest {
    @Test fun testEfAdd() = runCommonPlusTests(::efAdd)
    @Test fun testEfTimes() = runCommonTimesTests(::efTimes)
    @Test fun testEfPow() = runCommonPowTests(::efPow)
}
