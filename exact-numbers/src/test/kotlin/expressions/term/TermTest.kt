package expressions.term

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.logs.LogNum
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class TermTest {
    @Test
    internal fun testConstructor() {
        // zero
        var expectedLogs = listOf<LogNum>()
        var expectedPiCount = 0
        var expectedCoeff = ExactFraction.ZERO

        var term = Term(listOf(), 0, ExactFraction.ZERO)
        assertEquals(expectedLogs, term.logs)
        assertEquals(expectedPiCount, term.piCount)
        assertEquals(expectedCoeff, term.coefficient)

        term = Term(listOf(LogNum.ONE, LogNum.ZERO), 7, ExactFraction.TWO)
        assertEquals(expectedLogs, term.logs)
        assertEquals(expectedPiCount, term.piCount)
        assertEquals(expectedCoeff, term.coefficient)

        term = Term(listOf(LogNum(ExactFraction.TWO, 15)), 7, ExactFraction.ZERO)
        assertEquals(expectedLogs, term.logs)
        assertEquals(expectedPiCount, term.piCount)
        assertEquals(expectedCoeff, term.coefficient)

        // nonzero
        var logs = listOf(LogNum(ExactFraction.TWO), LogNum(ExactFraction(15)))
        var piCount = 0
        var coeff = ExactFraction.ONE
        term = Term(logs, piCount, coeff)
        assertEquals(logs, term.logs)
        assertEquals(piCount, term.piCount)
        assertEquals(coeff, term.coefficient)

        logs = listOf()
        piCount = -5
        coeff = ExactFraction.ONE
        term = Term(logs, piCount, coeff)
        assertEquals(logs, term.logs)
        assertEquals(piCount, term.piCount)
        assertEquals(coeff, term.coefficient)

        logs = listOf()
        piCount = 0
        coeff = ExactFraction(17, 4)
        term = Term(logs, piCount, coeff)
        assertEquals(logs, term.logs)
        assertEquals(piCount, term.piCount)
        assertEquals(coeff, term.coefficient)

        logs = listOf(LogNum(ExactFraction(18)), LogNum(ExactFraction(15, 4)))
        piCount = 9
        coeff = ExactFraction(-22, 43)
        term = Term(logs, piCount, coeff)
        assertEquals(logs, term.logs)
        assertEquals(piCount, term.piCount)
        assertEquals(coeff, term.coefficient)
    }

    @Test
    internal fun testEquals() {
        // equal
        var term = Term.ZERO
        assertEquals(term, term)

        term = Term(listOf(), 0, ExactFraction(-17, 4))
        assertEquals(term, term)

        term = Term(listOf(), 15, ExactFraction.ONE)
        assertEquals(term, term)

        term = Term(listOf(LogNum(ExactFraction.THREE), LogNum(ExactFraction.HALF)), 0, ExactFraction.ONE)
        assertEquals(term, term)

        term = Term(listOf(LogNum(ExactFraction.THREE), LogNum(ExactFraction.HALF)), 15, ExactFraction(-17, 4))
        assertEquals(term, term)

        term = Term(listOf(LogNum(ExactFraction.TWO), LogNum(ExactFraction.THREE)), 3, ExactFraction.FOUR)
        var other = Term(listOf(LogNum(ExactFraction.THREE), LogNum(ExactFraction.TWO)), 3, ExactFraction.FOUR)
        assertEquals(term, other)
        assertEquals(other, term)

        // not equal
        term = Term(listOf(), 0, ExactFraction.ONE)
        other = Term(listOf(), 0, ExactFraction.NEG_ONE)
        assertNotEquals(term, other)
        assertNotEquals(other, term)

        term = Term(listOf(), -1, ExactFraction.ONE)
        other = Term(listOf(), 1, ExactFraction.ONE)
        assertNotEquals(term, other)
        assertNotEquals(other, term)

        term = Term(listOf(LogNum(ExactFraction.TWO)), 0, ExactFraction.ONE)
        other = Term(listOf(LogNum(ExactFraction.HALF)), 0, ExactFraction.ONE)
        assertNotEquals(term, other)
        assertNotEquals(other, term)

        term = Term(listOf(LogNum(ExactFraction(5, 7))), -3, ExactFraction(-5, 7))
        other = Term(listOf(LogNum(ExactFraction(5, 7))), -2, ExactFraction(-5, 7))
        assertNotEquals(term, other)
        assertNotEquals(other, term)

        term = Term(listOf(LogNum(ExactFraction(5, 7)), LogNum(ExactFraction.EIGHT)), -2, ExactFraction(-5, 7))
        other = Term(listOf(LogNum(ExactFraction(5, 7))), -2, ExactFraction(-5, 7))
        assertNotEquals(term, other)
        assertNotEquals(other, term)

        term = Term(listOf(LogNum(ExactFraction(5, 7))), -3, ExactFraction(-5, 7))
        other = Term(listOf(LogNum(ExactFraction(5, 7))), -2, ExactFraction(-5, 6))
        assertNotEquals(term, other)
        assertNotEquals(other, term)
    }

    @Test
    internal fun testUnaryMinus() {
        var term = Term.ZERO
        var expected = Term.ZERO
        assertEquals(expected, -term)

        term = Term(listOf(), 3, ExactFraction.ONE)
        expected = Term(listOf(), 3, ExactFraction.NEG_ONE)
        assertEquals(expected, -term)

        term = Term(listOf(LogNum(ExactFraction.EIGHT), LogNum(ExactFraction(3, 8))), 0, -ExactFraction.SIX)
        expected = Term(listOf(LogNum(ExactFraction.EIGHT), LogNum(ExactFraction(3, 8))), 0, ExactFraction.SIX)
        assertEquals(expected, -term)

        term = Term(listOf(LogNum(ExactFraction.EIGHT), LogNum(ExactFraction(3, 8))), 4, ExactFraction(7, 3))
        expected = Term(listOf(LogNum(ExactFraction.EIGHT), LogNum(ExactFraction(3, 8))), 4, ExactFraction(-7, 3))
        assertEquals(expected, -term)

        term = Term(listOf(LogNum(ExactFraction.EIGHT), LogNum(ExactFraction(3, 8))), 4, ExactFraction(-15, 44))
        expected = Term(listOf(LogNum(ExactFraction.EIGHT), LogNum(ExactFraction(3, 8))), 4, ExactFraction(15, 44))
        assertEquals(expected, -term)
    }

    @Test
    internal fun testUnaryPlus() {
        var term = Term.ZERO
        assertEquals(term, +term)

        term = Term(listOf(), 3, ExactFraction.ONE)
        assertEquals(term, +term)

        term = Term(listOf(LogNum(ExactFraction.EIGHT), LogNum(ExactFraction(3, 8))), 0, -ExactFraction.SIX)
        assertEquals(term, +term)

        term = Term(listOf(LogNum(ExactFraction.EIGHT), LogNum(ExactFraction(3, 8))), 4, ExactFraction(7, 3))
        assertEquals(term, +term)

        term = Term(listOf(LogNum(ExactFraction.EIGHT), LogNum(ExactFraction(3, 8))), 4, ExactFraction(-15, 44))
        assertEquals(term, +term)
    }

    @Test
    internal fun testIsZero() {
        // zero
        var term = Term.ZERO
        assertTrue(term.isZero())

        // not zero
        term = Term.ONE
        assertFalse(term.isZero())

        var logs = listOf(LogNum(ExactFraction.TWO), LogNum(ExactFraction(15)))
        var piCount = 0
        var coeff = ExactFraction.ONE
        term = Term(logs, piCount, coeff)
        assertFalse(term.isZero())

        logs = listOf()
        piCount = -5
        coeff = ExactFraction.ONE
        term = Term(logs, piCount, coeff)
        assertFalse(term.isZero())

        logs = listOf()
        piCount = 0
        coeff = ExactFraction(17, 4)
        term = Term(logs, piCount, coeff)
        assertFalse(term.isZero())

        logs = listOf(LogNum(ExactFraction(18)), LogNum(ExactFraction(15, 4)))
        piCount = 9
        coeff = ExactFraction(-22, 43)
        term = Term(logs, piCount, coeff)
        assertFalse(term.isZero())
    }

    @Test internal fun testTimes() = runTimesTests()
    @Test internal fun testDiv() = runDivTests()
}
