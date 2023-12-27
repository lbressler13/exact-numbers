package xyz.lbres.exactnumbers.irrationals.pi

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

fun runSimplifyListTests() {
    val one = ExactFraction.ONE

    // error
    assertFailsWith<ClassCastException> { Pi.simplifyList(listOf(Pi(), Log.ONE)) }

    // equal
    var expected: Pair<ExactFraction, List<Pi>> = Pair(one, emptyList())

    assertEquals(expected, Pi.simplifyList(null))

    var pis: List<Pi> = emptyList()
    assertEquals(expected, Pi.simplifyList(pis))

    pis = listOf(Pi(), Pi(true))
    assertEquals(expected, Pi.simplifyList(pis))

    pis = listOf(Pi(true), Pi(), Pi(true), Pi(), Pi(), Pi(true))
    assertEquals(expected, Pi.simplifyList(pis))

    // positive
    pis = listOf(Pi())
    expected = Pair(one, listOf(Pi()))
    assertEquals(expected, Pi.simplifyList(pis))

    pis = listOf(Pi(), Pi(), Pi())
    expected = Pair(one, listOf(Pi(), Pi(), Pi()))
    assertEquals(expected, Pi.simplifyList(pis))

    pis = listOf(Pi(), Pi(), Pi(true))
    expected = Pair(one, listOf(Pi()))
    assertEquals(expected, Pi.simplifyList(pis))

    pis = listOf(Pi(), Pi(), Pi(), Pi(true), Pi(), Pi(true))
    expected = Pair(one, listOf(Pi(), Pi()))
    assertEquals(expected, Pi.simplifyList(pis))

    // negative
    pis = listOf(Pi(true))
    expected = Pair(one, listOf(Pi(true)))
    assertEquals(expected, Pi.simplifyList(pis))

    pis = listOf(Pi(true), Pi(true), Pi(true))
    expected = Pair(one, listOf(Pi(true), Pi(true), Pi(true)))
    assertEquals(expected, Pi.simplifyList(pis))

    pis = listOf(Pi(true), Pi(true), Pi())
    expected = Pair(one, listOf(Pi(true)))
    assertEquals(expected, Pi.simplifyList(pis))

    pis = listOf(Pi(true), Pi(true), Pi(true), Pi(), Pi(true), Pi())
    expected = Pair(one, listOf(Pi(true), Pi(true)))
    assertEquals(expected, Pi.simplifyList(pis))
}
