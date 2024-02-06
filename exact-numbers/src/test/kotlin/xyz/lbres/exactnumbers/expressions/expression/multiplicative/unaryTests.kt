package xyz.lbres.exactnumbers.expressions.expression.multiplicative

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.expression.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.exactnumbers.expressions.term.Term
import kotlin.test.assertEquals

private val simpleExpr1 = SimpleExpression(Term.fromValues(ExactFraction.EIGHT, listOf(pi)))
private val simpleExpr2 = SimpleExpression(Term.fromValues(ExactFraction(8, 17), listOf(log4, sqrt2, piInverse, pi)))
private val simpleExpr3 = SimpleExpression(Term.fromValues(one, listOf(sqrt1)))

fun runUnaryMinusTests() {
}

fun runUnaryPlusTests() {
}

fun runInverseTests() {
}

fun runGetValueTests() {
}
