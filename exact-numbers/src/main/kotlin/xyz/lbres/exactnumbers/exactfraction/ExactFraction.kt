package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.exactnumbers.common.castToByte
import xyz.lbres.exactnumbers.common.castToChar
import xyz.lbres.exactnumbers.common.castToDouble
import xyz.lbres.exactnumbers.common.castToFloat
import xyz.lbres.exactnumbers.common.castToInt
import xyz.lbres.exactnumbers.common.castToLong
import xyz.lbres.exactnumbers.common.castToShort
import xyz.lbres.exactnumbers.common.createCastingException
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
 *
 * @param numerator [BigInteger]: numerator of fraction
 * @param denominator [BigInteger]: denominator of fraction
 * @param fullySimplified [Boolean]: flag to indicate if numerator and denominator values are simplified
 */
class ExactFraction private constructor(numerator: BigInteger, denominator: BigInteger, fullySimplified: Boolean) : Comparable<ExactFraction>, Number() {
    /**
     * Numerator of number
     */
    val numerator: BigInteger

    /**
     * Denominator of number
     */
    val denominator: BigInteger

    init {
        if (denominator.isZero()) {
            throw divideByZero
        }

        if (fullySimplified) {
            this.numerator = numerator
            this.denominator = denominator
        } else {
            val simplifiedValues = simplify(Pair(numerator, denominator))
            this.numerator = simplifiedValues.first
            this.denominator = simplifiedValues.second
        }
    }

    /**
     * Constructor using numerator and denominator. Simplifies values on initialization.
     *
     * @param numerator [BigInteger]: numerator of fraction
     * @param denominator [BigInteger]: denominator of fraction
     */
    constructor (numerator: BigInteger, denominator: BigInteger) : this(numerator, denominator, fullySimplified = false)

    // UNARY OPERATORS

    operator fun unaryMinus(): ExactFraction = ExactFraction(-numerator, denominator, fullySimplified = true)
    operator fun unaryPlus(): ExactFraction = ExactFraction(numerator, denominator, fullySimplified = true)
    operator fun not(): Boolean = isZero()

    operator fun inc(): ExactFraction = ExactFraction(numerator + denominator, denominator, fullySimplified = true)
    operator fun dec(): ExactFraction = ExactFraction(numerator - denominator, denominator, fullySimplified = true)

    // BINARY OPERATORS

    operator fun plus(other: ExactFraction): ExactFraction = efAdd(this, other)
    operator fun plus(other: BigInteger): ExactFraction = plus(other.toExactFraction())
    operator fun plus(other: Long): ExactFraction = plus(other.toExactFraction())
    operator fun plus(other: Int): ExactFraction = plus(other.toExactFraction())

    operator fun minus(other: ExactFraction): ExactFraction = plus(-other)
    operator fun minus(other: BigInteger): ExactFraction = plus(-other)
    operator fun minus(other: Long): ExactFraction = plus(-other)
    operator fun minus(other: Int): ExactFraction = plus(-other)

    operator fun times(other: ExactFraction): ExactFraction = efTimes(this, other)
    operator fun times(other: BigInteger): ExactFraction = times(other.toExactFraction())
    operator fun times(other: Long): ExactFraction = times(other.toExactFraction())
    operator fun times(other: Int): ExactFraction = times(other.toExactFraction())

    operator fun div(other: ExactFraction): ExactFraction = times(other.inverse())
    operator fun div(other: BigInteger): ExactFraction = div(other.toExactFraction())
    operator fun div(other: Long): ExactFraction = div(other.toExactFraction())
    operator fun div(other: Int): ExactFraction = div(other.toExactFraction())

    override fun equals(other: Any?): Boolean {
        return other is ExactFraction && numerator == other.numerator && denominator == other.denominator
    }

    fun eq(other: Int): Boolean = isWholeNumber() && numerator.eq(other)
    fun eq(other: Long): Boolean = isWholeNumber() && numerator.eq(other)
    fun eq(other: BigInteger): Boolean = isWholeNumber() && numerator == other

    override fun compareTo(other: ExactFraction): Int = efCompare(this, other)
    operator fun compareTo(other: Int): Int = compareTo(other.toExactFraction())
    operator fun compareTo(other: Long): Int = compareTo(other.toExactFraction())
    operator fun compareTo(other: BigInteger): Int = compareTo(other.toExactFraction())

    fun pow(other: ExactFraction): ExactFraction = efPow(this, other)
    fun pow(other: Int): ExactFraction = pow(other.toExactFraction())
    fun pow(other: Long): ExactFraction = pow(other.toExactFraction())
    fun pow(other: BigInteger): ExactFraction = pow(other.toExactFraction())

    // UNARY NON-OPERATORS

    fun inverse(): ExactFraction {
        if (numerator.isZero()) {
            throw divideByZero
        }

        val signConverter = simpleIf(numerator.isNegative(), -BigInteger.ONE, BigInteger.ONE)
        return ExactFraction(denominator * signConverter, numerator * signConverter, fullySimplified = true)
    }

    fun absoluteValue(): ExactFraction = ExactFraction(numerator.abs(), denominator, fullySimplified = true)
    fun isNegative(): Boolean = numerator.isNegative()
    fun isZero(): Boolean = numerator.isZero()
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

    override fun toByte(): Byte = castToByte(toBigDecimal()) { createCastingException(this, "Byte") }
    override fun toChar(): Char = castToChar(toBigDecimal()) { createCastingException(this, "Char") }
    override fun toShort(): Short = castToShort(toBigDecimal()) { createCastingException(this, "Short") }
    override fun toInt(): Int = castToInt(toBigDecimal()) { createCastingException(this, "Int") }
    override fun toLong(): Long = castToLong(toBigDecimal()) { createCastingException(this, "Long") }
    override fun toFloat(): Float = castToFloat(toBigDecimal()) { createCastingException(this, "Float") }
    override fun toDouble(): Double = castToDouble(toBigDecimal()) { createCastingException(this, "Double") }

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
