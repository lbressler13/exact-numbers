package xyz.lbres.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumber
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.exactnumbers.testutils.TestNumber
import xyz.lbres.kotlinutils.list.StringList
import kotlin.test.assertEquals

private val logNum1 = Log(ExactFraction(15, 4))
private val logNum2 = Log(8, 7)
private val logNum3 = Log(ExactFraction(19, 33)).inverse()
private val logNum4 = Log(ExactFraction(25, 121))
private val testNumber1 = TestNumber(ExactFraction(3, 4))
private val testNumber2 = TestNumber(ExactFraction.SEVEN)
private val one = ExactFraction.ONE

fun runGetIrrationalsByTypeTests() {
    val types = listOf(Log.TYPE, Pi.TYPE, Sqrt.TYPE, TestNumber.TYPE)

    // zero
    var term = Term.fromValues(ExactFraction.ZERO, emptyList())
    runSingleIrrationalsByTypeTest(term, types, emptyMap())

    term = Term.fromValues(one, listOf(logNum1, logNum2, testNumber1, Pi(), TestNumber(ExactFraction.ZERO)))
    runSingleIrrationalsByTypeTest(term, types, emptyMap())

    // non-zero
    term = Term.fromValues(ExactFraction(4, 7), emptyList())
    runSingleIrrationalsByTypeTest(term, types, emptyMap())

    term = Term.fromValues(ExactFraction(4, 7), listOf(Pi(), Sqrt(47), Pi(), Pi(true)))
    runSingleIrrationalsByTypeTest(
        term, types,
        mapOf(
            Pi.TYPE to listOf(Pi(), Pi(), Pi(true)),
            Sqrt.TYPE to listOf(Sqrt(47))
        )
    )

    term = Term.fromValues(one, listOf(testNumber1, logNum2, testNumber2.inverse(), Pi(true)))
    runSingleIrrationalsByTypeTest(
        term, types + listOf("Random"),
        mapOf(
            TestNumber.TYPE to listOf(testNumber1, testNumber2.inverse()),
            Log.TYPE to listOf(logNum2),
            Pi.TYPE to listOf(Pi(true))
        )
    )

    term = Term.fromValues(one, listOf(logNum4, logNum4, logNum2), listOf(Sqrt.ONE, Sqrt(ExactFraction(17, 7))), -2)
    runSingleIrrationalsByTypeTest(
        term, types,
        mapOf(
            Log.TYPE to listOf(logNum4, logNum4, logNum2),
            Sqrt.TYPE to listOf(Sqrt.ONE, Sqrt(ExactFraction(17, 7))),
            Pi.TYPE to listOf(Pi(true), Pi(true))
        )
    )
}

fun runGetLogsTests() {
    // empty
    var expected: List<Log> = emptyList()

    var term = Term.fromValues(one, emptyList())
    assertEquals(expected, term.logs)

    term = Term.fromValues(ExactFraction.TEN, emptyList())
    assertEquals(expected, term.logs)

    term = Term.fromValues(one, listOf(Sqrt(ExactFraction(64, 9)), Pi(), Pi()))
    assertEquals(expected, term.logs)

    // just logs
    term = Term.fromValues(one, listOf(logNum1))
    expected = listOf(logNum1)
    assertEquals(expected, term.logs)

    term = Term.fromValues(one, listOf(logNum1, logNum1))
    expected = listOf(logNum1, logNum1)
    assertEquals(expected, term.logs)

    term = Term.fromValues(one, listOf(logNum1, logNum1.inverse()))
    expected = listOf(logNum1, logNum1.inverse())
    assertEquals(expected, term.logs)

    term = Term.fromValues(one, listOf(logNum3, logNum4, logNum1))
    expected = listOf(logNum3, logNum4, logNum1)
    assertEquals(expected, term.logs)

    // mix
    term = Term.fromValues(one, listOf(logNum3, Sqrt(2), Pi()))
    expected = listOf(logNum3)
    assertEquals(expected, term.logs)

    term = Term.fromValues(ExactFraction.EIGHT, listOf(logNum2, logNum2, logNum3, logNum4, Sqrt(15), Pi(), Pi(true)))
    expected = listOf(logNum2, logNum2, logNum3, logNum4)
    assertEquals(expected, term.logs)
}

