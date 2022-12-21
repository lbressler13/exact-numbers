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

internal fun runConstructorTests() {
    runSingleValueTests()
    runSingleTypeTests()
    runTwoTypeTests()
    runThreeTypeTests()
    runAllTypeTests()
}

private fun runSingleValueTests() {
    // zero
    var term = Term.fromValue(ExactFraction.ZERO)
    checkTermIsZero(term)

    term = Term.fromValue(Log.ZERO)
    checkTermIsZero(term)

    term = Term.fromValue(Sqrt.ZERO)
    checkTermIsZero(term)

    // nonzero
    term = Term.fromValue(ExactFraction.ONE)
    checkTerm(term, ExactFraction.ONE, emptyList(), emptyList(), emptyList(), 0)

    term = Term.fromValue(ExactFraction(-17, 100043))
    checkTerm(term, ExactFraction(-17, 100043), emptyList(), emptyList(), emptyList(), 0)

    term = Term.fromValue(logWhole)
    checkTerm(term, one, listOf(logWhole), emptyList(), emptyList(), 0)

    term = Term.fromValue(logInverse)
    checkTerm(term, one, listOf(logInverse), emptyList(), emptyList(), 0)

    term = Term.fromValue(sqrtWholeEF)
    checkTerm(term, one, emptyList(), listOf(sqrtWholeEF), emptyList(), 0)

    term = Term.fromValue(sqrtDecimal)
    checkTerm(term, one, emptyList(), listOf(sqrtDecimal), emptyList(), 0)

    term = Term.fromValue(pi)
    checkTerm(term, one, emptyList(), emptyList(), listOf(pi), 1)

    term = Term.fromValue(piInverse)
    checkTerm(term, one, emptyList(), emptyList(), listOf(piInverse), -1)
}

private fun runSingleTypeTests() {
    // zero
    var term = Term.fromValues(listOf(Log.ZERO))
    checkTermIsZero(term)

    term = Term.fromValues(listOf(Log.ZERO, logDecimal))
    checkTermIsZero(term)

    term = Term.fromValues(listOf(logInverse, Log.ZERO, logWhole, logChangeBase))
    checkTermIsZero(term)

    term = Term.fromValues(listOf(Sqrt.ZERO))
    checkTermIsZero(term)

    term = Term.fromValues(listOf(sqrtPartialWhole, Sqrt.ZERO))
    checkTermIsZero(term)

    term = Term.fromValues(listOf(Sqrt.ZERO, sqrtWhole, sqrtWholeEF))
    checkTermIsZero(term)

    // nonzero
    var logs = listOf(logWhole)
    term = Term.fromValues(logs)
    checkTerm(term, one, logs, emptyList(), emptyList(), 0)

    logs = listOf(logWhole, logWhole)
    term = Term.fromValues(logs)
    checkTerm(term, one, logs, emptyList(), emptyList(), 0)

    logs = listOf(logInverse, logDecimal, logDecimal.inverse())
    term = Term.fromValues(logs)
    checkTerm(term, one, logs, emptyList(), emptyList(), 0)

    var sqrts = listOf(sqrtDecimal)
    term = Term.fromValues(sqrts)
    checkTerm(term, one, emptyList(), sqrts, emptyList(), 0)

    sqrts = listOf(sqrtDecimal, sqrtDecimal)
    term = Term.fromValues(sqrts)
    checkTerm(term, one, emptyList(), sqrts, emptyList(), 0)

    sqrts = listOf(sqrtDecimal, sqrtWhole, sqrtPartialWhole)
    term = Term.fromValues(sqrts)
    checkTerm(term, one, emptyList(), sqrts, emptyList(), 0)

    var pis = listOf(pi)
    term = Term.fromValues(pis)
    checkTerm(term, one, emptyList(), emptyList(), pis, 1)

    pis = listOf(piInverse)
    term = Term.fromValues(pis)
    checkTerm(term, one, emptyList(), emptyList(), pis, -1)

    pis = listOf(pi, pi, pi)
    term = Term.fromValues(pis)
    checkTerm(term, one, emptyList(), emptyList(), pis, 3)

    pis = listOf(piInverse, piInverse)
    term = Term.fromValues(pis)
    checkTerm(term, one, emptyList(), emptyList(), pis, -2)

    pis = listOf(pi, piInverse, piInverse, pi)
    term = Term.fromValues(pis)
    checkTerm(term, one, emptyList(), emptyList(), pis, 0)

    pis = listOf(pi, piInverse, piInverse, pi, pi)
    term = Term.fromValues(pis)
    checkTerm(term, one, emptyList(), emptyList(), pis, 1)

    pis = listOf(pi, piInverse, piInverse, pi, piInverse)
    term = Term.fromValues(pis)
    checkTerm(term, one, emptyList(), emptyList(), pis, -1)
}

