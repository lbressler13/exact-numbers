package exactnumbers.irrationals.log

import common.divideBigDecimals
import common.throwDivideByZero
import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.common.Irrational
import exactnumbers.irrationals.common.div
import exactnumbers.irrationals.common.times
import exactnumbers.irrationals.pi.Pi
import expressions.term.Term
import kotlinutils.biginteger.ext.isZero
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.log
import kotlin.math.roundToInt

/**
 * Representation of a log, with an integer base and rational argument
 *
 * @param argument [ExactFraction]: value to compute log of
 * @param base [Int]: base to use when computing log
 * @param isDivided [Boolean]: if the inverse of the value should be calculated
 * @throws [ArithmeticException] if number is not positive, base is less than 1, or value is 0 when isDivided is true
 */
class Log(val argument: ExactFraction, val base: Int, override val isDivided: Boolean) : Comparable<Log>, Irrational {
    override val type = TYPE

    init {
        when {
            argument == ExactFraction.ONE && isDivided -> throwDivideByZero()
            argument.isZero() -> throw ArithmeticException("Cannot calculate log of 0")
            argument.isNegative() -> throw ArithmeticException("Cannot calculate log of negative number")
            base <= 1 -> throw ArithmeticException("Log base must be greater than 1")
        }
    }

    // constructors with reduced params
    constructor(argument: ExactFraction) : this(argument, base = 10, isDivided = false)
    constructor(argument: ExactFraction, base: Int) : this(argument, base, isDivided = false)
    constructor(argument: ExactFraction, isDivided: Boolean) : this(argument, 10, isDivided)

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Log) {
            return false
        }

        return getValue() == other.getValue()
    }

    // public methods to expose general Irrational operators
    operator fun times(other: Log): Term = times(other as Irrational)
    operator fun times(other: Pi): Term = times(other as Irrational)
    operator fun div(other: Log): Term = div(other as Irrational)
    operator fun div(other: Pi): Term = div(other as Irrational)

    override operator fun compareTo(other: Log): Int = getValue().compareTo(other.getValue())

    override fun isZero(): Boolean = argument == ExactFraction.ONE

    override fun isRational(): Boolean {
        val numLog = getLogOf(argument.numerator)
        val denomLog = getLogOf(argument.denominator)

        return numLog.toPlainString().indexOf('.') == -1 && denomLog.toPlainString().indexOf('.') == -1
    }

    override fun getRationalValue(): ExactFraction? {
        if (!isRational()) {
            return null
        }

        if (isZero()) {
            return ExactFraction.ZERO
        }

        val numLog = getLogOf(argument.numerator).toBigInteger()
        val denomLog = getLogOf(argument.denominator).toBigInteger()

        val result = when {
            numLog.isZero() -> ExactFraction(denomLog)
            denomLog.isZero() -> ExactFraction(numLog)
            else -> ExactFraction(numLog, denomLog)
        }

        return when {
            isDivided && argument < base -> -result.inverse()
            isDivided -> result.inverse()
            argument < base -> -result
            else -> result
        }
    }

    /**
     * Get value of log, using the expression log_b(x/y) = log_b(x) - log_b(y).
     * This reduces loss of precision when casting to Double.
     *
     * @return [BigDecimal]
     */
    override fun getValue(): BigDecimal {
        val logValue = getLogOf(argument.numerator) - getLogOf(argument.denominator)

        if (!isDivided) {
            return logValue
        }

        return divideBigDecimals(BigDecimal.ONE, logValue)
    }

    override fun swapDivided(): Log {
        if (isZero()) {
            throwDivideByZero()
        }

        return Log(argument, base, !isDivided)
    }

    /**
     * Get log value of a whole number, using the base assigned to this log
     *
     * @param [num] [BigInteger]: number to get log of
     * @return [BigDecimal]: the log of the number, using the current base
     * @throws [ArithmeticException] if the log returns NaN
     */
    internal fun getLogOf(num: BigInteger): BigDecimal {
        val logNum = log(num.toDouble(), base.toDouble())
        if (logNum.isNaN()) {
            throw ArithmeticException("Error calculating log of $argument")
        }

        // account for imprecision with doubles
        val upInt = ceil(logNum).roundToInt()
        val downInt = floor(logNum).roundToInt()

        return when (num) {
            base.toBigInteger().pow(upInt) -> upInt.toBigDecimal()
            base.toBigInteger().pow(downInt) -> downInt.toBigDecimal()
            else -> logNum.toBigDecimal()
        }
    }

    override fun toString(): String {
        val numString = if (argument.denominator == BigInteger.ONE) {
            argument.numerator.toString()
        } else {
            "${argument.numerator}/${argument.denominator}"
        }

        if (isDivided) {
            return "[1/log_$base($numString)]"
        }

        return "[log_$base($numString)]"
    }

    override fun hashCode(): Int = Pair(argument, base).hashCode()

    companion object {
        const val TYPE = "log"

        val ZERO = Log(ExactFraction.ONE)
        val ONE = Log(ExactFraction.TEN)

        /**
         * Simplify list of logs
         *
         * @param numbers [List<Irrational>]: list to simplify, expected to consist of only Logs
         * @return [List<Log>]: simplified list
         * @throws [ClassCastException] if any of the numbers are not a Log
         */
        // TODO improve simplification by looking at bases
        internal fun simplifyList(numbers: List<Irrational>?): Pair<ExactFraction, List<Log>> {
            if (numbers.isNullOrEmpty()) {
                return Pair(ExactFraction.ONE, listOf())
            }

            numbers as List<Log>

            if (numbers.any(Log::isZero)) {
                return Pair(ExactFraction.ZERO, listOf())
            }

            val groupedLogs = numbers.groupBy { it.isRational() }

            val rational: ExactFraction = groupedLogs[true]?.fold(ExactFraction.ONE) { acc, log ->
                acc * (log.getRationalValue() ?: ExactFraction.ONE)
            } ?: ExactFraction.ONE

            val newLogs = groupedLogs[false]?.filter { it != ONE } // remove ones
                ?.groupBy { Pair(it.argument, it.base) } // remove inverses
                ?.flatMap { pair ->
                    val currentLogs = pair.value

                    val countDivided = currentLogs.count { it.isDivided }
                    val countNotDivided = currentLogs.size - countDivided
                    when {
                        countDivided == countNotDivided -> listOf()
                        countDivided > countNotDivided -> List(countDivided - countNotDivided) {
                            Log(
                                pair.key.first,
                                pair.key.second,
                                true
                            )
                        }
                        else -> List(countNotDivided - countDivided) { Log(pair.key.first, pair.key.second, false) }
                    }
                } ?: listOf()

            return Pair(rational, newLogs.sorted())
        }
    }
}
