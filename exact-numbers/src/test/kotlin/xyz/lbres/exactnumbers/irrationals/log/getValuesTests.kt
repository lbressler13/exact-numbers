package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertNull

fun runGetValueTests() {
    // base 10
    var log = Log(ExactFraction.ONE)
    var expected = BigDecimal.ZERO
    assertEquals(expected, log.getValue())

    log = Log(100)
    expected = BigDecimal("2")
    assertEquals(expected, log.getValue())

    log = Log(3333)
    expected = BigDecimal("3.52283531366053")
    assertEquals(expected, log.getValue())

    log = Log(ExactFraction.HALF)
    expected = BigDecimal("-0.30102999566398114")
    assertEquals(expected, log.getValue())
    assertEquals(expected, log.getValue())

    log = Log(ExactFraction(21, 2))
    expected = BigDecimal("1.02118929906993786")
    assertEquals(expected, log.getValue())

    // base 2
    log = Log(ExactFraction.ONE, 2)
    expected = BigDecimal.ZERO
    assertEquals(expected, log.getValue())

    log = Log(ExactFraction(1, 8), 2)
    expected = BigDecimal("-3")
    assertEquals(expected, log.getValue())
    assertEquals(expected, log.getValue())

    log = Log(200, 2)
    expected = BigDecimal("7.643856189774724")
    assertEquals(expected, log.getValue())
    assertEquals(expected, log.getValue())

    // other
    log = Log(216, 6)
    expected = BigDecimal("3")
    assertEquals(expected, log.getValue())

    log = Log(15151515, 24)
    expected = BigDecimal("5.202432673429519")
    assertEquals(expected, log.getValue())

    log = Log(ExactFraction(18, 109), 9)
    expected = BigDecimal("-0.8196595572931246")
    assertEquals(expected, log.getValue())

    log = Log(ExactFraction(2000, 3), 3)
    expected = BigDecimal("5.9186395764396105")
    assertEquals(expected, log.getValue())

    // divided
    log = Log(10, 10).swapDivided()
    expected = BigDecimal.ONE
    assertEquals(expected, log.getValue())

    log = Log(8, 2).swapDivided()
    expected = BigDecimal("0.33333333333333333333")
    assertEquals(expected, log.getValue())

    log = Log(ExactFraction(1, 4), 10).swapDivided()
    expected = BigDecimal("-1.6609640474436814234")
    assertEquals(expected, log.getValue())
    assertEquals(expected, log.getValue())

    log = Log(12, 4).swapDivided()
    expected = BigDecimal("0.55788589130225962125")
    assertEquals(expected, log.getValue())
}

fun runGetRationalValueTests() {
    // irrational
    var log = Log(6)
    assertNull(log.getRationalValue())

    log = Log(1000, 5)
    assertNull(log.getRationalValue())
    assertNull(log.getRationalValue())

    log = Log(ExactFraction(5, 12), 5)
    assertNull(log.getRationalValue())

    log = Log(ExactFraction(5, 12), 5).swapDivided()
    assertNull(log.getRationalValue())

    // rational
    log = Log.ZERO
    var expected = ExactFraction.ZERO
    assertEquals(expected, log.getRationalValue())

    log = Log(32, 2)
    expected = ExactFraction.FIVE
    assertEquals(expected, log.getRationalValue())

    log = Log(ExactFraction(1, 27), 3)
    expected = -ExactFraction.THREE
    assertEquals(expected, log.getRationalValue())
    assertEquals(expected, log.getRationalValue())

    log = Log(ExactFraction(1, 1000))
    expected = -ExactFraction.THREE
    assertEquals(expected, log.getRationalValue())

    log = Log(ExactFraction(1, 1000)).swapDivided()
    expected = ExactFraction(-1, 3)
    assertEquals(expected, log.getRationalValue())
    assertEquals(expected, log.getRationalValue())
}
