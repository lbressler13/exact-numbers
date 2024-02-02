package xyz.lbres.exactnumbers.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.IrrationalNumber
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.exactnumbers.utils.castToByte
import xyz.lbres.exactnumbers.utils.castToChar
import xyz.lbres.exactnumbers.utils.castToDouble
import xyz.lbres.exactnumbers.utils.castToFloat
import xyz.lbres.exactnumbers.utils.castToInt
import xyz.lbres.exactnumbers.utils.castToLong
import xyz.lbres.exactnumbers.utils.castToShort
import xyz.lbres.exactnumbers.utils.deprecatedV1
import xyz.lbres.exactnumbers.utils.irrationalsPackage
import xyz.lbres.kotlinutils.collection.ext.toConstMultiSet
import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.iterable.ext.countElement
import xyz.lbres.kotlinutils.list.listOfValue
import java.math.BigDecimal
import kotlin.math.abs

/**
 * Representation of the product of several numbers, as a rational coefficient and list of irrational factors
 */
sealed class Term : Number() {
    abstract val coefficient: ExactFraction
    abstract val factors: List<IrrationalNumber<*>>

    abstract operator fun unaryMinus(): Term
    abstract operator fun unaryPlus(): Term

    abstract operator fun times(other: Term): Term
    abstract operator fun div(other: Term): Term

    abstract fun isZero(): Boolean
    abstract fun inverse(): Term

    /**
     * Simplify coefficient and factors
     *
     * @return [Term] simplified version of this term
     */
    abstract fun getSimplified(): Term

    /**
     * Get value of term by multiplying coefficient and factors
     *
     * @return [BigDecimal]
     */
    abstract fun getValue(): BigDecimal

    /**
     * Get list of irrational factors with a given type
     *
     * @param irrationalType [String]: type to retrieve numbers for
     * @return [List]<IrrationalNumber<*>>: list of irrational numbers, which all have the provided type
     */
    abstract fun getFactorsByType(irrationalType: String): List<IrrationalNumber<*>>

    override fun toByte(): Byte = castToByte(getValue(), this, "Term")
    override fun toChar(): Char = castToChar(getValue(), this, "Term")
    override fun toShort(): Short = castToShort(getValue(), this, "Term")
    override fun toInt(): Int = castToInt(getValue(), this, "Term")
    override fun toLong(): Long = castToLong(getValue(), this, "Term")
    override fun toFloat(): Float = castToFloat(getValue(), this, "Term")
    override fun toDouble(): Double = castToDouble(getValue(), this, "Term")

    @Deprecated("Method $deprecatedV1", ReplaceWith("getFactorsByType(Log.TYPE)", "$irrationalsPackage.log.Log"), DeprecationLevel.WARNING)
    @Suppress("UNCHECKED_CAST")
    fun getLogs(): List<Log> = getFactorsByType(Log.TYPE) as List<Log>

    @Deprecated("Method $deprecatedV1", ReplaceWith("getFactorsByType(Pi.TYPE)"), DeprecationLevel.WARNING)
    fun getPiCount(): Int {
        val pis = getFactorsByType(Pi.TYPE)
        return pis.countElement(Pi()) * 2 - pis.size // positive - (numbers.size - positive)
    }

    @Deprecated("Method $deprecatedV1", ReplaceWith("getFactorsByType(Sqrt.TYPE)", "$irrationalsPackage.sqrt.Sqrt"), DeprecationLevel.WARNING)
    @Suppress("UNCHECKED_CAST")
    fun getSquareRoots(): List<Sqrt> = getFactorsByType(Sqrt.TYPE) as List<Sqrt>

    companion object {
        val ZERO = fromValues(ExactFraction.ZERO, emptyList())
        val ONE = fromValues(ExactFraction.ONE, emptyList())

        /**
         * Construct a term by providing information about coefficient and irrational factors
         *
         * @param coefficient [ExactFraction]
         * @param factors [List]<IrrationalNumber<*>>: list of irrational numbers
         * @return [Term] with the given values
         */
        fun fromValues(coefficient: ExactFraction, factors: List<IrrationalNumber<*>>): Term {
            return TermImpl(coefficient, factors.toConstMultiSet())
        }

        /**
         * Construct a term by providing information about coefficient, logs, square roots, and pis
         *
         * @param coefficient [ExactFraction]
         * @param logs [List]<Log>: list of log numbers
         * @param roots [List]<Sqrt>: list of square root numbers
         * @param piCount [Int]: how many occurrences of pi to include in the list of numbers.
         * A negative number corresponds to Pi values where inverted is `true`
         * @return [Term] with the given values
         */
        fun fromValues(coefficient: ExactFraction, logs: List<Log>, roots: List<Sqrt>, piCount: Int): Term {
            val pi = simpleIf(piCount < 0, Pi().inverse(), Pi())
            return fromValues(coefficient, logs + roots + listOfValue(abs(piCount), pi))
        }
    }
}
