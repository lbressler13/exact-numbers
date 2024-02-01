package xyz.lbres.exactnumbers.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.exactnumbers.testutils.TestNumber
import xyz.lbres.exactnumbers.testutils.assertSucceeds
import xyz.lbres.exactnumbers.testutils.getCastingOverflowAssertion
import kotlin.test.assertEquals

private val assertCastingOverflow = getCastingOverflowAssertion<Term>("Term")

fun runToByteTests() {
    runWholeNumberCastingTests(Long::toByte, Term::toByte, Byte.MIN_VALUE, Byte.MAX_VALUE, "Byte")
}

fun runToCharTests() {
    var term = Term.ZERO
    var expected = 0.toChar()
    assertEquals(expected, term.toChar())

    term = Term.fromValues(one, listOf(Log(29, 3), Pi(), Pi().inverse()))
    expected = 3.toChar()
    assertEquals(expected, term.toChar())

    var factors = listOf(Sqrt(17), Pi(), Pi(), Log(1245, 12))
    term = Term.fromValues(one, factors)
    expected = 116.toChar()
    assertEquals(expected, term.toChar())

    factors = listOf(Sqrt(17), Pi(), Pi(), TestNumber(ExactFraction(3)), Log(1245, 12))
    term = Term.fromValues(ExactFraction(7, 51), factors)
    expected = 48.toChar()
    assertEquals(expected, term.toChar())

    factors = listOf(Sqrt(17), Pi(), Pi(), Log(1245, 12))
    term = Term.fromValues(ExactFraction(123, 7), factors)
    expected = 2050.toChar()
    assertEquals(expected, term.toChar())

    term = Term.fromValues(ExactFraction(-14, 11), emptyList())
    assertCastingOverflow("Char", term) { term.toChar() }

    term = Term.fromValues(one, listOf(Log(ExactFraction(1, 27), 3), Pi(), Pi().inverse()))
    assertCastingOverflow("Char", term) { term.toChar() }

    term = Term.fromValues(ExactFraction(Char.MAX_VALUE.code), listOf(Log(11).inverse()))
    assertSucceeds("Cast expected to succeed") { term.toChar() }

    term = Term.fromValues(ExactFraction(Char.MAX_VALUE.code), listOf(Log(11)))
    assertCastingOverflow("Char", term) { term.toChar() }
}

fun runToShortTests() {
    runWholeNumberCastingTests(Long::toShort, Term::toShort, Short.MIN_VALUE, Short.MAX_VALUE, "Short")
}

fun runToIntTests() {
    runWholeNumberCastingTests(Long::toInt, Term::toInt, Int.MIN_VALUE, Int.MAX_VALUE, "Int")
}

fun runToLongTests() {
    runWholeNumberCastingTests({ it }, Term::toLong, Long.MIN_VALUE, Long.MAX_VALUE, "Long")
}

fun runToFloatTests() {
    runDecimalNumberCastingTests(Double::toFloat, Term::toFloat, Float.MAX_VALUE, "Float")
}

fun runToDoubleTests() {
    runDecimalNumberCastingTests({ it }, Term::toDouble, Double.MAX_VALUE, "Double")
}

/**
 * Run tests for a single type of whole number
 *
 * @param castLong (Long) -> T: cast a long value to a value of the current number type
 * @param castTerm (Term) -> T: cast a term to a value of the current number type
 * @param minValue T: minimum valid value for the current number type
 * @param maxValue T: maximum valid value for the current number type
 * @param type [String]: string representation of type, which is used in overflow exceptions
 */
