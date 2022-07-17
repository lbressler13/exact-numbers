package exactnumbers.irrationals.sqrt

import common.divideBigDecimals
import common.throwDivideByZero
import exactnumbers.exactfraction.ExactFraction
import exactnumbers.ext.toExactFraction
import exactnumbers.irrationals.common.Irrational
import exactnumbers.irrationals.log.Log
import exactnumbers.irrationals.pi.Pi
import expressions.term.Term
import java.math.BigDecimal
import java.math.BigInteger

class Sqrt(val radicand: ExactFraction, override val isDivided: Boolean) : Irrational {
    override val type = TYPE

    init {
        when {
            radicand.isNegative() -> throw ArithmeticException("Cannot calculate root of a negative number")
            radicand.isZero() && isDivided -> throwDivideByZero()
        }
    }

    constructor(radicand: ExactFraction) : this(radicand, false)

    constructor(radicand: Int) : this(ExactFraction(radicand))
    constructor(radicand: Int, isDivided: Boolean) : this(ExactFraction(radicand), isDivided)
    constructor(radicand: Long) : this(ExactFraction(radicand))
    constructor(radicand: Long, isDivided: Boolean) : this(ExactFraction(radicand), isDivided)
    constructor(radicand: BigInteger) : this(ExactFraction(radicand))
    constructor(radicand: BigInteger, isDivided: Boolean) : this(ExactFraction(radicand), isDivided)

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

        return isZero() && other.isZero() || // 0 = 0
            radicand == ExactFraction.ONE && other.radicand == ExactFraction.ONE || // sqrt(1) = 1/sqrt(1)
            isDivided == other.isDivided && radicand == other.radicand || // sqrt(x) = sqrt(x), 1/sqrt(x) = 1/sqrt(x)
            isDivided != other.isDivided && radicand == other.radicand.inverse() // sqrt(1/x) = 1/sqrt(x)
    }

    // sqrt(32) returns 4*sqrt(2)
    fun getSimplified(): Pair<ExactFraction, Sqrt> {
        if (radicand.isZero()) {
            return Pair(ExactFraction.ONE, ZERO)
        }

        if (radicand == ExactFraction.ONE) {
            return Pair(ExactFraction.ONE, ONE) // not divided, even if current number is
        }

        val notDivided = if (isDivided) {
            radicand.inverse()
        } else {
            radicand
        }

        val numWhole = extractWholeOf(notDivided.numerator)
        val denomWhole = extractWholeOf(notDivided.denominator)
        val whole = ExactFraction(numWhole, denomWhole)

        val newNum = notDivided.numerator / (numWhole * numWhole)
        val newDenom = notDivided.denominator / (denomWhole * denomWhole)
        val newRadicand = ExactFraction(newNum, newDenom)

        return Pair(whole, Sqrt(newRadicand))
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

    override fun hashCode(): Int = listOf(TYPE, radicand, isDivided).hashCode()

    companion object {
        const val TYPE = "sqrt"

        val ZERO = Sqrt(ExactFraction.ZERO)
        val ONE = Sqrt(ExactFraction.ONE)

        internal fun simplifyList(numbers: List<Irrational>): Pair<ExactFraction, List<Sqrt>> {
            numbers as List<Sqrt>

            if (numbers.isEmpty() || numbers.any { it.isZero() }) {
                return Pair(ExactFraction.ONE, listOf())
            }

            var coeff = ExactFraction.ONE
            val currentNums: MutableSet<ExactFraction> = mutableSetOf()

            for (sqrt in numbers) {
                if (sqrt == ONE) {
                    continue
                }

                val simplified = sqrt.getSimplified()

                coeff *= simplified.first

                val num = simplified.second.radicand.numerator.toExactFraction()
                val denom = simplified.second.radicand.denominator.toExactFraction().inverse() // put value in denominator

                when {
                    // sqrt(x) * sqrt(x) = add x to coeff, remove from list
                    currentNums.contains(num) -> {
                        currentNums.remove(num)
                        coeff *= num
                    }
                    // sqrt(x) * sqrt(1/x) = remove from list, multiple coeff by 1 (no change)
                    currentNums.contains(num.inverse()) -> currentNums.remove(num.inverse())
                    // add sqrt(x) to list, may be removed later
                    num != ExactFraction.ONE -> currentNums.add(num)
                }

                when {
                    // sqrt(1/x) * sqrt(1/x) = add 1/x to coeff, remove from list
                    currentNums.contains(denom) -> {
                        currentNums.remove(denom)
                        coeff *= denom
                    }
                    // sqrt(1/x) * sqrt(x) = remove from list, multiple coeff by 1 (no change)
                    currentNums.contains(denom.inverse()) -> currentNums.remove(denom.inverse())
                    // add sqrt(1/x) to list, may be removed later
                    denom != ExactFraction.ONE -> currentNums.add(denom)
                }
            }

            val sqrtList = currentNums.map { Sqrt(it) }.toList()

            return Pair(coeff, sqrtList)
        }
    }
}
