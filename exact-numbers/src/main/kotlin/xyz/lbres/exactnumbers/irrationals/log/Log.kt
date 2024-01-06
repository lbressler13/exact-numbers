package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.common.createHashCode
import xyz.lbres.common.divideBigDecimals
import xyz.lbres.common.divideByZero
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumber
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumberCompanion
import xyz.lbres.kotlinutils.biginteger.ext.isZero
import xyz.lbres.kotlinutils.collection.ext.toConstMultiSet
import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.emptyConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.filterConsistent
import xyz.lbres.kotlinutils.set.multiset.filterNotToSetConsistent
import xyz.lbres.kotlinutils.set.multiset.filterToSetConsistent
import xyz.lbres.kotlinutils.set.multiset.mapToSetConsistent
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
 */
@Suppress("EqualsOrHashCode")
class Log private constructor(
    val argument: ExactFraction,
    val base: Int,
    override val isInverted: Boolean,
    private val fullySimplified: Boolean
) : IrrationalNumber<Log>() {
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
    constructor(argument: ExactFraction, isInverted: Boolean) : this(argument, base = 10, isInverted, false)
    constructor(argument: ExactFraction, base: Int, isInverted: Boolean) : this(argument, base, isInverted, false)

    // constructors with reduced params + other types
    constructor(argument: Int) : this(ExactFraction(argument))
    constructor(argument: Int, base: Int) : this(ExactFraction(argument), base)
    constructor(argument: Int, isInverted: Boolean) : this(ExactFraction(argument), isInverted)
    constructor(argument: Int, base: Int, isInverted: Boolean) : this(ExactFraction(argument), base, isInverted)
    constructor(argument: Long) : this(ExactFraction(argument))
    constructor(argument: Long, base: Int) : this(ExactFraction(argument), base)
    constructor(argument: Long, isInverted: Boolean) : this(ExactFraction(argument), isInverted)
    constructor(argument: Long, base: Int, isInverted: Boolean) : this(ExactFraction(argument), base, isInverted)
    constructor(argument: BigInteger) : this(ExactFraction(argument))
    constructor(argument: BigInteger, base: Int) : this(ExactFraction(argument), base)
    constructor(argument: BigInteger, isInverted: Boolean) : this(ExactFraction(argument), isInverted)
    constructor(argument: BigInteger, base: Int, isInverted: Boolean) : this(ExactFraction(argument), base, isInverted)

    override fun isZero(): Boolean = argument == ExactFraction.ONE

    /**
     * Determine if the value of the log is a rational number.
     *
     * @return [Boolean]: true if the value is rational, false otherwise
     */
    override fun checkIsRational(): Boolean {
        val numLog = getLogOf(argument.numerator, base)
        val denomLog = getLogOf(argument.denominator, base)

        // rational if both values are whole numbers
        return numLog.toPlainString().indexOf('.') == -1 && denomLog.toPlainString().indexOf('.') == -1
    }

    /**
     * Get the value of the log as a rational value if rational
     *
     * @return [ExactFraction]?: value of the log, or null if the value is irrational
     */
    override fun performGetRationalValue(): ExactFraction? {
        when {
            !isRational() -> return null
            isZero() -> return ExactFraction.ZERO
        }

        val numLog = getLogOf(argument.numerator, base).toBigInteger()
        val denomLog = getLogOf(argument.denominator, base).toBigInteger()

        val result = when {
            numLog.isZero() -> -ExactFraction(denomLog) // numerator of argument is 1
            denomLog.isZero() -> ExactFraction(numLog) // denominator of argument is 1
            else -> ExactFraction(numLog, denomLog)
        }

        return simpleIf(isInverted, { result.inverse() }, { result })
    }

    /**
     * Get value of log, using the expression log_b(x/y) = log_b(x) - log_b(y).
     * This reduces loss of precision when casting to Double.
     *
     * @return [BigDecimal]
     */
    override fun performGetValue(): BigDecimal {
        val logValue = getLogOf(argument.numerator, base) - getLogOf(argument.denominator, base)
        return simpleIf(isInverted, { divideBigDecimals(BigDecimal.ONE, logValue) }, { logValue })
    }

    /**
     * Simplify log into a coefficient and a log value.
     * Extracts rational value as coefficient and returns log as 1, or returns coefficient as 1 with the existing log for irrational logs.
     *
     * @return [Pair]<ExactFraction, Log>: a pair of coefficient and log such that the product has the same value as the current log
     */
    // TODO: improve the process of simplifying using exponents
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

        return simpleIf(isInverted, "[1/log_$base($numString)]", "[log_$base($numString)]")
    }

    override fun hashCode(): Int = createHashCode(listOf(argument, base, isInverted, this::class.toString()))

    companion object : IrrationalNumberCompanion<Log>() {
        override val TYPE = "log"

        val ZERO = Log(ExactFraction.ONE, 10, isInverted = false, fullySimplified = true)
        val ONE = Log(ExactFraction.TEN, 10, isInverted = false, fullySimplified = true)

        /**
         * Extract rational values and simplify remaining set of logs
         *
         * @param numbers [ConstMultiSet]<Log>: set to simplify
         * @return [Pair]<ExactFraction, ConstMultiSet<Log>>: product of rational values and simplified set of logs
         */
        // TODO: improve simplification by looking at bases
        override fun simplifySet(numbers: ConstMultiSet<Log>): Pair<ExactFraction, ConstMultiSet<Log>> {
            when {
                numbers.isEmpty() -> return Pair(ExactFraction.ONE, emptyConstMultiSet())
                numbers.any(Log::isZero) -> return Pair(ExactFraction.ZERO, emptyConstMultiSet())
            }

            val simplifiedNumbers = numbers.mapToSetConsistent { it.getSimplified() }
            val coefficient = simplifiedNumbers.fold(ExactFraction.ONE) { acc, pair -> acc * pair.first }

            val logValues: MultiSet<Log> = simplifiedNumbers.mapToSetConsistent { it.second }

            val invertedDistinct: Set<Log> = logValues
                .filterToSetConsistent { it.isInverted }
                .mapToSetConsistent { it.inverse() }
                .distinctValues

            val notInvertedDistinct: Set<Log> = logValues
                .filterNotToSetConsistent { it.isInverted }
                .distinctValues

            val bothTypesDistinct: Set<Log> = invertedDistinct intersect notInvertedDistinct // values that need to be simplified

            val notInvertedOnlyValues = logValues.filterConsistent { it != ONE && !it.isInverted && it !in bothTypesDistinct }
            val invertedOnlyValues = logValues.filterConsistent { it != ONE && it.isInverted && it.inverse() !in bothTypesDistinct }

            val allValues = bothTypesDistinct.map {
                val notInvertedCount = logValues.getCountOf(it)
                val invertedCount = logValues.getCountOf(it.inverse())
                val diff = notInvertedCount - invertedCount

                when {
                    it == ONE || diff == 0 -> emptyConstMultiSet()
                    diff > 0 -> ConstMultiSet(diff) { _ -> it }
                    else -> ConstMultiSet(abs(diff)) { _ -> it.inverse() }
                }
            }.fold(invertedOnlyValues + notInvertedOnlyValues) { acc, set -> acc + set }

            return Pair(coefficient, allValues.toConstMultiSet())
        }
    }
}
