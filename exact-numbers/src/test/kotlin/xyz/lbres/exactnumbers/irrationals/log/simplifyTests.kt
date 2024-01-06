package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.constMultiSetOf
import xyz.lbres.kotlinutils.set.multiset.const.emptyConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.filterNotToSet
import xyz.lbres.kotlinutils.set.multiset.filterToSet
import xyz.lbres.kotlinutils.set.multiset.mapToSet
import kotlin.test.assertEquals

private val one = Log.ONE
private val fractionOne = ExactFraction.ONE

fun runGetSimplifiedTests() {
    // zero
    var logNum = Log.ZERO
    var expected = Pair(fractionOne, Log.ZERO)
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

fun runSimplifySetTests() {
    // empty
    var expected: Pair<ExactFraction, ConstMultiSet<Log>> = Pair(fractionOne, emptyConstMultiSet())

    var logs: ConstMultiSet<Log> = constMultiSetOf()
    assertEquals(expected, Log.simplifySet(logs))

    // zero
    expected = Pair(ExactFraction.ZERO, constMultiSetOf())

    logs = constMultiSetOf(Log.ZERO)
    assertEquals(expected, Log.simplifySet(logs))

    logs = constMultiSetOf(Log.ZERO, Log.ZERO)
    assertEquals(expected, Log.simplifySet(logs))

    logs = constMultiSetOf(Log.ONE, Log.ZERO, Log(ExactFraction(18, 91)))
    assertEquals(expected, Log.simplifySet(logs))

    // ones
    logs = constMultiSetOf(one)
    expected = Pair(fractionOne, constMultiSetOf())
    assertEquals(expected, Log.simplifySet(logs))

    logs = constMultiSetOf(one, one, one)
    expected = Pair(fractionOne, constMultiSetOf())
    assertEquals(expected, Log.simplifySet(logs))

    logs = constMultiSetOf(one, Log(8), Log(4, 3).inverse())
    expected = Pair(fractionOne, constMultiSetOf(Log(8), Log(4, 3).inverse()))
    assertEquals(expected, Log.simplifySet(logs))

    logs = constMultiSetOf(one, Log(8), one, one, one, Log(4, 3).inverse())
    expected = Pair(fractionOne, constMultiSetOf(Log(8), Log(4, 3).inverse()))
    assertEquals(expected, Log.simplifySet(logs))

    // inverses
    logs = constMultiSetOf(Log(8), Log(8, 10).inverse())
    println(logs)
    val invertedDistinct: Set<Log> = logs.filterToSet { it.isInverted }.mapToSet { it.inverse() }.distinctValues
    val notInvertedDistinct: Set<Log> = logs.filterNotToSet { it.isInverted }.distinctValues
    println(invertedDistinct)
    println(notInvertedDistinct)
    println(invertedDistinct intersect notInvertedDistinct)

    expected = Pair(fractionOne, constMultiSetOf())
    assertEquals(expected, Log.simplifySet(logs))

    logs = constMultiSetOf(
        Log(4, 3).inverse(),
        Log(4, 3).inverse(),
        Log(4, 3)
    )
    expected = Pair(fractionOne, constMultiSetOf(Log(4, 3).inverse()))
    assertEquals(expected, Log.simplifySet(logs))

    logs = constMultiSetOf(
        Log(4, 3).inverse(),
        Log(4, 3),
        Log(4, 3)
    )
    expected = Pair(fractionOne, constMultiSetOf(Log(4, 3)))
    assertEquals(expected, Log.simplifySet(logs))

    logs = constMultiSetOf(
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
        constMultiSetOf(
            Log(ExactFraction(7, 3), 10).inverse(),
            Log(ExactFraction(5, 51)),
            Log(ExactFraction(5, 51), 5).inverse()
        )
    )
    assertEquals(expected, Log.simplifySet(logs))

    // rationals
    logs = constMultiSetOf(Log(1000))
    expected = Pair(ExactFraction.THREE, constMultiSetOf())
    assertEquals(expected, Log.simplifySet(logs))

    logs = constMultiSetOf(Log(9, 3), Log(ExactFraction.HALF, 2))
    expected = Pair(-ExactFraction.TWO, constMultiSetOf())
    assertEquals(expected, Log.simplifySet(logs))

    logs = constMultiSetOf(
        Log(ExactFraction(1, 1000)).inverse(),
        Log(6),
        Log(ExactFraction(15, 4), 4),
        Log(ExactFraction(1, 16), 2)
    )
    expected = Pair(ExactFraction(4, 3), constMultiSetOf(Log(6), Log(ExactFraction(15, 4), 4)))
    assertEquals(expected, Log.simplifySet(logs))

    // no changes
    logs = constMultiSetOf(Log(3))
    expected = Pair(fractionOne, logs)
    assertEquals(expected, Log.simplifySet(logs))

    logs = constMultiSetOf(Log(ExactFraction(3, 7)), Log(ExactFraction(7, 3)))
    expected = Pair(fractionOne, logs)
    assertEquals(expected, Log.simplifySet(logs))

    logs = constMultiSetOf(Log(3), Log(3, 9).inverse())
    expected = Pair(fractionOne, logs)
    assertEquals(expected, Log.simplifySet(logs))

    logs = constMultiSetOf(
        Log(ExactFraction(5, 51)),
        Log(4, 3).inverse(),
        Log(ExactFraction(7, 3), 10).inverse(),
        Log(ExactFraction(5, 51), 5).inverse(),
        Log(1000005, 3)
    )
    expected = Pair(fractionOne, logs)
    assertEquals(expected, Log.simplifySet(logs))
}
