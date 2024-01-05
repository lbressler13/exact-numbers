package xyz.lbres.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class TermTest {
    private val logNum1 = Log(ExactFraction(15, 4))
    private val logNum2 = Log(8, 7)
    private val logNum3 = Log(ExactFraction(19, 33), true)
    private val logNum4 = Log(ExactFraction(25, 121))
    private val one = ExactFraction.ONE

    @Test fun testConstructor() = runConstructorTests()
    @Test fun testFromValues() = runFromValuesTests()

    @Test fun testTimes() = runTimesTests()
    @Test fun testDiv() = runDivTests()

    @Test fun testGetSimplified() = runGetSimplifiedTests()
    @Test fun testGetValue() = runGetValueTests()

    @Test
    fun testEquals() {
        // equal
        var term1 = Term.ZERO
        assertEquals(term1, term1)

        term1 = Term(ExactFraction(-17, 4), emptyList())
        assertEquals(term1, term1)

        term1 = Term(one, listOf(logNum1, logNum2))
        assertEquals(term1, term1)

        term1 = Term(one, listOf(Pi(), Pi()))
        assertEquals(term1, term1)

        term1 = Term(ExactFraction.EIGHT, listOf(Pi(true), logNum4, logNum3, Pi(), logNum1, Sqrt(15)))
        assertEquals(term1, term1)

        term1 = Term(ExactFraction.EIGHT, listOf(Pi(true), Sqrt(15), logNum4, logNum3, Pi(), logNum1))
        var term2 = Term(ExactFraction.EIGHT, listOf(logNum3, Pi(), Pi(true), logNum1, Sqrt(15), logNum4))
        assertEquals(term1, term2)
        assertEquals(term2, term1)

        // not equal
        term1 = Term(one, emptyList())
        term2 = Term(ExactFraction.NEG_ONE, emptyList())
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term(ExactFraction.TWO, emptyList())
        term2 = Term(ExactFraction.HALF, emptyList())
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term(one, listOf(logNum1))
        term2 = Term(one, emptyList())
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term(one, listOf(logNum1))
        term2 = Term(one, listOf(logNum1.swapDivided()))
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term(one, listOf(logNum1))
        term2 = Term(one, listOf(logNum1, logNum2))
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term(one, listOf(Pi()))
        term2 = Term(one, emptyList())
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term(one, listOf(Pi()))
        term2 = Term(one, listOf(Pi(true)))
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term(one, listOf(Pi(), Pi(true)))
        term2 = Term(one, listOf(Pi(true)))
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term(one, listOf(Sqrt(12)))
        term2 = Term(one, emptyList())
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term(one, listOf(Sqrt(12)))
        term2 = Term(one, listOf(Sqrt(ExactFraction(1, 12))))
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term(one, listOf(Sqrt(12), Sqrt(1000)))
        term2 = Term(one, listOf(Sqrt(ExactFraction(1, 12))))
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term(ExactFraction.EIGHT, listOf(Log(15)))
        term2 = Term(ExactFraction.EIGHT, listOf(Sqrt(15)))
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term(ExactFraction(5, 7), listOf(Pi(), Pi(true), logNum1, logNum1))
        term2 = Term(ExactFraction.FIVE, listOf(Pi(), Pi(true), logNum1, logNum1))
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term(ExactFraction.EIGHT, listOf(Pi(true), logNum3, logNum4))
        term2 = Term(ExactFraction(-17, 15), listOf(logNum1, logNum2, logNum3))
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)
    }

    @Test fun testUnaryMinus() = runUnaryMinusTests()
    @Test fun testUnaryPlus() = runUnaryPlusTests()
    @Test fun testIsZero() = runIsZeroTests()

    @Test fun testGetLogs() = runGetLogsTests()
    @Test fun testGetPiCount() = runGetPiCountTests()
    @Test fun testGetSquareRoots() = runGetSquareRootsTests()

    @Test
    fun testToString() {
        // zero
        var term = Term.ZERO
        var expected = "<0>"
        assertEquals(expected, term.toString())

        // just coefficient
        term = Term(ExactFraction(-25), emptyList())
        expected = "<-25>"
        assertEquals(expected, term.toString())

        term = Term(ExactFraction(44, 7), emptyList())
        expected = "<[44/7]>"
        assertEquals(expected, term.toString())

        // just logs
        term = Term(one, listOf(Log.ONE))
        expected = "<1x${Log.ONE}>"
        assertEquals(expected, term.toString())

        term = Term(one, listOf(logNum2, logNum4, logNum1))
        expected = "<1x${logNum2}x${logNum4}x$logNum1>"
        assertEquals(expected, term.toString())

        // just pi
        term = Term(one, listOf(Pi()))
        expected = "<1x${Pi()}>"
        assertEquals(expected, term.toString())

        term = Term(one, listOf(Pi(), Pi(true), Pi()))
        expected = "<1x${Pi()}x${Pi(true)}x${Pi()}>"
        assertEquals(expected, term.toString())

        // just sqrt
        term = Term(one, listOf(Sqrt.ONE))
        expected = "<1x${Sqrt.ONE}>"
        assertEquals(expected, term.toString())

        term = Term(one, listOf(Sqrt(32), Sqrt(127), Sqrt(ExactFraction(2, 9))))
        expected = "<1x${Sqrt(32)}x${Sqrt(127)}x${Sqrt(ExactFraction(2, 9))}>"
        assertEquals(expected, term.toString())

        // mix
        term = Term(ExactFraction.EIGHT, listOf(Pi(), logNum3, Sqrt(12)))
        expected = "<8x${Pi()}x${logNum3}x${Sqrt(12)}>"
        assertEquals(expected, term.toString())

        val sqrt1 = Sqrt(ExactFraction(1000, 109))
        val sqrt2 = Sqrt(5096)
        term = Term(ExactFraction(-100, 333), listOf(Pi(true), logNum2, logNum2, sqrt1, logNum4, Pi(), logNum1, sqrt2))
        expected = "<[-100/333]x${Pi(true)}x${logNum2}x${logNum2}x${sqrt1}x${logNum4}x${Pi()}x${logNum1}x$sqrt2>"
        assertEquals(expected, term.toString())
    }
}
