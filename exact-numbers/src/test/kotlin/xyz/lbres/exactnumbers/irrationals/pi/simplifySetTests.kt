package xyz.lbres.exactnumbers.irrationals.pi

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.constMultiSetOf
import xyz.lbres.kotlinutils.set.multiset.const.emptyConstMultiSet
import kotlin.test.assertEquals

private val one = ExactFraction.ONE

fun runSimplifySetTests() {
    // equal
    var pis: ConstMultiSet<Pi> = constMultiSetOf()
    var expected: Pair<ExactFraction, ConstMultiSet<Pi>> = Pair(one, emptyConstMultiSet())

    assertEquals(expected, Pi.simplifySet(pis))

    pis = constMultiSetOf(Pi(), Pi(true))
    assertEquals(expected, Pi.simplifySet(pis))

    pis = constMultiSetOf(Pi(true), Pi(), Pi(true), Pi(), Pi(), Pi(true))
    assertEquals(expected, Pi.simplifySet(pis))

    // positive
    pis = constMultiSetOf(Pi())
    expected = Pair(one, constMultiSetOf(Pi()))
    assertEquals(expected, Pi.simplifySet(pis))

    pis = constMultiSetOf(Pi(), Pi(), Pi())
    expected = Pair(one, constMultiSetOf(Pi(), Pi(), Pi()))
    assertEquals(expected, Pi.simplifySet(pis))

    pis = constMultiSetOf(Pi(), Pi(), Pi(true))
    expected = Pair(one, constMultiSetOf(Pi()))
    assertEquals(expected, Pi.simplifySet(pis))

    pis = constMultiSetOf(Pi(), Pi(), Pi(), Pi(true), Pi(), Pi(true))
    expected = Pair(one, constMultiSetOf(Pi(), Pi()))
    assertEquals(expected, Pi.simplifySet(pis))

    // negative
    pis = constMultiSetOf(Pi(true))
    expected = Pair(one, constMultiSetOf(Pi(true)))
    assertEquals(expected, Pi.simplifySet(pis))

    pis = constMultiSetOf(Pi(true), Pi(true), Pi(true))
    expected = Pair(one, constMultiSetOf(Pi(true), Pi(true), Pi(true)))
    assertEquals(expected, Pi.simplifySet(pis))

    pis = constMultiSetOf(Pi(true), Pi(true), Pi())
    expected = Pair(one, constMultiSetOf(Pi(true)))
    assertEquals(expected, Pi.simplifySet(pis))

    pis = constMultiSetOf(Pi(true), Pi(true), Pi(true), Pi(), Pi(true), Pi())
    expected = Pair(one, constMultiSetOf(Pi(true), Pi(true)))
    assertEquals(expected, Pi.simplifySet(pis))
}
