package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.exactnumbers.utils.castToByte
import xyz.lbres.exactnumbers.utils.castToChar
import xyz.lbres.exactnumbers.utils.castToDouble
import xyz.lbres.exactnumbers.utils.castToFloat
import xyz.lbres.exactnumbers.utils.castToInt
import xyz.lbres.exactnumbers.utils.castToLong
import xyz.lbres.exactnumbers.utils.castToShort
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

/**
 * Number implementation for exact representation of rational numbers, represented as a numerator and a denominator.
 */
sealed class ExactFraction : Comparable<ExactFraction>, Number() {
    /**
     * Numerator of number
     */
    abstract val numerator: BigInteger

    /**
     * Denominator of number
     */
    abstract val denominator: BigInteger

    // UNARY OPERATORS

    abstract operator fun unaryMinus(): ExactFraction
    abstract operator fun unaryPlus(): ExactFraction
    abstract operator fun not(): Boolean

    abstract operator fun inc(): ExactFraction
    abstract operator fun dec(): ExactFraction

    // BINARY OPERATORS

    abstract operator fun plus(other: ExactFraction): ExactFraction
    operator fun plus(other: BigInteger): ExactFraction = plus(ExactFraction(other))
    operator fun plus(other: Long): ExactFraction = plus(ExactFraction(other))
    operator fun plus(other: Int): ExactFraction = plus(ExactFraction(other))

    abstract operator fun minus(other: ExactFraction): ExactFraction
    operator fun minus(other: BigInteger): ExactFraction = minus(ExactFraction(other))
    operator fun minus(other: Long): ExactFraction = minus(ExactFraction(other))
    operator fun minus(other: Int): ExactFraction = minus(ExactFraction(other))

    abstract operator fun times(other: ExactFraction): ExactFraction
    operator fun times(other: BigInteger): ExactFraction = times(ExactFraction(other))
    operator fun times(other: Long): ExactFraction = times(ExactFraction(other))
    operator fun times(other: Int): ExactFraction = times(ExactFraction(other))

    abstract operator fun div(other: ExactFraction): ExactFraction
    operator fun div(other: BigInteger): ExactFraction = div(ExactFraction(other))
    operator fun div(other: Long): ExactFraction = div(ExactFraction(other))
    operator fun div(other: Int): ExactFraction = div(ExactFraction(other))

    abstract fun pow(other: ExactFraction): ExactFraction
    fun pow(other: Int): ExactFraction = pow(ExactFraction(other))
    fun pow(other: Long): ExactFraction = pow(ExactFraction(other))
    fun pow(other: BigInteger): ExactFraction = pow(ExactFraction(other))

    abstract override fun compareTo(other: ExactFraction): Int
    operator fun compareTo(other: Int): Int = compareTo(ExactFraction(other))
    operator fun compareTo(other: Long): Int = compareTo(ExactFraction(other))
    operator fun compareTo(other: BigInteger): Int = compareTo(ExactFraction(other))

    abstract fun eq(other: BigInteger): Boolean
    fun eq(other: Int): Boolean = eq(other.toBigInteger())
    fun eq(other: Long): Boolean = eq(other.toBigInteger())

    // UNARY NON-OPERATORS

    abstract fun inverse(): ExactFraction
    abstract fun absoluteValue(): ExactFraction
    abstract fun isNegative(): Boolean
    abstract fun isZero(): Boolean
    abstract fun isWholeNumber(): Boolean

    /**
     * Round to nearest whole number
     *
     * @param roundingMode [RoundingMode]: mode to use for rounding number. Defaults to [RoundingMode.HALF_UP]
     */
    abstract fun roundToWhole(roundingMode: RoundingMode = RoundingMode.HALF_UP): ExactFraction

    // STRING METHODS

    /**
     * Create a string representation in standard decimal format
     *
     * @param digits [Int]: maximum number of digits after decimal point. Defaults to 8
     * @return [String]: representation in decimal format
     */
    abstract fun toDecimalString(digits: Int = 8): String

    /**
     * Create a fractional representation of the number, either as whole number or fraction
     *
     * @return [String]: representation of number in fractional format
     */
    abstract fun toFractionString(): String

    abstract fun toPairString(): String
    abstract fun toEFString(): String

    // CASTING

    override fun toByte(): Byte = castToByte(toBigDecimal(), this, "ExactFraction")
    override fun toChar(): Char = castToChar(toBigDecimal(), this, "ExactFraction")
    override fun toShort(): Short = castToShort(toBigDecimal(), this, "ExactFraction")
    override fun toInt(): Int = castToInt(toBigDecimal(), this, "ExactFraction")
    override fun toLong(): Long = castToLong(toBigDecimal(), this, "ExactFraction")
    override fun toFloat(): Float = castToFloat(toBigDecimal(), this, "ExactFraction")
    override fun toDouble(): Double = castToDouble(toBigDecimal(), this, "ExactFraction")

    abstract fun toPair(): Pair<BigInteger, BigInteger>
    abstract fun toBigInteger(): BigInteger
    abstract fun toBigDecimal(precision: Int = 20): BigDecimal

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
