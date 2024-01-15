package xyz.lbres.exactnumbers.irrationals.pi

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.constMultiSetOf
import xyz.lbres.kotlinutils.set.multiset.const.emptyConstMultiSet
import kotlin.test.assertEquals

private val one = ExactFraction.ONE

fun runSimplifySetTests() {
    // equal
    var expected: Pair<ExactFraction, ConstMultiSet<Pi>> = Pair(one, emptyConstMultiSet())

    var pis: ConstMultiSet<Pi> = constMultiSetOf()
    assertEquals(expected, Pi.simplifySet(pis))

    pis = constMultiSetOf(Pi(), Pi().inverse())
    assertEquals(expected, Pi.simplifySet(pis))

    pis = constMultiSetOf(Pi().inverse(), Pi(), Pi().inverse(), Pi(), Pi(), Pi().inverse())
    assertEquals(expected, Pi.simplifySet(pis))

    // positive
    pis = constMultiSetOf(Pi())
    expected = Pair(one, constMultiSetOf(Pi()))
    assertEquals(expected, Pi.simplifySet(pis))

    pis = constMultiSetOf(Pi(), Pi(), Pi())
    expected = Pair(one, constMultiSetOf(Pi(), Pi(), Pi()))
    assertEquals(expected, Pi.simplifySet(pis))

    pis = constMultiSetOf(Pi(), Pi(), Pi().inverse())
    expected = Pair(one, constMultiSetOf(Pi()))
    assertEquals(expected, Pi.simplifySet(pis))

    pis = constMultiSetOf(Pi(), Pi(), Pi(), Pi().inverse(), Pi(), Pi().inverse())
    expected = Pair(one, constMultiSetOf(Pi(), Pi()))
    assertEquals(expected, Pi.simplifySet(pis))

    // negative
    pis = constMultiSetOf(Pi().inverse())
    expected = Pair(one, constMultiSetOf(Pi().inverse()))
    assertEquals(expected, Pi.simplifySet(pis))

    pis = constMultiSetOf(Pi().inverse(), Pi().inverse(), Pi().inverse())
    expected = Pair(one, constMultiSetOf(Pi().inverse(), Pi().inverse(), Pi().inverse()))
    assertEquals(expected, Pi.simplifySet(pis))

    pis = constMultiSetOf(Pi().inverse(), Pi().inverse(), Pi())
    expected = Pair(one, constMultiSetOf(Pi().inverse()))
    assertEquals(expected, Pi.simplifySet(pis))

    pis = constMultiSetOf(Pi().inverse(), Pi().inverse(), Pi().inverse(), Pi(), Pi().inverse(), Pi())
    expected = Pair(one, constMultiSetOf(Pi().inverse(), Pi().inverse()))
    assertEquals(expected, Pi.simplifySet(pis))
}
