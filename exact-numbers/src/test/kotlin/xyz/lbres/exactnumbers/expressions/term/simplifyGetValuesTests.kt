package xyz.lbres.exactnumbers.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.exactnumbers.testutils.TestNumber
import java.math.BigDecimal
import kotlin.test.assertEquals

private val log1 = Log(ExactFraction(15, 4))
private val log2 = Log(8, 7)
private val sqrt = Sqrt(ExactFraction(20, 33))
private val testNumber1 = TestNumber(ExactFraction(3, 4))
private val testNumber2 = TestNumber(ExactFraction.SEVEN)
private val pi = Pi()
private val piInverse = Pi().inverse()
private val one = ExactFraction.ONE

fun runCommonSimplifyTests(simplify: (Term) -> Term) {
    // simplified
    var term = Term.fromValues(ExactFraction.EIGHT, listOf(pi, piInverse))
    var result = simplify(term)
    checkTerm(result, ExactFraction.EIGHT)

    term = Term.fromValues(ExactFraction(-3, 2), listOf(log1, pi, piInverse, pi))
    result = simplify(term)
    var expectedFactors = listOf(log1, pi)
    checkTerm(result, ExactFraction(-3, 2), expectedFactors)

    term = Term.fromValues(ExactFraction.HALF, listOf(Log.ONE, log1, testNumber2, testNumber2))
    result = simplify(term)
    expectedFactors = listOf(log1)
    checkTerm(result, ExactFraction(49, 2), expectedFactors)

    term = Term.fromValues(-ExactFraction.HALF, listOf(Log.ONE, piInverse))
    result = simplify(term)
    expectedFactors = listOf(piInverse)
    checkTerm(result, -ExactFraction.HALF, expectedFactors)

    term = Term.fromValues(ExactFraction.TEN, listOf(Sqrt.ONE, sqrt, testNumber1, testNumber1, testNumber1.inverse(), testNumber1.inverse(), testNumber1.inverse()))
    result = simplify(term)
    expectedFactors = listOf(Sqrt(ExactFraction(5, 33)), testNumber1.inverse())
    checkTerm(result, ExactFraction(20), expectedFactors)

    term = Term.fromValues(ExactFraction.TWO, listOf(Sqrt(64), Sqrt(ExactFraction(75, 98)), Sqrt(26)))
    result = simplify(term)
    expectedFactors = listOf(Sqrt(ExactFraction(39)))
    checkTerm(result, ExactFraction(80, 7), expectedFactors)

    term = Term.fromValues(
        ExactFraction(18, 5),
        listOf(log2, log2, log1, log2.inverse(), piInverse, piInverse, piInverse, pi)
    )
    result = simplify(term)
    val logs = listOf(log2, log1)
    val pis = listOf(piInverse, piInverse)
    checkTerm(result, ExactFraction(18, 5), logs + pis)

    term = Term.fromValues(ExactFraction.FOUR, listOf(Log(100), Sqrt(9), testNumber1, Sqrt(ExactFraction(1, 4))))
    result = simplify(term)
    checkTerm(result, ExactFraction(12), listOf(testNumber1))

    term = Term.fromValues(-ExactFraction.EIGHT, listOf(Sqrt(ExactFraction(27, 98)), piInverse))
    result = simplify(term)
    expectedFactors = listOf(Sqrt(ExactFraction(3, 2)), piInverse)
    checkTerm(result, ExactFraction(-24, 7), expectedFactors)

    term = Term.fromValues(ExactFraction(20), listOf(Log(ExactFraction(1, 27), 3).inverse()))
    result = simplify(term)
    checkTerm(result, ExactFraction(-20, 3))

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
            pi
        )
    )
    result = simplify(term)
    expectedFactors = listOf(Log(4), Log(1000, 12), Sqrt(ExactFraction(78, 7)), pi)
    checkTerm(result, ExactFraction(-6), expectedFactors)

    term = Term.fromValues(
        ExactFraction(4, 7),
        listOf(testNumber2, pi, piInverse, Log.ONE, Sqrt(ExactFraction(9, 49)), Log(1000), Log(ExactFraction(1, 32), 2))
    )
    result = simplify(term)
    checkTerm(result, ExactFraction(-180, 7))

    // no changes
    term = Term.fromValues(ExactFraction.EIGHT, emptyList())
    result = simplify(term)
    checkTerm(result, ExactFraction.EIGHT)

    term = Term.fromValues(ExactFraction.EIGHT, listOf(log1))
    result = simplify(term)
    expectedFactors = listOf(log1)
    checkTerm(result, ExactFraction.EIGHT, expectedFactors)

    term = Term.fromValues(ExactFraction.EIGHT, listOf(Sqrt(ExactFraction(1, 46))))
    result = simplify(term)
    expectedFactors = listOf(Sqrt(ExactFraction(1, 46)))
    checkTerm(result, ExactFraction.EIGHT, expectedFactors)

    term = Term.fromValues(ExactFraction(-5, 6), listOf(piInverse, testNumber1))
    result = simplify(term)
    expectedFactors = listOf(piInverse, testNumber1)
    checkTerm(result, ExactFraction(-5, 6), expectedFactors)

    term = Term.fromValues(ExactFraction.SEVEN, listOf(log1, log1, log2.inverse(), Sqrt(5), pi, pi))
    result = simplify(term)
    expectedFactors = listOf(log1, log1, log2.inverse(), Sqrt(5), pi, pi)
    checkTerm(result, ExactFraction.SEVEN, expectedFactors)
}

fun runGetValueTests() {
    // just coefficient
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
    term = Term.fromValues(one, listOf(log1, log1.inverse()))
    expected = BigDecimal.ONE
    assertEquals(expected, term.getValue())

    term = Term.fromValues(one, listOf(Log(3333)))
    expected = BigDecimal("3.52283531366053")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(one, listOf(log1, log2))
    expected = BigDecimal("0.61342218956802803344500481172832")
    assertEquals(expected, term.getValue())

    // just pi
    term = Term.fromValues(one, listOf(pi, piInverse))
    expected = BigDecimal.ONE
    assertEquals(expected, term.getValue())

    term = Term.fromValues(one, listOf(piInverse))
    expected = BigDecimal("0.31830988618379069570")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(one, listOf(pi, pi, pi))
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
    term = Term.fromValues(ExactFraction(-8, 3), listOf(pi))
    expected = BigDecimal("-8.3775804095727813333")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(ExactFraction.FOUR, listOf(Sqrt.ONE, testNumber2))
    expected = BigDecimal("28")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(ExactFraction.EIGHT, listOf(Log(9), Sqrt(49), Sqrt(2)))
    expected = BigDecimal("75.57215112395364893851321831545508672")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(ExactFraction.SEVEN, listOf(Log(ExactFraction(6, 7)), Log(40), pi))
    expected = BigDecimal("-2.35861167086684457383417423198393663398251286036")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(ExactFraction.HALF, listOf(Log(4, 2).inverse(), Log(123456789), piInverse))
    expected = BigDecimal("0.64390230285929702583103243749028475")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(ExactFraction(3, 11), listOf(Log(5, 2), Sqrt(122), pi))
    expected = BigDecimal("21.973899001484265398")
    assertEquals(expected, term.getValue())

    term = Term.fromValues(one, listOf(testNumber2, testNumber2, testNumber1, Log(5, 2)))
    expected = BigDecimal("85.33085748711055350")
    assertEquals(expected, term.getValue())
}
