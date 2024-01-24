package xyz.lbres.exactnumbers.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.IrrationalNumber
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.exactnumbers.testutils.TestNumber

private val logWhole = Log(1000)
private val logDecimal = Log(ExactFraction(25, 121))
private val logChangeBase = Log(8, 7)
private val logInverse = Log(ExactFraction(19, 33)).inverse()

private val sqrtWhole = Sqrt(289)
private val sqrtPartialWhole = Sqrt(8)
private val sqrtWholeEF = Sqrt(ExactFraction(9, 25))
private val sqrtDecimal = Sqrt(11)

private val pi = Pi()
private val piInverse = Pi().inverse()

private val testNumber1 = TestNumber(ExactFraction(5, 6))
private val testNumber2 = TestNumber(ExactFraction.SEVEN)

private val one = ExactFraction.ONE

fun runConstructorTests() {
    runFactorsConstructorTests()
    runComponentConstructorTests()
}

private fun runFactorsConstructorTests() {
    // zero
    var term = Term.fromValues(ExactFraction.ZERO, emptyList())
    checkTerm(term, ExactFraction.ZERO)

    term = Term.fromValues(ExactFraction.ZERO, listOf(pi, sqrtWholeEF, logWhole))
    checkTerm(term, ExactFraction.ZERO)

    term = Term.fromValues(ExactFraction.THREE, listOf(Log.ZERO, pi, sqrtWholeEF, logWhole))
    checkTerm(term, ExactFraction.ZERO)

    term = Term.fromValues(ExactFraction.THREE, listOf(Sqrt.ZERO, pi, sqrtWholeEF, testNumber1, logWhole))
    checkTerm(term, ExactFraction.ZERO)

    term = Term.fromValues(ExactFraction.THREE, listOf(sqrtPartialWhole, pi, sqrtWholeEF, TestNumber(ExactFraction.ZERO), logWhole))
    checkTerm(term, ExactFraction.ZERO)

    // single type
    term = Term.fromValues(one, emptyList())
    checkTerm(term, ExactFraction.ONE)

    term = Term.fromValues(ExactFraction(-17, 100043), emptyList())
    checkTerm(term, ExactFraction(-17, 100043))

    term = Term.fromValues(one, listOf(logWhole, logInverse))
    var factors: List<IrrationalNumber<*>> = listOf(logWhole, logInverse)
    checkTerm(term, one, factors)

    term = Term.fromValues(one, listOf(sqrtWhole))
    checkTerm(term, one, listOf(sqrtWhole))

    term = Term.fromValues(one, listOf(pi))
    checkTerm(term, one, listOf(pi))

    term = Term.fromValues(one, listOf(pi, piInverse))
    factors = listOf(pi, piInverse)
    checkTerm(term, one, factors)

    // multi type
    term = Term.fromValues(ExactFraction(-17, 100043), listOf(logWhole))
    checkTerm(term, ExactFraction(-17, 100043), listOf(logWhole))

    term = Term.fromValues(ExactFraction.NEG_ONE, listOf(piInverse, logWhole))
    factors = listOf(piInverse, logWhole)
    checkTerm(term, ExactFraction.NEG_ONE, factors)

    factors = listOf(logChangeBase, logInverse, logDecimal, sqrtDecimal, Sqrt.ONE)
    term = Term.fromValues(ExactFraction(-1, 5), factors)
    checkTerm(term, ExactFraction(-1, 5), factors)

    factors = listOf(sqrtDecimal, Sqrt.ONE, pi, pi, piInverse)
    term = Term.fromValues(ExactFraction(-1, 5), factors)
    checkTerm(term, ExactFraction(-1, 5), factors)

    factors = listOf(pi, pi, piInverse, logChangeBase, logInverse, logDecimal, testNumber1, testNumber2)
    term = Term.fromValues(one, factors)
    checkTerm(term, one, factors)
}

private fun runComponentConstructorTests() {
    // zero
    var term = Term.fromValues(ExactFraction.ZERO, emptyList(), emptyList(), 0)
    checkTerm(term, ExactFraction.ZERO)

    term = Term.fromValues(ExactFraction.ZERO, listOf(logWhole), listOf(sqrtWholeEF), 1)
    checkTerm(term, ExactFraction.ZERO)

    term = Term.fromValues(ExactFraction.THREE, listOf(Log.ZERO, logWhole), listOf(sqrtWholeEF), 1)
    checkTerm(term, ExactFraction.ZERO)

    term = Term.fromValues(ExactFraction.THREE, listOf(logWhole), listOf(Sqrt.ZERO, sqrtWholeEF), 1)
    checkTerm(term, ExactFraction.ZERO)

    // single type
    term = Term.fromValues(one, emptyList(), emptyList(), 0)
    checkTerm(term, ExactFraction.ONE)

    term = Term.fromValues(ExactFraction(-17, 100043), emptyList(), emptyList(), 0)
    checkTerm(term, ExactFraction(-17, 100043))

    term = Term.fromValues(one, listOf(logWhole, logInverse), emptyList(), 0)
    checkTerm(term, one, listOf(logWhole, logInverse))

    term = Term.fromValues(one, emptyList(), listOf(sqrtPartialWhole), 0)
    checkTerm(term, one, listOf(sqrtPartialWhole))

    term = Term.fromValues(one, emptyList(), emptyList(), -2)
    checkTerm(term, one, listOf(piInverse, piInverse))

    // multi type
    term = Term.fromValues(ExactFraction(-17, 100043), listOf(logWhole), emptyList(), 0)
    checkTerm(term, ExactFraction(-17, 100043), listOf(logWhole))

    term = Term.fromValues(ExactFraction.NEG_ONE, emptyList(), emptyList(), -1)
    checkTerm(term, ExactFraction.NEG_ONE, listOf(piInverse))

    val logs = listOf(logChangeBase, logInverse, logDecimal)
    val sqrts = listOf(sqrtDecimal, Sqrt.ONE)
    val pis = listOf(pi, pi)

    term = Term.fromValues(ExactFraction(-1, 5), logs, sqrts, 0)
    checkTerm(term, ExactFraction(-1, 5), logs + sqrts)

    term = Term.fromValues(ExactFraction(-1, 5), logs, emptyList(), 2)
    checkTerm(term, ExactFraction(-1, 5), logs + pis)

    term = Term.fromValues(ExactFraction(-1, 5), emptyList(), sqrts, 2)
    checkTerm(term, ExactFraction(-1, 5), sqrts + pis)

    term = Term.fromValues(ExactFraction(-1, 5), logs, sqrts, 2)
    checkTerm(term, ExactFraction(-1, 5), logs + sqrts + pis)
}
