package xyz.lbres.exactnumbers.irrationals.common

abstract class IrrationalNumber<T: IrrationalNumber<T>>: Comparable<T>, Irrational, Number() {
    override fun compareTo(other: T): Int = getValue().compareTo(other.getValue())

    override fun toByte(): Byte = getValue().toByte()
    override fun toChar(): Char = getValue().toInt().toChar()
    override fun toShort(): Short = getValue().toShort()
    override fun toInt(): Int = getValue().toInt()
    override fun toLong(): Long = getValue().toLong()
    override fun toDouble(): Double = getValue().toDouble()
    override fun toFloat(): Float = getValue().toFloat()
}