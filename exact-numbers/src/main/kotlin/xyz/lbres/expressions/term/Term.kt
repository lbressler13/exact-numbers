package xyz.lbres.expressions.term

import xyz.lbres.common.divideBigDecimals
import xyz.lbres.common.divideByZero
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.kotlinutils.generic.ext.ifNull
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.abs

/**
 * Representation of the product of several numbers, represented as a rational coefficient and list of irrational numbers
 *
 * @param coefficient [ExactFraction]
 * @param logs [List]<[Log]>: list of log numbers
 * @param squareRoots [List]<[Sqrt]>: list of square root numbers
 * @param pis [List]<[Pi]>: list of pi numbers
 * @return [Term] with the given values
 */
class Term private constructor(coefficient: ExactFraction, logs: List<Log>, squareRoots: List<Sqrt>, pis: List<Pi>) {
    val coefficient: ExactFraction
    // internal val numbers: List<Irrational>
    val logs: List<Log>
    val squareRoots: List<Sqrt>
    val pis: List<Pi>
    val piCount: Int

    private var storedIsZero: Boolean? = null
    private var storedSimplified: Term? = null
    private var storedValue: BigDecimal? = null
    private var storedString: String? = null

    init {
        if (coefficient.isZero() || logs.any(Log::isZero) || squareRoots.any(Sqrt::isZero) || pis.any(Pi::isZero)) {
            this.coefficient = ExactFraction.ZERO
            // this.numbers = emptyList()
            this.logs = emptyList()
            this.squareRoots = emptyList()
            this.pis = emptyList()
            piCount = 0
        } else {
            this.coefficient = coefficient
            // this.numbers = numbers

            this.logs = logs
            this.squareRoots = squareRoots
            this.pis = pis
            piCount = calculatePiCount()
        }
    }

    operator fun unaryMinus(): Term = Term(-coefficient, logs, squareRoots, pis)
    operator fun unaryPlus(): Term = Term(coefficient, logs, squareRoots, pis)

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Term) {
            return false
        }

        val simplified = getSimplified()
        val otherSimplified = other.getSimplified()

        return simplified.coefficient == otherSimplified.coefficient &&
            simplified.piCount == otherSimplified.piCount &&
            simplified.logs.sorted() == otherSimplified.logs.sorted() &&
            simplified.squareRoots.sorted() == otherSimplified.squareRoots.sorted()
    }

    operator fun times(other: Term): Term {
        return Term(
            coefficient * other.coefficient,
            logs + other.logs,
            squareRoots + other.squareRoots,
            pis + other.pis
        )
    }

    operator fun div(other: Term): Term {
        if (other.isZero()) {
            throw divideByZero
        }

//        val newCoeff = coefficient / other.coefficient
//        val newNumbers = numbers + other.numbers.map { it.swapDivided() }
//        return Term(newCoeff, newNumbers)
        return Term(
            coefficient / other.coefficient,
            logs + other.logs.map(Log::swapDivided),
            squareRoots + other.squareRoots.map(Sqrt::swapDivided),
            pis + other.pis.map(Pi::swapDivided)
        )
    }

    fun isZero(): Boolean {
        return storedIsZero.ifNull {
            val result = coefficient.isZero() || logs.any(Log::isZero) || squareRoots.any(Sqrt::isZero) || pis.any(Pi::isZero)
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
            val simplifiedLogs = Log.simplifyList(logs)
            val simplifiedSqrts = Sqrt.simplifyList(squareRoots)
            val simplifiedPis = Pi.simplifyList(pis)
            val newCoefficient = coefficient * simplifiedLogs.first * simplifiedSqrts.first

            val result = Term(newCoefficient, simplifiedLogs.second, simplifiedSqrts.second, simplifiedPis)
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

            val logProduct = simplified.logs.fold(BigDecimal.ONE) { acc, num -> acc * num.getValue() }
            val sqrtProduct = simplified.squareRoots.fold(BigDecimal.ONE) { acc, num -> acc * num.getValue() }
            val piProduct = simplified.pis.fold(BigDecimal.ONE) { acc, num -> acc * num.getValue() }
            val numeratorProduct = logProduct * sqrtProduct * piProduct * simplified.coefficient.numerator.toBigDecimal()

            val result = divideBigDecimals(numeratorProduct, simplified.coefficient.denominator.toBigDecimal())
            storedValue = result
            result
        }
    }

    /**
     * Get all logs from numbers
     */
