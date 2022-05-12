package ext

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class StringExtTest {
    @Test
    fun testSubstringTo() {
        assertFailsWith<Exception> { "".substringTo(1) }
        assertFailsWith<Exception> { "a".substringTo(-1) }
        assertFailsWith<Exception> { "a b".substringTo(4) }

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