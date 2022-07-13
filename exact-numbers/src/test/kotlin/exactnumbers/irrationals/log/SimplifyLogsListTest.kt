package exactnumbers.irrationals.log

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.pi.Pi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class SimplifyLogsListTest {
    private val one = Log.ONE

    @Test
    internal fun testSimplifyLogsList() {
        // error
        assertFails { simplifyLogsList(listOf(Pi(), Log.ONE)) }

        // zero
        var expected = listOf(Log.ZERO)

        var logs = listOf(Log.ZERO)
        assertEquals(expected.sorted(), simplifyLogsList(logs))

        logs = listOf(Log.ZERO, Log.ZERO)
        assertEquals(expected.sorted(), simplifyLogsList(logs))

        logs = listOf(Log.ONE, Log.ZERO, Log(ExactFraction(18, 91)))
        assertEquals(expected.sorted(), simplifyLogsList(logs))

        // ones
        logs = listOf(one)
        expected = listOf(one)
        assertEquals(expected.sorted(), simplifyLogsList(logs))

        logs = listOf(one, one, one)
        expected = listOf(one)
        assertEquals(expected.sorted(), simplifyLogsList(logs))

        logs = listOf(one, Log(ExactFraction.EIGHT), Log(ExactFraction.FOUR, 3, true))
        expected = listOf(Log(ExactFraction.EIGHT), Log(ExactFraction.FOUR, 3, true))
        assertEquals(expected.sorted(), simplifyLogsList(logs))

        logs = listOf(one, Log(ExactFraction.EIGHT), one, one, one, Log(ExactFraction.FOUR, 3, true))
        expected = listOf(Log(ExactFraction.EIGHT), Log(ExactFraction.FOUR, 3, true))
        assertEquals(expected.sorted(), simplifyLogsList(logs))

        // inverses
        logs = listOf(Log(ExactFraction.EIGHT), Log(ExactFraction.EIGHT, 10, true))
        expected = listOf(one)
        assertEquals(expected.sorted(), simplifyLogsList(logs))

        logs = listOf(
            Log(ExactFraction.FOUR, 3, true),
            Log(ExactFraction.FOUR, 3, true),
            Log(ExactFraction.FOUR, 3, false)
        )
        expected = listOf(Log(ExactFraction.FOUR, 3, true))
        assertEquals(expected.sorted(), simplifyLogsList(logs))

        logs = listOf(
            Log(ExactFraction.FOUR, 3, true),
            Log(ExactFraction.FOUR, 3, false),
            Log(ExactFraction.FOUR, 3, false)
        )
        expected = listOf(Log(ExactFraction.FOUR, 3, false))
        assertEquals(expected.sorted(), simplifyLogsList(logs))

        logs = listOf(
            Log(ExactFraction(7, 3)),
            Log(ExactFraction(5, 51)),
            Log(ExactFraction(7, 3), 10, true),
            Log(ExactFraction.FOUR, 3, true),
            Log(ExactFraction(7, 3), 10, true),
            Log(ExactFraction(5, 51), 5, true),
            Log(ExactFraction.FOUR, 3)
        )
        expected = listOf(
            Log(ExactFraction(7, 3), 10, true),
            Log(ExactFraction(5, 51)),
            Log(ExactFraction(5, 51), 5, true)
        )
        assertEquals(expected.sorted(), simplifyLogsList(logs))

        // no changes
        logs = listOf(Log(ExactFraction.THREE))
        assertEquals(logs.sorted(), simplifyLogsList(logs))

        logs = listOf(Log(ExactFraction(3, 7)), Log(ExactFraction(7, 3)))
        assertEquals(logs.sorted(), simplifyLogsList(logs))

        logs = listOf(Log(ExactFraction.THREE), Log(ExactFraction.THREE, 9, true))
        assertEquals(logs.sorted(), simplifyLogsList(logs))

        logs = listOf(
            Log(ExactFraction(5, 51)),
            Log(ExactFraction.FOUR, 3, true),
            Log(ExactFraction(7, 3), 10, true),
            Log(ExactFraction(5, 51), 5, true),
            Log(ExactFraction(1000005), 3)
        )
        assertEquals(logs.sorted(), simplifyLogsList(logs))
    }
}
