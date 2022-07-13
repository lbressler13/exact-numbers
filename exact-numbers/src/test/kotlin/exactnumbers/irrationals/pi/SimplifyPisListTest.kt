package exactnumbers.irrationals.pi

import exactnumbers.irrationals.logs.LogNum
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class SimplifyPisListTest {
    @Test
    fun testSimplifyPisList() {
        // error
        assertFails { simplifyPisList(listOf(Pi(), LogNum.ONE)) }

        // equal
        var expected: List<Pi> = listOf()

        var pis: List<Pi> = listOf()
        assertEquals(expected, simplifyPisList(pis))

        pis = listOf(Pi(), Pi(true))
        assertEquals(expected, simplifyPisList(pis))

        pis = listOf(Pi(true), Pi(), Pi(true), Pi(), Pi(), Pi(true))
        assertEquals(expected, simplifyPisList(pis))

        // positive
        pis = listOf(Pi())
        expected = listOf(Pi())
        assertEquals(expected, simplifyPisList(pis))

        pis = listOf(Pi(), Pi(), Pi())
        expected = listOf(Pi(), Pi(), Pi())
        assertEquals(expected, simplifyPisList(pis))

        pis = listOf(Pi(), Pi(), Pi(true))
        expected = listOf(Pi())
        assertEquals(expected, simplifyPisList(pis))

        pis = listOf(Pi(), Pi(), Pi(), Pi(true), Pi(), Pi(true))
        expected = listOf(Pi(), Pi())
        assertEquals(expected, simplifyPisList(pis))

        // negative
        pis = listOf(Pi(true))
        expected = listOf(Pi(true))
        assertEquals(expected, simplifyPisList(pis))

        pis = listOf(Pi(true), Pi(true), Pi(true))
        expected = listOf(Pi(true), Pi(true), Pi(true))
        assertEquals(expected, simplifyPisList(pis))

        pis = listOf(Pi(true), Pi(true), Pi())
        expected = listOf(Pi(true))
        assertEquals(expected, simplifyPisList(pis))

        pis = listOf(Pi(true), Pi(true), Pi(true), Pi(), Pi(true), Pi())
        expected = listOf(Pi(true), Pi(true))
        assertEquals(expected, simplifyPisList(pis))
    }
}
