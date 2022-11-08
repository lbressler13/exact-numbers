package xyz.lbres.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import java.math.BigDecimal
import kotlin.test.assertEquals

private val logNum1 = Log(ExactFraction(15, 4))
private val logNum2 = Log(8, 7)
private val sqrt1 = Sqrt(64)
private val sqrt2 = Sqrt(ExactFraction(20, 33))
private val one = ExactFraction.ONE

internal fun runGetSimplifiedTests() {
    // simplified
    var term = Term.fromValues(ExactFraction.EIGHT, listOf(Pi(), Pi(true)))
    var expectedCoeff = ExactFraction.EIGHT
    // var expectedNumbers: List<Irrational> = emptyList()
    // var result = term.getSimplified()
    // assertEquals(expectedCoeff, result.coefficient)
    // assertEquals(expectedNumbers, result.numbers)
    runSingleGetSimplifiedTest(term, expectedCoeff, emptyList(), emptyList(), emptyList())

    term = Term.fromValues(ExactFraction(-3, 2), listOf(logNum1), listOf(Pi(), Pi(true), Pi()))
    expectedCoeff = ExactFraction(-3, 2)
    var expectedLogs = listOf(logNum1)
    var expectedPis = listOf(Pi())
    // expectedNumbers = listOf(logNum1, Pi())
    // result = term.getSimplified()
    // assertEquals(expectedCoeff, result.coefficient)
    // assertEquals(expectedNumbers, result.numbers)
    runSingleGetSimplifiedTest(term, expectedCoeff, expectedLogs, emptyList(), expectedPis)

    term = Term.fromValues(ExactFraction.HALF, listOf(Log.ONE, logNum1))
    expectedCoeff = ExactFraction.HALF
    expectedLogs = listOf(logNum1)
    runSingleGetSimplifiedTest(term, expectedCoeff, expectedLogs, emptyList(), emptyList())
    // expectedNumbers = listOf(logNum1)
    // result = term.getSimplified()
    // assertEquals(expectedCoeff, result.coefficient)
    // assertEquals(expectedNumbers, result.numbers)

    term = Term.fromValues(-ExactFraction.HALF, listOf(Log.ONE), listOf(Pi(true)))
    expectedCoeff = -ExactFraction.HALF
    expectedPis = listOf(Pi(true))
    runSingleGetSimplifiedTest(term, expectedCoeff, emptyList(), emptyList(), expectedPis)
    // expectedNumbers = listOf(Pi(true))
    // result = term.getSimplified()
    // assertEquals(expectedCoeff, result.coefficient)
    // assertEquals(expectedNumbers, result.numbers)

    term = Term.fromValues(ExactFraction.TEN, listOf(Sqrt.ONE, sqrt2))
    expectedCoeff = ExactFraction(20)
    var expectedSqrts = listOf(Sqrt(ExactFraction(5, 33)))
    runSingleGetSimplifiedTest(term, expectedCoeff, emptyList(), expectedSqrts, emptyList())
    // expectedNumbers = listOf(Sqrt(ExactFraction(5, 33)))
    // result = term.getSimplified()
    // assertEquals(expectedCoeff, result.coefficient)
    // assertEquals(expectedNumbers, result.numbers)

    term = Term.fromValues(ExactFraction.TWO, listOf(Sqrt(64), Sqrt(ExactFraction(75, 98)), Sqrt(26)))
    expectedCoeff = ExactFraction(80, 7)
    expectedSqrts = listOf(Sqrt(ExactFraction(39)))
    runSingleGetSimplifiedTest(term, expectedCoeff, emptyList(), expectedSqrts, emptyList())
    // expectedNumbers = listOf(Sqrt(ExactFraction(39)))
    // result = term.getSimplified()
    // assertEquals(expectedCoeff, result.coefficient)
    // assertEquals(expectedNumbers, result.numbers)

    term = Term.fromValues(
        ExactFraction(18, 5),
        listOf(logNum2, logNum2, logNum1, logNum2.swapDivided()),
        listOf(Pi(true), Pi(true), Pi(true), Pi())
    )
    expectedCoeff = ExactFraction(18, 5)
    expectedLogs = listOf(logNum2, logNum1)
    expectedPis = listOf(Pi(true), Pi(true))
    runSingleGetSimplifiedTest(term, expectedCoeff, expectedLogs, emptyList(), expectedPis)
    // expectedNumbers = listOf(logNum2, logNum1, Pi(true), Pi(true))
    // result = term.getSimplified()
    // assertEquals(expectedCoeff, result.coefficient)
    // assertEquals(expectedNumbers, result.numbers)

    term = Term.fromValues(ExactFraction.FOUR, listOf(Log(100)), listOf(Sqrt(9), Sqrt(ExactFraction(1, 4))))
    expectedCoeff = ExactFraction(12)
    runSingleGetSimplifiedTest(term, expectedCoeff, emptyList(), emptyList(), emptyList())
    // expectedNumbers = emptyList()
    // result = term.getSimplified()
    // assertEquals(expectedCoeff, result.coefficient)
    // assertEquals(expectedNumbers, result.numbers)

    term = Term.fromValues(-ExactFraction.EIGHT, listOf(Sqrt(ExactFraction(27, 98))), listOf(Pi(true)))
    expectedCoeff = ExactFraction(-24, 7)
    expectedSqrts = listOf(Sqrt(ExactFraction(3, 2)))
    expectedPis = listOf(Pi(true))
    runSingleGetSimplifiedTest(term, expectedCoeff, emptyList(), expectedSqrts, expectedPis)
    // expectedNumbers = listOf(Sqrt(ExactFraction(3, 2)), Pi(true))
    // result = term.getSimplified()
    // assertEquals(expectedCoeff, result.coefficient)
    // assertEquals(expectedNumbers, result.numbers)

    term = Term.fromValues(ExactFraction(20), listOf(Log(ExactFraction(1, 27), 3, true)))
    expectedCoeff = ExactFraction(-20, 3)
    runSingleGetSimplifiedTest(term, expectedCoeff, emptyList(), emptyList(), emptyList())
    // expectedNumbers = emptyList()
    // result = term.getSimplified()
    // assertEquals(expectedCoeff, result.coefficient)
    // assertEquals(expectedNumbers, result.numbers)

    term = Term.fromValues(
        ExactFraction(3, 5),
        listOf(
            Log(4),
            Log(ExactFraction(625), 5),
            Log(1000, 12),
            Log(ExactFraction(1, 16), 4, true)
        ),
        listOf(Sqrt(ExactFraction(18, 7)), Sqrt(25), Sqrt(ExactFraction(13, 3))),
        listOf(Pi())
    )
    expectedCoeff = ExactFraction(-6)
    expectedLogs = listOf(Log(4), Log(1000, 12))
    expectedSqrts = listOf(Sqrt(ExactFraction(78, 7)))
    expectedPis = listOf(Pi())
    runSingleGetSimplifiedTest(term, expectedCoeff, expectedLogs, expectedSqrts, expectedPis)
    // expectedNumbers = listOf(
        // Log(4),
        // Log(1000, 12),
        // Sqrt(ExactFraction(78, 7)),
        // Pi()
    // )
    // result = term.getSimplified()
    // assertEquals(expectedCoeff, result.coefficient)
    // assertEquals(expectedNumbers, result.numbers)

    // no changes
    term = Term.fromValue(ExactFraction.EIGHT)
    expectedCoeff = ExactFraction.EIGHT
    runSingleGetSimplifiedTest(term, expectedCoeff, emptyList(), emptyList(), emptyList())
    // expectedNumbers = emptyList()
    // result = term.getSimplified()
    // assertEquals(expectedCoeff, result.coefficient)
    // assertEquals(expectedNumbers, result.numbers)

    term = Term.fromValues(ExactFraction.EIGHT, listOf(logNum1))
    expectedCoeff = ExactFraction.EIGHT
    expectedLogs = listOf(logNum1)
    runSingleGetSimplifiedTest(term, expectedCoeff, expectedLogs, emptyList(), emptyList())
    // expectedNumbers = listOf(logNum1)
    // result = term.getSimplified()
    // assertEquals(expectedCoeff, result.coefficient)
    // assertEquals(expectedNumbers, result.numbers)

    term = Term.fromValues(ExactFraction.EIGHT, listOf(Sqrt(ExactFraction(1, 46))))
    expectedCoeff = ExactFraction.EIGHT
    expectedSqrts = listOf(Sqrt(ExactFraction(1, 46)))
    runSingleGetSimplifiedTest(term, expectedCoeff, emptyList(), expectedSqrts, emptyList())
    // expectedNumbers = listOf(Sqrt(ExactFraction(1, 46)))
    // result = term.getSimplified()
    // assertEquals(expectedCoeff, result.coefficient)
    // assertEquals(expectedNumbers, result.numbers)

    term = Term.fromValues(ExactFraction(-5, 6), listOf(Pi(true)))
    expectedCoeff = ExactFraction(-5, 6)
    expectedPis = listOf(Pi(true))
    runSingleGetSimplifiedTest(term, expectedCoeff, emptyList(), emptyList(), expectedPis)
    // expectedNumbers = listOf(Pi(true))
    // result = term.getSimplified()
    // assertEquals(expectedCoeff, result.coefficient)
    // assertEquals(expectedNumbers, result.numbers)

    term = Term.fromValues(ExactFraction.SEVEN, listOf(logNum1, logNum1, logNum2.swapDivided()), listOf(Sqrt(5)), listOf(Pi(), Pi()))
    expectedCoeff = ExactFraction.SEVEN
    expectedLogs = listOf(logNum1, logNum1, logNum2.swapDivided())
    expectedSqrts = listOf(Sqrt(5))
    expectedPis = listOf(Pi(), Pi())
    runSingleGetSimplifiedTest(term, expectedCoeff, expectedLogs, expectedSqrts, expectedPis)
    // expectedNumbers = listOf(logNum1, logNum1, logNum2.swapDivided(), Sqrt(5), Pi(), Pi())
    // result = term.getSimplified()
    // assertEquals(expectedCoeff, result.coefficient)
    // assertEquals(expectedNumbers, result.numbers)
}

