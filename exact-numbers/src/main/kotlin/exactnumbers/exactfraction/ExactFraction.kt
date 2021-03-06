package exactnumbers.exactfraction

import common.divideByZero
import common.getGCD
import exactnumbers.ext.eq
import exactnumbers.ext.toExactFraction
import kotlinutils.biginteger.ext.ifZero
import kotlinutils.biginteger.ext.isNegative
import kotlinutils.biginteger.ext.isZero
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode

/**
 * Custom number implementation inspired by BigDecimal.
 * Exact representations of rational numbers, without specifying decimal precision.
 */
class ExactFraction private constructor() : Comparable<ExactFraction>, Number() {
    // These values are re-assigned in all public constructors
    var numerator: BigInteger = BigInteger.ZERO
    var denominator: BigInteger = BigInteger.ONE

    // CONSTRUCTORS

    /**
     * Constructor using only numerator.
     * Creates ExactFraction with integer value.
     *
     * @param numerator [BigInteger]: numerator of fraction
     */
    constructor (numerator: BigInteger) : this() {
        this.numerator = numerator
        this.denominator = BigInteger.ONE
    }

    /**
     * Constructor using numerator and denominator.
     * Simplifies fraction on creation
     *
     * @param numerator [BigInteger]: numerator of fraction
     * @param denominator [BigInteger]: denominator of fraction
     * @throws ArithmeticException if denominator is 0
     */
    constructor (numerator: BigInteger, denominator: BigInteger) : this() {
        this.numerator = numerator
        this.denominator = denominator.ifZero { throw divideByZero }
        simplify()
    }

    /**
     * Constructor which parses value from string
     *
     * @param s [String]: string to parse
     * @throws NumberFormatException if s is not in a parsable format
     */
    constructor (s: String) : this() {
        // result was simplified when initialized, no need to re-simplify here
        val result = parse(s)
        numerator = result.numerator
        denominator = result.denominator
    }

    // constructors for combinations of Int, Long, and BigInteger
    constructor (numerator: Int) : this(numerator.toBigInteger())
    constructor (numerator: Long) : this(numerator.toBigInteger())
    constructor (numerator: Int, denominator: Int) : this(numerator.toBigInteger(), denominator.toBigInteger())
    constructor (numerator: Long, denominator: Long) : this(numerator.toBigInteger(), denominator.toBigInteger())
    constructor (numerator: Int, denominator: Long) : this(numerator.toBigInteger(), denominator.toBigInteger())
    constructor (numerator: Long, denominator: Int) : this(numerator.toBigInteger(), denominator.toBigInteger())
    constructor (numerator: BigInteger, denominator: Int) : this(numerator, denominator.toBigInteger())
    constructor (numerator: Int, denominator: BigInteger) : this(numerator.toBigInteger(), denominator)
    constructor (numerator: BigInteger, denominator: Long) : this(numerator, denominator.toBigInteger())
    constructor (numerator: Long, denominator: BigInteger) : this(numerator.toBigInteger(), denominator)

    // UNARY OPERATORS

    operator fun unaryMinus(): ExactFraction = ExactFraction(-numerator, denominator)
    operator fun unaryPlus(): ExactFraction = ExactFraction(numerator, denominator)
    operator fun not(): Boolean = isZero()

    operator fun inc(): ExactFraction {
        val newNumerator = numerator + denominator
        return ExactFraction(newNumerator, denominator)
    }

    operator fun dec(): ExactFraction {
        val newNumerator = numerator - denominator
        return ExactFraction(newNumerator, denominator)
    }

    // BINARY OPERATORS

    operator fun plus(other: ExactFraction): ExactFraction {
        if (denominator == other.denominator) {
            val newNumerator = numerator + other.numerator
            return ExactFraction(newNumerator, denominator)
        }

        val scaled1 = numerator * other.denominator
        val scaled2 = other.numerator * denominator

        val newNumerator = scaled1 + scaled2
        val newDenominator = denominator * other.denominator
        return ExactFraction(newNumerator, newDenominator)
    }

    operator fun plus(other: BigInteger): ExactFraction = plus(other.toExactFraction())
    operator fun plus(other: Long): ExactFraction = plus(other.toExactFraction())
    operator fun plus(other: Int): ExactFraction = plus(other.toExactFraction())

    operator fun minus(other: ExactFraction): ExactFraction = plus(-other)
    operator fun minus(other: BigInteger): ExactFraction = plus(-other)
    operator fun minus(other: Long): ExactFraction = plus(-other)
    operator fun minus(other: Int): ExactFraction = plus(-other)

    operator fun times(other: ExactFraction): ExactFraction {
        val newNumerator = numerator * other.numerator
        val newDenominator = denominator * other.denominator
        return ExactFraction(newNumerator, newDenominator)
    }

