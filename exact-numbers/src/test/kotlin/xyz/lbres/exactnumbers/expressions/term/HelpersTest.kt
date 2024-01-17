package xyz.lbres.exactnumbers.expressions.term

import org.junit.Test

class HelpersTest {
    @Test
    fun testCreateSimplifiedTerm() = runCommonSimplifyTests {
        createSimplifiedTerm(it.coefficient, it.factors.groupBy { factor -> factor.type })
    }
}
