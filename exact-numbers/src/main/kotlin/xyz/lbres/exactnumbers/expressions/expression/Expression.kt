package xyz.lbres.exactnumbers.expressions.expression

abstract class Expression : Number() {
    abstract operator fun unaryMinus(): Expression
    abstract operator fun unaryPlus(): Expression
    abstract fun inverse(): Expression

    // open operator fun plus(other: Expression): Expression = AdditiveExpression(constMultiSetOf(this, other))
    // open operator fun minus(other: Expression): Expression = AdditiveExpression(constMultiSetOf(this, -other))
    // open operator fun times(other: Expression): Expression = MultiplicativeExpression(constMultiSetOf(this, other))
    // open operator fun div(other: Expression): Expression = MultiplicativeExpression(constMultiSetOf(this, other.inverse()))

    override fun toByte(): Byte = 0 // TODO
    override fun toChar(): Char = '0' // TODO
    override fun toShort(): Short = 0 // TODO
    override fun toInt(): Int = 0 // TODO
    override fun toLong(): Long = 0 // TODO
    override fun toFloat(): Float = 0f // TODO
    override fun toDouble(): Double = 0.0 // TODO
}
