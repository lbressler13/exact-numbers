package xyz.lbres.exactnumbers.expressions.expression

import xyz.lbres.exactnumbers.expressions.Expression
import xyz.lbres.exactnumbers.expressions.ExpressionImpl
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.utils.createHashCode
import xyz.lbres.kotlinutils.bigdecimal.ext.isZero
import xyz.lbres.kotlinutils.collection.ext.toConstMultiSet
import xyz.lbres.kotlinutils.iterable.ext.forEachWith
import xyz.lbres.kotlinutils.set.multiset.anyConsistent
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.constMultiSetOf
import xyz.lbres.kotlinutils.set.multiset.const.constMutableMultiSetOf
import xyz.lbres.kotlinutils.set.multiset.mapToSetConsistent
import java.math.BigDecimal

/**
 * Expression which is the product of several other expressions
 */
@Suppress("EqualsOrHashCode")
internal class MultiplicativeExpression private constructor(expressions: ConstMultiSet<Expression>) : ExpressionImpl() {
    private val expressions: ConstMultiSet<Expression>

    init {
        this.expressions = when {
            expressions.isEmpty() -> throw Exception("Invalid expression")
            expressions.anyConsistent { it.getValue().isZero() } -> constMultiSetOf(ZERO)
            else -> expressions
        }
    }

    constructor(expr1: Expression, expr2: Expression) : this(constMultiSetOf(expr1, expr2))

    override fun unaryPlus(): Expression = this
    override fun unaryMinus(): Expression {
        val newExpressions = (expressions + constMultiSetOf(-ONE)).toConstMultiSet()
        return MultiplicativeExpression(newExpressions)
    }

    override fun inverse(): Expression {
        val newExpressions = expressions.mapToSetConsistent { it.inverse() }.toConstMultiSet()
        return MultiplicativeExpression(newExpressions)
    }

    override fun isZero(): Boolean = expressions.any { it.isZero() }

    private fun getSplitExpressions(): Pair<ConstMultiSet<SimpleExpression>, ConstMultiSet<AdditiveExpression>> {
        val simple: ConstMutableMultiSet<SimpleExpression> = constMutableMultiSetOf()
        val additive: ConstMutableMultiSet<AdditiveExpression> = constMutableMultiSetOf()

        expressions.forEach {
            when (it) {
                is SimpleExpression -> simple.add(it)
                is AdditiveExpression -> additive.add(it)
                is MultiplicativeExpression -> {
                    val split = it.getSplitExpressions()
                    simple.addAll(split.first)
                    additive.addAll(split.second)
                }
            }
        }

        return Pair(simple, additive)
    }

    override fun getSimplified(): Expression {
        val split = getSplitExpressions()
        val simpleTerm = split.first.fold(Term.ONE) { acc, expr -> acc * expr.term }
        val simple: Expression = SimpleExpression(simpleTerm.getSimplified())

        if (split.second.isEmpty()) {
            return simple
        }

        // TODO extract coefficient for each additive expr

        val exprs = split.second.fold(constMultiSetOf(simple)) { acc, additiveExpr ->
            val distributed: ConstMutableMultiSet<Expression> = constMutableMultiSetOf()
            acc.forEachWith(additiveExpr.expressions) { expr1, expr2 ->
                distributed.add(MultiplicativeExpression(expr1, expr2)) // TODO use times
            }
            distributed
        }
        return AdditiveExpression(exprs.toConstMultiSet()).getSimplified()
    }

    override fun getValue(): BigDecimal = getSimplified().getValue()

    override fun hashCode(): Int = createHashCode(listOf(expressions, "MultiplicativeExpression"))

    override fun toString(): String = "(${expressions.joinToString("x")})"
}