private fun <T : Number> runWholeNumberCastingTests(castLong: (Long) -> T, castTerm: (Term) -> T, minValue: T, maxValue: T, type: String) {
    var term = Term.ZERO
    var expected = castLong(0)
    assertEquals(expected, castTerm(term))

    term = Term.fromValues(ExactFraction(-14, 11), emptyList())
    expected = castLong(-1)
    assertEquals(expected, castTerm(term))

    term = Term.fromValues(one, listOf(Log(ExactFraction(1, 27), 3), Pi(), Pi().inverse()))
    expected = castLong(-3)
    assertEquals(expected, castTerm(term))

    term = Term.fromValues(one, listOf(Log(29, 3), Pi(), Pi().inverse()))
    expected = castLong(3)
    assertEquals(expected, castTerm(term))

    var factors = listOf(Sqrt(17), Pi(), Pi(), Log(1245, 12))
    term = Term.fromValues(one, factors)
    expected = castLong(116)
    assertEquals(expected, castTerm(term))

    factors = listOf(Sqrt(17), Pi(), Pi(), TestNumber(ExactFraction(3)), Log(1245, 12))
    term = Term.fromValues(ExactFraction(7, 51), factors)
    expected = castLong(48)
    assertEquals(expected, castTerm(term))

    factors = listOf(Sqrt(17), Pi(), Pi(), Log(1245, 12))
    term = Term.fromValues(ExactFraction(-123, 7), factors)
    if (type == "Byte") {
        assertCastingOverflow(type, term) { castTerm(term) }
    } else {
        expected = castLong(-2050)
        assertEquals(expected, castTerm(term))
    }

    term = Term.fromValues(ExactFraction(minValue.toLong()), listOf(Log(11).inverse()))
    assertSucceeds("Cast expected to succeed") { castTerm(term) }

    term = Term.fromValues(ExactFraction(minValue.toLong()), listOf(Log(11)))
    assertCastingOverflow(type, term) { castTerm(term) }

    term = Term.fromValues(ExactFraction(maxValue.toLong()), listOf(Log(11).inverse()))
    assertSucceeds("Cast expected to succeed") { castTerm(term) }

    term = Term.fromValues(ExactFraction(maxValue.toLong()), listOf(Log(11)))
    assertCastingOverflow(type, term) { castTerm(term) }
}

/**
 * Run tests for a single type of decimal number
 *
 * @param castDouble (Double) -> T: cast a double value to a value of the current number type
 * @param castTerm (Term) -> T: cast a term to a value of the current number type
 * @param maxValue T: maximum valid value for the current number type
 * @param type [String]: string representation of type, which is used in overflow exceptions
 */
private fun <T : Number> runDecimalNumberCastingTests(castDouble: (Double) -> T, castTerm: (Term) -> T, maxValue: T, type: String) {
    var term = Term.ZERO
    var expected = castDouble(0.0)
    assertEquals(expected, castTerm(term))

    term = Term.fromValues(ExactFraction(-1, 3), emptyList())
    expected = castDouble(-0.3333333333333333)
    assertEquals(expected, castTerm(term))

    term = Term.fromValues(one, listOf(Log(ExactFraction(1, 27), 3), Pi(), Pi().inverse()))
    expected = castDouble(-3.0)
    assertEquals(expected, castTerm(term))

    term = Term.fromValues(one, listOf(Log(3333)))
    expected = castDouble(3.52283531366053)
    assertEquals(expected, castTerm(term))

    term = Term.fromValues(ExactFraction(-8, 3), listOf(Pi()))
    expected = castDouble(-8.377580409572781)
    assertEquals(expected, castTerm(term))

    term = Term.fromValues(ExactFraction.EIGHT, listOf(TestNumber(ExactFraction(1, 16)), Log(4, 2).inverse(), Log(123456789), Pi().inverse()))
    expected = castDouble(0.6439023028592971)
    assertEquals(expected, castTerm(term))

    term = Term.fromValues(ExactFraction(-123, 7), listOf(Sqrt(17), Pi(), Pi(), Log(1245, 12)))
    expected = castDouble(-2050.790534360116)
    assertEquals(expected, castTerm(term))

    val largeValue = maxValue.toDouble().toBigDecimal().toBigInteger()
    val smallValue = (-maxValue.toDouble()).toBigDecimal().toBigInteger()

    term = Term.fromValues(ExactFraction(smallValue), listOf(Log(11).inverse()))
    assertSucceeds("Cast expected to succeed") { castTerm(term) }

    term = Term.fromValues(ExactFraction(smallValue), listOf(Log(11)))
    assertCastingOverflow(type, term) { castTerm(term) }

    term = Term.fromValues(ExactFraction(largeValue), listOf(Log(11).inverse()))
    assertSucceeds("Cast expected to succeed") { castTerm(term) }

    term = Term.fromValues(ExactFraction(largeValue), listOf(Log(11)))
    assertCastingOverflow(type, term) { castTerm(term) }
}
