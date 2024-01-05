package xyz.lbres.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumber
import kotlin.test.assertEquals

/**
 * Validate values in a term
 *
 * @param expectedCoeff [ExactFraction]: expected coefficient
 * @param expectedNumbers [List]<Irrational>: expected numbers in term
 * @param term [Term]: term to validate
 */
internal fun assertTermComponentsEqual(expectedCoeff: ExactFraction, expectedNumbers: List<IrrationalNumber<*>>, term: Term) {
    assertEquals(expectedCoeff, term.coefficient)
    assertEquals(expectedNumbers, term.numbers)
}
