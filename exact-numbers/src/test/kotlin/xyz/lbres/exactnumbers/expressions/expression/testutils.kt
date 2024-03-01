package xyz.lbres.exactnumbers.expressions.expression

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.exactnumbers.testutils.TestNumber

// constants to use in tests
val log1 = Log(ExactFraction(15, 4))
val log2 = Log(8, 7)
val log3 = Log(ExactFraction(19, 33)).inverse()
val log4 = Log(ExactFraction(25, 121))
val sqrt1 = Sqrt(99)
val sqrt2 = Sqrt(ExactFraction(64, 121))
val sqrt3 = Sqrt(ExactFraction(15, 44))
val pi = Pi()
val piInverse = Pi().inverse()
val testNumber1 = TestNumber(ExactFraction(3, 4))
val testNumber2 = TestNumber(ExactFraction.SEVEN)
val one = ExactFraction.ONE

internal val simpleExpr1 = SimpleExpression(Term.fromValues(ExactFraction.EIGHT, listOf(pi)))
internal val simpleExpr2 = SimpleExpression(Term.fromValues(ExactFraction(8, 17), listOf(log4, sqrt2, piInverse, pi)))
internal val simpleExpr3 = SimpleExpression(Term.fromValues(one, listOf(sqrt1)))
internal val multExpr1 = MultiplicativeExpression(simpleExpr3, simpleExpr1)
