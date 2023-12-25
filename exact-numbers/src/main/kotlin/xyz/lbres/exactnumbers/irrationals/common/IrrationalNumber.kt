package xyz.lbres.exactnumbers.irrationals.common

import xyz.lbres.common.divideByZero
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.expressions.term.Term
import java.math.BigDecimal
import java.math.BigInteger

abstract class IrrationalNumber<T : IrrationalNumber<T>> : Comparable<T>, Number() {
    abstract val type: String

    abstract fun getValue(): BigDecimal
    abstract fun isZero(): Boolean
    abstract fun isRational(): Boolean
    abstract fun getRationalValue(): ExactFraction?

    abstract val isDivided: Boolean
    abstract fun swapDivided(): T

    operator fun times(other: IrrationalNumber<*>): Term = Term(ExactFraction.ONE, listOf(this, other))
    operator fun div(other: IrrationalNumber<*>): Term {
        if (other.isZero()) {
            throw divideByZero
        }

        return Term(ExactFraction.ONE, listOf(this, other.swapDivided()))
    }

    override fun compareTo(other: T): Int = getValue().compareTo(other.getValue())

    override fun toByte(): Byte {
        return castToWholeNumber(Byte.MIN_VALUE.toString(), Byte.MAX_VALUE.toString()) { getValue().toByte() }
    }

    override fun toChar(): Char {
        val code = castToWholeNumber(Char.MIN_VALUE.code.toString(), Char.MAX_VALUE.code.toString()) { getValue().toInt() }
        return code.toChar()
    }

    override fun toShort(): Short {
        return castToWholeNumber(Short.MIN_VALUE.toString(), Short.MAX_VALUE.toString()) { getValue().toShort() }
    }

    override fun toInt(): Int {
        val minVal = Int.MIN_VALUE.toString()
        val maxValue = Int.MAX_VALUE.toString()
        val result = castToWholeNumber(minVal, maxValue) { getValue().toLong() }
        return result.toInt()
        // return castToWholeNumber(Int.MIN_VALUE.toLong().toString(), Int.MAX_VALUE.toLong().toString()) { getValue().toInt() }
    }

    override fun toLong(): Long {
        return castToWholeNumber(Long.MIN_VALUE.toString(), Long.MAX_VALUE.toString()) { getValue().toLong() }
    }

    override fun toDouble(): Double {
        return castToDecimal(-Double.MAX_VALUE, Double.MAX_VALUE) { getValue().toDouble() }
    }

    override fun toFloat(): Float {
        return castToDecimal(-Float.MAX_VALUE, Float.MAX_VALUE) { getValue().toFloat() }
    }

//    private fun <S: Number> castToWholeNumber(minValue: S, maxValue: S, cast: (BigInteger) -> S): S {
//        println("Getting value: ${toString()}")
//        val value = getValue().toBigInteger()
//        println("$minValue, $maxValue, $value")
//        val minInt = BigInteger(minValue.toString())
//        val maxInt = BigInteger(maxValue.toString())
//        if (value in minInt..maxInt) {
//            return cast(value)
//        }
//
//        throw ArithmeticException("Value would overflow supported range")
//    }

    private fun <S : Number> castToDecimal(minValue: S, maxValue: S, cast: (BigDecimal) -> S): S {
        println("Getting value: ${toString()}")
        val value = getValue()
        println("$minValue, $maxValue, $value")
        val minDecimal = BigDecimal(minValue.toString())
        val maxDecimal = BigDecimal(maxValue.toString())
        if (value in minDecimal..maxDecimal) {
            return cast(value)
        }

        throw ArithmeticException("Value would overflow supported range")
    }
    private fun <S : Number> castToWholeNumber(minValue: String, maxValue: String, cast: (BigInteger) -> S): S {
        val value = getValue().toBigInteger()

        val minInt = BigInteger(minValue)
        val maxInt = BigInteger(maxValue)
        if (value in minInt..maxInt) {
            return cast(value)
        }

        throw ArithmeticException("Value would overflow supported range")
    }
}
