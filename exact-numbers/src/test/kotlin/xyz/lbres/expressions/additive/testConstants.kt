package xyz.lbres.expressions.additive

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.expressions.term.Term

internal val term1 = Term.fromValues(ExactFraction.ONE, listOf(Log(8, 5)), listOf(Sqrt(ExactFraction(11, 15))))
internal val term2 = Term.fromValues(ExactFraction.TWO, listOf(Log(1000)), emptyList(), 2)
internal val term3 = Term.fromValue(ExactFraction(14, 5))
internal val term4 = Term.fromValues(ExactFraction(-2, 33), listOf(Log(ExactFraction(12, 13), 2)), listOf(Sqrt(16)), 1)
internal val term5 = Term.fromValues(-ExactFraction.TEN, listOf(Pi()))

internal val termZero = Term.ZERO
internal val termOne = Term.ONE
