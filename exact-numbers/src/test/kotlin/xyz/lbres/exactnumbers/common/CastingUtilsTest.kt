package xyz.lbres.exactnumbers.common

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import kotlin.test.Test
import kotlin.test.assertEquals

class CastingUtilsTest {
    @Test
    fun testCreateCastingException() {
        var e = createCastingException(123, "Char")
        var expectedMessage = "Overflow casting value 123 of type Int to Char"
        assertEquals(expectedMessage, e.message)
        assertEquals(123, e.overflowValue)

        e = createCastingException(ExactFraction(3, 4), "Int")
        expectedMessage = "Overflow casting value EF[3 4] of type ExactFraction to Int"
        assertEquals(expectedMessage, e.message)
        assertEquals(ExactFraction(3, 4), e.overflowValue)

        e = createCastingException(ExactFraction(3, 4), "Int", "3/4")
        expectedMessage = "Overflow casting value 3/4 of type ExactFraction to Int"
        assertEquals(expectedMessage, e.message)
        assertEquals(ExactFraction(3, 4), e.overflowValue)

        e = createCastingException("hello world", "fake type")
        expectedMessage = "Overflow casting value hello world of type String to fake type"
        assertEquals(expectedMessage, e.message)
        assertEquals("hello world", e.overflowValue)

        e = createCastingException(Log(5, 2), "ExactFraction", "Log(5, 2)")
        expectedMessage = "Overflow casting value Log(5, 2) of type Log to ExactFraction"
        assertEquals(expectedMessage, e.message)
        assertEquals(Log(5, 2), e.overflowValue)
    }
}
