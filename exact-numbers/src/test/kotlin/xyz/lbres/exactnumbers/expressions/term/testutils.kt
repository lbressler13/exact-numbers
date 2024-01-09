package xyz.lbres.exactnumbers.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumber
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import kotlin.test.assertEquals

fun checkTerm(
    term: Term,
    coeff: ExactFraction,
    factors: List<IrrationalNumber<*>> = emptyList(),
    logs: List<Log> = emptyList(),
    sqrts: List<Sqrt> = emptyList(),
    pis: List<Pi> = emptyList(),
    piCount: Int = 0
) {
    assertEquals(coeff, term.coefficient)
    assertEquals(factors, term.factors)
    assertEquals(logs.sorted(), term.logs.sorted())
    assertEquals(sqrts.sorted(), term.squareRoots.sorted())
    assertEquals(pis.sorted(), term.pis.sorted())
    assertEquals(piCount, term.piCount)
}
