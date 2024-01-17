package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.ext.divideBy
import xyz.lbres.exactnumbers.utils.createHashCode
import xyz.lbres.exactnumbers.utils.divideByZero
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

    override fun isRational(): Boolean {
        setLogs()
        // rational if both values are whole numbers
        return !numLog!!.toPlainString().contains('.') && !denomLog!!.toPlainString().contains('.')
    }

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

    // uses the formula log_b(x/y) = log_b(x) - log_b(y) to reduce loss of precision when casting to Double
    override fun getValue(): BigDecimal {
        setLogs()
        val logValue = numLog!! - denomLog!!
        return simpleIf(isInverted, { BigDecimal.ONE.divideBy(logValue) }, { logValue })
    }

    // TODO improve simplification using bases
    override fun getSimplified(): Pair<ExactFraction, Log> {
        if (simplified == null) {
            simplified = when {
                fullySimplified || isZero() || equals(ONE) -> Pair(ExactFraction.ONE, this)
                isRational() -> Pair(getRationalValue()!!, ONE)
                else -> Pair(ExactFraction.ONE, LogImpl(argument, base, isInverted, fullySimplified = true))
            }
        }

        return simplified!!
    }

    // multiplicative inverse, *not* inverse log
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
