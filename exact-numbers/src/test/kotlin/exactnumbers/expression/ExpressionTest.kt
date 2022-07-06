package exactnumbers.expression

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.ext.toExactFraction
import exactnumbers.irrationals.logs.LogNum
import exactnumbers.irrationals.logs.LogProduct
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class ExpressionTest {
    @Test
    internal fun testConstructor() {
        // Int
        var int = 0
        var expr = Expression(int)
        checkExpressionValues(expr, listOf(int.toExactFraction()), listOf())

        int = 103
        expr = Expression(int)
        checkExpressionValues(expr, listOf(int.toExactFraction()), listOf())

        int = -200000
        expr = Expression(int)
        checkExpressionValues(expr, listOf(int.toExactFraction()), listOf())

        // ExactFraction
        var ef = ExactFraction.ZERO
        expr = Expression(ef)
        checkExpressionValues(expr, listOf(ef), listOf())

        ef = ExactFraction(103)
        expr = Expression(ef)
        checkExpressionValues(expr, listOf(ef), listOf())

        ef = ExactFraction(-20000)
        expr = Expression(ef)
        checkExpressionValues(expr, listOf(ef), listOf())

        ef = ExactFraction(100, 3)
        expr = Expression(ef)
        checkExpressionValues(expr, listOf(ef), listOf())

        ef = ExactFraction(-17, 3087)
        expr = Expression(ef)
        checkExpressionValues(expr, listOf(ef), listOf())

        // lists
        assertFailsWith<Exception>("Expression must contain at least one value") { Expression(listOf(), listOf()) }

        var numbers = listOf(ExactFraction.ZERO)
        var logs = listOf<LogProduct>()
        expr = Expression(numbers, logs)
        checkExpressionValues(expr, numbers, logs)

        numbers = listOf()
        logs = listOf(LogProduct(listOf(LogNum(ExactFraction.EIGHT))))
        expr = Expression(numbers, logs)
        checkExpressionValues(expr, numbers, logs)

        numbers = listOf(
            ExactFraction.SEVEN,
            ExactFraction(-11, 3),
            ExactFraction(4448214, 212123),
            ExactFraction(-2345)
        )
        logs = listOf(
            LogProduct(listOf(LogNum(ExactFraction.TWO), LogNum(ExactFraction(17)), LogNum(ExactFraction(100))))
        )
        expr = Expression(numbers, logs)
        checkExpressionValues(expr, numbers, logs)

        // multiple logs
        numbers = listOf(
            ExactFraction.SEVEN,
            ExactFraction(-11, 3),
            ExactFraction(4448214, 212123),
            ExactFraction(-2345)
        )
        logs = listOf(
            LogProduct.ONE,
            LogProduct(listOf(LogNum(ExactFraction.EIGHT), LogNum(ExactFraction(15))), ExactFraction(-3, 2)),
            LogProduct(listOf(LogNum.ONE), ExactFraction.FIVE)
        )
        expr = Expression(numbers, logs)
        checkExpressionValues(expr, numbers, logs)
    }

    @Test internal fun testPlus() = runPlusTests()
    @Test internal fun testMinus() = runMinusTests()
    @Test internal fun testGetSimplified() = runGetSimplifiedTests()

    @Test
    internal fun testToString() {
        // just numbers
        var expr = Expression(5)
        var expected = "${ExactFraction.FIVE}"
        assertEquals(expected, expr.toString())

        expr = Expression(listOf(ExactFraction.FOUR, -ExactFraction.HALF, ExactFraction(15, 4)), listOf())
        expected = "${ExactFraction.FOUR}+${-ExactFraction.HALF}+${ExactFraction(15, 4)}"
        assertEquals(expected, expr.toString())

        // just logs
        expr = Expression(listOf(), listOf(LogProduct.ZERO))
        expected = "${LogProduct.ZERO}"
        assertEquals(expected, expr.toString())

        val product1 = LogProduct(listOf(LogNum.ONE, LogNum(ExactFraction.TEN)))
        val product2 = LogProduct(listOf(LogNum(ExactFraction(15, 7))), ExactFraction.FOUR)

        expr = Expression(listOf(), listOf(product1, product2))
        expected = "$product1+$product2"
        assertEquals(expected, expr.toString())

        // both
        expr = Expression(listOf(ExactFraction.HALF), listOf(LogProduct.ONE))
        expected = "${ExactFraction.HALF}+${LogProduct.ONE}"
        assertEquals(expected, expr.toString())

        expr = Expression(listOf(ExactFraction.EIGHT, ExactFraction(-15, 7)), listOf(product1, product2))
        expected = "${ExactFraction.EIGHT}+${ExactFraction(-15, 7)}+$product1+$product2"
        assertEquals(expected, expr.toString())
    }
}

internal fun checkExpressionValues(
    expr: Expression,
    expectedNumbers: List<ExactFraction>,
    expectedLogs: List<LogProduct>
) {
    assertEquals(expectedNumbers.sorted(), expr.numbers.sorted())
    assertEquals(expectedLogs.groupBy { it.logs }, expr.logs.groupBy { it.logs })
}
