package exactnumbers.irrationals.log

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.pi.Pi
import kotlin.math.exp
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private val one = Log.ONE
private val fractionOne = ExactFraction.ONE

internal fun runSimplifyListTests() {
    // error
    assertFailsWith<ClassCastException> { Log.simplifyList(listOf(Pi(), Log.ONE)) }

    // empty
    var expected: Pair<ExactFraction, List<Log>> = Pair(fractionOne, listOf())

    assertEquals(expected, Log.simplifyList(null))

    var logs: List<Log> = listOf()
    assertEquals(expected, Log.simplifyList(logs))

    // zero
    expected = Pair(ExactFraction.ZERO, listOf())

    logs = listOf(Log.ZERO)
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(Log.ZERO, Log.ZERO)
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(Log.ONE, Log.ZERO, Log(ExactFraction(18, 91)))
    assertEquals(expected, Log.simplifyList(logs))

    // ones
    logs = listOf(one)
    expected = Pair(fractionOne, listOf())
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(one, one, one)
    expected = Pair(fractionOne, listOf())
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(one, Log(ExactFraction.EIGHT), Log(ExactFraction.FOUR, 3, true))
    expected = Pair(fractionOne, listOf(Log(ExactFraction.EIGHT), Log(ExactFraction.FOUR, 3, true)).sorted())
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(one, Log(ExactFraction.EIGHT), one, one, one, Log(ExactFraction.FOUR, 3, true))
    expected = Pair(fractionOne, listOf(Log(ExactFraction.EIGHT), Log(ExactFraction.FOUR, 3, true)).sorted())
    assertEquals(expected, Log.simplifyList(logs))

    // inverses
    logs = listOf(Log(ExactFraction.EIGHT), Log(ExactFraction.EIGHT, 10, true))
    expected = Pair(fractionOne, listOf())
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(
        Log(ExactFraction.FOUR, 3, true),
        Log(ExactFraction.FOUR, 3, true),
        Log(ExactFraction.FOUR, 3, false)
    )
    expected = Pair(fractionOne, listOf(Log(ExactFraction.FOUR, 3, true)))
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(
        Log(ExactFraction.FOUR, 3, true),
        Log(ExactFraction.FOUR, 3, false),
        Log(ExactFraction.FOUR, 3, false)
    )
    expected = Pair(fractionOne, listOf(Log(ExactFraction.FOUR, 3, false)))
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(
        Log(ExactFraction(7, 3)),
        Log(ExactFraction(5, 51)),
        Log(ExactFraction(7, 3), 10, true),
        Log(ExactFraction.FOUR, 3, true),
        Log(ExactFraction(7, 3), 10, true),
        Log(ExactFraction(5, 51), 5, true),
        Log(ExactFraction.FOUR, 3)
    )
    expected = Pair(
        fractionOne, listOf(
            Log(ExactFraction(7, 3), 10, true),
            Log(ExactFraction(5, 51)),
            Log(ExactFraction(5, 51), 5, true)
        ).sorted()
    )
    assertEquals(expected, Log.simplifyList(logs))

    // rationals
    logs = listOf(Log(ExactFraction(100)))
    expected = Pair(ExactFraction.TWO, listOf())
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(Log(ExactFraction.NINE, 3), Log(ExactFraction.HALF, 2))
    expected = Pair(-ExactFraction.TWO, listOf())
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(
        Log(ExactFraction(1, 1000), true),
        Log(ExactFraction.SIX),
        Log(ExactFraction(15, 4), 4),
        Log(ExactFraction(1, 16), 2)
    )
    expected = Pair(ExactFraction(4, 3), listOf(Log(ExactFraction.SIX), Log(ExactFraction(15, 4), 4)))
    assertEquals(expected, Log.simplifyList(logs))

    // no changes
    logs = listOf(Log(ExactFraction.THREE))
    expected = Pair(fractionOne, logs)
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(Log(ExactFraction(3, 7)), Log(ExactFraction(7, 3))).sorted()
    expected = Pair(fractionOne, logs)
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(Log(ExactFraction.THREE), Log(ExactFraction.THREE, 9, true)).sorted()
    expected = Pair(fractionOne, logs)
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(
        Log(ExactFraction(5, 51)),
        Log(ExactFraction.FOUR, 3, true),
        Log(ExactFraction(7, 3), 10, true),
        Log(ExactFraction(5, 51), 5, true),
        Log(ExactFraction(1000005), 3)
    ).sorted()
    expected = Pair(fractionOne, logs)
    assertEquals(expected, Log.simplifyList(logs))
}
