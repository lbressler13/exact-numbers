package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.common.divideByZero
import xyz.lbres.exactnumbers.ext.eq
import xyz.lbres.exactnumbers.ext.toExactFraction
import xyz.lbres.kotlinutils.biginteger.ext.ifZero
import xyz.lbres.kotlinutils.biginteger.ext.isNegative
import xyz.lbres.kotlinutils.biginteger.ext.isZero
import xyz.lbres.kotlinutils.biginteger.getGCD
import xyz.lbres.kotlinutils.general.simpleIf
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode

/**
 * Number implementation for exact representation of rational numbers, represented as a numerator and a denominator.
 */
class ExactFraction private constructor() : Comparable<ExactFraction>, Number() {
    // These values are re-assigned in all constructors
    private var _numerator: BigInteger = BigInteger.ZERO
    private var _denominator: BigInteger = BigInteger.ONE

    /**
     * Numerator of number
     */
    val numerator: BigInteger
        get() = _numerator

    /**
     * Denominator of number
     */
    val denominator: BigInteger
        get() = _denominator

    /**
     * Private constructor using numerator, denominator, and a flag to indicate if the provided values have been simplified.
     *
     * @param numerator [BigInteger]: numerator of fraction
     * @param denominator [BigInteger]: denominator of fraction
     * @param fullySimplified [Boolean]: flag to indicate if numerator and denominator values are simplified
     */
    private constructor (numerator: BigInteger, denominator: BigInteger, fullySimplified: Boolean) : this() {
        _numerator = numerator
        _denominator = denominator.ifZero { throw divideByZero }

        if (!fullySimplified) {
            simplify()
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

    operator fun plus(other: ExactFraction): ExactFraction {
        if (denominator == other.denominator) {
            val newNumerator = numerator + other.numerator
            return ExactFraction(newNumerator, denominator, fullySimplified = false)
        }

        val scaled1 = numerator * other.denominator
        val scaled2 = other.numerator * denominator

        val newNumerator = scaled1 + scaled2
        val newDenominator = denominator * other.denominator
        return ExactFraction(newNumerator, newDenominator, fullySimplified = false)
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
        return ExactFraction(newNumerator, newDenominator, fullySimplified = false)
    }

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

    // TODO pow with other types
    fun pow(other: ExactFraction): ExactFraction {
        if (other.denominator != BigInteger.ONE) {
            throw ArithmeticException("Exponents must be whole numbers")
        }

        when {
            equals(ZERO) -> return ZERO
            equals(ONE) || other == ZERO -> return ONE
            other == ONE -> return this
        }

        var powNumerator = BigInteger.ONE
        var powDenominator = BigInteger.ONE
        var remaining = other.absoluteValue().numerator.abs()
        val intMax = Int.MAX_VALUE

        try {
            while (remaining > BigInteger.ZERO) {
                if (remaining > intMax.toBigInteger()) {
                    powNumerator *= numerator.pow(intMax)
                    powDenominator *= denominator.pow(intMax)
                    remaining -= intMax.toBigInteger()
                } else {
                    val exp = remaining.toInt()
                    powNumerator = numerator.pow(exp)
                    powDenominator = denominator.pow(exp)
                    remaining = BigInteger.ZERO
                }
            }
        } catch (e: ArithmeticException) {
            if (e.message == "BigInteger would overflow supported range") {
                throw ExactFractionOverflowException()
            }

            throw e
        }

        val result = ExactFraction(powNumerator, powDenominator, fullySimplified = false)
        return simpleIf(other.isNegative(), { result.inverse() }, { result })
    }

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

    // SIMPLIFICATION

    /**
     * Simplify numerator and denominator to smallest values with same ratio, and move all negatives into numerator
     */
    private fun simplify() {
        // set denominator to 1 when numerator is 0
        if (numerator.isZero()) {
            _denominator = BigInteger.ONE
        }

        // move negatives to numerator
        if (denominator.isNegative()) {
            _numerator = -numerator
            _denominator = -denominator
        }

        // simplify using greatest common divisor
        if (numerator != BigInteger.ZERO) {
            val gcd = getGCD(numerator, denominator)
            _numerator /= gcd
            _denominator /= gcd
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
        if (isWholeNumber()) {
            return numerator.toString()
        }

        val whole: BigInteger = numerator / denominator
        val remainder: BigInteger = numerator - denominator * whole

        val mc = MathContext(digits, RoundingMode.HALF_UP)
        val remainderDecimal = remainder.toBigDecimal()
        val denominatorDecimal = denominator.toBigDecimal()
        val decimal = remainderDecimal.divide(denominatorDecimal, mc)
        return (whole.toBigDecimal() + decimal).toString()
    }

    /**
     * Create a fractional representation of the number, either as whole number or fraction
     *
     * @return string representation of number in fractional format
     */
    fun toFractionString() = simpleIf(isWholeNumber(), numerator.toString(), "$numerator/$denominator")

    fun toPairString(): String = "($numerator, $denominator)"
    fun toEFString(): String = "EF[$numerator $denominator]"

    override fun toString(): String = toEFString()

    // CASTING

    fun toPair(): Pair<BigInteger, BigInteger> = Pair(numerator, denominator)

    /**
     * Cast to a type of whole number, or throw [ExactFractionOverflowException] if fraction exceeds allowed range
     *
     * @param minValue [String]: minimum value in allowed range
     * @param maxValue [String]: maximum value in allowed range
     * @param type [String]: name of type to cast to
     * @param cast ([BigInteger]) -> T: perform cast on fraction
     */
    private fun <T> castToWholeNumber(minValue: String, maxValue: String, type: String, cast: (BigInteger) -> T): T {
        val value = toBigInteger()

        val minInt = BigInteger(minValue)
        val maxInt = BigInteger(maxValue)
        if (value in minInt..maxInt) {
            return cast(value)
        }

        throw ExactFractionOverflowException("Overflow when casting to $type", toFractionString())
    }

    /**
     * Cast to a type of decimal number, or throw [ExactFractionOverflowException] if fraction exceeds allowed range
     *
     * @param minValue [String]: minimum value in allowed range
     * @param maxValue [String]: maximum value in allowed range
     * @param type [String]: name of type to cast to
     * @param cast ([BigDecimal]) -> T: perform cast on fraction
     */
    private fun <T> castToDecimalNumber(minValue: String, maxValue: String, type: String, cast: (BigDecimal) -> T): T {
        val value = toBigDecimal()

        val minDecimal = BigDecimal(minValue)
        val maxDecimal = BigDecimal(maxValue)
        if (value in minDecimal..maxDecimal) {
            return cast(value)
        }

        throw ExactFractionOverflowException("Overflow when casting to $type", toFractionString())
    }

    /**
     * If value is between min and max Byte values, cast to Byt using simple division, rounding down.
     *
     * @return number as Byte
     * @throws ExactFractionOverflowException if value is outside range of Byte value
     */
    override fun toByte(): Byte {
        return castToWholeNumber(Byte.MIN_VALUE.toString(), Byte.MAX_VALUE.toString(), "Byte") { it.toByte() }
    }

    /**
     * If value is between min and max Char values, cast to Char using simple division, rounding down.
     * Accounts for Chars being non-negative
     *
     * @return number as Char
     * @throws ExactFractionOverflowException if value is outside range of Char value
     */
    override fun toChar(): Char {
        val minValue = Char.MIN_VALUE.code
        val maxValue = Char.MAX_VALUE.code
        return castToWholeNumber(minValue.toString(), maxValue.toString(), "Char") { it.toInt().toChar() }
    }

    /**
     * If value is between min and max Short values, cast to Short using simple division, rounding down.
     *
     * @return number as Short
     * @throws ExactFractionOverflowException if value is outside range of Short value
     */
    override fun toShort(): Short {
        return castToWholeNumber(Short.MIN_VALUE.toString(), Short.MAX_VALUE.toString(), "Short") { it.toShort() }
    }

    /**
     * If value is between min and max Int values, cast to Int using simple division, rounding down.
     *
     * @return number as Int
     * @throws ExactFractionOverflowException if value is outside range of Int value
     */
    override fun toInt(): Int {
        return castToWholeNumber(Int.MIN_VALUE.toString(), Int.MAX_VALUE.toString(), "Int") { it.toInt() }
    }

    /**
     * If value is between min and max Long values, cast to Long using simple division, rounding down.
     *
     * @return number as Long
     * @throws ExactFractionOverflowException if value is outside range of Long value
     */
    override fun toLong(): Long {
        return castToWholeNumber(Long.MIN_VALUE.toString(), Long.MAX_VALUE.toString(), "Long") { it.toLong() }
    }

    /**
     * If value is between min and max Double values, cast to Double, using division with precision of 20.
     *
     * @return number as Double
     * @throws ExactFractionOverflowException if value is outside range of Double value
     */
    override fun toDouble(): Double {
        val valueString = Double.MAX_VALUE.toString()
        return castToDecimalNumber("-$valueString", valueString, "Double") { it.toDouble() }
    }

    /**
     * If value is between min and max Float values, cast to Float, using division with precision of 20.
     *
     * @return number as Float
     * @throws ExactFractionOverflowException if value is outside range of Float value
     */
    override fun toFloat(): Float {
        val valueString = Float.MAX_VALUE.toString()
        return castToDecimalNumber("-$valueString", valueString, "Float") { it.toFloat() }
    }

    fun toBigInteger(): BigInteger = numerator / denominator
    fun toBigDecimal(precision: Int = 20): BigDecimal {
        val mc = MathContext(precision, RoundingMode.HALF_UP)
        return numerator.toBigDecimal().divide(denominator.toBigDecimal(), mc)
    }

    override fun hashCode(): Int {
        var result = 31 * numerator.hashCode()
        result = 31 * result + denominator.hashCode()
        result = 31 * result + javaClass.name.hashCode()
        return result
    }

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
