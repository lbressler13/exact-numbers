package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.exactnumbers.common.createHashCode
import xyz.lbres.exactnumbers.common.divideByZero
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.ext.divideBy
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

    private var numLog: BigDecimal? = null
    private var denomLog: BigDecimal? = null
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

    /**
     * Determine if the value of the log is a rational number.
     *
     * @return [Boolean]: true if the value is rational, false otherwise
     */
    override fun isRational(): Boolean {
        setLogs()
        // rational if both values are whole numbers
        return numLog!!.toPlainString().indexOf('.') == -1 && denomLog!!.toPlainString().indexOf('.') == -1
    }

    /**
     * Get the value of the log as a rational value if rational
     *
     * @return [ExactFraction]?: value of the log, or null if the value is irrational
     */
    override fun getRationalValue(): ExactFraction? {
        when {
            !isRational() -> return null
            isZero() -> return ExactFraction.ZERO
        }

        setLogs()
        val numInt = numLog!!.toBigInteger()
        val denomInt = denomLog!!.toBigInteger()

        val result = when {
            numInt.isZero() -> -ExactFraction(denomInt) // numerator of argument is 1
            denomInt.isZero() -> ExactFraction(numInt) // denominator of argument is 1
            else -> ExactFraction(numInt, denomInt)
        }

        return simpleIf(isInverted, { result.inverse() }, { result })
    }

    /**
     * Get value of log, using the expression log_b(x/y) = log_b(x) - log_b(y).
     * This reduces loss of precision when casting to Double.
     *
     * @return [BigDecimal]
     */
    override fun getValue(): BigDecimal {
        setLogs()
        val logValue = numLog!! - denomLog!!
        return simpleIf(isInverted, { BigDecimal.ONE.divideBy(logValue) }, { logValue })
    }

    /**
     * Simplify log into a coefficient and a log value.
     * Extracts rational value as coefficient and returns log as 1, or returns coefficient as 1 with the existing log for irrational logs.
     *
     * @return [Pair]<ExactFraction, Log>: a pair of coefficient and log such that the product has the same value as the current log
     */
    override fun getSimplified(): Pair<ExactFraction, Log> {
        if (simplified == null) {
            when {
                fullySimplified -> return Pair(ExactFraction.ONE, this)
                isZero() -> return Pair(ExactFraction.ONE, ZERO)
                equals(ONE) -> return Pair(ExactFraction.ONE, ONE)
                isRational() -> return Pair(getRationalValue()!!, ONE)
            }

            simplified = Pair(ExactFraction.ONE, LogImpl(argument, base, isInverted, fullySimplified = true))
        }

        return simplified!!
    }

    /**
     * Get multiplicative inverse. This does not correspond to an inverse log.
     */
    override fun inverse(): Log {
        if (isZero()) {
            throw divideByZero
        }

        return LogImpl(argument, base, !isInverted, fullySimplified = false)
    }

    private fun setLogs() {
        if (numLog == null || denomLog == null) {
            numLog = getLogOf(argument.numerator, base)
            denomLog = getLogOf(argument.denominator, base)
        }
    }

    override fun toString(): String {
        val numString = argument.toFractionString()
        return simpleIf(isInverted, "[1/log_$base($numString)]", "[log_$base($numString)]")
    }

    override fun hashCode(): Int = createHashCode(listOf(argument, base, isInverted, type))
}
