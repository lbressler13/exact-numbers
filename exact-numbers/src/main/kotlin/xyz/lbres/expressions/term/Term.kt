package xyz.lbres.expressions.term

import xyz.lbres.common.createHashCode
import xyz.lbres.common.divideBigDecimals
import xyz.lbres.common.divideByZero
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumber
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.kotlinutils.collection.ext.toConstMultiSet
import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.emptyConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.mapToSetConsistent
import java.math.BigDecimal
import kotlin.math.abs

/**
 * Representation of the product of several numbers, represented as a rational coefficient and lists of irrational numbers
 */
class Term {
    val coefficient: ExactFraction

    private val _irrationals: ConstMultiSet<IrrationalNumber<*>>
    private val _logs: ConstMultiSet<Log>
    private val _squareRoots: ConstMultiSet<Sqrt>
    private val _pis: ConstMultiSet<Pi>
    private val _otherIrrationals: ConstMultiSet<IrrationalNumber<*>>

    val irrationals: List<IrrationalNumber<*>>
    val logs: List<Log>
    val squareRoots: List<Sqrt>
    val pis: List<Pi>
    val piCount: Int

    // previously computed values for method returns
    private var simplified: Term? = null
    private var value: BigDecimal? = null
    private var string: String? = null

    // Initialize values using list of numbers. Accessible via the fromValues methods in the companion object.
    private constructor(coefficient: ExactFraction, irrationals: List<IrrationalNumber<*>>) {
        if (coefficient.isZero() || irrationals.any { it.isZero() }) {
            this.coefficient = ExactFraction.ZERO
            this.irrationals = emptyList()
            this._irrationals = emptyConstMultiSet()

            _logs = emptyConstMultiSet()
            _squareRoots = emptyConstMultiSet()
            _pis = emptyConstMultiSet()
            piCount = 0
            _otherIrrationals = emptyConstMultiSet()

            logs = emptyList()
            squareRoots = emptyList()
            pis = emptyList()
        } else {
            this.coefficient = coefficient
            this.irrationals = irrationals
            this._irrationals = irrationals.toConstMultiSet()

            val groups = irrationals.groupBy { it.type }
            @Suppress("UNCHECKED_CAST")
            logs = (groups[Log.TYPE] ?: emptyList()) as List<Log>
            @Suppress("UNCHECKED_CAST")
            squareRoots = (groups[Sqrt.TYPE] ?: emptyList()) as List<Sqrt>
            @Suppress("UNCHECKED_CAST")
            pis = (groups[Pi.TYPE] ?: emptyList()) as List<Pi>

            _logs = logs.toConstMultiSet()
            _squareRoots = squareRoots.toConstMultiSet()
            _pis = pis.toConstMultiSet()
            piCount = _pis.getCountOf(Pi()) - _pis.getCountOf(Pi(isInverted = true))
            _otherIrrationals = irrationals.filter { it !is Log && it !is Sqrt && it !is Pi }.toConstMultiSet()
        }
    }

    // Initialize values using multisets. Only used inside the class, to avoid unnecessary casts when creating new terms after operations
    private constructor(
        coefficient: ExactFraction,
        irrationals: ConstMultiSet<IrrationalNumber<*>>,
        logs: ConstMultiSet<Log>,
        squareRoots: ConstMultiSet<Sqrt>,
        pis: ConstMultiSet<Pi>,
        otherIrrationals: ConstMultiSet<IrrationalNumber<*>>
    ) {
        if (coefficient.isZero() || irrationals.any { it.isZero() }) {
            this.coefficient = ExactFraction.ZERO
            this.irrationals = emptyList()
            this._irrationals = emptyConstMultiSet()

            _logs = emptyConstMultiSet()
            _squareRoots = emptyConstMultiSet()
            _pis = emptyConstMultiSet()
            piCount = 0
            _otherIrrationals = emptyConstMultiSet()

            this.logs = emptyList()
            this.squareRoots = emptyList()
            this.pis = emptyList()
        } else {
            this.coefficient = coefficient
            this.irrationals = irrationals.toList()
            this._irrationals = irrationals

            this.logs = logs.toList()
            this.squareRoots = squareRoots.toList()
            this.pis = pis.toList()

            _logs = logs
            _squareRoots = squareRoots
            _pis = pis
            piCount = _pis.getCountOf(Pi()) - _pis.getCountOf(Pi(isInverted = true))
            _otherIrrationals = otherIrrationals
        }
    }

