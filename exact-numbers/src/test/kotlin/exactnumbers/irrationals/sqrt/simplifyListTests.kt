package exactnumbers.irrationals.sqrt

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.common.Irrational
import exactnumbers.irrationals.pi.Pi
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal fun runSimplifyListTests() {
    val one = ExactFraction.ONE

    // error
    assertFailsWith<ClassCastException> { Sqrt.simplifyList(listOf(Pi())) }

    // empty
    var expected = Pair(one, listOf<Sqrt>())

    var numbers: List<Irrational> = listOf()
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(Sqrt.ZERO, Sqrt(ExactFraction.TWO))
    assertEquals(expected, Sqrt.simplifyList(numbers))

    // only whole
    numbers = listOf(Sqrt.ONE)
    expected = Pair(one, listOf())
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(Sqrt(ExactFraction.NINE))
    expected = Pair(ExactFraction.THREE, listOf())
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(
        Sqrt(ExactFraction(25, 16)),
        Sqrt(ExactFraction(1, 64)),
        Sqrt(ExactFraction(49))
    )
    expected = Pair(ExactFraction(35, 32), listOf())
    assertEquals(expected, Sqrt.simplifyList(numbers))

    // mixed
    numbers = listOf(Sqrt(ExactFraction.EIGHT))
    expected = Pair(ExactFraction.TWO, listOf(Sqrt(ExactFraction.TWO)))
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(Sqrt(ExactFraction(2, 15)), Sqrt(ExactFraction(16)))
    expected = Pair(ExactFraction.FOUR, listOf(Sqrt(ExactFraction(2)), Sqrt(ExactFraction(1, 15))))
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(
        Sqrt(ExactFraction(99, 8)),
        Sqrt(ExactFraction.EIGHT),
        Sqrt(ExactFraction(121, 500))
    )
    expected = Pair(
        ExactFraction(33, 10),
        listOf(
            Sqrt(ExactFraction(11)),
            Sqrt(ExactFraction(1, 5))
        )
    )
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(
        Sqrt(ExactFraction(15, 2)),
        Sqrt(ExactFraction(2, 27)),
        Sqrt(ExactFraction(1, 17)),
        Sqrt(ExactFraction.FOUR)
    )
    expected = Pair(
        ExactFraction(2, 3),
        listOf(Sqrt(ExactFraction(15)), Sqrt(ExactFraction(1, 3)), Sqrt(ExactFraction(1, 17)))
    )
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(
        Sqrt(ExactFraction(15, 2)),
        Sqrt(ExactFraction(2, 27)),
        Sqrt(ExactFraction(15)),
        Sqrt(ExactFraction.FOUR)
    )
    expected = Pair(ExactFraction.TEN, listOf(Sqrt(ExactFraction(1, 3))))
    assertEquals(expected, Sqrt.simplifyList(numbers))

    // no wholes
    numbers = listOf(Sqrt(ExactFraction.TWO), Sqrt(ExactFraction(17, 33)))
    expected = Pair(one, listOf(Sqrt(ExactFraction.TWO), Sqrt(ExactFraction(17)), Sqrt(ExactFraction(1, 33))))
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(Sqrt(ExactFraction(15)), Sqrt(ExactFraction(1511, 119)), Sqrt(ExactFraction(1, 13)))
    expected = Pair(
        one,
        listOf(
            Sqrt(ExactFraction(15)),
            Sqrt(ExactFraction(1511)),
            Sqrt(ExactFraction(1, 119)),
            Sqrt(ExactFraction(1, 13))
        )
    )
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(Sqrt(ExactFraction(13)), Sqrt(ExactFraction(1, 13)))
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

    numbers = listOf(Sqrt(ExactFraction(2, 15)), rootOne, Sqrt(ExactFraction(16)))
    expected = Pair(ExactFraction.FOUR, listOf(Sqrt(ExactFraction.TWO), Sqrt(ExactFraction(1, 15))))
    assertEquals(expected, Sqrt.simplifyList(numbers))

    numbers = listOf(
        rootOne,
        Sqrt(ExactFraction(25, 16)),
        rootOne,
        rootOne,
        Sqrt(ExactFraction(1, 64)),
        Sqrt(ExactFraction(49))
    )
    expected = Pair(ExactFraction(35, 32), listOf())
    assertEquals(expected, Sqrt.simplifyList(numbers))
}