internal fun runGetValueTests() {
    // just number
    var term = Term.ZERO
    var expected = BigDecimal.ZERO
    assertEquals(expected, term.getValue())

    term = Term.fromValue(ExactFraction.EIGHT)
    expected = BigDecimal("8")
    assertEquals(expected, term.getValue())

    term = Term.fromValue(ExactFraction(-1, 3))
    expected = BigDecimal("-0.33333333333333333333")
    assertEquals(expected, term.getValue())

    // just logs
    term = Term.fromValues(listOf(logNum1, logNum1.swapDivided()))
    expected = BigDecimal.ONE
    assertEquals(expected, term.getValue())

    term = Term.fromValue(Log(3333))
    expected = BigDecimal("3.52283531366053")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(listOf(logNum1, logNum2))
    expected = BigDecimal("0.61342218956802803344500481172832")
    assertEquals(expected, term.getValue())

    // just pi
    term = Term.fromValues(listOf(Pi(), Pi(true)))
    expected = BigDecimal.ONE
    assertEquals(expected, term.getValue())

    term = Term.fromValue(Pi(true))
    expected = BigDecimal("0.31830988618379069570")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(listOf(Pi(), Pi(), Pi()))
    expected = BigDecimal("31.006276680299813114880451174049119330924860257")
    assertEquals(expected, term.getValue())

    // just sqrt
    term = Term.fromValue(Sqrt(ExactFraction(9, 5)))
    expected = BigDecimal("1.34164078649987381784")
    assertEquals(expected, term.getValue())

    term = Term.fromValue(Sqrt(121))
    expected = BigDecimal("11")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(listOf(Sqrt(12), Sqrt(ExactFraction(9, 25))))
    expected = BigDecimal("2.0784609690826527522")
    assertEquals(expected, term.getValue())

    // combination
    term = Term.fromValues(ExactFraction(-8, 3), listOf(Pi()))
    expected = BigDecimal("-8.3775804095727813333")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(ExactFraction.EIGHT, listOf(Log(9)), listOf(Sqrt(49), Sqrt(2)))
    expected = BigDecimal("75.57215112395364893851321831545508672")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(ExactFraction.SEVEN, listOf(Log(ExactFraction(6, 7)), Log(40)), listOf(Pi()))
    expected = BigDecimal("-3.9175691871908989550925271506970548502975209152")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(ExactFraction.HALF, listOf(Log(4, 2, true), Log(123456789)), listOf(Pi(true)))
    expected = BigDecimal("0.64390230285929702583103243749028475")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(ExactFraction(3, 11), listOf(Log(5, 2)), listOf(Sqrt(122)), listOf(Pi()))
    expected = BigDecimal("21.973899001484265398")
    assertEquals(expected, term.getValue())
}

private fun runSingleGetSimplifiedTest(term: Term, expectedCoeff: ExactFraction, expectedLogs: List<Log>, expectedSqrts: List<Sqrt>, expectedPis: List<Pi>) {
    val result = term.getSimplified()
    assertEquals(result.coefficient, expectedCoeff)
    assertEquals(result.logs.sorted(), expectedLogs.sorted())
    assertEquals(result.squareRoots.sorted(), expectedSqrts.sorted())
    assertEquals(result.pis.sorted(), expectedPis.sorted())
}
