package exactnumbers.irrationals.logs

import exactnumbers.irrationals.pi.Pi
import shared.NumType
import kotlin.math.abs

// simplify product of logs
internal fun simplifyLogsList(logs: List<NumType>): List<LogNum> {
    logs as List<LogNum>

    if (logs.any(LogNum::isZero)) {
        return listOf(LogNum.ZERO)
    }

    // need to simplify to same base --> at what point?

    val newLogs = logs.filter { it != LogNum.ONE } // remove ones
        .groupBy { Pair(it.number, it.base) } // remove inverses
        .flatMap { pair ->
            val currentLogs = pair.value

            val countDivided = currentLogs.count { it.isDivided }
            val countNotDivided = currentLogs.size - countDivided
            when {
                countDivided == countNotDivided -> listOf()
                countDivided > countNotDivided -> List(countDivided - countNotDivided) { LogNum(pair.key.first, pair.key.second, true) }
                else -> List(countNotDivided - countDivided) { LogNum(pair.key.first, pair.key.second, false) }
            }
        }.ifEmpty { listOf(LogNum.ONE) }

    return newLogs.sorted()
}

internal fun simplifyPi(piCount: Int): List<Pi> {
    return when {
        piCount == 0 -> listOf()
        piCount < 0 -> List(abs(piCount)) { Pi(isDivided = true) }
        else -> List(abs(piCount)) { Pi(isDivided = false) }
    }
}
