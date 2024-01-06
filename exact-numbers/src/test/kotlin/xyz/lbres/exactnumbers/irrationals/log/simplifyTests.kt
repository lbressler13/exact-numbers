package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.emptyMultiSet
import xyz.lbres.kotlinutils.set.multiset.filterToSet
import xyz.lbres.kotlinutils.set.multiset.filterNotToSet
import xyz.lbres.kotlinutils.set.multiset.mapToSet
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import kotlin.test.assertEquals

private val one = Log.ONE
private val fractionOne = ExactFraction.ONE

internal fun runGetSimplifiedTests() {
    // zero
    var logNum = Log.ZERO
    var expected = Pair(ExactFraction.ONE, Log.ZERO)
    assertEquals(expected, logNum.getSimplified())

    // one
    logNum = one
    expected = Pair(fractionOne, one)
    assertEquals(expected, logNum.getSimplified())

    // rational
    logNum = Log(1000)
    expected = Pair(ExactFraction.THREE, one)
    assertEquals(expected, logNum.getSimplified())

    logNum = Log(ExactFraction(1, 1000))
    expected = Pair(-ExactFraction.THREE, one)
    assertEquals(expected, logNum.getSimplified())

    logNum = Log(ExactFraction(32), 2)
    expected = Pair(ExactFraction.FIVE, one)
    assertEquals(expected, logNum.getSimplified())

    logNum = Log(ExactFraction(32), 2).inverse()
    expected = Pair(ExactFraction(1, 5), one)
    assertEquals(expected, logNum.getSimplified())

    logNum = Log(ExactFraction(1, 81), 3)
    expected = Pair(-ExactFraction.FOUR, one)
    assertEquals(expected, logNum.getSimplified())

    // irrational
    logNum = Log(30)
    expected = Pair(fractionOne, Log(30))
    assertEquals(expected, logNum.getSimplified())

    logNum = Log(ExactFraction(100, 9))
    expected = Pair(fractionOne, Log(ExactFraction(100, 9)))
    assertEquals(expected, logNum.getSimplified())

    logNum = Log(100, 2)
    expected = Pair(fractionOne, Log(100, 2))
    assertEquals(expected, logNum.getSimplified())

    logNum = Log(ExactFraction(2, 15), 7)
    expected = Pair(fractionOne, Log(ExactFraction(2, 15), 7))
    assertEquals(expected, logNum.getSimplified())
}

internal fun runSimplifySetTests() {
    // empty
    var expected: Pair<ExactFraction, MultiSet<Log>> = Pair(fractionOne, emptyMultiSet())

    var logs: MultiSet<Log> = multiSetOf()
    assertEquals(expected, Log.simplifySet(logs))

    // zero
    expected = Pair(ExactFraction.ZERO, multiSetOf())

    logs = multiSetOf(Log.ZERO)
    assertEquals(expected, Log.simplifySet(logs))

    logs = multiSetOf(Log.ZERO, Log.ZERO)
    assertEquals(expected, Log.simplifySet(logs))

    logs = multiSetOf(Log.ONE, Log.ZERO, Log(ExactFraction(18, 91)))
    assertEquals(expected, Log.simplifySet(logs))

    // ones
    logs = multiSetOf(one)
    expected = Pair(fractionOne, multiSetOf())
    assertEquals(expected, Log.simplifySet(logs))

    logs = multiSetOf(one, one, one)
    expected = Pair(fractionOne, multiSetOf())
    assertEquals(expected, Log.simplifySet(logs))

    logs = multiSetOf(one, Log(8), Log(4, 3).inverse())
    expected = Pair(fractionOne, multiSetOf(Log(8), Log(4, 3).inverse()))
    assertEquals(expected, Log.simplifySet(logs))

    logs = multiSetOf(one, Log(8), one, one, one, Log(4, 3).inverse())
    expected = Pair(fractionOne, multiSetOf(Log(8), Log(4, 3).inverse()))
    assertEquals(expected, Log.simplifySet(logs))

    // inverses
    logs = multiSetOf(Log(8), Log(8, 10).inverse())
    println(logs)
    val invertedDistinct: Set<Log> = logs.filterToSet { it.isInverted }.mapToSet { it.inverse() }.distinctValues
    val notInvertedDistinct: Set<Log> = logs.filterNotToSet { it.isInverted }.distinctValues
    println(invertedDistinct)
    println(notInvertedDistinct)
    println(invertedDistinct intersect notInvertedDistinct)

    expected = Pair(fractionOne, multiSetOf())
    assertEquals(expected, Log.simplifySet(logs))

    logs = multiSetOf(
        Log(4, 3).inverse(),
        Log(4, 3).inverse(),
        Log(4, 3)
    )
    expected = Pair(fractionOne, multiSetOf(Log(4, 3).inverse()))
    assertEquals(expected, Log.simplifySet(logs))

    logs = multiSetOf(
        Log(4, 3).inverse(),
        Log(4, 3),
        Log(4, 3)
    )
    expected = Pair(fractionOne, multiSetOf(Log(4, 3)))
    assertEquals(expected, Log.simplifySet(logs))

    logs = multiSetOf(
        Log(ExactFraction(7, 3)),
        Log(ExactFraction(5, 51)),
        Log(ExactFraction(7, 3), 10).inverse(),
        Log(4, 3).inverse(),
        Log(ExactFraction(7, 3), 10).inverse(),
        Log(ExactFraction(5, 51), 5).inverse(),
        Log(4, 3)
    )
    expected = Pair(
        fractionOne,
        multiSetOf(
            Log(ExactFraction(7, 3), 10).inverse(),
            Log(ExactFraction(5, 51)),
            Log(ExactFraction(5, 51), 5).inverse()
        )
    )
    assertEquals(expected, Log.simplifySet(logs))

    // rationals
    logs = multiSetOf(Log(1000))
    expected = Pair(ExactFraction.THREE, multiSetOf())
    assertEquals(expected, Log.simplifySet(logs))

    logs = multiSetOf(Log(9, 3), Log(ExactFraction.HALF, 2))
    expected = Pair(-ExactFraction.TWO, multiSetOf())
    assertEquals(expected, Log.simplifySet(logs))

    logs = multiSetOf(
        Log(ExactFraction(1, 1000)).inverse(),
        Log(6),
        Log(ExactFraction(15, 4), 4),
        Log(ExactFraction(1, 16), 2)
    )
    expected = Pair(ExactFraction(4, 3), multiSetOf(Log(6), Log(ExactFraction(15, 4), 4)))
    assertEquals(expected, Log.simplifySet(logs))

    // no changes
    logs = multiSetOf(Log(3))
    expected = Pair(fractionOne, logs)
    assertEquals(expected, Log.simplifySet(logs))

    logs = multiSetOf(Log(ExactFraction(3, 7)), Log(ExactFraction(7, 3)))
    expected = Pair(fractionOne, logs)
    assertEquals(expected, Log.simplifySet(logs))

    logs = multiSetOf(Log(3), Log(3, 9).inverse())
    expected = Pair(fractionOne, logs)
    assertEquals(expected, Log.simplifySet(logs))

    logs = multiSetOf(
        Log(ExactFraction(5, 51)),
        Log(4, 3).inverse(),
        Log(ExactFraction(7, 3), 10).inverse(),
        Log(ExactFraction(5, 51), 5).inverse(),
        Log(1000005, 3)
    )
    expected = Pair(fractionOne, logs)
    assertEquals(expected, Log.simplifySet(logs))
}
