package xyz.lbres.exactnumbers.ext

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.testutils.INT_MAX
import xyz.lbres.testutils.INT_MIN
import xyz.lbres.testutils.LONG_MAX
import xyz.lbres.testutils.LONG_MIN
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@Suppress("KotlinConstantConditions")
class NumberOperatorsTest {
    @Test
    fun testToExactFraction() {
        // Int
        var i = 0
        assertEquals(ExactFraction(i), i.toExactFraction())

        i = -1
        assertEquals(ExactFraction(i), i.toExactFraction())

        i = 1
        assertEquals(ExactFraction(i), i.toExactFraction())

        i = Int.MIN_VALUE
        assertEquals(ExactFraction(i), i.toExactFraction())

        i = Int.MAX_VALUE
        assertEquals(ExactFraction(i), i.toExactFraction())

        // Long
        var l = 0L
        assertEquals(ExactFraction(l), l.toExactFraction())

        l = -1
        assertEquals(ExactFraction(l), l.toExactFraction())

        l = 1
        assertEquals(ExactFraction(l), l.toExactFraction())

        l = Long.MIN_VALUE
        assertEquals(ExactFraction(l), l.toExactFraction())

        l = Long.MAX_VALUE
        assertEquals(ExactFraction(l), l.toExactFraction())

        // BigInteger
        var bi = BigInteger.ZERO
        assertEquals(ExactFraction(bi), bi.toExactFraction())

        bi = -BigInteger.ONE
        assertEquals(ExactFraction(bi), bi.toExactFraction())

        bi = BigInteger.ONE
        assertEquals(ExactFraction(bi), bi.toExactFraction())

        bi = BigInteger("100000000000")
        assertEquals(ExactFraction(bi), bi.toExactFraction())

        bi = BigInteger("-100000000000")
        assertEquals(ExactFraction(bi), bi.toExactFraction())
    }

    @Test
    fun testEq() {
        // Int
        runSingleIntEqTest("0", 0)
        runSingleIntEqTest("-1", -1)
        runSingleIntEqTest("1", 1)
        runSingleIntEqTest(INT_MIN.toString(), INT_MIN)
        runSingleIntEqTest(INT_MAX.toString(), INT_MAX)

        // Long
        runSingleLongEqTest("0", 0)
        runSingleLongEqTest("-1", -1)
        runSingleLongEqTest("1", 1)
        runSingleLongEqTest(LONG_MIN.toString(), LONG_MIN)
        runSingleLongEqTest(LONG_MAX.toString(), LONG_MAX)
    }

    private fun runSingleIntEqTest(string: String, int: Int) = assertTrue { BigInteger(string).eq(int) }
    private fun runSingleLongEqTest(string: String, long: Long) = assertTrue { BigInteger(string).eq(long) }
}
