package xyz.lbres.exactnumbers.expressions.term

import xyz.lbres.exactnumbers.common.castToByte
import xyz.lbres.exactnumbers.common.castToChar
import xyz.lbres.exactnumbers.common.castToDouble
import xyz.lbres.exactnumbers.common.castToFloat
import xyz.lbres.exactnumbers.common.castToInt
import xyz.lbres.exactnumbers.common.castToLong
import xyz.lbres.exactnumbers.common.castToShort
import xyz.lbres.exactnumbers.common.deprecatedV1
import xyz.lbres.exactnumbers.common.irrationalPackage
import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumber
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.kotlinutils.collection.ext.toConstMultiSet
import xyz.lbres.kotlinutils.general.simpleIf
import java.math.BigDecimal
import kotlin.math.abs

/**
 * Representation of the product of several numbers, as a rational coefficient and list of irrational numbers
 */
sealed class Term : Number() {
    abstract val coefficient: ExactFraction
    abstract val irrationals: List<IrrationalNumber<*>>
    abstract val logs: List<Log>
    abstract val squareRoots: List<Sqrt>
    abstract val pis: List<Pi>
    abstract val piCount: Int

    abstract operator fun unaryMinus(): Term
    abstract operator fun unaryPlus(): Term

    abstract operator fun times(other: Term): Term
    abstract operator fun div(other: Term): Term

    abstract fun isZero(): Boolean

    /**
     * Simplify coefficients and irrationals
     *
     * @return [Term] simplified version of this term
     */
    abstract fun getSimplified(): Term

    /**
     * Get value of term by multiplying numbers
     *
     * @return [BigDecimal]
     */
    abstract fun getValue(): BigDecimal

    /**
     * Get list of irrational numbers with a given type
     *
     * @param type [String]: type to retrieve numbers for
     * @return [List]<IrrationalNumber<*>>: list of irrational numbers, which all have type [type]
     */
    abstract fun getIrrationalsByType(type: String): List<IrrationalNumber<*>>

    override fun toByte(): Byte = castToByte(getValue(), this, "Term")
    override fun toChar(): Char = castToChar(getValue(), this, "Term")
    override fun toShort(): Short = castToShort(getValue(), this, "Term")
    override fun toInt(): Int = castToInt(getValue(), this, "Term")
    override fun toLong(): Long = castToLong(getValue(), this, "Term")

    override fun toFloat(): Float = castToFloat(getValue(), this, "Term")
    override fun toDouble(): Double = castToDouble(getValue(), this, "Term")

    @Deprecated("Method $deprecatedV1", ReplaceWith("getIrrationalsByType(Log.TYPE)", "$irrationalPackage.log.Log"), DeprecationLevel.WARNING)
    @JvmName("getLogsDeprecated")
    fun getLogs(): List<Log> = logs

    @Deprecated("Method $deprecatedV1", ReplaceWith("piCount"), DeprecationLevel.WARNING)
    @JvmName("getPiCountDeprecated")
    fun getPiCount(): Int = piCount

    @Deprecated("Method $deprecatedV1", ReplaceWith("getIrrationalsByType(Sqrt.TYPE)", "$irrationalPackage.sqrt.Sqrt"), DeprecationLevel.WARNING)
    @JvmName("getSquareRootsDeprecated")
    fun getSquareRoots(): List<Sqrt> = squareRoots

    companion object {
        val ZERO = fromValues(ExactFraction.ZERO, emptyList())
        val ONE = fromValues(ExactFraction.ONE, emptyList())

        /**
         * Construct a term by providing information about coefficient and irrationals
         *
         * @param coefficient [ExactFraction]
         * @param irrationals [List]<IrrationalNumber<*>>: list of irrational numbers
         * @return [Term] with the given values
         */
        fun fromValues(coefficient: ExactFraction, irrationals: List<IrrationalNumber<*>>): Term {
            return TermImpl(coefficient, irrationals.toConstMultiSet())
        }

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
            val pis = List(abs(piCount)) { simpleIf(piCount < 0, { Pi().inverse() }, { Pi() }) }
            return fromValues(coefficient, logs + roots + pis)
        }
    }
}
