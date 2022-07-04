package exactnumbers.expression

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.logs.LogNum
import java.math.BigInteger

internal fun runPlusTests() {
    // just numbers
    var expr1 = Expression(5)
    var expr2 = Expression(12)
    var expectedNumbers = listOf(ExactFraction(5), ExactFraction(12))
    var expectedLogs = listOf<LogNum>()
    checkExpressionValues(expr1 + expr2, expectedNumbers, expectedLogs)
    checkExpressionValues(expr2 + expr1, expectedNumbers, expectedLogs)

    // just logs
    expr1 = Expression(listOf(), listOf(LogNum.ZERO))
    expr2 = Expression(listOf(), listOf(LogNum(ExactFraction.HALF, BigInteger.TWO)))
    expectedNumbers = listOf()
    expectedLogs = listOf(LogNum.ZERO, LogNum(ExactFraction.HALF, BigInteger.TWO))
    checkExpressionValues(expr1 + expr2, expectedNumbers, expectedLogs)
    checkExpressionValues(expr2 + expr1, expectedNumbers, expectedLogs)

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
    checkExpressionValues(expr1 + expr2, expectedNumbers, expectedLogs)
    checkExpressionValues(expr2 + expr1, expectedNumbers, expectedLogs)

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
    checkExpressionValues(expr1 + expr2, expectedNumbers, expectedLogs)
    checkExpressionValues(expr2 + expr1, expectedNumbers, expectedLogs)
}

internal fun runMinusTests() {
    // just numbers
    var expr1 = Expression(5)
    var expr2 = Expression(12)
    var expectedNumbers = listOf(ExactFraction(5), ExactFraction(-12))
    var expectedLogs = listOf<LogNum>()
    checkExpressionValues(expr1 - expr2, expectedNumbers, expectedLogs)

    expr1 = Expression(0)
    expr2 = Expression(12)
    expectedLogs = listOf()

    expectedNumbers = listOf(ExactFraction(0), ExactFraction(-12))
    checkExpressionValues(expr1 - expr2, expectedNumbers, expectedLogs)

    expectedNumbers = listOf(ExactFraction(12), ExactFraction(0))
    checkExpressionValues(expr2 - expr1, expectedNumbers, expectedLogs)

    // just logs
    expr1 = Expression(listOf(), listOf(LogNum(ExactFraction.FOUR, BigInteger.TEN)))
    expr2 = Expression(listOf(), listOf(LogNum(ExactFraction.HALF, BigInteger.TWO)))
    expectedNumbers = listOf()
    expectedLogs = listOf(LogNum(ExactFraction.FOUR, BigInteger.TEN), LogNum(-ExactFraction.HALF, BigInteger.TWO))
    checkExpressionValues(expr1 - expr2, expectedNumbers, expectedLogs)

    expr1 = Expression(listOf(), listOf(LogNum.ZERO))
    expr2 = Expression(listOf(), listOf(LogNum(ExactFraction.HALF, BigInteger.TWO)))
    expectedNumbers = listOf()

    expectedLogs = listOf(LogNum.ZERO, LogNum(-ExactFraction.HALF, BigInteger.TWO))
    checkExpressionValues(expr1 - expr2, expectedNumbers, expectedLogs)

    expectedLogs = listOf(LogNum(ExactFraction.HALF, BigInteger.TWO), LogNum.ZERO)
    checkExpressionValues(expr2 - expr1, expectedNumbers, expectedLogs)

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
    checkExpressionValues(expr1 - expr2, expectedNumbers, expectedLogs)

    expectedNumbers = listOf(ExactFraction.HALF, ExactFraction(-1000, 2401))
    expectedLogs = listOf(
        LogNum(-ExactFraction.FOUR, BigInteger.TEN),
        LogNum.ZERO,
        LogNum(ExactFraction(7, 4), 33.toBigInteger())
    )
    checkExpressionValues(expr2 - expr1, expectedNumbers, expectedLogs)

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
    checkExpressionValues(expr1 - expr2, expectedNumbers, expectedLogs)

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
    checkExpressionValues(expr2 - expr1, expectedNumbers, expectedLogs)
}
