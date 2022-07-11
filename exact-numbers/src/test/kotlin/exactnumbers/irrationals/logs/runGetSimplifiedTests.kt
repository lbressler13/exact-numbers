// package exactnumbers.irrationals.logs
//
// import exactnumbers.exactfraction.ExactFraction
// import kotlin.test.assertEquals
//
// internal fun runGetSimplifiedTests() {
//    val zero = LogNum.ZERO
//    val one = LogNum.ONE
//
//    // zero
//    var expectedLogs = listOf(zero)
//    var expectedCoeff = ExactFraction.ZERO
//
//    var product = LogProduct(listOf(zero))
//    assertEquals(expectedLogs, product.getSimplified().logs)
//    assertEquals(expectedCoeff, product.getSimplified().coefficient)
//
//    product = LogProduct(listOf(zero, zero, zero))
//    assertEquals(expectedLogs, product.getSimplified().logs)
//    assertEquals(expectedCoeff, product.getSimplified().coefficient)
//
//    product = LogProduct(listOf(zero, one))
//    assertEquals(expectedLogs, product.getSimplified().logs)
//    assertEquals(expectedCoeff, product.getSimplified().coefficient)
//
//    product = LogProduct(
//        listOf(
//            LogNum(ExactFraction.TWO),
//            LogNum(ExactFraction(18)),
//            zero,
//            LogNum(ExactFraction.TEN)
//        )
//    )
//    assertEquals(expectedLogs, product.getSimplified().logs)
//    assertEquals(expectedCoeff, product.getSimplified().coefficient)
//
//    product = LogProduct(
//        listOf(
//            LogNum(ExactFraction(18, 109)),
//            zero,
//            LogNum(ExactFraction.TEN)
//        )
//    )
//    assertEquals(expectedLogs, product.getSimplified().logs)
//    assertEquals(expectedCoeff, product.getSimplified().coefficient)
//
//    // ones
//    expectedLogs = listOf(one)
//
//    product = LogProduct(listOf(one))
//    expectedCoeff = ExactFraction.ONE
//    assertEquals(expectedLogs, product.getSimplified().logs)
//    assertEquals(expectedCoeff, product.getSimplified().coefficient)
//
//    product = LogProduct(listOf(one, one, one))
//    expectedCoeff = ExactFraction.ONE
//    assertEquals(expectedLogs, product.getSimplified().logs)
//    assertEquals(expectedCoeff, product.getSimplified().coefficient)
//
//    product = LogProduct(listOf(one, one, one), ExactFraction.HALF)
//    expectedCoeff = ExactFraction.HALF
//    assertEquals(expectedLogs, product.getSimplified().logs)
//    assertEquals(expectedCoeff, product.getSimplified().coefficient)
//
//    expectedLogs = listOf(
//        LogNum(ExactFraction.TWO),
//        LogNum(ExactFraction(18)),
//        LogNum(ExactFraction(81))
//    )
//
//    product = LogProduct(
//        listOf(
//            LogNum(ExactFraction.TWO),
//            LogNum(ExactFraction(18)),
//            one,
//            LogNum(ExactFraction(81))
//        )
//    )
//    expectedCoeff = ExactFraction.ONE
//    assertEquals(expectedLogs, product.getSimplified().logs)
//    assertEquals(expectedCoeff, product.getSimplified().coefficient)
//
//    product = LogProduct(
//        listOf(
//            LogNum(ExactFraction.TWO),
//            LogNum(ExactFraction(18)),
//            one,
//            LogNum(ExactFraction(81)),
//            one, one, one
//        ),
//        ExactFraction(-18, 11)
//    )
//    expectedCoeff = ExactFraction(-18, 11)
//    assertEquals(expectedLogs, product.getSimplified().logs)
//    assertEquals(expectedCoeff, product.getSimplified().coefficient)
//
//    expectedLogs = listOf(
//        LogNum(ExactFraction(18, 35)),
//        LogNum(ExactFraction(81))
//    )
//    product = LogProduct(
//        listOf(
//            LogNum(ExactFraction(18, 35)),
//            one,
//            LogNum(ExactFraction(81))
//        )
//    )
//    expectedCoeff = ExactFraction.ONE
//    assertEquals(expectedLogs, product.getSimplified().logs)
//    assertEquals(expectedCoeff, product.getSimplified().coefficient)
//
//    // others
//    product = LogProduct(
//        listOf(
//            LogNum(ExactFraction.TWO),
//            LogNum(ExactFraction(18)),
//            LogNum(ExactFraction(81, 98))
//        )
//    )
//    expectedLogs = listOf(
//        LogNum(ExactFraction.TWO),
//        LogNum(ExactFraction(18)),
//        LogNum(ExactFraction(81, 98)),
//    )
//    expectedCoeff = ExactFraction.ONE
//    assertEquals(expectedLogs, product.getSimplified().logs)
//    assertEquals(expectedCoeff, product.getSimplified().coefficient)
//
//    product = LogProduct(listOf(LogNum(ExactFraction.TWO), LogNum(ExactFraction.TWO)))
//    expectedLogs = listOf(LogNum(ExactFraction.TWO), LogNum(ExactFraction.TWO))
//    expectedCoeff = ExactFraction.ONE
//    assertEquals(expectedLogs, product.getSimplified().logs)
//    assertEquals(expectedCoeff, product.getSimplified().coefficient)
//
//    product = LogProduct(listOf(LogNum(ExactFraction.TWO), LogNum(ExactFraction.HALF)))
//    expectedLogs = listOf(LogNum(ExactFraction.TWO), LogNum(ExactFraction.HALF))
//    expectedCoeff = ExactFraction.ONE
//    assertEquals(expectedLogs, product.getSimplified().logs)
//    assertEquals(expectedCoeff, product.getSimplified().coefficient)
//
//    product = LogProduct(
//        listOf(LogNum(ExactFraction(103)), LogNum(ExactFraction.TWO)),
//        -ExactFraction.FOUR
//    )
//    expectedLogs = listOf(LogNum(ExactFraction(103)), LogNum(ExactFraction.TWO))
//    expectedCoeff = -ExactFraction.FOUR
//    assertEquals(expectedLogs, product.getSimplified().logs)
//    assertEquals(expectedCoeff, product.getSimplified().coefficient)
// }
