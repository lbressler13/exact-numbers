package xyz.lbres.exactnumbers.irrationals.sqrt

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.emptyMultiSet
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import kotlin.test.assertEquals

internal fun runGetSimplifiedTests() {
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

internal fun runSimplifyListTests() {
    val one = ExactFraction.ONE

    // empty
    var expected = Pair(one, listOf<Sqrt>())

    assertEquals(expected, Sqrt.simplifyList(null))

    var numbers: List<Sqrt> = listOf()
    assertEquals(expected, Sqrt.simplifyList(numbers))

    // zero
    expected = Pair(ExactFraction.ZERO, listOf())
    numbers = listOf(Sqrt.ZERO)
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(Sqrt.ZERO, Sqrt(2))
    assertEquals(expected, Sqrt.simplifyList(numbers))

    // only whole
    numbers = listOf(Sqrt.ONE)
    expected = Pair(one, listOf())
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(Sqrt(9))
    expected = Pair(ExactFraction.THREE, listOf())
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(
        Sqrt(ExactFraction(25, 16)),
        Sqrt(ExactFraction(1, 64)),
        Sqrt(49)
    )
    expected = Pair(ExactFraction(35, 32), listOf())
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(Sqrt(15), Sqrt(6), Sqrt(10))
    expected = Pair(ExactFraction(30), listOf())
    assertEquals(expected, Sqrt.simplifyList(numbers))

    // mixed
    numbers = listOf(Sqrt(2), Sqrt(6))
    expected = Pair(ExactFraction.TWO, listOf(Sqrt(3)))
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(Sqrt(8))
    expected = Pair(ExactFraction.TWO, listOf(Sqrt(2)))
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(Sqrt(ExactFraction(2, 15)), Sqrt(16))
    expected = Pair(ExactFraction.FOUR, listOf(Sqrt(ExactFraction(2, 15))))
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(
        Sqrt(ExactFraction(99, 8)),
        Sqrt(8),
        Sqrt(ExactFraction(121, 500))
    )
    expected = Pair(
        ExactFraction(33, 10),
        listOf(Sqrt(ExactFraction(11, 5)))
    )
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(
        Sqrt(ExactFraction(15, 2)),
        Sqrt(ExactFraction(2, 27)),
        Sqrt(ExactFraction(1, 17)),
        Sqrt(4)
    )
    expected = Pair(
        ExactFraction(2, 3),
        listOf(Sqrt(ExactFraction(5, 17)))
    )
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(
        Sqrt(ExactFraction(15, 2)),
        Sqrt(ExactFraction(2, 27)),
        Sqrt(15),
        Sqrt(4)
    )
    expected = Pair(ExactFraction.TEN, listOf(Sqrt(ExactFraction(1, 3))))
    assertEquals(expected, Sqrt.simplifyList(numbers))

    // no wholes
    numbers = listOf(Sqrt(2), Sqrt(ExactFraction(17, 33)))
    expected = Pair(one, listOf(Sqrt(ExactFraction(34, 33))))
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(Sqrt(15), Sqrt(ExactFraction(1511, 119)), Sqrt(ExactFraction(1, 13)))
    expected = Pair(
        one,
        listOf(Sqrt(ExactFraction(22665, 1547)))
    )
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(Sqrt(13), Sqrt(ExactFraction(1, 13)))
    expected = Pair(one, listOf())
    assertEquals(expected, Sqrt.simplifyList(numbers))

    // ones
    val rootOne = Sqrt.ONE

    numbers = listOf(rootOne)
    expected = Pair(one, listOf())
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(rootOne, rootOne, rootOne)
    expected = Pair(one, listOf())
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(Sqrt(ExactFraction(2, 15)), rootOne, Sqrt(16))
    expected = Pair(ExactFraction.FOUR, listOf(Sqrt(ExactFraction(2, 15))))
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(
        rootOne,
        Sqrt(ExactFraction(25, 16)),
        rootOne,
        rootOne,
        Sqrt(ExactFraction(1, 64)),
        Sqrt(49)
    )
    expected = Pair(ExactFraction(35, 32), listOf())
    assertEquals(expected, Sqrt.simplifyList(numbers))
}

internal fun runSimplifySetTests() {
    val one = ExactFraction.ONE

    // empty
    var expected = Pair(one, multiSetOf<Sqrt>())
    var numbers: MultiSet<Sqrt> = emptyMultiSet()
    assertEquals(expected, Sqrt.simplifySet(numbers))

    // zero
    expected = Pair(ExactFraction.ZERO, multiSetOf())
    numbers = multiSetOf(Sqrt.ZERO)
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = multiSetOf(Sqrt.ZERO, Sqrt(2))
    assertEquals(expected, Sqrt.simplifySet(numbers))

    // only whole
    numbers = multiSetOf(Sqrt.ONE)
    expected = Pair(one, multiSetOf())
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = multiSetOf(Sqrt(9))
    expected = Pair(ExactFraction.THREE, multiSetOf())
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = multiSetOf(
        Sqrt(ExactFraction(25, 16)),
        Sqrt(ExactFraction(1, 64)),
        Sqrt(49)
    )
    expected = Pair(ExactFraction(35, 32), multiSetOf())
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = multiSetOf(Sqrt(15), Sqrt(6), Sqrt(10))
    expected = Pair(ExactFraction(30), multiSetOf())
    assertEquals(expected, Sqrt.simplifySet(numbers))

    // mixed
    numbers = multiSetOf(Sqrt(2), Sqrt(6))
    expected = Pair(ExactFraction.TWO, multiSetOf(Sqrt(3)))
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = multiSetOf(Sqrt(8))
    expected = Pair(ExactFraction.TWO, multiSetOf(Sqrt(2)))
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = multiSetOf(Sqrt(ExactFraction(2, 15)), Sqrt(16))
    expected = Pair(ExactFraction.FOUR, multiSetOf(Sqrt(ExactFraction(2, 15))))
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = multiSetOf(
        Sqrt(ExactFraction(99, 8)),
        Sqrt(8),
        Sqrt(ExactFraction(121, 500))
    )
    expected = Pair(
        ExactFraction(33, 10),
        multiSetOf(Sqrt(ExactFraction(11, 5)))
    )
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = multiSetOf(
        Sqrt(ExactFraction(15, 2)),
        Sqrt(ExactFraction(2, 27)),
        Sqrt(ExactFraction(1, 17)),
        Sqrt(4)
    )
    expected = Pair(
        ExactFraction(2, 3),
        multiSetOf(Sqrt(ExactFraction(5, 17)))
    )
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = multiSetOf(
        Sqrt(ExactFraction(15, 2)),
        Sqrt(ExactFraction(2, 27)),
        Sqrt(15),
        Sqrt(4)
    )
    expected = Pair(ExactFraction.TEN, multiSetOf(Sqrt(ExactFraction(1, 3))))
    assertEquals(expected, Sqrt.simplifySet(numbers))

    // no wholes
    numbers = multiSetOf(Sqrt(2), Sqrt(ExactFraction(17, 33)))
    expected = Pair(one, multiSetOf(Sqrt(ExactFraction(34, 33))))
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = multiSetOf(Sqrt(15), Sqrt(ExactFraction(1511, 119)), Sqrt(ExactFraction(1, 13)))
    expected = Pair(
        one,
        multiSetOf(Sqrt(ExactFraction(22665, 1547)))
    )
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = multiSetOf(Sqrt(13), Sqrt(ExactFraction(1, 13)))
    expected = Pair(one, multiSetOf())
    assertEquals(expected, Sqrt.simplifySet(numbers))

    // ones
    val rootOne = Sqrt.ONE

    numbers = multiSetOf(rootOne)
    expected = Pair(one, multiSetOf())
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = multiSetOf(rootOne, rootOne, rootOne)
    expected = Pair(one, multiSetOf())
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = multiSetOf(Sqrt(ExactFraction(2, 15)), rootOne, Sqrt(16))
    expected = Pair(ExactFraction.FOUR, multiSetOf(Sqrt(ExactFraction(2, 15))))
    assertEquals(expected, Sqrt.simplifySet(numbers))

    numbers = multiSetOf(
        rootOne,
        Sqrt(ExactFraction(25, 16)),
        rootOne,
        rootOne,
        Sqrt(ExactFraction(1, 64)),
        Sqrt(49)
    )
    expected = Pair(ExactFraction(35, 32), multiSetOf())
    assertEquals(expected, Sqrt.simplifySet(numbers))
}
