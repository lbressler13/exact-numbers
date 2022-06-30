package exactnumbers.expression

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.ext.toExactFraction
import exactnumbers.irrationals.LogNum
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ExpressionTest {
    @Test
    fun testConstructor() {
        // Int
        var int = 0
        var expr = Expression(int)
        assertEquals(listOf(int.toExactFraction()), expr.numbers)
        assertEquals(listOf(), expr.logs)

        int = 103
        expr = Expression(int)
        assertEquals(listOf(int.toExactFraction()), expr.numbers)
        assertEquals(listOf(), expr.logs)

        int = -200000
        expr = Expression(int)
        assertEquals(listOf(int.toExactFraction()), expr.numbers)
        assertEquals(listOf(), expr.logs)

        // ExactFraction
        var ef = ExactFraction.ZERO
        expr = Expression(ef)
        assertEquals(listOf(ef), expr.numbers)
        assertEquals(listOf(), expr.logs)

        ef = ExactFraction(103)
        expr = Expression(ef)
        assertEquals(listOf(ef), expr.numbers)
        assertEquals(listOf(), expr.logs)

        ef = ExactFraction(-20000)
        expr = Expression(ef)
        assertEquals(listOf(ef), expr.numbers)
        assertEquals(listOf(), expr.logs)

        ef = ExactFraction(100, 3)
        expr = Expression(ef)
        assertEquals(listOf(ef), expr.numbers)
        assertEquals(listOf(), expr.logs)

        ef = ExactFraction(-17, 3087)
        expr = Expression(ef)
        assertEquals(listOf(ef), expr.numbers)
        assertEquals(listOf(), expr.logs)

        // lists
        assertFailsWith<Exception>("Expression must contain at least one value") { Expression(listOf(), listOf()) }

        var numbers = listOf(ExactFraction.ZERO)
        var logs = listOf<LogNum>()
        expr = Expression(numbers, logs)
        assertEquals(numbers, expr.numbers)
        assertEquals(logs, expr.logs)

        numbers = listOf()
        logs = listOf(LogNum(ExactFraction.HALF, 8.toBigInteger()))
        expr = Expression(numbers, logs)
        assertEquals(numbers, expr.numbers)
        assertEquals(logs, expr.logs)

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
        assertEquals(numbers, expr.numbers)
        assertEquals(logs, expr.logs)
    }

    @Test
    fun testPlus() {
        // just numbers
        var expr1 = Expression(5)
        var expr2 = Expression(12)
        var expectedNumbers = listOf(ExactFraction(5), ExactFraction(12))
        var expectedLogs = listOf<LogNum>()
        checkExpectedValues(expr1 + expr2, expectedNumbers, expectedLogs)
        checkExpectedValues(expr2 + expr1, expectedNumbers, expectedLogs)

        // just logs
        expr1 = Expression(listOf(), listOf(LogNum.ZERO))
        expr2 = Expression(listOf(), listOf(LogNum(ExactFraction.HALF, BigInteger.TWO)))
        expectedNumbers = listOf()
        expectedLogs = listOf(LogNum.ZERO, LogNum(ExactFraction.HALF, BigInteger.TWO))
        checkExpectedValues(expr1 + expr2, expectedNumbers, expectedLogs)
        checkExpectedValues(expr2 + expr1, expectedNumbers, expectedLogs)

        // one logs, one numbers
        expr1 = Expression(
            listOf(),
            listOf(
                LogNum(ExactFraction.FOUR, BigInteger.TEN),
                LogNum.ZERO,
                LogNum(ExactFraction(-7, 4), 33.toBigInteger())
            )
        )
        expr2 = Expression(listOf(ExactFraction.HALF, ExactFraction(-1000, 2401)), listOf())
        expectedNumbers = listOf(ExactFraction.HALF, ExactFraction(-1000, 2401))
        expectedLogs = listOf(
            LogNum(ExactFraction.FOUR, BigInteger.TEN),
            LogNum.ZERO,
            LogNum(ExactFraction(-7, 4), 33.toBigInteger())
        )
        checkExpectedValues(expr1 + expr2, expectedNumbers, expectedLogs)
        checkExpectedValues(expr2 + expr1, expectedNumbers, expectedLogs)

        // both have logs and numbers
        expr1 = Expression(
            listOf(ExactFraction.SEVEN, ExactFraction(-106, 17), ExactFraction(14, 31)),
            listOf(LogNum(ExactFraction(15, 7), 16.toBigInteger()), LogNum(ExactFraction.HALF, 100.toBigInteger()))
        )
        expr2 = Expression(
            listOf(-ExactFraction.HALF, ExactFraction(100), ExactFraction(-12, 25), ExactFraction(33)),
            listOf(
                LogNum.ZERO,
                LogNum(ExactFraction(16, 33), 200.toBigInteger()),
                LogNum(ExactFraction(18), 18.toBigInteger())
            )
        )
        expectedNumbers = listOf(
            ExactFraction.SEVEN,
            ExactFraction(-106, 17),
            ExactFraction(14, 31),
            -ExactFraction.HALF,
            ExactFraction(100),
            ExactFraction(-12, 25),
            ExactFraction(33)
        )
        expectedLogs = listOf(
            LogNum(ExactFraction(15, 7), 16.toBigInteger()),
            LogNum(ExactFraction.HALF, 100.toBigInteger()),
            LogNum.ZERO,
            LogNum(ExactFraction(16, 33), 200.toBigInteger()),
            LogNum(ExactFraction(18), 18.toBigInteger())
        )
        checkExpectedValues(expr1 + expr2, expectedNumbers, expectedLogs)
        checkExpectedValues(expr2 + expr1, expectedNumbers, expectedLogs)
    }

    @Test
    fun testMinus() {
        // just numbers
        var expr1 = Expression(5)
        var expr2 = Expression(12)
        var expectedNumbers = listOf(ExactFraction(5), ExactFraction(-12))
        var expectedLogs = listOf<LogNum>()
        checkExpectedValues(expr1 - expr2, expectedNumbers, expectedLogs)

        expr1 = Expression(0)
        expr2 = Expression(12)
        expectedLogs = listOf()

        expectedNumbers = listOf(ExactFraction(0), ExactFraction(-12))
        checkExpectedValues(expr1 - expr2, expectedNumbers, expectedLogs)

        expectedNumbers = listOf(ExactFraction(12), ExactFraction(0))
        checkExpectedValues(expr2 - expr1, expectedNumbers, expectedLogs)

        // just logs
        expr1 = Expression(listOf(), listOf(LogNum(ExactFraction.FOUR, BigInteger.TEN)))
        expr2 = Expression(listOf(), listOf(LogNum(ExactFraction.HALF, BigInteger.TWO)))
        expectedNumbers = listOf()
        expectedLogs = listOf(LogNum(ExactFraction.FOUR, BigInteger.TEN), LogNum(-ExactFraction.HALF, BigInteger.TWO))
        checkExpectedValues(expr1 - expr2, expectedNumbers, expectedLogs)

        expr1 = Expression(listOf(), listOf(LogNum.ZERO))
        expr2 = Expression(listOf(), listOf(LogNum(ExactFraction.HALF, BigInteger.TWO)))
        expectedNumbers = listOf()

        expectedLogs = listOf(LogNum.ZERO, LogNum(-ExactFraction.HALF, BigInteger.TWO))
        checkExpectedValues(expr1 - expr2, expectedNumbers, expectedLogs)

        expectedLogs = listOf(LogNum(ExactFraction.HALF, BigInteger.TWO), LogNum.ZERO)
        checkExpectedValues(expr2 - expr1, expectedNumbers, expectedLogs)

        // one logs, one numbers
        expr1 = Expression(
            listOf(),
            listOf(
                LogNum(ExactFraction.FOUR, BigInteger.TEN),
                LogNum.ZERO,
                LogNum(ExactFraction(-7, 4), 33.toBigInteger())
            )
        )
        expr2 = Expression(listOf(ExactFraction.HALF, ExactFraction(-1000, 2401)), listOf())

        expectedNumbers = listOf(-ExactFraction.HALF, ExactFraction(1000, 2401))
        expectedLogs = listOf(
            LogNum(ExactFraction.FOUR, BigInteger.TEN),
            LogNum.ZERO,
            LogNum(ExactFraction(-7, 4), 33.toBigInteger())
        )
        checkExpectedValues(expr1 - expr2, expectedNumbers, expectedLogs)

        expectedNumbers = listOf(ExactFraction.HALF, ExactFraction(-1000, 2401))
        expectedLogs = listOf(
            LogNum(-ExactFraction.FOUR, BigInteger.TEN),
            LogNum.ZERO,
            LogNum(ExactFraction(7, 4), 33.toBigInteger())
        )
        checkExpectedValues(expr2 - expr1, expectedNumbers, expectedLogs)

        // both have logs and numbers
        expr1 = Expression(
            listOf(ExactFraction.SEVEN, ExactFraction(-106, 17), ExactFraction(14, 31)),
            listOf(LogNum(ExactFraction(15, 7), 16.toBigInteger()), LogNum(ExactFraction.HALF, 100.toBigInteger()))
        )
        expr2 = Expression(
            listOf(-ExactFraction.HALF, ExactFraction(100), ExactFraction(-12, 25), ExactFraction(33)),
            listOf(
                LogNum.ZERO,
                LogNum(ExactFraction(16, 33), 200.toBigInteger()),
                LogNum(ExactFraction(18), 18.toBigInteger())
            )
        )

        expectedNumbers = listOf(
            ExactFraction.SEVEN,
            ExactFraction(-106, 17),
            ExactFraction(14, 31),
            ExactFraction.HALF,
            -ExactFraction(100),
            ExactFraction(12, 25),
            ExactFraction(-33)
        )
        expectedLogs = listOf(
            LogNum(ExactFraction(15, 7), 16.toBigInteger()),
            LogNum(ExactFraction.HALF, 100.toBigInteger()),
            LogNum.ZERO,
            LogNum(ExactFraction(-16, 33), 200.toBigInteger()),
            LogNum(ExactFraction(-18), 18.toBigInteger())
        )
        checkExpectedValues(expr1 - expr2, expectedNumbers, expectedLogs)

        expectedNumbers = listOf(
            -ExactFraction.SEVEN,
            ExactFraction(106, 17),
            ExactFraction(-14, 31),
            -ExactFraction.HALF,
            ExactFraction(100),
            ExactFraction(-12, 25),
            ExactFraction(33)
        )
        expectedLogs = listOf(
            LogNum(ExactFraction(-15, 7), 16.toBigInteger()),
            LogNum(-ExactFraction.HALF, 100.toBigInteger()),
            LogNum.ZERO,
            LogNum(ExactFraction(16, 33), 200.toBigInteger()),
            LogNum(ExactFraction(18), 18.toBigInteger())
        )
        checkExpectedValues(expr2 - expr1, expectedNumbers, expectedLogs)
    }

    private fun checkExpectedValues(expr: Expression, expectedNumbers: List<ExactFraction>, expectedLogs: List<LogNum>) {
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
}
