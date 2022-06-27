package ext

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class StringExtTest {
    @Test
    internal fun testSubstringTo() {
        assertFails { "".substringTo(1) }
        assertFails { "a".substringTo(-1) }
        assertFails { "a b".substringTo(4) }

        var s = ""
        var i = 0
        var expected = ""
        assertEquals(expected, s.substringTo(i))

        s = "hello world"

        i = 11
        expected = "hello world"
        assertEquals(expected, s.substringTo(i))

        i = 1
        expected = "h"
        assertEquals(expected, s.substringTo(i))

        i = 6
        expected = "hello "
        assertEquals(expected, s.substringTo(i))

    }
}