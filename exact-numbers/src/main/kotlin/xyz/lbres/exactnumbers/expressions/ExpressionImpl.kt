package xyz.lbres.exactnumbers.expressions

import xyz.lbres.exactnumbers.utils.castToByte
import xyz.lbres.exactnumbers.utils.castToChar
import xyz.lbres.exactnumbers.utils.castToDouble
import xyz.lbres.exactnumbers.utils.castToFloat
import xyz.lbres.exactnumbers.utils.castToInt
import xyz.lbres.exactnumbers.utils.castToLong
import xyz.lbres.exactnumbers.utils.castToShort

// internal implementation of expression
@Suppress("EqualsOrHashCode")
internal abstract class ExpressionImpl : Expression() {
    override fun equals(other: Any?): Boolean = other is Expression && getValue() == other.getValue()

    override fun toByte(): Byte = castToByte(getValue(), this, "Expression")
    override fun toChar(): Char = castToChar(getValue(), this, "Expression")
    override fun toShort(): Short = castToShort(getValue(), this, "Expression")
    override fun toInt(): Int = castToInt(getValue(), this, "Expression")
    override fun toLong(): Long = castToLong(getValue(), this, "Expression")
    override fun toFloat(): Float = castToFloat(getValue(), this, "Expression")
    override fun toDouble(): Double = castToDouble(getValue(), this, "Expression")
}
