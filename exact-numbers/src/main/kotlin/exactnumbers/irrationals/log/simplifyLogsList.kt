package exactnumbers.irrationals.log

import exactnumbers.irrationals.pi.Pi
import shared.NumType
import kotlin.math.abs

// simplify product of logs
internal fun simplifyLogsList(logs: List<NumType>): List<Log> {
    logs as List<Log>

    if (logs.any(Log::isZero)) {
        return listOf(Log.ZERO)
    }

    // need to simplify to same base --> at what point?

    val newLogs = logs.filter { it != Log.ONE } // remove ones
        .groupBy { Pair(it.number, it.base) } // remove inverses
        .flatMap { pair ->
            val currentLogs = pair.value

            val countDivided = currentLogs.count { it.isDivided }
            val countNotDivided = currentLogs.size - countDivided
            when {
                countDivided == countNotDivided -> listOf()
                countDivided > countNotDivided -> List(countDivided - countNotDivided) { Log(pair.key.first, pair.key.second, true) }
                else -> List(countNotDivided - countDivided) { Log(pair.key.first, pair.key.second, false) }
            }
        }.ifEmpty { listOf(Log.ONE) }

    return newLogs.sorted()
}

internal fun simplifyPi(piCount: Int): List<Pi> {
    return when {
        piCount == 0 -> listOf()
        piCount < 0 -> List(abs(piCount)) { Pi(isDivided = true) }
        else -> List(abs(piCount)) { Pi(isDivided = false) }
    }
}
