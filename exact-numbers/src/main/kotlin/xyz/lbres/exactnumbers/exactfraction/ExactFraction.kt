package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.exactnumbers.common.castToByte
import xyz.lbres.exactnumbers.common.castToChar
import xyz.lbres.exactnumbers.common.castToDouble
import xyz.lbres.exactnumbers.common.castToFloat
import xyz.lbres.exactnumbers.common.castToInt
import xyz.lbres.exactnumbers.common.castToLong
import xyz.lbres.exactnumbers.common.castToShort
import xyz.lbres.exactnumbers.common.createHashCode
import xyz.lbres.exactnumbers.ext.toExactFraction
import xyz.lbres.kotlinutils.general.simpleIf
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
    operator fun plus(other: BigInteger): ExactFraction = plus(other.toExactFraction())
    operator fun plus(other: Long): ExactFraction = plus(other.toExactFraction())
    operator fun plus(other: Int): ExactFraction = plus(other.toExactFraction())

    abstract operator fun minus(other: ExactFraction): ExactFraction
    operator fun minus(other: BigInteger): ExactFraction = plus(-other)
    operator fun minus(other: Long): ExactFraction = plus(-other)
    operator fun minus(other: Int): ExactFraction = plus(-other)

    abstract operator fun times(other: ExactFraction): ExactFraction
    operator fun times(other: BigInteger): ExactFraction = times(other.toExactFraction())
    operator fun times(other: Long): ExactFraction = times(other.toExactFraction())
    operator fun times(other: Int): ExactFraction = times(other.toExactFraction())

    abstract operator fun div(other: ExactFraction): ExactFraction
    operator fun div(other: BigInteger): ExactFraction = div(other.toExactFraction())
    operator fun div(other: Long): ExactFraction = div(other.toExactFraction())
    operator fun div(other: Int): ExactFraction = div(other.toExactFraction())

    abstract fun eq(other: Int): Boolean
    abstract fun eq(other: Long): Boolean
    abstract fun eq(other: BigInteger): Boolean

    abstract override fun compareTo(other: ExactFraction): Int
    operator fun compareTo(other: Int): Int = compareTo(other.toExactFraction())
    operator fun compareTo(other: Long): Int = compareTo(other.toExactFraction())
    operator fun compareTo(other: BigInteger): Int = compareTo(other.toExactFraction())

    abstract fun pow(other: ExactFraction): ExactFraction
    fun pow(other: Int): ExactFraction = pow(other.toExactFraction())
    fun pow(other: Long): ExactFraction = pow(other.toExactFraction())
    fun pow(other: BigInteger): ExactFraction = pow(other.toExactFraction())

    // UNARY NON-OPERATORS

    abstract fun inverse(): ExactFraction
    abstract fun absoluteValue(): ExactFraction
    abstract fun isNegative(): Boolean
    abstract fun isZero(): Boolean
    abstract fun isWholeNumber(): Boolean

    /**
     * Round ExactFraction to nearest whole number.
     *
     * @param roundingMode [RoundingMode]: mode to use for rounding number. Optional, defaults to [RoundingMode.HALF_UP]
     */
    abstract fun roundToWhole(roundingMode: RoundingMode = RoundingMode.HALF_UP): ExactFraction

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

    override fun toByte(): Byte = castToByte(toBigDecimal(), this, "ExactFraction")
    override fun toChar(): Char = castToChar(toBigDecimal(), this, "ExactFraction")
    override fun toShort(): Short = castToShort(toBigDecimal(), this, "ExactFraction")
    override fun toInt(): Int = castToInt(toBigDecimal(), this, "ExactFraction")
    override fun toLong(): Long = castToLong(toBigDecimal(), this, "ExactFraction")
    override fun toFloat(): Float = castToFloat(toBigDecimal(), this, "ExactFraction")
    override fun toDouble(): Double = castToDouble(toBigDecimal(), this, "ExactFraction")

    abstract fun toBigInteger(): BigInteger
    abstract fun toBigDecimal(precision: Int = 20): BigDecimal

    override fun equals(other: Any?): Boolean {
        return other is ExactFraction && numerator == other.numerator && denominator == other.denominator
    }

    override fun hashCode(): Int = createHashCode(listOf(numerator, denominator, "ExactFraction"))

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
