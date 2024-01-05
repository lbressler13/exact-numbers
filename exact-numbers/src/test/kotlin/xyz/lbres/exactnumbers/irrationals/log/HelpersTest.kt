package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.testutils.assertFailsWithMessage
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals

class HelpersTest {
    @Test
    fun testGetLogOf() {
        // base 10
        runSingleGetLogOfTest(1, 10, "0")
        runSingleGetLogOfTest(10, 10, "1")
        runSingleGetLogOfTest(100, 10, "2")
        runSingleGetLogOfTest(200, 10, "2.301029995663981")
        runSingleGetLogOfTest(3333, 10, "3.52283531366053")
        runSingleGetLogOfTest(300, 10, "2.477121254719662")
        runSingleGetLogOfTest(77, 10, "1.8864907251724818")

        // base 2
        runSingleGetLogOfTest(1, 2, "0")
        runSingleGetLogOfTest(32, 2, "5")
        runSingleGetLogOfTest(200, 2, "7.643856189774724")

        // other base
        runSingleGetLogOfTest(1, 7, "0")
        runSingleGetLogOfTest(216, 6, "3")
        runSingleGetLogOfTest(15151515, 24, "5.202432673429519")

        // error
        val bi = BigInteger.TWO.pow(Short.MAX_VALUE.toInt())
        assertFailsWithMessage<ArithmeticException>("Error calculating log") { getLogOf(bi, 2) }
    }

    private fun runSingleGetLogOfTest(arg: Int, base: Int, expected: String) {
        assertEquals(BigDecimal(expected), getLogOf(arg.toBigInteger(), base))
    }
}
