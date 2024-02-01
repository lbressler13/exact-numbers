package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.testutils.assertFailsWithMessage
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class LogConstructorsTest {
    @Test
    fun testLog() {
        // error
        var error = "Cannot calculate log of 0"
        assertFailsWithMessage<ArithmeticException>(error) { Log(ExactFraction.ZERO) }
        assertFailsWithMessage<ArithmeticException>(error) { Log(0) }
        assertFailsWithMessage<ArithmeticException>(error) { Log(0L) }
        assertFailsWithMessage<ArithmeticException>(error) { Log(BigInteger.ZERO) }

        error = "Cannot calculate log of negative number"
        assertFailsWithMessage<ArithmeticException>(error) { Log(-ExactFraction.TEN) }
        assertFailsWithMessage<ArithmeticException>(error) { Log(-10) }
        assertFailsWithMessage<ArithmeticException>(error) { Log(-10L) }
        assertFailsWithMessage<ArithmeticException>(error) { Log(-BigInteger.TEN) }
        assertFailsWithMessage<ArithmeticException>(error) { Log(ExactFraction(-4, 3)) }

        error = "Log base must be greater than 1"
        assertFailsWithMessage<ArithmeticException>(error) { Log(ExactFraction.TEN, -1) }
        assertFailsWithMessage<ArithmeticException>(error) { Log(ExactFraction.TEN, 0) }
        assertFailsWithMessage<ArithmeticException>(error) { Log(ExactFraction(4, 9), 1) }
        assertFailsWithMessage<ArithmeticException>(error) { Log(10, -1) }
        assertFailsWithMessage<ArithmeticException>(error) { Log(10L, 0) }
        assertFailsWithMessage<ArithmeticException>(error) { Log(BigInteger.TEN, 1) }

        // ExactFraction
        // zero
        var expectedArgument = ExactFraction.ONE
        var expectedBase = 10
        var logs = listOf(Log(ExactFraction.ONE), Log(1), Log(1L), Log(BigInteger.ONE))
        logs.forEach {
            assertEquals(expectedArgument, it.argument)
            assertEquals(expectedBase, it.base)
            assertFalse(it.isInverted)
        }

        // just argument
        expectedArgument = ExactFraction.TWO
        expectedBase = 10
        logs = listOf(Log(ExactFraction.TWO), Log(2), Log(2L), Log(BigInteger.TWO))
        logs.forEach {
            assertEquals(expectedArgument, it.argument)
            assertEquals(expectedBase, it.base)
            assertFalse(it.isInverted)
        }

        var log = Log(ExactFraction(107, 3))
        expectedArgument = ExactFraction(107, 3)
        expectedBase = 10
        assertEquals(expectedArgument, log.argument)
        assertEquals(expectedBase, log.base)
        assertFalse(log.isInverted)

        // argument + base
        expectedArgument = ExactFraction.TWO
        expectedBase = 2
        logs = listOf(Log(ExactFraction.TWO, 2), Log(2, 2), Log(2L, 2), Log(BigInteger.TWO, 2))
        logs.forEach {
            assertEquals(expectedArgument, it.argument)
            assertEquals(expectedBase, it.base)
            assertFalse(it.isInverted)
        }

        log = Log(ExactFraction(107, 3), 5)
        expectedArgument = ExactFraction(107, 3)
        expectedBase = 5
        assertEquals(expectedArgument, log.argument)
        assertEquals(expectedBase, log.base)
        assertFalse(log.isInverted)
    }
}
