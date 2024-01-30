package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private val logOne = Log.ONE
private val one = ExactFraction.ONE

fun runGetSimplifiedTests() {
    // zero
    var log = Log.ZERO
    var expected = Pair(one, Log.ZERO)
    assertEquals(expected, log.getSimplified())

    // one
    log = logOne
    expected = Pair(one, logOne)
    assertEquals(expected, log.getSimplified())

    // rational
    log = Log(1000)
    expected = Pair(ExactFraction.THREE, logOne)
    assertEquals(expected, log.getSimplified())

    log = Log(ExactFraction(1, 1000))
    expected = Pair(-ExactFraction.THREE, logOne)
    assertEquals(expected, log.getSimplified())

    log = Log(ExactFraction(32), 2)
    expected = Pair(ExactFraction.FIVE, logOne)
    assertEquals(expected, log.getSimplified())

    log = Log(ExactFraction(32), 2).swapDivided()
    expected = Pair(ExactFraction(1, 5), logOne)
    assertEquals(expected, log.getSimplified())

    log = Log(ExactFraction(1, 81), 3)
    expected = Pair(-ExactFraction.FOUR, logOne)
    assertEquals(expected, log.getSimplified())

    // irrational
    log = Log(30)
    expected = Pair(one, Log(30))
    assertEquals(expected, log.getSimplified())

    log = Log(ExactFraction(100, 9))
    expected = Pair(one, Log(ExactFraction(100, 9)))
    assertEquals(expected, log.getSimplified())

    log = Log(100, 2)
    expected = Pair(one, Log(100, 2))
    assertEquals(expected, log.getSimplified())

    log = Log(ExactFraction(2, 15), 7)
    expected = Pair(one, Log(ExactFraction(2, 15), 7))
    assertEquals(expected, log.getSimplified())
}

fun runSimplifyListTests() {
    // error
    assertFailsWith<ClassCastException> { Log.simplifyList(listOf(Pi(), Log.ONE)) }

    // empty
    var expected: Pair<ExactFraction, List<Log>> = Pair(one, emptyList())

    assertEquals(expected, Log.simplifyList(null))

    var logs: List<Log> = emptyList()
    assertEquals(expected, Log.simplifyList(logs))

    // zero
    expected = Pair(ExactFraction.ZERO, emptyList())

    logs = listOf(Log.ZERO)
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(Log.ZERO, Log.ZERO)
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(Log.ONE, Log.ZERO, Log(ExactFraction(18, 91))).sorted()
    assertEquals(expected, Log.simplifyList(logs))

    // ones
    logs = listOf(logOne)
    expected = Pair(one, emptyList())
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(logOne, logOne, logOne)
    expected = Pair(one, emptyList())
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(logOne, Log(8), Log(4, 3, true)).sorted()
    expected = Pair(one, listOf(Log(8), Log(4, 3, true)).sorted())
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(logOne, Log(8), logOne, logOne, logOne, Log(4, 3, true)).sorted()
    expected = Pair(one, listOf(Log(8), Log(4, 3, true)).sorted())
    assertEquals(expected, Log.simplifyList(logs))

    // inverses
    logs = listOf(Log(8), Log(8, 10, true)).sorted()
    expected = Pair(one, emptyList())
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(
        Log(4, 3, true),
        Log(4, 3, true),
        Log(4, 3, false)
    )
    expected = Pair(one, listOf(Log(4, 3, true)))
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(
        Log(4, 3, true),
        Log(4, 3, false),
        Log(4, 3, false)
    )
    expected = Pair(one, listOf(Log(4, 3, false)))
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(
        Log(ExactFraction(7, 3)),
        Log(ExactFraction(5, 51)),
        Log(ExactFraction(7, 3), 10, true),
        Log(4, 3, true),
        Log(ExactFraction(7, 3), 10, true),
        Log(ExactFraction(5, 51), 5, true),
        Log(4, 3)
    ).sorted()
    expected = Pair(
        one,
        listOf(
            Log(ExactFraction(7, 3), 10, true),
            Log(ExactFraction(5, 51)),
            Log(ExactFraction(5, 51), 5, true)
        ).sorted()
    )
    assertEquals(expected, Log.simplifyList(logs))

    // rationals
    logs = listOf(Log(1000))
    expected = Pair(ExactFraction.THREE, emptyList())
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(Log(9, 3), Log(ExactFraction.HALF, 2))
    expected = Pair(-ExactFraction.TWO, emptyList())
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(
        Log(ExactFraction(1, 1000), true),
        Log(6),
        Log(ExactFraction(15, 4), 4),
        Log(ExactFraction(1, 16), 2)
    ).sorted()
    expected = Pair(ExactFraction(4, 3), listOf(Log(6), Log(ExactFraction(15, 4), 4)).sorted())
    assertEquals(expected, Log.simplifyList(logs))

    // no changes
    logs = listOf(Log(3))
    expected = Pair(one, logs)
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(Log(ExactFraction(3, 7)), Log(ExactFraction(7, 3))).sorted()
    expected = Pair(one, logs)
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(Log(3), Log(3, 9, true)).sorted()
    expected = Pair(one, logs)
    assertEquals(expected, Log.simplifyList(logs))

    logs = listOf(
        Log(ExactFraction(5, 51)),
        Log(4, 3, true),
        Log(ExactFraction(7, 3), 10, true),
        Log(ExactFraction(5, 51), 5, true),
        Log(1000005, 3)
    ).sorted()
    expected = Pair(one, logs)
    assertEquals(expected, Log.simplifyList(logs))
}
