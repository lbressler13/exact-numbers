package xyz.lbres.exactnumbers.irrationals.pi

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.emptyMultiSet
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import kotlin.test.assertEquals

private val one = ExactFraction.ONE

internal fun runSimplifySetTests() {
    // equal
    var pis: MultiSet<Pi> = multiSetOf()
    var expected: Pair<ExactFraction, MultiSet<Pi>> = Pair(one, emptyMultiSet())

    assertEquals(expected, Pi.simplifySet(pis))

    pis = multiSetOf(Pi(), Pi().inverse())
    assertEquals(expected, Pi.simplifySet(pis))

    pis = multiSetOf(Pi().inverse(), Pi(), Pi().inverse(), Pi(), Pi(), Pi().inverse())
    assertEquals(expected, Pi.simplifySet(pis))

    // positive
    pis = multiSetOf(Pi())
    expected = Pair(one, multiSetOf(Pi()))
    assertEquals(expected, Pi.simplifySet(pis))

    pis = multiSetOf(Pi(), Pi(), Pi())
    expected = Pair(one, multiSetOf(Pi(), Pi(), Pi()))
    assertEquals(expected, Pi.simplifySet(pis))

    pis = multiSetOf(Pi(), Pi(), Pi().inverse())
    expected = Pair(one, multiSetOf(Pi()))
    assertEquals(expected, Pi.simplifySet(pis))

    pis = multiSetOf(Pi(), Pi(), Pi(), Pi().inverse(), Pi(), Pi().inverse())
    expected = Pair(one, multiSetOf(Pi(), Pi()))
    assertEquals(expected, Pi.simplifySet(pis))

    // negative
    pis = multiSetOf(Pi().inverse())
    expected = Pair(one, multiSetOf(Pi().inverse()))
    assertEquals(expected, Pi.simplifySet(pis))

    pis = multiSetOf(Pi().inverse(), Pi().inverse(), Pi().inverse())
    expected = Pair(one, multiSetOf(Pi().inverse(), Pi().inverse(), Pi().inverse()))
    assertEquals(expected, Pi.simplifySet(pis))

    pis = multiSetOf(Pi().inverse(), Pi().inverse(), Pi())
    expected = Pair(one, multiSetOf(Pi().inverse()))
    assertEquals(expected, Pi.simplifySet(pis))

    pis = multiSetOf(Pi().inverse(), Pi().inverse(), Pi().inverse(), Pi(), Pi().inverse(), Pi())
    expected = Pair(one, multiSetOf(Pi().inverse(), Pi().inverse()))
    assertEquals(expected, Pi.simplifySet(pis))
}
