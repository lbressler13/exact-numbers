package exactnumbers.irrationals.logs

import exactnumbers.utils.LogList

class LogProduct(logs: List<LogNum>) {
    val logs: LogList

    init {
        when {
            logs.isEmpty() -> throw Exception("LogProduct must contain at least one value")
            logs.any { it.isZero() } -> this.logs = listOf(LogNum.ZERO)
            else -> this.logs = logs
        }
    }

    operator fun times(other: LogProduct): LogProduct = LogProduct(logs + other.logs)
    operator fun times(other: LogNum): LogProduct = LogProduct(logs + other)

    fun isZero(): Boolean = logs[0].isZero()

    // TODD this should use simplified
    override operator fun equals(other: Any?): Boolean {
        if (other == null || other !is LogProduct) {
            return false
        }

        val logSort: (LogNum, LogNum) -> Int = { logNum1, logNum2 ->
            if (logNum1.coefficient != logNum2.coefficient) {
                logNum1.coefficient.compareTo(logNum2.coefficient)
            } else {
                logNum1.number.compareTo(logNum2.number)
            }
        }

        return logs.sortedWith(logSort) == other.logs.sortedWith(logSort)
    }

    // TODO update when bases are added
    fun getSimplified(): LogProduct {
        if (logs.any { it.isZero() }) {
            return ZERO
        }

        val newLogs = logs.filter { it != LogNum.ONE }
            .ifEmpty { listOf(LogNum.ONE) }

        return LogProduct(newLogs)
    }

    companion object {
        val ZERO = LogProduct(listOf(LogNum.ZERO))
        val ONE = LogProduct(listOf(LogNum.ONE))
    }
}
