package xyz.lbres.exactnumbers.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.IrrationalNumber
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.exactnumbers.testutils.TestNumber
import xyz.lbres.kotlinutils.list.StringList
import kotlin.test.assertEquals

private val log1 = Log(ExactFraction(15, 4))
private val log2 = Log(8, 7)
private val log3 = Log(ExactFraction(19, 33)).inverse()
private val log4 = Log(ExactFraction(25, 121))
private val testNumber1 = TestNumber(ExactFraction(3, 4))
private val testNumber2 = TestNumber(ExactFraction.SEVEN)
private val one = ExactFraction.ONE

@Suppress("Deprecated")
fun runGetFactorsByTypeTests() {
    val types = listOf(Log.TYPE, Pi.TYPE, Sqrt.TYPE, TestNumber.TYPE)

    // zero
    var term = Term.fromValues(ExactFraction.ZERO, emptyList())
    runSingleIrrationalsByTypeTest(term, types, emptyMap())

    term = Term.fromValues(one, listOf(log1, log2, testNumber1, Pi(), TestNumber(ExactFraction.ZERO)))
    runSingleIrrationalsByTypeTest(term, types, emptyMap())

    // non-zero
    term = Term.fromValues(ExactFraction(4, 7), emptyList())
    runSingleIrrationalsByTypeTest(term, types, emptyMap())

    term = Term.fromValues(ExactFraction(4, 7), listOf(Pi(), Sqrt(47), Pi(), Pi().inverse()))
    runSingleIrrationalsByTypeTest(
        term, types,
        mapOf(
            Pi.TYPE to listOf(Pi(), Pi(), Pi().inverse()),
            Sqrt.TYPE to listOf(Sqrt(47))
        )
    )

    term = Term.fromValues(one, listOf(testNumber1, log2, testNumber2.inverse(), Pi().inverse()))
    runSingleIrrationalsByTypeTest(
        term, types + listOf("Random"),
        mapOf(
            TestNumber.TYPE to listOf(testNumber1, testNumber2.inverse()),
            Log.TYPE to listOf(log2),
            Pi.TYPE to listOf(Pi().inverse())
        )
    )

    term = Term.fromValues(one, listOf(log4, log4, log2), listOf(Sqrt.ONE, Sqrt(ExactFraction(17, 7))), -2)
    runSingleIrrationalsByTypeTest(
        term, types,
        mapOf(
            Log.TYPE to listOf(log4, log4, log2),
            Sqrt.TYPE to listOf(Sqrt.ONE, Sqrt(ExactFraction(17, 7))),
            Pi.TYPE to listOf(Pi().inverse(), Pi().inverse())
        )
    )
}

@Suppress("Deprecation")
fun runGetLogsTests() {
    // empty
    var expected: List<Log> = emptyList()

    var term = Term.fromValues(one, emptyList())
    assertEquals(expected, term.getLogs())

    term = Term.fromValues(ExactFraction.TEN, emptyList())
    assertEquals(expected, term.getLogs())

    term = Term.fromValues(one, listOf(Sqrt(ExactFraction(64, 9)), Pi(), Pi()))
    assertEquals(expected, term.getLogs())

    // just logs
    term = Term.fromValues(one, listOf(log1))
    expected = listOf(log1)
    assertEquals(expected, term.getLogs())

    term = Term.fromValues(one, listOf(log1, log1))
    expected = listOf(log1, log1)
    assertEquals(expected, term.getLogs())

    term = Term.fromValues(one, listOf(log1, log1.inverse()))
    expected = listOf(log1, log1.inverse())
    assertEquals(expected, term.getLogs())

    term = Term.fromValues(one, listOf(log3, log4, log1))
    expected = listOf(log3, log4, log1)
    assertEquals(expected, term.getLogs())

    // mix
    term = Term.fromValues(one, listOf(log3, Sqrt(2), Pi()))
    expected = listOf(log3)
    assertEquals(expected, term.getLogs())

    term = Term.fromValues(ExactFraction.EIGHT, listOf(log2, log2, log3, log4, Sqrt(15), Pi(), Pi().inverse()))
    expected = listOf(log2, log2, log3, log4)
    assertEquals(expected, term.getLogs())
}

