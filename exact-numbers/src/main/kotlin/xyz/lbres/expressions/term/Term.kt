package xyz.lbres.expressions.term

import xyz.lbres.common.createHashCode
import xyz.lbres.common.deprecatedV1
import xyz.lbres.common.divideBigDecimals
import xyz.lbres.common.divideByZero
import xyz.lbres.common.irrationalPackage
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumber
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.kotlinutils.collection.ext.toConstMultiSet
import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.emptyConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.filterConsistent
import xyz.lbres.kotlinutils.set.multiset.mapToSetConsistent
import java.math.BigDecimal
import kotlin.math.abs

/**
 * Representation of the product of several numbers, represented as a rational coefficient and lists of irrational numbers
 */
class Term {
    val coefficient: ExactFraction

    private val irrationalTypes: MutableMap<String, List<IrrationalNumber<*>>> = mutableMapOf()

    private val _irrationals: ConstMultiSet<IrrationalNumber<*>>
    val irrationals: List<IrrationalNumber<*>>

    @Suppress("UNCHECKED_CAST")
    val logs: List<Log>
        get() = getIrrationalsByType(Log.TYPE) as List<Log>
    @Suppress("UNCHECKED_CAST")
    val squareRoots: List<Sqrt>
        get() = getIrrationalsByType(Sqrt.TYPE) as List<Sqrt>
    @Suppress("UNCHECKED_CAST")
    val pis: List<Pi>
        get() = getIrrationalsByType(Pi.TYPE) as List<Pi>
    val piCount: Int
        get() = calculatePiCount()

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
        } else {
            this.coefficient = coefficient
            this.irrationals = irrationals
            this._irrationals = irrationals.toConstMultiSet()
        }
    }

    // Initialize values using multisets. Only used inside the class, to avoid unnecessary casts when creating new terms after operations
    private constructor(
        coefficient: ExactFraction,
        irrationals: ConstMultiSet<IrrationalNumber<*>>,
    ) {
        if (coefficient.isZero() || irrationals.any { it.isZero() }) {
            this.coefficient = ExactFraction.ZERO
            this.irrationals = emptyList()
            this._irrationals = emptyConstMultiSet()
        } else {
            this.coefficient = coefficient
            this.irrationals = irrationals.toList()
            this._irrationals = irrationals
        }
    }

    operator fun unaryMinus(): Term = Term(-coefficient, _irrationals)
    operator fun unaryPlus(): Term = Term(coefficient, _irrationals)

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Term) {
            return false
        }

        val simplified = getSimplified()
        val otherSimplified = other.getSimplified()

        return simplified.coefficient == otherSimplified.coefficient && simplified._irrationals == otherSimplified._irrationals
    }

    operator fun times(other: Term): Term {
        val newIrrationals = _irrationals + other._irrationals
        return Term(coefficient * other.coefficient, newIrrationals.toConstMultiSet())
    }

    operator fun div(other: Term): Term {
        if (other.isZero()) {
            throw divideByZero
        }

        val newIrrationals = _irrationals + other._irrationals.mapToSetConsistent { it.inverse() }
        return Term(coefficient / other.coefficient, newIrrationals.toConstMultiSet())
    }

    fun isZero(): Boolean = coefficient.isZero()

    fun getIrrationalsByType(type: String): List<IrrationalNumber<*>> {
        if (type !in irrationalTypes) {
            val result = _irrationals.filterConsistent { it.type == type }
            irrationalTypes[type] = result
        }

        return irrationalTypes[type]!!
    }

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

            val irrationalProduct = simplified.irrationals.fold(BigDecimal.ONE) { acc, number -> acc * number.getValue() }
            val numeratorProduct = simplified.coefficient.numerator.toBigDecimal() * irrationalProduct

            val result = divideBigDecimals(numeratorProduct, simplified.coefficient.denominator.toBigDecimal())
            value = result
        }

        return value!!
    }

    private fun calculatePiCount(): Int {
        @Suppress("UNCHECKED_CAST")
        val pis = getIrrationalsByType(Pi.TYPE) as List<Pi>
        val positive = pis.count { !it.isInverted }
        val negative = pis.size - positive
        return positive - negative
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

    @Deprecated("Method $deprecatedV1", ReplaceWith("getIrrationalsByType(Log.TYPE)", "${irrationalPackage}.log.Log"), DeprecationLevel.WARNING)
    @JvmName("getLogsDeprecated")
    fun getLogs(): List<Log> = logs

    @Deprecated("Method $deprecatedV1", ReplaceWith("piCount"), DeprecationLevel.WARNING)
    @JvmName("getPiCountDeprecated")
    fun getPiCount(): Int = piCount

    @Deprecated("Method $deprecatedV1", ReplaceWith("getIrrationalsByType(Sqrt.TYPE)", "${irrationalPackage}.sqrt.Sqrt"), DeprecationLevel.WARNING)
    @JvmName("getSquareRootsDeprecated")
    fun getSquareRoots(): List<Sqrt> = squareRoots

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
