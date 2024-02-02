package xyz.lbres.exactnumbers.expressions.expression

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
internal class SimpleExpression(private val term: Term) : Expression() {
    override fun unaryMinus(): Expression = SimpleExpression(-term)
    override fun unaryPlus(): Expression = this
    override fun inverse(): Expression = SimpleExpression(term.inverse())

    override fun equals(other: Any?): Boolean = other is SimpleExpression && term == other.term
    override fun hashCode(): Int = createHashCode(listOf(term, "SimpleExpression"))

    override fun toByte(): Byte = castToByte(term.getValue(), term, "SimpleExpression")
    override fun toChar(): Char = castToChar(term.getValue(), term, "SimpleExpression")
    override fun toShort(): Short = castToShort(term.getValue(), term, "SimpleExpression")
    override fun toInt(): Int = castToInt(term.getValue(), term, "SimpleExpression")
    override fun toLong(): Long = castToLong(term.getValue(), term, "SimpleExpression")
    override fun toFloat(): Float = castToFloat(term.getValue(), term, "SimpleExpression")
    override fun toDouble(): Double = castToDouble(term.getValue(), term, "SimpleExpression")
}