private fun runTwoTypeTests() {
    // zero
    var term = Term.fromValues(ExactFraction.ZERO, listOf<Log>())
    checkTermIsZero(term)

    term = Term.fromValues(ExactFraction.ZERO, listOf(logWhole, logChangeBase))
    checkTermIsZero(term)

    term = Term.fromValues(ExactFraction.ZERO, listOf(sqrtWholeEF, sqrtPartialWhole))
    checkTermIsZero(term)

    term = Term.fromValues(ExactFraction.ZERO, listOf(pi, piInverse))
    checkTermIsZero(term)

    term = Term.fromValues(ExactFraction.TEN, listOf(Log.ZERO, logDecimal))
    checkTermIsZero(term)

    term = Term.fromValues(ExactFraction.HALF, listOf(Sqrt.ZERO))
    checkTermIsZero(term)

    term = Term.fromValues(listOf(Log.ZERO), listOf(sqrtWholeEF))
    checkTermIsZero(term)

    term = Term.fromValues(listOf(Log.ZERO), listOf(pi))
    checkTermIsZero(term)

    term = Term.fromValues(listOf(Sqrt.ZERO), listOf(pi))
    checkTermIsZero(term)

    // nonzero
    term = Term.fromValues(ExactFraction.HALF, emptyList<Log>())
    checkTerm(term, ExactFraction.HALF, emptyList(), emptyList(), emptyList(), 0)

    val logs = listOf(logWhole, logChangeBase, logWhole)
    term = Term.fromValues(ExactFraction(11, 3), logs)
    checkTerm(term, ExactFraction(11, 3), logs, emptyList(), emptyList(), 0)

    val sqrts = listOf(sqrtDecimal, sqrtPartialWhole, sqrtWholeEF, Sqrt.ONE, sqrtDecimal)
    term = Term.fromValues(ExactFraction(11, 3), sqrts)
    checkTerm(term, ExactFraction(11, 3), emptyList(), sqrts, emptyList(), 0)

    val pis = listOf(pi, piInverse, piInverse)
    term = Term.fromValues(ExactFraction(-2, 7), pis)
    checkTerm(term, ExactFraction(-2, 7), emptyList(), emptyList(), pis, -1)

    term = Term.fromValues(logs, sqrts)
    checkTerm(term, one, logs, sqrts, emptyList(), 0)

    term = Term.fromValues(logs, pis)
    checkTerm(term, one, logs, emptyList(), pis, -1)

    term = Term.fromValues(sqrts, pis)
    checkTerm(term, one, emptyList(), sqrts, pis, -1)
}

private fun runThreeTypeTests() {
    // zero
    var term = Term.fromValues(ExactFraction.ZERO, listOf(logWhole, logDecimal), listOf(sqrtDecimal))
    checkTermIsZero(term)

    term = Term.fromValues(ExactFraction.ZERO, listOf(logWhole, logDecimal), listOf(pi, pi))
    checkTermIsZero(term)

    term = Term.fromValues(ExactFraction.ZERO, listOf(sqrtDecimal), listOf(pi, pi))
    checkTermIsZero(term)

    term = Term.fromValues(listOf(Log.ZERO, logChangeBase), listOf(sqrtDecimal, sqrtPartialWhole), listOf(pi))
    checkTermIsZero(term)

    term = Term.fromValues(listOf(logInverse, logChangeBase), listOf(Sqrt.ZERO, sqrtPartialWhole), listOf(pi))
    checkTermIsZero(term)

    // nonzero
    term = Term.fromValues(emptyList(), emptyList(), emptyList())
    checkTerm(term, one, emptyList(), emptyList(), emptyList(), 0)

    val logs = listOf(logWhole, logInverse, logDecimal)
    val sqrts = listOf(sqrtPartialWhole, Sqrt.ONE)
    val pis = listOf(pi, pi)

    term = Term.fromValues(ExactFraction(-1, 5), logs, sqrts)
    checkTerm(term, ExactFraction(-1, 5), logs, sqrts, emptyList(), 0)

    term = Term.fromValues(ExactFraction(-1, 5), logs, pis)
    checkTerm(term, ExactFraction(-1, 5), logs, emptyList(), pis, 2)

    term = Term.fromValues(ExactFraction(-1, 5), sqrts, pis)
    checkTerm(term, ExactFraction(-1, 5), emptyList(), sqrts, pis, 2)

    term = Term.fromValues(logs, sqrts, pis)
    checkTerm(term, one, logs, sqrts, pis, 2)
}

private fun runAllTypeTests() {
    val logs = listOf(logWhole, logInverse, logDecimal)
    val sqrts = listOf(sqrtPartialWhole, Sqrt.ONE)
    val pis = listOf(pi, pi, piInverse)

    // zero
    var term = Term.fromValues(ExactFraction.ZERO, logs, sqrts, pis)
    checkTermIsZero(term)

    term = Term.fromValues(ExactFraction.EIGHT, listOf(Log.ZERO, Log.ONE), sqrts, pis)
    checkTermIsZero(term)

    term = Term.fromValues(ExactFraction.NEG_ONE, logs, listOf(sqrtWholeEF, Sqrt.ZERO), pis)
    checkTermIsZero(term)

    // nonzero
    term = Term.fromValues(ExactFraction(18), logs, sqrts, pis)
    checkTerm(term, ExactFraction(18), logs, sqrts, pis, 1)
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
}

private fun checkTermIsZero(term: Term) = checkTerm(term, ExactFraction.ZERO, emptyList(), emptyList(), emptyList(), 0)
