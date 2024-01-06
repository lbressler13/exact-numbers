package xyz.lbres.expressions.term

import xyz.lbres.common.divideBigDecimals
import xyz.lbres.common.divideByZero
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumber
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.generic.ext.ifNull
import xyz.lbres.kotlinutils.set.multiset.MultiSet
import xyz.lbres.kotlinutils.set.multiset.emptyMultiSet
import xyz.lbres.kotlinutils.set.multiset.multiSetOf
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.abs

// TODO still support old interfaces
// TODO allow other types of irrational numbers

/**
 * Representation of the product of several numbers, represented as a rational coefficient and lists of irrational numbers
 */
class Term {
    val coefficient: ExactFraction

    val logs: List<Log>
        get() = getGeneratedLogs()
    val squareRoots: List<Sqrt>
        get() = getGeneratedSquareRoots()
    val pis: List<Pi>
        get() = getGeneratedPis()
    val piCount: Int

    private var logsList: List<Log>? = null
    private var squareRootsList: List<Sqrt>? = null
    private var pisList: List<Pi>? = null

    private val logsSet: MultiSet<Log>
    private val squareRootsSet: MultiSet<Sqrt>
    private val pisSet: MultiSet<Pi>

    private var storedIsZero: Boolean? = null
    private var storedSimplified: Term? = null
    private var storedValue: BigDecimal? = null
    private var storedString: String? = null

    // Initialize values using lists of numbers. Accessible via the fromValues methods in the companion object.
    private constructor(coefficient: ExactFraction, logs: List<Log>, squareRoots: List<Sqrt>, pis: List<Pi>) {
        if (coefficient.isZero() || logs.any(Log::isZero) || squareRoots.any(Sqrt::isZero) || pis.any(Pi::isZero)) {
            this.coefficient = ExactFraction.ZERO
            logsSet = emptyMultiSet()
            squareRootsSet = emptyMultiSet()
            pisSet = emptyMultiSet()
            piCount = 0
        } else {
            this.coefficient = coefficient
            logsSet = multiSetOf(*logs.toTypedArray())
            squareRootsSet = multiSetOf(*squareRoots.toTypedArray())
            pisSet = multiSetOf(*pis.toTypedArray())
            piCount = calculatePiCount()
        }
    }

    // Initialize values using multisets. Only used inside the class, to avoid unnecessary casts when creating new terms after operations
    private constructor(coefficient: ExactFraction, logs: MultiSet<Log>, squareRoots: MultiSet<Sqrt>, pis: MultiSet<Pi>) {
        if (coefficient.isZero() || logs.any(Log::isZero) || squareRoots.any(Sqrt::isZero) || pis.any(Pi::isZero)) {
            this.coefficient = ExactFraction.ZERO
            logsSet = emptyMultiSet()
            squareRootsSet = emptyMultiSet()
            pisSet = emptyMultiSet()
            piCount = 0
        } else {
            this.coefficient = coefficient
            logsSet = logs
            squareRootsSet = squareRoots
            pisSet = pis
            piCount = calculatePiCount()
        }
    }

