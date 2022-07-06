package exactnumbers.expression

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.logs.LogNum
import exactnumbers.irrationals.logs.LogProduct

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
    expr2 = Expression(listOf(), listOf(LogProduct(listOf(LogNum(ExactFraction.TWO)))))
    expectedNumbers = listOf()
    expectedLogs = listOf(LogProduct.ONE, LogProduct(listOf(LogNum(ExactFraction.TWO))))
    checkExpressionValues(expr1 + expr2, expectedNumbers, expectedLogs)
    checkExpressionValues(expr2 + expr1, expectedNumbers, expectedLogs)

    expr1 = Expression(
        listOf(),
        listOf(
            LogProduct.ONE,
            LogProduct(listOf(LogNum(ExactFraction(12))), ExactFraction.FOUR)
        )
    )
    expr2 = Expression(
        listOf(),
        listOf(
            LogProduct(listOf(LogNum(ExactFraction.TWO))),
            LogProduct(listOf(LogNum.ONE, LogNum(ExactFraction(106, 7))))
        )
    )
    expectedNumbers = listOf()
    expectedLogs = listOf(
        LogProduct.ONE,
        LogProduct(listOf(LogNum(ExactFraction(12))), ExactFraction.FOUR),
        LogProduct(listOf(LogNum(ExactFraction.TWO))),
        LogProduct(listOf(LogNum.ONE, LogNum(ExactFraction(106, 7))))
    )
    checkExpressionValues(expr1 + expr2, expectedNumbers, expectedLogs)
    checkExpressionValues(expr2 + expr1, expectedNumbers, expectedLogs)

    // one logs, one numbers
    expr1 = Expression(
        listOf(),
        listOf(LogProduct(listOf(LogNum(ExactFraction.TEN), LogNum.ZERO, LogNum(ExactFraction(33)))))
    )
    expr2 = Expression(listOf(ExactFraction.HALF, ExactFraction(-1000, 2401)), listOf())
    expectedNumbers = listOf(ExactFraction.HALF, ExactFraction(-1000, 2401))
    expectedLogs = listOf(
        LogProduct(
            listOf(
                LogNum(ExactFraction.TEN),
                LogNum.ZERO,
                LogNum(ExactFraction(33))
            )
        )
    )
    checkExpressionValues(expr1 + expr2, expectedNumbers, expectedLogs)
    checkExpressionValues(expr2 + expr1, expectedNumbers, expectedLogs)

    // both have logs and numbers
    expr1 = Expression(
        listOf(ExactFraction.SEVEN, ExactFraction(-106, 17), ExactFraction(14, 31)),
        listOf(LogProduct(listOf(LogNum(ExactFraction(16)), LogNum(ExactFraction(100)))))
    )
    expr2 = Expression(
        listOf(-ExactFraction.HALF, ExactFraction(100), ExactFraction(-12, 25), ExactFraction(33)),
        listOf(LogProduct.ONE, LogProduct(listOf(LogNum.ONE, LogNum(ExactFraction(200)), LogNum(ExactFraction(18)))))
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
        LogProduct(listOf(LogNum(ExactFraction(16)), LogNum(ExactFraction(100)))),
        LogProduct.ONE,
        LogProduct(listOf(LogNum.ONE, LogNum(ExactFraction(200)), LogNum(ExactFraction(18))))
    )
    checkExpressionValues(expr1 + expr2, expectedNumbers, expectedLogs)
    checkExpressionValues(expr2 + expr1, expectedNumbers, expectedLogs)

    expr1 = Expression(
        listOf(ExactFraction.SEVEN, ExactFraction(-106, 17), ExactFraction(14, 31)),
        listOf(
            LogProduct(listOf(LogNum(ExactFraction(16, 103)), LogNum(ExactFraction(100)))),
            LogProduct(listOf(LogNum(ExactFraction(16, 103)), LogNum(ExactFraction(100))), ExactFraction.FIVE),
            LogProduct(listOf(LogNum(ExactFraction(17, 15))), ExactFraction.HALF)
        )
    )
    expr2 = Expression(
        listOf(-ExactFraction.HALF, ExactFraction(100), ExactFraction(-12, 25), ExactFraction(33)),
        listOf(
            LogProduct.ONE,
            LogProduct(listOf(LogNum.ONE, LogNum(ExactFraction(200, 19)), LogNum(ExactFraction(18))))
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
        LogProduct(listOf(LogNum(ExactFraction(16, 103)), LogNum(ExactFraction(100)))),
        LogProduct(listOf(LogNum(ExactFraction(16, 103)), LogNum(ExactFraction(100))), ExactFraction.FIVE),
        LogProduct(listOf(LogNum(ExactFraction(17, 15))), ExactFraction.HALF),
        LogProduct.ONE,
        LogProduct(listOf(LogNum.ONE, LogNum(ExactFraction(200, 19)), LogNum(ExactFraction(18))))
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
    expr1 = Expression(listOf(), listOf(LogProduct(listOf(LogNum(ExactFraction.TEN)))))
    expr2 = Expression(listOf(), listOf(LogProduct(listOf(LogNum(ExactFraction.TWO)))))
    expectedNumbers = listOf()
    expectedLogs = listOf(
        LogProduct(listOf(LogNum(ExactFraction.TEN))),
        LogProduct(listOf(LogNum(ExactFraction.TWO)), ExactFraction.NEG_ONE)
    )
    checkExpressionValues(expr1 - expr2, expectedNumbers, expectedLogs)

    expr1 = Expression(listOf(), listOf(LogProduct.ZERO))
    expr2 = Expression(listOf(), listOf(LogProduct(listOf(LogNum(ExactFraction(17, 7))))))
    expectedNumbers = listOf()

    expectedLogs = listOf(LogProduct.ZERO, LogProduct(listOf(LogNum(ExactFraction(17, 7))), ExactFraction.NEG_ONE))
    checkExpressionValues(expr1 - expr2, expectedNumbers, expectedLogs)

    expectedLogs = listOf(LogProduct(listOf(LogNum(ExactFraction(17, 7)))), LogProduct.ZERO)
    checkExpressionValues(expr2 - expr1, expectedNumbers, expectedLogs)

    // one logs, one numbers
    expr1 = Expression(
        listOf(),
        listOf(LogProduct(listOf(LogNum(ExactFraction.TEN), LogNum.ZERO, LogNum(ExactFraction(33)))))
    )
    expr2 = Expression(listOf(ExactFraction.HALF, ExactFraction(-1000, 2401)), listOf())

    expectedNumbers = listOf(-ExactFraction.HALF, ExactFraction(1000, 2401))
    expectedLogs = listOf(
        LogProduct(
            listOf(
                LogNum(ExactFraction.TEN),
                LogNum.ZERO,
                LogNum(ExactFraction(33))
            )
        )
    )
    checkExpressionValues(expr1 - expr2, expectedNumbers, expectedLogs)

    expectedNumbers = listOf(ExactFraction.HALF, ExactFraction(-1000, 2401))
    expectedLogs = listOf(
        LogProduct(listOf(LogNum(ExactFraction.TEN), LogNum.ZERO, LogNum(ExactFraction(33))), ExactFraction.NEG_ONE)
    )
    checkExpressionValues(expr2 - expr1, expectedNumbers, expectedLogs)

    // both have logs and numbers
    expr1 = Expression(
        listOf(ExactFraction.SEVEN, ExactFraction(-106, 17), ExactFraction(14, 31)),
        listOf(
            LogProduct(listOf(LogNum(ExactFraction(16, 53)), LogNum(ExactFraction(100)))),
            LogProduct(listOf(LogNum(ExactFraction.FOUR)), ExactFraction(-17, 12))
        )
    )
    expr2 = Expression(
        listOf(-ExactFraction.HALF, ExactFraction(100), ExactFraction(-12, 25), ExactFraction(33)),
        listOf(
            LogProduct(listOf(LogNum(ExactFraction(200, 27)), LogNum(ExactFraction(18, 209))), ExactFraction.HALF),
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
        LogProduct(listOf(LogNum(ExactFraction(16, 53)), LogNum(ExactFraction(100)))),
        LogProduct(listOf(LogNum(ExactFraction.FOUR)), ExactFraction(-17, 12)),
        LogProduct(listOf(LogNum(ExactFraction(200, 27)), LogNum(ExactFraction(18, 209))), -ExactFraction.HALF),
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
        LogProduct(listOf(LogNum(ExactFraction(16, 53)), LogNum(ExactFraction(100))), ExactFraction.NEG_ONE),
        LogProduct(listOf(LogNum(ExactFraction.FOUR)), ExactFraction(17, 12)),
        LogProduct(listOf(LogNum(ExactFraction(200, 27)), LogNum(ExactFraction(18, 209))), ExactFraction.HALF),
        LogProduct.ZERO
    )
    checkExpressionValues(expr2 - expr1, expectedNumbers, expectedLogs)
}
