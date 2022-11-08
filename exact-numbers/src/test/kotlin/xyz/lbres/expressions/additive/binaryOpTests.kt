package xyz.lbres.expressions.additive
//
//import kotlin.test.assertEquals
//
//fun runPlusTests() {
//    // zero
//    var expr1 = AdditiveExpression.ZERO
//
//    var expr2 = AdditiveExpression.ZERO
//    var expected = AdditiveExpression.ZERO
//    assertEquals(expected, expr1 + expr2)
//    assertEquals(expected, expr2 + expr1)
//
//    expr2 = AdditiveExpression.ONE
//    expected = AdditiveExpression.ONE
//    assertEquals(expected, expr1 + expr2)
//    assertEquals(expected, expr2 + expr1)
//
//    expr2 = AdditiveExpression(term1)
//    expected = AdditiveExpression(term1)
//    assertEquals(expected, expr1 + expr2)
//    assertEquals(expected, expr2 + expr1)
//
//    expr2 = AdditiveExpression(listOf(term1, term4, term1))
//    expected = AdditiveExpression(listOf(term1, term4, term1))
//    assertEquals(expected, expr1 + expr2)
//    assertEquals(expected, expr2 + expr1)
//
//    // non-zero
//    expr1 = AdditiveExpression(term1)
//    expr2 = AdditiveExpression(term1)
//    expected = AdditiveExpression(listOf(term1, term1))
//    assertEquals(expected, expr1 + expr2)
//    assertEquals(expected, expr2 + expr1)
//
//    expr1 = AdditiveExpression(term2)
//    expr2 = AdditiveExpression(term3)
//    expected = AdditiveExpression(listOf(term2, term3))
//    assertEquals(expected, expr1 + expr2)
//    assertEquals(expected, expr2 + expr1)
//
//    expr1 = AdditiveExpression(term2)
//    expr2 = AdditiveExpression(listOf(term3, term4))
//    expected = AdditiveExpression(listOf(term2, term3, term4))
//    assertEquals(expected, expr1 + expr2)
//    assertEquals(expected, expr2 + expr1)
//
//    expr1 = AdditiveExpression(listOf(term3, termZero, term1, term2))
//    expr2 = AdditiveExpression(listOf(term3, term4))
//    expected = AdditiveExpression(listOf(term1, term2, term3, term3, term4))
//    assertEquals(expected, expr1 + expr2)
//    assertEquals(expected, expr2 + expr1)
//}
//
//fun runMinusTests() {
//    // zero
//    var expr1 = AdditiveExpression.ZERO
//
//    var expr2 = AdditiveExpression.ZERO
//    var expected = AdditiveExpression.ZERO
//    assertEquals(expected, expr1 - expr2)
//
//    expr2 = AdditiveExpression.ONE
//    expected = -AdditiveExpression.ONE
//    assertEquals(expected, expr1 - expr2)
//    assertEquals(expr2, expr2 - expr1)
//
//    expr2 = -AdditiveExpression.ONE
//    expected = AdditiveExpression.ONE
//    assertEquals(expected, expr1 - expr2)
//    assertEquals(expr2, expr2 - expr1)
//
//    expr2 = AdditiveExpression(listOf(term1, term3, term4))
//    expected = AdditiveExpression(listOf(-term1, -term3, -term4))
//    assertEquals(expected, expr1 - expr2)
//    assertEquals(expr2, expr2 - expr1)
//
//    // non-zero
//    expr1 = AdditiveExpression(term1)
//    expr2 = AdditiveExpression(term1)
//    expected = AdditiveExpression(listOf(term1, -term1))
//    assertEquals(expected, expr1 - expr2)
//
//    expr1 = AdditiveExpression(-term2)
//    expr2 = AdditiveExpression(term3)
//    expected = AdditiveExpression(listOf(-term2, -term3))
//    assertEquals(expected, expr1 - expr2)
//
//    expr1 = AdditiveExpression(listOf(term1, term1, term2))
//    expr2 = AdditiveExpression(listOf(term3, term4))
//    expected = AdditiveExpression(listOf(term1, term1, term2, -term3, -term4))
//    assertEquals(expected, expr1 - expr2)
//
//    expr1 = AdditiveExpression(listOf(term1, -term1, term2))
//    expr2 = AdditiveExpression(listOf(term3, -term4))
//    expected = AdditiveExpression(listOf(term1, -term1, term2, -term3, term4))
//    assertEquals(expected, expr1 - expr2)
//}
