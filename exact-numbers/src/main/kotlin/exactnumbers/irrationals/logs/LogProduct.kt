package exactnumbers.irrationals.logs

// /**
// * Representation of the product of several logs, and a rational coefficient for the product
// *
// * @param logs [List<LogNum>]: list of logs to multiply
// * @param coefficient [ExactFraction]: coefficient for product
// * @throws [Exception] if list of logs is empty
// */
// class LogProduct(logs: List<LogNum>, coefficient: ExactFraction) {
//    val logs: List<LogNum>
//    val coefficient: ExactFraction
//
//    init {
//        when {
//            logs.isEmpty() -> throw Exception("LogProduct must contain at least one value")
//            coefficient == ExactFraction.ZERO || logs.any { it.isZero() } -> {
//                this.logs = listOf(LogNum.ZERO)
//                this.coefficient = ExactFraction.ZERO
//            }
//            else -> {
//                this.logs = logs
//                this.coefficient = coefficient
//            }
//        }
//    }
//
//    constructor(logs: List<LogNum>) : this(logs, ExactFraction.ONE)
//
//    operator fun unaryMinus() = LogProduct(logs, -coefficient)
//    operator fun unaryPlus() = this
//
//    operator fun times(other: LogProduct): LogProduct = LogProduct(logs + other.logs, coefficient * other.coefficient)
//    operator fun times(other: LogNum): LogProduct = LogProduct(logs + other, coefficient)
//
//    fun isZero(): Boolean = coefficient.isZero() || logs[0].isZero()
//
//    override operator fun equals(other: Any?): Boolean {
//        if (other == null || other !is LogProduct) {
//            return false
//        }
//
//        // not a real sort, just used for purposes of comparing lists
//        val logSort: (LogNum, LogNum) -> Int = { logNum1, logNum2 ->
//            if (logNum1.number != logNum2.number) {
//                logNum1.number.compareTo(logNum2.number)
//            } else {
//                logNum1.base.compareTo(logNum2.base)
//            }
//        }
//
//        val simplified = getSimplified()
//        val otherSimplified = other.getSimplified()
//
//        return simplified.coefficient == otherSimplified.coefficient &&
//            simplified.logs.sortedWith(logSort) == otherSimplified.logs.sortedWith(logSort)
//    }
//
//    /**
//     * Get simplified version of log product by simplifying zero and removing ones that don't affect value
//     *
//     * @return [LogProduct]: simplified log product
//     */
//    fun getSimplified(): LogProduct {
//        // zero
//        if (coefficient.isZero() || logs.any { it.isZero() }) {
//            return ZERO
//        }
//
//        // remove unnecessary ones
//        val newLogs = logs.filter { it != LogNum.ONE }
//            .ifEmpty { listOf(LogNum.ONE) }
//
//        return LogProduct(newLogs, coefficient)
//    }
//
//    override fun hashCode(): Int = Pair(logs, coefficient).hashCode()
//
//    override fun toString(): String {
//        val logsString = logs.joinToString("x")
//        val coefficientString = if (coefficient.denominator == BigInteger.ONE) {
//            coefficient.numerator.toString()
//        } else {
//            coefficient.toString()
//        }
//        return "${coefficientString}x$logsString"
//    }
//
//    companion object {
//        val ZERO = LogProduct(listOf(LogNum.ZERO))
//        val ONE = LogProduct(listOf(LogNum.ONE))
//    }
// }
