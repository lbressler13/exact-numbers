package xyz.lbres.exactnumbers.expressions

import xyz.lbres.exactnumbers.utils.castToByte
import xyz.lbres.exactnumbers.utils.castToChar
import xyz.lbres.exactnumbers.utils.castToDouble
import xyz.lbres.exactnumbers.utils.castToFloat
import xyz.lbres.exactnumbers.utils.castToInt
import xyz.lbres.exactnumbers.utils.castToLong
import xyz.lbres.exactnumbers.utils.castToShort
import xyz.lbres.exactnumbers.utils.divideByZero

// internal implementation of expression
@Suppress("EqualsOrHashCode")
internal abstract class ExpressionImpl : Expression() {
    override fun minus(other: Expression): Expression = plus(-other)

    override fun div(other: Expression): Expression {
        if (other == ZERO) {
            throw divideByZero
        }
        return times(other.inverse())
    }

    override fun equals(other: Any?): Boolean = other is Expression && getValue() == other.getValue()

    override fun toByte(): Byte = castToByte(getValue(), this, "Expression")
    override fun toChar(): Char = castToChar(getValue(), this, "Expression")
    override fun toShort(): Short = castToShort(getValue(), this, "Expression")
    override fun toInt(): Int = castToInt(getValue(), this, "Expression")
    override fun toLong(): Long = castToLong(getValue(), this, "Expression")
    override fun toFloat(): Float = castToFloat(getValue(), this, "Expression")
    override fun toDouble(): Double = castToDouble(getValue(), this, "Expression")
}
