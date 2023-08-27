package xyz.lbres.exactnumbers.exactfraction

import java.math.BigInteger
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

 fun runEqualsTests() {
    assertEquals(ExactFraction(0), ExactFraction(0))
    assertEquals(ExactFraction(-1, 3), ExactFraction(-1, 3))
    assertEquals(ExactFraction(5, 2), ExactFraction(5, 2))
    assertEquals(ExactFraction(10), ExactFraction(-30, -3))
    assertEquals(ExactFraction(5, 17), ExactFraction(10, 34))

    assertNotEquals(ExactFraction(1, 3), ExactFraction(-1, 3))
    assertNotEquals(ExactFraction(2, 3), ExactFraction(3, 2))
}

 fun runEqTests() {
    var ef = ExactFraction(0)
    assertTrue(ef.eq(0))
    assertTrue(ef.eq(0L))
    assertTrue(ef.eq(BigInteger.ZERO))

    ef = ExactFraction(-3)
    assertTrue(ef.eq(-3))
    assertTrue(ef.eq(-3L))
    assertTrue(ef.eq((-3).toBigInteger()))

    ef = ExactFraction(10)
    assertFalse(ef.eq(-10))
    assertFalse(ef.eq(-10L))
    assertFalse(ef.eq((-10).toBigInteger()))

    ef = ExactFraction(10, 7)
    assertFalse(ef.eq(1))
    assertFalse(ef.eq(1L))
    assertFalse(ef.eq(BigInteger.ONE))

    ef = ExactFraction(-70)
    assertFalse(ef.eq(0))
    assertFalse(ef.eq(0L))
    assertFalse(ef.eq(BigInteger.ZERO))
}
