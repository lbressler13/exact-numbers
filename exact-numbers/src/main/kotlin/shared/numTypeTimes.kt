package shared

import exactnumbers.exactfraction.ExactFraction
import exactnumbers.irrationals.logs.LogNum
import expressions.term.Term

internal fun genericTimes(value: NumType, other: NumType): Term {
    val coefficient = ExactFraction.ONE
    val logs: MutableList<LogNum> = mutableListOf()
    var piCount = 0

    when (value.type) {
        "log" -> logs += value as LogNum
        "pi" -> piCount += 1
    }

    when (other.type) {
        "log" -> logs += other as LogNum
        "pi" -> piCount += 1
    }

    return Term(logs, piCount, coefficient)
}
