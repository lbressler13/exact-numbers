package xyz.lbres.exactnumbers.exactfraction

import xyz.lbres.exactnumbers.common.divideByZero
import xyz.lbres.kotlinutils.biginteger.ext.isNegative
import xyz.lbres.kotlinutils.biginteger.ext.isZero
import xyz.lbres.kotlinutils.general.simpleIf
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode

// implementation of ExactFraction class
internal class ExactFractionImpl private constructor(numerator: BigInteger, denominator: BigInteger, fullySimplified: Boolean) : ExactFraction() {
    override val numerator: BigInteger
    override val denominator: BigInteger

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

    constructor(numerator: BigInteger, denominator: BigInteger) : this(numerator, denominator, fullySimplified = false)

    // UNARY OPERATORS

    override operator fun unaryMinus(): ExactFraction = ExactFractionImpl(-numerator, denominator, fullySimplified = true)
    override operator fun unaryPlus(): ExactFraction = ExactFractionImpl(numerator, denominator, fullySimplified = true)
    override operator fun not(): Boolean = isZero()

    override fun inc(): ExactFraction = ExactFractionImpl(numerator + denominator, denominator, fullySimplified = true)
    override fun dec(): ExactFraction = ExactFractionImpl(numerator - denominator, denominator, fullySimplified = true)

    // BINARY OPERATORS

    override fun plus(other: ExactFraction): ExactFraction = efAdd(this, other)
    override fun minus(other: ExactFraction): ExactFraction = plus(-other)
    override fun times(other: ExactFraction): ExactFraction = efTimes(this, other)
    override fun div(other: ExactFraction): ExactFraction = times(other.inverse())

    override fun eq(other: BigInteger): Boolean = isWholeNumber() && numerator == other

    override fun compareTo(other: ExactFraction): Int = efCompare(this, other)

    override fun pow(other: ExactFraction): ExactFraction = efPow(this, other)

    // UNARY NON-OPERATORS

    override fun inverse(): ExactFraction {
        if (numerator.isZero()) {
            throw divideByZero
        }

        val signConverter = simpleIf(numerator.isNegative(), -BigInteger.ONE, BigInteger.ONE)
        return ExactFractionImpl(denominator * signConverter, numerator * signConverter, fullySimplified = true)
    }

    override fun absoluteValue(): ExactFraction = ExactFractionImpl(numerator.abs(), denominator, fullySimplified = true)
    override fun isNegative(): Boolean = numerator.isNegative()
    override fun isZero(): Boolean = numerator.isZero()
    override fun isWholeNumber(): Boolean = denominator == BigInteger.ONE

    override fun roundToWhole(roundingMode: RoundingMode): ExactFraction {
        val decimal = numerator.toBigDecimal().divide(denominator.toBigDecimal(), roundingMode)
        val int = decimal.toBigInteger()

        return ExactFractionImpl(int, BigInteger.ONE, fullySimplified = true)
    }

    // CASTING

    override fun toBigInteger(): BigInteger = numerator / denominator
    override fun toBigDecimal(precision: Int): BigDecimal {
        val mc = MathContext(precision, RoundingMode.HALF_UP)
        return numerator.toBigDecimal().divide(denominator.toBigDecimal(), mc)
    }
}
