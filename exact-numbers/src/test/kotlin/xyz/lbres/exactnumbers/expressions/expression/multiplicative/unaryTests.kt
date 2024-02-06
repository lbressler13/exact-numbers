package xyz.lbres.exactnumbers.expressions.expression.multiplicative

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.expressions.Expression
import xyz.lbres.exactnumbers.expressions.expression.* // ktlint-disable no-wildcard-imports no-unused-imports
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.testutils.assertDivByZero
import java.math.BigDecimal
import kotlin.test.assertEquals

fun runUnaryMinusTests() {
    var expr = MultiplicativeExpression(simpleExpr1, Expression.ZERO)
    var expected = MultiplicativeExpression(simpleExpr1, Expression.ZERO)
    assertEquals(expected, -expr)

    expr = MultiplicativeExpression(simpleExpr1, simpleExpr1)
    expected = MultiplicativeExpression(SimpleExpression(Term.fromValues(-ExactFraction.EIGHT, listOf(pi))), simpleExpr1)
    assertEquals(expected, -expr)

    expr = MultiplicativeExpression(-simpleExpr2, partialExpr)
    expected = MultiplicativeExpression(simpleExpr2, partialExpr)
    assertEquals(expected, -expr)

    expr = MultiplicativeExpression(simpleExpr2, simpleExpr3)
    expected = MultiplicativeExpression(SimpleExpression(Term.fromValues(-one, listOf(sqrt1))), simpleExpr2)
    assertEquals(expected, -expr)

    expr = MultiplicativeExpression(partialExpr, MultiplicativeExpression(partialExpr, Expression.ONE))
    expected = MultiplicativeExpression(partialExpr, MultiplicativeExpression(partialExpr, SimpleExpression(-Term.ONE)))
    assertEquals(expected, -expr)
}

fun runUnaryPlusTests() {
    var expr = MultiplicativeExpression(simpleExpr1, Expression.ZERO)
    assertEquals(expr, +expr)

    expr = MultiplicativeExpression(simpleExpr1, simpleExpr1)
    assertEquals(expr, +expr)

    expr = MultiplicativeExpression(-simpleExpr2, partialExpr)
    assertEquals(expr, +expr)

    expr = MultiplicativeExpression(simpleExpr2, simpleExpr3)
    assertEquals(expr, +expr)

    expr = MultiplicativeExpression(partialExpr, MultiplicativeExpression(partialExpr, simpleExpr2.inverse()))
    assertEquals(expr, +expr)
}

fun runInverseTests() {
    assertDivByZero { MultiplicativeExpression(Expression.ONE, Expression.ZERO).inverse() }

    var expr = MultiplicativeExpression(Expression.ONE, Expression.ONE)
    var expected = Expression.ONE
    assertEquals(expected, expr.inverse())

    expr = MultiplicativeExpression(simpleExpr1, simpleExpr1.inverse())
    expected = MultiplicativeExpression(simpleExpr1, simpleExpr1.inverse())
    assertEquals(expected, expr.inverse())

    expr = MultiplicativeExpression(simpleExpr2, simpleExpr1.inverse())
    expected = MultiplicativeExpression(simpleExpr2.inverse(), simpleExpr1)
    assertEquals(expected, expr.inverse())

    expr = MultiplicativeExpression(partialExpr, simpleExpr2)
    val partialInverse = MultiplicativeExpression(simpleExpr1.inverse(), simpleExpr3.inverse())
    expected = MultiplicativeExpression(partialInverse, simpleExpr2.inverse())
    assertEquals(expected, expr.inverse())
}

fun runGetValueTests() {
    var expr = MultiplicativeExpression(Expression.ZERO, Expression.ZERO)
    var expected = BigDecimal.ZERO
    assertEquals(expected, expr.getValue())

    expr = MultiplicativeExpression(simpleExpr1, simpleExpr1.inverse())
    expected = BigDecimal.ONE
    assertEquals(expected, expr.getValue())

    expr = MultiplicativeExpression(simpleExpr1, MultiplicativeExpression(simpleExpr2, simpleExpr2.inverse()))
    expected = BigDecimal("25.132741228718344")
    assertEquals(expected, expr.getValue())

    expr = MultiplicativeExpression(simpleExpr2.inverse(), MultiplicativeExpression(simpleExpr2, simpleExpr1))
    expected = BigDecimal("25.132741228718344")
    assertEquals(expected, expr.getValue())

    expr = MultiplicativeExpression(partialExpr, simpleExpr2)
    expected = BigDecimal("-58.612243222514357190681244841421306472893141640125321701455605589642097222080498297758968827615621990661861234584318882871011979592239226579660319550743893287383592140753516728261073125496301380737193")
    // expected = BigDecimal("-58.612243222514357191")
    assertEquals(expected, expr.getValue())

    expr = MultiplicativeExpression(partialExpr.inverse(), -simpleExpr2.inverse())
    expected = BigDecimal("0.017061281824748113494259559081001638329504400758354834298439155187267937410335702902091897196026099193024016721514628127266793917205357756156035937533761734752584007213581282245117667974675303725547485")
    // expected = BigDecimal("0.017061281824748112795308108019747092438130381820126559258080078125")
    assertEquals(expected, expr.getValue())
}
