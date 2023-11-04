package xyz.lbres.exactnumbers.irrationals.common

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.expressions.term.Term
import xyz.lbres.testutils.assertDivByZero
import kotlin.test.Test
import kotlin.test.assertEquals

class IrrationalOperatorsTest {
    @Test
    fun testTimes() {
        // Log
        var num1: Irrational = Log.ZERO
        var num2: Irrational = Log.ZERO
        var expected = Term(ExactFraction.ONE, listOf(Log.ZERO, Log.ZERO))
        assertEquals(expected, num1 * num2)

        num1 = Log(ExactFraction(4, 5))
        num2 = Log(ExactFraction(5, 4), true)
        expected = Term(
            ExactFraction.ONE,
            listOf(Log(ExactFraction(4, 5)), Log(ExactFraction(5, 4), true))
        )
        assertEquals(expected, num1 * num2)

        num1 = Log(ExactFraction(4, 5), 3)
        num2 = Log(ExactFraction(5, 4))
        expected = Term(
            ExactFraction.ONE,
            listOf(Log(ExactFraction(4, 5), 3), Log(ExactFraction(5, 4)))
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

        // Sqrt
        num1 = Sqrt(15)
        num2 = Sqrt(ExactFraction(100, 19))
        expected = Term(ExactFraction.ONE, listOf(Sqrt(15), Sqrt(ExactFraction(100, 19))))
        assertEquals(expected, num1 * num2)

        num1 = Sqrt(ExactFraction(3, 5))
        num2 = Sqrt(ExactFraction(5, 3))
        expected = Term(ExactFraction.ONE, listOf(Sqrt(ExactFraction(3, 5)), Sqrt(ExactFraction(5, 3))))
        assertEquals(expected, num1 * num2)

        // Multiple
        num1 = Log(ExactFraction(25), 4)
        num2 = Pi(true)
        expected = Term(ExactFraction.ONE, listOf(Log(ExactFraction(25), 4), Pi(true)))
        assertEquals(expected, num1 * num2)

        num1 = Sqrt(ExactFraction(25, 92))
        num2 = Pi()
        expected = Term(ExactFraction.ONE, listOf(Sqrt(ExactFraction(25, 92)), Pi()))
        assertEquals(expected, num1 * num2)

        num1 = Log(9876)
        num2 = Sqrt(9876)
        expected = Term(ExactFraction.ONE, listOf(Log(9876), Sqrt(9876)))
        assertEquals(expected, num1 * num2)
    }

    @Test
    fun testDiv() {
        // error
        assertDivByZero { Log.ONE / Log.ZERO }

        // Log
        var num1: Irrational = Log(ExactFraction.EIGHT)
        var num2: Irrational = Log(ExactFraction(15, 4), 7)
        var expected = Term(ExactFraction.ONE, listOf(Log(ExactFraction.EIGHT), Log(ExactFraction(15, 4), 7, true)))
        assertEquals(expected, num1 / num2)

        num1 = Log(ExactFraction(1, 7))
        num2 = Log(ExactFraction.FOUR, true)
        expected = Term(ExactFraction.ONE, listOf(Log(ExactFraction(1, 7)), Log(ExactFraction.FOUR)))
        assertEquals(expected, num1 / num2)

        num1 = Log(ExactFraction(1, 7), true)
        num2 = Log(ExactFraction.FOUR, true)
        expected = Term(ExactFraction.ONE, listOf(Log(ExactFraction(1, 7), true), Log(ExactFraction.FOUR)))
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

        // Sqrt
        num1 = Sqrt(123)
        num2 = Sqrt(123)
        expected = Term(ExactFraction.ONE, listOf(Sqrt(123), Sqrt(ExactFraction(1, 123))))
        assertEquals(expected, num1 / num2)

        num1 = Sqrt(ExactFraction(4, 9))
        num2 = Sqrt(ExactFraction(9, 4))
        expected = Term(ExactFraction.ONE, listOf(Sqrt(ExactFraction(4, 9)), Sqrt(ExactFraction(4, 9))))
        assertEquals(expected, num1 / num2)

        num1 = Sqrt(ExactFraction(16, 9))
        num2 = Sqrt(107)
        expected = Term(ExactFraction.ONE, listOf(Sqrt(ExactFraction(16, 9)), Sqrt(ExactFraction(1, 107))))
        assertEquals(expected, num1 / num2)

        // Multiple
        num1 = Log(ExactFraction.HALF)
        num2 = Pi(true)
        expected = Term(ExactFraction.ONE, listOf(Log(ExactFraction.HALF), Pi()))
        assertEquals(expected, num1 / num2)

        num1 = Sqrt(100)
        num2 = Log(87, 4, true)
        expected = Term(ExactFraction.ONE, listOf(Sqrt(100), Log(87, 4)))
        assertEquals(expected, num1 / num2)

        num1 = Pi()
        num2 = Sqrt(ExactFraction(7, 15))
        expected = Term(ExactFraction.ONE, listOf(Pi(), Sqrt(ExactFraction(15, 7))))
        assertEquals(expected, num1 / num2)
    }
}
