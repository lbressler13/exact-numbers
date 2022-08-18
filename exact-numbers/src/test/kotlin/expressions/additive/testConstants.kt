package expressions.additive

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.log.Log
import exactnumbers.irrationals.pi.Pi
import exactnumbers.irrationals.sqrt.Sqrt
import expressions.term.Term

internal val term1 = Term(ExactFraction.ONE, listOf(Log(8, 5), Pi(), Sqrt(ExactFraction(11, 15))))
internal val term2 = Term(ExactFraction.TWO, listOf(Pi(), Pi(), Log(1000)))
internal val term3 = Term(ExactFraction(14, 5), listOf())
internal val term4 = Term(ExactFraction(-2, 33), listOf(Log(ExactFraction(12, 13), 2), Pi(), Sqrt(16)))
internal val term5 = Term(-ExactFraction.TEN, listOf(Pi()))

internal val termZero = Term.ZERO
internal val termOne = Term.ONE
