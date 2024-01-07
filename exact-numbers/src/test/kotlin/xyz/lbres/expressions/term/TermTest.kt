package xyz.lbres.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.testutils.TestNumber
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class TermTest {
    private val logNum1 = Log(ExactFraction(15, 4))
    private val logNum2 = Log(8, 7)
    private val logNum3 = Log(ExactFraction(19, 33), true)
    private val logNum4 = Log(ExactFraction(25, 121))
    private val testNumber1 = TestNumber(ExactFraction(5, 6))
    private val testNumber2 = TestNumber(ExactFraction.SEVEN)
    private val one = ExactFraction.ONE

    @Test fun testConstructor() = runConstructorTests()

    @Test fun testTimes() = runTimesTests()
    @Test fun testDiv() = runDivTests()

    @Test fun testGetSimplified() = runCommonSimplifyTests(Term::getSimplified)
    @Test fun testGetValue() = runGetValueTests()

    @Test fun testGetIrrationalsByType() = runGetIrrationalsByTypeTests()

    @Test
    fun testEquals() {
        // equal
        var term1 = Term.ZERO
        assertEquals(term1, term1)

        term1 = Term.fromValues(ExactFraction(-17, 4), emptyList())
        assertEquals(term1, term1)

        term1 = Term.fromValues(one, listOf(logNum1, logNum2))
        assertEquals(term1, term1)

        term1 = Term.fromValues(one, listOf(Pi(), Pi()))
        assertEquals(term1, term1)

        term1 = Term.fromValues(ExactFraction.EIGHT, listOf(logNum4, logNum3, logNum1, Sqrt(15), Pi(true), Pi()))
        assertEquals(term1, term1)

        term1 = Term.fromValues(one, listOf(Pi(), TestNumber(ExactFraction(5))))
        assertEquals(term1, term1)

        // not equal
        term1 = Term.ONE
        var term2 = -Term.ONE
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term.fromValues(ExactFraction.TWO, emptyList())
        term2 = Term.fromValues(ExactFraction.HALF, emptyList())
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term.fromValues(one, listOf(logNum1))
        term2 = Term.ONE
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term.fromValues(one, listOf(logNum1))
        term2 = Term.fromValues(one, listOf(logNum1.inverse()))
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term.fromValues(one, listOf(logNum1))
        term2 = Term.fromValues(one, listOf(logNum1, logNum2))
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term.fromValues(one, listOf(Pi()))
        term2 = Term.ONE
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term.fromValues(one, listOf(Pi()))
        term2 = Term.fromValues(one, listOf(Pi(true)))
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term.fromValues(one, listOf(Pi(), Pi(true)))
        term2 = Term.fromValues(one, listOf(Pi(true)))
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term.fromValues(one, listOf(Sqrt(12)))
        term2 = Term.ONE
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term.fromValues(one, listOf(Sqrt(12)))
        term2 = Term.fromValues(one, listOf(Sqrt(ExactFraction(1, 12))))
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term.fromValues(one, listOf(Sqrt(12), Sqrt(1000)))
        term2 = Term.fromValues(one, listOf(Sqrt(ExactFraction(1, 12))))
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term.fromValues(ExactFraction.EIGHT, listOf(Log(15)))
        term2 = Term.fromValues(ExactFraction.EIGHT, listOf(Sqrt(15)))
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term.fromValues(ExactFraction(5, 7), listOf(logNum1, logNum1, Pi(), Pi(true)))
        term2 = Term.fromValues(ExactFraction.FIVE, listOf(logNum1, logNum1, Pi(), Pi(true)))
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term.fromValues(ExactFraction.EIGHT, listOf(logNum3, logNum4, Pi(true)))
        term2 = Term.fromValues(ExactFraction(-17, 15), listOf(logNum1, logNum2, logNum3))
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term.fromValues(ExactFraction.FOUR, listOf(Pi(), testNumber1))
        term2 = Term.fromValues(ExactFraction.FOUR, listOf(Pi(), testNumber1.inverse()))
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
        term = Term.fromValues(ExactFraction(-25), emptyList())
        expected = "<-25>"
        assertEquals(expected, term.toString())

        term = Term.fromValues(ExactFraction(44, 7), emptyList())
        expected = "<[44/7]>"
        assertEquals(expected, term.toString())

        // just logs
        term = Term.fromValues(one, listOf(Log.ONE))
        expected = "<1x${Log.ONE}>"
        assertEquals(expected, term.toString())

        term = Term.fromValues(one, listOf(logNum2, logNum4, logNum1))
        expected = "<1x${logNum2}x${logNum4}x$logNum1>"
        assertEquals(expected, term.toString())

        // just pi
        term = Term.fromValues(one, listOf(Pi()))
        expected = "<1x${Pi()}>"
        assertEquals(expected, term.toString())

        term = Term.fromValues(one, listOf(Pi(), Pi(true), Pi()))
        expected = "<1x${Pi()}x${Pi(true)}x${Pi()}>"
        assertEquals(expected, term.toString())

        // just sqrt
        term = Term.fromValues(one, listOf(Sqrt.ONE))
        expected = "<1x${Sqrt.ONE}>"
        assertEquals(expected, term.toString())

        term = Term.fromValues(one, listOf(Sqrt(32), Sqrt(127), Sqrt(ExactFraction(2, 9))))
        expected = "<1x${Sqrt(32)}x${Sqrt(127)}x${Sqrt(ExactFraction(2, 9))}>"
        assertEquals(expected, term.toString())

        // mix
        term = Term.fromValues(ExactFraction.EIGHT, listOf(logNum3, Sqrt(12), testNumber2, Pi()))
        expected = "<8x${logNum3}x${Sqrt(12)}x${testNumber2}x${Pi()}>"
        assertEquals(expected, term.toString())

        val sqrt1 = Sqrt(ExactFraction(1000, 109))
        val sqrt2 = Sqrt(5096)
        term = Term.fromValues(
            ExactFraction(-100, 333),
            listOf(logNum2, logNum2, logNum4, logNum1, sqrt1, sqrt2, Pi(true), Pi())
        )
        expected = "<[-100/333]x${logNum2}x${logNum2}x${logNum4}x${logNum1}x${sqrt1}x${sqrt2}x${Pi(true)}x${Pi()}>"
        assertEquals(expected, term.toString())
    }
}
