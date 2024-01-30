package xyz.lbres.exactnumbers.exactfraction

import kotlin.test.assertEquals

fun runCommonPlusTests(add: (ExactFraction, ExactFraction) -> ExactFraction) {
    // add zero
    var first = ExactFraction(0)
    var second = ExactFraction(0)
    assertEquals(ExactFraction(0), add(first, second))

    first = ExactFraction(4)
    second = ExactFraction(0)
    assertEquals(ExactFraction(4), add(first, second))
    assertEquals(ExactFraction(4), add(second, first))

    first = ExactFraction(0)
    second = ExactFraction(-4)
    assertEquals(ExactFraction(-4), add(first, second))
    assertEquals(ExactFraction(-4), add(second, first))

    // whole numbers
    first = ExactFraction(4)
    second = ExactFraction(1)
    assertEquals(ExactFraction(5), add(first, second))

    first = ExactFraction(-12)
    second = ExactFraction(33)
    assertEquals(ExactFraction(21), add(first, second))
    assertEquals(ExactFraction(21), add(second, first))

    first = ExactFraction(-12)
    second = ExactFraction(-6)
    assertEquals(ExactFraction(-18), add(first, second))

    // same denominator
    first = ExactFraction(5, 3)
    second = ExactFraction(-2, 3)
    assertEquals(ExactFraction(1), add(first, second))

    first = ExactFraction(5, 19)
    second = ExactFraction(11, 19)
    assertEquals(ExactFraction(16, 19), add(second, first))
    assertEquals(ExactFraction(16, 19), add(first, second))

    first = ExactFraction(24, 19)
    second = ExactFraction(32, 19)
    assertEquals(ExactFraction(56, 19), add(first, second))

    // different denominator
    first = ExactFraction(5)
    second = ExactFraction(-4, 3)
    assertEquals(ExactFraction(11, 3), add(first, second))

    first = ExactFraction(1, 7)
    second = ExactFraction(1, 4)
    assertEquals(ExactFraction(11, 28), add(first, second))

    first = ExactFraction(5, 2)
    second = ExactFraction(7, 3)
    assertEquals(ExactFraction(29, 6), add(first, second))

    first = ExactFraction(5, 12)
    second = ExactFraction(3, 11)
    assertEquals(ExactFraction(91, 132), add(first, second))

    first = ExactFraction(4, 8)
    second = ExactFraction(-1, 3)
    assertEquals(ExactFraction(1, 6), add(first, second))
}

fun runCommonTimesTests(multiply: (ExactFraction, ExactFraction) -> ExactFraction) {
    // zero
    var first = ExactFraction(0)
    var second = ExactFraction(0)
    assertEquals(ExactFraction(0), multiply(first, second))

    first = ExactFraction(1)
    second = ExactFraction(0)
    assertEquals(ExactFraction(0), multiply(first, second))
    assertEquals(ExactFraction(0), multiply(second, first))

    // whole numbers
    first = ExactFraction(1)
    second = ExactFraction(8)
    assertEquals(ExactFraction(8), multiply(first, second))

    first = ExactFraction(7)
    second = ExactFraction(23)
    assertEquals(ExactFraction(161), multiply(first, second))

    first = ExactFraction(-17)
    second = ExactFraction(9)
    assertEquals(ExactFraction(-153), multiply(first, second))
    assertEquals(ExactFraction(-153), multiply(second, first))

    first = ExactFraction(-5)
    second = ExactFraction(-7)
    assertEquals(ExactFraction(35), multiply(first, second))

    // fractions
    first = ExactFraction(7, 11)
    second = ExactFraction(3, 13)
    assertEquals(ExactFraction(21, 143), multiply(first, second))

    first = ExactFraction(1, 7)
    second = ExactFraction(1, 4)
    assertEquals(ExactFraction(1, 28), multiply(first, second))

    first = ExactFraction(-1, 3)
    second = ExactFraction(-4, 12)
    assertEquals(ExactFraction(1, 9), multiply(first, second))

    first = ExactFraction(11, 3)
    second = ExactFraction(4, 3)
    assertEquals(ExactFraction(44, 9), multiply(first, second))

    first = ExactFraction(6, 4)
    second = ExactFraction(8, 3)
    assertEquals(ExactFraction(4), multiply(first, second))

    first = ExactFraction(-6, 7)
    second = ExactFraction(8, 3)
    assertEquals(ExactFraction(-16, 7), multiply(first, second))
    assertEquals(ExactFraction(-16, 7), multiply(second, first))

    first = ExactFraction(6, 7)
    second = ExactFraction(7, 6)
    assertEquals(ExactFraction(1), multiply(first, second))
}
