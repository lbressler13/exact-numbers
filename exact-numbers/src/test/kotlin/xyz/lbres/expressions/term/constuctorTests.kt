package xyz.lbres.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumber
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt

private val logNum1 = Log(ExactFraction(15, 4))
private val logNum2 = Log(8, 7)
private val logNum3 = Log(ExactFraction(19, 33), true)

fun runConstructorTests() {
    // zero
    var expectedCoeff = ExactFraction.ZERO
    var expectedNumbers: List<IrrationalNumber<*>> = emptyList()

    var term = Term(ExactFraction.ZERO, emptyList())
    assertTermComponentsEqual(expectedCoeff, expectedNumbers, term)

    term = Term(ExactFraction.ZERO, listOf(logNum1, Sqrt(35), logNum3, Pi()))
    assertTermComponentsEqual(expectedCoeff, expectedNumbers, term)

    term = Term(ExactFraction.FIVE, listOf(logNum1, Log.ZERO))
    assertTermComponentsEqual(expectedCoeff, expectedNumbers, term)

    term = Term(ExactFraction.FIVE, listOf(logNum1, Sqrt.ZERO))
    assertTermComponentsEqual(expectedCoeff, expectedNumbers, term)

    // others
    term = Term(-ExactFraction.FIVE, emptyList())
    expectedCoeff = -ExactFraction.FIVE
    expectedNumbers = emptyList()
    assertTermComponentsEqual(expectedCoeff, expectedNumbers, term)

    term = Term(ExactFraction(17, 3), listOf(Pi(), logNum2))
    expectedCoeff = ExactFraction(17, 3)
    expectedNumbers = listOf(Pi(), logNum2)
    assertTermComponentsEqual(expectedCoeff, expectedNumbers, term)

    term = Term(ExactFraction(17, 3), listOf(Pi(), logNum2, Sqrt(ExactFraction(9, 25))))
    expectedCoeff = ExactFraction(17, 3)
    expectedNumbers = listOf(Pi(), logNum2, Sqrt(ExactFraction(9, 25)))
    assertTermComponentsEqual(expectedCoeff, expectedNumbers, term)

    term = Term(
        ExactFraction(-4, 5),
        listOf(Pi(true), logNum2, logNum1, Pi(), logNum1.swapDivided(), Sqrt(32), logNum2, Sqrt(109))
    )
    expectedCoeff = ExactFraction(-4, 5)
    expectedNumbers = listOf(Pi(true), logNum2, logNum1, Pi(), logNum1.swapDivided(), Sqrt(32), logNum2, Sqrt(109))
    assertTermComponentsEqual(expectedCoeff, expectedNumbers, term)
}

fun runFromValuesTests() {
    // zero
    var expectedCoeff = ExactFraction.ZERO
    var expectedNumbers: List<IrrationalNumber<*>> = emptyList()

    var term = Term.fromValues(ExactFraction.ZERO, emptyList(), emptyList(), 0)
    assertTermComponentsEqual(expectedCoeff, expectedNumbers, term)

    term = Term.fromValues(ExactFraction.ZERO, listOf(logNum1, Log.ONE), listOf(Sqrt.ONE), 8)
    assertTermComponentsEqual(expectedCoeff, expectedNumbers, term)

    term = Term.fromValues(-ExactFraction.EIGHT, listOf(logNum1, logNum2, Log.ZERO, logNum3), listOf(Sqrt(64)), 5)
    assertTermComponentsEqual(expectedCoeff, expectedNumbers, term)

    term = Term.fromValues(
        ExactFraction.TWO, listOf(logNum1), listOf(Sqrt(64), Sqrt.ZERO, Sqrt(ExactFraction(3, 19))), -2
    )
    assertTermComponentsEqual(expectedCoeff, expectedNumbers, term)

    // nonzero
    term = Term.fromValues(ExactFraction(-5, 7), emptyList(), emptyList(), 0)
    expectedCoeff = ExactFraction(-5, 7)
    expectedNumbers = emptyList()
    assertTermComponentsEqual(expectedCoeff, expectedNumbers, term)

    term = Term.fromValues(ExactFraction.EIGHT, emptyList(), emptyList(), 3)
    expectedCoeff = ExactFraction.EIGHT
    expectedNumbers = listOf(Pi(), Pi(), Pi())
    assertTermComponentsEqual(expectedCoeff, expectedNumbers, term)

    term = Term.fromValues(ExactFraction.EIGHT, emptyList(), emptyList(), -3)
    expectedCoeff = ExactFraction.EIGHT
    expectedNumbers = listOf(Pi(true), Pi(true), Pi(true))
    assertTermComponentsEqual(expectedCoeff, expectedNumbers, term)

    term = Term.fromValues(ExactFraction(-2, 191), listOf(logNum1, logNum2, logNum1), emptyList(), 0)
    expectedCoeff = ExactFraction(-2, 191)
    expectedNumbers = listOf(logNum1, logNum2, logNum1)
    assertTermComponentsEqual(expectedCoeff, expectedNumbers, term)

    term = Term.fromValues(ExactFraction(-2, 191), emptyList(), listOf(Sqrt.ONE, Sqrt(52)), 0)
    expectedCoeff = ExactFraction(-2, 191)
    expectedNumbers = listOf(Sqrt.ONE, Sqrt(52))
    assertTermComponentsEqual(expectedCoeff, expectedNumbers, term)

    term = Term.fromValues(ExactFraction(-2, 191), listOf(logNum1, logNum2, logNum3), listOf(Sqrt(12), Sqrt(99)), 2)
    expectedCoeff = ExactFraction(-2, 191)
    expectedNumbers = listOf(logNum1, logNum2, logNum3, Sqrt(12), Sqrt(99), Pi(), Pi())
    assertTermComponentsEqual(expectedCoeff, expectedNumbers, term)

    term = Term.fromValues(ExactFraction(22), listOf(logNum1, logNum2, logNum3), listOf(Sqrt(12), Sqrt(99)), -2)
    expectedCoeff = ExactFraction(22)
    expectedNumbers = listOf(logNum1, logNum2, logNum3, Sqrt(12), Sqrt(99), Pi(true), Pi(true))
    assertTermComponentsEqual(expectedCoeff, expectedNumbers, term)
}