@Suppress("Deprecation")
fun runGetPiCountTests() {
    // zero
    var expected = 0

    var term = Term.fromValues(one, emptyList())
    assertEquals(expected, term.getPiCount())

    term = Term.fromValues(ExactFraction.TEN, emptyList())
    assertEquals(expected, term.getPiCount())

    term = Term.fromValues(one, listOf(Pi(), Pi().inverse()))
    assertEquals(expected, term.getPiCount())

    term = Term.fromValues(one, listOf(Pi(), Pi().inverse(), Pi(), Pi().inverse(), Pi(), Pi().inverse()))
    assertEquals(expected, term.getPiCount())

    term = Term.fromValues(one, listOf(log1, log2, Sqrt(ExactFraction(64, 9))))
    assertEquals(expected, term.getPiCount())

    term = Term.fromValues(one, listOf(log3, log4, log2, Pi().inverse(), Pi(), Pi().inverse(), Pi()))
    assertEquals(expected, term.getPiCount())

    // just pi
    term = Term.fromValues(one, listOf(Pi()))
    expected = 1
    assertEquals(expected, term.getPiCount())

    term = Term.fromValues(one, listOf(Pi().inverse()))
    expected = -1
    assertEquals(expected, term.getPiCount())

    term = Term.fromValues(one, listOf(Pi().inverse(), Pi().inverse(), Pi().inverse()))
    expected = -3
    assertEquals(expected, term.getPiCount())

    term = Term.fromValues(one, listOf(Pi(), Pi().inverse(), Pi(), Pi(), Pi().inverse()))
    expected = 1
    assertEquals(expected, term.getPiCount())

    // mix
    term = Term.fromValues(one, listOf(log2, Sqrt(2), Pi().inverse()))
    expected = -1
    assertEquals(expected, term.getPiCount())

    term = Term.fromValues(ExactFraction.EIGHT, listOf(log3, log2, Sqrt(36), Pi(), Pi(), Pi().inverse(), Pi()))
    expected = 2
    assertEquals(expected, term.getPiCount())
}

@Suppress("Deprecation")
fun runGetSquareRootsTests() {
    // zero
    var expected: List<Sqrt> = emptyList()

    var term = Term.ONE
    assertEquals(expected, term.getSquareRoots())

    term = Term.fromValues(ExactFraction.TEN, emptyList())
    assertEquals(expected, term.getSquareRoots())

    term = Term.fromValues(one, listOf(log1, log2, Pi(), Pi().inverse()))
    assertEquals(expected, term.getSquareRoots())

    // just sqrt
    term = Term.fromValues(one, listOf(Sqrt(4)))
    expected = listOf(Sqrt(4))
    assertEquals(expected, term.getSquareRoots())

    term = Term.fromValues(one, listOf(Sqrt(4), Sqrt(4)))
    expected = listOf(Sqrt(4), Sqrt(4))
    assertEquals(expected, term.getSquareRoots())

    term = Term.fromValues(one, listOf(Sqrt(ExactFraction(9, 23)), Sqrt(ExactFraction(23, 9))))
    expected = listOf(Sqrt(ExactFraction(9, 23)), Sqrt(ExactFraction(23, 9)))
    assertEquals(expected, term.getSquareRoots())

    term = Term.fromValues(one, listOf(Sqrt.ONE, Sqrt(97), Sqrt(ExactFraction(9, 25))))
    expected = listOf(Sqrt.ONE, Sqrt(97), Sqrt(ExactFraction(9, 25)))
    assertEquals(expected, term.getSquareRoots())

    // mix
    term = Term.fromValues(one, listOf(log2, Sqrt(2), Pi().inverse()))
    expected = listOf(Sqrt(2))
    assertEquals(expected, term.getSquareRoots())

    term = Term.fromValues(ExactFraction.EIGHT, listOf(log1, log2, Sqrt.ONE, Sqrt(97), Sqrt(ExactFraction(9, 25)), Pi()))
    expected = listOf(Sqrt.ONE, Sqrt(97), Sqrt(ExactFraction(9, 25)))
    assertEquals(expected, term.getSquareRoots())
}

/**
 * Run single test for getIrrationals by type
 *
 * @param term [Term]
 * @param types [StringList]: types to check
 * @param expectedValues [Map]<String, List<IrrationalNumber<*>>: map of type names to expected values.
 * If type is not in map, expected value is assumed to be empty list
 */
private fun runSingleIrrationalsByTypeTest(term: Term, types: StringList, expectedValues: Map<String, List<IrrationalNumber<*>>>) {
    for (type in types) {
        val expectedValue = expectedValues.getOrDefault(type, emptyList())
        assertEquals(expectedValue, term.getFactorsByType(type))
    }
}
