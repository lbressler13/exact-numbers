package xyz.lbres.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import kotlin.test.assertEquals

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

private val one = ExactFraction.ONE

fun runConstructorTests() {
    runFullListConstructorTests()
    runComponentConstructorTests()
}

private fun runFullListConstructorTests() {
    // zero
    var term = Term.fromValues(ExactFraction.ZERO, emptyList())
    checkTermIsZero(term)

    term = Term.fromValues(ExactFraction.ZERO, listOf(pi, sqrtWholeEF, logWhole))
    checkTermIsZero(term)

    term = Term.fromValues(ExactFraction.THREE, listOf(Log.ZERO, pi, sqrtWholeEF, logWhole))
    checkTermIsZero(term)

    term = Term.fromValues(ExactFraction.THREE, listOf(Sqrt.ZERO, pi, sqrtWholeEF, logWhole))
    checkTermIsZero(term)

    // single type
    term = Term.fromValues(one, emptyList())
    checkTerm(term, ExactFraction.ONE, emptyList(), emptyList(), emptyList(), 0)

    term = Term.fromValues(ExactFraction(-17, 100043), emptyList())
    checkTerm(term, ExactFraction(-17, 100043), emptyList(), emptyList(), emptyList(), 0)

    term = Term.fromValues(one, listOf(logWhole))
    checkTerm(term, one, listOf(logWhole), emptyList(), emptyList(), 0)

    term = Term.fromValues(one, listOf(logWhole, logInverse))
    checkTerm(term, one, listOf(logWhole, logInverse), emptyList(), emptyList(), 0)

    term = Term.fromValues(one, listOf(sqrtPartialWhole))
    checkTerm(term, one, emptyList(), listOf(sqrtPartialWhole), emptyList(), 0)

    term = Term.fromValues(one, listOf(sqrtPartialWhole, sqrtWhole))
    checkTerm(term, one, emptyList(), listOf(sqrtPartialWhole, sqrtWhole), emptyList(), 0)

    term = Term.fromValues(one, listOf(pi))
    checkTerm(term, one, emptyList(), emptyList(), listOf(pi), 1)

    term = Term.fromValues(one, listOf(pi, pi))
    checkTerm(term, one, emptyList(), emptyList(), listOf(pi, pi), 2)

    term = Term.fromValues(one, listOf(pi, piInverse))
    checkTerm(term, one, emptyList(), emptyList(), listOf(pi, piInverse), 0)

    // multi type
    term = Term.fromValues(ExactFraction(-17, 100043), listOf(logWhole))
    checkTerm(term, ExactFraction(-17, 100043), listOf(logWhole), emptyList(), emptyList(), 0)

    term = Term.fromValues(ExactFraction.NEG_ONE, listOf(piInverse, logWhole))
    checkTerm(term, ExactFraction.NEG_ONE, listOf(logWhole), emptyList(), listOf(piInverse), -1)

    val logs = listOf(logChangeBase, logInverse, logDecimal)
    val sqrts = listOf(sqrtDecimal, Sqrt.ONE)
    val pis = listOf(pi, pi, piInverse)
    term = Term.fromValues(ExactFraction(-1, 5), logs + sqrts)
    checkTerm(term, ExactFraction(-1, 5), logs, sqrts, emptyList(), 0)

    term = Term.fromValues(ExactFraction(-1, 5), logs + pis)
    checkTerm(term, ExactFraction(-1, 5), logs, emptyList(), pis, 1)

    term = Term.fromValues(ExactFraction(-1, 5), sqrts + pis)
    checkTerm(term, ExactFraction(-1, 5), emptyList(), sqrts, pis, 1)

    term = Term.fromValues(one, sqrts + pis + logs)
    checkTerm(term, one, logs, sqrts, pis, 1)

    term = Term.fromValues(ExactFraction(-1, 5), sqrts + pis + logs)
    checkTerm(term, ExactFraction(-1, 5), logs, sqrts, pis, 1)
}

private fun runComponentConstructorTests() {
    // TODO finish these
    // zero
    var term = Term.fromValues(ExactFraction.ZERO, emptyList(), emptyList(), 0)
    checkTermIsZero(term)

    term = Term.fromValues(ExactFraction.ZERO, listOf(logWhole), listOf(sqrtWholeEF), 1)
    checkTermIsZero(term)

    term = Term.fromValues(ExactFraction.THREE, listOf(Log.ZERO, logWhole), listOf(sqrtWholeEF), 1)
    checkTermIsZero(term)

    term = Term.fromValues(ExactFraction.THREE, listOf(logWhole), listOf(Sqrt.ZERO, sqrtWholeEF), 1)
    checkTermIsZero(term)

    // single type

    // multi type

    val logs = listOf(logChangeBase, logInverse, logDecimal)
    val sqrts = listOf(sqrtDecimal, Sqrt.ONE)
    val pis = listOf(pi, pi)

    term = Term.fromValues(ExactFraction(-1, 5), logs, sqrts, 0)
    checkTerm(term, ExactFraction(-1, 5), logs, sqrts, emptyList(), 0)

    term = Term.fromValues(ExactFraction(-1, 5), logs, emptyList(), 2)
    checkTerm(term, ExactFraction(-1, 5), logs, emptyList(), pis, 2)

    term = Term.fromValues(ExactFraction(-1, 5), emptyList(), sqrts, 2)
    checkTerm(term, ExactFraction(-1, 5), emptyList(), sqrts, pis, 2)

    term = Term.fromValues(one, logs, sqrts, 2)
    checkTerm(term, one, logs, sqrts, pis, 2)

    term = Term.fromValues(ExactFraction(-1, 5), logs, sqrts, 2)
    checkTerm(term, ExactFraction(-1, 5), logs, sqrts, pis, 2)
}

private fun checkTerm(
    term: Term,
    expectedCoeff: ExactFraction,
    expectedLogs: List<Log>,
    expectedSqrts: List<Sqrt>,
    expectedPis: List<Pi>,
    expectedPiCount: Int
) {
    assertEquals(expectedCoeff, term.coefficient)
    assertEquals(expectedLogs, term.logs)
    assertEquals(expectedSqrts, term.squareRoots)
    assertEquals(expectedPis, term.pis)
    assertEquals(expectedPiCount, term.piCount)
    // TODO check irrationals list
}

private fun checkTermIsZero(term: Term) = checkTerm(term, ExactFraction.ZERO, emptyList(), emptyList(), emptyList(), 0)
