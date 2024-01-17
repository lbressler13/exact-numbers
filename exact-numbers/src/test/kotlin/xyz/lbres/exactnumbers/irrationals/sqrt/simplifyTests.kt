package xyz.lbres.exactnumbers.irrationals.sqrt

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.constMultiSetOf
import xyz.lbres.kotlinutils.set.multiset.const.emptyConstMultiSet
import kotlin.test.assertEquals

fun runGetSimplifiedTests() {
    val one = ExactFraction.ONE

    // rational
    var sqrt = Sqrt.ZERO
    var expected = Pair(one, Sqrt.ZERO)
    assertEquals(expected, sqrt.getSimplified())

    sqrt = Sqrt.ONE
    expected = Pair(one, Sqrt.ONE)
    assertEquals(expected, sqrt.getSimplified())

    sqrt = Sqrt(1024)
    expected = Pair(ExactFraction(32), Sqrt.ONE)
    assertEquals(expected, sqrt.getSimplified())

    sqrt = Sqrt(ExactFraction(9, 25))
    expected = Pair(ExactFraction(3, 5), Sqrt.ONE)
    assertEquals(expected, sqrt.getSimplified())

    // rational w/ whole
    sqrt = Sqrt(50)
    expected = Pair(ExactFraction.FIVE, Sqrt(ExactFraction.TWO))
    assertEquals(expected, sqrt.getSimplified())

    sqrt = Sqrt(3000)
    expected = Pair(ExactFraction.TEN, Sqrt(ExactFraction(30)))
    assertEquals(expected, sqrt.getSimplified())

    sqrt = Sqrt(ExactFraction(50, 27))
    expected = Pair(ExactFraction(5, 3), Sqrt(ExactFraction(2, 3)))
    assertEquals(expected, sqrt.getSimplified())

    // no whole
    sqrt = Sqrt(15)
    expected = Pair(one, sqrt)
    assertEquals(expected, sqrt.getSimplified())

    sqrt = Sqrt(107)
    expected = Pair(one, sqrt)
    assertEquals(expected, sqrt.getSimplified())

    sqrt = Sqrt(ExactFraction(94, 46))
    expected = Pair(one, sqrt)
    assertEquals(expected, sqrt.getSimplified())
}

fun runSimplifySetTests() {
    val one = ExactFraction.ONE

    // empty
    var numbers: ConstMultiSet<Sqrt> = emptyConstMultiSet()
    var expected = Pair(one, constMultiSetOf<Sqrt>())
    assertEquals(expected, Sqrt.simplifySet(numbers))

    // zero
    numbers = constMultiSetOf(Sqrt.ZERO)
    expected = Pair(ExactFraction.ZERO, constMultiSetOf())
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = constMultiSetOf(Sqrt.ZERO, Sqrt(2))
    expected = Pair(ExactFraction.ZERO, constMultiSetOf())
    assertEquals(expected, Sqrt.simplifySet(numbers))

    // only whole
    numbers = constMultiSetOf(Sqrt.ONE)
    expected = Pair(one, constMultiSetOf())
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = constMultiSetOf(Sqrt(9))
    expected = Pair(ExactFraction.THREE, constMultiSetOf())
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = constMultiSetOf(
        Sqrt(ExactFraction(25, 16)),
        Sqrt(ExactFraction(1, 64)),
        Sqrt(49)
    )
    expected = Pair(ExactFraction(35, 32), constMultiSetOf())
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = constMultiSetOf(Sqrt(15), Sqrt(6), Sqrt(10))
    expected = Pair(ExactFraction(30), constMultiSetOf())
    assertEquals(expected, Sqrt.simplifySet(numbers))

    // partial whole
    numbers = constMultiSetOf(Sqrt(2), Sqrt(6))
    expected = Pair(ExactFraction.TWO, constMultiSetOf(Sqrt(3)))
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = constMultiSetOf(Sqrt(8))
    expected = Pair(ExactFraction.TWO, constMultiSetOf(Sqrt(2)))
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = constMultiSetOf(Sqrt(ExactFraction(2, 15)), Sqrt(16))
    expected = Pair(ExactFraction.FOUR, constMultiSetOf(Sqrt(ExactFraction(2, 15))))
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = constMultiSetOf(
        Sqrt(ExactFraction(99, 8)),
        Sqrt(8),
        Sqrt(ExactFraction(121, 500))
    )
    expected = Pair(
        ExactFraction(33, 10),
        constMultiSetOf(Sqrt(ExactFraction(11, 5)))
    )
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = constMultiSetOf(
        Sqrt(ExactFraction(15, 2)),
        Sqrt(ExactFraction(2, 27)),
        Sqrt(ExactFraction(1, 17)),
        Sqrt(4)
    )
    expected = Pair(
        ExactFraction(2, 3),
        constMultiSetOf(Sqrt(ExactFraction(5, 17)))
    )
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = constMultiSetOf(
        Sqrt(ExactFraction(15, 2)),
        Sqrt(ExactFraction(2, 27)),
        Sqrt(15),
        Sqrt(4)
    )
    expected = Pair(ExactFraction.TEN, constMultiSetOf(Sqrt(ExactFraction(1, 3))))
    assertEquals(expected, Sqrt.simplifySet(numbers))

    // no wholes
    numbers = constMultiSetOf(Sqrt(2), Sqrt(ExactFraction(17, 33)))
    expected = Pair(one, constMultiSetOf(Sqrt(ExactFraction(34, 33))))
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = constMultiSetOf(Sqrt(15), Sqrt(ExactFraction(1511, 119)), Sqrt(ExactFraction(1, 13)))
    expected = Pair(
        one,
        constMultiSetOf(Sqrt(ExactFraction(22665, 1547)))
    )
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = constMultiSetOf(Sqrt(13), Sqrt(ExactFraction(1, 13)))
    expected = Pair(one, constMultiSetOf())
    assertEquals(expected, Sqrt.simplifySet(numbers))

    // ones
    val rootOne = Sqrt.ONE

    numbers = constMultiSetOf(rootOne)
    expected = Pair(one, constMultiSetOf())
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = constMultiSetOf(rootOne, rootOne, rootOne)
    expected = Pair(one, constMultiSetOf())
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = constMultiSetOf(Sqrt(ExactFraction(2, 15)), rootOne, Sqrt(16))
    expected = Pair(ExactFraction.FOUR, constMultiSetOf(Sqrt(ExactFraction(2, 15))))
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = constMultiSetOf(
        rootOne,
        Sqrt(ExactFraction(25, 16)),
        rootOne,
        rootOne,
        Sqrt(ExactFraction(1, 64)),
        Sqrt(49)
    )
    expected = Pair(ExactFraction(35, 32), constMultiSetOf())
    assertEquals(expected, Sqrt.simplifySet(numbers))
}