//    @Suppress("UNCHECKED_CAST")
//    fun getLogs(): List<Log> = numbers.filter { it.type == Log.TYPE } as List<Log>
    // fun getLogs() = logs

    /**
     * Get number of Pi in numbers. Divided Pi is counted as -1
     */
    private fun calculatePiCount(): Int {
        // val pis = numbers.filter { it.type == Pi.TYPE }
        val positive = pis.count { !it.isDivided }
        val negative = pis.size - positive
        return positive - negative
    }

    /**
     * Get all square roots from numbers
     */
//    @Suppress("UNCHECKED_CAST")
//    fun getSquareRoots(): List<Sqrt> = numbers.filter { it.type == Sqrt.TYPE } as List<Sqrt>
    // fun getSquareRoots() = squareRoots

    override fun toString(): String {
        return storedString.ifNull {
            val coeffString = if (coefficient.denominator == BigInteger.ONE) {
                coefficient.numerator.toString()
            } else {
                "[${coefficient.numerator}/${coefficient.denominator}]"
            }

            val numString = (logs + pis + squareRoots).joinToString("x")

            val result = if (numString.isEmpty()) {
                "<$coeffString>"
            } else {
                "<${coeffString}x$numString>"
            }

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
            val piDivided = piCount < 0
            val piList = List(abs(piCount)) { Pi(isDivided = piDivided) }

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
        fun fromValues(logs: List<Log>, roots: List<Sqrt>, pis: List<Pi>) = fromValues(ExactFraction.ONE, logs, roots, pis)

        // one type of irrational + coefficient
        @JvmName("termFromCoeffLogs")
        fun fromValues(coefficient: ExactFraction, logs: List<Log>) = fromValues(coefficient, logs, emptyList(), emptyList())
        @JvmName("termFromCoeffRoots")
        fun fromValues(coefficient: ExactFraction, roots: List<Sqrt>) = fromValues(coefficient, emptyList(), roots, emptyList())
        @JvmName("termFromCoeffPis")
        fun fromValues(coefficient: ExactFraction, pis: List<Pi>) = fromValues(coefficient, emptyList(), emptyList(), pis)

        // two types of irrationals + coefficient
        @JvmName("termFromCoeffLogsRoots")
        fun fromValues(coefficient: ExactFraction, logs: List<Log>, roots: List<Sqrt>) = fromValues(coefficient, logs, roots, emptyList())
        @JvmName("termFromCoeffLogsPis")
        fun fromValues(coefficient: ExactFraction, logs: List<Log>, pis: List<Pi>) = fromValues(coefficient, logs, emptyList(), pis)
        @JvmName("termFromCoeffsRootsPis")
        fun fromValues(coefficient: ExactFraction, roots: List<Sqrt>, pis: List<Pi>) = fromValues(coefficient, emptyList(), roots, pis)

        fun fromValue(coefficient: ExactFraction) = fromValues(coefficient, emptyList(), emptyList(), emptyList())
        fun fromValue(log: Log) = fromValues(ExactFraction.ONE, listOf(log), emptyList(), emptyList())
        fun fromValue(sqrt: Sqrt) = fromValues(ExactFraction.ONE, emptyList(), listOf(sqrt), emptyList())
        fun fromValue(pi: Pi) = fromValues(ExactFraction.ONE, emptyList(), emptyList(), listOf(pi))
    }
}
