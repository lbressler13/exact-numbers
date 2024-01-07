package xyz.lbres.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumber
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.kotlinutils.collection.ext.toConstMultiSet
import xyz.lbres.testutils.TestNumber
import java.math.BigDecimal
import kotlin.test.assertEquals

private val logNum1 = Log(ExactFraction(15, 4))
private val logNum2 = Log(8, 7)
private val sqrt = Sqrt(ExactFraction(20, 33))
private val testNumber1 = TestNumber(ExactFraction(3, 4))
private val testNumber2 = TestNumber(ExactFraction.SEVEN)
private val one = ExactFraction.ONE

fun runGetSimplifiedTests() {
    // simplified
    var term = Term.fromValues(ExactFraction.EIGHT, listOf(Pi(), Pi(true)))
    var expectedCoeff = ExactFraction.EIGHT
    runSingleGetSimplifiedTest(term, expectedCoeff, emptyList())

    term = Term.fromValues(ExactFraction(-3, 2), listOf(logNum1, Pi(), Pi(true), Pi()))
    expectedCoeff = ExactFraction(-3, 2)
    var expectedIrrationals: List<IrrationalNumber<*>> = listOf(logNum1, Pi())
    runSingleGetSimplifiedTest(term, expectedCoeff, expectedIrrationals)

    term = Term.fromValues(ExactFraction.HALF, listOf(Log.ONE, logNum1, testNumber2, testNumber2))
    expectedCoeff = ExactFraction(49, 2)
    expectedIrrationals = listOf(logNum1)
    runSingleGetSimplifiedTest(term, expectedCoeff, expectedIrrationals)

    term = Term.fromValues(-ExactFraction.HALF, listOf(Log.ONE, Pi(true)))
    expectedCoeff = -ExactFraction.HALF
    expectedIrrationals = listOf(Pi(true))
    runSingleGetSimplifiedTest(term, expectedCoeff, expectedIrrationals)

    term = Term.fromValues(ExactFraction.TEN, listOf(Sqrt.ONE, sqrt))
    expectedCoeff = ExactFraction(20)
    expectedIrrationals = listOf(Sqrt(ExactFraction(5, 33)))
    runSingleGetSimplifiedTest(term, expectedCoeff, expectedIrrationals)

    term = Term.fromValues(ExactFraction.TWO, listOf(Sqrt(64), Sqrt(ExactFraction(75, 98)), Sqrt(26)))
    expectedCoeff = ExactFraction(80, 7)
    expectedIrrationals = listOf(Sqrt(ExactFraction(39)))
    runSingleGetSimplifiedTest(term, expectedCoeff, expectedIrrationals)

    term = Term.fromValues(
        ExactFraction(18, 5),
        listOf(logNum2, logNum2, logNum1, logNum2.inverse(), Pi(true), Pi(true), Pi(true), Pi())
    )
    expectedCoeff = ExactFraction(18, 5)
    expectedIrrationals = listOf(logNum2, logNum1, Pi(true), Pi(true))
    runSingleGetSimplifiedTest(term, expectedCoeff, expectedIrrationals)

    term = Term.fromValues(ExactFraction.FOUR, listOf(Log(100), Sqrt(9), testNumber1, Sqrt(ExactFraction(1, 4))))
    expectedCoeff = ExactFraction(12)
    runSingleGetSimplifiedTest(term, expectedCoeff, listOf(testNumber1))

    term = Term.fromValues(-ExactFraction.EIGHT, listOf(Sqrt(ExactFraction(27, 98)), Pi(true)))
    expectedCoeff = ExactFraction(-24, 7)
    expectedIrrationals = listOf(Sqrt(ExactFraction(3, 2)), Pi(true))
    runSingleGetSimplifiedTest(term, expectedCoeff, expectedIrrationals)

    term = Term.fromValues(ExactFraction(20), listOf(Log(ExactFraction(1, 27), 3).inverse()))
    expectedCoeff = ExactFraction(-20, 3)
    runSingleGetSimplifiedTest(term, expectedCoeff, emptyList())

    term = Term.fromValues(
        ExactFraction(3, 5),
        listOf(
            Log(4),
            Log(ExactFraction(625), 5),
            Log(1000, 12),
            Log(ExactFraction(1, 16), 4).inverse(),
            Sqrt(ExactFraction(18, 7)),
            Sqrt(25),
            Sqrt(ExactFraction(13, 3)),
            Pi()
        )
    )
    expectedCoeff = ExactFraction(-6)
    expectedIrrationals = listOf(Log(4), Log(1000, 12), Sqrt(ExactFraction(78, 7)), Pi())
    runSingleGetSimplifiedTest(term, expectedCoeff, expectedIrrationals)

    // no changes
    term = Term.fromValues(ExactFraction.EIGHT, emptyList())
    expectedCoeff = ExactFraction.EIGHT
    runSingleGetSimplifiedTest(term, expectedCoeff, emptyList())

    term = Term.fromValues(ExactFraction.EIGHT, listOf(logNum1))
    expectedCoeff = ExactFraction.EIGHT
    expectedIrrationals = listOf(logNum1)
    runSingleGetSimplifiedTest(term, expectedCoeff, expectedIrrationals)

    term = Term.fromValues(ExactFraction.EIGHT, listOf(Sqrt(ExactFraction(1, 46))))
    expectedCoeff = ExactFraction.EIGHT
    expectedIrrationals = listOf(Sqrt(ExactFraction(1, 46)))
    runSingleGetSimplifiedTest(term, expectedCoeff, expectedIrrationals)

    term = Term.fromValues(ExactFraction(-5, 6), listOf(Pi(true), testNumber1))
    expectedCoeff = ExactFraction(-5, 6)
    expectedIrrationals = listOf(Pi(true), testNumber1)
    runSingleGetSimplifiedTest(term, expectedCoeff, expectedIrrationals)

    term = Term.fromValues(ExactFraction.SEVEN, listOf(logNum1, logNum1, logNum2.inverse(), Sqrt(5), Pi(), Pi()))
    expectedCoeff = ExactFraction.SEVEN
    expectedIrrationals = listOf(logNum1, logNum1, logNum2.inverse(), Sqrt(5), Pi(), Pi())
    runSingleGetSimplifiedTest(term, expectedCoeff, expectedIrrationals)
}

