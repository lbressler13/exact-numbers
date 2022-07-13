package expressions.term

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.log.Log
import exactnumbers.irrationals.pi.Pi
import shared.NumType
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class TermTest {
    private val logNum1 = Log(ExactFraction(15, 4))
    private val logNum2 = Log(ExactFraction.EIGHT, 7)
    private val logNum3 = Log(ExactFraction(19, 33), true)
    private val logNum4 = Log(ExactFraction(25, 121))
    private val one = ExactFraction.ONE

    @Test
    internal fun testConstructor() {
        // zero
        var expectedCoeff = ExactFraction.ZERO
        var expectedNumbers: List<NumType> = listOf()

        var term = Term(ExactFraction.ZERO, listOf())
        assertEquals(expectedCoeff, term.coefficient)
        assertEquals(expectedNumbers, term.numbers)

        term = Term(ExactFraction.ZERO, listOf(logNum1, logNum3, Pi()))
        assertEquals(expectedCoeff, term.coefficient)
        assertEquals(expectedNumbers, term.numbers)

        term = Term(ExactFraction.FIVE, listOf(logNum1, Log.ZERO))
        assertEquals(expectedCoeff, term.coefficient)
        assertEquals(expectedNumbers, term.numbers)

        // others
        term = Term(-ExactFraction.FIVE, listOf())
        expectedCoeff = -ExactFraction.FIVE
        expectedNumbers = listOf()
        assertEquals(expectedCoeff, term.coefficient)
        assertEquals(expectedNumbers, term.numbers)

        term = Term(ExactFraction(17, 3), listOf(Pi(), logNum2))
        expectedCoeff = ExactFraction(17, 3)
        expectedNumbers = listOf(Pi(), logNum2)
        assertEquals(expectedCoeff, term.coefficient)
        assertEquals(expectedNumbers, term.numbers)

        term = Term(
            ExactFraction(-4, 5),
            listOf(Pi(true), logNum2, logNum1, Pi(), logNum1.swapDivided(), logNum2)
        )
        expectedCoeff = ExactFraction(-4, 5)
        expectedNumbers = listOf(Pi(true), logNum2, logNum1, Pi(), logNum1.swapDivided(), logNum2)
        assertEquals(expectedCoeff, term.coefficient)
        assertEquals(expectedNumbers, term.numbers)
    }

    @Test
    internal fun testFromValues() {
        // zero
        var expectedCoeff = ExactFraction.ZERO
        var expectedNumbers: List<NumType> = listOf()

        var term = Term.fromValues(ExactFraction.ZERO, listOf(), 0)
        assertEquals(expectedCoeff, term.coefficient)
        assertEquals(expectedNumbers, term.numbers)

        term = Term.fromValues(ExactFraction.ZERO, listOf(logNum1, Log.ONE), 8)
        assertEquals(expectedCoeff, term.coefficient)
        assertEquals(expectedNumbers, term.numbers)

        term = Term.fromValues(-ExactFraction.EIGHT, listOf(logNum1, logNum2, Log.ZERO, logNum3), 5)
        assertEquals(expectedCoeff, term.coefficient)
        assertEquals(expectedNumbers, term.numbers)

        // nonzero
        term = Term.fromValues(ExactFraction(-5, 7), listOf(), 0)
        expectedCoeff = ExactFraction(-5, 7)
        expectedNumbers = listOf()
        assertEquals(expectedCoeff, term.coefficient)
        assertEquals(expectedNumbers, term.numbers)

        term = Term.fromValues(ExactFraction.EIGHT, listOf(), 3)
        expectedCoeff = ExactFraction.EIGHT
        expectedNumbers = listOf(Pi(), Pi(), Pi())
        assertEquals(expectedCoeff, term.coefficient)
        assertEquals(expectedNumbers, term.numbers)

        term = Term.fromValues(ExactFraction.EIGHT, listOf(), -3)
        expectedCoeff = ExactFraction.EIGHT
        expectedNumbers = listOf(Pi(true), Pi(true), Pi(true))
        assertEquals(expectedCoeff, term.coefficient)
        assertEquals(expectedNumbers, term.numbers)

        term = Term.fromValues(ExactFraction(-2, 191), listOf(logNum1, logNum2, logNum1), 0)
        expectedCoeff = ExactFraction(-2, 191)
        expectedNumbers = listOf(logNum1, logNum2, logNum1)
        assertEquals(expectedCoeff, term.coefficient)
        assertEquals(expectedNumbers, term.numbers)

        term = Term.fromValues(ExactFraction(-2, 191), listOf(logNum1, logNum2, logNum3), 2)
        expectedCoeff = ExactFraction(-2, 191)
        expectedNumbers = listOf(logNum1, logNum2, logNum3, Pi(), Pi())
        assertEquals(expectedCoeff, term.coefficient)
        assertEquals(expectedNumbers, term.numbers)

        term = Term.fromValues(ExactFraction(22), listOf(logNum1, logNum2, logNum3), -2)
        expectedCoeff = ExactFraction(22)
        expectedNumbers = listOf(logNum1, logNum2, logNum3, Pi(true), Pi(true))
        assertEquals(expectedCoeff, term.coefficient)
        assertEquals(expectedNumbers, term.numbers)
    }

    @Test
    internal fun testEquals() {
        // equal
        var term1 = Term.ZERO
        assertEquals(term1, term1)

        term1 = Term(ExactFraction(-17, 4), listOf())
        assertEquals(term1, term1)

        term1 = Term(one, listOf(logNum1, logNum2))
        assertEquals(term1, term1)

        term1 = Term(one, listOf(Pi(), Pi()))
        assertEquals(term1, term1)

        term1 = Term(ExactFraction.EIGHT, listOf(Pi(true), logNum4, logNum3, Pi(), logNum1))
        assertEquals(term1, term1)

        term1 = Term(ExactFraction.EIGHT, listOf(Pi(true), logNum4, logNum3, Pi(), logNum1))
        var term2 = Term(ExactFraction.EIGHT, listOf(logNum3, Pi(), Pi(true), logNum1, logNum4))
        assertEquals(term1, term2)
        assertEquals(term2, term1)

        // not equal
        term1 = Term(one, listOf())
        term2 = Term(ExactFraction.NEG_ONE, listOf())
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term(ExactFraction.TWO, listOf())
        term2 = Term(ExactFraction.HALF, listOf())
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term(one, listOf(logNum1))
        term2 = Term(one, listOf())
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
        term2 = Term(one, listOf())
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

        term1 = Term(ExactFraction(5, 7), listOf(Pi(), Pi(true), logNum1, logNum1))
        term2 = Term(ExactFraction.FIVE, listOf(Pi(), Pi(true), logNum1, logNum1))
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)

        term1 = Term(ExactFraction.EIGHT, listOf(Pi(true), logNum3, logNum4))
        term2 = Term(ExactFraction(-17, 15), listOf(logNum1, logNum2, logNum3))
        assertNotEquals(term1, term2)
        assertNotEquals(term2, term1)
    }

    @Test
    internal fun testUnaryMinus() {
        var term = Term.ZERO
        var expected = Term.ZERO
        assertEquals(expected, -term)

        term = Term(one, listOf(Log.ONE))
        expected = Term(ExactFraction.NEG_ONE, listOf(Log.ONE))
        assertEquals(expected, -term)

        term = Term(ExactFraction.NEG_ONE, listOf(Pi()))
        expected = Term(one, listOf(Pi()))
        assertEquals(expected, -term)

        term = Term(-ExactFraction.SIX, listOf(logNum3, logNum4, Pi(true)))
        expected = Term(ExactFraction.SIX, listOf(logNum3, logNum4, Pi(true)))
        assertEquals(expected, -term)

        term = Term(ExactFraction(15, 44), listOf())
        expected = Term(ExactFraction(-15, 44), listOf())
        assertEquals(expected, -term)

        term = Term(ExactFraction(-15, 44), listOf(Pi(), Pi(true), Pi(), logNum2, logNum3, logNum4))
        expected = Term(ExactFraction(15, 44), listOf(Pi(), Pi(true), Pi(), logNum2, logNum3, logNum4))
        assertEquals(expected, -term)
    }

    @Test
    internal fun testUnaryPlus() {
        var term = Term.ZERO
        assertEquals(term, +term)

        term = Term(one, listOf(Log.ONE))
        assertEquals(term, +term)

        term = Term(one, listOf(Pi()))
        assertEquals(term, +term)

        term = Term(-ExactFraction.SIX, listOf(logNum3, logNum4, Pi(true)))
        assertEquals(term, +term)

        term = Term(ExactFraction(15, 44), listOf())
        assertEquals(term, +term)

        term = Term(ExactFraction(-15, 44), listOf(Pi(), Pi(true), Pi(), logNum2, logNum3, logNum4))
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

        term = Term(one, listOf(Log.ONE))
        assertFalse(term.isZero())

        term = Term(one, listOf(Pi()))
        assertFalse(term.isZero())

        term = Term(ExactFraction(5, 4), listOf(logNum2, Pi(true), logNum4))
        assertFalse(term.isZero())

        term = Term(-ExactFraction.HALF, listOf(logNum2, logNum2.swapDivided()))
        assertFalse(term.isZero())

        term = Term(ExactFraction(-1, 1000000), listOf(Pi(true), Pi(true), Pi(true)))
        assertFalse(term.isZero())
    }

    @Test
    fun testGetSimplified() {
        // simplified
        var term = Term(ExactFraction.EIGHT, listOf(Pi(), Pi(true)))
        var expectedCoeff = ExactFraction.EIGHT
        var expectedNumbers: List<NumType> = listOf()
        var result = term.getSimplified()
        assertEquals(expectedCoeff, result.coefficient)
        assertEquals(expectedNumbers, result.numbers)

        term = Term(ExactFraction(-3, 2), listOf(Pi(), Pi(true), Pi(), logNum1))
        expectedCoeff = ExactFraction(-3, 2)
        expectedNumbers = listOf(logNum1, Pi())
        result = term.getSimplified()
        assertEquals(expectedCoeff, result.coefficient)
        assertEquals(expectedNumbers, result.numbers)

        term = Term(ExactFraction.HALF, listOf(Log.ONE, logNum1))
        expectedCoeff = ExactFraction.HALF
        expectedNumbers = listOf(logNum1)
        result = term.getSimplified()
        assertEquals(expectedCoeff, result.coefficient)
        assertEquals(expectedNumbers, result.numbers)

        term = Term(-ExactFraction.HALF, listOf(Log.ONE, Pi(true)))
        expectedCoeff = -ExactFraction.HALF
        expectedNumbers = listOf(Pi(true))
        result = term.getSimplified()
        assertEquals(expectedCoeff, result.coefficient)
        assertEquals(expectedNumbers, result.numbers)

        term = Term(ExactFraction(18, 5), listOf(Pi(true), logNum2, Pi(true), logNum2, logNum1, logNum2.swapDivided(), Pi(true), Pi()))
        expectedCoeff = ExactFraction(18, 5)
        expectedNumbers = listOf(logNum1, logNum2, Pi(true), Pi(true))
        result = term.getSimplified()
        assertEquals(expectedCoeff, result.coefficient)
        assertEquals(expectedNumbers, result.numbers)

        // no changes
        term = Term(ExactFraction.EIGHT, listOf())
        expectedCoeff = ExactFraction.EIGHT
        expectedNumbers = listOf()
        result = term.getSimplified()
        assertEquals(expectedCoeff, result.coefficient)
        assertEquals(expectedNumbers, result.numbers)

        term = Term(ExactFraction.EIGHT, listOf(logNum1))
        expectedCoeff = ExactFraction.EIGHT
        expectedNumbers = listOf(logNum1)
        result = term.getSimplified()
        assertEquals(expectedCoeff, result.coefficient)
        assertEquals(expectedNumbers, result.numbers)

        term = Term(ExactFraction(-5, 6), listOf(Pi(true)))
        expectedCoeff = ExactFraction(-5, 6)
        expectedNumbers = listOf(Pi(true))
        result = term.getSimplified()
        assertEquals(expectedCoeff, result.coefficient)
        assertEquals(expectedNumbers, result.numbers)

        term = Term(ExactFraction.SEVEN, listOf(Pi(), Pi(), logNum1, logNum4, logNum2.swapDivided()))
        expectedCoeff = ExactFraction.SEVEN
        expectedNumbers = listOf(logNum4, logNum1, logNum2.swapDivided(), Pi(), Pi())
        result = term.getSimplified()
        assertEquals(expectedCoeff, result.coefficient)
        assertEquals(expectedNumbers, result.numbers)
    }

    @Test
    fun testGetValue() {
        // just number
        var term = Term.ZERO
        var expected = BigDecimal.ZERO
        assertEquals(expected, term.getValue())

        term = Term(ExactFraction.EIGHT, listOf())
        expected = BigDecimal("8")
        assertEquals(expected, term.getValue())

        term = Term(ExactFraction(-1, 3), listOf())
        expected = BigDecimal("-0.33333333333333333333")
        assertEquals(expected, term.getValue())

        // just logs
        term = Term(one, listOf(logNum1, logNum1.swapDivided()))
        expected = BigDecimal.ONE
        assertEquals(expected, term.getValue())

        term = Term(one, listOf(Log(ExactFraction(3333))))
        expected = BigDecimal("3.52283531366053")
        assertEquals(expected, term.getValue())

        term = Term(one, listOf(logNum1, logNum2))
        expected = BigDecimal("0.61342218956802803344500481172832")
        assertEquals(expected, term.getValue())

        // just pi
        term = Term(one, listOf(Pi(), Pi(true)))
        expected = BigDecimal.ONE
        assertEquals(expected, term.getValue())

        term = Term(one, listOf(Pi(true)))
        expected = BigDecimal("0.31830988618379069570")
        assertEquals(expected, term.getValue())

        term = Term(one, listOf(Pi(), Pi(), Pi()))
        expected = BigDecimal("31.006276680299813114880451174049119330924860257")
        assertEquals(expected, term.getValue())

        // combination
        term = Term(ExactFraction(-8, 3), listOf(Pi()))
        expected = BigDecimal("-8.3775804095727813333")
        assertEquals(expected, term.getValue())

        term = Term(ExactFraction.SEVEN, listOf(Log(ExactFraction(6, 7), 4), Pi(), Log(ExactFraction(40))))
        expected = BigDecimal("-3.9175691871908989550925271506970548502975209152")
        assertEquals(expected, term.getValue())

        term = Term(ExactFraction.HALF, listOf(Log(ExactFraction.FOUR, 2, true), Pi(true), Log(ExactFraction(123456789))))
        expected = BigDecimal("0.64390230285929702583103243749028475")
        assertEquals(expected, term.getValue())
    }

    @Test
    fun testGetLogs() {
        // empty
        var expected: List<Log> = listOf()

        var term = Term(one, listOf())
        assertEquals(expected, term.getLogs())

        term = Term(one, listOf(Pi(), Pi()))
        assertEquals(expected, term.getLogs())

        // just logs
        term = Term(one, listOf(logNum1))
        expected = listOf(logNum1)
        assertEquals(expected, term.getLogs())

        term = Term(one, listOf(logNum1, logNum1))
        expected = listOf(logNum1, logNum1)
        assertEquals(expected, term.getLogs())

        term = Term(one, listOf(logNum1, logNum1.swapDivided()))
        expected = listOf(logNum1, logNum1.swapDivided())
        assertEquals(expected, term.getLogs())

        term = Term(one, listOf(logNum3, logNum4, logNum1))
        expected = listOf(logNum3, logNum4, logNum1)
        assertEquals(expected, term.getLogs())

        // mix
        term = Term(one, listOf(Pi(), logNum3))
        expected = listOf(logNum3)
        assertEquals(expected, term.getLogs())

        term = Term(one, listOf(logNum2, Pi(), Pi(true), logNum2, logNum3, logNum4))
        expected = listOf(logNum2, logNum2, logNum3, logNum4)
        assertEquals(expected, term.getLogs())
    }

    @Test
    fun testGetPiCount() {
        // zero
        var expected = 0

        var term = Term(one, listOf())
        assertEquals(expected, term.getPiCount())

        term = Term(one, listOf(Pi(), Pi(true)))
        assertEquals(expected, term.getPiCount())

        term = Term(one, listOf(Pi(), Pi(true), Pi(), Pi(true), Pi(), Pi(true)))
        assertEquals(expected, term.getPiCount())

        term = Term(one, listOf(logNum1, logNum4))
        assertEquals(expected, term.getPiCount())

        term = Term(one, listOf(logNum3, logNum4, Pi(true), Pi(), logNum2, Pi(true), Pi()))
        assertEquals(expected, term.getPiCount())

        // just pi
        term = Term(one, listOf(Pi()))
        expected = 1
        assertEquals(expected, term.getPiCount())

        term = Term(one, listOf(Pi(true)))
        expected = -1
        assertEquals(expected, term.getPiCount())

        term = Term(one, listOf(Pi(true), Pi(true), Pi(true)))
        expected = -3
        assertEquals(expected, term.getPiCount())

        term = Term(one, listOf(Pi(), Pi(true), Pi(), Pi(), Pi(true)))
        expected = 1
        assertEquals(expected, term.getPiCount())

        // mix
        term = Term(one, listOf(Pi(true), logNum2))
        expected = -1
        assertEquals(expected, term.getPiCount())

        term = Term(one, listOf(logNum3, Pi(), Pi(), logNum2, Pi(true), Pi()))
        expected = 2
        assertEquals(expected, term.getPiCount())
    }

    @Test
    fun testToString() {
        // zero
        var term = Term.ZERO
        var expected = "<0>"
        assertEquals(expected, term.toString())

        // just coefficient
        term = Term(ExactFraction(-25), listOf())
        expected = "<-25>"
        assertEquals(expected, term.toString())

        term = Term(ExactFraction(44, 7), listOf())
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

        // mix
        term = Term(ExactFraction.EIGHT, listOf(Pi(), logNum3))
        expected = "<8x${Pi()}x$logNum3>"
        assertEquals(expected, term.toString())

        term = Term(ExactFraction(-100, 333), listOf(Pi(true), logNum2, logNum2, logNum4, Pi(), logNum1))
        expected = "<[-100/333]x${Pi(true)}x${logNum2}x${logNum2}x${logNum4}x${Pi()}x$logNum1>"
        assertEquals(expected, term.toString())
    }

    @Test internal fun testTimes() = runTimesTests()
    @Test internal fun testDiv() = runDivTests()
}
