package exactnumbers.irrationals.logs

import exactnumbers.exactfraction.ExactFraction
import java.math.BigInteger

class LogProduct(logs: List<LogNum>, coefficient: ExactFraction) {
    val logs: List<LogNum>
    val coefficient: ExactFraction

    init {
        when {
            logs.isEmpty() -> throw Exception("LogProduct must contain at least one value")
            coefficient == ExactFraction.ZERO || logs.any { it.isZero() } -> {
                this.logs = listOf(LogNum.ZERO)
                this.coefficient = ExactFraction.ZERO
            }
            else -> {
                this.logs = logs
                this.coefficient = coefficient
            }
        }
    }

    constructor(logs: List<LogNum>) : this(logs, ExactFraction.ONE)

    operator fun unaryMinus() = LogProduct(logs, -coefficient)
    operator fun unaryPlus() = this

    operator fun times(other: LogProduct): LogProduct = LogProduct(logs + other.logs, coefficient * other.coefficient)
    operator fun times(other: LogNum): LogProduct = LogProduct(logs + other, coefficient)

    fun isZero(): Boolean = logs[0].isZero()

    // TODD this should use simplified
    override operator fun equals(other: Any?): Boolean {
        if (other == null || other !is LogProduct) {
            return false
        }

        val logSort: (LogNum, LogNum) -> Int = { logNum1, logNum2 ->
            logNum1.number.compareTo(logNum2.number)
        }

        val simplified = getSimplified()
        val otherSimplified = other.getSimplified()

        return simplified.coefficient == otherSimplified.coefficient &&
            simplified.logs.sortedWith(logSort) == otherSimplified.logs.sortedWith(logSort)
    }

    // TODO update when bases are added
    fun getSimplified(): LogProduct {
        if (logs.any { it.isZero() }) {
            return ZERO
        }

        val newLogs = logs.filter { it != LogNum.ONE }
            .ifEmpty { listOf(LogNum.ONE) }

        return LogProduct(newLogs, coefficient)
    }

    override fun hashCode(): Int = Pair(logs, coefficient).hashCode()

    override fun toString(): String {
        val logsString = logs.joinToString("x")
        val coefficientString = if (coefficient.denominator == BigInteger.ONE) {
            coefficient.numerator.toString()
        } else {
            coefficient.toString()
        }
        return "${coefficientString}x$logsString"
    }

    companion object {
        val ZERO = LogProduct(listOf(LogNum.ZERO))
        val ONE = LogProduct(listOf(LogNum.ONE))
    }
}
