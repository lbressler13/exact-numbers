package xyz.lbres.exactnumbers.irrationals.pi

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
