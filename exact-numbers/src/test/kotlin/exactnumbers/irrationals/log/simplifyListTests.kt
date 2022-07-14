package exactnumbers.irrationals.log

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.pi.Pi
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private val one = Log.ONE

internal fun runSimplifyListTests() {
    // error
    assertFailsWith<ClassCastException> { Log.simplifyList(listOf(Pi(), Log.ONE)) }

    // empty
    var expected: List<Log> = listOf()

    assertEquals(expected.sorted(), Log.simplifyList(null))

    var logs: List<Log> = listOf()
    assertEquals(expected.sorted(), Log.simplifyList(logs))

    // zero
    expected = listOf(Log.ZERO)

    logs = listOf(Log.ZERO)
    assertEquals(expected.sorted(), Log.simplifyList(logs))

    logs = listOf(Log.ZERO, Log.ZERO)
    assertEquals(expected.sorted(), Log.simplifyList(logs))

    logs = listOf(Log.ONE, Log.ZERO, Log(ExactFraction(18, 91)))
    assertEquals(expected.sorted(), Log.simplifyList(logs))

    // ones
    logs = listOf(one)
    expected = listOf()
    assertEquals(expected.sorted(), Log.simplifyList(logs))

    logs = listOf(one, one, one)
    expected = listOf()
    assertEquals(expected.sorted(), Log.simplifyList(logs))

    logs = listOf(one, Log(ExactFraction.EIGHT), Log(ExactFraction.FOUR, 3, true))
    expected = listOf(Log(ExactFraction.EIGHT), Log(ExactFraction.FOUR, 3, true))
    assertEquals(expected.sorted(), Log.simplifyList(logs))

    logs = listOf(one, Log(ExactFraction.EIGHT), one, one, one, Log(ExactFraction.FOUR, 3, true))
    expected = listOf(Log(ExactFraction.EIGHT), Log(ExactFraction.FOUR, 3, true))
    assertEquals(expected.sorted(), Log.simplifyList(logs))

    // inverses
    logs = listOf(Log(ExactFraction.EIGHT), Log(ExactFraction.EIGHT, 10, true))
    expected = listOf()
    assertEquals(expected.sorted(), Log.simplifyList(logs))

    logs = listOf(
        Log(ExactFraction.FOUR, 3, true),
        Log(ExactFraction.FOUR, 3, true),
        Log(ExactFraction.FOUR, 3, false)
    )
    expected = listOf(Log(ExactFraction.FOUR, 3, true))
    assertEquals(expected.sorted(), Log.simplifyList(logs))

    logs = listOf(
        Log(ExactFraction.FOUR, 3, true),
        Log(ExactFraction.FOUR, 3, false),
        Log(ExactFraction.FOUR, 3, false)
    )
    expected = listOf(Log(ExactFraction.FOUR, 3, false))
    assertEquals(expected.sorted(), Log.simplifyList(logs))

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
    assertEquals(expected.sorted(), Log.simplifyList(logs))

    // no changes
    logs = listOf(Log(ExactFraction.THREE))
    assertEquals(logs.sorted(), Log.simplifyList(logs))

    logs = listOf(Log(ExactFraction(3, 7)), Log(ExactFraction(7, 3)))
    assertEquals(logs.sorted(), Log.simplifyList(logs))

    logs = listOf(Log(ExactFraction.THREE), Log(ExactFraction.THREE, 9, true))
    assertEquals(logs.sorted(), Log.simplifyList(logs))

    logs = listOf(
        Log(ExactFraction(5, 51)),
        Log(ExactFraction.FOUR, 3, true),
        Log(ExactFraction(7, 3), 10, true),
        Log(ExactFraction(5, 51), 5, true),
        Log(ExactFraction(1000005), 3)
    )
    assertEquals(logs.sorted(), Log.simplifyList(logs))
}