    operator fun unaryMinus(): Term = Term(-coefficient, logsSet, squareRootsSet, pisSet)
    operator fun unaryPlus(): Term = Term(coefficient, logsSet, squareRootsSet, pisSet)

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Term) {
            return false
        }

        val simplified = getSimplified()
        val otherSimplified = other.getSimplified()

        return simplified.coefficient == otherSimplified.coefficient &&
            simplified.piCount == otherSimplified.piCount &&
            simplified.logsSet == otherSimplified.logsSet &&
            simplified.squareRootsSet == otherSimplified.squareRootsSet
    }

    operator fun times(other: Term): Term {
        return Term(
            coefficient * other.coefficient,
            logsSet + other.logsSet,
            squareRootsSet + other.squareRootsSet,
            pisSet + other.pisSet
        )
    }

    operator fun div(other: Term): Term {
        if (other.isZero()) {
            throw divideByZero
        }

        return Term(
            coefficient / other.coefficient,
            logsSet + other.logsSet.map(Log::inverse),
            squareRootsSet + other.squareRootsSet.map(Sqrt::inverse),
            pisSet + other.pisSet.map(Pi::inverse)
        )
    }

    fun isZero(): Boolean {
        return storedIsZero.ifNull {
            val result =
                // TODO update with set after any operator is implemented
                coefficient.isZero() || logs.any(Log::isZero) || squareRoots.any(Sqrt::isZero) || pis.any(Pi::isZero)
            storedIsZero = result
            result
        }
    }

    /**
     * Simplify all numbers, based on the simplify function for their type
     *
     * @return [Term] simplified version of this term
     */
    fun getSimplified(): Term {
        return storedSimplified.ifNull {
            val simplifiedLogs = Log.simplifySet(logsSet)
            val simplifiedSqrts = Sqrt.simplifySet(squareRootsSet)
            val simplifiedPis = Pi.simplifySet(pisSet)
            val newCoefficient = coefficient * simplifiedLogs.first * simplifiedSqrts.first * simplifiedPis.first

            val result = Term(newCoefficient, simplifiedLogs.second, simplifiedSqrts.second, simplifiedPis.second)
            storedSimplified = result
            result
        }
    }

    /**
     * Get value of term by multiplying numbers.
     * Term is simplified before any computation is run
     *
     * @return [BigDecimal]
     */
    fun getValue(): BigDecimal {
        return storedValue.ifNull {
            val simplified = getSimplified()

            val logProduct = simplified.logsSet.fold(BigDecimal.ONE) { acc, num -> acc * num.getValue() }
            val sqrtProduct = simplified.squareRootsSet.fold(BigDecimal.ONE) { acc, num -> acc * num.getValue() }
            val piProduct = simplified.pisSet.fold(BigDecimal.ONE) { acc, num -> acc * num.getValue() }
            val numeratorProduct = logProduct * sqrtProduct * piProduct * simplified.coefficient.numerator.toBigDecimal()

            val result = divideBigDecimals(numeratorProduct, simplified.coefficient.denominator.toBigDecimal())
            storedValue = result
            result
        }
    }

    /**
     * Get number of Pi in numbers. Inverted Pi is counted as -1
     */
    private fun calculatePiCount(): Int {
        val positive = pisSet.getCountOf(Pi())
        val negative = pisSet.getCountOf(Pi().inverse())
        return positive - negative
    }

    private fun getGeneratedLogs(): List<Log> {
        if (logsList == null) {
            logsList = logsSet.toList()
        }

        return logsList!!
    }

    private fun getGeneratedSquareRoots(): List<Sqrt> {
        if (squareRootsList == null) {
            squareRootsList = squareRootsSet.toList()
        }

        return squareRootsList!!
    }

    private fun getGeneratedPis(): List<Pi> {
        if (pisList == null) {
            pisList = pisSet.toList()
        }

        return pisList!!
    }

    override fun toString(): String {
        return storedString.ifNull {
            val coeffString = if (coefficient.denominator == BigInteger.ONE) {
                coefficient.numerator.toString()
            } else {
                "[${coefficient.numerator}/${coefficient.denominator}]"
            }

            val numString = (logs + squareRoots + pis).joinToString("x")
            val result = simpleIf(numString.isEmpty(), "<$coeffString>", "<${coeffString}x$numString>")

            storedString = result
            result
        }
    }

    override fun hashCode(): Int = listOf("Term", coefficient, logs, squareRoots, pis).hashCode()

    companion object {
        val ZERO = Term(ExactFraction.ZERO, emptyList(), emptyList(), emptyList())
        val ONE = Term(ExactFraction.ONE, emptyList(), emptyList(), emptyList())

        /**
         * Public method of constructing a Term, by providing information about irrationals
         *
         * @param coefficient [ExactFraction]
         * @param logs [List<Log>]: list of log numbers
         * @param roots [List]<[Sqrt]>: list of square root numbers
         * @param piCount [Int]: how many occurrence of Pi to include in the list of numbers.
         * A negative number corresponds to divided Pi values
         * @return [Term] with the given values
         */
        fun fromValues(coefficient: ExactFraction, logs: List<Log>, roots: List<Sqrt>, piCount: Int): Term {
            val pi = simpleIf(piCount < 0, Pi().inverse(), Pi())
            val piList = List(abs(piCount)) { pi }

            return Term(coefficient, logs, roots, piList)
        }

        /**
         * Public method of constructing a Term, by providing information about irrationals
         *
         * @param coefficient [ExactFraction]
         * @param logs [List<Log>]: list of log numbers
         * @param roots [List]<[Sqrt]>: list of square root numbers
         * @param pis [List]<[Pi]>: list of pi numbers
         * @return [Term] with the given values
         */
        fun fromValues(coefficient: ExactFraction, logs: List<Log>, roots: List<Sqrt>, pis: List<Pi>): Term {
            return Term(coefficient, logs, roots, pis)
        }

        // one type of irrational
        @JvmName("termFromLogs")
        fun fromValues(logs: List<Log>) = fromValues(ExactFraction.ONE, logs, emptyList(), emptyList())

        @JvmName("termFromRoots")
        fun fromValues(roots: List<Sqrt>) = fromValues(ExactFraction.ONE, emptyList(), roots, emptyList())

        @JvmName("termFromPis")
        fun fromValues(pis: List<Pi>) = fromValues(ExactFraction.ONE, emptyList(), emptyList(), pis)

        // two types of irrationals
        @JvmName("termFromLogsRoots")
        fun fromValues(logs: List<Log>, roots: List<Sqrt>) = fromValues(ExactFraction.ONE, logs, roots, emptyList())

        @JvmName("termFromLogsPis")
        fun fromValues(logs: List<Log>, pis: List<Pi>) = fromValues(ExactFraction.ONE, logs, emptyList(), pis)

        @JvmName("termFromRootsPis")
        fun fromValues(roots: List<Sqrt>, pis: List<Pi>) = fromValues(ExactFraction.ONE, emptyList(), roots, pis)

        // three types of irrationals
        @JvmName("termFromLogsRootsPis")
        fun fromValues(logs: List<Log>, roots: List<Sqrt>, pis: List<Pi>) =
            fromValues(ExactFraction.ONE, logs, roots, pis)

        // one type of irrational + coefficient
        @JvmName("termFromCoeffLogs")
        fun fromValues(coefficient: ExactFraction, logs: List<Log>) =
            fromValues(coefficient, logs, emptyList(), emptyList())

        @JvmName("termFromCoeffRoots")
        fun fromValues(coefficient: ExactFraction, roots: List<Sqrt>) =
            fromValues(coefficient, emptyList(), roots, emptyList())

        @JvmName("termFromCoeffPis")
        fun fromValues(coefficient: ExactFraction, pis: List<Pi>) =
            fromValues(coefficient, emptyList(), emptyList(), pis)

        // two types of irrationals + coefficient
        @JvmName("termFromCoeffLogsRoots")
        fun fromValues(coefficient: ExactFraction, logs: List<Log>, roots: List<Sqrt>) =
            fromValues(coefficient, logs, roots, emptyList())

        @JvmName("termFromCoeffLogsPis")
        fun fromValues(coefficient: ExactFraction, logs: List<Log>, pis: List<Pi>) =
            fromValues(coefficient, logs, emptyList(), pis)

        @JvmName("termFromCoeffRootsPis")
        fun fromValues(coefficient: ExactFraction, roots: List<Sqrt>, pis: List<Pi>) =
            fromValues(coefficient, emptyList(), roots, pis)

        @JvmName("termCoeffNumbers")
        fun fromValues(coefficient: ExactFraction, numbers: List<IrrationalNumber<*>>): Term {
            val groupedNumbers = numbers.groupBy { it.type }
            val logs: List<Log> = (groupedNumbers[Log.TYPE] ?: emptyList()) as List<Log>
            val roots: List<Sqrt> = (groupedNumbers[Sqrt.TYPE] ?: emptyList()) as List <Sqrt>
            val pis: List<Pi> = (groupedNumbers[Pi.TYPE] ?: emptyList()) as List<Pi>
            return Term(coefficient, logs, roots, pis)
        }

        // single rational or irrational value
        fun fromValue(coefficient: ExactFraction) = fromValues(coefficient, emptyList(), emptyList(), emptyList())
        fun fromValue(log: Log) = fromValues(ExactFraction.ONE, listOf(log), emptyList(), emptyList())
        fun fromValue(sqrt: Sqrt) = fromValues(ExactFraction.ONE, emptyList(), listOf(sqrt), emptyList())
        fun fromValue(pi: Pi) = fromValues(ExactFraction.ONE, emptyList(), emptyList(), listOf(pi))
    }
}
