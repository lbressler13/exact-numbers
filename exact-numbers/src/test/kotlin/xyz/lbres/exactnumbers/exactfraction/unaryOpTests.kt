package xyz.lbres.exactnumbers.exactfraction

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun runUnaryMinusTests() {
    var ef = ExactFraction(0)
    var expected = ExactFraction(0)
    assertEquals(expected, -ef)

    ef = ExactFraction(3)
    expected = ExactFraction(-3)
    assertEquals(expected, -ef)

    ef = ExactFraction(-3)
    expected = ExactFraction(3)
    assertEquals(expected, -ef)

    ef = ExactFraction(5, 2)
    expected = ExactFraction(-5, 2)
    assertEquals(expected, -ef)

    ef = ExactFraction(-2, 5)
    expected = ExactFraction(2, 5)
    assertEquals(expected, -ef)
}

fun runUnaryPlusTests() {
    var ef = ExactFraction(0)
    assertEquals(ef, +ef)

    ef = ExactFraction(3)
    assertEquals(ef, +ef)

    ef = ExactFraction(-3)
    assertEquals(ef, +ef)

    ef = ExactFraction(5, 2)
    assertEquals(ef, +ef)

    ef = ExactFraction(-2, 5)
    assertEquals(ef, +ef)
}

fun runNotTests() {
    assertTrue(!ExactFraction(0))
    assertTrue(!ExactFraction(0, -3))
    assertFalse(!ExactFraction(1))
    assertFalse(!ExactFraction(-1))
    assertFalse(!ExactFraction(1, 3))
}

fun runIncTests() {
    var ef = ExactFraction(3)
    ef++
    var expected = ExactFraction(4)
    assertEquals(expected, ef)

    ef = ExactFraction(-3)
    ef++
    expected = ExactFraction(-2)
    assertEquals(expected, ef)

    ef = ExactFraction(0)
    ef++
    expected = ExactFraction(1)
    assertEquals(expected, ef)

    ef = ExactFraction(-1)
    ef++
    expected = ExactFraction(0)
    assertEquals(expected, ef)

    ef = ExactFraction(6, 7)
    ef++
    expected = ExactFraction(13, 7)
    assertEquals(expected, ef)

    ef = ExactFraction(-7, 9)
    ef++
    expected = ExactFraction(2, 9)
    assertEquals(expected, ef)
}

fun runDecTests() {
    var ef = ExactFraction(3)
    ef--
    var expected = ExactFraction(2)
    assertEquals(expected, ef)

    ef = ExactFraction(-3)
    ef--
    expected = ExactFraction(-4)
    assertEquals(expected, ef)

    ef = ExactFraction(0)
    ef--
    expected = ExactFraction(-1)
    assertEquals(expected, ef)

    ef = ExactFraction(1)
    ef--
    expected = ExactFraction(0)
    assertEquals(expected, ef)

    ef = ExactFraction(6, 7)
    ef--
    expected = ExactFraction(-1, 7)
    assertEquals(expected, ef)

    ef = ExactFraction(15, 9)
    ef--
    expected = ExactFraction(6, 9)
    assertEquals(expected, ef)
}