fun runGetPiCountTests() {
    // zero
    var expected = 0

    var term = Term.fromValues(one, emptyList())
    assertEquals(expected, term.piCount)

    term = Term.fromValues(ExactFraction.TEN, emptyList())
    assertEquals(expected, term.piCount)

    term = Term.fromValues(one, listOf(Pi(), Pi(true)))
    assertEquals(expected, term.piCount)

    term = Term.fromValues(one, listOf(Pi(), Pi(true), Pi(), Pi(true), Pi(), Pi(true)))
    assertEquals(expected, term.piCount)

    term = Term.fromValues(one, listOf(logNum1, logNum2, Sqrt(ExactFraction(64, 9))))
    assertEquals(expected, term.piCount)

    term = Term.fromValues(one, listOf(logNum3, logNum4, logNum2, Pi(true), Pi(), Pi(true), Pi()))
    assertEquals(expected, term.piCount)

    // just pi
    term = Term.fromValues(one, listOf(Pi()))
    expected = 1
    assertEquals(expected, term.piCount)

    term = Term.fromValues(one, listOf(Pi(true)))
    expected = -1
    assertEquals(expected, term.piCount)

    term = Term.fromValues(one, listOf(Pi(true), Pi(true), Pi(true)))
    expected = -3
    assertEquals(expected, term.piCount)

    term = Term.fromValues(one, listOf(Pi(), Pi(true), Pi(), Pi(), Pi(true)))
    expected = 1
    assertEquals(expected, term.piCount)

    // mix
    term = Term.fromValues(one, listOf(logNum2, Sqrt(2), Pi(true)))
    expected = -1
    assertEquals(expected, term.piCount)

    term = Term.fromValues(ExactFraction.EIGHT, listOf(logNum3, logNum2, Sqrt(36), Pi(), Pi(), Pi(true), Pi()))
    expected = 2
    assertEquals(expected, term.piCount)
}

fun runGetSquareRootsTests() {
    // zero
    var expected: List<Sqrt> = emptyList()

    var term = Term.ONE
    assertEquals(expected, term.squareRoots)

    term = Term.fromValues(ExactFraction.TEN, emptyList())
    assertEquals(expected, term.squareRoots)

    term = Term.fromValues(one, listOf(logNum1, logNum2, Pi(), Pi(true)))
    assertEquals(expected, term.squareRoots)

    // just sqrt
    term = Term.fromValues(one, listOf(Sqrt(4)))
    expected = listOf(Sqrt(4))
    assertEquals(expected, term.squareRoots)

    term = Term.fromValues(one, listOf(Sqrt(4), Sqrt(4)))
    expected = listOf(Sqrt(4), Sqrt(4))
    assertEquals(expected, term.squareRoots)

    term = Term.fromValues(one, listOf(Sqrt(ExactFraction(9, 23)), Sqrt(ExactFraction(23, 9))))
    expected = listOf(Sqrt(ExactFraction(9, 23)), Sqrt(ExactFraction(23, 9)))
    assertEquals(expected, term.squareRoots)

    term = Term.fromValues(one, listOf(Sqrt.ONE, Sqrt(97), Sqrt(ExactFraction(9, 25))))
    expected = listOf(Sqrt.ONE, Sqrt(97), Sqrt(ExactFraction(9, 25)))
    assertEquals(expected, term.squareRoots)

    // mix
    term = Term.fromValues(one, listOf(logNum2, Sqrt(2), Pi(true)))
    expected = listOf(Sqrt(2))
    assertEquals(expected, term.squareRoots)

    term = Term.fromValues(ExactFraction.EIGHT, listOf(logNum1, logNum2, Sqrt.ONE, Sqrt(97), Sqrt(ExactFraction(9, 25)), Pi()))
    expected = listOf(Sqrt.ONE, Sqrt(97), Sqrt(ExactFraction(9, 25)))
    assertEquals(expected, term.squareRoots)
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
        assertEquals(expectedValue, term.getIrrationalsByType(type))
    }
}