fun runGetValueTests() {
    // just number
    var term = Term.ZERO
    var expected = BigDecimal.ZERO
    assertEquals(expected, term.getValue())

    term = Term.fromValues(ExactFraction.EIGHT, emptyList())
    expected = BigDecimal("8")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(ExactFraction(-1, 3), emptyList())
    expected = BigDecimal("-0.33333333333333333333")
    assertEquals(expected, term.getValue())

    // just logs
    term = Term.fromValues(one, listOf(logNum1, logNum1.inverse()))
    expected = BigDecimal.ONE
    assertEquals(expected, term.getValue())

    term = Term.fromValues(one, listOf(Log(3333)))
    expected = BigDecimal("3.52283531366053")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(one, listOf(logNum1, logNum2))
    expected = BigDecimal("0.61342218956802803344500481172832")
    assertEquals(expected, term.getValue())

    // just pi
    term = Term.fromValues(one, listOf(Pi(), Pi(true)))
    expected = BigDecimal.ONE
    assertEquals(expected, term.getValue())

    term = Term.fromValues(one, listOf(Pi(true)))
    expected = BigDecimal("0.31830988618379069570")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(one, listOf(Pi(), Pi(), Pi()))
    expected = BigDecimal("31.006276680299813114880451174049119330924860257")
    assertEquals(expected, term.getValue())

    // just sqrt
    term = Term.fromValues(one, listOf(Sqrt(ExactFraction(9, 5))))
    expected = BigDecimal("1.34164078649987381784")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(one, listOf(Sqrt(121)))
    expected = BigDecimal("11")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(one, listOf(Sqrt(12), Sqrt(ExactFraction(9, 25))))
    expected = BigDecimal("2.0784609690826527522")
    assertEquals(expected, term.getValue())

    // combination
    term = Term.fromValues(ExactFraction(-8, 3), listOf(Pi()))
    expected = BigDecimal("-8.3775804095727813333")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(ExactFraction.FOUR, listOf(Sqrt.ONE, testNumber2))
    expected = BigDecimal("28")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(ExactFraction.EIGHT, listOf(Log(9), Sqrt(49), Sqrt(2)))
    expected = BigDecimal("75.57215112395364893851321831545508672")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(ExactFraction.SEVEN, listOf(Log(ExactFraction(6, 7)), Log(40), Pi()))
    expected = BigDecimal("-2.35861167086684457383417423198393663398251286036")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(ExactFraction.HALF, listOf(Log(4, 2).inverse(), Log(123456789), Pi(true)))
    expected = BigDecimal("0.64390230285929702583103243749028475")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(ExactFraction(3, 11), listOf(Log(5, 2), Sqrt(122), Pi()))
    expected = BigDecimal("21.973899001484265398")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(one, listOf(testNumber2, testNumber2, testNumber1, Log(5, 2)))
    expected = BigDecimal("85.33085748711055350")
    assertEquals(expected, term.getValue())
}

private fun runSingleGetSimplifiedTest(term: Term, expectedCoeff: ExactFraction, expectedIrrationals: List<IrrationalNumber<*>>) {
    val result = term.getSimplified()
    assertEquals(result.coefficient, expectedCoeff)
    assertEquals(expectedIrrationals.toConstMultiSet(), result.irrationals.toConstMultiSet())
}
