package exactnumbers.irrationals.sqrt

import common.divideBigDecimals
import common.getIntFromDecimal
import common.throwDivideByZero
import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.common.Irrational
import exactnumbers.irrationals.log.Log
import exactnumbers.irrationals.pi.Pi
import expressions.term.Term
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode

class Sqrt(val radicand: ExactFraction, override val isDivided: Boolean) : Irrational {
    override val type = TYPE

    constructor(radicand: ExactFraction) : this(radicand, false)

    init {
        when {
            radicand.isNegative() -> throw ArithmeticException("Cannot calculate root of a negative number")
            radicand.isZero() && isDivided -> throwDivideByZero()
        }
    }

    operator fun times(other: Sqrt): Term = times(other)
    operator fun times(other: Log): Term = times(other)
    operator fun times(other: Pi): Term = times(other)
    operator fun div(other: Sqrt): Term = div(other)
    operator fun div(other: Log): Term = div(other)
    operator fun div(other: Pi): Term = div(other)

    override fun isZero(): Boolean = radicand.isZero()
    override fun swapDivided(): Sqrt = Sqrt(radicand, !isDivided)

    override fun isRational(): Boolean {
        val numRoot = getRootOf(radicand.numerator).toPlainString()
        val denomRoot = getRootOf(radicand.denominator).toPlainString()

        return numRoot.indexOf('.') == -1 && denomRoot.indexOf('.') == -1
    }

    override fun getRationalValue(): ExactFraction? {
        if (!isRational()) {
            return null
        }

        val numRoot = getRootOf(radicand.numerator).toBigInteger()
        val denomRoot = getRootOf(radicand.denominator).toBigInteger()
        val result = ExactFraction(numRoot, denomRoot)

        if (isDivided) {
            return result.inverse()
        }

        return result
    }

    override fun getValue(): BigDecimal {
        val numRoot = getRootOf(radicand.numerator)
        val denomRoot = getRootOf(radicand.denominator)
        val result = divideBigDecimals(numRoot, denomRoot)

        if (isDivided) {
            return divideBigDecimals(BigDecimal.ONE, result)
        }

        return result
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Sqrt) {
            return false
        }

        return isZero() && other.isZero() // 0 = 0
                || radicand == ExactFraction.ONE && other.radicand == ExactFraction.ONE // sqrt(1) = 1/sqrt(1)
                || isDivided == other.isDivided && radicand == other.radicand // sqrt(x) = sqrt(x), 1/sqrt(x) = 1/sqrt(x)
                || isDivided != other.isDivided && radicand == other.radicand.inverse() // sqrt(1/x) = 1/sqrt(x)
    }

    // sqrt(32) returns 4*sqrt(2)
    fun getSimplified(): Pair<ExactFraction, Sqrt> {
        if (radicand.isZero()) {
            return Pair(ExactFraction.ONE, ZERO)
        }

        if (radicand == ExactFraction.ONE) {
            return Pair(ExactFraction.ONE, ONE) // not divided, even if current number is
        }

        val numWhole = extractWholeOf(radicand.numerator)
        val denomWhole = extractWholeOf(radicand.denominator)
        var whole = ExactFraction(numWhole, denomWhole)
        if (isDivided) {
            whole = whole.inverse()
        }

        val newNum = radicand.numerator / (numWhole * numWhole)
        val newDenom = radicand.denominator / (denomWhole * denomWhole)
        val newRadicand = ExactFraction(newNum, newDenom)

        return Pair(whole, Sqrt(newRadicand, isDivided))
    }

    override fun toString(): String {
        val numString = if (radicand.denominator == BigInteger.ONE) {
            radicand.numerator.toString()
        } else {
            "${radicand.numerator}/${radicand.denominator}"
        }

        if (isDivided) {
            return "[1/√($numString)]"
        }

        return "[√($numString)]"
    }

    companion object {
        const val TYPE = "sqrt"

        val ZERO = Sqrt(ExactFraction.ZERO)
        val ONE = Sqrt(ExactFraction.ONE)

        internal fun simplifyList(numbers: List<Irrational>): Pair<ExactFraction, List<Sqrt>> {
            numbers as List<Sqrt>

            if (numbers.isEmpty() || numbers.any { it.isZero() }) {
                return Pair(ExactFraction.ONE, listOf())
            }

            val combinedResult: Pair<ExactFraction, List<Sqrt>> = numbers.map { it.getSimplified() }
                .fold(Pair(ExactFraction.ONE, listOf())) { acc, pair ->
                    val coeff = acc.first * pair.first
                    val numList = acc.second + pair.second
                    Pair(coeff, numList)
                }

            println(combinedResult)
            println(combinedResult.second.map { it == ONE })

            val filteredNums = combinedResult.second.filter { it != ONE }

            return Pair(combinedResult.first, filteredNums)
        }
    }
}