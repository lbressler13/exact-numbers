package xyz.lbres.exactnumbers.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.IrrationalNumber
import kotlin.test.assertEquals

/**
 * Check the values in a single term
 *
 * @param term [Term]: term to check
 * @param coeff [ExactFraction]: expected coefficient
 * @param factors [List]<IrrationalNumber<*>>: expected factors. Defaults to empty list
 */
fun checkTerm(term: Term, coeff: ExactFraction, factors: List<IrrationalNumber<*>> = emptyList()) {
    assertEquals(coeff, term.coefficient)
    assertEquals(factors, term.factors)
}
