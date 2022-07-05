package exactnumbers.expression

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.logs.LogNum
import exactnumbers.irrationals.logs.LogProduct
import java.math.BigInteger

internal fun runGetSimplifiedTests() {
    // just numbers
    var expectedLogs = listOf<LogProduct>()

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

    expr = Expression(listOf(), listOf(LogProduct.ZERO))
    expectedLogs = listOf(LogProduct.ZERO)
    checkExpressionValues(expr.getSimplified(), expectedNumbers, expectedLogs)

    expr = Expression(listOf(), listOf(LogProduct(listOf(LogNum(BigInteger.TWO)))))
    expectedLogs = listOf(LogProduct(listOf(LogNum(BigInteger.TWO))))
    checkExpressionValues(expr.getSimplified(), expectedNumbers, expectedLogs)

    expr = Expression(listOf(), listOf(LogProduct.ZERO, LogProduct(listOf(LogNum(BigInteger.TWO)))))
    expectedLogs = listOf(LogProduct(listOf(LogNum(BigInteger.TWO))))
    checkExpressionValues(expr.getSimplified(), expectedNumbers, expectedLogs)

    expr = Expression(
        listOf(),
        listOf(
            LogProduct(listOf(LogNum.ONE)),
            LogProduct(listOf(LogNum(BigInteger.TWO)))
        )
    )
    expectedLogs = listOf(
        LogProduct(listOf(LogNum.ONE)),
        LogProduct(listOf(LogNum(BigInteger.TWO)))
    )
    checkExpressionValues(expr.getSimplified(), expectedNumbers, expectedLogs)

    expr = Expression(
        listOf(),
        listOf(
            LogProduct(listOf(LogNum(BigInteger.TEN), LogNum(BigInteger.TEN)), ExactFraction(-100)),
            LogProduct(listOf(LogNum(BigInteger.TEN), LogNum(BigInteger.TEN)), ExactFraction(100)),
        )
    )
    expectedLogs = listOf(LogProduct.ZERO)
    checkExpressionValues(expr.getSimplified(), expectedNumbers, expectedLogs)

    expr = Expression(
        listOf(),
        listOf(
            LogProduct(listOf(LogNum(BigInteger.TEN), LogNum(BigInteger.TEN)), ExactFraction.NEG_ONE),
            LogProduct(listOf(LogNum(BigInteger.TEN), LogNum(BigInteger.TEN)), ExactFraction.TWO),
            LogProduct.ONE,
            LogProduct.ZERO
        )
    )
    expectedLogs = listOf(
        LogProduct(listOf(LogNum(BigInteger.TEN), LogNum(BigInteger.TEN)), ExactFraction.ONE),
        LogProduct.ONE
    )
    checkExpressionValues(expr.getSimplified(), expectedNumbers, expectedLogs)

    // both numbers and logs
    expr = Expression(
        listOf(ExactFraction.EIGHT),
        listOf(LogProduct.ONE, LogProduct(listOf(LogNum(3.toBigInteger()), LogNum(BigInteger.TEN))))
    )
    expectedNumbers = listOf(ExactFraction.EIGHT)
    expectedLogs = listOf(LogProduct.ONE, LogProduct(listOf(LogNum(3.toBigInteger()), LogNum(BigInteger.TEN))))
    checkExpressionValues(expr.getSimplified(), expectedNumbers, expectedLogs)

    expr = Expression(
        listOf(ExactFraction(8, 3), ExactFraction(5, 6), ExactFraction.TWO),
        listOf(
            LogProduct(listOf(LogNum(BigInteger.TEN), LogNum(BigInteger.TEN)), ExactFraction.NEG_ONE),
            LogProduct(listOf(LogNum(BigInteger.TEN), LogNum(BigInteger.TEN)), ExactFraction.TWO),
            LogProduct.ONE,
            LogProduct.ZERO
        )
    )
    expectedNumbers = listOf(ExactFraction(11, 2))
    expectedLogs = listOf(
        LogProduct(listOf(LogNum(BigInteger.TEN), LogNum(BigInteger.TEN)), ExactFraction.ONE),
        LogProduct.ONE
    )
    checkExpressionValues(expr.getSimplified(), expectedNumbers, expectedLogs)
}
