package exactnumbers.expression

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.logs.LogNum
import exactnumbers.irrationals.logs.LogProduct
import java.math.BigInteger

internal fun runPlusTests() {
    // just numbers
    var expr1 = Expression(5)
    var expr2 = Expression(12)
    var expectedNumbers = listOf(ExactFraction(5), ExactFraction(12))
    var expectedLogs = listOf<LogProduct>()
    checkExpressionValues(expr1 + expr2, expectedNumbers, expectedLogs)
    checkExpressionValues(expr2 + expr1, expectedNumbers, expectedLogs)

    // just logs
    expr1 = Expression(listOf(), listOf(LogProduct.ONE))
    expr2 = Expression(listOf(), listOf(LogProduct(listOf(LogNum(BigInteger.TWO)))))
    expectedNumbers = listOf()
    expectedLogs = listOf(LogProduct.ONE, LogProduct(listOf(LogNum(BigInteger.TWO))))
    checkExpressionValues(expr1 + expr2, expectedNumbers, expectedLogs)
    checkExpressionValues(expr2 + expr1, expectedNumbers, expectedLogs)

    expr1 = Expression(
        listOf(), listOf(
            LogProduct.ONE,
            LogProduct(listOf(LogNum(12.toBigInteger())), ExactFraction.FOUR)
        )
    )
    expr2 = Expression(
        listOf(), listOf(
            LogProduct(listOf(LogNum(BigInteger.TWO))),
            LogProduct(listOf(LogNum.ONE, LogNum(106.toBigInteger())))
        )
    )
    expectedNumbers = listOf()
    expectedLogs = listOf(
        LogProduct.ONE,
        LogProduct(listOf(LogNum(12.toBigInteger())), ExactFraction.FOUR),
        LogProduct(listOf(LogNum(BigInteger.TWO))),
        LogProduct(listOf(LogNum.ONE, LogNum(106.toBigInteger())))
    )
    checkExpressionValues(expr1 + expr2, expectedNumbers, expectedLogs)
    checkExpressionValues(expr2 + expr1, expectedNumbers, expectedLogs)

    // one logs, one numbers
    expr1 = Expression(
        listOf(),
        listOf(LogProduct(listOf(LogNum(BigInteger.TEN), LogNum.ZERO, LogNum(33.toBigInteger()))))
    )
    expr2 = Expression(listOf(ExactFraction.HALF, ExactFraction(-1000, 2401)), listOf())
    expectedNumbers = listOf(ExactFraction.HALF, ExactFraction(-1000, 2401))
    expectedLogs = listOf(
        LogProduct(
            listOf(
                LogNum(BigInteger.TEN),
                LogNum.ZERO,
                LogNum(33.toBigInteger())
            )
        )
    )
    checkExpressionValues(expr1 + expr2, expectedNumbers, expectedLogs)
    checkExpressionValues(expr2 + expr1, expectedNumbers, expectedLogs)

    // both have logs and numbers
    expr1 = Expression(
        listOf(ExactFraction.SEVEN, ExactFraction(-106, 17), ExactFraction(14, 31)),
        listOf(LogProduct(listOf(LogNum(16.toBigInteger()), LogNum(100.toBigInteger()))))
    )
    expr2 = Expression(
        listOf(-ExactFraction.HALF, ExactFraction(100), ExactFraction(-12, 25), ExactFraction(33)),
        listOf(LogProduct.ONE, LogProduct(listOf(LogNum.ONE, LogNum(200.toBigInteger()), LogNum(18.toBigInteger()))))
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
        LogProduct(listOf(LogNum(16.toBigInteger()), LogNum(100.toBigInteger()))),
        LogProduct.ONE,
        LogProduct(listOf(LogNum.ONE, LogNum(200.toBigInteger()), LogNum(18.toBigInteger())))
    )
    checkExpressionValues(expr1 + expr2, expectedNumbers, expectedLogs)
    checkExpressionValues(expr2 + expr1, expectedNumbers, expectedLogs)

    expr1 = Expression(
        listOf(ExactFraction.SEVEN, ExactFraction(-106, 17), ExactFraction(14, 31)),
        listOf(
            LogProduct(listOf(LogNum(16.toBigInteger()), LogNum(100.toBigInteger()))),
            LogProduct(listOf(LogNum(16.toBigInteger()), LogNum(100.toBigInteger())), ExactFraction.FIVE),
            LogProduct(listOf(LogNum(17.toBigInteger())), ExactFraction.HALF)
        )
    )
    expr2 = Expression(
        listOf(-ExactFraction.HALF, ExactFraction(100), ExactFraction(-12, 25), ExactFraction(33)),
        listOf(
            LogProduct.ONE,
            LogProduct(listOf(LogNum.ONE, LogNum(200.toBigInteger()), LogNum(18.toBigInteger())))
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
        LogProduct(listOf(LogNum(16.toBigInteger()), LogNum(100.toBigInteger()))),
        LogProduct(listOf(LogNum(16.toBigInteger()), LogNum(100.toBigInteger())), ExactFraction.FIVE),
        LogProduct(listOf(LogNum(17.toBigInteger())), ExactFraction.HALF),
        LogProduct.ONE,
        LogProduct(listOf(LogNum.ONE, LogNum(200.toBigInteger()), LogNum(18.toBigInteger())))
    )
    checkExpressionValues(expr1 + expr2, expectedNumbers, expectedLogs)
    checkExpressionValues(expr2 + expr1, expectedNumbers, expectedLogs)
}

internal fun runMinusTests() {
    // just numbers
    var expr1 = Expression(5)
    var expr2 = Expression(12)
    var expectedNumbers = listOf(ExactFraction(5), ExactFraction(-12))
    var expectedLogs = listOf<LogProduct>()
    checkExpressionValues(expr1 - expr2, expectedNumbers, expectedLogs)

    expr1 = Expression(0)
    expr2 = Expression(12)
    expectedLogs = listOf()

    expectedNumbers = listOf(ExactFraction(0), ExactFraction(-12))
    checkExpressionValues(expr1 - expr2, expectedNumbers, expectedLogs)

    expectedNumbers = listOf(ExactFraction(12), ExactFraction(0))
    checkExpressionValues(expr2 - expr1, expectedNumbers, expectedLogs)

    // just logs
    expr1 = Expression(listOf(), listOf(LogProduct(listOf(LogNum(BigInteger.TEN)))))
    expr2 = Expression(listOf(), listOf(LogProduct(listOf(LogNum(BigInteger.TWO)))))
    expectedNumbers = listOf()
    expectedLogs = listOf(
        LogProduct(listOf(LogNum(BigInteger.TEN))),
        LogProduct(listOf(LogNum(BigInteger.TWO)), ExactFraction.NEG_ONE)
    )
    checkExpressionValues(expr1 - expr2, expectedNumbers, expectedLogs)

    expr1 = Expression(listOf(), listOf(LogProduct.ZERO))
    expr2 = Expression(listOf(), listOf(LogProduct(listOf(LogNum(BigInteger.TWO)))))
    expectedNumbers = listOf()

    expectedLogs = listOf(LogProduct.ZERO, LogProduct(listOf(LogNum(BigInteger.TWO)), ExactFraction.NEG_ONE))
    checkExpressionValues(expr1 - expr2, expectedNumbers, expectedLogs)

    expectedLogs = listOf(LogProduct(listOf(LogNum(BigInteger.TWO))), LogProduct.ZERO)
    checkExpressionValues(expr2 - expr1, expectedNumbers, expectedLogs)

    // one logs, one numbers
    expr1 = Expression(
        listOf(),
        listOf(
            LogProduct(
                listOf(
                    LogNum(BigInteger.TEN),
                    LogNum.ZERO,
                    LogNum(33.toBigInteger())
                )
            )
        )
    )
    expr2 = Expression(listOf(ExactFraction.HALF, ExactFraction(-1000, 2401)), listOf())

    expectedNumbers = listOf(-ExactFraction.HALF, ExactFraction(1000, 2401))
    expectedLogs = listOf(
        LogProduct(
            listOf(
                LogNum(BigInteger.TEN),
                LogNum.ZERO,
                LogNum(33.toBigInteger())
            )
        )
    )
    checkExpressionValues(expr1 - expr2, expectedNumbers, expectedLogs)

    expectedNumbers = listOf(ExactFraction.HALF, ExactFraction(-1000, 2401))
    expectedLogs = listOf(
        LogProduct(
            listOf(
                LogNum(BigInteger.TEN),
                LogNum.ZERO,
                LogNum(33.toBigInteger())
            ), ExactFraction.NEG_ONE
        )
    )
    checkExpressionValues(expr2 - expr1, expectedNumbers, expectedLogs)

    // both have logs and numbers
    expr1 = Expression(
        listOf(ExactFraction.SEVEN, ExactFraction(-106, 17), ExactFraction(14, 31)),
        listOf(
            LogProduct(listOf(LogNum(16.toBigInteger()), LogNum(100.toBigInteger()))),
            LogProduct(listOf(LogNum(4.toBigInteger())), ExactFraction(-17, 12))
        )
    )
    expr2 = Expression(
        listOf(-ExactFraction.HALF, ExactFraction(100), ExactFraction(-12, 25), ExactFraction(33)),
        listOf(
            LogProduct(listOf(LogNum(200.toBigInteger()), LogNum(18.toBigInteger())), ExactFraction.HALF),
            LogProduct.ZERO
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
        LogProduct(listOf(LogNum(16.toBigInteger()), LogNum(100.toBigInteger()))),
        LogProduct(listOf(LogNum(4.toBigInteger())), ExactFraction(-17, 12)),
        LogProduct(listOf(LogNum(200.toBigInteger()), LogNum(18.toBigInteger())), -ExactFraction.HALF),
        LogProduct.ZERO
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
        LogProduct(listOf(LogNum(16.toBigInteger()), LogNum(100.toBigInteger())), ExactFraction.NEG_ONE),
        LogProduct(listOf(LogNum(4.toBigInteger())), ExactFraction(17, 12)),
        LogProduct(listOf(LogNum(200.toBigInteger()), LogNum(18.toBigInteger())), ExactFraction.HALF),
        LogProduct.ZERO
    )
    checkExpressionValues(expr2 - expr1, expectedNumbers, expectedLogs)
}
