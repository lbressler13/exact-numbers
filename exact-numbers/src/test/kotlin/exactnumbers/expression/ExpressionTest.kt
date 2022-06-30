package exactnumbers.expression

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.ext.toExactFraction
import exactnumbers.irrationals.LogNum
import java.math.BigInteger
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
        var logs = listOf<LogNum>()
        expr = Expression(numbers, logs)
        checkExpressionValues(expr, numbers, logs)

        numbers = listOf()
        logs = listOf(LogNum(ExactFraction.HALF, 8.toBigInteger()))
        expr = Expression(numbers, logs)
        checkExpressionValues(expr, numbers, logs)

        numbers = listOf(
            ExactFraction.SEVEN,
            ExactFraction(-11, 3),
            ExactFraction(4448214, 212123),
            ExactFraction(-2345)
        )
        logs = listOf(
            LogNum(ExactFraction.NEG_ONE, BigInteger.TWO),
            LogNum(ExactFraction(18, 5), 17.toBigInteger()),
            LogNum(ExactFraction(-12, 13), 100.toBigInteger())
        )
        expr = Expression(numbers, logs)
        checkExpressionValues(expr, numbers, logs)
    }

    @Test
    internal fun testSimplify() {
        // just numbers
        var expectedLogs = listOf<LogNum>()

        var expr = Expression(listOf(ExactFraction.HALF), listOf())
        var expectedNumbers = listOf(ExactFraction.HALF)
        checkExpressionValues(expr.getSimplified(), expectedNumbers, expectedLogs)

        expr = Expression(listOf(ExactFraction.ZERO, ExactFraction.SEVEN), listOf())
        expectedNumbers = listOf(ExactFraction.SEVEN)
        checkExpressionValues(expr.getSimplified(), expectedNumbers, expectedLogs)

        expr = Expression(listOf(-ExactFraction.HALF, ExactFraction.EIGHT), listOf())
        expectedNumbers = listOf(ExactFraction(15, 2))
        checkExpressionValues(expr.getSimplified(), expectedNumbers, expectedLogs)

        expr = Expression(
            listOf(ExactFraction(8, 3), ExactFraction(5, 6), ExactFraction.TWO),
            listOf()
        )
        expectedNumbers = listOf(ExactFraction(11, 2))
        checkExpressionValues(expr.getSimplified(), expectedNumbers, expectedLogs)

        // just logs
        expectedNumbers = listOf()

        expr = Expression(listOf(), listOf(LogNum.ZERO))
        expectedLogs = listOf(LogNum.ZERO)
        checkExpressionValues(expr.getSimplified(), expectedNumbers, expectedLogs)

        expr = Expression(listOf(), listOf(LogNum(ExactFraction.FOUR, BigInteger.TWO)))
        expectedLogs = listOf(LogNum(ExactFraction.FOUR, BigInteger.TWO))
        checkExpressionValues(expr.getSimplified(), expectedNumbers, expectedLogs)

        expr = Expression(listOf(), listOf(LogNum.ZERO, LogNum(ExactFraction.FOUR, BigInteger.TWO)))
        expectedLogs = listOf(LogNum(ExactFraction.FOUR, BigInteger.TWO))
        checkExpressionValues(expr.getSimplified(), expectedNumbers, expectedLogs)

        expr = Expression(
            listOf(),
            listOf(LogNum(ExactFraction.FOUR, BigInteger.TEN), LogNum(ExactFraction.SIX, BigInteger.TEN))
        )
        expectedLogs = listOf(LogNum(ExactFraction.TEN, BigInteger.TEN))
        checkExpressionValues(expr.getSimplified(), expectedNumbers, expectedLogs)

        expr = Expression(
            listOf(),
            listOf(
                LogNum(ExactFraction.FOUR, BigInteger.TEN),
                LogNum(-ExactFraction.HALF, BigInteger.TEN),
                LogNum(ExactFraction.SIX, BigInteger.TWO)
            )
        )
        expectedLogs = listOf(LogNum(ExactFraction(7, 2), BigInteger.TEN), LogNum(ExactFraction.SIX, BigInteger.TWO))
        checkExpressionValues(expr.getSimplified(), expectedNumbers, expectedLogs)

        expr = Expression(
            listOf(),
            listOf(
                LogNum(ExactFraction(8, 3), 104.toBigInteger()),
                LogNum(ExactFraction(13, 2), 1000.toBigInteger()),
                LogNum(ExactFraction(-18, 17), 20.toBigInteger()),
                LogNum(ExactFraction(5, 6), 104.toBigInteger()),
                LogNum(ExactFraction.TWO, 104.toBigInteger()),
                LogNum(ExactFraction(-17, 2), 1000.toBigInteger()),
            )
        )
        expectedLogs = listOf(
            LogNum(ExactFraction(11, 2), 104.toBigInteger()),
            LogNum(-ExactFraction.TWO, 1000.toBigInteger()),
            LogNum(ExactFraction(-18, 17), 20.toBigInteger())
        )
        checkExpressionValues(expr.getSimplified(), expectedNumbers, expectedLogs)

        // both
        expr = Expression(
            listOf(ExactFraction.EIGHT, ExactFraction.HALF, -ExactFraction.SEVEN),
            listOf(LogNum(ExactFraction.FOUR, BigInteger.TWO), LogNum(ExactFraction.FOUR, BigInteger.TEN))
        )
        expectedNumbers = listOf(ExactFraction(3, 2))
        expectedLogs = listOf(LogNum(ExactFraction.FOUR, BigInteger.TWO), LogNum(ExactFraction.FOUR, BigInteger.TEN))
        checkExpressionValues(expr.getSimplified(), expectedNumbers, expectedLogs)

        expr = Expression(
            listOf(ExactFraction(-17, 42)),
            listOf(
                LogNum(ExactFraction.FOUR, BigInteger.TEN),
                LogNum(-ExactFraction.HALF, BigInteger.TEN),
                LogNum(ExactFraction.SIX, BigInteger.TWO)
            )
        )
        expectedNumbers = listOf(ExactFraction(-17, 42))
        expectedLogs = listOf(LogNum(ExactFraction(7, 2), BigInteger.TEN), LogNum(ExactFraction.SIX, BigInteger.TWO))
        checkExpressionValues(expr.getSimplified(), expectedNumbers, expectedLogs)

        expr = Expression(
            listOf(ExactFraction(-21, 4), -ExactFraction.SIX, ExactFraction(9, 2)),
            listOf(
                LogNum(ExactFraction(8, 3), 104.toBigInteger()),
                LogNum(ExactFraction(13, 2), 1000.toBigInteger()),
                LogNum(ExactFraction(-18, 17), 20.toBigInteger()),
                LogNum(ExactFraction(5, 6), 104.toBigInteger()),
                LogNum(ExactFraction.TWO, 104.toBigInteger()),
                LogNum(ExactFraction(-17, 2), 1000.toBigInteger()),
            )
        )
        expectedNumbers = listOf(ExactFraction(-27, 4))
        expectedLogs = listOf(
            LogNum(ExactFraction(11, 2), 104.toBigInteger()),
            LogNum(-ExactFraction.TWO, 1000.toBigInteger()),
            LogNum(ExactFraction(-18, 17), 20.toBigInteger())
        )
        checkExpressionValues(expr.getSimplified(), expectedNumbers, expectedLogs)
    }

    @Test internal fun testPlus() = runPlusTests()
    @Test internal fun testMinus() = runMinusTests()
}

internal fun checkExpressionValues(expr: Expression, expectedNumbers: List<ExactFraction>, expectedLogs: List<LogNum>) {
    // not a real sort, just something to use for ordering for tests
    val logSort: (LogNum, LogNum) -> Int = { log1, log2 ->
        if (log1.coefficient != log2.coefficient) {
            log1.coefficient.compareTo(log2.coefficient)
        } else {
            log1.number.compareTo(log2.number)
        }
    }
    assertEquals(expectedNumbers.sorted(), expr.numbers.sorted())
    assertEquals(expectedLogs.sortedWith(logSort), expr.logs.sortedWith(logSort))
}
