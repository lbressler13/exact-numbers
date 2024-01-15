package xyz.lbres.exactnumbers.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
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
    runFullListConstructorTests()
    runComponentConstructorTests()
}

private fun runFullListConstructorTests() {
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
    var logs = listOf(logWhole, logInverse)
    checkTerm(term, one, logs, logs = logs)

    term = Term.fromValues(one, listOf(sqrtWhole))
    checkTerm(term, one, listOf(sqrtWhole), sqrts = listOf(sqrtWhole))

    term = Term.fromValues(one, listOf(pi))
    checkTerm(term, one, listOf(pi), pis = listOf(pi), piCount = 1)

    term = Term.fromValues(one, listOf(pi, piInverse))
    var pis = listOf(pi, piInverse)
    checkTerm(term, one, pis, pis = pis, piCount = 0)

    // multi type
    term = Term.fromValues(ExactFraction(-17, 100043), listOf(logWhole))
    checkTerm(term, ExactFraction(-17, 100043), listOf(logWhole), logs = listOf(logWhole))

    term = Term.fromValues(ExactFraction.NEG_ONE, listOf(piInverse, logWhole))
    logs = listOf(logWhole)
    pis = listOf(piInverse)
    checkTerm(term, ExactFraction.NEG_ONE, listOf(piInverse, logWhole), logs = logs, pis = pis, piCount = -1)

    logs = listOf(logChangeBase, logInverse, logDecimal)
    val sqrts = listOf(sqrtDecimal, Sqrt.ONE)
    pis = listOf(pi, pi, piInverse)

    term = Term.fromValues(ExactFraction(-1, 5), logs + sqrts)
    checkTerm(term, ExactFraction(-1, 5), logs + sqrts, logs, sqrts, emptyList(), 0)

    term = Term.fromValues(ExactFraction(-1, 5), sqrts + pis)
    checkTerm(term, ExactFraction(-1, 5), sqrts + pis, emptyList(), sqrts, pis, 1)

    term = Term.fromValues(one, sqrts + pis + logs + listOf(testNumber1, testNumber2))
    checkTerm(term, one, sqrts + pis + logs + listOf(testNumber1, testNumber2), logs, sqrts, pis, 1)
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
    var logs = listOf(logWhole, logInverse)
    checkTerm(term, one, logs, logs = logs)

    term = Term.fromValues(one, emptyList(), listOf(sqrtPartialWhole), 0)
    checkTerm(term, one, listOf(sqrtPartialWhole), sqrts = listOf(sqrtPartialWhole))

    term = Term.fromValues(one, emptyList(), emptyList(), -2)
    var pis = listOf(piInverse, piInverse)
    checkTerm(term, one, pis, pis = pis, piCount = -2)

    // multi type
    term = Term.fromValues(ExactFraction(-17, 100043), listOf(logWhole), emptyList(), 0)
    checkTerm(term, ExactFraction(-17, 100043), listOf(logWhole), logs = listOf(logWhole))

    term = Term.fromValues(ExactFraction.NEG_ONE, emptyList(), emptyList(), -1)
    checkTerm(term, ExactFraction.NEG_ONE, listOf(piInverse), pis = listOf(piInverse), piCount = -1)

    logs = listOf(logChangeBase, logInverse, logDecimal)
    val sqrts = listOf(sqrtDecimal, Sqrt.ONE)
    pis = listOf(pi, pi)

    term = Term.fromValues(ExactFraction(-1, 5), logs, sqrts, 0)
    checkTerm(term, ExactFraction(-1, 5), logs + sqrts, logs, sqrts, emptyList(), 0)

    term = Term.fromValues(ExactFraction(-1, 5), logs, emptyList(), 2)
    checkTerm(term, ExactFraction(-1, 5), logs + pis, logs, emptyList(), pis, 2)

    term = Term.fromValues(ExactFraction(-1, 5), emptyList(), sqrts, 2)
    checkTerm(term, ExactFraction(-1, 5), sqrts + pis, emptyList(), sqrts, pis, 2)

    term = Term.fromValues(ExactFraction(-1, 5), logs, sqrts, 2)
    checkTerm(term, ExactFraction(-1, 5), logs + sqrts + pis, logs, sqrts, pis, 2)
}
