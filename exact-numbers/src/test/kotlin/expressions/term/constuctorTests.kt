package expressions.term

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.common.Irrational
import exactnumbers.irrationals.log.Log
import exactnumbers.irrationals.pi.Pi
import kotlin.test.assertEquals

private val logNum1 = Log(ExactFraction(15, 4))
private val logNum2 = Log(ExactFraction.EIGHT, 7)
private val logNum3 = Log(ExactFraction(19, 33), true)
private val logNum4 = Log(ExactFraction(25, 121))
private val one = ExactFraction.ONE

internal fun runConstructorTests() {
    // zero
    var expectedCoeff = ExactFraction.ZERO
    var expectedNumbers: List<Irrational> = listOf()

    var term = Term(ExactFraction.ZERO, listOf())
    assertEquals(expectedCoeff, term.coefficient)
    assertEquals(expectedNumbers, term.numbers)

    term = Term(ExactFraction.ZERO, listOf(logNum1, logNum3, Pi()))
    assertEquals(expectedCoeff, term.coefficient)
    assertEquals(expectedNumbers, term.numbers)

    term = Term(ExactFraction.FIVE, listOf(logNum1, Log.ZERO))
    assertEquals(expectedCoeff, term.coefficient)
    assertEquals(expectedNumbers, term.numbers)

    // others
    term = Term(-ExactFraction.FIVE, listOf())
    expectedCoeff = -ExactFraction.FIVE
    expectedNumbers = listOf()
    assertEquals(expectedCoeff, term.coefficient)
    assertEquals(expectedNumbers, term.numbers)

    term = Term(ExactFraction(17, 3), listOf(Pi(), logNum2))
    expectedCoeff = ExactFraction(17, 3)
    expectedNumbers = listOf(Pi(), logNum2)
    assertEquals(expectedCoeff, term.coefficient)
    assertEquals(expectedNumbers, term.numbers)

    term = Term(
        ExactFraction(-4, 5),
        listOf(Pi(true), logNum2, logNum1, Pi(), logNum1.swapDivided(), logNum2)
    )
    expectedCoeff = ExactFraction(-4, 5)
    expectedNumbers = listOf(Pi(true), logNum2, logNum1, Pi(), logNum1.swapDivided(), logNum2)
    assertEquals(expectedCoeff, term.coefficient)
    assertEquals(expectedNumbers, term.numbers)
}

internal fun runFromValuesTests() {
    // zero
    var expectedCoeff = ExactFraction.ZERO
    var expectedNumbers: List<Irrational> = listOf()

    var term = Term.fromValues(ExactFraction.ZERO, listOf(), 0)
    assertEquals(expectedCoeff, term.coefficient)
    assertEquals(expectedNumbers, term.numbers)

    term = Term.fromValues(ExactFraction.ZERO, listOf(logNum1, Log.ONE), 8)
    assertEquals(expectedCoeff, term.coefficient)
    assertEquals(expectedNumbers, term.numbers)

    term = Term.fromValues(-ExactFraction.EIGHT, listOf(logNum1, logNum2, Log.ZERO, logNum3), 5)
    assertEquals(expectedCoeff, term.coefficient)
    assertEquals(expectedNumbers, term.numbers)

    // nonzero
    term = Term.fromValues(ExactFraction(-5, 7), listOf(), 0)
    expectedCoeff = ExactFraction(-5, 7)
    expectedNumbers = listOf()
    assertEquals(expectedCoeff, term.coefficient)
    assertEquals(expectedNumbers, term.numbers)

    term = Term.fromValues(ExactFraction.EIGHT, listOf(), 3)
    expectedCoeff = ExactFraction.EIGHT
    expectedNumbers = listOf(Pi(), Pi(), Pi())
    assertEquals(expectedCoeff, term.coefficient)
    assertEquals(expectedNumbers, term.numbers)

    term = Term.fromValues(ExactFraction.EIGHT, listOf(), -3)
    expectedCoeff = ExactFraction.EIGHT
    expectedNumbers = listOf(Pi(true), Pi(true), Pi(true))
    assertEquals(expectedCoeff, term.coefficient)
    assertEquals(expectedNumbers, term.numbers)

    term = Term.fromValues(ExactFraction(-2, 191), listOf(logNum1, logNum2, logNum1), 0)
    expectedCoeff = ExactFraction(-2, 191)
    expectedNumbers = listOf(logNum1, logNum2, logNum1)
    assertEquals(expectedCoeff, term.coefficient)
    assertEquals(expectedNumbers, term.numbers)

    term = Term.fromValues(ExactFraction(-2, 191), listOf(logNum1, logNum2, logNum3), 2)
    expectedCoeff = ExactFraction(-2, 191)
    expectedNumbers = listOf(logNum1, logNum2, logNum3, Pi(), Pi())
    assertEquals(expectedCoeff, term.coefficient)
    assertEquals(expectedNumbers, term.numbers)

    term = Term.fromValues(ExactFraction(22), listOf(logNum1, logNum2, logNum3), -2)
    expectedCoeff = ExactFraction(22)
    expectedNumbers = listOf(logNum1, logNum2, logNum3, Pi(true), Pi(true))
    assertEquals(expectedCoeff, term.coefficient)
    assertEquals(expectedNumbers, term.numbers)
}
