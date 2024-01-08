package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.exactnumbers.common.castToByte
import xyz.lbres.exactnumbers.common.castToChar
import xyz.lbres.exactnumbers.common.castToDouble
import xyz.lbres.exactnumbers.common.castToFloat
import xyz.lbres.exactnumbers.common.castToInt
import xyz.lbres.exactnumbers.common.castToLong
import xyz.lbres.exactnumbers.common.castToShort
import xyz.lbres.exactnumbers.common.createHashCode
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

/**
 * Number implementation for exact representation of rational numbers, represented as a numerator and a denominator.
 */
class ExactFraction constructor(numerator: BigInteger, denominator: BigInteger) : Comparable<ExactFraction>, Number() {
    private val implementation = ExactFractionImpl(numerator, denominator)

    /**
     * Numerator of number
     */
    val numerator: BigInteger
        get() = implementation.numerator

    /**
     * Denominator of number
     */
    val denominator: BigInteger
        get() = implementation.denominator

    // UNARY OPERATORS

    operator fun unaryMinus(): ExactFraction = implementation.unaryMinus().toExactFraction()
    operator fun unaryPlus(): ExactFraction = implementation.unaryPlus().toExactFraction()
    operator fun not(): Boolean = isZero()

    operator fun inc(): ExactFraction = implementation.inc().toExactFraction()
    operator fun dec(): ExactFraction = implementation.dec().toExactFraction()

    // BINARY OPERATORS

    operator fun plus(other: ExactFraction): ExactFraction = implementation.plus(other.implementation).toExactFraction()
    operator fun plus(other: BigInteger): ExactFraction = plus(other.toExactFraction())
    operator fun plus(other: Long): ExactFraction = plus(other.toExactFraction())
    operator fun plus(other: Int): ExactFraction = plus(other.toExactFraction())

    operator fun minus(other: ExactFraction): ExactFraction = plus(-other)
    operator fun minus(other: BigInteger): ExactFraction = plus(-other)
    operator fun minus(other: Long): ExactFraction = plus(-other)
    operator fun minus(other: Int): ExactFraction = plus(-other)

    operator fun times(other: ExactFraction): ExactFraction = implementation.times(other.implementation).toExactFraction()
    operator fun times(other: BigInteger): ExactFraction = times(other.toExactFraction())
    operator fun times(other: Long): ExactFraction = times(other.toExactFraction())
    operator fun times(other: Int): ExactFraction = times(other.toExactFraction())

    operator fun div(other: ExactFraction): ExactFraction = implementation.div(other.implementation).toExactFraction()
    operator fun div(other: BigInteger): ExactFraction = div(other.toExactFraction())
    operator fun div(other: Long): ExactFraction = div(other.toExactFraction())
    operator fun div(other: Int): ExactFraction = div(other.toExactFraction())

    override fun equals(other: Any?): Boolean = other is ExactFraction && implementation == other.implementation

    fun eq(other: Int): Boolean = implementation.eq(other)
    fun eq(other: Long): Boolean = implementation.eq(other)
    fun eq(other: BigInteger): Boolean = implementation.eq(other)

    override fun compareTo(other: ExactFraction): Int = implementation.compareTo(other.implementation)
    operator fun compareTo(other: Int): Int = compareTo(other.toExactFraction())
    operator fun compareTo(other: Long): Int = compareTo(other.toExactFraction())
    operator fun compareTo(other: BigInteger): Int = compareTo(other.toExactFraction())

    fun pow(other: ExactFraction): ExactFraction = implementation.pow(other.implementation).toExactFraction()
    fun pow(other: Int): ExactFraction = pow(other.toExactFraction())
    fun pow(other: Long): ExactFraction = pow(other.toExactFraction())
    fun pow(other: BigInteger): ExactFraction = pow(other.toExactFraction())

    // UNARY NON-OPERATORS

    fun inverse(): ExactFraction = implementation.inverse().toExactFraction()
    fun absoluteValue(): ExactFraction = implementation.absoluteValue().toExactFraction()
    fun isNegative(): Boolean = implementation.isNegative()
    fun isZero(): Boolean = implementation.isZero()
    fun isWholeNumber(): Boolean = denominator == BigInteger.ONE

    /**
     * Round ExactFraction to nearest whole number.
     *
     * @param roundingMode [RoundingMode]: mode to use for rounding number. Optional, defaults to [RoundingMode.HALF_UP]
     */
    fun roundToWhole(roundingMode: RoundingMode = RoundingMode.HALF_UP): ExactFraction {
        val decimal = numerator.toBigDecimal().divide(denominator.toBigDecimal(), roundingMode)
        val int = decimal.toBigInteger()

        return ExactFraction(int)
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

    override fun hashCode(): Int = createHashCode(listOf(numerator, denominator, this::class.toString()))

    companion object {
        val ZERO = ExactFraction(0)
        val ONE = ExactFraction(1)
        val TWO = ExactFraction(2)
        val THREE = ExactFraction(3)
        val FOUR = ExactFraction(4)
        val FIVE = ExactFraction(5)
        val SIX = ExactFraction(6)
        val SEVEN = ExactFraction(7)
        val EIGHT = ExactFraction(8)
        val NINE = ExactFraction(9)
        val TEN = ExactFraction(10)
        val NEG_ONE = ExactFraction(-1)
        val HALF = ExactFraction(1, 2)

        fun parse(s: String): ExactFraction {
            if (isEFString(s)) {
                return parseEFString(s)
            }

            return parseDecimal(s)
        }

        fun isEFString(s: String): Boolean = checkIsEFString(s)
    }
}
