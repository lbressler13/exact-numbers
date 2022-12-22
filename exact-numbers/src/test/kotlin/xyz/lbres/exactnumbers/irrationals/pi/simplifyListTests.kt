package xyz.lbres.exactnumbers.irrationals.pi

import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import kotlin.test.assertEquals

internal fun runSimplifyListTests() {
    // equal
    var expected: List<Pi> = listOf()

    assertEquals(expected, Pi.simplifyList(null))

    var pis: List<Pi> = listOf()
    assertEquals(expected, Pi.simplifyList(pis))

    pis = listOf(Pi(), Pi().inverse())
    assertEquals(expected, Pi.simplifyList(pis))

    pis = listOf(Pi().inverse(), Pi(), Pi().inverse(), Pi(), Pi(), Pi().inverse())
    assertEquals(expected, Pi.simplifyList(pis))

    // positive
    pis = listOf(Pi())
    expected = listOf(Pi())
    assertEquals(expected, Pi.simplifyList(pis))

    pis = listOf(Pi(), Pi(), Pi())
    expected = listOf(Pi(), Pi(), Pi())
    assertEquals(expected, Pi.simplifyList(pis))

    pis = listOf(Pi(), Pi(), Pi().inverse())
    expected = listOf(Pi())
    assertEquals(expected, Pi.simplifyList(pis))

    pis = listOf(Pi(), Pi(), Pi(), Pi().inverse(), Pi(), Pi().inverse())
    expected = listOf(Pi(), Pi())
    assertEquals(expected, Pi.simplifyList(pis))

    // negative
    pis = listOf(Pi().inverse())
    expected = listOf(Pi().inverse())
    assertEquals(expected, Pi.simplifyList(pis))

    pis = listOf(Pi().inverse(), Pi().inverse(), Pi().inverse())
    expected = listOf(Pi().inverse(), Pi().inverse(), Pi().inverse())
    assertEquals(expected, Pi.simplifyList(pis))

    pis = listOf(Pi().inverse(), Pi().inverse(), Pi())
    expected = listOf(Pi().inverse())
    assertEquals(expected, Pi.simplifyList(pis))

    pis = listOf(Pi().inverse(), Pi().inverse(), Pi().inverse(), Pi(), Pi().inverse(), Pi())
    expected = listOf(Pi().inverse(), Pi().inverse())
    assertEquals(expected, Pi.simplifyList(pis))
}

internal fun runSimplifySetTests() {
    // equal
    var expected: MultiSet<Pi> = multiSetOf()
    var pis: MultiSet<Pi> = multiSetOf()
    assertEquals(expected, Pi.simplifySet(pis))

    pis = multiSetOf(Pi(), Pi().inverse())
    assertEquals(expected, Pi.simplifySet(pis))

    pis = multiSetOf(Pi().inverse(), Pi(), Pi().inverse(), Pi(), Pi(), Pi().inverse())
    assertEquals(expected, Pi.simplifySet(pis))

    // positive
    pis = multiSetOf(Pi())
    expected = multiSetOf(Pi())
    assertEquals(expected, Pi.simplifySet(pis))

    pis = multiSetOf(Pi(), Pi(), Pi())
    expected = multiSetOf(Pi(), Pi(), Pi())
    assertEquals(expected, Pi.simplifySet(pis))

    pis = multiSetOf(Pi(), Pi(), Pi().inverse())
    expected = multiSetOf(Pi())
    assertEquals(expected, Pi.simplifySet(pis))

    pis = multiSetOf(Pi(), Pi(), Pi(), Pi().inverse(), Pi(), Pi().inverse())
    expected = multiSetOf(Pi(), Pi())
    assertEquals(expected, Pi.simplifySet(pis))

    // negative
    pis = multiSetOf(Pi().inverse())
    expected = multiSetOf(Pi().inverse())
    assertEquals(expected, Pi.simplifySet(pis))

    pis = multiSetOf(Pi().inverse(), Pi().inverse(), Pi().inverse())
    expected = multiSetOf(Pi().inverse(), Pi().inverse(), Pi().inverse())
    assertEquals(expected, Pi.simplifySet(pis))

    pis = multiSetOf(Pi().inverse(), Pi().inverse(), Pi())
    expected = multiSetOf(Pi().inverse())
    assertEquals(expected, Pi.simplifySet(pis))

    pis = multiSetOf(Pi().inverse(), Pi().inverse(), Pi().inverse(), Pi(), Pi().inverse(), Pi())
    expected = multiSetOf(Pi().inverse(), Pi().inverse())
    assertEquals(expected, Pi.simplifySet(pis))
}
