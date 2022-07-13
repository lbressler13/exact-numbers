package shared

import assertDivByZero
import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.logs.LogNum
import exactnumbers.irrationals.pi.Pi
import expressions.term.Term
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NumTypeTest {
    @Test
    internal fun testTimes() {
        // Log
        var num1: NumType = LogNum.ZERO
        var num2: NumType = LogNum.ZERO
        var expected = Term(ExactFraction.ONE, listOf(LogNum.ZERO, LogNum.ZERO))
        assertEquals(expected, num1 * num2)

        num1 = LogNum(ExactFraction(4, 5))
        num2 = LogNum(ExactFraction(5, 4), true)
        expected = Term(
            ExactFraction.ONE,
            listOf(LogNum(ExactFraction(4, 5)), LogNum(ExactFraction(5, 4), true))
        )
        assertEquals(expected, num1 * num2)

        num1 = LogNum(ExactFraction(4, 5), 3)
        num2 = LogNum(ExactFraction(5, 4))
        expected = Term(
            ExactFraction.ONE,
            listOf(LogNum(ExactFraction(4, 5), 3), LogNum(ExactFraction(5, 4)))
        )
        assertEquals(expected, num1 * num2)

        // Pi
        num1 = Pi()
        num2 = Pi()
        expected = Term(ExactFraction.ONE, listOf(Pi(), Pi()))
        assertEquals(expected, num1 * num2)

        num1 = Pi(true)
        num2 = Pi()
        expected = Term(ExactFraction.ONE, listOf(Pi(true), Pi()))
        assertEquals(expected, num1 * num2)

        num1 = Pi(true)
        num2 = Pi(true)
        expected = Term(ExactFraction.ONE, listOf(Pi(true), Pi(true)))
        assertEquals(expected, num1 * num2)

        // Both
        num1 = LogNum(ExactFraction(25), 4)
        num2 = Pi(true)
        expected = Term(ExactFraction.ONE, listOf(LogNum(ExactFraction(25), 4), Pi(true)))
        assertEquals(expected, num1 * num2)

        num1 = LogNum(ExactFraction(25, 92))
        num2 = Pi()
        expected = Term(ExactFraction.ONE, listOf(LogNum(ExactFraction(25, 92)), Pi()))
        assertEquals(expected, num1 * num2)
    }

    @Test
    internal fun testDiv() {
        // error
        assertDivByZero { LogNum.ONE / LogNum.ZERO }

        // Log
        var num1: NumType = LogNum(ExactFraction.EIGHT)
        var num2: NumType = LogNum(ExactFraction(15, 4), 7)
        var expected = Term(ExactFraction.ONE, listOf(LogNum(ExactFraction.EIGHT), LogNum(ExactFraction(15, 4), 7, true)))
        assertEquals(expected, num1 / num2)

        num1 = LogNum(ExactFraction(1, 7))
        num2 = LogNum(ExactFraction.FOUR, true)
        expected = Term(ExactFraction.ONE, listOf(LogNum(ExactFraction(1, 7)), LogNum(ExactFraction.FOUR)))
        assertEquals(expected, num1 / num2)

        num1 = LogNum(ExactFraction(1, 7), true)
        num2 = LogNum(ExactFraction.FOUR, true)
        expected = Term(ExactFraction.ONE, listOf(LogNum(ExactFraction(1, 7), true), LogNum(ExactFraction.FOUR)))
        assertEquals(expected, num1 / num2)

        // Pi
        num1 = Pi()
        num2 = Pi()
        expected = Term(ExactFraction.ONE, listOf(Pi(), Pi(true)))
        assertEquals(expected, num1 / num2)

        num1 = Pi()
        num2 = Pi(true)
        expected = Term(ExactFraction.ONE, listOf(Pi(), Pi()))
        assertEquals(expected, num1 / num2)

        num1 = Pi(true)
        num2 = Pi(true)
        expected = Term(ExactFraction.ONE, listOf(Pi(true), Pi()))
        assertEquals(expected, num1 / num2)

        // Both
        num1 = LogNum(ExactFraction.HALF)
        num2 = Pi(true)
        expected = Term(ExactFraction.ONE, listOf(LogNum(ExactFraction.HALF), Pi()))
        assertEquals(expected, num1 / num2)

        num1 = Pi(true)
        num2 = LogNum(ExactFraction(15, 7), 4)
        expected = Term(ExactFraction.ONE, listOf(Pi(true), LogNum(ExactFraction(15, 7), 4, true)))
        assertEquals(expected, num1 / num2)

        num1 = Pi()
        num2 = LogNum(ExactFraction(15, 7), true)
        expected = Term(ExactFraction.ONE, listOf(Pi(), LogNum(ExactFraction(15, 7))))
        assertEquals(expected, num1 / num2)
    }
}
