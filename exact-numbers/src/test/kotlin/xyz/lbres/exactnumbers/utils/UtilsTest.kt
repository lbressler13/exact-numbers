package xyz.lbres.exactnumbers.utils

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.kotlinutils.biginteger.ext.isZero
import xyz.lbres.kotlinutils.iterable.ext.countElement
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UtilsTest {
    @Test
    fun testGetIntFromDecimal() {
        var values = mapOf(
            BigInteger.ZERO to setOf(BigDecimal.ZERO, BigDecimal("0.000001")),
            null to setOf(BigDecimal.ONE, BigDecimal("-15"), BigDecimal("1.001"))
        )
        runSingleGetIntFromDecimalTest(values, BigInteger::isZero)

        values = mapOf(null to setOf(BigDecimal.ONE, BigDecimal.ZERO))
        runSingleGetIntFromDecimalTest(values) { false }

        values = mapOf(
            BigInteger("10") to setOf(BigDecimal("10"), BigDecimal("9.999999999"), BigDecimal("10.0000001")),
            BigInteger("-10") to setOf(BigDecimal("-10")),
            null to setOf(BigDecimal("9"), BigDecimal("11"))
        )
        runSingleGetIntFromDecimalTest(values) { it * it == BigInteger("100") }

        values = mapOf(null to setOf(BigDecimal.ONE, -BigDecimal.ONE, BigDecimal("2"), BigDecimal("-2"), BigDecimal("4")))
        runSingleGetIntFromDecimalTest(values) { it * it == BigInteger("2") }

        values = mapOf(
            BigInteger.TWO to setOf(BigDecimal("2"), BigDecimal("1.9999"), BigDecimal("2.0000002")),
            null to setOf(BigDecimal("-2"), BigDecimal("0.5"), BigDecimal("4.999999"))
        )
        runSingleGetIntFromDecimalTest(values) { BigInteger("5").pow(it.toInt()) == BigInteger("25") }

        values = mapOf(null to setOf(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal("2"), BigDecimal("-2"), BigDecimal("1.9999999")))
        runSingleGetIntFromDecimalTest(values) { BigInteger("10").pow(it.toInt()) == BigInteger("99") }

        // test choosing closer number
        values = mapOf(
            BigInteger.ZERO to setOf(BigDecimal("0.3")),
            BigInteger.ONE to setOf(BigDecimal.ONE, BigDecimal("0.5"), BigDecimal("0.7")),
            BigInteger("16") to setOf(BigDecimal("15.5")),
            BigInteger("-15") to setOf(BigDecimal("-15.3")),
            BigInteger("-16") to setOf(BigDecimal("-15.5"), BigDecimal("-15.7")),
            BigInteger("10000") to setOf(BigDecimal("10000.000001")),
            BigInteger("10001") to setOf(BigDecimal("10000.999999"))
        )
        runSingleGetIntFromDecimalTest(values) { true }
    }

    @Test
    fun testGetOrSet() {
        // simple types
        var int: Int? = 0
        var intResult = getOrSet({ int }, { int = it }) { 8 }
        assertEquals(0, int)
        assertEquals(0, intResult)

        int = null
        intResult = getOrSet({ int }, { int = it }) { 8 }
        assertEquals(8, int)
        assertEquals(8, intResult)

        int = 123
        intResult = getOrSet({ int }, { int = it }) { 1 / 0 }
        assertEquals(123, int)
        assertEquals(123, intResult)

        int = null
        assertFailsWith<ArithmeticException> { intResult = getOrSet({ int }, { int = it }) { 1 / 0 } }

        var string: String? = ""
        var stringResult = getOrSet({ string }, { string = it }) { (string ?: "placeholder").length.toString() }
        assertEquals("", string)
        assertEquals("", stringResult)

        string = null
        stringResult = getOrSet({ string }, { string = it }) { (string ?: "placeholder").length.toString() }
        assertEquals("11", string)
        assertEquals("11", stringResult)

        string = "hello world"
        stringResult = getOrSet({ string }, { string = it }) { "" }
        assertEquals("hello world", string)
        assertEquals("hello world", stringResult)

        int = null
        intResult = getOrSet({ int }, { int = it }) { string?.length ?: 0 }
        assertEquals(11, int)
        assertEquals(11, intResult)

        // term
        val ints = listOf(100, 4, 17, -1, -11, 0, 4, -17)
        val factors = listOf(Pi(), Log(100, 2), Log(16, 2), Sqrt(100))
        val generateTerm: () -> Term = {
            val coeff = ints.maxByOrNull { ints.countElement(it) }!!
            val factor = factors.filter { it.isRational() }[0]
            Term.fromValues(ExactFraction(coeff), listOf(factor))
        }

        var term: Term? = Term.fromValues(ExactFraction.EIGHT, listOf(Pi(), Pi().inverse()))
        var termExpected = Term.fromValues(ExactFraction.EIGHT, listOf(Pi(), Pi().inverse()))
        var termResult = getOrSet({ term }, { term = it }, generateTerm)
        assertEquals(termExpected, term)
        assertEquals(termExpected, termResult)

        term = null
        termExpected = Term.fromValues(ExactFraction(4), listOf(Log(16, 2)))
        termResult = getOrSet({ term }, { term = it }, generateTerm)
        assertEquals(termExpected, term)
        assertEquals(termExpected, termResult)

        // multiple vars
        int = null
        string = "123"
        intResult = getOrSet({ int }, { int = string!!.length }) {
            string = "hello"
            0
        }
        assertEquals(5, int)
        assertEquals(5, intResult)
    }

    /**
     * Run single test for getIntFromDecimal
     *
     * @param mapping [Map]<BigInteger?, Set<BigDecimal>>: map where first key is the expected result, and value is a set of inputs expected to return the result
     * @param test ([BigInteger]) -> Boolean: test to pass to getIntFromDecimal
     */
    private fun runSingleGetIntFromDecimalTest(mapping: Map<BigInteger?, Set<BigDecimal>>, test: (BigInteger) -> Boolean) {
        mapping.forEach { (result, values) ->
            values.forEach { assertEquals(result, getIntFromDecimal(it, test)) }
        }
    }
}
