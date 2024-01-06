package xyz.lbres.exactnumbers.exactfraction

import kotlin.test.Test

class ExactFractionOperatorsTest {
    @Test fun testEfAdd() = runCommonPlusTests { ef1, ef2 -> efAdd(ef1, ef2) }
    @Test fun testEfTimes() = runCommonTimesTests { ef1, ef2 -> efTimes(ef1, ef2) }
    @Test fun testEfPow() = runCommonPowTests { ef1, ef2 -> efPow(ef1, ef2) }
}
