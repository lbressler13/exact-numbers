package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.ext.divideBy
import xyz.lbres.exactnumbers.ext.isWholeNumber
import xyz.lbres.exactnumbers.utils.createHashCode
import xyz.lbres.exactnumbers.utils.divideByZero
import xyz.lbres.exactnumbers.utils.getOrSet
import xyz.lbres.kotlinutils.biginteger.ext.isZero
import xyz.lbres.kotlinutils.general.simpleIf
import java.math.BigDecimal

@Suppress("EqualsOrHashCode")
// implementation of Log class
internal class LogImpl private constructor(
    override val argument: ExactFraction,
    override val base: Int,
    override val isInverted: Boolean,
    private val fullySimplified: Boolean
) : Log() {
    override val type = TYPE

    private var numeratorLog: BigDecimal? = null
    private var denominatorLog: BigDecimal? = null
    private var simplified: Pair<ExactFraction, Log>? = null

    init {
        when {
            argument == ExactFraction.ONE && isInverted -> throw divideByZero
            argument.isZero() -> throw ArithmeticException("Cannot calculate log of 0")
            argument.isNegative() -> throw ArithmeticException("Cannot calculate log of negative number")
            base <= 1 -> throw ArithmeticException("Log base must be greater than 1")
        }
    }

    constructor(argument: ExactFraction, base: Int, isInverted: Boolean) : this(argument, base, isInverted, false)

    override fun isZero(): Boolean = argument == ExactFraction.ONE

    override fun isRational(): Boolean {
        setLogs()
        // rational if both values are whole numbers
        return numeratorLog!!.isWholeNumber() && denominatorLog!!.isWholeNumber()
    }

    override fun getRationalValue(): ExactFraction? {
        when {
            !isRational() -> return null
            isZero() -> return ExactFraction.ZERO
        }

        setLogs()
        val numInt = numeratorLog!!.toBigInteger()
        val denomInt = denominatorLog!!.toBigInteger()

        val result = when {
            numInt.isZero() -> -ExactFraction(denomInt) // numerator of argument is 1
            denomInt.isZero() -> ExactFraction(numInt) // denominator of argument is 1
            else -> ExactFraction(numInt, denomInt)
        }

        return simpleIf(isInverted, { result.inverse() }, { result })
    }

    // uses the formula log_b(x/y) = log_b(x) - log_b(y) to increase precision
    override fun getValue(): BigDecimal {
        setLogs()
        val logValue = numeratorLog!! - denominatorLog!!
        return simpleIf(isInverted, { BigDecimal.ONE.divideBy(logValue) }, { logValue })
    }

    // TODO improve simplification using bases
    override fun getSimplified(): Pair<ExactFraction, Log> {
        return getOrSet({ simplified }, { simplified = it }) {
            when {
                fullySimplified || isZero() || equals(ONE) -> Pair(ExactFraction.ONE, this)
                isRational() -> Pair(getRationalValue()!!, ONE)
                else -> Pair(ExactFraction.ONE, LogImpl(argument, base, isInverted, fullySimplified = true))
            }
        }
    }

    // multiplicative inverse, *not* inverse log
    override fun inverse(): Log {
        if (isZero()) {
            throw divideByZero
        }

        return LogImpl(argument, base, !isInverted, fullySimplified = false)
    }

    private fun setLogs() {
        if (numeratorLog == null || denominatorLog == null) {
            numeratorLog = getLogOf(argument.numerator, base)
            denominatorLog = getLogOf(argument.denominator, base)
        }
    }

    override fun toString(): String {
        val argString = argument.toFractionString()
        return simpleIf(isInverted, "[1/log_$base($argString)]", "[log_$base($argString)]")
    }

    override fun hashCode(): Int = createHashCode(listOf(argument, base, isInverted, type))
}
