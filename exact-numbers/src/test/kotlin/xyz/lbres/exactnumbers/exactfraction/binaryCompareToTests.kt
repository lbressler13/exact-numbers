package xyz.lbres.exactnumbers.exactfraction

import java.math.BigInteger
import kotlin.test.assertEquals
import kotlin.test.assertTrue

fun runCompareToTests() {
    // equal values
    var first = ExactFraction(0)
    var second = ExactFraction(0)
    assertEquals(0, first.compareTo(second))

    first = ExactFraction(100)
    second = ExactFraction(100)
    assertEquals(0, first.compareTo(second))

    // pos less than zero
    first = ExactFraction(3)
    second = ExactFraction(0)
    assertTrue(first > second)

    // neg less than zero
    first = ExactFraction(-3)
    second = ExactFraction(0)
    assertTrue(first < second)

    // neg less than pos
    first = ExactFraction(-1)
    second = ExactFraction(1)
    assertTrue(first < second)

    // neg order
    first = ExactFraction(-3)
    second = ExactFraction(-2)
    assertTrue(first < second)

    // pos order
    first = ExactFraction(3)
    second = ExactFraction(2)
    assertTrue(first > second)

    // BigInteger
    var ef = ExactFraction(0)
    var bi = BigInteger.ZERO
    assertEquals(0, ef.compareTo(bi))

    ef = ExactFraction(100)
    bi = 100.toBigInteger()
    assertEquals(0, ef.compareTo(bi))

    ef = ExactFraction(3)
    bi = BigInteger.ZERO
    assertTrue(ef > bi)

    ef = ExactFraction(-3)
    bi = BigInteger.ZERO
    assertTrue(ef < bi)

    ef = ExactFraction(-1)
    bi = BigInteger.ONE
    assertTrue(ef < bi)

    ef = ExactFraction(-3)
    bi = (-2).toBigInteger()
    assertTrue(ef < bi)

    ef = ExactFraction(3)
    bi = 2.toBigInteger()
    assertTrue(ef > bi)

    // Int
    ef = ExactFraction(0)
    var i = 0
    assertEquals(0, ef.compareTo(i))

    ef = ExactFraction(100)
    i = 100
    assertEquals(0, ef.compareTo(i))

    ef = ExactFraction(3)
    i = 0
    assertTrue(ef > i)

    ef = ExactFraction(-3)
    i = 0
    assertTrue(ef < i)

    ef = ExactFraction(-1)
    i = 1
    assertTrue(ef < i)

    ef = ExactFraction(-3)
    i = -2
    assertTrue(ef < i)

    ef = ExactFraction(3)
    i = 2
    assertTrue(ef > i)

    // Long
    ef = ExactFraction(0)
    var l = 0L
    assertEquals(0, ef.compareTo(l))

    ef = ExactFraction(100)
    l = 100L
    assertEquals(0, ef.compareTo(l))

    ef = ExactFraction(3)
    l = 0L
    assertTrue(ef > l)

    ef = ExactFraction(-3)
    l = 0L
    assertTrue(ef < l)

    ef = ExactFraction(-1)
    l = 1L
    assertTrue(ef < l)

    ef = ExactFraction(-3)
    l = -2L
    assertTrue(ef < l)

    ef = ExactFraction(3)
    l = 2L
    assertTrue(ef > l)
}
