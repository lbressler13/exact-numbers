package xyz.lbres.exactnumbers.expressions.expression

import xyz.lbres.exactnumbers.expressions.Expression
import xyz.lbres.exactnumbers.expressions.ExpressionImpl
import xyz.lbres.exactnumbers.expressions.term.Term
import xyz.lbres.exactnumbers.utils.castToByte
import xyz.lbres.exactnumbers.utils.castToChar
import xyz.lbres.exactnumbers.utils.castToDouble
import xyz.lbres.exactnumbers.utils.castToFloat
import xyz.lbres.exactnumbers.utils.castToInt
import xyz.lbres.exactnumbers.utils.castToLong
import xyz.lbres.exactnumbers.utils.castToShort
import xyz.lbres.exactnumbers.utils.createHashCode

/**
 * Expression consisting of a single term
 */
internal class SimpleExpression(private val term: Term) : ExpressionImpl() {
    override fun unaryMinus(): Expression = SimpleExpression(-term)
    override fun unaryPlus(): Expression = this
    override fun inverse(): Expression = SimpleExpression(term.inverse())

    override fun equals(other: Any?): Boolean = other is SimpleExpression && term == other.term
    override fun hashCode(): Int = createHashCode(listOf(term, "Expression"))

    override fun toByte(): Byte = castToByte(term.getValue(), this, "Expression")
    override fun toChar(): Char = castToChar(term.getValue(), this, "Expression")
    override fun toShort(): Short = castToShort(term.getValue(), this, "Expression")
    override fun toInt(): Int = castToInt(term.getValue(), this, "Expression")
    override fun toLong(): Long = castToLong(term.getValue(), this, "Expression")
    override fun toFloat(): Float = castToFloat(term.getValue(), this, "Expression")
    override fun toDouble(): Double = castToDouble(term.getValue(), this, "Expression")

    override fun toString(): String = "($term)"
}
