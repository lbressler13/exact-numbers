package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.common.divideBigDecimals
import xyz.lbres.common.divideByZero
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.Irrational
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.expressions.term.Term
import xyz.lbres.kotlinutils.biginteger.ext.isZero
import xyz.lbres.kotlinutils.int.ext.isNegative
import xyz.lbres.kotlinutils.int.ext.isZero
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.emptyMultiSet
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.abs

/**
 * Representation of a log, with an integer base and rational argument
 *
 * @param argument [ExactFraction]: value to compute log of
 * @param base [Int]: base to use when computing log
 * @param isInverted [Boolean]: if the inverse of the value should be calculated
 * @param fullySimplified [Boolean]: if the value has already been simplified, such that getSimplified will return the same value
 * @throws [ArithmeticException] if number is not positive, base is less than 1, or value is 0 when isDivided is true
 */
class Log private constructor(
    val argument: ExactFraction,
    val base: Int,
    override val isInverted: Boolean,
    private val fullySimplified: Boolean
) : Irrational<Log>() {
    override val type = TYPE

    init {
        when {
            argument == ExactFraction.ONE && isInverted -> throw divideByZero
            argument.isZero() -> throw ArithmeticException("Cannot calculate log of 0")
            argument.isNegative() -> throw ArithmeticException("Cannot calculate log of negative number")
            base <= 1 -> throw ArithmeticException("Log base must be greater than 1")
        }
    }

    // constructors with reduced params + other types
    constructor(argument: ExactFraction) : this(argument, base = 10, isInverted = false, false)
    constructor(argument: ExactFraction, base: Int) : this(argument, base, isInverted = false, false)
    constructor(argument: Int) : this(ExactFraction(argument))
    constructor(argument: Int, base: Int) : this(ExactFraction(argument), base)
    constructor(argument: Long) : this(ExactFraction(argument))
    constructor(argument: Long, base: Int) : this(ExactFraction(argument), base)
    constructor(argument: BigInteger) : this(ExactFraction(argument))
    constructor(argument: BigInteger, base: Int) : this(ExactFraction(argument), base)

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Log) {
            return false
        }

        return getValue() == other.getValue()
    }

    operator fun times(other: ExactFraction): Term = Term.fromValues(other, listOf(this))
    operator fun times(other: Log): Term = Term.fromValues(listOf(this, other))
    operator fun times(other: Pi): Term = Term.fromValues(listOf(this), listOf(other))
    operator fun times(other: Sqrt): Term = Term.fromValues(listOf(this), listOf(other))
    operator fun div(other: ExactFraction): Term = Term.fromValues(other.inverse(), listOf(this))
    operator fun div(other: Log): Term = Term.fromValues(listOf(this, other.inverse()))
    operator fun div(other: Pi): Term = Term.fromValues(listOf(this), listOf(other.inverse()))
    operator fun div(other: Sqrt): Term = Term.fromValues(listOf(this), listOf(other.inverse()))

    override operator fun compareTo(other: Log): Int = getValue().compareTo(other.getValue())

    override fun isZero(): Boolean = argument == ExactFraction.ONE

    /**
     * Determine if the value of the log is a rational number.
     *
     * @return [Boolean]: true if the value is rational, false otherwise
     */
    override fun isRational(): Boolean {
        val numLog = getLogOf(argument.numerator, base)
        val denomLog = getLogOf(argument.denominator, base)

        // rational if both values are whole numbers
        return numLog.toPlainString().indexOf('.') == -1 && denomLog.toPlainString().indexOf('.') == -1
    }

    /**
     * Get the value of the log as a rational value if rational
     *
     * @return [ExactFraction?]: value of the log, or null if the value is irrational
     */
    override fun getRationalValue(): ExactFraction? {
        if (!isRational()) {
            return null
        }

        if (isZero()) {
            return ExactFraction.ZERO
        }

        val numLog = getLogOf(argument.numerator, base).toBigInteger()
        val denomLog = getLogOf(argument.denominator, base).toBigInteger()

        val result = when {
            numLog.isZero() -> -ExactFraction(denomLog) // numerator of argument is 1
            denomLog.isZero() -> ExactFraction(numLog) // denominator of argument is 1
            else -> ExactFraction(numLog, denomLog)
        }

        return if (isInverted) {
            result.inverse()
        } else {
            result
        }
    }

    /**
     * Get value of log, using the expression log_b(x/y) = log_b(x) - log_b(y).
     * This reduces loss of precision when casting to Double.
     *
     * @return [BigDecimal]
     */
    override fun getValue(): BigDecimal {
        val logValue = getLogOf(argument.numerator, base) - getLogOf(argument.denominator, base)

        if (!isInverted) {
            return logValue
        }

        return divideBigDecimals(BigDecimal.ONE, logValue)
    }

    /**
     * Simplify log into a coefficient and a log value.
     * Extracts rational value as coefficient and returns log as 1, or returns coefficient as 1 with the existing log for irrational logs.
     *
     * @return [Pair<ExactFraction, Log>]: a pair of coefficient and log such that the product has the same value as the current log
     */
    // TODO: improve the process of simplifying
    fun getSimplified(): Pair<ExactFraction, Log> {
        when {
            fullySimplified -> return Pair(ExactFraction.ONE, this)
            isZero() -> return Pair(ExactFraction.ONE, ZERO)
            equals(ONE) -> return Pair(ExactFraction.ONE, ONE)
        }

        val rational = getRationalValue()
        if (rational == null) {
            return Pair(ExactFraction.ONE, Log(argument, base, isInverted, true))
        }

        return Pair(rational, ONE)
    }

    /**
     * Create log with value 1/this.
     *
     * **This does not correspond to an inverse log**
     *
     * @return [Log]: log with value 1/this
     */
    override fun inverse(): Log {
        if (isZero()) {
            throw divideByZero
        }

        return Log(argument, base, !isInverted, false)
    }

    override fun toString(): String {
        val numString = if (argument.denominator == BigInteger.ONE) {
            argument.numerator.toString()
        } else {
            "${argument.numerator}/${argument.denominator}"
        }

        if (isInverted) {
            return "[1/log_$base($numString)]"
        }

        return "[log_$base($numString)]"
    }

    override fun hashCode(): Int = listOf(TYPE, argument, base, isInverted).hashCode()

    companion object {
        const val TYPE = "log"

        val ZERO = Log(ExactFraction.ONE, 10, isInverted = false, fullySimplified = true)
        val ONE = Log(ExactFraction.TEN, 10, isInverted = false, fullySimplified = true)

        /**
         * Extract rational values and simplify remaining list of logs
         *
         * @param numbers [List<Log>]: list to simplify
         * @return [Pair<ExactFraction, List<Log>>]: product of rational values and simplified list of logs
         * @throws [ClassCastException] if any of the numbers are not a Log
         */
        // TODO: improve simplification by looking at bases
        internal fun simplifySet(numbers: MultiSet<Log>): Pair<ExactFraction, MultiSet<Log>> {
            when {
                numbers.isEmpty() -> return Pair(ExactFraction.ONE, emptyMultiSet())
                numbers.any(Log::isZero) -> return Pair(ExactFraction.ZERO, emptyMultiSet())
            }

            val simplifiedNumbers = numbers.map { it.getSimplified() }
            val coefficient = simplifiedNumbers.fold(ExactFraction.ONE) { acc, pair -> acc * pair.first }

            val logValues: MultiSet<Log> = simplifiedNumbers.map { it.second }

            val invertedDistinct: Set<Log> = logValues
                .filter { it.isInverted }
                .map { it.inverse() }
                .distinctValues

            val notInvertedDistinct: Set<Log> = logValues
                .filterNot { it.isInverted }
                .distinctValues

            val bothTypesDistinct: Set<Log> = invertedDistinct intersect notInvertedDistinct // values that need to be simplified

            val notInvertedOnlyValues = logValues.filter { it != ONE && !it.isInverted && it !in bothTypesDistinct }
            val invertedOnlyValues = logValues.filter { it != ONE && it.isInverted && it.inverse() !in bothTypesDistinct }

            val allValues = bothTypesDistinct.map {
                val notInvertedCount = logValues.getCountOf(it)
                val invertedCount = logValues.getCountOf(it.inverse())
                val diff = notInvertedCount - invertedCount

                when {
                    it == ONE || diff == 0 -> emptyMultiSet()
                    diff > 0 -> MultiSet(diff) { _ -> it }
                    else -> MultiSet(abs(diff)) { _ -> it.inverse() }
                }
            }.fold(invertedOnlyValues + notInvertedOnlyValues) { acc, set -> acc + set }

            return Pair(coefficient, allValues)
        }

        /**
         * Extract rational values and simplify remaining list of logs
         *
         * @param numbers [List<Log>]: list to simplify
         * @return [Pair<ExactFraction, List<Log>>]: product of rational values and simplified list of logs
         * @throws [ClassCastException] if any of the numbers are not a Log
         */
        // TODO: improve simplification by looking at bases
        internal fun simplifyList(numbers: List<Log>?): Pair<ExactFraction, List<Log>> {
            if (numbers.isNullOrEmpty()) {
                return Pair(ExactFraction.ONE, emptyList())
            }

            if (numbers.any(Log::isZero)) {
                return Pair(ExactFraction.ZERO, emptyList())
            }

            val simplifiedNums = numbers.map { it.getSimplified() }
            val coeff = simplifiedNums.fold(ExactFraction.ONE) { acc, pair -> acc * pair.first }

            val combinedNums: List<Log> = simplifiedNums.map { it.second }
                .groupBy { Pair(it.argument, it.base) }
                .flatMap { pair ->
                    if (Log(pair.key.first, pair.key.second) == ONE) {
                        emptyList()
                    } else {
                        val currentLogs = pair.value
                        val countDivided = currentLogs.count { it.isInverted }
                        val countNotDivided = currentLogs.size - countDivided

                        when {
                            countDivided == countNotDivided -> emptyList()
                            countDivided > countNotDivided -> List(countDivided - countNotDivided) {
                                Log(pair.key.first, pair.key.second, isInverted = true, fullySimplified = true)
                            }
                            else -> List(countNotDivided - countDivided) {
                                Log(
                                    pair.key.first,
                                    pair.key.second,
                                    isInverted = false,
                                    fullySimplified = true
                                )
                            }
                        }
                    }
                }

            return Pair(coeff, combinedNums)
        }
    }
}