    operator fun times(other: BigInteger): ExactFraction = times(other.toExactFraction())
    operator fun times(other: Long): ExactFraction = times(other.toExactFraction())
    operator fun times(other: Int): ExactFraction = times(other.toExactFraction())

    operator fun div(other: ExactFraction): ExactFraction = times(other.inverse())

    operator fun div(other: BigInteger): ExactFraction = div(other.toExactFraction())
    operator fun div(other: Long): ExactFraction = div(other.toExactFraction())
    operator fun div(other: Int): ExactFraction = div(other.toExactFraction())

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is ExactFraction) {
            return false
        }

        val scaled1 = numerator * other.denominator
        val scaled2 = other.numerator * denominator
        return scaled1 == scaled2
    }

    fun eq(other: Int): Boolean = numerator.eq(other) && denominator.eq(1)
    fun eq(other: Long): Boolean = numerator.eq(other) && denominator.eq(1)
    fun eq(other: BigInteger): Boolean = numerator == other && denominator.eq(1)

    override operator fun compareTo(other: ExactFraction): Int {
        val difference = minus(other)
        return when {
            difference.isNegative() -> -1
            difference.isZero() -> 0
            else -> 1
        }
    }

    operator fun compareTo(other: Int): Int = compareTo(other.toExactFraction())
    operator fun compareTo(other: Long): Int = compareTo(other.toExactFraction())
    operator fun compareTo(other: BigInteger): Int = compareTo(other.toExactFraction())

    fun pow(other: ExactFraction): ExactFraction {
        if (other.isZero()) {
            return ONE
        }

        if (other.denominator != BigInteger.ONE) {
            throw ArithmeticException("Exponents must be whole numbers")
        }

        var numeratorMult = BigInteger.ONE
        var denominatorMult = BigInteger.ONE
        var remaining = other.absoluteValue().numerator.abs()
        val intMax = Int.MAX_VALUE

        while (remaining > BigInteger.ZERO) {
            if (remaining > intMax.toBigInteger()) {
                numeratorMult *= numerator.pow(intMax)
                denominatorMult *= denominator.pow(intMax)
                remaining -= intMax.toBigInteger()
            } else {
                val exp = remaining.toInt()
                numeratorMult = numerator.pow(exp)
                denominatorMult = denominator.pow(exp)
                remaining = BigInteger.ZERO
            }
        }

        val result = ExactFraction(numeratorMult, denominatorMult)
        return if (other < 0) result.inverse() else result
    }

    // UNARY NON-OPERATORS

    fun inverse(): ExactFraction {
        if (numerator.isZero()) {
            throw divideByZero
        }

        return ExactFraction(denominator, numerator)
    }

    fun absoluteValue(): ExactFraction = ExactFraction(numerator.abs(), denominator)
    fun isNegative(): Boolean = numerator.isNegative()
    fun isZero(): Boolean = numerator.isZero()

    // SIMPLIFICATION

    private fun simplify() {
        simplifyZero()
        simplifyGCD()
        simplifySign()
    }

    /**
     * Set denominator to 1 when numerator is 0
     */
    private fun simplifyZero() {
        if (numerator.eq(0)) {
            denominator = BigInteger.ONE
        }
    }

    /**
     * Move negatives to numerator
     */
    private fun simplifySign() {
        val numNegative = numerator.isNegative()
        val denomNegative = denominator.isNegative()

        when {
            numNegative && denomNegative -> {
                numerator = numerator.abs()
                denominator = denominator.abs()
            }
            !numNegative && denomNegative -> {
                numerator = -numerator
                denominator = denominator.abs()
            }
        }
    }

    /**
     * Simplify using greatest common divisor
     */
    private fun simplifyGCD() {
        if (!numerator.isZero()) {
            val gcd = getGCD(numerator, denominator)
            numerator /= gcd
            denominator /= gcd
        }
    }

    // STRING METHODS

    /**
     * Create a string representation in standard decimal format
     *
     * @param digits [Int]: digits of precision in string. Defaults to 8.
     * Will be ignored if this number results in a string in exponential format
     * @return string representation in decimal format
     */
    fun toDecimalString(digits: Int = 8): String {
        if (denominator.eq(1)) {
            return numerator.toString()
        }

        val whole: BigInteger = numerator / denominator
        val remainder: BigInteger = numerator - denominator * whole

        val mc = MathContext(digits, RoundingMode.HALF_UP)
        val remainderDecimal = remainder.toBigDecimal()
        val denomDecimal = denominator.toBigDecimal()
        val decimal = remainderDecimal.divide(denomDecimal, mc)
        return (whole.toBigDecimal() + decimal).toString()
    }

    /**
     * Create a fractional representation of the number, either as whole number or fraction
     *
     * @return string representation of number in fractional format
     */
    fun toFractionString(): String = if (denominator.eq(1)) {
        numerator.toString()
    } else {
        "$numerator/$denominator"
    }

    fun toPairString(): String = "($numerator, $denominator)"
    fun toEFString(): String = "EF[$numerator $denominator]"

    override fun toString(): String = toEFString()

    override fun hashCode(): Int = toPair().hashCode()

    // CASTING

    fun toPair(): Pair<BigInteger, BigInteger> = Pair(numerator, denominator)

    /**
     * If value is between min and max Byte values, cast to Byt using simple division, rounding down.
     *
     * @return number as Byte
     * @throws ExactFractionOverflowException if value is outside range of Byte value
     */
    override fun toByte(): Byte {
        val value = numerator / denominator
        val maxByte = Byte.MAX_VALUE.toInt().toBigInteger()
        val minByte = Byte.MIN_VALUE.toInt().toBigInteger()
        if (value in minByte..maxByte) {
            return value.toByte()
        }

        throw overflowException("Byte")
    }

    /**
     * If value is between min and max Char values, cast to Char using simple division, rounding down.
     * Accounts for Chars being non-negative
     *
     * @return number as Char
     * @throws ExactFractionOverflowException if value is outside range of Char value
     */
    override fun toChar(): Char {
        val value = numerator / denominator
        val maxChar = Char.MAX_VALUE.code.toBigInteger()
        val minChar = Char.MIN_VALUE.code.toBigInteger()
        if (value in minChar..maxChar) {
            return value.toInt().toChar()
        }

        throw overflowException("Char")
    }

    /**
     * If value is between min and max Short values, cast to Short using simple division, rounding down.
     *
     * @return number as Short
     * @throws ExactFractionOverflowException if value is outside range of Short value
     */
    override fun toShort(): Short {
        val value = numerator / denominator
        val maxShort = Short.MAX_VALUE.toInt().toBigInteger()
        val minShort = Short.MIN_VALUE.toInt().toBigInteger()
        if (value in minShort..maxShort) {
            return value.toShort()
        }

        throw overflowException("Short")
    }

    /**
     * If value is between min and max Int values, cast to Int using simple division, rounding down.
     *
     * @return number as Int
     * @throws ExactFractionOverflowException if value is outside range of Int value
     */
    override fun toInt(): Int {
        val value = numerator / denominator
        val maxInt = Int.MAX_VALUE.toBigInteger()
        val minInt = Int.MIN_VALUE.toBigInteger()
        if (value in minInt..maxInt) {
            return value.toInt()
        }

        throw overflowException("Int")
    }

    /**
     * If value is between min and max Long values, cast to Long using simple division, rounding down.
     *
     * @return number as Long
     * @throws ExactFractionOverflowException if value is outside range of Long value
     */
    override fun toLong(): Long {
        val value = numerator / denominator
        val maxLong = Long.MAX_VALUE.toBigInteger()
        val minLong = Long.MIN_VALUE.toBigInteger()
        if (value in minLong..maxLong) {
            return value.toLong()
        }

        throw overflowException("Long")
    }

    /**
     * If value is between min and max Double values, cast to Double, using division with precision of 20.
     *
     * @return number as Double
     * @throws ExactFractionOverflowException if value is outside range of Double value
     */
    override fun toDouble(): Double {
        val mc = MathContext(20, RoundingMode.HALF_UP)
        val numDecimal = numerator.toBigDecimal()
        val denomDecimal = denominator.toBigDecimal()
        val value = numDecimal.divide(denomDecimal, mc)

        val maxDouble = Double.MAX_VALUE.toBigDecimal()
        val minDouble = -maxDouble

        if (value in minDouble..maxDouble) {
            return value.toDouble()
        }

        throw overflowException("Double")
    }

    /**
     * If value is between min and max Float values, cast to Float, using division with precision of 20.
     *
     * @return number as Float
     * @throws ExactFractionOverflowException if value is outside range of Float value
     */
    override fun toFloat(): Float {
        val mc = MathContext(20, RoundingMode.HALF_UP)
        val numDecimal = numerator.toBigDecimal()
        val denomDecimal = denominator.toBigDecimal()
        val value = numDecimal.divide(denomDecimal, mc)

        val maxFloat = Float.MAX_VALUE.toBigDecimal()
        val minFloat = -maxFloat

        if (value in minFloat..maxFloat) {
            return value.toFloat()
        }

        throw overflowException("Float")
    }

    fun toBigInteger(): BigInteger = numerator / denominator
    fun toBigDecimal(precision: Int = 20): BigDecimal {
        val mc = MathContext(precision, RoundingMode.HALF_UP)
        return numerator.toBigDecimal().divide(denominator.toBigDecimal(), mc)
    }

    private fun overflowException(type: String): Exception = ExactFractionOverflowException("Overflow when casting to $type", toFractionString())

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
