package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.exactnumbers.common.*
import xyz.lbres.exactnumbers.common.castToByte
import xyz.lbres.exactnumbers.common.castToChar
import xyz.lbres.exactnumbers.common.castToDouble
import xyz.lbres.exactnumbers.common.castToFloat
import xyz.lbres.exactnumbers.common.castToInt
import xyz.lbres.exactnumbers.common.castToLong
import xyz.lbres.exactnumbers.common.castToShort
import xyz.lbres.exactnumbers.common.divideByZero
import xyz.lbres.exactnumbers.ext.eq
import xyz.lbres.exactnumbers.ext.toExactFraction
import xyz.lbres.kotlinutils.biginteger.ext.isNegative
import xyz.lbres.kotlinutils.biginteger.ext.isZero
import xyz.lbres.kotlinutils.general.simpleIf
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode

internal class ExactFractionImpl private constructor(numerator: BigInteger, denominator: BigInteger, fullySimplified: Boolean) : Comparable<ExactFractionImpl>, Number() {
    /**
     * Numerator of number
     */
    val numerator: BigInteger

    /**
     * Denominator of number
     */
    val denominator: BigInteger

    init {
        when {
            denominator.isZero() -> throw divideByZero
            fullySimplified -> {
                this.numerator = numerator
                this.denominator = denominator
            }
            else -> {
                val simplifiedValues = simplify(Pair(numerator, denominator))
                this.numerator = simplifiedValues.first
                this.denominator = simplifiedValues.second
            }
        }
    }

    constructor(numerator: BigInteger, denominator: BigInteger): this(numerator, denominator, fullySimplified = false)

    // UNARY OPERATORS

    fun unaryMinus(): ExactFractionImpl = ExactFractionImpl(-numerator, denominator, fullySimplified = true)
    fun unaryPlus(): ExactFractionImpl = ExactFractionImpl(numerator, denominator, fullySimplified = true)
    fun not(): Boolean = isZero()

    fun inc(): ExactFractionImpl = ExactFractionImpl(numerator + denominator, denominator, fullySimplified = true)
    fun dec(): ExactFractionImpl = ExactFractionImpl(numerator - denominator, denominator, fullySimplified = true)

    // BINARY OPERATORS

    fun plus(other: ExactFractionImpl): ExactFractionImpl = efAdd(this, other)
    fun minus(other: ExactFractionImpl): ExactFractionImpl = plus(-other)
    fun times(other: ExactFractionImpl): ExactFractionImpl = efTimes(this, other)
    fun div(other: ExactFractionImpl): ExactFractionImpl = times(other.inverse())

    override fun equals(other: Any?): Boolean {
        return other is ExactFractionImpl && numerator == other.numerator && denominator == other.denominator
    }

    fun eq(other: Int): Boolean = isWholeNumber() && numerator.eq(other)
    fun eq(other: Long): Boolean = isWholeNumber() && numerator.eq(other)
    fun eq(other: BigInteger): Boolean = isWholeNumber() && numerator == other

    override fun compareTo(other: ExactFractionImpl): Int = efCompare(this, other)

    fun pow(other: ExactFractionImpl): ExactFractionImpl = efPow(this, other)

    fun toExactFraction(): ExactFraction = ExactFraction(numerator, denominator)

    // UNARY NON-OPERATORS

    fun inverse(): ExactFractionImpl {
        if (numerator.isZero()) {
            throw divideByZero
        }

        val signConverter = simpleIf(numerator.isNegative(), -BigInteger.ONE, BigInteger.ONE)
        return ExactFractionImpl(denominator * signConverter, numerator * signConverter, fullySimplified = true)
    }

    fun absoluteValue(): ExactFractionImpl = ExactFractionImpl(numerator.abs(), denominator, fullySimplified = true)
    fun isNegative(): Boolean = numerator.isNegative()
    fun isZero(): Boolean = numerator.isZero()
    fun isWholeNumber(): Boolean = denominator == BigInteger.ONE

    /**
     * Round ExactFraction to nearest whole number.
     *
     * @param roundingMode [RoundingMode]: mode to use for rounding number. Optional, defaults to [RoundingMode.HALF_UP]
     */
    fun roundToWhole(roundingMode: RoundingMode = RoundingMode.HALF_UP): ExactFractionImpl {
        val decimal = numerator.toBigDecimal().divide(denominator.toBigDecimal(), roundingMode)
        val int = decimal.toBigInteger()

        return ExactFractionImpl(int, BigInteger.ONE, fullySimplified = true)
    }

    // STRING METHODS

    /**
     * Create a string representation in standard decimal format
     *
     * @param digits [Int]: digits of precision in string. Defaults to 8.
     * Will be ignored if this number results in a string in exponential format
     * @return [String]: representation in decimal format
     */
    fun toDecimalString(digits: Int = 8): String = createDecimalString(this, digits)

    /**
     * Create a fractional representation of the number, either as whole number or fraction
     *
     * @return [String]: representation of number in fractional format
     */
    fun toFractionString() = simpleIf(isWholeNumber(), numerator.toString(), "$numerator/$denominator")

    fun toPairString(): String = "($numerator, $denominator)"
    fun toEFString(): String = "EF[$numerator $denominator]"

    override fun toString(): String = toEFString()

    // CASTING

    fun toPair(): Pair<BigInteger, BigInteger> = Pair(numerator, denominator)

    override fun toByte(): Byte = castToByte(toBigDecimal(), this)
    override fun toChar(): Char = castToChar(toBigDecimal(), this)
    override fun toShort(): Short = castToShort(toBigDecimal(), this)
    override fun toInt(): Int = castToInt(toBigDecimal(), this)
    override fun toLong(): Long = castToLong(toBigDecimal(), this)
    override fun toFloat(): Float = castToFloat(toBigDecimal(), this)
    override fun toDouble(): Double = castToDouble(toBigDecimal(), this)

    fun toBigInteger(): BigInteger = numerator / denominator
    fun toBigDecimal(precision: Int = 20): BigDecimal {
        val mc = MathContext(precision, RoundingMode.HALF_UP)
        return numerator.toBigDecimal().divide(denominator.toBigDecimal(), mc)
    }
}