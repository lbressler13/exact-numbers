package xyz.lbres.exactnumbers.expressions.term

import org.junit.Test

class HelpersTest {
    @Test
    fun testCreateSimplifiedTerm() = runCommonSimplifyTests { term ->
        createSimplifiedTerm(term.coefficient, term.factors.groupBy { it.type })
    }
}
