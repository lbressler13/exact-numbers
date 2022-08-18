package expressions.additive

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.pi.Pi
import exactnumbers.irrationals.sqrt.Sqrt
import expressions.term.Term
import kotlin.test.assertEquals

fun runGetSimplifiedTests() {
    // zero
    var expected = listOf(termZero)

    var terms = listOf(termZero)
    runSingleGetSimplifiedTest(terms, expected)

    terms = listOf(termZero, termZero, termZero)
    runSingleGetSimplifiedTest(terms, expected)

    terms = listOf(termZero, term1, -term1)
    runSingleGetSimplifiedTest(terms, expected)

    terms = listOf(term1, -term1)
    runSingleGetSimplifiedTest(terms, expected)

    terms = listOf(term1, -term1, -term2, term2)
    runSingleGetSimplifiedTest(terms, expected)

    // single term
    terms = listOf(term1)
    expected = listOf(term1)
    runSingleGetSimplifiedTest(terms, expected)

    terms = listOf(term3)
    expected = listOf(term3)
    runSingleGetSimplifiedTest(terms, expected)

    terms = listOf(term4)
    expected = listOf(term4)
    runSingleGetSimplifiedTest(terms, expected)

    // multiple terms
    terms = listOf(Term(ExactFraction.EIGHT, listOf()), Term(ExactFraction.ONE, listOf()))
    expected = listOf(Term(ExactFraction.NINE, listOf()))
    runSingleGetSimplifiedTest(terms, expected)

    terms = listOf(Term(ExactFraction.EIGHT, listOf()), Term(ExactFraction.ONE, listOf()))
    expected = listOf(Term(ExactFraction.NINE, listOf()))
    runSingleGetSimplifiedTest(terms, expected)

    terms = listOf(Term(ExactFraction.HALF, listOf(Pi())), Term(ExactFraction(-3, 4), listOf(Pi())))
    expected = listOf(Term(ExactFraction(-1, 4), listOf(Pi())))
    runSingleGetSimplifiedTest(terms, expected)

    terms = listOf(
        Term(ExactFraction.HALF, listOf(Pi())), Term(ExactFraction(-3, 4), listOf(Pi())),
        term1
    )
    expected = listOf(Term(ExactFraction(-1, 4), listOf(Pi())), term1)
    runSingleGetSimplifiedTest(terms, expected)

    terms = listOf(
        Term(ExactFraction.HALF, listOf(Pi())), Term(ExactFraction(-3, 4), listOf(Pi())),
        term1, -term1
    )
    expected = listOf(Term(ExactFraction(-1, 4), listOf(Pi())))
    runSingleGetSimplifiedTest(terms, expected)

    terms = listOf(
        Term(ExactFraction(17), term1.numbers), Term(ExactFraction(-12), term1.numbers),
        Term(ExactFraction.ONE, listOf(Sqrt(18))), Term(ExactFraction.FIVE, listOf(Sqrt(2)))
    )
    expected = listOf(Term(ExactFraction.FIVE, term1.numbers), Term(ExactFraction.EIGHT, listOf(Sqrt(2))))
    runSingleGetSimplifiedTest(terms, expected)

    // no simplification
    terms = listOf(term1, term2, term3)
    expected = listOf(term1, term2, term3)
    runSingleGetSimplifiedTest(terms, expected)

    terms = listOf(term1, term4, term5)
    expected = listOf(term1, term4, term5)
    runSingleGetSimplifiedTest(terms, expected)
}

private fun runSingleGetSimplifiedTest(initialTerms: List<Term>, expectedTerms: List<Term>) {
    val expr = AdditiveExpression(initialTerms)
    val expected = expectedTerms.sortedBy { it.getValue() }
    val actual = expr.getSimplified().terms.sortedBy { it.getValue() }
    assertEquals(expected, actual)
}
