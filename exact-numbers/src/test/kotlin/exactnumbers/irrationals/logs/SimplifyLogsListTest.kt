package exactnumbers.irrationals.logs

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.pi.Pi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class SimplifyLogsListTest {
    private val one = LogNum.ONE

    @Test
    internal fun testSimplifyLogsList() {
        // error
        assertFails { simplifyLogsList(listOf(Pi(), LogNum.ONE)) }

        // zero
        var expected = listOf(LogNum.ZERO)

        var logs = listOf(LogNum.ZERO)
        assertEquals(expected.sorted(), simplifyLogsList(logs))

        logs = listOf(LogNum.ZERO, LogNum.ZERO)
        assertEquals(expected.sorted(), simplifyLogsList(logs))

        logs = listOf(LogNum.ONE, LogNum.ZERO, LogNum(ExactFraction(18, 91)))
        assertEquals(expected.sorted(), simplifyLogsList(logs))

        // ones
        logs = listOf(one)
        expected = listOf(one)
        assertEquals(expected.sorted(), simplifyLogsList(logs))

        logs = listOf(one, one, one)
        expected = listOf(one)
        assertEquals(expected.sorted(), simplifyLogsList(logs))

        logs = listOf(one, LogNum(ExactFraction.EIGHT), LogNum(ExactFraction.FOUR, 3, true))
        expected = listOf(LogNum(ExactFraction.EIGHT), LogNum(ExactFraction.FOUR, 3, true))
        assertEquals(expected.sorted(), simplifyLogsList(logs))

        logs = listOf(one, LogNum(ExactFraction.EIGHT), one, one, one, LogNum(ExactFraction.FOUR, 3, true))
        expected = listOf(LogNum(ExactFraction.EIGHT), LogNum(ExactFraction.FOUR, 3, true))
        assertEquals(expected.sorted(), simplifyLogsList(logs))

        // inverses
        logs = listOf(LogNum(ExactFraction.EIGHT), LogNum(ExactFraction.EIGHT, 10, true))
        expected = listOf(one)
        assertEquals(expected.sorted(), simplifyLogsList(logs))

        logs = listOf(
            LogNum(ExactFraction.FOUR, 3, true),
            LogNum(ExactFraction.FOUR, 3, true),
            LogNum(ExactFraction.FOUR, 3, false)
        )
        expected = listOf(LogNum(ExactFraction.FOUR, 3, true))
        assertEquals(expected.sorted(), simplifyLogsList(logs))

        logs = listOf(
            LogNum(ExactFraction.FOUR, 3, true),
            LogNum(ExactFraction.FOUR, 3, false),
            LogNum(ExactFraction.FOUR, 3, false)
        )
        expected = listOf(LogNum(ExactFraction.FOUR, 3, false))
        assertEquals(expected.sorted(), simplifyLogsList(logs))

        logs = listOf(
            LogNum(ExactFraction(7, 3)),
            LogNum(ExactFraction(5, 51)),
            LogNum(ExactFraction(7, 3), 10, true),
            LogNum(ExactFraction.FOUR, 3, true),
            LogNum(ExactFraction(7, 3), 10, true),
            LogNum(ExactFraction(5, 51), 5, true),
            LogNum(ExactFraction.FOUR, 3)
        )
        expected = listOf(
            LogNum(ExactFraction(7, 3), 10, true),
            LogNum(ExactFraction(5, 51)),
            LogNum(ExactFraction(5, 51), 5, true)
        )
        assertEquals(expected.sorted(), simplifyLogsList(logs))

        // no changes
        logs = listOf(LogNum(ExactFraction.THREE))
        assertEquals(logs.sorted(), simplifyLogsList(logs))

        logs = listOf(LogNum(ExactFraction(3, 7)), LogNum(ExactFraction(7, 3)))
        assertEquals(logs.sorted(), simplifyLogsList(logs))

        logs = listOf(LogNum(ExactFraction.THREE), LogNum(ExactFraction.THREE, 9, true))
        assertEquals(logs.sorted(), simplifyLogsList(logs))

        logs = listOf(
            LogNum(ExactFraction(5, 51)),
            LogNum(ExactFraction.FOUR, 3, true),
            LogNum(ExactFraction(7, 3), 10, true),
            LogNum(ExactFraction(5, 51), 5, true),
            LogNum(ExactFraction(1000005), 3)
        )
        assertEquals(logs.sorted(), simplifyLogsList(logs))
    }
}