    operator fun unaryMinus(): Term = Term(-coefficient, _irrationals, _logs, _squareRoots, _pis, _otherIrrationals)
    operator fun unaryPlus(): Term = Term(coefficient, _irrationals, _logs, _squareRoots, _pis, _otherIrrationals)

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Term) {
            return false
        }

        val simplified = getSimplified()
        val otherSimplified = other.getSimplified()

        return simplified.coefficient == otherSimplified.coefficient &&
            simplified.piCount == otherSimplified.piCount &&
            simplified._logs == otherSimplified._logs &&
            simplified._squareRoots == otherSimplified._squareRoots
    }

    operator fun times(other: Term): Term {
        return Term(
            coefficient * other.coefficient,
            (_irrationals + other._irrationals).toConstMultiSet(),
            (_logs + other._logs).toConstMultiSet(),
            (_squareRoots + other._squareRoots).toConstMultiSet(),
            (_pis + other._pis).toConstMultiSet(),
            (_otherIrrationals + other._otherIrrationals).toConstMultiSet()
        )
    }

    operator fun div(other: Term): Term {
        if (other.isZero()) {
            throw divideByZero
        }

        return Term(
            coefficient / other.coefficient,
            (_irrationals + other._irrationals.mapToSetConsistent { it.inverse() }).toConstMultiSet(),
            (_logs + other._logs.mapToSetConsistent(Log::inverse)).toConstMultiSet(),
            (_squareRoots + other._squareRoots.mapToSetConsistent(Sqrt::inverse)).toConstMultiSet(),
            (_pis + other._pis.mapToSetConsistent(Pi::inverse)).toConstMultiSet(),
            (_otherIrrationals + other._otherIrrationals.mapToSetConsistent { it.inverse() }).toConstMultiSet()
        )
    }

    fun isZero(): Boolean = coefficient.isZero()

    /**
     * Simplify all numbers, based on the simplify function for their type
     *
     * @return [Term] simplified version of this term
     */
    fun getSimplified(): Term {
        if (simplified == null) {
            simplified = simplifyTerm(this)
        }

        return simplified!!
    }

    /**
     * Get value of term by multiplying numbers.
     * Term is simplified before any computation is run
     *
     * @return [BigDecimal]
     */
    fun getValue(): BigDecimal {
        if (value == null) {
            val simplified = getSimplified()

            val logProduct = simplified._logs.fold(BigDecimal.ONE) { acc, num -> acc * num.getValue() }
            val sqrtProduct = simplified._squareRoots.fold(BigDecimal.ONE) { acc, num -> acc * num.getValue() }
            val piProduct = simplified._pis.fold(BigDecimal.ONE) { acc, num -> acc * num.getValue() }
            val numeratorProduct = logProduct * sqrtProduct * piProduct * simplified.coefficient.numerator.toBigDecimal()

            val result = divideBigDecimals(numeratorProduct, simplified.coefficient.denominator.toBigDecimal())
            value = result
        }

        return value!!
    }

    override fun toString(): String {
        if (string == null) {
            val fractionString = coefficient.toFractionString()
            val coeffString = simpleIf(coefficient.isWholeNumber(), fractionString, "[$fractionString]")
            val numString = _irrationals.joinToString("x")
            val result = simpleIf(numString.isEmpty(), "<$coeffString>", "<${coeffString}x$numString>")

            string = result
        }

        return string!!
    }

    override fun hashCode(): Int = createHashCode(listOf(coefficient, _irrationals, this::class.toString()))

    companion object {
        val ZERO = Term(ExactFraction.ZERO, emptyList())
        val ONE = Term(ExactFraction.ONE, emptyList())

        /**
         * Construct a term by providing information about coefficient and irrationals
         *
         * @param coefficient [ExactFraction]
         * @param irrationals [List]<IrrationalNumber<*>>: list of all irrational numbers
         * @return [Term] with the given values
         */
        fun fromValues(coefficient: ExactFraction, irrationals: List<IrrationalNumber<*>>): Term = Term(coefficient, irrationals)

        /**
         * Construct a term by providing information about coefficient, logs, square roots, and pis
         *
         * @param coefficient [ExactFraction]
         * @param logs [List]<Log>: list of log numbers
         * @param roots [List]<Sqrt>: list of square root numbers
         * @param piCount [Int]: how many occurrence of Pi to include in the list of numbers.
         * A negative number corresponds to divided Pi values
         * @return [Term] with the given values
         */
        fun fromValues(coefficient: ExactFraction, logs: List<Log>, roots: List<Sqrt>, piCount: Int): Term {
            val pi = simpleIf(piCount < 0, Pi().inverse(), Pi())
            val piList = List(abs(piCount)) { pi }
            return fromValues(coefficient, logs + roots + piList)
        }
    }
}
